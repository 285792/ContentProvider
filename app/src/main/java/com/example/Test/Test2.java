package com.example.Test;




public class Test2{
    /*
     * 假如我们在开发一个系统时需要对员工进行建模，【员工】包含3个属性【经理】也是员工，除了含有员工的属性外，另为还有一个奖金属性
         请使用继承的思想设计出员工类和经理类。要求类中提供必要的方法进行属性访问
     */
    public class yuan{
        private String name;
        private int id;
        private int age;

        public yuan(String name, int id, int age) {
            this.name = name;
            this.id = id;
            this.age = age;
        }
    }

    public class jin extends yuan{

        private int money;
        public jin(String name, int id, int age) {
            super(name, id, age);
        }
        public void money (int money){
            this.money=money;
        }
    }

    public static void main(String[] args){

    }
}
