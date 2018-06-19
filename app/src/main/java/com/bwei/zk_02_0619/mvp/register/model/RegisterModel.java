package com.bwei.zk_02_0619.mvp.register.model;

import android.util.Log;

import com.bwei.zk_02_0619.utils.OkhtttpUtils;

import java.util.Map;

public class RegisterModel {

    private static final String TAG = "RegisterModel---";

    public void register(String url, Map<String ,String> map, final IRegisterModel iRegisterModel){


        OkhtttpUtils instance = OkhtttpUtils.getInstance();

        instance.doPost(url, map, new OkhtttpUtils.OkCallback() {
            @Override
            public void onFailure(Exception e) {

                if (iRegisterModel!=null){

                    iRegisterModel.getError(e);

                }

            }

            @Override
            public void onResponse(String json) {

                if (iRegisterModel!=null){

                    Log.d(TAG, "onResponse: onResponse 您进来了");

                    iRegisterModel.getSuccess(json);

                }

            }
        });


    }

    public interface IRegisterModel{

        void getSuccess(String json);

        void getError(Exception e);

    }

}
