package com.example.youcloudp1000sdk.retrofit;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by shankar.savant on 4/21/2017.
 */

public interface ApiListener {
    @POST("Y2000Req")
    Call<ResponseParams> callService(@Body RequestParams jsonReq);

    @Multipart
    @POST("wallet/loadWalletReq")
    Call<ResponseParams> callTopupMultipart(@Part("file\"; filename=\"myfile.png") RequestBody file, @Part("data") RequestBody data);

    @Multipart
    @POST("wallet/loadWalletReq")
    Call<ResponseParams> callTopupMultipart(@Part("data") RequestBody data);

    @Multipart
    @POST("log/upload")
    Call<ResponseBody> callMultipart(@Part("description") RequestBody body, @Part List<MultipartBody.Part> files);

}
