package com.example.youcloudp1000sdk.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.youcloudp1000sdk.R;
import com.example.youcloudp1000sdk.custom_view.DialogUtils;
import com.example.youcloudp1000sdk.custom_view.FailCustomDialog;
import com.example.youcloudp1000sdk.custom_view.SuccessCustomDialog;
import com.example.youcloudp1000sdk.retrofit.NetworkController;
import com.example.youcloudp1000sdk.retrofit.RequestParams;
import com.example.youcloudp1000sdk.retrofit.ResponseListener;
import com.example.youcloudp1000sdk.retrofit.ResponseParams;
import com.example.youcloudp1000sdk.utils.Constants;
import com.example.youcloudp1000sdk.utils.MyConst;
import com.example.youcloudp1000sdk.utils.Session;
import com.example.youcloudp1000sdk.utils.SessionConst;
import com.example.youcloudp1000sdk.utils.SessionConstants;
import com.google.gson.Gson;

import retrofit2.Response;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.example.youcloudp1000sdk.utils.PinBlock.getISO1Pinblock_TPK;

/**
 * Created on 4/24/2017.
 * Change history : 1.0
 * date : 28 / 8 /2017
 * Description : Closer of VAPT velvalnerability
 * Modified By : Shankar
 * <p>
 * Login with username and password.
 * ***********************************************
 * If password forgotten then user can change password
 * ***********************************************
 * Can see terms and condition in "About app" menu
 */


public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    String imsi="null", imei="865650030389197";
    TelephonyManager telephonyManager;
    EditText txtpass;
    AutoCompleteTextView txtuname;
    CheckBox chkRemember;
    TextView txtForgot, about_App_txt;
    String alluser = ",", verCode = "", verName = "";
    String[] users;
    public static final int RequestPermissionCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        btnLogin = (Button) findViewById(R.id.btnlogin);
        txtuname = (AutoCompleteTextView) findViewById(R.id.txtuname);
        txtpass = (EditText) findViewById(R.id.txtpass);
        chkRemember = (CheckBox) findViewById(R.id.checkBoxRem);
        txtForgot = (TextView) findViewById(R.id.txtforgot);
        about_App_txt = (TextView) findViewById(R.id.about_App_txt);

        txtuname.setFilters(new InputFilter[]{
                new InputFilter() {
                    public CharSequence filter(CharSequence src, int start,
                                               int end, Spanned dst, int dstart, int dend) {
                        if (src.equals("")) { // for backspace
                            return src;
                        }
                        if (src.toString().matches("[a-zA-Z0-9]+")) {
                            return src;
                        }
                        return "";
                    }
                }
        });


        if (getIntent().getBooleanExtra("exitme", false) == true) {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
        }

        //appversion
        PackageManager manager = this.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        verName = "" + info.versionName;
        verCode = "" + info.versionCode;
        Log.d("Version Code", "" + info.versionCode);
        Log.d("Version Name", "" + info.versionName);

        //remember user logic
        alluser = alluser + Session.getString(getApplicationContext(), "allusers");

        Log.d("AllUsers : ", alluser);
        users = alluser.split(",");
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_row_item, users);

        txtuname.setAdapter(adapter);
        txtuname.setThreshold(1);

        if (!Session.getBoolean(getApplicationContext(), SessionConstants.Session_isfirst)) {
            //changedefaultsim();
        }

        txtuname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.equals(" ")) {
                    txtuname.setText(txtuname.getText().toString().replaceAll(" ", ""));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

  /*      txtForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtuname.setError(null);
                txtpass.setError(null);
                Intent i = new Intent(LoginActivity.this, ForgotPasswordActivty.class);
                i.putExtra("flag", "forgot_pass");
                startActivity(i);
            }
        });*/

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get imei and imsi
                if (checkPermission()) {
                    //permission already granted
                    callLogin();
                } else {
                    //request permissions
                    requestPermission();
                }
            }
        });

        about_App_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkPermission()) {
                    //permission already granted
                 //   startActivity(new Intent(LoginActivity.this, AboutAppActivity.class));
                } else {
                    //request permissions
                    requestPermission();
                }
            }
        });
    }

    public void callLoginWS() {

        //Req  : {"requestcode":"doLogin","username":"shankar","password":"1111","imei":"352672078500352","imsi":"405874001163008","verCode":"1","verName":"1.0.10"}
        //Resp : {"companyid":"1000001010","ismerchant":"N","merchantlocation":"merLocation-MUMBAI","merchantname":"Merchant Name Here","mobileno":"8888602188","msg":"SUCCESS","name":"shankar","sessiontimeout":"300","status":"true"}

        RequestParams r = new RequestParams();
        r.setRequestcode(Constants.wslogincode);
        r.setUsername("" + txtuname.getText().toString().trim().toUpperCase());
        r.setPassword("" + getISO1Pinblock_TPK(txtpass.getText().toString().trim(), Constants.LoginTPK));
        r.setImei("" + imei);
        r.setImsi("" + imsi);
        r.setVerCode("" + verCode);
        r.setVerName("" + verName);
        //Log.d("requets to server", new Gson().toJson(r));
        NetworkController.getInstance().sendRequest(r, new ResponseListener() {
            @Override
            public void onResponseSuccess(Response<ResponseParams> response, SuccessCustomDialog d) {
                Log.d("SHANKY", "RESPCODE : " + response.code() + " MSG : " + response.message());
                if (response.body() != null) {
                    Log.d("response from server", new Gson().toJson(response.body()));
                    if (response.body().isStatus()) {
                        String[] cur = response.body().getCurrency().split("#");
                        Session.setString(getApplicationContext(), SessionConstants.Session_username, txtuname.getText().toString().trim().toUpperCase());
                        Session.setString(getApplicationContext(), SessionConstants.Session_sessiontimeout, response.body().getSessiontimeout());
                        Session.setString(getApplicationContext(), SessionConstants.Session_companyid, response.body().getCompanyid());
                        Session.setString(getApplicationContext(), SessionConstants.Session_merchantlocation, response.body().getMerchantlocation());
                        Session.setString(getApplicationContext(), SessionConstants.Session_merchantname, response.body().getMerchantname());
                        Session.setString(getApplicationContext(), SessionConstants.Session_ismerchant, response.body().getIsmerchant());
                        Session.setString(getApplicationContext(), SessionConstants.Session_mid, response.body().getMid());
                        Session.setString(getApplicationContext(), SessionConstants.Session_gmid, response.body().getGroupmid());
                        Session.setString(getApplicationContext(), SessionConstants.Session_tid, response.body().getPosId());
                        Session.setString(getApplicationContext(), SessionConstants.Session_currency_name, cur[0]);
                        Session.setString(getApplicationContext(), SessionConstants.Session_currency_code, cur[1]);
                        Session.setString(getApplicationContext(), SessionConstants.Session_appTxReq, "" + response.body().getAppTxReq());
                        Session.setString(getApplicationContext(), SessionConstants.Session_addr1, "" + response.body().getAddr1());
                        Session.setString(getApplicationContext(), SessionConstants.Session_addr2, "" + response.body().getAddr2());
                        Session.setString(getApplicationContext(), SessionConstants.Check_location, "" + response.body().getLocationCheck());
                        Session.setString(getApplicationContext(), SessionConstants.ICICI_MATM, "" + response.body().getMatmTid());
                        if (response.body().getOperatorList() != null)
                            Session.setString(getApplicationContext(), SessionConstants.operators, "" + response.body().getOperatorList());

                        Session.setString(getApplicationContext(), SessionConstants.Wallet_Balance, "" + response.body().getWalletBalance());
                        Session.setString(getApplicationContext(), SessionConstants.service_bin, "" + response.body().getServiceBin());
                        Log.d("address1" + Session.getString(getApplicationContext(), SessionConstants.Session_addr1), "");

                        //AppTxReq : 00 = req sent to middleware, 01 : Req sent to MPU
                       /* if (response.body().getAppTxReq().equalsIgnoreCase("01")) {
                            Session.setString(getApplicationContext(), SessionConstants.Session_mpuip, "" + response.body().getMpuip());
                            Session.setString(getApplicationContext(), SessionConstants.Session_mpuport, "" + response.body().getMpuport());
                            TCPClient.setSERVERIP("" + response.body().getMpuip());
                            TCPClient.setSERVERPORT(Integer.parseInt(response.body().getMpuport() + ""));
                        }*/

                        Log.d("USTATUS : ", response.body().getUstatus().trim());
                        if (response.body().getUstatus().trim().equals("7")) {
                            d.dismiss();
                        /*    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            i.putExtra("uid", "" + txtuname.getText().toString().trim());
                            i.putExtra("flag", Constants.wsfirstpasschangecode);
                            i.putExtra("mono", "");
                            i.putExtra("otp", "");
                            startActivity(i);
                        */} else if (response.body().getUstatus().trim().equals("8")) {
                            txtuname.setText("");
                            txtpass.setText("");
                            d.dismiss();
                            txtuname.setError(null);
                            txtpass.setError(null);
                         /*   Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            i.putExtra("flag", "device_reg");
                            startActivity(i);
                       */ } else {
                            if (response.body().getSessionId() != null)
                                MyConst.sessionToken = response.body().getSessionId();

                            d.dismiss();
                            txtuname.setText("");
                            txtpass.setText("");

                            Session.setString(getApplicationContext(), SessionConstants.trackTmk, "" + response.body().getMkskKeys());
                            Session.setString(getApplicationContext(), SessionConstants.tpkTmk, "" + response.body().getMkskKeys());
                            Session.setString(getApplicationContext(), Constants.INJECTKEY, "T");
                          /*  Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                          */  SessionConst.clearLog(LoginActivity.this);
                            txtuname.requestFocus();
                        }
                    } else {
                        if (response.body().getMsg() != null && response.body().getMsg().contains("Version not match.")) {
                            d.dismiss();
                            DialogUtils.showConfirmDialog(LoginActivity.this, "01", "Version Expired", "" + response.body().getMsg() + ".\n Do you want to install new version?");
                        } else {
                            d.dismiss();
                            FailCustomDialog failCustomDialog = new FailCustomDialog(LoginActivity.this, "Fail...", "" + response.body().getMsg(), "");
                            failCustomDialog.show();
                            txtuname.setText("");
                            txtpass.setText("");
                            txtuname.requestFocus();
                        }
                    }
                } else {
                    d.dismiss();
                    FailCustomDialog failCustomDialog = new FailCustomDialog(LoginActivity.this, "Fail", "Check Internet Connection : " + response.code() + " : " + response.message(), "");
                    failCustomDialog.show();
                    txtuname.setText("");
                    txtpass.setText("");
                    txtuname.requestFocus();
                }
            }

            @Override
            public void onResponseFailure(Throwable t, SuccessCustomDialog d) {
                Log.e("", t.toString());
                d.cancel();
                FailCustomDialog failCustomDialog = new FailCustomDialog(LoginActivity.this, "Fail", "Check Internet Connection", "");
                failCustomDialog.show();
            }
        }, new SuccessCustomDialog(LoginActivity.this, "Logging in", "Please Wait", ""));
    }

    public void callLogin() {
        telephonyManager = (TelephonyManager) LoginActivity.this.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //imei = "" + telephonyManager.getDeviceId();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //imsi = "" + telephonyManager.getSubscriberId();

        Session.setString(getApplicationContext(), SessionConstants.Session_imei, imei);
        Session.setString(getApplicationContext(), SessionConstants.Session_imsi, imsi);
        Log.d("IMEI&IMSI", "" + imei + "-" + imsi);

        if (txtuname.getText().toString().equals("")) {
            txtuname.setError("Please Enter Username");
        } else if (txtpass.getText().toString().trim().length() < 4) {
            txtpass.setError("Please Enter 4 Digit PIN");
        } else if (txtpass.getText().toString().equalsIgnoreCase("0000")) {
            txtpass.setError("Please Enter Valid PIN");
        } else {
            //Remember this user
            if (chkRemember.isChecked()) {
                Log.d("Remember", "Checked");
                if (alluser.contains("," + txtuname.getText().toString().trim())) {
                    Log.d("Remeber user : ", "false");
                } else {
                    Log.d("Remeber user : ", "true");
                    alluser = alluser + txtuname.getText().toString().trim() + ",";
                    Session.setString(getApplicationContext(), "allusers", alluser);
                }
            }
            callLoginWS();
        }
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(LoginActivity.this, new String[]
                {
                        WRITE_EXTERNAL_STORAGE,
                        READ_PHONE_STATE,
                        ACCESS_FINE_LOCATION,
                        "com.pos.permission.SECURITY",
                        "com.pos.permission.ACCESSORY_DATETIME",
                        "com.pos.permission.ACCESSORY_LED",
                        "com.pos.permission.ACCESSORY_BEEP",
                        "com.pos.permission.ACCESSORY_RFREGISTER",
                        "com.pos.permission.CARD_READER_ICC",
                        "com.pos.permission.CARD_READER_PICC",
                        "com.pos.permission.CARD_READER_MAG",
                        "com.pos.permission.COMMUNICATION",
                        "com.pos.permission.PRINTER",
                        "com.pos.permission.EMVCORE"
                }, RequestPermissionCode);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length > 0) {
                    boolean CameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadPhoneStatePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean LocationPermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                    boolean core_1 = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean core_2 = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                    boolean core_3 = grantResults[5] == PackageManager.PERMISSION_GRANTED;
                    boolean core_4 = grantResults[6] == PackageManager.PERMISSION_GRANTED;
                    boolean core_5 = grantResults[7] == PackageManager.PERMISSION_GRANTED;
                    boolean core_6 = grantResults[8] == PackageManager.PERMISSION_GRANTED;
                    boolean core_7 = grantResults[9] == PackageManager.PERMISSION_GRANTED;
                    boolean core_8 = grantResults[10] == PackageManager.PERMISSION_GRANTED;
                    boolean core_9 = grantResults[11] == PackageManager.PERMISSION_GRANTED;
                    boolean core_10 = grantResults[12] == PackageManager.PERMISSION_GRANTED;
                    if (CameraPermission && ReadPhoneStatePermission && LocationPermission && core_1 && core_2 && core_3 && core_4 && core_5 && core_6 && core_7 && core_8 && core_9 && core_10) {
                        Toast.makeText(LoginActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermission() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int ThirdPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);
        int ForthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int core_1 = ContextCompat.checkSelfPermission(getApplicationContext(), "com.pos.permission.SECURITY");
        int core_2 = ContextCompat.checkSelfPermission(getApplicationContext(), "com.pos.permission.ACCESSORY_DATETIME");
        int core_3 = ContextCompat.checkSelfPermission(getApplicationContext(), "com.pos.permission.ACCESSORY_LED");
        int core_4 = ContextCompat.checkSelfPermission(getApplicationContext(), "com.pos.permission.ACCESSORY_BEEP");
        int core_5 = ContextCompat.checkSelfPermission(getApplicationContext(), "com.pos.permission.ACCESSORY_RFREGISTER");
        int core_6 = ContextCompat.checkSelfPermission(getApplicationContext(), "com.pos.permission.CARD_READER_ICC");
        int core_7 = ContextCompat.checkSelfPermission(getApplicationContext(), "com.pos.permission.CARD_READER_PICC");
        int core_8 = ContextCompat.checkSelfPermission(getApplicationContext(), "com.pos.permission.CARD_READER_MAG");
        int core_9 = ContextCompat.checkSelfPermission(getApplicationContext(), "com.pos.permission.COMMUNICATION");
        int core_10 = ContextCompat.checkSelfPermission(getApplicationContext(), "com.pos.permission.EMVCORE");

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ThirdPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ForthPermissionResult == PackageManager.PERMISSION_GRANTED &&
                core_1 == PackageManager.PERMISSION_GRANTED &&
                core_2 == PackageManager.PERMISSION_GRANTED &&
                core_3 == PackageManager.PERMISSION_GRANTED &&
                core_4 == PackageManager.PERMISSION_GRANTED &&
                core_5 == PackageManager.PERMISSION_GRANTED &&
                core_6 == PackageManager.PERMISSION_GRANTED &&
                core_7 == PackageManager.PERMISSION_GRANTED &&
                core_8 == PackageManager.PERMISSION_GRANTED &&
                core_9 == PackageManager.PERMISSION_GRANTED &&
                core_10 == PackageManager.PERMISSION_GRANTED;
    }

}


