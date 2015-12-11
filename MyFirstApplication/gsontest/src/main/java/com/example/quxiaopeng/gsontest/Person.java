package com.example.quxiaopeng.gsontest;

/**
 * Created by quxiaopeng on 15/9/7.
 */
public class Person {
    int age;
    String name;
    char sex;

    public  Person(){
        this.age=0;
        this.name="a";
        this.sex='m';
    }
    public Person(int age,String name, char sex){
        this.age=age;
        this.name=name;
        this.sex=sex;
    }

    public void setPerson(int age,String name,char sex){
        this.age=age;
        this.name=name;
        this.sex=sex;
    }
    @Override
    public String toString() {
        return "age is:"+age+" name is:"+name+" sex is:"+(sex=='f'?"female":"male");
    }
}
