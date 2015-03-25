package com.victorsima.uber.model.sandbox;

import com.squareup.okhttp.Response;
import retrofit.http.Body;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by victorsima on 3/23/15.
 */
public interface SandboxService {

    /**
     * <p><b>FOR SANDBOX USE ONLY</b></p>
     * <p>Currently the sandbox does not change states automatically the way a real Request in production would,
     * so this endpoint gives the ability to walk an application through the different states of a Request.</p>
     *
     * <p>This endpoint effectively just modifies the status of an ongoing sandbox Request.</p>
     * @param requestId
     * @param request
     * @return
     */
    @PUT("/v1/sandbox/requests/{request_id}")
    Response putRequest(@Path("request_id") String requestId, @Body SandboxRequestBody request);

    /**
     * <p><b>FOR SANDBOX USE ONLY</b></p>
     * <p>Accepts a JSON body indicating what you would like the surge_multiplier to be when making a Request to a particular Product.</p>
     * @param productId
     * @param sandboxProductBody
     * @return
     */
    @PUT("/v1/sandbox/products/{product_id}")
    Response putProducts(@Path("product_id") String productId, @Body SandboxProductBody sandboxProductBody);
}
