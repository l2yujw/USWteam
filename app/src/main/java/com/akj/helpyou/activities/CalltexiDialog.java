package com.akj.helpyou.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.akj.helpyou.R;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

public class CalltexiDialog extends Dialog implements View.OnClickListener {
    private Button cancelButton;
    private Button callButton;
    private TextView telNum;
    private Context context;
    private MapPoint currentLocation;
    private String PointToAddress;
    private String region;
    String tel = "1588-4388";

    public void getAddress(String s){
        PointToAddress = s;
    }


    private androidx.appcompat.widget.Toolbar toolbar;

    private CalltexiDialog.GroupDialogListener groupDialogListener;

    public CalltexiDialog(Context context) {
        super(context);
        this.context = context;
    }

    interface GroupDialogListener{
        void onCancelClicked();
        void onCallClicked();
    }
    public void dialogListener(CalltexiDialog.GroupDialogListener groupDialogListener){
        this.groupDialogListener = groupDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_calltexi);
        TextView textView = findViewById(R.id.region);


        region = PointToAddress;
        Log.e("확인", "주소: " + PointToAddress);
        textView.setText(region);
        Log.e("region", "지역: " + region);
        toolbar = findViewById(R.id.calltexitoolbar);
        toolbar.setTitle("장애인 콜 택시 호출");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

        cancelButton = findViewById(R.id.button25);
        callButton = findViewById(R.id.button26);
        telNum = findViewById(R.id.callNum);
        telNum.setText(tel);

        cancelButton.setOnClickListener(this);
        callButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button26:
                ((Activity)context).startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+tel)));
                break;
            case R.id.button25:
                cancel();
                break;
        }
    }

}
