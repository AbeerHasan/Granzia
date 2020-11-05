package com.example.granzia.Controlers;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.granzia.Controlers.Categories;
import com.example.granzia.R;

public class About extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        //______________ Toolbar ________________________\\
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //______________ Navigation side bar ____________\\
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //_______________ Back Bottun ___________________\\
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackButtonClicked();
            }
        });
        //
    }
    public void BackButtonClicked(){
        this.finish();
    }
    //________________Tool & Navigation Bar_________________\\
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_Exit) {
            System.exit(0);
        }else if (id == R.id.action_home){
            startActivity(new Intent(this,Categories.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_About) {
            startActivity(new Intent(this,About.class));
        } else if (id == R.id.nav_contactUs) {
            startActivity(new Intent(this,ContactUs.class));
        } else if (id == R.id.nav_Events) {
            startActivity(new Intent(this,Events.class));
        } else if (id == R.id.nav_findUs) {
            startActivity(new Intent(this,FindUs.class));
        } else if (id == R.id.nav_Offers) {
            startActivity(new Intent(this,Offers.class));
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
