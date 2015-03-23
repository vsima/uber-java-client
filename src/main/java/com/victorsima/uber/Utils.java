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
}
