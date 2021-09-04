package com.example.youcloudp1000sdk.custom_view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.youcloudp1000sdk.R;
import com.example.youcloudp1000sdk.retrofit.NetworkController;
import com.example.youcloudp1000sdk.retrofit.RequestParams;
import com.example.youcloudp1000sdk.retrofit.ResponseListener;
import com.example.youcloudp1000sdk.utils.Constants;
import com.example.youcloudp1000sdk.utils.SessionConst;


/**
 * Created by sachin.maske on 27-04-2017.
 */

public class CustomDialog extends Dialog {
    private TextView txtDialogTitle;
    private Button btnCancel,btnConfirm;
    RequestParams requestObj;
    ResponseListener api_listner;
    String screen_flag= "";

    public CustomDialog(@NonNull Context context, RequestParams params, ResponseListener listners, String op) {
        super(context);
        requestObj = params;
        api_listner = listners;
        screen_flag = op;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_cash_drawer);

        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initialize();
        setCancelable(false);

    }

    void initialize() {
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        txtDialogTitle = (TextView) findViewById(R.id.txtDialogTitle);
       if(screen_flag.equals(Constants.wschangepass)){
            txtDialogTitle.setText("Are you sure want to update your password?");
        }else if(screen_flag.equals("send_log")){
           txtDialogTitle.setText(SessionConst.getLog(getContext()));
           btnConfirm.setText("Send Log");
           //btnCancel.setVisibility(View.GONE);
       }
        setClicks();
    }

    void setClicks() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                 if(screen_flag.equals(Constants.wschangepass)){
                    NetworkController.getInstance().sendRequest(requestObj, api_listner, new SuccessCustomDialog(getContext(), "Password Management","Please wait", ""));
                }else if(screen_flag.equals("send_log")){
                     NetworkController.getInstance().sendRequest(requestObj, api_listner, new SuccessCustomDialog(getContext(),"Please Wait", "Sending Log", ""));
                }
            }
        });
    }
}
