package com.example.youcloudp1000sdk.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WebSocketUtils {
    private static OkHttpClient client;
    public static WebSocket webSocket;
    //public static Request request = new Request.Builder().url("ws://10.101.10.73:9045/YouCloudCashAtHome/wsreq").build();

    public static Request request = new Request.Builder().url("ws://" + NetworkController.IP + "/YouCloudCashAtHome/wsreq").build();

    public static boolean connect(WebSocketListener listener) {
        try {
            client = new OkHttpClient();
            webSocket = client.newWebSocket(request, listener);
            client.dispatcher().executorService().shutdown();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean send(String msg) {
        try {
            if (webSocket != null) {
                webSocket.send(msg);
                return true;
            } else {
                connect(new WebSocketListener() {
                    @Override
                    public void onOpen(WebSocket webSocket, Response response) {
                        super.onOpen(webSocket, response);
                        send(msg);
                    }
                });
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            connect(new WebSocketListener() {
                @Override
                public void onOpen(WebSocket webSocket, Response response) {
                    super.onOpen(webSocket, response);
                    send(msg);
                }
            });
            return true;
        }
    }
}
