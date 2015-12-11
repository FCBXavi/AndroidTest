package com.example.quxiaopeng.gsontest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.tv_mytext);

        Gson gson = new Gson();

        //普通对象转换为Json字符串
        Person person1 = new Person(1, "xiaoming", 'f');
        String str1 = gson.toJson(person1);
        Log.i("object to json", str1);


        //Json字符串转换为普通对象
        Person person2 = gson.fromJson(str1, Person.class);
        Log.i("json to object", person2.toString());

        //集合对象转换为Json字符串
        List<Person> persons1 = new ArrayList<Person>();
        for (int i = 0; i < 10; i++) {
            Person person = new Person(i, "xiaoming", 'm');
            persons1.add(person);
        }
        String str2 = gson.toJson(persons1);
        Log.i("collection to json", str2);

        //json字符串转换为集合对象
        List<Person> persons2;
        persons2 = gson.fromJson(str2, new TypeToken<List<Person>>() {
        }.getType());
        for (Person person : persons2) {
            Log.i("json to collection", person.toString());
        }

        //数组转换为json字符串
        Person[] persons3 = new Person[3];
        int length=persons3.length;
        for (int i=0;i<length;i++) {
            persons3[i]=new Person(i, "xiaogang", 'm');
        }
        String str3 = gson.toJson(persons3);
        Log.i("array to Json", str3);

        //Json字符串转换为数组
        Person[] persons4 = gson.fromJson(str3, new TypeToken<Person[]>() {
        }.getType());
        for (Person person : persons4) {
            Log.i("json to array", person.toString());
        }


        //Map对象转换为json字符串
        Map<String, Person> map1 = new HashMap<>();
        for (int i = 0; i < 3; i++){
            map1.put("person"+Integer.toString(i+1),persons3[i]);
        }
        String str4=gson.toJson(map1);
        Log.i("map to json",str4);


        //json字符串转换为map对象
        Map<String,Person>map2=gson.fromJson(str4,new TypeToken<Map<String,Person>>(){}.getType());
        Log.i("json to map",map2.toString());

    }

}
