package com.example.youcloudp1000sdk.retrofit;


import com.example.youcloudp1000sdk.custom_view.SuccessCustomDialog;
import com.example.youcloudp1000sdk.utils.MyConst;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.CertificatePinner;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Shankar Savant on 4/21/2017.
 */

public class NetworkController {
    public static final String IP = "199.34.22.236:9406";

    //114.79.162.173:5555
    //Juinagar Public
/*    public static String MAIN_URL = "http://114.79.162.173:5556/";//public
    public static String BASE_URL = MAIN_URL + "YouCloudMiddlewareIndCommon/";*/

    //Youcloud Public
//    public static String  MAIN_URL = "https://app.youcloudpayment.in/";
//    public static String BASE_URL = MAIN_URL + "YouCloudMiddlewareFingpay/";

    //9.77 UAT
     public static final String MAIN_URL = "http://199.34.22.225:9087/";

    //public static String MAIN_URL = "http://199.34.22.236:9406/";//public
    public static String BASE_URL = MAIN_URL + "YouCloudMiddlewarePre/";

    //SHANKY
   /* public static final String MAIN_URL = "http://10.101.10.73:9040/"; // local
    public static final String BASE_URL = MAIN_URL + "YouCloudMiddlewareIndCommon/";*/



    private ApiListener apiService;
    private DmtApiListener dmtApiListener;
    private AEPSApiListener aepsResponseListener;

    public NetworkController() {
       // configureURL(false);
        String hostname = "nownow.com.mm";
        CertificatePinner certificatePinner = new CertificatePinner.Builder()
                .add(hostname, "sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=")
                .build();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiListener.class);
        dmtApiListener = retrofit.create(DmtApiListener.class);
        aepsResponseListener = retrofit.create(AEPSApiListener.class);
    }

    public static NetworkController getInstance() {
        return new NetworkController();
    }

    public void sendRequest(RequestParams requestParams, ResponseListener responseListener) {
        requestParams.setSessionId(MyConst.sessionToken);
        Call<ResponseParams> call = apiService.callService(requestParams);
        serverCall(call, responseListener, null);
    }

    public void sendDMTRequest(RequestParamDmt requestParams, DmtResponseListener responseListener) {
        requestParams.setSessionId(MyConst.sessionToken);
        Call<ResponseParamDmt> call = dmtApiListener.callDmtService(requestParams);
        serverDmtCall(call, responseListener, null);
    }

    private void serverDmtCall(Call call, final DmtResponseListener responseListener, final SuccessCustomDialog d) {
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                responseListener.onResponseSuccess(response, d);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                t.printStackTrace();
                responseListener.onResponseFailure(t, d);
            }
        });
    }

    public void sendMultipartRequest(List<MultipartBody.Part> parts, RequestBody description) {
        Call<ResponseBody> call = apiService.callMultipart(description, parts);
        serverCall(call, null, null);
    }

    public void sendRequest(RequestParams requestParams, ResponseListener responseListener, SuccessCustomDialog d) {
        requestParams.setSessionId(MyConst.sessionToken);
        Call<ResponseParams> call = apiService.callService(requestParams);
        serverCall(call, responseListener, d);
        d.show();
    }

    public void sendMultipartNetworkRequest(RequestBody requestFile, RequestBody descBody, ResponseListener responseListener, SuccessCustomDialog processDialog) {
        try {
            Call<ResponseParams> call = apiService.callTopupMultipart(requestFile, descBody);
            serverCall(call, responseListener, processDialog);
            if (processDialog != null)
                processDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMultipartNetworkRequest(RequestBody descBody, ResponseListener responseListener, SuccessCustomDialog processDialog) {
        try {
            Call<ResponseParams> call = apiService.callTopupMultipart(descBody);
            serverCall(call, responseListener, processDialog);
            if (processDialog != null)
                processDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void serverCall(Call call, final ResponseListener responseListener, final SuccessCustomDialog d) {
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                responseListener.onResponseSuccess(response, d);
            }
                         //Response{protocol=http/1.1, code=200, message=, url=http://199.34.22.236:9406/YouCloudMiddlewareIndCommon/Y2000Req}
            @Override
            public void onFailure(Call call, Throwable t) {
                t.printStackTrace();
                responseListener.onResponseFailure(t, d);
            }
        });
    }
/*

    public void sendAEPSRequest(AEPSRequestParams requestParams, AEPSResponseListener responseListener) {
        requestParams.setSessionId(MyConst.sessionToken);
        Call<AEPSResponseParams> call = aepsResponseListener.callService(requestParams);
        serverAEPSCall(call, responseListener, null);
    }

    public void sendAEPSRequest(AEPSRequestParams requestParams, AEPSResponseListener responseListener, SuccessCustomDialog d) {
        requestParams.setSessionId(MyConst.sessionToken);
        Call<AEPSResponseParams> call = aepsResponseListener.callService(requestParams);
        serverAEPSCall(call, responseListener, d);
        d.show();
    }
*/

    private void serverAEPSCall(Call call, final AEPSResponseListener responseListener, final SuccessCustomDialog d) {
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                responseListener.onResponseSuccess(response, d);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                responseListener.onResponseFailure(t, d);
            }
        });
    }
}
