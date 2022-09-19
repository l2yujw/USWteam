package com.akj.helpyou;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
// 바텀 네비게이션 추가 합시다 -> 승강기, 화장실 등등있어요.
public class SubwayMapActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_subwaymap);

    }

}
