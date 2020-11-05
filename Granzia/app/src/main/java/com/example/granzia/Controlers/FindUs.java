package com.example.granzia.Controlers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.granzia.Controlers.HelpClasses.DataParser;
import com.example.granzia.Controlers.HelpClasses.FetchURL;
import com.example.granzia.Controlers.HelpClasses.TaskLoadedCallback;
import com.example.granzia.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FindUs extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, TaskLoadedCallback {
    DrawerLayout drawer;

    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastlocation;
    private FusedLocationProviderClient fusedLocationClient;
    int PROXIMITY_RADIUS = 10000;
    double latitude,longitude;
    //-------Direction-----------------------
    private final static int LOCATION_REQUEST = 2;
    GoogleMap map;
    SupportMapFragment mapFragment;
    Button getDirection;
    MarkerOptions userLocation, companyLocation;
    private Marker currentLocationmMarker;
    private Polyline currentPolyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findus);

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

        //_______________ Back Bottun ____________________\\
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackButtonClicked();
            }
        });

        //______________Location & Get Direction _________\\
        companyLocation = new MarkerOptions().position(new LatLng(30.013106, 31.296306)).title("company Location");

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
        getDirection = (Button)findViewById(R.id.getDirectionButt);
        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserLocation ();
            }
        });

  }

    //________________get map & company Location prepared_________________\\
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setZoomControlsEnabled(true);
        companyLocation.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        map.addMarker(companyLocation);
        map.moveCamera(CameraUpdateFactory.newLatLng(companyLocation.getPosition()));

    }
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case LOCATION_REQUEST :
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    fusedLocationClient.getLastLocation()
                            .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    // Got last known location. In some rare situations this can be null.
                                    if (location != null) {
                                        // Logic to handle location object
                                        Double lat = location.getLatitude(),lon = location.getLongitude();
                                        userLocation = new MarkerOptions().position(new LatLng(lat,lon)).title("Your Location");

                                        userLocation.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                                        map.addMarker(userLocation);
                                        map.moveCamera(CameraUpdateFactory.newLatLng(userLocation.getPosition()));

                                        String url = getLocationURL(userLocation.getPosition(),companyLocation.getPosition(),"driving");
                                        new FetchURL(FindUs.this).execute(url,"driving");
                                    }
                                }
                            });
                }
                break;
        }
    }
    @Override
    public void onTaskDone(Object... values) {
        if(currentPolyline != null) {
            currentPolyline.remove();
            Toast.makeText(this,"no currentPolyline" ,Toast.LENGTH_SHORT).show();
        }
        currentPolyline = map.addPolyline((PolylineOptions) values[0]);
    }
    //_______________ Get user Location __________________________________\\
    private void getUserLocation (){
        if (ContextCompat.checkSelfPermission(FindUs.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(FindUs.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                //new AlertDialog.Builder(this).setTitle("Requered Location Permsion").setMessage("You have to give this permsion to access the feature");
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(FindUs.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_REQUEST);

            }
        } else {
            // Permission has already been granted
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                Double lat = location.getLatitude(),lon = location.getLongitude();
                                userLocation = new MarkerOptions().position(new LatLng(lat,lon)).title("Your Location");

                                userLocation.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                                map.addMarker(userLocation);
                                map.moveCamera(CameraUpdateFactory.newLatLng(userLocation.getPosition()));

                                String url = getLocationURL(userLocation.getPosition(),companyLocation.getPosition(),"driving");
                                new FetchURL(FindUs.this).execute(url,"driving");

                            }
                        }
                    });
        }

    }
    //________________ Get Direction_______________________\\
    private String getLocationURL(LatLng origin , LatLng dest , String directionMode){
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }
    //----------------------------------------------------------------------------------------------------------
    //_________________Back Button Action___________________\\
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
            startActivity(new Intent(this, FindUs.class));
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
