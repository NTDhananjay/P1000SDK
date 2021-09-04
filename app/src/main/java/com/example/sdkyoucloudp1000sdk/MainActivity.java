package com.example.sdkyoucloudp1000sdk;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youcloudp1000sdk.P1000CallBacks;
import com.example.youcloudp1000sdk.custom_view.ProcessDialog;
import com.example.youcloudp1000sdk.model.P1000Request;
import com.example.youcloudp1000sdk.utils.TransactionType;
import com.example.youcloudp1000sdk.view.fragment.PairedDeviceFragment;
import com.example.youcloudp1000sdk.view.fragment.P1000Manager;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    String fName;
    P1000Manager pinpadFragmentSettings;
    PairedDeviceFragment pairedDeviceFragment;
    TextView statusTv, responseCodeTv, responseMessageTv, version;
    CustomDialog customDialog;

    protected ProcessDialog processdialog = null;
    Button btnLoadAidKey,btnStartTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        statusTv = findViewById(R.id.status_code);
        responseCodeTv = findViewById(R.id.response_code);
        responseMessageTv = findViewById(R.id.response_message);


        btnLoadAidKey = findViewById(R.id.btnLoadAidKey);
        btnStartTransaction = findViewById(R.id.btnStartTransaction);

       pinpadFragmentSettings = P1000Manager.getInstance(MainActivity.this,"yCPDTW1iDgCO3VSn8Orn5DmEQth8MTGj");

        btnLoadAidKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTaskRunner runner = new AsyncTaskRunner();
                runner.execute();
            }
        });

        btnStartTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   pinpadFragmentSettings = new PinpadFragmentSettings();

                showStatusDialog(true);
                P1000Request p1000Request = new P1000Request();
                p1000Request.setUsername("Avi");
                p1000Request.setPassword("1234");
                p1000Request.setRefCompany("SIL");
                p1000Request.setMid("442000227364352");
                p1000Request.setTid("42099143");
                p1000Request.setTransactionId(pinpadFragmentSettings.getTransactionId());
                p1000Request.setImei("3dc0c6f6b9429aa");
                p1000Request.setImsi("null");
                p1000Request.setTxn_amount("10.0");
                p1000Request.setRequestCode(TransactionType.DEBIT);

                pinpadFragmentSettings.execute(p1000Request, new P1000CallBacks() {
                    @Override
                    public void successCallback(JSONObject jsonObject) {
                       hideDialog();
                        Log.d("SHANKY", "successCallback: " + jsonObject);
                        try {
                            String status = "Success";
                            int responseCode = -1;
                            JSONObject responseMessage = null;
                               //     = new JSONObject();

                            if (jsonObject.has("Msg")) {
                                status = jsonObject.getString("Msg");
                            }
                            if (jsonObject.has("ResponseCode")) {
                                responseCode = jsonObject.getInt("ResponseCode");
                            }
                            if (jsonObject.has("Response")) {
                             //   responseMessage = jsonObject.getJSONObject("Response");

                                responseMessage = jsonObject.getJSONObject("Response");
                            }

                            /*{"Msg":"Success","ResponseCode":0,"Response":"{\"aid\":\"A0000000031010\",
                            \"applName\":\"Visa International\",\"arpcdata\":\"012910a4262415376775b263030\",
                            \"authid\":\"013937\",\"batchNo\":\"212420\",\"cardno\":\"401806XXXXXX8185\",
                            \"date\":\"30.08.2021 10:45:31\",\"ed\":\"404.8#404.8\",\"hitachiResCode\":\"00\",
                            \"invoiceNumber\":\"126844\",\"msg\":\"Successful\",\"printmsg\":\"\",
                            \"respCode\":\"00\",\"rrn\":\"124210126844\",\"serviceBin\":\"Offus\",\"status\":true,
                            \"token\":\"124210891504\",
                            \"transhistory\":[],\"tsi\":\"E800\",\"tvr\":\"0080048800\"}"}*/

                            setMessage(status, responseCode, responseMessage.toString());
                        } catch (JSONException jsonexception) {
                            jsonexception.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void progressCallback(String message) {
                      //  hideDialog();
                        Log.d("SHANKY", "progressCallback: " + message);
                        updateTransactionMessage(message);
                    }

                    @Override
                    public void failureCallback(JSONObject jsonObject) {
                        hideDialog();
                        Log.d("SHANKY", "failureCallback: " + jsonObject);
                        try {
                            String status = "Success";
                            int responseCode = -1;

                            if (jsonObject.has("Msg")) {
                                status = jsonObject.getString("Msg");
                            }
                            if (jsonObject.has("ResponseCode")) {
                                responseCode = jsonObject.getInt("ResponseCode");
                            }
                            if (responseCode == 100) {
                                try {
                                    JSONObject responseMessage = new JSONObject();
                                    if (jsonObject.has("Response")) {
                                        responseMessage = jsonObject.getJSONObject("Response");
                                        setMessage(status, responseCode, responseMessage.toString());
                                    }
                                } catch (JSONException jsonexception) {
                                    String responseMessage = "";
                                    if (jsonObject.has("Response")) {
                                        responseMessage = jsonObject.getString("Response");
                                    }
                                    setMessage(status, responseCode, responseMessage);
                                    jsonexception.printStackTrace();
                                }

                            } else {
                                String responseMessage = "";
                                if (jsonObject.has("Response")) {
                                    responseMessage = jsonObject.getString("Response");
                                }
                                setMessage(status, responseCode, responseMessage);
                            }
                        } catch (JSONException jsonexception) {
                            jsonexception.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });


            }
        });
    }

    private void updateTransactionMessage(final String message) {


        runOnUiThread(new Runnable() {
            public void run() {
                if (customDialog != null && customDialog.isShowing()) {
                    customDialog.setMessage(message);
                }            }
        });
    }



    private void showStatusDialog(boolean show) {
        if (show) {
            if (customDialog == null)
                customDialog = new CustomDialog(MainActivity.this);

            customDialog.show();
            btnLoadAidKey.setEnabled(false);
        } else {
            if (customDialog != null) {
                customDialog.dismiss();
                btnLoadAidKey.setEnabled(true);
            }
        }
    }

    private void hideDialog() {
        runOnUiThread(() -> showStatusDialog(false));
    }
    @SuppressLint("SetTextI18n")
    private void setMessage(final String status, final int responseCode,
                            final String responseMessage) {
        runOnUiThread(() -> {
            if (statusTv != null && status != null && !status.isEmpty()) {
                statusTv.setText(status);
            }
            if (responseCodeTv != null) {
                responseCodeTv.setText(responseCode + "");
            }
            if (responseMessageTv != null && responseMessage != null && !responseMessage.isEmpty()) {
                responseMessageTv.setText(responseMessage);
            }
        });
    }


    /*===========================================================================*/

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;
        boolean isSuccess = false;

        @Override
        protected String doInBackground(String... params) {
            try {
                isSuccess = pinpadFragmentSettings.login();
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            progressDialog = null;
            if (isSuccess) {
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Load Aid Successful", Toast.LENGTH_SHORT).show());
            } else {
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Load Aid Not Successful", Toast.LENGTH_SHORT).show());
            }
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Loading Aid keys.");
            progressDialog.show();
        }

        @Override
        protected void onProgressUpdate(String... text) {
        }
    }
}