package com.example.youcloudp1000sdk.view.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.basewin.aidl.OnPrinterListener;
import com.basewin.define.InputPBOCInitData;
import com.basewin.define.PBOCOption;
import com.basewin.services.DeviceInfoBinder;
import com.basewin.services.PBOCBinder;
import com.basewin.services.PrinterBinder;
import com.basewin.services.ServiceManager;
import com.basewin.utils.BytesUtil;
import com.basewin.utils.LoadParamManage;
import com.example.youcloudp1000sdk.R;
import com.example.youcloudp1000sdk.custom_view.EnterDialog;
import com.example.youcloudp1000sdk.custom_view.FailCustomDialog;
import com.example.youcloudp1000sdk.custom_view.ProcessDialog;
import com.example.youcloudp1000sdk.custom_view.SignatureView;
import com.example.youcloudp1000sdk.custom_view.SuccessCustomDialog;
import com.example.youcloudp1000sdk.retrofit.NetworkController;
import com.example.youcloudp1000sdk.retrofit.RequestParams;
import com.example.youcloudp1000sdk.retrofit.ResponseListener;
import com.example.youcloudp1000sdk.retrofit.ResponseParams;
import com.example.youcloudp1000sdk.utils.AndyUtility;
import com.example.youcloudp1000sdk.utils.Constants;
import com.example.youcloudp1000sdk.utils.MyConst;
import com.example.youcloudp1000sdk.utils.ResponseCodeConst;
import com.example.youcloudp1000sdk.utils.Session;
import com.example.youcloudp1000sdk.utils.SessionConst;
import com.example.youcloudp1000sdk.utils.SessionConstants;
import com.example.youcloudp1000sdk.utils.StaticValues;
import com.example.youcloudp1000sdk.y2000.constants.DeviceErrorCodes;
import com.example.youcloudp1000sdk.y2000.constants.GlobalData;
import com.example.youcloudp1000sdk.y2000.constants.MyCUPParam;
import com.example.youcloudp1000sdk.y2000.constants.PinpadInterfaceVersion;
import com.example.youcloudp1000sdk.y2000.constants.StringHelper;
import com.example.youcloudp1000sdk.y2000.constants.listeners.OnConfirmListener;
import com.example.youcloudp1000sdk.y2000.constants.onlinePBOCListener;
import com.google.gson.Gson;
import com.pos.sdk.emvcore.PosEmvCoreManager;
import com.pos.sdk.emvcore.PosEmvParam;
import com.pos.sdk.emvcore.PosTermInfo;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PairedDeviceFragment  {

    ResponseParams serverResponse = new ResponseParams();

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
    TextView txt_remark, merchant_ref, pay_type, txtAmt, merchant_name;
    Button btn_connect_old, btn_connect_new;
    private String from = "", getAmt = "1", getRemark = "";
    Context context;
    String tipamt = "0", currencyname = "", currencycode = "", op = "", status = "", cash_back_amt = "",
            cvm_tag = "", loc_flag = "", modeofPay = "", recharge_type = "", mobile_no = "",
            operator_name = "", operator_code = "", tvr = "", tsi = "", aid = "";
    //Thread t;
    //private ResourceBundle msgBundle;
    SignatureView mSignature;
    //private IntentFilter mIntentFilter;
   // App appVariable;

    String latLang = "";

    private static final int SHOWLOG = 1;
    /**
     * clear logs
     */
    private static final int CLEARLOG = 2;
    private StringBuffer sb = new StringBuffer("");
    Context getActivity;

    private PrinterListener printer_callback = new PrinterListener();

    protected ProcessDialog processdialog = null;

    //SIL Keys default
    private String defProtectKey = "11111111111111111111111111111111";
    private String defMainKey = "E5B6AC8CB1147317EDBB065494F20BED";
    private String defMainKeyKcv = "163AC02F";//163AC02F
    private String defMacKey = "1F690B570A32F8A05AE7C3AE4D5F437D";
    private String defMacKeyKcv = null;//91CD3A3A
    private String defPinKey = "1F690B570A32F8A05AE7C3AE4D5F437D";
    private String defPinKeyKcv = null;
    private String defTDKey = "1F690B570A32F8A05AE7C3AE4D5F437D";
    private String defTDKeyKcv = null;


    //Maximus keys
     /*private String defProtectKey = "11111111111111111111111111111111";
     private String defMainKey = "58F79846627581D602BBC8E2DCE333C3";
     private String defMainKeyKcv = "5E22CB27";//163AC02F
     private String defMacKey = "AFEBF49CA262712933F4DAB70C093B9C";
     private String defMacKeyKcv = null;//91CD3A3A
     private String defPinKey = "AFEBF49CA262712933F4DAB70C093B9C";
     private String defPinKeyKcv = null;
     private String defTDKey = "AFEBF49CA262712933F4DAB70C093B9C";
     private String defTDKeyKcv = null;*/

    private int area = 1;
    private int tmkindex = 1;

    int cnt = 0;
    boolean isReady = false, isLocAvail = false;

    String respCode = "";
    RequestParams req = new RequestParams();

    public PairedDeviceFragment() {
        // Required empty public constructor
    }

/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_paired_device, container, false);
        initialise(rootView);
        return rootView;
    }
*/

    public void initialise(Context mainActivity,String loc_flags,String froms,String amount,String remark,String ops) {

        getActivity = mainActivity;
        context = mainActivity;
        SessionConst.appendLog(getActivity, "**************Card Tx**************");
        SessionConst.appendLog(getActivity, "Date Time : " + AndyUtility.getLogDate());


        from =froms;
        //getAmt = bundle.getString("amount");
        getRemark = remark;
        op = ops;
        cash_back_amt = amount;
        loc_flag = loc_flags;

        getAmt = "" + Double.parseDouble(getAmt);

        Log.d("SHANKY", "FROM : " + from + "OP: " + op);
/*
        if (from.equalsIgnoreCase("DTH") || from.equalsIgnoreCase("Prepaid") || from.equalsIgnoreCase("Postpaid") || from.equalsIgnoreCase("Gas") || from.equalsIgnoreCase("Landline")
                || from.equalsIgnoreCase("Broadband") || from.equalsIgnoreCase("Datacard") || from.equalsIgnoreCase("Insurance") || from.equalsIgnoreCase("Electricity")) {
            modeofPay = bundle.getString("modeOfPayment");
            recharge_type = bundle.getString("rechargetype");
            mobile_no = bundle.getString("mobileno");
            operator_name = bundle.getString("operator_name");
            operator_code = bundle.getString("operator_code");
        }*/

        //Reset Signature/receipts
        StaticValues.custSign = null;
        MyConst.setCustReceipt(null);
        MyConst.setRetailReceipt(null);
        MyConst.setDuplReceipt(null);

        if (StaticValues.getIsY2000()) {
            GlobalData.getInstance().init(context);
            try {
                DeviceInfoBinder deviceInfoBinder = ServiceManager.getInstence().getDeviceinfo();
                //P2000L, P1000
                Log.d("SHANKY", "DEV Type : " + deviceInfoBinder.getDeviceType());
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    //    appVariable = (App) context.getApplicationContext();
        //mIntentFilter = new IntentFilter();

   //     Bundle bundle = getActivity.getArguments();
   //     if (bundle != null) {
      /*      from = bundle.getString("from");
            //getAmt = bundle.getString("amount");
            getRemark = bundle.getString("remark");
            op = bundle.getString("op");
            cash_back_amt = bundle.getString("cash_back_amt");
            loc_flag = bundle.getString("loc_flag");

            getAmt = "" + Double.parseDouble(getAmt);

            Log.d("SHANKY", "FROM : " + from + "OP: " + op);

            if (from.equalsIgnoreCase("DTH") || from.equalsIgnoreCase("Prepaid") || from.equalsIgnoreCase("Postpaid") || from.equalsIgnoreCase("Gas") || from.equalsIgnoreCase("Landline")
                 || from.equalsIgnoreCase("Broadband") || from.equalsIgnoreCase("Datacard") || from.equalsIgnoreCase("Insurance") || from.equalsIgnoreCase("Electricity")) {
                modeofPay = bundle.getString("modeOfPayment");
                recharge_type = bundle.getString("rechargetype");
                mobile_no = bundle.getString("mobileno");
                operator_name = bundle.getString("operator_name");
                operator_code = bundle.getString("operator_code");
            }
      *///  }

        SessionConst.appendLog(getActivity, "Tx Type : " + op);
/*
        currencyname = Session.getString(getActivity.getApplicationContext(), SessionConstants.Session_currency_name);
        currencycode = Session.getString(getActivity.getApplicationContext(), SessionConstants.Session_currency_code);

        btn_connect_old = (Button) rootView.findViewById(R.id.btn_connect_old);
        btn_connect_new = (Button) rootView.findViewById(R.id.btn_connect_new);

        txt_remark = (TextView) rootView.findViewById(R.id.txt_remark);
        merchant_ref = (TextView) rootView.findViewById(R.id.merchant_ref);
        pay_type = (TextView) rootView.findViewById(R.id.pay_type);
        txtAmt = (TextView) rootView.findViewById(R.id.txtAmt);
        merchant_name = (TextView) rootView.findViewById(R.id.merchant_name);
*/
        if (op.equalsIgnoreCase(Constants.MICRO_MINI_STMT) || op.equalsIgnoreCase(Constants.MICRO_BAL_ENQ)) {
            txtAmt.setVisibility(View.GONE);
        }

        txtAmt.setText(Constants.CURRENCY_NAME + "  " + getAmt);
        txt_remark.setText(getRemark);

        pay_type.setText(from + "");

        merchant_name.setText(Session.getString(getActivity, SessionConstants.Session_merchantname));
        merchant_ref.setText(Session.getString(getActivity, SessionConstants.Session_mid));

        try {
            //msgBundle = new PropertyResourceBundle(getActivity().getResources().openRawResource(R.raw.ucube_strings));
        } catch (Exception e) {
            e.printStackTrace();
        }

        btn_connect_old.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StaticValues.getIsY2000()) {
                    btn_connect_old.setClickable(false);
                    if (!GlobalData.getInstance().getLogin()) {
                        btn_connect_old.setClickable(true);
                        showProcessDialog("Init Device", "Device initializing, please wait...");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                login();
                            }
                        }).start();

                        //Toast.makeText(getActivity(), "Start Transaction Again", Toast.LENGTH_LONG).show();
                    } else {
                        if (Session.getString(getActivity, SessionConstants.isMainKeyInjected).equalsIgnoreCase("T")) {
                            startPayment();
                        } else {
                            getMainKeys();
                            //Toast.makeText(getActivity(), "Main Key Not Injected, Please inject it from 'Settings>>Pinpad Settings>>Load Main Keys', and try again.", Toast.LENGTH_LONG).show();
                            /*FailCustomDialog failCustomDialog = new FailCustomDialog(getActivity(), "Fail", "Main Key Not Injected, Please inject it from 'Settings>>Pinpad Settings>>Load Main Keys', and try again.", "");
                            failCustomDialog.show();*/
                        }
                    }
                } else {
                    Toast.makeText(getActivity, "Device Is Not Y2000", Toast.LENGTH_LONG).show();
                }



               /* if (!Session.getString(getActivity(), SessionConstants.Check_location).equalsIgnoreCase("N")) {
                    //check location within 1km
                    if (calculateDistance()) {
                        startPayment();
                    } else {
                        FailCustomDialog d = new FailCustomDialog(getActivity(), "Location", "Your location is not found within 1 Kilometer", "payment_error");
                        d.show();
                    }
                } else {
                    startPayment();
                }*/
            }
        });

        btn_connect_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDeviceSelectDialog();
                new AsyncTaskRunner().execute();
                // loadProtectKey();
            }
        });
        //setClicks();
    }


    public void startPayment() {
        Log.d("SHANKY", "StartPayment");
        SessionConst.appendLog(getActivity, "Connecting to device" + "\n" + Session.getString(getActivity, "sr_no"));
        double amount = -1;

        try {
            amount = Double.parseDouble("" + (Float.parseFloat(getAmt) + Float.parseFloat(tipamt) + 0));              //amount
        } catch (Exception e) {
            amount = -1;
        }

        String aa = "";
        DecimalFormat form = new DecimalFormat("0.00");
        aa = form.format(Float.parseFloat(amount + ""));

        if (op.equals(Constants.wspurchaseWithCashback)) {
            //paymentContext.setCashBackAmount(cash_back_amt);
        } else {
            //paymentContext.setCashBackAmount("0");
        }


        if (op.equalsIgnoreCase(Constants.wscardpay)) {
            //paymentContext.setTransactionType(Constants.DEBIT);
        } else if (op.equalsIgnoreCase(Constants.wscashonpos)) {
            //paymentContext.setTransactionType(Constants.WITHDRAWAL);
        } else if (op.equalsIgnoreCase(Constants.wspurchaseWithCashback)) {
            // paymentContext.setTransactionType(Constants.PURCHASE_CASHBACK);
        } else {
            // paymentContext.setTransactionType(Constants.DEBIT);
        }


        req.setRequestcode(op);
        req.setImei("" + Session.getString(getActivity, SessionConstants.Session_imei));
        req.setImsi("" + Session.getString(getActivity, SessionConstants.Session_imsi));
        req.setUsername("" + Session.getString(getActivity, SessionConstants.Session_username));
        req.setCompanyid("" + Session.getString(getActivity, SessionConstants.Session_companyid));
        req.setMid("" + Session.getString(getActivity, SessionConstants.Session_mid));
        req.setSrno("" + Session.getString(getActivity, SessionConstants.Session_tid));
        req.setAmt("" + getAmt);
        req.setTipamt("" + tipamt);
        req.setPassword("1234");
        req.setRemark(getRemark);
        if (isLocAvail) {
            req.setPosLocation(latLang);
        }
        if (from.equalsIgnoreCase("Purchase With Cashback")) {
            req.setCashBackAmt("" + cash_back_amt);
        } else {
            req.setCashBackAmt("0.0");
        }

        if (from.equalsIgnoreCase("DTH") || from.equalsIgnoreCase("Prepaid") || from.equalsIgnoreCase("Postpaid") || from.equalsIgnoreCase("Gas") || from.equalsIgnoreCase("Landline")
                || from.equalsIgnoreCase("Broadband") || from.equalsIgnoreCase("Datacard") || from.equalsIgnoreCase("Insurance") || from.equalsIgnoreCase("Electricity")) {
            req.setModeOfPayment(modeofPay);
            req.setRechargetype(from);
            req.setMobileno(mobile_no);
            req.setOperator(operator_code);
            req.setRequestcode("recharge");
        }

        Log.d("Check>>>>>", "HELLO");
        Log.d("SHANKAR", "ACTION#" + MyConst.getActioncode());

        onlineTrans(req, "ALL");

    }

    public void callRevRequest() {
        Log.d("SHANKY", "InReversal");
        String ops = "";
        if (MyConst.getArpc().length() >= 15) {
            if (MyConst.getArpc().substring(5, 7).equalsIgnoreCase("0A") && MyConst.getTag5513().substring(2, 6).equalsIgnoreCase("0008")) {
                ops = "17";
            } else if (MyConst.getTag5513().substring(2, 6).equalsIgnoreCase("0008") && !MyConst.getArpc().substring(5, 7).equalsIgnoreCase("0A")) {
                ops = "E1";
            } else if (MyConst.getArpc().substring(5, 7).equalsIgnoreCase("0A") && (!MyConst.getTag5513().substring(2, 6).equalsIgnoreCase("0008") || !MyConst.getTag5513().substring(2, 6).equalsIgnoreCase("0007"))) {
                ops = "E2";
            } else {
                ops = "E1";
            }
        } else {
            ops = "E1";
        }


        Log.d("OPS", "" + ops);
        SessionConst.appendLog(getActivity, "REVERSAL CALLED");
        RequestParams r = new RequestParams();
        r.setRequestcode( Constants.wsReversal);
        r.setMerchantId(Session.getString(getActivity, SessionConstants.Session_merchantname));
        r.setAmt(Float.parseFloat(getAmt) + Float.parseFloat(tipamt) + "");
        r.setInvoiceNo(MyConst.getInvoice_no());
        r.setRrn("" + MyConst.getRrn());
        r.setCarddata(MyConst.getTag5025().substring(12, 60));
        r.setTag55(MyConst.getTag55() + MyConst.getTagDF03());
        r.setOp("" + ops);
        r.setKsn("KSNHere");

        // Log.d("request to server", new Gson().toJson(r));

        NetworkController.getInstance().sendRequest(r, new ResponseListener() {
            @Override
            public void onResponseSuccess(Response<ResponseParams> response, SuccessCustomDialog d) {
                if (response.body() != null) {
                    Log.d("Status Msg", "" + response.body().getMsg());
                    SessionConst.appendLog(getActivity, "REVERSAL_STATUS:" + response.body().getStatus());
                    if (response.body().getStatus() == true) {
                        d.cancel();
                        SuccessCustomDialog success_d = new SuccessCustomDialog(getActivity, "Void", "", response.body().getMsg());
                        success_d.show();
                        //gotoNewFrag("Y");
                    } else {
                        d.cancel();
                        FailCustomDialog success_d = new FailCustomDialog(getActivity, "Fail", response.body().getMsg(), "payment_error");
                        success_d.show();
                        // gotoNewFrag("N");
                    }
                } else {
                    d.cancel();
                    FailCustomDialog success_d = new FailCustomDialog(getActivity, "Fail", getActivity.getResources().getString(R.string.server_error), "payment_error");
                    success_d.show();
                }
            }

            @Override
            public void onResponseFailure(Throwable throwable, SuccessCustomDialog d) {
                d.cancel();
                FailCustomDialog success_d = new FailCustomDialog(getActivity, "Fail", getActivity.getResources().getString(R.string.server_error), "payment_error");
                success_d.show();
            }
        }, new SuccessCustomDialog(getActivity, "Reversal", "Loading please Wait...", ""));
    }


    public void gotoNewFrag(String status) {
        Log.d("ST>>", "" + status);

        /*try {
            if (serverResponse != null || serverResponse.getWalletBalance() != null || !serverResponse.getWalletBalance().equals("")) {
                Session.setString(getActivity, SessionConstants.Wallet_Balance, "" + serverResponse.getWalletBalance());
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(MainActivity.mBroadcastWallet);
                broadcastIntent.putExtra("msg", "Enter PIN");
                getActivity.sendBroadcast(broadcastIntent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        if (!from.equalsIgnoreCase("card")) {
            if (status.equals("N")) {
                //Get bluetooth adapter
                SessionConst.appendLog(getActivity, "Fail" + MyConst.getMsg());

            /* BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            boolean bluetoothEnabled = mBluetoothAdapter.isEnabled();
            if (bluetoothEnabled) {
                mBluetoothAdapter.disable();
            } */

                MyConst.fallback_tag5025 = "";
                MyConst.isCardRemoved = false;
                MyConst.setResponseStatus((short) 0);
                MyConst.isFallBack = false;
                MyConst.setTxnattempt(0);

                //open fail dailog
                FailCustomDialog f_dialog = new FailCustomDialog(context, "Fail", MyConst.getMsg(), "payment_error");
                f_dialog.show();

            } else if (status.equals("R")) {
                //Get bluetooth adapter
                SessionConst.appendLog(getActivity, "REVERSAL REQUEST");

                MyConst.fallback_tag5025 = "";
                MyConst.isCardRemoved = false;
                MyConst.setResponseStatus((short) 0);
                MyConst.isFallBack = false;
                MyConst.setTxnattempt(0);
            } else {
               /* if (paymentContext.getActivatedReader() == 17) {
                    Update5513.ctx = getActivity();
                    Intent intent = new Intent(getActivity(), Update5513.class);
                    intent.putExtra("rrn", "" + MyConst.getRrn());
                    intent.putExtra("5513", "" + MyConst.getTag5513());
                    getActivity().startService(intent);

                    //Check 9F34 tag for signature
                    TreeMap<String, String> tlvMap9F34 = MyBERTLV.parseTLV("" + MyConst.tag_5513_getCVM);
                    Log.d("9F34 CMV value", "" + tlvMap9F34.toString());
                    cvm_tag = tlvMap9F34.get("9F34") == null ? "" : tlvMap9F34.get("9F34");
                    Log.d("CVM Tag value", cvm_tag);
                } else {
                    //check for magstrip txn
                    cvm_tag = "";
                }*/


                if (!cvm_tag.equals("")) {
                    if (cvm_tag.substring(2).equals("3F") && (status.equals("Y"))) {
                        MyConst.isWithoutPin = true;
                        dialog_action();
                    } else {
                        SessionConst.appendLog(getActivity, "Success " + " Generating RECEIPT");
                        MyConst.setResponseStatus((short) 0);
                        MyConst.isCardRemoved = false;
                        MyConst.isFallBack = false;
                        MyConst.setTxnattempt(0);
                        MyConst.isServiceCalled = false;
                        //start new fragment
                        TransactionStatusFragment successFrag = new TransactionStatusFragment();
                        Bundle args = new Bundle();
                        if (status.equals("S")) {
                            args.putString("flag", "success");
                        } else {
                            args.putString("flag", "fail");
                        }
                        args.putString("op", "" + op);
                        args.putString("amt", "" + getAmt);
                        args.putString("from", "" + from);
                        args.putString("reader", "" + MyConst.getReader());  // 17 for EMV, 65 for MS
                        args.putString("type", "" + "CARD");
                        args.putString("rrn", MyConst.getRrn());
                        args.putString("remark", getRemark);
                        args.putString("date", MyConst.getDate());
                        args.putString("auth_id", MyConst.getAuth_id());
                        args.putString("msg_desr", MyConst.getMsg());
                        args.putString("card_no", MyConst.getCard_no());
                        args.putString("invoice_no", MyConst.getInvoice_no());
                        args.putString("card_type", MyConst.getCard_type());
                        args.putString("batch_no", MyConst.getBatch_no());
                        args.putString("loc_flag", loc_flag);
                        args.putString("tvr", tvr);
                        args.putString("tsi", tsi);
                        args.putString("aid", aid);
                        args.putString("respcode", respCode);
                        args.putString("balance", "" + serverResponse.getEd());

                        if (from.equalsIgnoreCase("DTH") || from.equalsIgnoreCase("Prepaid") || from.equalsIgnoreCase("Postpaid") || from.equalsIgnoreCase("Gas") || from.equalsIgnoreCase("Landline")
                                || from.equalsIgnoreCase("Broadband") || from.equalsIgnoreCase("Datacard") || from.equalsIgnoreCase("Insurance") || from.equalsIgnoreCase("Electricity")) {

                            args.putString("op", "" + from);
                            args.putString("modeOfPayment", modeofPay);
                            args.putString("rechargetype", recharge_type);
                            args.putString("mobileno", mobile_no);
                            args.putString("operator_name", operator_name);
                        }

                        successFrag.setArguments(args);
                        MyConst.paymentBaseList.add(successFrag);
                        AppCompatActivity activity = (AppCompatActivity) context;

                      //  android.app.FragmentManager fragmentManager = activity.getFragmentManager();

                        FragmentManager fragmentManagers = activity.getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManagers.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, successFrag);
                        fragmentTransaction.commit();


                       // getActivity.beginTransaction().replace(R.id.fragment_container, successFrag).commit();
                    }
                } else {
                    SessionConst.appendLog(getActivity, "Success " + " Generating RECEIPT");
                    MyConst.setResponseStatus((short) 0);
                    MyConst.isCardRemoved = false;
                    MyConst.isFallBack = false;
                    MyConst.setTxnattempt(0);
                    MyConst.isServiceCalled = false;
                    //start new fragment
                    TransactionStatusFragment successFrag = new TransactionStatusFragment();
                    Bundle args = new Bundle();
                    if (status.equals("S")) {
                        args.putString("flag", "success");
                    } else {
                        args.putString("flag", "fail");
                    }

                    args.putString("op", "" + op);
                    args.putString("from", "" + from);
                    args.putString("amt", "" + getAmt);
                    args.putString("reader", "" + MyConst.getReader());  // 17 for EMV, 65 for MS
                    args.putString("type", "" + "CARD");
                    args.putString("rrn", MyConst.getRrn());
                    args.putString("remark", getRemark);
                    args.putString("date", MyConst.getDate());
                    args.putString("auth_id", MyConst.getAuth_id());
                    args.putString("msg_desr", MyConst.getMsg());
                    args.putString("card_no", MyConst.getCard_no());
                    args.putString("invoice_no", MyConst.getInvoice_no());
                    args.putString("card_type", MyConst.getCard_type());
                    args.putString("batch_no", MyConst.getBatch_no());
                    args.putString("loc_flag", loc_flag);
                    args.putString("tvr", tvr);
                    args.putString("tsi", tsi);
                    args.putString("aid", aid);
                    args.putString("respcode", respCode);
                    args.putString("balance", "" + serverResponse.getEd());

                    if (from.equalsIgnoreCase("DTH") || from.equalsIgnoreCase("Prepaid") || from.equalsIgnoreCase("Postpaid") || from.equalsIgnoreCase("Gas") || from.equalsIgnoreCase("Landline")
                            || from.equalsIgnoreCase("Broadband") || from.equalsIgnoreCase("Datacard") || from.equalsIgnoreCase("Insurance") || from.equalsIgnoreCase("Electricity")) {
                        args.putString("op", "" + from);
                        args.putString("modeOfPayment", modeofPay);
                        args.putString("rechargetype", recharge_type);
                        args.putString("mobileno", mobile_no);
                        args.putString("operator_name", operator_name);
                    }
                    successFrag.setArguments(args);
                    MyConst.paymentBaseList.add(successFrag);

                    AppCompatActivity activity = (AppCompatActivity) context;

                    FragmentManager fragmentManagers = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManagers.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, successFrag);
                    fragmentTransaction.commit();


                    //    FragmentManager.beginTransaction().replace(R.id.fragment_container, successFrag).commit();
                }
            }
        } else {
            FailCustomDialog success_d = new FailCustomDialog(getActivity, "Success", "" + MyConst.getMsg(), "payment_error");
            success_d.show();
        }
    }

    // Function for Digital Signature
    public void dialog_action() {
        // Dialog Function
        final Dialog dialog = new Dialog(getActivity);
        // Removing the features of Normal Dialogs
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.digitalsign);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mSignature = new SignatureView(getActivity);
      /*  LinearLayout linearLayout1 = (LinearLayout) dialog.findViewById(R.id.linearLayout1);
        linearLayout1.getBackground().setAlpha(200);*/
        LinearLayout mContent = (LinearLayout) dialog.findViewById(R.id.sign_layout);

        // Dynamically generating Layout through java code
        mContent.addView(mSignature, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button mClear = (Button) dialog.findViewById(R.id.clear);
        final Button mGetSign = (Button) dialog.findViewById(R.id.getsign);
        TextView txttitle = (TextView) dialog.findViewById(R.id.txttitle);

        mGetSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticValues.setCustSign(mSignature.getSignature());
                dialog.dismiss();
                SessionConst.appendLog(getActivity, "GET SIGNATURE " + " Generating RECEIPT");
                MyConst.setResponseStatus((short) 0);
                MyConst.isCardRemoved = false;
                MyConst.isFallBack = false;
                MyConst.setTxnattempt(0);
                MyConst.isServiceCalled = false;
                //start new fragment
                TransactionStatusFragment successFrag = new TransactionStatusFragment();
                Bundle args = new Bundle();
                if (status.equals("Y")) {
                    args.putString("flag", "success");
                } else {
                    args.putString("flag", "fail");
                }
                args.putString("op", "" + op);
                args.putString("from", "" + from);
                args.putString("amt", "" + getAmt);
                args.putString("reader", "" + MyConst.getReader());  // 17 for EMV, 65 for MS
                args.putString("type", "" + "CARD");
                args.putString("rrn", MyConst.getRrn());
                args.putString("remark", MyConst.getRemark());
                args.putString("date", MyConst.getDate());
                args.putString("auth_id", MyConst.getAuth_id());
                args.putString("msg_desr", MyConst.getMsg());
                args.putString("card_no", MyConst.getCard_no());
                args.putString("invoice_no", MyConst.getInvoice_no());
                args.putString("card_type", MyConst.getCard_type());
                args.putString("batch_no", MyConst.getBatch_no());
                successFrag.setArguments(args);

                AppCompatActivity activity = (AppCompatActivity) context;

                FragmentManager fragmentManagers = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManagers.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, successFrag);
                fragmentTransaction.commit();


                //  FragmentManager.beginTransaction().replace(R.id.fragment_container, successFrag).commit();
            }
        });

        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSignature.clearSignature();
            }
        });
        dialog.show();
    }


/*
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

*/


    /*Y2000*/
    private void login() {
        try {
            boolean bRet;
            LoadParamManage.getInstance().DeleteAllTerParamFile();
            for (int j = 0; j < MyCUPParam.aid_data.length; j++) {
                bRet = ServiceManager.getInstence().getPboc().updateAID(0, MyCUPParam.aid_data[j]);
                Log.d("SHANKY", "download " + j + " aid [" + MyCUPParam.aid_data[j] + "]" + " bRet = " + bRet);
            }

            for (int i = 0; i < MyCUPParam.ca_data.length; i++) {
                bRet = ServiceManager.getInstence().getPboc().updateRID(0, MyCUPParam.ca_data[i]);
                Log.d("SHANKY", "download " + i + " rid [" + MyCUPParam.ca_data[i] + "]" + " bRet = [" + bRet + "]");
            }
            GlobalData.getInstance().setLogin(true);
            //Toast.makeText(context, "Login Success", Toast.LENGTH_LONG).show();
            //dialogLogin.dismiss();
            //Start Payment
            /*if (Session.getString(getActivity(), SessionConstants.isMainKeyInjected).equalsIgnoreCase("T")) {
                startPayment();
            } else {
                getMainKeys();
            }*/

            dismissProcessDialog();
        } catch (Exception e) {
            e.printStackTrace();
            dismissProcessDialog();
        }
    }


    public Long formatAmount() {
        return Long.parseLong(StringHelper.changeAmout(getAmt).replace(".", ""));
    }

    //Online Trans(联机交易)
    private void onlineTrans(RequestParams req, String flag) {
        Log.d("SHANKY", "onlineTrans");
       // defMacKey = Session.getString(context, SessionConstants.trackTmk);

        //defPinKey = Session.getString(context, SessionConstants.tpkTmk);
        //defTDKey = Session.getString(context, SessionConstants.trackTmk);

        Log.d("SHANKY", "onlineTrans1");
        if (Session.getString(context, Constants.INJECTKEY).equalsIgnoreCase("T")) {
            //load working keys
            loadPinKey();
            Log.d("SHANKY", "onlineTrans2");
        }

        if (!GlobalData.getInstance().getLogin()) {
            Toast.makeText(context, "Login First", Toast.LENGTH_LONG).show();
        } else {
            try {
                String msg = "Swipe/Insert Card...";
                Double d = Double.parseDouble(getAmt) * 100;
                Integer i = d.intValue();

                Log.d("SHANKY", "Start Tx\n AMT : " + i);
                Intent in = new Intent();
                in.putExtra(InputPBOCInitData.AMOUNT_FLAG, i);
                PBOCBinder binder = ServiceManager.getInstence().getPboc();
                PosEmvParam posEmvParam = new PosEmvParam();
                PosEmvCoreManager mPosEmv = PosEmvCoreManager.getDefault();

                if (flag.equalsIgnoreCase("MS")) {
                    msg = "Swipe Card...";
                    in.putExtra(InputPBOCInitData.USE_DEVICE_FLAG, InputPBOCInitData.USE_MAG_CARD);
                } else if (flag.equalsIgnoreCase("EMV")) {
                    msg = "Insert Card...";
                    in.putExtra(InputPBOCInitData.USE_DEVICE_FLAG, InputPBOCInitData.USE_IC_CARD);
                    in.putExtra(InputPBOCInitData.IS_SUPPERT_EC_FLAG, false);
                    in.putExtra(InputPBOCInitData.IS_QPBOC_FORCE_ONLINE, false);

                    posEmvParam.TransCurrCode = BytesUtil.hexString2Bytes("0356");
                    posEmvParam.CountryCode = BytesUtil.hexString2Bytes("0356");
                    //terminal Capability for offline auth TC1A
                    posEmvParam.ExCapability[3] |= 0x40;
                    //posEmvParam.Capability[1] |= 0x20;
                    posEmvParam.Capability[0] = (byte) 0x60;
                    posEmvParam.Capability[1] = (byte) 0xe8;  // TODO plain OFF (byte 2 bit 8)
                    posEmvParam.Capability[2] = (byte) 0xc8;
                    posEmvParam.TerminalType = 0x22;
                    ///
                    mPosEmv.EmvSetTermPara(posEmvParam);
                    binder.setPosTermPara(posEmvParam);
                } else {
                    msg = "Swipe/Insert Card...";
                    in.putExtra(InputPBOCInitData.USE_DEVICE_FLAG, InputPBOCInitData.USE_MAG_CARD | InputPBOCInitData.USE_IC_CARD);
                    posEmvParam.TransCurrCode = BytesUtil.hexString2Bytes("0356");
                    posEmvParam.CountryCode = BytesUtil.hexString2Bytes("0356");
                    in.putExtra(InputPBOCInitData.IS_SUPPERT_EC_FLAG, false);
                    in.putExtra(InputPBOCInitData.IS_QPBOC_FORCE_ONLINE, false);

                    Log.d("SHANKY", "CAPABILITY : " + BytesUtil.bytes2HexString(posEmvParam.Capability));

                    //terminal Capability for offline auth TC1A
                    posEmvParam.ExCapability[3] |= 0x40;
                    posEmvParam.Capability[0] = (byte) 0x60;
                    posEmvParam.Capability[1] = (byte) 0xe8;  // TODO plain OFF (byte 2 bit 8)
                    posEmvParam.Capability[2] = (byte) 0xc8;
                    posEmvParam.TerminalType = 0x22;
                    Log.d("SHANKY", "CAPABILITY : " + BytesUtil.bytes2HexString(posEmvParam.Capability));
                    mPosEmv.EmvSetTermPara(posEmvParam);
                    binder.setPosTermPara(posEmvParam);
                }

                //PosEmvCoreManager.getDefault().EmvGetTermInfo().bForcedOnline = 0;
                //Force Online 0=Not force, 1=force
                PosTermInfo info = PosEmvCoreManager.getDefault().EmvGetTermInfo();
                info.bForcedOnline = 0;
                mPosEmv.EmvSetTermInfo(info);

                in.putExtra(InputPBOCInitData.TIMEOUT, 60);
                freshProcessDialog("Card Transaction", msg);

                int txType = PBOCOption.ONLINE_PAY;
                if (op.equalsIgnoreCase(Constants.MICRO_BAL_ENQ)) {
                    txType = PBOCOption.ONLINE_INQUIRY;
                    Log.d("SHANKY", "TX_TYPE : ONLINE_INQUIRY");
                } else if (op.equalsIgnoreCase(Constants.MICRO_MINI_STMT)) {
                    Log.d("SHANKY", "TX_TYPE : MICRO_MINI_STMT");
                    txType = PBOCOption.ONLINE_INQUIRY;
                } else if (op.equalsIgnoreCase(Constants.MICRO_CASH_WITHDRAW)) {
                    Log.d("SHANKY", "TX_TYPE : MICRO_CASH_WITHDRAW");
                    txType = PBOCOption.FUN_CASH;
                    //txType = PBOCOption.ONLINE_PAY;
                    //in.putExtra(InputPBOCInitData.IS_SUPPERT_EC_FLAG, true);
                } else {
                    Log.d("SHANKY", "TX_TYPE : ONLINE_PAY");
                    txType = PBOCOption.ONLINE_PAY;
                }

                //TODO added for CVMR result issue
                //ServiceManager.getInstence().getPboc().setCardtype(0x01);

              //  binder.startTransfer(txType, in, new onlinePBOCListener(getActivity, getAmt, req));
                //ServiceManager.getInstence().getPboc().startTransfer(PBOCOption.ONLINE_PAY, in, new onlinePBOCListener(this, getAmt, req));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void onlineFallbackMSTrans(RequestParams req) {
        Log.d("SHANKY", "onlineTrans");
        defMacKey = Session.getString(context, SessionConstants.trackTmk);
        defPinKey = Session.getString(context, SessionConstants.tpkTmk);
        defTDKey = Session.getString(context, SessionConstants.trackTmk);

        Log.d("SHANKY", "onlineTrans1");
        if (Session.getString(context, Constants.INJECTKEY).equalsIgnoreCase("T")) {
            loadPinKey();
            Log.d("SHANKY", "onlineTrans2");
        }

        if (!GlobalData.getInstance().getLogin()) {
            Toast.makeText(context, "Login First", Toast.LENGTH_LONG).show();
        } else {
            try {
                Double d = Double.parseDouble(getAmt) * 100;
                Integer i = d.intValue();

                Log.d("SHANKY", "Start Tx\n AMT : " + i);
                Intent in = new Intent();
                in.putExtra(InputPBOCInitData.AMOUNT_FLAG, i);
                in.putExtra(InputPBOCInitData.USE_DEVICE_FLAG, InputPBOCInitData.USE_MAG_CARD);
                in.putExtra(InputPBOCInitData.TIMEOUT, 60);

                PBOCBinder binder = ServiceManager.getInstence().getPboc();

                PosEmvCoreManager.getDefault().EmvGetTermInfo().bForcedOnline = 0;

                freshProcessDialog("Card Transaction", "Please Swipe Card...");

                int txType = PBOCOption.ONLINE_PAY;
                if (op.equalsIgnoreCase(Constants.MICRO_BAL_ENQ)) {
                    txType = PBOCOption.ONLINE_INQUIRY;
                    Log.d("SHANKY", "TX_TYPE : ONLINE_INQUIRY");
                } else if (op.equalsIgnoreCase(Constants.MICRO_MINI_STMT)) {
                    Log.d("SHANKY", "TX_TYPE : ONLINE_INQUIRY");
                    txType = PBOCOption.ONLINE_INQUIRY;
                } else if (op.equalsIgnoreCase(Constants.MICRO_CASH_WITHDRAW)) {
                    Log.d("SHANKY", "TX_TYPE : ONLINE_PAY");
                    txType = PBOCOption.FUN_CASH;
                    //txType = PBOCOption.ONLINE_PAY;
                    in.putExtra(InputPBOCInitData.IS_SUPPERT_EC_FLAG, true);
                } else {
                    Log.d("SHANKY", "TX_TYPE : ONLINE_PAY");
                    txType = PBOCOption.ONLINE_PAY;
                }

              //  binder.startTransfer(txType, in, new onlinePBOCListener(getActivity, getAmt, req));

                //ServiceManager.getInstence().getPboc().startTransfer(PBOCOption.ONLINE_PAY, in, new onlinePBOCListener(this, getAmt, req));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case SHOWLOG:
                    sb.append(msg.obj + "\n");
                    break;
                case CLEARLOG:
                    sb = new StringBuffer("");
                    break;
            }
        }

        ;
    };

    @SuppressLint("HandlerLeak")
    Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case SHOWLOG:
                    Log.d("SHANKY", "COMPLETE");
                    btn_connect_old.setClickable(true);
                    try {
                        serverResponse = (ResponseParams) msg.obj;
                        if (serverResponse != null) {

                            Log.d("SHNKYWSCRD", new Gson().toJson(serverResponse));

                            respCode = serverResponse.getRespCode();

                            MyConst.setMsg("" + serverResponse.getMsg());
                            if (serverResponse.isStatus()) {
                                //Tx complete

                                MyConst.setRrn("" + serverResponse.getRrn());
                                MyConst.setDate("" + serverResponse.getDate());
                                MyConst.setAuth_id("" + serverResponse.getAuthid());
                                MyConst.setMsg("" + serverResponse.getMsg());
                                MyConst.setCard_no("" + serverResponse.getCardno());
                                MyConst.setInvoice_no("" + serverResponse.getInvoiceNo());
                                MyConst.setCard_type("" + serverResponse.getCardType());
                                MyConst.setBatch_no("" + serverResponse.getBatchNo());

                                if (MyConst.getReader().equalsIgnoreCase("EMV")) {
                                    MyConst.setAppl_name("" + serverResponse.getApplName());
                                    tvr = serverResponse.getTvr();
                                    tsi = serverResponse.getTsi();
                                    aid = serverResponse.getAid();
                                }

                                gotoNewFrag("S");
                            } else {
                                if (serverResponse.getRespCode().equalsIgnoreCase("" + DeviceErrorCodes.FALLBACK)) {
                                    //Fallback Case : Chip is faulty , use MS
                                    ((Activity)context).runOnUiThread(new Runnable()
                                    {
                                        public void run()
                                        {
                                            new EnterDialog(getActivity).showConfirmDialog("Faulty Chip", "Please remove card and use Mag Stripe.\n Do you want to continue with Mag Stripe ?", "Yes", "No", new OnConfirmListener() {
                                                @Override
                                                public void OK() {
                                                    onlineFallbackMSTrans(req);
                                                }

                                                @Override
                                                public void Cancel() {
                                                    Toast.makeText(getActivity, "Txn Canceled By User", Toast.LENGTH_LONG).show();
                                                }
                                            });
                                        }
                                    });


                                } else if (serverResponse.getRespCode().equalsIgnoreCase(ResponseCodeConst.USE_CHIP)) {

                                    ((Activity)context).runOnUiThread(new Runnable()
                                    {
                                        public void run()
                                        {
                                            onlineTrans(req, "EMV");
                                        }
                                    });


                                } else {

                                    MyConst.setRrn("" + serverResponse.getRrn());
                                    MyConst.setDate("" + serverResponse.getDate());
                                    MyConst.setAuth_id("" + serverResponse.getAuthid());
                                    MyConst.setMsg("" + serverResponse.getMsg());
                                    MyConst.setCard_no("" + serverResponse.getCardno());
                                    MyConst.setInvoice_no("" + serverResponse.getInvoiceNo());
                                    MyConst.setCard_type("" + serverResponse.getCardType());
                                    MyConst.setBatch_no("" + serverResponse.getBatchNo());
                                    if (MyConst.getReader().equalsIgnoreCase("EMV")) {
                                        MyConst.setAppl_name("" + serverResponse.getApplName());
                                        tvr = serverResponse.getTvr();
                                        tsi = serverResponse.getTsi();
                                        aid = serverResponse.getAid();
                                    }
                                    gotoNewFrag("F");
                                }
                            }
                        } else {
                            Toast.makeText(getActivity, "Resp : NULL", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    sb.append(msg.obj + "\n");
                    break;
                case CLEARLOG:
                    sb = new StringBuffer("");
                    break;
            }
        }

        ;
    };


    public void updateData(ResponseParams resp) {
        //btn_connect_old.setVisibility(View.VISIBLE);
        Message message = new Message();
        message.what = SHOWLOG;
        message.obj = resp;
        handler1.sendMessage(message);
    }


    public void freshProcessDialog(final String title, final String msg) {
        showProcessDialog(title, msg);

        /*   runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showProcessDialog(title, msg);
            }
        });*/
    }

    public void dismissDialog() {
        dismissProcessDialog();

     /*   runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dismissProcessDialog();
            }
        });*/
    }


    public void showProcessDialog(String title, String msg) {
        if (processdialog == null)
            processdialog = new ProcessDialog(context, title, msg);
        else {
            processdialog.freshTitle(title, msg);
        }
    }

    /**
     *
     */
    public void dismissProcessDialog() {
        if (processdialog != null && processdialog.isShowing()) {
            processdialog.stopTimer();
            processdialog.dismiss();
            processdialog = null;
        }
    }

    class PrinterListener implements OnPrinterListener {
        private final String TAG = "Print";

        @Override
        public void onStart() {
            // Print start
            Log.d("Print", "start print");
        }

        @Override
        public void onFinish() {
            // End of the print
            Log.d("Print", "pint success");
        }

        @Override
        public void onError(int errorCode, String detail) {
            // print error
            Log.d("Print", "print error" + " errorcode = " + errorCode + " detail = " + detail);
            if (errorCode == PrinterBinder.PRINTER_ERROR_NO_PAPER) {
                Toast.makeText(context, "paper runs out during printing", Toast.LENGTH_SHORT).show();
            }
            if (errorCode == PrinterBinder.PRINTER_ERROR_OVER_HEAT) {
                Toast.makeText(context, "over heat during printing", Toast.LENGTH_SHORT).show();
            }
            if (errorCode == PrinterBinder.PRINTER_ERROR_OTHER) {
                Toast.makeText(context, "other error happen during printing", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class AsyncTaskRunner extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            // loadProtectKey();
            loadPinKey();
            return null;
        }

    }

    public void getMainKeys() {
        RequestParams r = new RequestParams();
        r.setRequestcode(Constants.wsgetMainKey);
        r.setUsername(Session.getString(getActivity, SessionConstants.Session_username));
        r.setImei(Session.getString(getActivity, SessionConstants.Session_imei));
        r.setImsi(Session.getString(getActivity, SessionConstants.Session_imsi));
        NetworkController.getInstance().sendRequest(r, new ResponseListener() {
            @Override
            public void onResponseSuccess(Response<ResponseParams> response, SuccessCustomDialog d) {
                d.cancel();
                if (response.body() != null) {
                    if (response.body().getMkskKeys() != null) {
                        if (response.body().isStatus()) {
                            if (response.body().getMkskKeys().contains("#")) {
                                String[] keys = response.body().getMkskKeys().split("#");
                                if (keys.length < 3) {
                                    Toast.makeText(getActivity, "Key Size is small : " + keys.length + " Keys : " + keys.toString(), Toast.LENGTH_LONG).show();
                                } else {
                                    defProtectKey = keys[0];
                                    defMainKey = keys[1];
                                    defMainKeyKcv = keys[2];
                                    loadProtectKey();
                                    Log.d("SHANKY", "KEYS : Prot : " + defProtectKey + " Main : " + defMainKey + " KCV : " + defMainKeyKcv);
                                }
                            }
                        } else {
                            FailCustomDialog failCustomDialog = new FailCustomDialog(getActivity, "Fail", "" + response.body().getMsg(), "");
                            failCustomDialog.show();
                        }
                    } else {
                        FailCustomDialog failCustomDialog = new FailCustomDialog(getActivity, "Fail", "Key Is NULL", "");
                        failCustomDialog.show();
                    }
                } else {
                    FailCustomDialog failCustomDialog = new FailCustomDialog(getActivity, "Fail", "Check Internet Connection:Null Responce", "");
                    failCustomDialog.show();
                }
            }

            @Override
            public void onResponseFailure(Throwable throwable, SuccessCustomDialog d) {
                Log.e("", throwable.toString());
                d.cancel();
                FailCustomDialog failCustomDialog = new FailCustomDialog(getActivity, "Fail", "Check Internet Connection", "");
                failCustomDialog.show();
            }

        }, new SuccessCustomDialog(getActivity, "Loading Key", "Please Wait", ""));
    }

    public void loadProtectKey() {

        //TODO clear all keys first
        try {
            if (StaticValues.getDeviceType().contains("P2000")) {
                //ServiceManager.getInstence().getPinpad().format();
                Log.d("SHANKY", "FORMAT SUCCESS");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String protect = defProtectKey;
        if (TextUtils.isEmpty(protect)) {
            Log.d("SHANKY", "protect key  is  null  ！");
            return;
        }
        try {
            boolean iRet = ServiceManager.getInstence().getPinpad().loadProtectKeyByArea(area, protect);
            if (iRet) {
                //Toast.makeText(getActivity(),"Load Procted Key Success", Toast.LENGTH_LONG).show();
                GlobalData.getInstance().setPinpadVersion(PinpadInterfaceVersion.PINPAD_INTERFACE_VERSION3);
                GlobalData.getInstance().setArea(area);
                loadMainKey();
            } else {
                Toast.makeText(getActivity, "Load Procted Key Fail", Toast.LENGTH_LONG).show();
                Log.d("SHETTY ", "loadProtectKey: 1");
            }
        } catch (Exception e) {
            Toast.makeText(getActivity, "Load Procted Key Exception", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void loadMainKey() {
        try {
            //Demo main key
            String main = defMainKey;
            String mainKcv = defMainKeyKcv;
            if (TextUtils.isEmpty(main)) {
                Log.d("SHANKY", "main key  is  null  ！");
                return;
            }
            boolean iRet = false;
            if (TextUtils.isEmpty(mainKcv)) {
                iRet = ServiceManager.getInstence().getPinpad().loadMainKeyByArea(area, tmkindex, main);
            } else {
                iRet = ServiceManager.getInstence().getPinpad().loadMainKeyWithKcvByArea(area, tmkindex, main, mainKcv);
            }
            if (iRet) {
                //Toast.makeText(getActivity(), "load Main Key Success!");
                GlobalData.getInstance().setTmkId(tmkindex);
                Toast.makeText(getActivity, "Load Main Key Success", Toast.LENGTH_LONG).show();
                Session.setString(getActivity, SessionConstants.isMainKeyInjected, "T");

                startPayment();

                //loadPinKey();
            } else {
                Toast.makeText(getActivity, "Load Main Key Fail", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity, "Load Main Key Exception", Toast.LENGTH_LONG).show();
        }
    }

    ///Load Keys
    public void loadMacKey() {
        try {
            //Demo main key
            String mac = defMacKey;
            String macKcv = defMacKeyKcv;
            if (TextUtils.isEmpty(mac)) {
                Log.d("SHANKY", "mac key  is  null  ！");
                return;
            }
            boolean iRet = false;

            //Set The algorithm
            GlobalData.getInstance().setPinpadVersion(PinpadInterfaceVersion.PINPAD_INTERFACE_VERSION3);
            GlobalData.getInstance().setArea(area);

            if (TextUtils.isEmpty(macKcv)) {
                iRet = ServiceManager.getInstence().getPinpad().loadMacKeyByArea(area, tmkindex, mac, null);
            } else {
                iRet = ServiceManager.getInstence().getPinpad().loadMacKeyByArea(area, tmkindex, mac, macKcv);
            }

            if (iRet) {
                Toast.makeText(context, "Load Key Success", Toast.LENGTH_LONG).show();
                // showToast("load mac Key Success!");
                //loadTDKey();
            } else {
                Toast.makeText(context, "Load MAC Key Fail", Toast.LENGTH_LONG).show();
                //showToast("error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Load MAC Key Exception", Toast.LENGTH_LONG).show();
        }
    }

    public void loadPinKey() {
        try {
            //Demo main key
            String pin = defPinKey;
            String pin_kcv = defPinKeyKcv;
            if (TextUtils.isEmpty(pin)) {
                Log.d("SHANKY", "pin key  is  null  ！");
                return;
            }
            boolean iRet = false;
            if (TextUtils.isEmpty(pin_kcv)) {
                iRet = ServiceManager.getInstence().getPinpad().loadPinKeyByArea(area, tmkindex, pin, null);
            } else {
                iRet = ServiceManager.getInstence().getPinpad().loadPinKeyByArea(area, tmkindex, pin, pin_kcv);
            }

            if (iRet) {
                //Toast.makeText(context, "Load PIN Key Success", Toast.LENGTH_LONG).show();
                // showToast("load pin Key Success!");
                GlobalData.getInstance().setPinkeyFlag(true);
                loadTDKey();
            } else {
                Toast.makeText(context, "Load PIN Key Fail", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("SHANKY", "loadPinKey error !");
            Toast.makeText(context, "Load PIN Key Exception", Toast.LENGTH_LONG).show();
        }
    }

    public void loadTDKey() {
        try {
            //Demo main key
            String tdkey = defTDKey;
            String td_kcv = defTDKeyKcv;
            if (TextUtils.isEmpty(tdkey)) {
                Log.d("SHANKY", "td key  is  null  ！");
                return;
            }
            boolean iRet = false;
            if (TextUtils.isEmpty(td_kcv)) {
                iRet = ServiceManager.getInstence().getPinpad().loadTDKeyByArea(area, tmkindex, tdkey, null);
            } else {
                iRet = ServiceManager.getInstence().getPinpad().loadTDKeyByArea(area, tmkindex, tdkey, td_kcv);
            }

            if (iRet) {
                Session.setString(context, Constants.INJECTKEY, "F");
                isReady = true;
                loadMacKey();
                // showToast("load td Key Success!");
            } else {
                // showToast("load td Key error");
                // showToast("td key:" + tdkey);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("SHANKY", "loadTDKey error !");
        }
    }


}
