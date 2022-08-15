package com.example.webviewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.contentprovider.R;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);
        OkHttpClient client = new OkHttpClient();

        try {
            RequestBody requestBody = new FormBody.Builder()
                    .add("username","admin")
                    .add("password","123456")
                    .build();
            Request request = new Request.Builder()
                    .url("http://www.baidu.com")
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}