package com.example.ashrafi.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private int[] imageResId = {R.drawable.ic_one, R.drawable.ic_two, R.drawable.ic_three }; //tab icon images

    /*public static Context contextOfApplication;
    //this method is to get the context of this application/activity
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drayer_layout);

        //contextOfApplication = getApplicationContext();

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SimpleFragmentPagerAdapter(getSupportFragmentManager(),
                MainActivity.this)); //setting the adapter with the viewpager type object

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs); //the tab-layout
        tabLayout.setupWithViewPager(viewPager); //built in function to add the tab-layout with the viewpager type object?

        for (int i = 0; i < imageResId.length; i++) {
            tabLayout.getTabAt(i).setIcon(imageResId[i]); //
        }

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); //built in function?

        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer); //built in function?


        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);



    }

    //underneath method-> option bar/menu bar task
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }


        //reacting to action selection from app bar
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.refresh:
                Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            // action with ID action_settings was selected
            case R.id.action:
                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.security:
                Toast.makeText(this, "Security selected", Toast.LENGTH_SHORT)
                        .show();
            case R.id.more:
                Toast.makeText(this, "More selected", Toast.LENGTH_SHORT)
                        .show();
            default:
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        Intent intent;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                intent = new Intent(this, NewActivity.class);
                startActivity(intent);
                Toast.makeText(this, "First Navigation", Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.nav_second_fragment:
                intent = new Intent(this, NewActivity.class);
                startActivity(intent);
                Toast.makeText(this, "Second Navigation", Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.nav_third_fragment:
                intent = new Intent(this, NewActivity.class);
                startActivity(intent);
                Toast.makeText(this, "Third Navigation", Toast.LENGTH_SHORT)
                        .show();
                break;
            default:
        }
    }

    // ...


}
