package com.victorsima.uber;

import com.google.gson.Gson;
import com.victorsima.uber.model.request.SurgeConfirmationError;
import org.apache.commons.io.IOUtils;
import retrofit.RetrofitError;

import java.io.IOException;

/**
 * Created by victorsima on 3/25/15.
 */
public class Utils {

    //*************
    // Error helpers
    //*************

    /**
     * Parses surge confirmation retrofit error (409) into a model object
     * @param gson
     * @param retrofitError
     * @return
     * @throws IOException
     */
    public static SurgeConfirmationError parseSurgeConfirmationError(Gson gson, RetrofitError retrofitError) throws IOException {
        String body = IOUtils.toString(retrofitError.getResponse().getBody().in(), "UTF-8");
        IOUtils.closeQuietly(retrofitError.getResponse().getBody().in());
        return gson.fromJson(body, SurgeConfirmationError.class);
    }

    //*************
    // OAuth helpers
    //*************

    /**
     * Generates the initial url for the OAuth authorization flow
     * @return oauth authorize url
     */
    public static String generateAuthorizeUrl(String oAuthUri, String clientId, String[] scope){
        StringBuilder sb = new StringBuilder(oAuthUri);
        sb.append("/oauth/authorize?response_type=code");
        sb.append("&client_id=" + clientId);
        if (scope != null) {
            sb.append("&scope=");
            for (int i = 0; i < scope.length; i++) {
                if (i > 0) {
                    sb.append("%20");
                }
                sb.append(scope[i]);
            }
        }
        return sb.toString();
    }
}
