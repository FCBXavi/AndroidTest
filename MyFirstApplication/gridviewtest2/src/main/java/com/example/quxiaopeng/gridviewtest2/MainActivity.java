package com.example.quxiaopeng.gridviewtest2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<FruitModel> fruitModels;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView= (GridView) findViewById(R.id.gv_fruit);

        fruitModels=new ArrayList<>();
        FruitModel fruitModel1=new FruitModel("樱桃",R.drawable.cherry);
        FruitModel fruitModel2=new FruitModel("火龙果",R.drawable.dragonfruit);
        FruitModel fruitModel3=new FruitModel("柠檬",R.drawable.lemon);
        FruitModel fruitModel4=new FruitModel("橙子",R.drawable.orange);
        FruitModel fruitModel5=new FruitModel("草莓",R.drawable.strawberry);
        FruitModel fruitModel6=new FruitModel("西瓜",R.drawable.watermelon);
        fruitModels.add(fruitModel1);
        fruitModels.add(fruitModel2);
        fruitModels.add(fruitModel3);
        fruitModels.add(fruitModel4);
        fruitModels.add(fruitModel5);
        fruitModels.add(fruitModel6);

        GridViewAdapter gridViewAdapter=new GridViewAdapter(this,fruitModels);
        gridView.setAdapter(gridViewAdapter);

    }


}
