package com.example.Test;

import java.util.ArrayList;
import java.util.List;

public class Test3 {
    //定义一个的ArrayList中存放一个String类型的对象，一个Inegter类型对象
    public static void main(String[] agrs){
        List<Object> list = new ArrayList<>();
        String strMsg = new String("likes");
        int intMsg = 7;
        list.add((Object)strMsg);
        list.add((Object)intMsg);
        System.out.println(list.toString());
    }
}
