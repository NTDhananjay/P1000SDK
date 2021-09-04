package com.example.youcloudp1000sdk.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.youcloudp1000sdk.R;
import com.example.youcloudp1000sdk.utils.AndyUtility;
import com.example.youcloudp1000sdk.utils.Constants;
import com.example.youcloudp1000sdk.utils.MyConst;
import com.example.youcloudp1000sdk.utils.Session;
import com.example.youcloudp1000sdk.utils.SessionConstants;
import com.example.youcloudp1000sdk.utils.StaticValues;

public class TransactionStatusFragment extends Fragment {
    private View view;
    private TextView txtTransId, txtpaytype, txtRemark, txtAED, txt_trans_status, txtMercName;
    private Button btnDetails;
    ImageView imgsuccess;
    String tvr = "", tsi = "", aid = "", flag = "", str_reader = "", op = "", strCardno = "", str_msgdescr = "",
            str_authid = "", str_date = "", stramt = "", str_pay_type = "", modeofPay = "", recharge_type = "",
            app_name = "", str_rrn = "", str_remark = "", str_invoice_no = "", str_card_type = "", str_batch_no = "",
            str_cust_vpa = "", loc_flag = "", mobile_no = "", operator_name = "", from = "", str_bal = "", respcode = "";
    LinearLayout payment_type;

    public TransactionStatusFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_transaction_status, container, false);
        initialize();
        return view;
    }

    private void initialize() {
        txtTransId = (TextView) view.findViewById(R.id.txtTransId);
        txtpaytype = (TextView) view.findViewById(R.id.txtpaytype);
        txtMercName = (TextView) view.findViewById(R.id.txtMercName);
        txtRemark = (TextView) view.findViewById(R.id.txtRemark);
        btnDetails = (Button) view.findViewById(R.id.btnDetails);
        imgsuccess = (ImageView) view.findViewById(R.id.imgsuccess);
        txtAED = (TextView) view.findViewById(R.id.txtAED);
        txt_trans_status = (TextView) view.findViewById(R.id.txt_trans_status);
        payment_type = (LinearLayout) view.findViewById(R.id.payment_type);

        Bundle b = getArguments();
        if (b != null) {
            Log.d("SHANKY", "TRANS STATUS DETAILS : " + b.toString());
            op = b.getString("op");
            from = b.getString("from");
            flag = b.getString("flag");
            str_msgdescr = b.getString("msg_desr");
            str_date = b.getString("date");
            stramt = b.getString("amt");
            str_rrn = b.getString("rrn");
            str_invoice_no = b.getString("invoice_no");
            str_batch_no = b.getString("batch_no");
            loc_flag = b.getString("loc_flag");
            str_remark = b.getString("remark");
            str_authid = b.getString("auth_id");
            tvr = b.getString("tvr");
            tsi = b.getString("tsi");
            aid = b.getString("aid");
            str_bal = b.getString("balance");
            respcode = b.getString("respcode");
            app_name = b.getString("app_name");

            try {
                if (from.equalsIgnoreCase("DTH") || from.equalsIgnoreCase("Prepaid") || from.equalsIgnoreCase("Postpaid") || from.equalsIgnoreCase("Gas") || from.equalsIgnoreCase("Landline")
                        || from.equalsIgnoreCase("Broadband") || from.equalsIgnoreCase("Datacard") || from.equalsIgnoreCase("Insurance") || from.equalsIgnoreCase("Electricity")) {
                    modeofPay = b.getString("modeOfPayment");
                    recharge_type = b.getString("rechargetype");
                    mobile_no = b.getString("mobileno");
                    operator_name = b.getString("operator_name");
                    txtpaytype.setText("Recharge " + op);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!op.equals(Constants.UPIAutoCheckStatus)) {
            if (op.equals(Constants.cashTrans_SaleByCash)) {
                txtpaytype.setText("Sale By Cash");
                if (from.equalsIgnoreCase("DTH") || from.equalsIgnoreCase("Prepaid") || from.equalsIgnoreCase("Postpaid") || from.equalsIgnoreCase("Gas") || from.equalsIgnoreCase("Landline")
                        || from.equalsIgnoreCase("Broadband") || from.equalsIgnoreCase("Datacard") || from.equalsIgnoreCase("Insurance") || from.equalsIgnoreCase("Electricity")) {
                    txtpaytype.setText(from + "-" + "Cash");
                }
            } else if (op.equals(Constants.recharge)) {
                txtpaytype.setText(from + "-" + "Wallet");
            } else {
                str_authid = b.getString("auth_id");
                str_pay_type = b.getString("type");
                str_reader = b.getString("reader");
                strCardno = b.getString("card_no");
                str_card_type = b.getString("card_type");

                if (from.equalsIgnoreCase("DTH") || from.equalsIgnoreCase("Prepaid") || from.equalsIgnoreCase("Postpaid") || from.equalsIgnoreCase("Gas") || from.equalsIgnoreCase("Landline")
                        || from.equalsIgnoreCase("Broadband") || from.equalsIgnoreCase("Datacard") || from.equalsIgnoreCase("Insurance") || from.equalsIgnoreCase("Electricity")) {
                    txtpaytype.setText(from + ":" + "Card");
                }
            }
        } else {
            str_cust_vpa = getArguments().getString("cust_vpa");
            payment_type.setVisibility(View.GONE);
        }

        if (flag.equals("fail")) {
            imgsuccess.setImageResource(R.drawable.ic_void_success);
            txt_trans_status.setText("Transaction Declined");
        }

        txtTransId.setText(str_rrn);
        if (str_remark == null || str_remark.equalsIgnoreCase("null") || str_remark.equalsIgnoreCase(""))
            txtRemark.setText("");
        else
            txtRemark.setText("" + str_remark);

        txtAED.setText(Constants.CURRENCY_NAME + " " + stramt);
        if (op.equalsIgnoreCase(Constants.MICRO_BAL_ENQ) || op.equalsIgnoreCase(Constants.MICRO_MINI_STMT)) {
            txtAED.setVisibility(View.GONE);
        }

        if (op.equalsIgnoreCase(Constants.MICRO_BAL_ENQ)) {
            txtpaytype.setText("Balance Enquiry");
        } else if (op.equalsIgnoreCase(Constants.MICRO_CASH_WITHDRAW)) {
            txtpaytype.setText("Cash Withdrawal");
        }

        txtMercName.setText(Session.getString(getActivity().getApplicationContext(), SessionConstants.Session_merchantname));
        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticValues.setCurrenLocImg(null);
                if (!op.equals(Constants.UPIAutoCheckStatus)) {
                    Fragment fragment = null;
                    if (op.equalsIgnoreCase(Constants.MICRO_BAL_ENQ) || op.equalsIgnoreCase(Constants.MICRO_BAL_ENQ_SBM) ||
                            op.equalsIgnoreCase(Constants.MICRO_MINI_STMT) || op.equalsIgnoreCase(Constants.MICRO_MINI_STMT_SBM) ||
                            op.equalsIgnoreCase(Constants.MICRO_CASH_WITHDRAW) ||  op.equalsIgnoreCase(Constants.MICRO_CASH_WITHDRAW_SBM)) {
                    //    fragment = new MicroAtmReceipt(false);
                    }else if (op.equalsIgnoreCase(Constants.wscardpay) || op.equalsIgnoreCase(Constants.wsVoid)) {
                   //     fragment = new FragmentTransactionDetailsNew();
                    }else if (op.equalsIgnoreCase(Constants.cashTrans_SaleByCash)) {
                 //       fragment = new CashTransactionReceipts();
                    } else {
                   //     fragment = new GetReceiptImageFragment();
                    }
                    //Fragment fragment = new FragmentTransactionDetailsNew();
                    Bundle args = new Bundle();
                    args.putString("from", from);
                    args.putString("op", op);
                    args.putString("flag", "" + flag);
                    args.putString("amt", "" + stramt);
                    if (op.equalsIgnoreCase(Constants.MICRO_BAL_ENQ) ||  op.equalsIgnoreCase(Constants.MICRO_BAL_ENQ_SBM) ||  op.equalsIgnoreCase(Constants.MICRO_CASH_WITHDRAW_SBM) || op.equalsIgnoreCase(Constants.MICRO_CASH_WITHDRAW)) {
                        args.putString("type", "Card");
                    } else {
                        args.putString("type", "" + txtpaytype.getText().toString().trim());
                    }
                    args.putString("rrn", str_rrn);
                    args.putString("remark", str_remark);
                    args.putString("date", str_date);
                    args.putString("auth_id", str_authid);
                    args.putString("msg_desr", str_msgdescr);
                    args.putString("invoice_no", str_invoice_no);
                    args.putString("card_type", str_card_type);
                    if (AndyUtility.isNullOrEmpty(app_name))
                        args.putString("app_name", "" + MyConst.getAppl_name());
                    else
                        args.putString("app_name", "" + app_name);
                    args.putString("balance", str_bal);
                    args.putString("respcode", respcode);

                    //clear Appl name
                    MyConst.setAppl_name("");

                    args.putString("loc_flag", loc_flag);
                    args.putString("tvr", tvr);
                    args.putString("tsi", tsi);
                    args.putString("aid", aid);
                    if (str_reader != null) {
                        args.putString("reader", str_reader);
                        args.putString("card_no", strCardno);
                    }
                    args.putString("batch_no", str_batch_no);
                    if (from.equalsIgnoreCase("DTH") || from.equalsIgnoreCase("Prepaid") ||
                            from.equalsIgnoreCase("Postpaid") || from.equalsIgnoreCase("Gas") ||
                            from.equalsIgnoreCase("Landline") || from.equalsIgnoreCase("Broadband") ||
                            from.equalsIgnoreCase("Datacard") || from.equalsIgnoreCase("Insurance") ||
                            from.equalsIgnoreCase("Electricity")) {
                        args.putString("op", "" + op);
                        args.putString("modeOfPayment", modeofPay);
                        args.putString("rechargetype", recharge_type);
                        args.putString("mobileno", mobile_no);
                        args.putString("operator_name", operator_name);
                    }
                    fragment.setArguments(args);
                    MyConst.paymentBaseList.add(fragment);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_mainLayout, fragment).addToBackStack(null).commit();
                } else {
                   // Fragment fragment = new UpiReceiptFragment();
                    Bundle args = new Bundle();
                    args.putString("op", op);
                    args.putString("flag", "" + flag);
                    args.putString("amt", "" + stramt);
                    args.putString("rrn", str_rrn);
                    args.putString("date", str_date);
                    args.putString("msg_desr", str_msgdescr);
                    args.putString("auth_id", str_authid);
                    args.putString("invoice_no", str_invoice_no);
                    args.putString("batch_no", str_batch_no);
                    args.putString("cust_vpa", str_cust_vpa);
                    /*args.putString("loc_flag", loc_flag);
                    args.putString("tvr", tvr);
                    args.putString("tsi", tsi);
                    args.putString("aid", aid);*/

                    if (op.equalsIgnoreCase("DTH") || op.equalsIgnoreCase("Prepaid") || op.equalsIgnoreCase("Postpaid") || op.equalsIgnoreCase("Gas") || op.equalsIgnoreCase("Landline")
                            || op.equalsIgnoreCase("Broadband") || op.equalsIgnoreCase("Datacard") || op.equalsIgnoreCase("Insurance") || op.equalsIgnoreCase("Electricity")) {
                        args.putString("op", "" + op);
                        args.putString("modeOfPayment", modeofPay);
                        args.putString("rechargetype", recharge_type);
                        args.putString("mobileno", mobile_no);
                        args.putString("operator_name", operator_name);
                    }

                  //  fragment.setArguments(args);
                   // MyConst.paymentBaseList.add(fragment);
                    //getFragmentManager().beginTransaction().replace(R.id.fragment_mainLayout, fragment).addToBackStack(null).commit();
                }
            }
        });

    }

}