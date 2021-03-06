package com.weixin.zhongli.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.weixin.zhongli.R;
import com.weixin.zhongli.util.LocalUserInfo;

import java.util.HashMap;
import java.util.Map;


public class UpdateFxidActivity extends Activity {

    public static final int UPDATE_FXID =4 ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_fxid);

        final String nick = LocalUserInfo.getInstance(UpdateFxidActivity.this)
                .getUserInfo("nick");

        final EditText et_nick = (EditText) this.findViewById(R.id.et_nick);
        // et_nick.setText(nick);
        TextView tv_save = (TextView) this.findViewById(R.id.tv_save);
        tv_save.setOnClickListener(new OnClickListener() {



            @Override
            public void onClick(View v) {
                String newFxid = et_nick.getText().toString().trim();
                if (nick.equals(newFxid) || newFxid.equals("")) {
                    return;
                }

                Intent intent=new Intent();
                intent.putExtra(UPDATE_FXID+"", et_nick.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
//                updateIvnServer(newFxid);
            }

        });
    }

    private void updateIvnServer(final String newFxid) {
        Map<String, String> map = new HashMap<String, String>();
        String hxid = LocalUserInfo.getInstance(UpdateFxidActivity.this)
                .getUserInfo("hxid");
        map.put("newFxid", newFxid);
        map.put("hxid", hxid);
        final ProgressDialog dialog = new ProgressDialog(
                UpdateFxidActivity.this);
        dialog.setMessage("正在更新...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();

      /*  LoadDataFromServer registerTask = new LoadDataFromServer(
                UpdateFxidActivity.this, Constant.URL_UPDATE_Fxid, map);*/

        /*registerTask.getData(new DataCallBack() {

            @SuppressLint("ShowToast")
            @Override
            public void onDataCallBack(JSONObject data) {
                dialog.dismiss();
                try {
                    int code = data.getInteger("code");
                    if (code == 1) {
                        LocalUserInfo.getInstance(UpdateFxidActivity.this)
                                .setUserInfo("fxid", newFxid);
                        finish();

                    } else if (code == 3) {

                        Toast.makeText(UpdateFxidActivity.this, "该微信号已经被占用...",
                                Toast.LENGTH_SHORT).show();
                    }

                    else {

                        Toast.makeText(UpdateFxidActivity.this, "更新失败...",
                                Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {

                    Toast.makeText(UpdateFxidActivity.this, "数据解析错误...",
                            Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }

        });*/
    }

    public void back(View view) {

        finish();
    }
}
