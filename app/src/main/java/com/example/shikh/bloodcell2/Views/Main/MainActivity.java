package com.example.shikh.bloodcell2.Views.Main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.shikh.bloodcell2.R;
import com.example.shikh.bloodcell2.Utils.SharedPrefManager;
import com.example.shikh.bloodcell2.Views.Authentication.LoginActivity;
import com.example.shikh.bloodcell2.Views.Fragments.FragmentAboutUs;
import com.example.shikh.bloodcell2.Views.Fragments.FragmentContactUs;
import com.example.shikh.bloodcell2.Views.Fragments.FragmentDonate;
import com.example.shikh.bloodcell2.Views.Fragments.FragmentFeedback;
import com.example.shikh.bloodcell2.Views.Fragments.FragmentLearn;
import com.example.shikh.bloodcell2.Views.Fragments.FragmentRequest;
import com.example.shikh.bloodcell2.Views.Fragments.FragmentSearch;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.logout)
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        ButterKnife.bind(this);
        email.setText(SharedPrefManager.getInstance(this).getUserEmail());
        name.setText(SharedPrefManager.getInstance(this).getUsername());
        phone.setText(SharedPrefManager.getInstance(this).getUserMobile());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            onBackPressed2();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @OnClick(R.id.logout)
    public void onLogout(View view) {
        SharedPrefManager.getInstance(this).logout();
        finish();
        startActivity(new Intent(this, LoginActivity.class));
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
    private void displayselectedscreen(int id) {
        Fragment fragment = null;
        switch (id) {
            case R.id.history:
                Toast.makeText(this, "Coming soon in next Prototype.!!",
                        Toast.LENGTH_LONG).show();
//                fragment = new FragmentDonate();
                break;
            case R.id.donate:
                fragment = new FragmentDonate();
                break;
            case R.id.request:
                fragment = new FragmentRequest();
                break;
            case R.id.search:
//                Toast.makeText(this, "Coming soon in next Review 3.!!",
//                        Toast.LENGTH_LONG).show();
                fragment = new FragmentSearch();
                break;
            case R.id.feedback:
                fragment = new FragmentFeedback();
                break;
            case R.id.about_us:
                fragment = new FragmentAboutUs();
                break;
            case R.id.contact_us:
                fragment = new FragmentContactUs();
                break;
            case R.id.info:
//                Toast.makeText(this, "Coming soon in next Review 3.!!",
//                        Toast.LENGTH_LONG).show();
                fragment = new FragmentLearn();
                break;
        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main,fragment);
            ft.commit();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);


        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        displayselectedscreen(id);
        return true;
    }


    boolean doubleBackToExitPressedOnce = false;



    public void onBackPressed2() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}


