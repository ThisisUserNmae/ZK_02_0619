package com.bwei.zk_02_0619.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mumu on 2018/6/14.
 */

public class OkhtttpUtils {

    private static final String TAG = "OkhtttpUtils---";

    private static OkhtttpUtils mOkhtttpUtils;
    private OkHttpClient mOkHttpClien;
    private final Handler mHandler;


    private OkhtttpUtils() {
        //创建一个主线程的handler
        mHandler = new Handler(Looper.getMainLooper());
        mOkHttpClien = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .writeTimeout(5000, TimeUnit.MILLISECONDS)
                .build();
    }

    public static OkhtttpUtils getInstance() {
        if (mOkhtttpUtils == null) {
            synchronized (OkhtttpUtils.class) {
                if (mOkhtttpUtils == null) {
                    return mOkhtttpUtils = new OkhtttpUtils();
                }
            }
        }
        return mOkhtttpUtils;
    }

    public void doGet(String url, final OkCallback okCallback) {
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();

        final Call call = mOkHttpClien.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                if (okCallback != null) {

                    //切换到主线程
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            okCallback.onFailure(e);
                        }
                    });

                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (response != null && response.isSuccessful()) {
                                String json = response.body().string();
                                if (okCallback != null) {
                                    okCallback.onResponse(json);
                                    return;
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (okCallback != null) {
                            okCallback.onFailure(new Exception("网络异常"));
                        }
                    }
                });
            }
        });
    }

    public void doPost(String url, Map<String, String> map, final OkCallback okCallback) {

        FormBody.Builder builder = new FormBody.Builder();
        for (String key : map.keySet()) {
            builder.add(key, map.get(key));
        }
        FormBody formBody = builder.build();
        Request request = new Request.Builder()
                .post(formBody)
                .url(url)
                .build();
        final Call call = mOkHttpClien.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                if (okCallback != null) {

                    //切换到主线程
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            okCallback.onFailure(e);
                        }
                    });

                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (response != null && response.isSuccessful()) {
                                String json = response.body().string();

                                Log.d(TAG, "run: "+json);

                                if (okCallback != null) {
                                    okCallback.onResponse(json);
                                    return;
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (okCallback != null) {
                            okCallback.onFailure(new Exception("网络异常"));
                        }
                    }
                });
            }
        });
    }

    public interface OkCallback {
        void onFailure(Exception e);

        void onResponse(String json);
    }

}
