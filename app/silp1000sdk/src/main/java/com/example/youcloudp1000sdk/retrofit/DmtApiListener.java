package com.example.youcloudp1000sdk.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by shankar.savant on 4/21/2017.
 */

public interface DmtApiListener {
    @POST("MobileReq/dmt")
    Call<ResponseParamDmt> callDmtService(@Body RequestParamDmt jsonReq);
}
