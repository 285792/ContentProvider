package com.example.Test;


import java.util.HashMap;
import java.util.Map;


public class Test1 {

    /*
     * 取出一个字符串中字母出现的次数
     */

    public static void main(String[] args) {
        String str = "hagsdioqwenqjnd";
        Map<Character, Integer> total = new HashMap<Character, Integer>();
        //通过哈希存放键值对 当字符不存在时，值为1 字符存在时 值加1，最后通过字符读取值
        for(int i = 0, len = str.length(); i < len; i++){
            char c = str.charAt(i);
            Integer a = total.get(c);
            if(a == null){
                total.put(c, 1);
            }else{
                // i++ 返回原来的值，++i 返回加1后的值
                total.put(c, ++a);
            }
        }

        System.out.println(total.get(new Character('q')));
        System.out.println(total.toString());
    }
}

