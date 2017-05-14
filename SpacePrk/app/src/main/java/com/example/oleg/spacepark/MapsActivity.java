package com.example.oleg.spacepark;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.example.oleg.spacepark.R.layout.activity_maps;

public class MapsActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener ,OnMapReadyCallback {


    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String PREFS_NAME2 = "MyPrefsFile2";
    public static final String DEFAULT="N/A";
    String savedname, savedsmall,savedlastname,savedemail, savedmedium, savedbig,savedpay,savedfree;
    public String LONGTITUDE,LATITUDE,TAG1,TAG2,TAG3,EMAIL;
    private GoogleMap mMap;

    public double longitude;
    public double latitude;
    public Button sinexia;
    //Context ctx = this;
    String lan;
    String lon;
    String PER_NAME,ADDRESS;
    String LAT,LNG,ID;
    String MyJson;
    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 1;
    private static final int PERMISSION_ACCESS_FINE_LOCATION = 1;
    public String id = null ;
    public String latit = null ;
    public String lngtd = null ;
    public String RADIUS = null ;
    public String cityName="Athens";
    private GoogleApiClient client;
    //drawer
    Toolbar toolbar;
    Context context;
    MapsActivity ctx=this;
    DrawerLayout drawerLayout;
    RecyclerView recyclerView;
    String navTitles[];
    TypedArray navIcons;
    RecyclerView.Adapter recyclerViewAdapter;
    ActionBarDrawerToggle drawerToggle;
    View view;
    TextView tvfoundSpot,tvdestinationMarker,tvtimeToDestination,tvcharge,tvprice,tvcurrentPosition,tvdestinetionPosition;


    public LatLng parkSpace,parkSpace2,parkSpace3;

  public double  latMark1,longMark1,latMark2,longMark2,latMark3,longMark3;
    ///end drawer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_maps);
        setupToolbar();
        loadData();


        Intent intent = getIntent();
        LATITUDE = intent.getStringExtra("LATITUDE");
        LONGTITUDE = intent.getStringExtra("LONGTITUDE");
        TAG1 = intent.getStringExtra("bigcar");
        TAG2 = intent.getStringExtra("name");
        TAG3 = intent.getStringExtra("lastname");
       // email = intent.getStringExtra("email");
        Log.i("LATITUDE", "[" + LATITUDE + "]}}}}}}}}}}}}}}}}}}}}}}}}}}}}{{{{{{{{{{{{{{}}}");
        Log.i("TAG1", "[" + TAG1 + "]}}}}}}}}}}}}}}}}}}}}}}}}}}}}{{{{{{{{{{{{{{}}}");




        tvfoundSpot = (TextView)findViewById(R.id.found_spots);
        //apo map data

        tvdestinationMarker = (TextView)findViewById(R.id.destinationmarker);
        tvtimeToDestination = (TextView)findViewById(R.id.time_to_destination);
        tvcharge = (TextView)findViewById(R.id.chargingtx);
        tvprice = (TextView)findViewById(R.id.pricetx);
        tvcurrentPosition = (TextView)findViewById(R.id.current_position);
        tvdestinetionPosition = (TextView)findViewById(R.id.destination_position);


       // TextView txt = (TextView) View.inflate(this, R.layout.map_data, null);
        /*
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.mapdatalayout);
        View vi = inflater.inflate(R.layout.map_data, null); //log.xml is your file.
        TextView addressmarkertx = (TextView)vi.findViewById(R.id.addressmarker);
        addressmarkertx.setText(cityName);


        ///Request permissio for API 24 and higher///////////
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSION_ACCESS_COARSE_LOCATION);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_ACCESS_FINE_LOCATION);
        }
*/
        /////End ask permission/////////////////////////

// Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ///----------------------drawer
        //Initialize Views
        recyclerView  = (RecyclerView) findViewById(R.id.recyclerViewMap);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        //Setup Titles and Icons of Navigation Drawer
        navTitles = getResources().getStringArray(R.array.navDrawerItems);
        navIcons = getResources().obtainTypedArray(R.array.navDrawerIcons);

        //DIVIDERS
        recyclerView.addItemDecoration(new com.example.oleg.spacepark.DividerItemDecoration(getResources().getDrawable(R.drawable.line_seperator)));
        recyclerViewAdapter = new RecyclerViewAdapter(navTitles,navIcons,this);
        recyclerView.setAdapter(recyclerViewAdapter);

        /**
         *It is must to set a Layout Manager For Recycler View
         *As per docs ,
         *RecyclerView allows client code to provide custom layout arrangements for child views.
         *These arrangements are controlled by the RecyclerView.LayoutManager.
         *A LayoutManager must be provided for RecyclerView to function.
         */

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Finally setup ActionBarDrawerToggle
        setupDrawerToggle();




        //TextView usernametx= new homeView(TextView)  find
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);

            }
        };

        //change drawerToggle button icon
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        Drawable drawable = getResources().getDrawable(R.drawable.slide_menu_icon);
        actionBarDrawerToggle.setHomeAsUpIndicator(drawable);
        actionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API)
                // .addScope(Drive.SCOPE_FILE)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // Create an instance of GoogleAPIClient.
        if (client == null) {
            client = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }





    @Override
    public void onBackPressed() {

        Intent intent = new Intent(MapsActivity.this, UserActivity.class);

        startActivity(intent);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            // Log.i("latitude3", "["+ latitude+"]}}}}}}}}}}}}}}}}}}}}}}}}}}}}{{{{{{{{{{{{{{}}}");
            // mMap.setMyLocationEnabled(true);
            onConnected(null);
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbarmap);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    void setupDrawerToggle(){
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        drawerToggle.syncState();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                client);
        if (mLastLocation == null) {
            // Log.i("NUUUUULl", "["+ latitude+"]}}}}}}}}}}}}}}}}}}}}}}}}}}}}{{{{{{{{{{{{{{}}}");

        }
        //   Log.i("latitude", "["+ latitude+"]}}}}}}}}}}}}}}}}}}}}}}}}}}}}{{{{{{{{{{{{{{}}}");
        lan= String.valueOf(mLastLocation.getLatitude());
        lon = String.valueOf(mLastLocation.getLongitude());
        latitude = Double.parseDouble(lan);
        longitude= Double.parseDouble(lon);



        latit=lan;
        lngtd=lon;
         Log.i("latitude4", "["+ latitude+"]}}}}}}}}}}}}}}}}}}}}}}}}}}}}{{{{{{{{{{{{{{}}}");
        Log.i("longtitude4", "["+ longitude+"]}}}}}}}}}}}}}}}}}}}}}}}}}}}}{{{{{{{{{{{{{{}}}");

        // Add a marker in Sydney and move the camera
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        float zoom_lvl = mMap.getCameraPosition().zoom;
        double dpPerdegree = 256.0*Math.pow(2, zoom_lvl)/170.0;

        double screen_height_30p = 0.0006*height/100.0;

        double degree_30p = screen_height_30p/dpPerdegree;


        LatLng currentPosition = new LatLng(latitude-degree_30p, longitude- degree_30p);

        parkSpace = new LatLng(latitude +0.001, longitude);
         latMark1 = latitude + 0.001;
         longMark1 = longitude;

        parkSpace2 = new LatLng(latitude -0.002, longitude+0.001);
        latMark2 = latitude -0.002;
        longMark2 = longitude+0.001;

         parkSpace3 = new LatLng(latitude -0.0014, longitude);
        latMark3 = latitude -0.0014;
         longMark3 = longitude;
/*
        Marker  MarkerMe = mMap.addMarker(new MarkerOptions()
                .position(currentPosition)
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.menu_car)));
*/


       // mMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) this);

        Marker parkMarker = mMap.addMarker(new MarkerOptions()
                .position(parkSpace)
                .title("parkSpace1")
                .snippet(cityName)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mapmarker_one)));

        Marker parkMarker2 = mMap.addMarker(new MarkerOptions()
                .position(parkSpace2)
                .title("parkSpace2")
                .snippet(cityName)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mapmarker_four)));
        Marker parkMarker3 = mMap.addMarker(new MarkerOptions()
                .position(parkSpace3)
                .title("parkSpace3")
                .snippet(cityName)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mapmarker)));



        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {

            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.i("CITYYYYYYYYYYYYYYYYY", "[" +marker.getId() + "]}}}}}}}}}}}}}}}}}}}}}}}}}}}}{{{{{{{{{{{{{{}}}");

              //  Toast.makeText(ctx, marker.getTitle(), Toast.LENGTH_SHORT).show();// display toast
                LatLng Mposition = marker.getPosition();
                double Mlatitude = Mposition.latitude;
                double Mlongtitude = Mposition.longitude;

                // Mlongitude = this.position.lng();
                Geocoder geocoder2 = new Geocoder(MapsActivity.this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder2.getFromLocation(Mlatitude, Mlongtitude, 1);
                    if (addresses.size() > 0) {
                        cityName = addresses.get(0).getAddressLine(0);
                        // you should also try with addresses.get(0).toSring();
                        Log.i("CITYYYYYYYYYYYYYYYYY", "[" + cityName + "]}}}}}}}}}}}}}}}}}}}}}}}}}}}}{{{{{{{{{{{{{{}}}");
                        tvdestinationMarker.setText(cityName);
                        tvdestinetionPosition.setText(cityName);
                    }
                    Log.i("CITYYYYYYYYYYYYYYYYY", "["+ addresses+"]}}}}}}}}}}}}}}}}}}}}}}}}}}}}{{{{{{{{{{{{{{}}}");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }

        });



        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation( latMark3, longMark3, 1);
            if (addresses.size() > 0) {
                cityName = addresses.get(0).getAddressLine(0);
                // you should also try with addresses.get(0).toSring();
                Log.i("CITYYYYYYYYYYYYYYYYY", "[" + cityName + "]}}}}}}}}}}}}}}}}}}}}}}}}}}}}{{{{{{{{{{{{{{}}}");

                tvdestinationMarker.setText(cityName);
                tvdestinetionPosition.setText(cityName);
            }
            Log.i("CITYYYYYYYYYYYYYYYYY", "["+ addresses+"]}}}}}}}}}}}}}}}}}}}}}}}}}}}}{{{{{{{{{{{{{{}}}");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Geocoder geocoder3 = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder3.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                cityName = addresses.get(0).getAddressLine(0);
                // you should also try with addresses.get(0).toSring();
                Log.i("CITYYYYYYYYYYYYYYYYY", "[" + cityName + "]}}}}}}}}}}}}}}}}}}}}}}}}}}}}{{{{{{{{{{{{{{}}}");
                tvcurrentPosition.setText(cityName);
            }
            Log.i("CITYYYYYYYYYYYYYYYYY", "["+ addresses+"]}}}}}}}}}}}}}}}}}}}}}}}}}}}}{{{{{{{{{{{{{{}}}");
        } catch (IOException e) {
            e.printStackTrace();
        }







        tvfoundSpot.setText("FOUND "+"3"+" FREE SPOTS");
        //tvcurrentPosition.setText("Hello");



        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(currentPosition)      // Sets the center of the map to Mountain View
                .zoom(16)                   // Sets the zoom
                .bearing(60)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onStart() {

        super.onStart();
        client.connect();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.oleg.spacepark/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://betom.com.twentyfourseven/http/host/path")
        );
       // findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }



    public void saveData(String name, String last_name, String email, String smallV, String mediumV, String bigV,String freeP,String payP) {
        getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
                .edit()
                .putString("NAME", name)
                .putString("LAST_NAME",last_name)
                .putString("EMAIL",email)
                .putString("FREE",freeP)
                .putString("PAYMENT",payP)

                .commit();
        getSharedPreferences(PREFS_NAME2,MODE_PRIVATE)
                .edit()
                .putString("SMALL", smallV)
                .putString("MEDIUM", mediumV)
                .putString("BIG", bigV)
                .commit();

    }
    public void loadData() {
        SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        savedname = pref.getString("NAME", DEFAULT);
        savedemail=pref.getString("EMAIL",DEFAULT);
        savedlastname=pref.getString("LAST_NAME",DEFAULT);
        savedfree=pref.getString("FREE",DEFAULT);
        savedpay=pref.getString("PAYMENT",DEFAULT);

        SharedPreferences pref2 = getSharedPreferences(PREFS_NAME2, MODE_PRIVATE);
        savedsmall = pref2.getString("SMALL", DEFAULT);
        savedmedium = pref2.getString("MEDIUM", DEFAULT);
        savedbig = pref2.getString("BIG", DEFAULT);

    }



}
