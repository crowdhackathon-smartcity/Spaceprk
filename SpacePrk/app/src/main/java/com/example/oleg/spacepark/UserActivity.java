package com.example.oleg.spacepark;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import android.support.v4.view.GravityCompat;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;


import android.view.View;

import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;


import java.util.Random;



public class UserActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    int small_V, medium_V, big_V, slotS, slotM, slotB, min = 0, max = 4;
    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 1;
    private static final int PERMISSION_ACCESS_FINE_LOCATION = 1;
    int small_Vehicles,big_Vehicles,medium_Vehicles;
    String small, medium, big, name, last_name, email, parkType;
    private long UPDATE_INTERVAL = 4 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 15000; /* 2 sec */
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String PREFS_NAME2 = "MyPrefsFile2";
    public static final String DEFAULT = "N/A";
    String savedname, savedsmall, savedlastname, savedemail, savedmedium, savedbig, savedpay, savedfree;
    Switch switchButton;
    Switch switchButton2;
    // Declaring DrawerLayout
    Context context;
    UserActivity ctx = this;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    RecyclerView recyclerView;
    String navTitles[];
    TypedArray navIcons;
    RecyclerView.Adapter recyclerViewAdapter;
    ActionBarDrawerToggle drawerToggle;
    View view;
    Random rand = new Random();
    String tag;
    public String LONGTITUDE,LATITUDE,TAG1,TAG2,TAG3,EMAIL,CITY,ADDRESS;
    boolean doubleBackToExitPressedOnce = false;
    ///end drawer
    ///location
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager mLocationManager;
    private LocationRequest mLocationRequest;
    ////end location
    Drawable firstslot,secondslot,thirdslot;
    int colorARGB = R.color.blueSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        setupToolbar();
        loadData();
        small_Vehicles= Integer.valueOf(savedsmall);
        medium_Vehicles= Integer.valueOf(savedmedium);
        big_Vehicles= Integer.valueOf(savedbig);
        EMAIL=savedemail;

        Button aboutUs = (Button) findViewById(R.id.aboutus);
        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.userback);
        final ImageView firstCar = (ImageView) findViewById(R.id.firstCar);
        final ImageView secondCar = (ImageView) findViewById(R.id.secondCar);
        final ImageView thirdCar = (ImageView) findViewById(R.id.thirdCar);


        small = savedsmall;
        medium = savedmedium;
        big = savedbig;
        name = savedname;
        last_name = savedlastname;
        email = savedemail;

        small_V = Integer.valueOf(small);
        medium_V = Integer.valueOf(medium);
        big_V = Integer.valueOf(big);
        slotS = small_V;
        slotM = medium_V;
        slotB = big_V;

        if (slotS != 0) {
            firstCar.setBackgroundResource(R.drawable.small_car);
            TAG1="small";
            firstslot = getResources().getDrawable(R.drawable.small_car);

            slotS = 0;
        } else if (slotM != 0) {
            firstCar.setBackgroundResource(R.drawable.medium_car);
            firstslot = getResources().getDrawable(R.drawable.medium_car);
            TAG1="medium";
            slotM = 0;
        } else {
            firstCar.setBackgroundResource(R.drawable.big_car);
            firstslot = getResources().getDrawable(R.drawable.big_car);
            TAG1="big";
            slotB = 0;
        }


        if (slotS != 0) {
            secondCar.setBackgroundResource(R.drawable.small_car);
            secondslot = getResources().getDrawable(R.drawable.small_car);
            TAG2="small";
            slotS = 0;
        } else if (slotM != 0) {
            secondCar.setBackgroundResource(R.drawable.medium_car);
            secondslot = getResources().getDrawable(R.drawable.medium_car);
            TAG2="medium";
            slotM = 0;
        } else if (slotB != 0) {
            secondCar.setBackgroundResource(R.drawable.big_car);
            secondslot = getResources().getDrawable(R.drawable.big_car);

            TAG2="big";
            slotB = 0;
        } else {

            secondCar.setBackgroundResource(R.drawable.plus_button);
            secondslot = getResources().getDrawable(R.drawable.plus_button);

        }

        if (slotS != 0) {
            thirdCar.setBackgroundResource(R.drawable.small_car);
            thirdslot = getResources().getDrawable(R.drawable.small_car);

            TAG3="small";

            slotS = 0;
        } else if (slotM != 0) {
            thirdCar.setBackgroundResource(R.drawable.medium_car);
            thirdslot = getResources().getDrawable(R.drawable.medium_car);
            TAG3="medium";
            slotM = 0;
        } else if (slotB != 0) {
            thirdCar.setBackgroundResource(R.drawable.big_car);
            thirdslot = getResources().getDrawable(R.drawable.big_car);

            TAG3="big";
            slotB = 0;
        } else {
            thirdCar.setBackgroundResource(R.drawable.plus_button);
            thirdslot = getResources().getDrawable(R.drawable.plus_button);


        }




        ///Request permissio for API 24 and higher///////////
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSION_ACCESS_COARSE_LOCATION);
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_ACCESS_FINE_LOCATION);
        }

        /////End ask permission/////////////////////////




///LOCATION
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

      //  mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        mLocationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

        checkLocation();

//LOCATION



        Log.e("Free: ", savedfree + "");
        Log.e("pay: ", savedpay + "");
        Log.e("name: ", name + "");

        Log.e("lastname: ", last_name + "");

        Log.e("medium: ", medium + "");


        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // layout.setBackground(getResources().getDrawable(R.drawable.mercedes));

                int n = rand.nextInt((max - min) + 1) + min;
                ;
                Log.e("RANDOM: ", n + "");
                if (n == 0) {

                    layout.setBackground(getResources().getDrawable(R.drawable.ww));
                } else if (n == 1) {

                    layout.setBackground(getResources().getDrawable(R.drawable.mercedes));
                } else if (n == 2) {

                    layout.setBackground(getResources().getDrawable(R.drawable.porshe));
                } else if (n == 3) {

                    layout.setBackground(getResources().getDrawable(R.drawable.bmw));
                } else if (n == 4) {

                    layout.setBackground(getResources().getDrawable(R.drawable.town));
                }

            }
        });




        firstCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstslot.setColorFilter(getResources().getColor(R.color.bluelight), PorterDuff.Mode.MULTIPLY );
                firstCar.setImageDrawable(firstslot);
                Intent intent = new Intent(UserActivity.this, MapsActivity.class);
                intent.putExtra("LATITUDE", LATITUDE);
                intent.putExtra("LONGTITUDE", LONGTITUDE);
                intent.putExtra("TAG1", TAG1);
                intent.putExtra("TAG2", TAG2);
                intent.putExtra("TAG3", TAG3);
                intent.putExtra("CITY", CITY);

                startActivity(intent);


            }
        });
        secondCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondslot.setColorFilter(getResources().getColor(R.color.bluelight), PorterDuff.Mode.MULTIPLY );
                secondCar.setImageDrawable(secondslot);
                Intent intent = new Intent(UserActivity.this, MapsActivity.class);
                intent.putExtra("LATITUDE", LATITUDE);
                intent.putExtra("LONGTITUDE", LONGTITUDE);
                intent.putExtra("TAG1", TAG1);
                intent.putExtra("TAG2", TAG2);
                intent.putExtra("TAG3", TAG3);
                intent.putExtra("CITY", CITY);

                startActivity(intent);




            }
        });
        thirdCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thirdCar.getDrawable()!= getResources().getDrawable(R.drawable.plus_button)) {
                    thirdslot.setColorFilter(getResources().getColor(R.color.bluelight), PorterDuff.Mode.MULTIPLY);
                    thirdCar.setImageDrawable(secondslot);
                    showDialog(UserActivity.this, "Pick your Car");
                }else{
                    thirdslot.setColorFilter(getResources().getColor(R.color.bluelight), PorterDuff.Mode.MULTIPLY );
                    firstCar.setImageDrawable(thirdslot);
                    Intent intent = new Intent(UserActivity.this, MapsActivity.class);
                    intent.putExtra("LATITUDE", LATITUDE);
                    intent.putExtra("LONGTITUDE", LONGTITUDE);
                    intent.putExtra("TAG1", TAG1);
                    intent.putExtra("TAG2", TAG2);
                    intent.putExtra("TAG3", TAG3);
                    intent.putExtra("CITY", CITY);

                    startActivity(intent);
                }



            }
        });


        ///----------------------drawer
        //Initialize Views
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        //Setup Titles and Icons of Navigation Drawer
        navTitles = getResources().getStringArray(R.array.navDrawerItems);
        navIcons = getResources().obtainTypedArray(R.array.navDrawerIcons);

        //DIVIDERS
        recyclerView.addItemDecoration(new com.example.oleg.spacepark.DividerItemDecoration(getResources().getDrawable(R.drawable.line_seperator)));
        recyclerViewAdapter = new RecyclerViewAdapter(navTitles, navIcons, this);
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
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View homeView = inflater.inflate(R.layout.header, null);
        TextView header = (TextView) homeView.findViewById(R.id.username);
        final View ameavView= inflater.inflate(R.layout.tool_bar, null);
        final ImageView amea=(ImageView) ameavView.findViewById(R.id.amea) ;





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

//---------------------end drawer


    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
           // finishAndRemoveTask ();
            this.finishAffinity();


        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 1500);
    }



    void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
       // View cView = getLayoutInflater().inflate(R.layout.tool_bar, null);


    }
    public void ameaclick(View v) {
        if (v.getId() == R.id.amea) {

            Intent intent = new Intent(UserActivity.this, MapsActivity.class);
            intent.putExtra("LATITUDE", LATITUDE);
            intent.putExtra("LONGTITUDE", LONGTITUDE);



            startActivity(intent);
        }
    }


    void setupDrawerToggle() {
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        drawerToggle.syncState();
    }


    public void loadData() {
        SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        savedname = pref.getString("NAME", DEFAULT);
        savedemail = pref.getString("EMAIL", DEFAULT);
        savedlastname = pref.getString("LAST_NAME", DEFAULT);
        SharedPreferences pref2 = getSharedPreferences(PREFS_NAME2, MODE_PRIVATE);
        savedsmall = pref2.getString("SMALL", DEFAULT);
        savedmedium = pref2.getString("MEDIUM", DEFAULT);
        savedbig = pref2.getString("BIG", DEFAULT);

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {


        startLocationUpdates();

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
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLocation == null) {
            startLocationUpdates();
        }
        if (mLocation != null) {

            // mLatitudeTextView.setText(String.valueOf(mLocation.getLatitude()));
            //mLongitudeTextView.setText(String.valueOf(mLocation.getLongitude()));
        } else {
            Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
        }








    }

    @Override
    public void onConnectionSuspended(int i) {
      //  Log.i("SUP", "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
      //  Log.i("SUP2", "Connection failed. Error: " + connectionResult.getErrorCode());

    }



    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates

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
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
        Log.d("reque", "--->>>>");
    }
    @Override
    public void onLocationChanged(Location location) {

        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());

       // Log.i("SUP", msg);
       // Log.i("SUP", "Connection Suspended");

        // mLatitudeTextView.setText(String.valueOf(location.getLatitude()));
       // mLongitudeTextView.setText(String.valueOf(location.getLongitude() ));
        ///Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        // You can now create a LatLng Object for use with maps
        LATITUDE=String.valueOf(location.getLatitude());
        LONGTITUDE=String.valueOf(location.getLongitude());
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        double latitude = Double.parseDouble(LATITUDE);
        double longitude= Double.parseDouble(LONGTITUDE);
        Log.i("CITYYYYYYYYYYYYYYYYY", "["+ latitude+"]}}}}}}}}}}}}}}}}}}}}}}}}}}}}{{{{{{{{{{{{{{}}}");

      /*  Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                CITY = addresses.get(0).getLocality();
                ADDRESS = addresses.get(1).getLocality();
                String sss = addresses.get(2).getLocality();
                Log.i("CITYYYYYYYYYYYYYYYYY", "["+ sss+"]}}}}}}}}}}}}}}}}}}}}}}}}}}}}{{{{{{{{{{{{{{}}}");

            }
            Log.i("CITYYYYYYYYYYYYYYYYY", "["+ CITY+"]}}}}}}}}}}}}}}}}}}}}}}}}}}}}{{{{{{{{{{{{{{}}}");
            Log.i("ADDRESS", "["+ ADDRESS+"]}}}}}}}}}}}}}}}}}}}}}}}}}}}}{{{{{{{{{{{{{{}}}");


        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    private boolean checkLocation() {
        if(!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }



    public void showDialog(Activity activity, String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.cars_size);
        // final RelativeLayout timoni = (RelativeLayout) findViewById(R.id.relative);
        //final LinearLayout timoni = (LinearLayout) findViewById(R.id.linear);

        final Button small = (Button) dialog.findViewById(R.id.small);
        Button medium = (Button) dialog.findViewById(R.id.medium);

        Button big = (Button) dialog.findViewById(R.id.big);

        small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handler handler = new Handler();
                //handler.postDelayed(new Runnable() {
                // public void run() {

                // timoni.setBackgroundResource(R.drawable.small);
                //  }
                // }, 5000);
                // timoni.setImageResource(R.drawable.pharmacy2);
                small_Vehicles=small_Vehicles+1;
                   /* Intent intent = new Intent(MainActivity.this, UserActivity.class);
                    intent.putExtra("vehicles", total_vehicles);
                    intent.putExtra("size", car_size);

                    startActivity(intent);
                    */
                Toast.makeText(ctx, "Πατησες small", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                saveData(String.valueOf(small_Vehicles) ,String.valueOf(medium_Vehicles),String.valueOf(big_Vehicles));
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ctx, "Πατησες medium", Toast.LENGTH_SHORT).show();
                medium_Vehicles=medium_Vehicles+1;
                dialog.dismiss();
                saveData(String.valueOf(small_Vehicles) ,String.valueOf(medium_Vehicles),String.valueOf(big_Vehicles));
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        big.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(ctx, "Πατησες big", Toast.LENGTH_SHORT).show();
                big_Vehicles=big_Vehicles+1;
                dialog.dismiss();
                saveData(String.valueOf(small_Vehicles) ,String.valueOf(medium_Vehicles),String.valueOf(big_Vehicles));
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        dialog.show();


    }
    public void saveData(String smallV, String mediumV, String bigV) {
        getSharedPreferences(PREFS_NAME2,MODE_PRIVATE)
                .edit()
                .putString("SMALL", smallV)
                .putString("MEDIUM", mediumV)
                .putString("BIG", bigV)
                .commit();

    }
}
