package com.bwei.zk_02_0619.mvp.register.presenter;

import android.util.Log;

import com.bwei.zk_02_0619.mvp.register.model.RegisterModel;
import com.bwei.zk_02_0619.mvp.register.view.iview;

import java.util.Map;

public class RegisterPresenter {

    private static final String TAG = "RegisterPresenter---";

    private iview iView;

    private RegisterModel registerModel;

    public RegisterPresenter(iview iView) {
        this.iView = iView;
        registerModel = new RegisterModel();
    }

    public void Register(String url, Map<String,String> map){

        Log.d(TAG, "Register: Presenter我也进来了");


        registerModel.register(url, map, new RegisterModel.IRegisterModel() {
            @Override
            public void getSuccess(String json) {

                if (iView!=null){

                    Log.d(TAG, "getSuccess:getSuccess 您进来了 ");

                    iView.getSuccess(json);

                }

            }

            @Override
            public void getError(Exception e) {


                if (iView!=null){

                    iView.getError(e);

                }

            }
        });

    }

}
