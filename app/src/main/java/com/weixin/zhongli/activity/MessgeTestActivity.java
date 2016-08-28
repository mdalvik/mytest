package com.weixin.zhongli.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.dss886.emotioninputdetector.library.EmotionInputDetector;
import com.weixin.zhongli.R;

public class MessgeTestActivity extends AppCompatActivity {

    ListView list;
    ViewPager vp;

    EditText etShur;
    TextView btCilk;
   private EmotionInputDetector mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messge_test);
        etShur= (EditText) findViewById(R.id.et_shur);
        btCilk= (TextView) findViewById(R.id.bt_cilk);
        list= (ListView) findViewById(R.id.list);
        vp= (ViewPager) findViewById(R.id.vp);

        EmotionInputDetector.with(this)
                .setEmotionView(vp)
                .bindToContent(list)
                .bindToEditText(etShur)
                .bindToEmotionButton(btCilk)
                .build();
    }

    @Override
    public void onBackPressed() {
       /* if (!mDetector.interceptBackPress()) {
            super.onBackPressed();
        }*/
    }
}
