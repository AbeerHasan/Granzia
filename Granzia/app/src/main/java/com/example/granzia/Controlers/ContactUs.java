package com.example.granzia.Controlers;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

import com.example.granzia.Adapter.ContactUs_RecyclerViewAdapter;
import com.example.granzia.Models.Contact;
import com.example.granzia.MyApplication;
import com.example.granzia.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ContactUs extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    MyApplication myApplication;
    private final static int CALL_RQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);
        //________________ toolbar & Navigations _________________\\
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackButtonClicked();
            }
        });

        //____________________ Contacts Filling _______________________\\
        getCustomerServiceContacts();
        getDistributorContacts();
        //____________________ cull permission class___________________\\
        myApplication = (MyApplication) getApplicationContext();

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            myApplication.callPermission = true;
        }
        if(!myApplication.callPermission){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CALL_PHONE},CALL_RQUEST);
        }
    }
    //-----------------------------------------------------------------------------------------------------
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CALL_RQUEST && grantResults.length > 0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                myApplication.callPermission = true;
            }
        }
    }
    //____________________ Contacts Filling _______________________\\
    private RecyclerView recyclerView , recyclerView_;
    private DatabaseReference contactsRef;
    private ArrayList<Contact> contactsArrayList;
    ContactUs_RecyclerViewAdapter myAdapter;
    private void getCustomerServiceContacts() {
        recyclerView = (RecyclerView) findViewById(R.id.CustomerService_Recycl);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);//-- to set the no of items pear line
        recyclerView.setLayoutManager(layoutManager);
        contactsRef = FirebaseDatabase.getInstance().getReference().child("Database").child("Contacts").child("Customer Service");
        contactsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                contactsArrayList = new ArrayList<Contact>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Contact c = dataSnapshot1.getValue(Contact.class);
                    contactsArrayList.add(c);
                }
                myAdapter = new ContactUs_RecyclerViewAdapter(getBaseContext(),contactsArrayList);
                recyclerView.setAdapter(myAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Toast.makeText(,"*** Error in database ***", Toast.LENGTH_SHORT).show();
                throw databaseError.toException();
            }
        });
    }
    private void getDistributorContacts() {
        recyclerView_ = (RecyclerView) findViewById(R.id.Distributor_Recycl);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);//-- to set the no of items pear line
        recyclerView_.setLayoutManager(layoutManager);
        contactsRef = FirebaseDatabase.getInstance().getReference().child("Database").child("Contacts").child("Distributor");
        contactsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                contactsArrayList = new ArrayList<Contact>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Contact c = dataSnapshot1.getValue(Contact.class);
                    contactsArrayList.add(c);
                }
                myAdapter = new ContactUs_RecyclerViewAdapter(getBaseContext(),contactsArrayList);
                recyclerView_.setAdapter(myAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Toast.makeText(,"*** Error in database ***", Toast.LENGTH_SHORT).show();
                throw databaseError.toException();
            }
        });
    }
    //________________ toolbar & Navigations _________________\\

    public void BackButtonClicked(){
        this.finish();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            return true;
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
