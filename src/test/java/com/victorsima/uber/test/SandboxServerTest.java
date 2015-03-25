package com.victorsima.uber.test;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.util.WebConnectionWrapper;
import com.squareup.okhttp.Response;
import com.victorsima.uber.UberAuthService;
import com.victorsima.uber.Utils;
import com.victorsima.uber.model.AccessToken;
import com.victorsima.uber.model.Products;
import com.victorsima.uber.model.UserActivity;
import com.victorsima.uber.model.request.Request;
import com.victorsima.uber.model.request.RequestBody;
import com.victorsima.uber.model.request.RequestMap;
import com.victorsima.uber.model.request.SurgeConfirmationError;
import com.victorsima.uber.model.sandbox.SandboxProductBody;
import com.victorsima.uber.model.sandbox.SandboxRequestBody;
import org.junit.Before;
import org.junit.Test;
import retrofit.RetrofitError;

import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests for requests using uber sandbox
 *
 */
public class SandboxServerTest extends BaseTest {

    private static final int delay = 3000;// use a delay to allow the PUT calls to take effect

    @Override
    public boolean runUsingSandboxServer() {
        return true;
    }

    @Override
    @Before
    public void setup() throws Exception {
        super.setup();
        if (!client.hasAccessToken()) {
            retrieveAccessToken();
        }
    }

    @Test
    public void testHistory() throws Exception {
        UserActivity userActivity = client.getApiService().getUserActivity(null, null);
        assertNotNull("User Activity is null", userActivity);
        assertNotNull(userActivity.getHistory());
        assertTrue(userActivity.getHistory().size() > 0);
    }

    @Test
    public void testRiderCancelRequestStatus() throws Exception {
        Request request = getRequest();
        client.getApiService().deleteRequest(request.getRequestId());
        Thread.sleep(delay);
        request = client.getApiService().getRequestDetails(request.getRequestId());
        assertEquals(request.getStatus(), RequestBody.Status.RIDER_CANCELED);
    }

    @Test
    public void testNoDriverAvailableRequestStatus() throws Exception {
        Products products = client.getApiService().getProducts(Double.parseDouble(latitude), Double.parseDouble(longitude));
        String productId = products.getProducts().get(0).getProductId();
        client.getSandboxService().putProducts(productId, new SandboxProductBody(null, false));
        Thread.sleep(delay);
        try {
            Request request = client.getApiService().postRequest(new RequestBody(productId,
                    Double.parseDouble(startLatitude),
                    Double.parseDouble(startLongitude),
                    Double.parseDouble(endLatitude),
                    Double.parseDouble(endLongitude),
                    null));
        } catch (RetrofitError re) {
            assertEquals(re.getResponse().getStatus(), 409);
            assertEquals(re.getResponse().getReason(), RequestBody.Status.NO_DRIVERS_AVAILABLE.value());
        }

    }

    @Test
    public void testRequestStatus() throws Exception {
        Request request = getRequest();
        Thread.sleep(delay);
        /**
         * processing is the default state and cannot be updated, simply check status
         */
        request = client.getApiService().getRequestDetails(request.getRequestId());
        assertEquals(request.getStatus(), RequestBody.Status.PROCESSING);

        client.getSandboxService().putRequest(request.getRequestId(), new SandboxRequestBody(RequestBody.Status.ACCEPTED));
        Thread.sleep(delay);
        request = client.getApiService().getRequestDetails(request.getRequestId());
        assertEquals(request.getStatus(), RequestBody.Status.ACCEPTED);

        client.getSandboxService().putRequest(request.getRequestId(), new SandboxRequestBody(RequestBody.Status.ARRIVING));
        Thread.sleep(delay);
        request = client.getApiService().getRequestDetails(request.getRequestId());
        assertEquals(request.getStatus(), RequestBody.Status.ARRIVING);

        client.getSandboxService().putRequest(request.getRequestId(), new SandboxRequestBody(RequestBody.Status.IN_PROGRESS));
        Thread.sleep(delay);
        request = client.getApiService().getRequestDetails(request.getRequestId());
        assertEquals(request.getStatus(), RequestBody.Status.IN_PROGRESS);

        client.getSandboxService().putRequest(request.getRequestId(), new SandboxRequestBody(RequestBody.Status.DRIVER_CANCELLED));
        Thread.sleep(delay);
        request = client.getApiService().getRequestDetails(request.getRequestId());
        assertEquals(request.getStatus(), RequestBody.Status.DRIVER_CANCELLED);
    }

    @Test
    public void testCompletedRequestStatus() throws Exception {
        Request request = getRequest();
        Response response = client.getSandboxService().putRequest(request.getRequestId(), new SandboxRequestBody(RequestBody.Status.COMPLETED));
        Thread.sleep(delay);
        request = client.getApiService().getRequestDetails(request.getRequestId());
        assertEquals(request.getStatus(), RequestBody.Status.COMPLETED);
    }

    @Test
    public void testRequestMap() throws Exception {
        Request request = getRequest();
        RequestMap requestMap = client.getApiService().getRequestMap(request.getRequestId());
        assertNotNull(requestMap.getHref());
    }

    @Test
    public void testSurgeConfirmation() throws Exception {
        Products products = client.getApiService().getProducts(Double.parseDouble(latitude), Double.parseDouble(longitude));
        String productId = products.getProducts().get(0).getProductId();
        client.getSandboxService().putProducts(productId, new SandboxProductBody(1.5f, true));
        Thread.sleep(delay);

        try {

            Request request = client.getApiService().postRequest(new RequestBody(productId,
                    Double.parseDouble(startLatitude),
                    Double.parseDouble(startLongitude),
                    Double.parseDouble(endLatitude),
                    Double.parseDouble(endLongitude),
                    null));
        } catch (RetrofitError re) {
            assertEquals(re.getResponse().getStatus(), 409);

            SurgeConfirmationError surgeConfirmationError = Utils.parseSurgeConfirmationError(client.getGson(), re);
            assertNotNull("Surge Confirmation URL not found", surgeConfirmationError.getMeta().getSurgeConfirmation().getHref());
            assertNotNull("Surge Confirmation Id not found", surgeConfirmationError.getMeta().getSurgeConfirmation().getSurgeConfirmationId());

            //mock chrome browser to display confirmation page
            final WebClient webClient = new WebClient(BrowserVersion.CHROME);
            webClient.setAjaxController(new NicelyResynchronizingAjaxController());
            webClient.getOptions().setRedirectEnabled(true);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setCssEnabled(true);
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setUseInsecureSSL(true);
            webClient.getCookieManager().setCookiesEnabled(true);
            final HtmlPage confirmationPage = webClient.getPage(surgeConfirmationError.getMeta().getSurgeConfirmation().getHref());
            HtmlAnchor acceptAnchor = (HtmlAnchor) confirmationPage.getElementById("accept-surge");
            assertNotNull("Accept button not found", acceptAnchor);
            Page page = acceptAnchor.click();
            Thread.sleep(delay);

            Request request = client.getApiService().postRequest(new RequestBody(productId,
                    Double.parseDouble(startLatitude),
                    Double.parseDouble(startLongitude),
                    Double.parseDouble(endLatitude),
                    Double.parseDouble(endLongitude),
                    surgeConfirmationError.getMeta().getSurgeConfirmation().getSurgeConfirmationId()));

            assertNotNull("Request is null", request);
            assertNotNull("Request id is null", request.getRequestId());
            assertNotNull("Request status is null", request.getStatus());

        }

    }

    /**
     * Calls getProducts and uses the first result to initiate a new request
     * @return Request
     */
    private Request getRequest() throws Exception{
        Products products = client.getApiService().getProducts(Double.parseDouble(latitude), Double.parseDouble(longitude));
        String productId = products.getProducts().get(0).getProductId();
        //reset product to default product state
        client.getSandboxService().putProducts(productId, new SandboxProductBody(1.0f, true));
        Thread.sleep(delay);
        Request request = client.getApiService().postRequest(new RequestBody(productId,
                Double.parseDouble(startLatitude),
                Double.parseDouble(startLongitude),
                Double.parseDouble(endLatitude),
                Double.parseDouble(endLongitude),
                null));
        assertNotNull("Request is null", request);
        assertNotNull("Request id is null", request.getRequestId());
        assertNotNull("Request status is null", request.getStatus());
        Thread.sleep(delay);
        return request;
    }

    /**
     * Initiates OAuth authorization using a mock chrome browser and retrieves and access token
     * @throws Exception
     */
    private void retrieveAccessToken() throws Exception {
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.setWebConnection(new WebConnectionWrapper(webClient) {
            @Override
            public WebResponse getResponse(final WebRequest request) throws IOException {

                WebResponse response = super.getResponse(request);
                String location = response.getResponseHeaderValue("location");
                if (response.getStatusCode() == 302 && location != null && location.startsWith(client.getClientRedirectUri())) {

                    location = location.replace(client.getClientRedirectUri() + "?", "");
                    String[] nameValues = location.split("&");
                    for (String nameValue : nameValues) {
                        int idx = nameValue.indexOf("=");
                        if ("code".equals(URLDecoder.decode(nameValue.substring(0, idx), "UTF-8"))) {
                            String authorizationCode = URLDecoder.decode(nameValue.substring(idx + 1), "UTF-8");
                            AccessToken accessToken = client.getAuthService().requestAccessToken(clientId, clientSecret, authorizationCode, "authorization_code", redirectUrl);
                            client.setAccessToken(accessToken.getAccessToken());
                            client.setRefreshToken(accessToken.getRefreshToken());
                            break;
                        }
                    }

                    //return empty response
                    request.setUrl(new URL("http://redirect"));
                    WebResponseData data = new WebResponseData("".getBytes(), 200, "OK", Collections.EMPTY_LIST);
                    return new WebResponse(data, request, 10);

                }
                return response;
            }
        });
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setRedirectEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setCssEnabled(true);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getCookieManager().setCookiesEnabled(true);

        String authUrl = client.getAuthorizeUrl(new String[] {UberAuthService.SCOPE_PROFILE, UberAuthService.SCOPE_HISTORY_LITE, UberAuthService.SCOPE_REQUEST});
        final HtmlPage loginPage = webClient.getPage(authUrl);
        final HtmlForm form = loginPage.getForms().get(0);
        final HtmlTextInput emailInputText = form.getInputByName("email");
        final HtmlPasswordInput passwordInputText = form.getInputByName("password");
        final HtmlButton submitButton = (HtmlButton) form.getByXPath("//button[@type='submit']").get(0);
        emailInputText.setText(username);
        passwordInputText.setText(password);
        final Page scopePage = submitButton.click();
        /**
         * Ff the user already accepted scope permissions, the accept screen is skipped and we are redirected to the authorization code
         */
        //check for Accept html page
        if (scopePage instanceof HtmlPage) {
            //click accept button
            final HtmlForm form2 = ((HtmlPage)scopePage).getForms().get(0);
            final HtmlButton allowButton = (HtmlButton) form2.getByXPath("//button[@value='yes']").get(0);
            allowButton.click();
        }
    }
}
