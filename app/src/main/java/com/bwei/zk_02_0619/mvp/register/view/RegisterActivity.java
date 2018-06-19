package com.bwei.zk_02_0619.mvp.register.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bwei.zk_02_0619.HttpConfig;
import com.bwei.zk_02_0619.R;
import com.bwei.zk_02_0619.mvp.register.model.RegisterBean;
import com.bwei.zk_02_0619.mvp.register.presenter.RegisterPresenter;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener,iview{

    private static final String TAG = "RegisterActivity---";

    private EditText ed_userName,ed_affirm_password,ed_password;

    private Button btn_register;

    private RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        initDatas();

    }

    private void initDatas() {

    }

    private void initViews() {

        ed_userName = findViewById(R.id.ed_username);

        ed_affirm_password = findViewById(R.id.ed_affirm_password);

        ed_password = findViewById(R.id.ed_password);

        btn_register = findViewById(R.id.btn_register);

        registerPresenter = new RegisterPresenter(this);

        btn_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_register:

                Log.d(TAG, "onClick: 您机那里了");

                String name = ed_userName.getText().toString().trim();

                String password = ed_password.getText().toString().trim();

                String afftrm_password = ed_affirm_password.getText().toString().trim();

                Map<String,String> map = new HashMap<>();

                    map.put("username",name);
                    map.put("password",password);
                    map.put("repassword",afftrm_password);
                    registerPresenter.Register(HttpConfig.REGISTER_URL,map);

                break;

        }

    }

    @Override
    public void getSuccess(String json) {


        Gson gson = new Gson();

        RegisterBean registerBean = gson.fromJson(json, RegisterBean.class);

        int errorCode = registerBean.getErrorCode();

        if ("0".equals(errorCode)){

            Intent it = new Intent(RegisterActivity.this,SuccessActivity.class);

            startActivity(it);

        }

    }

    @Override
    public void getError(Exception e) {

        Log.d(TAG, "getSuccess: ---额嗯");

    }
}
