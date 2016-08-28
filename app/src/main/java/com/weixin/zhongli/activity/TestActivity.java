package com.weixin.zhongli.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;

import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.StackAdapter;
import com.weixin.zhongli.R;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends Activity {

    CardStackView mStackView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        List datas=new ArrayList();
        datas.add("jian");
        datas.add("jian");
        datas.add("jian");
        datas.add("jian");



         mStackView = (CardStackView) findViewById(R.id.stackview_main);
        TestStackAdapter  mTestStackAdapter = new TestStackAdapter(this);
        mStackView.setAdapter(mTestStackAdapter);
        mTestStackAdapter.updateData(datas);

    }

    private class TestStackAdapter extends StackAdapter {
        public TestStackAdapter(Context context) {
            super(context);
        }

        @Override
        public void bindView(Object data, int position, CardStackView.ViewHolder holder) {

        }

        @Override
        protected CardStackView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
            return null;
        }
    }
}
