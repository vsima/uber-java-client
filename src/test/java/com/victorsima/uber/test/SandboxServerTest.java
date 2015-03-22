package com.victorsima.uber.test;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;
import com.gargoylesoftware.htmlunit.util.WebConnectionWrapper;
import com.victorsima.uber.model.AccessToken;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.io.IOException;
import java.net.*;
import java.util.Collections;

import static org.junit.Assert.assertTrue;

/**
 * Created by victorsima on 8/20/14.
 */
public class SandboxServerTest extends BaseTest {

    @Override
    public boolean runUsingSandboxServer() {
        return true;
    }

    @Test
    public void testOAuthAuthentication() throws Exception{
        client.setAccessToken(null);
        retrieveAccessToken();
        assertTrue("Access Token is not found", client.hasAccessToken());
    }

    @Override
    @Before
    public void setup() throws Exception {
        super.setup();
        if (!client.hasAccessToken()) {
            retrieveAccessToken();
        }
    }

    private void retrieveAccessToken() throws IOException {
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

        String authUrl = client.getAuthorizeUrl();
        final HtmlPage loginPage = webClient.getPage(authUrl);
        final HtmlForm form = loginPage.getForms().get(0);
        final HtmlTextInput emailInputText = form.getInputByName("email");
        final HtmlPasswordInput passwordInputText = form.getInputByName("password");
        final HtmlButton submitButton = (HtmlButton) form.getByXPath("//button[@type='submit']").get(0);
        emailInputText.setText(username);
        passwordInputText.setText(password);
        final Page scopePage = submitButton.click();
        //if we already accepted scope permissions, it should redirect and give us our authorization code
        //check for html page to see if we need to accept
        if (scopePage instanceof HtmlPage) {
            final HtmlForm form2 = ((HtmlPage)scopePage).getForms().get(0);
            final HtmlButton allowButton = (HtmlButton) form2.getByXPath("//button[@value='yes']").get(0);
            allowButton.click();
        }
    }
}
