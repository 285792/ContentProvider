package com.example.contentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HideMenuIntentActivity extends AppCompatActivity {

    private EditText hide_menu_intent_et;
    private Button hide_menu_intent_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hide_menu_intent2);
        initView();
        hide_menu_intent_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass_for_intent = hide_menu_intent_et.getText().toString();
                passForIntent(pass_for_intent);
            }
        });

    }

    private void initView(){
        hide_menu_intent_et = (EditText) findViewById(R.id.hide_menu_intent_et);
        hide_menu_intent_button = (Button) findViewById(R.id.hide_menu_intent_button);
    }

    private void passForIntent(String pass){
        switch (pass){
            case "111":
                Log.e("yulu","==========跳转成功========");
                break;
            default:
                Log.e("yulu","=========密码输入错误========");
                break;
        }
    }
}