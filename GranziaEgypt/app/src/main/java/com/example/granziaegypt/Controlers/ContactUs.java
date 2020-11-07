package com.example.granziaegypt.Controlers;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.granziaegypt.Adapter.ContactUs_RecyclerViewAdapter;
import com.example.granziaegypt.Models.Contact;
import com.example.granziaegypt.MyApplication;
import com.example.granziaegypt.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ContactUs extends MainActivity {

    MyApplication myApplication;
    private final static int CALL_RQUEST = 1;
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        UiInit();
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
}
