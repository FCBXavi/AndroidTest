package com.example.quxiaopeng.guavatest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test();
    }

    public void test() {

        //ImmutableSet:不可修改的集合
        Set<String> set = new HashSet();
        set.add("d");
        ImmutableSet<String> immutableSet = ImmutableSet.of("a", "b");
        for (String s : immutableSet) {
            Log.i("data", s);
        }

        //对set进行修改后,immutableset中的内容不会改变
        //ImmutableSet<String> immutableSet = ImmutableSet.copyOf(set);

        //ImmutableSet immutableSet1=ImmutableSet.builder().add("e").add("c").addAll(set).build();
        //会抛出异常java.lang.UnsupportedOperationException
        //immutableSet.add("cc");


        //Multiset:把重复的元素放入集合
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("a");
        list.add("b");
        HashMultiset<String> multiset = HashMultiset.create();
        multiset.addAll(list);
        Log.i("count of a", multiset.count("a") + "");
        Log.i("count of b", multiset.count("b") + "");

        //Multimap:双向 Map
        Multimap<String,String> multimap= HashMultimap.create();
        multimap.put("key","value1");
        multimap.put("key","value2");
        multimap.put("key1","value1");
        Log.i("multimap",multimap.toString());


        //BiMap
        BiMap<String,String>biMap= HashBiMap.create();
        biMap.put("key","value");
        Log.i("bimap",biMap.inverse().get("value"));

        //MapMaker: 超级强大的 Map 构造工具
        //首先，它可以用来构造 ConcurrentHashMap:
        ConcurrentMap<String ,Object> map1=new MapMaker().concurrencyLevel(8).makeMap();
        //或者构造用各种不同 reference 作为 key 和 value 的 Map:
        ConcurrentMap<String,Object> map2=new MapMaker().weakKeys().softValues().makeMap();
        //或者构造有自动移除时间过期项的 Map expireAfterWrite方法为protected,无法调用该方法
        //ConcurrentMap<String,Object> map3=new MapMaker().expireAfterWrite(30, TimeUnit.SECONDS).makeMap();

        //Ordering class: 灵活的多字段排序比较器




    }




}
