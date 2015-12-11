package com.example.quxiaopeng.floatactionbutton;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends ActionBarActivity {

    private FloatingActionButton floatingActionButton;
    private ListView mListView;
    private FrameLayout frameLayout;
    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextInputLayout textInputLayout;
    private List<View> views;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setListView();
          init();


    }

    public void init(){
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_button);
//        frameLayout=(FrameLayout)findViewById(R.id.root_layout);
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.root_layout);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
//        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        setSupportActionBar(toolbar);
        collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collaspsingToolBarLayout);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout);
        navigationView = (NavigationView)findViewById(R.id.navigation);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(coordinatorLayout, "Hello,I am a Snackbar!", Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "undo", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        Toast.makeText(MainActivity.this, "home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_discussion:
                        Toast.makeText(MainActivity.this, "discussion", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_friends:
                        Toast.makeText(MainActivity.this, "friends", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_message:
                        Toast.makeText(MainActivity.this, "message", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_subitem1:
                        Toast.makeText(MainActivity.this, "submit1", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_subitem2:
                        Toast.makeText(MainActivity.this, "submit2", Toast.LENGTH_SHORT).show();
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
 //       initViewPager();
        collapsingToolbarLayout.setTitle("Design Library");
//
//        tabLayout.addTab(tabLayout.newTab().setText("Tab1"));
//        tabLayout.addTab(tabLayout.newTab().setText("Tab2"));
//        tabLayout.addTab(tabLayout.newTab().setText("Tab3"));
//        tabLayout.setupWithViewPager(viewPager);
        //viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        initLoginButton();

    }
     public void initLoginButton(){
         Button loginButton=(Button)findViewById(R.id.btn_login);
         loginButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 textInputLayout=(TextInputLayout)findViewById(R.id.textInputLayout_password);
                 EditText editText_pwd=textInputLayout.getEditText();
                 String s=editText_pwd.getText().toString();
                 if (TextUtils.isEmpty(s)){
                     textInputLayout.setError("Please input password!");
                     textInputLayout.setErrorEnabled(true);
                 }
                 else if (s.length()>10){
                     textInputLayout.setError("Too much words!");
                     textInputLayout.setErrorEnabled(true);
                 }
                 else {
                     textInputLayout.setErrorEnabled(false);
                 }
             }
         });

     }


//    public void initViewPager(){
//        viewPager=(ViewPager)findViewById(R.id.vp_viewPager);
//        views=new ArrayList<>();
//        LayoutInflater inflater=getLayoutInflater();
//        View view1=inflater.inflate(R.layout.fragment1, null);
//        View view2=inflater.inflate(R.layout.fragment2, null);
//        View view3=inflater.inflate(R.layout.fragment3,null);
//        views.add(view1);
//        views.add(view2);
//        views.add(view3);
//        MyAdapter adapter=new MyAdapter(views);
//        viewPager.setAdapter(adapter);
//        viewPager.setCurrentItem(0);
//        viewPager.setOffscreenPageLimit(2);
//    }


    class MyAdapter extends PagerAdapter {

        private List<View> views;

        public MyAdapter(List<View> views) {
            this.views = views;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager) container).addView(views.get(position));
            return views.get(position);
        }
    }



//    public void setListView(){
//        mListView = (ListView) findViewById(R.id.lv_listview);
//        List<Map<String ,String>>list=new ArrayList<>();
//        for (int i=0;i<20;i++){
//            Map<String,String>map=new HashMap<>();
//            map.put("data","data"+i);
//            list.add(map);
//        }
//        SimpleAdapter adapter=new SimpleAdapter(this,list,R.layout.item,new String[]{"data"},new int[]{R.id.tv_text});
//        mListView.setAdapter(adapter);
//    }

}
