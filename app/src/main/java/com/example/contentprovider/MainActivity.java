 package com.example.contentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.format.Formatter;
import android.widget.Button;


import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlertDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.os.SystemClock;

 public class MainActivity extends AppCompatActivity {

    private String newId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addData = (Button) findViewById(R.id.add_data);
        Button queryData = (Button) findViewById(R.id.query_data);
        Button deleteData = (Button) findViewById(R.id.delete_data);
        Button updateData = (Button) findViewById(R.id.update_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.databasetest.provider/book");
                ContentValues values = new ContentValues();
                values.put("name","A Clash of Kings");
                values.put("author","George Martin");
                values.put("pages",1020);
                values.put("price",22.84);
                Uri newUri = getContentResolver().insert(uri,values);
                newId = newUri.getPathSegments().get(1);

            }
        });
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.databasetest.provider/book");
                Cursor cursor = getContentResolver().query(uri,null,null,null,null,null);
                if(cursor!=null){
                    while (cursor.moveToNext()){
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("yulu","name : "+name);
                        Log.d("yulu","author : "+author);
                        Log.d("yulu","pages : "+pages);
                        Log.d("yulu","price : "+price);
                    }
                    cursor.close();
                }
            }
        });
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.databasetest.provider/book/"+newId);
                getContentResolver().delete(uri,null,null);
            }
        });
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.databasetest.provider/book/"+newId);
                ContentValues values = new ContentValues();
                values.put("name","A Storm of Sword");
                values.put("pages",1216);
                values.put("price",24.05);
                getContentResolver().update(uri,values,null,null);
            }
        });
    }

     private boolean flag = false;
     private static final String PASS1="1a";
     private static final String PASS2="2b";
     final static int DURATION = 3000;// 规定有效时间
     private List<Integer> mList = new ArrayList<>();
     private List<String> mTime = new ArrayList<>();
     Object[] pass = new Object[]{25,25,24,25};


     @Override
     public boolean onKeyDown(int keyCode, KeyEvent event) {
         Log.e("yulu", "keycode : "+String.valueOf(keyCode));
         if(event.getAction() == KeyEvent.ACTION_DOWN){
             continuousClick(event);
         }

         return super.onKeyDown(keyCode, event);
     }

     //判断快速按键
     private void continuousClick(KeyEvent event) {
         int index = mList.size();
         mList.add(event.getKeyCode());
         Boolean contain = false;
         //每次点击添加当前时间
         mTime.add(String.valueOf(SystemClock.uptimeMillis()));
         Log.e("yulu","mlist : "+mList.toString());
         if(mList.size()>3){
             //将Arraylist对象转换为数组
             Object[] list = mList.toArray();
             for(int a=0;a<mList.size();a++){
                 System.out.println(list[a]+"\n"+mTime.get(a));
             }
             Log.e("yulu","后四个按键花费时间 : "+String.valueOf(Integer.parseInt(mTime.get(index))-Integer.parseInt(mTime.get(index-pass.length+1))));
             if (Integer.parseInt(mTime.get(index))-Integer.parseInt(mTime.get(index-pass.length+1)) < DURATION) {
                 for(int i=mList.size()-1; i>=mList.size()-pass.length;i--){
                     Log.e("yulu","list[i] : "+String.valueOf(list[i]));
                     Log.e("yulu","pass[j] : "+String.valueOf(pass[i-(mList.size()-pass.length)]));
                     contain= list[i] == pass[i-mList.size()+pass.length];
                     if (!contain){
                         break;
                     }
                 }
                 if(contain){
                     //重新初始化数据
                     normalDialog(this);
                     mList = new ArrayList<>();
                     mTime = new ArrayList<>();
                 }
             }else {
                 Log.e("yulu","==========超时========");
             }
         }
     }

     public void normalDialog(Context context){
         //得到自定义对话框
         View DialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit, null);
         //初始化edit值，把当前值显示到对话框里。
         EditText dialogEdit = (EditText)DialogView.findViewById(R.id.dialog_et);
         new AlertDialog.Builder(context).setTitle("请输入密码")
                 .setIcon(android.R.drawable.sym_def_app_icon)
                 .setView(DialogView)
                 .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int i) {
                         String passForApps = dialogEdit.getText().toString();
                         intent(passForApps);
                     }
                 }).setNegativeButton("取消",null).show();
     }

     public void intent(String password){
         switch (password){
             case PASS1:
                 Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                 intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 finish();
                 finishAndRemoveTask();
                 startActivity(intent);
                 break;
             case PASS2:
                 break;
             default:
                 Toast.makeText(this,"密码输入错误", Toast.LENGTH_SHORT).show();
                 break;
         }
     }
 }