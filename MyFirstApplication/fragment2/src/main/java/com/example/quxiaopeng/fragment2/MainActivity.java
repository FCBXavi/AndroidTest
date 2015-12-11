package com.example.quxiaopeng.fragment2;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity {

    private ImageButton wechatButton;
    private ImageButton contactButton;
    private ImageButton friendCircleButton;
    private ImageButton settingsButton;

    private ContentFragment mContentFragment;
    private ContactFragment mContactFragment;
    private FriendCircleFragment mFriendCircleFragment;
    private SettingsFragment mSettingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wechatButton=(ImageButton)findViewById(R.id.ibt_wechat);
        contactButton=(ImageButton)findViewById(R.id.ibt_contact);
        friendCircleButton=(ImageButton)findViewById(R.id.ibt_friendCircle);
        settingsButton=(ImageButton)findViewById(R.id.ibt_settings);
        wechatButton.setOnClickListener(new ButtonListener());
        contactButton.setOnClickListener(new ButtonListener());
        friendCircleButton.setOnClickListener(new ButtonListener());
        settingsButton.setOnClickListener(new ButtonListener());

        setDefaultFragment();
    }

    private void setDefaultFragment()
    {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mContentFragment = new ContentFragment();
        transaction.replace(R.id.layoutContent, mContentFragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            FragmentManager fm = getFragmentManager();
            // 开启Fragment事务
            FragmentTransaction transaction = fm.beginTransaction();
            switch (v.getId()){
                case R.id.ibt_wechat:
                    if (mContentFragment==null){
                        mContentFragment=new ContentFragment();
                    }
                    transaction.replace(R.id.layoutContent,mContentFragment);
                    break;
                case R.id.ibt_contact:
                    if (mContactFragment==null){
                        mContactFragment=new ContactFragment();
                    }
                    transaction.replace(R.id.layoutContent,mContactFragment);
                    break;
                case R.id.ibt_friendCircle:
                    if (mFriendCircleFragment==null){
                        mFriendCircleFragment=new FriendCircleFragment();
                    }
                    transaction.replace(R.id.layoutContent,mFriendCircleFragment);
                    break;
                case R.id.ibt_settings:
                    if (mSettingsFragment==null){
                        mSettingsFragment=new SettingsFragment();
                    }
                    transaction.replace(R.id.layoutContent,mSettingsFragment);
                    break;
            }
            transaction.commit();
        }
    }
}
