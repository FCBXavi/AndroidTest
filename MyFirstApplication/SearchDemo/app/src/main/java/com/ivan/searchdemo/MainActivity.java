package com.ivan.searchdemo;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class MainActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
//        MenuItem addItem = menu.findItem(R.id.add);
//        View v = getLayoutInflater().inflate(R.layout.image_view, null);
//        addItem.setActionView(v);
//        View v = findViewById(R.id.search);

      //  searchItem.expandActionView();
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//        searchView.getChildAt(0).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("search", "onClick");
//                MainActivity.this.finish();
//            }
//        });
//        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
//            @Override
//            public boolean onClose() {
//                Log.d("search", "onClose");
//                MainActivity.this.finish();
//                return true;
//            }
//        });
//        searchView.setOnQueryTextListener(this);
////        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
////            @Override
////            public boolean onMenuItemActionExpand(MenuItem item) {
////                Log.d("search", "onMenuItemActionExpand");
////                //MainActivity.this.finish();
////                return true;
////            }
////
////            @Override
////            public boolean onMenuItemActionCollapse(MenuItem item) {
////                Log.d("search", "onMenuItemActionCollapse");
////                MainActivity.this.finish();
////                return true;
////            }
////        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                break;
            case R.id.add:
                startAnimate(item);
                break;

            case android.R.id.home:
                break;
        }
        Log.d("search", "id= " + item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    private void startAnimate(MenuItem item) {
        View v = item.getActionView();
        Animation clockwiseAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise_45);
        clockwiseAnim.setFillAfter(true);
        v.startAnimation(clockwiseAnim);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // User pressed the search button
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // User changed the text
        return false;
    }
}
