package com.example.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Test4 {

    public static class Student{
        String name;
        int score1;
        int score2;
        int score3;

        public Student(String name, int score1, int score2, int score3) {
            super();
            this.name = name;
            this.score1 = score1;
            this.score2 = score2;
            this.score3 = score3;
        }

        int scoreAll(){
            return score1+score2+score3;
        }

        @Override
        public String toString() {
            return "Student [name=" + name + ", score1=" + score1 + ", score2="
                    + score2 + ", score3=" + score3 + "]";
        }
    }

    public static void main(String[] args) {
        Student[] stu = new Student[5];
        Scanner scan = new Scanner(System.in);
        File file = new File("src/file/stu.txt");
        BufferedWriter writer;

        // TODO Auto-generated catch block
        try {
            if(!file.exists()){
                file.createNewFile();
            }

            writer = new BufferedWriter(new FileWriter(file));
            for(int i = 0; i < 5; i++){
                String str = scan.next();
                String[] inputData = str.split("#");
                int score1 = Integer.valueOf(inputData[1]);
                int score2 = Integer.valueOf(inputData[2]);
                int score3 = Integer.valueOf(inputData[3]);
                stu[i] = new Student(inputData[0], score1, score2, score3);
            }

            Arrays.sort(stu, new Comparator<Student>() {

                @Override
                public int compare(Student o1, Student o2) {
                    // TODO Auto-generated method stub
                    if(o1.scoreAll()>o2.scoreAll()){
                        return 1;
                    }else{
                        return -1;
                    }
                }
            });

            for(int i = 0; i < 5; i++){
                writer.write(stu[i].toString());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
