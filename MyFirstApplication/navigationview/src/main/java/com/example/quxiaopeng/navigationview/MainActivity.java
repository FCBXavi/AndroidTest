package com.example.quxiaopeng.navigationview;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView= (NavigationView) findViewById(R.id.nv_menu);

        //Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

//        final ActionBar actionBar=getSupportActionBar();
//        actionBar.setHomeAsUpIndicator(R.drawable.icon);
//        actionBar.setDisplayHomeAsUpEnabled(true);

        setupDrawerContent(mNavigationView);

    }

    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        switch (menuItem.getItemId()){
                            case R.id.nav_home:
                                Toast.makeText(getApplicationContext(),"home",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nav_friends:
                                Toast.makeText(getApplicationContext(),"friends",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nav_discussion:
                                Toast.makeText(getApplicationContext(),"discussion",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nav_message:
                                Toast.makeText(getApplicationContext(),"message",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nav_subitem1:
                                Toast.makeText(getApplicationContext(),"subitem1",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nav_subitem2:
                                Toast.makeText(getApplicationContext(),"subitem2",Toast.LENGTH_SHORT).show();
                                break;
                        }
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                }
        );
    }

}
