package com.example.granzia.Controlers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
//import com.google.android.gms.ads.MobileAds;
import com.example.granzia.Adapter.Category_RecycleViewAdapter;
import com.example.granzia.Fragments.CategoryFragment;
import com.example.granzia.Fragments.ProductFragment;
import com.example.granzia.R;

public class Categories extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener , Category_RecycleViewAdapter.FragmentActionListener {
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        if (findViewById(R.id.drawer_layout) != null) {
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        }else {
            drawer =  (DrawerLayout) findViewById(R.id.drawer_layout_land);
        }

        //________________Tool & Navigation Bar_________________\\
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //________________Adding Fragment_________________\\
        if (savedInstanceState == null) addCategoriesFragment();

        //_______________AdMob_____________________________\\
        //  MobileAds.initialize(this, "YOUR_ADMOB_APP_ID");
    }
    //__________________ Fragments filling _____________________________\\
    private void addCategoriesFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CategoryFragment categoryFragment = new CategoryFragment();
        fragmentTransaction.add(R.id.fragment_Container,categoryFragment);
        fragmentTransaction.commit();
    }
    @Override
    public void onCategorySelected(String id){
        if (drawer.findViewById(R.id.drawer_layout) != null) {
            Intent intent = new Intent(this, Products.class);
            intent.putExtra("categ info",id);
            startActivity(intent);
        }else if (drawer.findViewById(R.id.drawer_layout_land) != null){
            addProductsFragment(id);
        }
    }
    protected void addProductsFragment(String categoryID) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(Category_RecycleViewAdapter.FragmentActionListener.KEY_SELECTED_CATEGORY, categoryID);
        ProductFragment productFragment = new ProductFragment();
        productFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment_Container_p, productFragment ,categoryID);
        fragmentTransaction.addToBackStack(categoryID);
        fragmentTransaction.commit();
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
        int id = item.getItemId();
        if (id == R.id.nav_About) {
            startActivity(new Intent(this,About.class));
        } else if (id == R.id.nav_contactUs) {
            startActivity(new Intent(this,ContactUs.class));
        } else if (id == R.id.nav_Events) {
            startActivity(new Intent(this,Events.class));
        } else if (id == R.id.nav_findUs) {
            startActivity(new Intent(this, FindUs.class));
        } else if (id == R.id.nav_Offers) {
            startActivity(new Intent(this,Offers.class));
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
