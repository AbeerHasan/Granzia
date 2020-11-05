package com.example.granzia.Controlers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.granzia.Adapter.Category_RecycleViewAdapter;
import com.example.granzia.Fragments.ProductFragment;

//import com.example.granzia.Adapter.Category_RecycleViewAdapter;
import com.example.granzia.Models.Product;
import com.example.granzia.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Products extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        //________________Tool & Navigation Bar_________________\\
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //________________Back Bottun ____________________\\
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackButtonClicked();
            }
        });

        //________________Adding Fragment_________________\\
        addProductsFragment(getIntent().getStringExtra("categ info"));

    }
    //________________ Filling Fragments _______________\\
    protected void addProductsFragment(String categoryID) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(Category_RecycleViewAdapter.FragmentActionListener.KEY_SELECTED_CATEGORY, categoryID);
        ProductFragment productFragment = new ProductFragment();
        productFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.fragment_Container_p, productFragment);
        fragmentTransaction.commit();
    }

    //________________Tool & Navigation_________________\\
    public void BackButtonClicked(){
        this.finish();
    }

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_Exit) {
            finish();
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
