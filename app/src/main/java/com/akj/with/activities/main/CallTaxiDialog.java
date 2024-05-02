package com.akj.with.activities.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.akj.with.R;

public class CallTaxiDialog extends Dialog implements View.OnClickListener {
    private Button cancelButton;
    private Button okButton;
    private TextView telNum;
    private Context context;
    public String callTaxiAddress;
    public String region;
    private int site;
    private String tel = null;
    private String[][] callTaxi = {
            {"서울", "1588-4388"},
            {"경기 고양시", "1577-5909"},
            {"경기 과천시", "02-502-9418"},
            {"경기 광명시", "02-2688-2582"},
            {"경기 광주시", "1666-6636"},
            {"경기 구리시", "031-551-0020"},
            {"경기 군포시", "1899-4428"},
            {"경기 김포시", "1899-2008"},
            {"경기 남양주시", "1666-5525"},
            {"경기 동두천시", "031-862-1091"},
            {"경기 부천시", "1588-3815"},
            {"경기 성남시", "1577-1158"},
            {"경기 수원시", "031-523-5525"},
            {"경기 시흥시", "031-488-6822~4"},
            {"경기 안산시", "1588-5410"},
            {"경기 안성시", "031-674-8050"},
            {"경기 안양시", "031-389-5200"},
            {"경기 양주시", "031-862-9977"},
            {"경기 여주시", "031-882-0747"},
            {"경기 오산시", "031-378-7816~7"},
            {"경기 용인시", "1588-6585"},
            {"경기 의왕시", "031-462-8253"},
            {"경기 의정부시", "1577-2515"},
            {"경기 이천시", "1899-0017"},
            {"경기 파주시", "1899-6199"},
            {"경기 평택시", "031-651-4700"},
            {"경기 포천시", "031-536-2000"},
            {"경기 하남시", "031-795-2701~3"},
            {"경기 화성시", "1588-0677"},
            {"경기 가평군", "031-581-2302"},
            {"경기 양평군", "1899-8268"},
            {"경기 연천군", "031-835-1155"}
    };

    private androidx.appcompat.widget.Toolbar toolbar;

    private GroupDialogListener groupDialogListener;

    public CallTaxiDialog(Context context) {
        super(context);
        this.context = context;
    }

    interface GroupDialogListener {
        void onCancelClicked();

        void onCallClicked();
    }

    public void dialogListener(GroupDialogListener groupDialogListener) {
        this.groupDialogListener = groupDialogListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_calltaxi);
        TextView textView = findViewById(R.id.region);
        TextView textView2 = findViewById(R.id.calltaxi_textview);

        textView.setText("서비스 제공 불가");
        tel = "호출 가능한 콜 택시가 없습니다";
        textView2.setText("");
        for (site = 0; site < 31; site++) {
            if (callTaxiAddress.contains(callTaxi[site][0])) {
                region = callTaxi[site][0];
                textView2.setText("지역");
                tel = callTaxi[site][1];
            }
        }
        textView.setText(region);

        toolbar = findViewById(R.id.calltaxi_toolbar);
        toolbar.setTitle("장애인 콜 택시 호출");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

        cancelButton = findViewById(R.id.button_cancel);
        okButton = findViewById(R.id.button_ok);

        telNum = findViewById(R.id.callNum);
        telNum.setText(tel);

        cancelButton.setOnClickListener(this);
        okButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_ok:
                ((Activity) context).startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel)));
                break;
            case R.id.button_cancel:
                cancel();
                break;
        }
    }

}
