package com.airsaid.indicatedotview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.airsaid.indicatedotview.widget.IndicateDotView;

public class MainActivity extends AppCompatActivity {

    private IndicateDotView mIndicateDotView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIndicateDotView = (IndicateDotView) findViewById(R.id.indicateDotView);
//        mIndicateDotView.setClickable(false);
    }
}
