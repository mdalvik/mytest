
package com.weixin.zhongli.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.weixin.zhongli.IndicatorActivity;
import com.weixin.zhongli.R;


/**
 * 登陆页面
 *
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    private EditText et_usertel;
    private EditText et_password;
    private Button btn_login;
    private Button btn_qtlogin;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dialog = new ProgressDialog(LoginActivity.this);
        initView();

    }

    private void initView() {

        et_usertel = (EditText) findViewById(R.id.et_usertel);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_qtlogin = (Button) findViewById(R.id.btn_qtlogin);
        // 监听多个输入框

        et_usertel.addTextChangedListener(new TextChange());
        et_password.addTextChangedListener(new TextChange());


        btn_qtlogin.setOnClickListener(this);
        btn_login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_login:
                //登录
                dialog.setMessage("正在登录...");
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.show();
               new Handler().postDelayed(new Runnable() {
                   @Override
                   public void run() {

                       startActivity(new Intent(LoginActivity.this, IndicatorActivity.class));
                       finish();
                   }
               },2000);



                break;
            case R.id.btn_qtlogin:
                //注册
                startActivity(new Intent(LoginActivity.this,
                        RegisterActivity.class));


                break;
        }
    }


    // EditText监听器
    class TextChange implements TextWatcher {

        @Override
        public void afterTextChanged(Editable arg0) {

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence cs, int start, int before,
                                  int count) {

            String user = et_usertel.getText().toString().trim();
            String password = et_password.getText().toString().trim();
            boolean Sign2 = user.length() > 0;
            boolean Sign3 = password.length() > 0;

            if (Sign2 & Sign3) {
//                if (user.equals("911")&&password.equals("lai")){
                if (true){
                    btn_login.setTextColor(0xFFFFFFFF);
                    btn_login.setEnabled(true);
                }

            }
            // 在layout文件中，对Button的text属性应预先设置默认值，否则刚打开程序的时候Button是无显示的
            else {
                btn_login.setTextColor(0xFFD0EFC6);
                btn_login.setEnabled(false);
            }
        }

    }







}
