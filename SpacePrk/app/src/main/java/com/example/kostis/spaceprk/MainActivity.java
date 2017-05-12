package com.example.kostis.spaceprk;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.intertech.customviews.MotoSelector;
import com.intertech.customviews.ValueSelector;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    MainActivity ctx=this;
    User user;
    int small_Vehicles=0,big_Vehicles=0,medium_Vehicles=0;
    ViewDialog alert;
    ValueSelector valueSelector;
    MotoSelector motoSelector;
    public int carNum=0 ,motoNum=0,temp=0;
    public String carN,motoN;
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String PREFS_NAME2 = "MyPrefsFile2";
    public static final String DEFAULT="N/A";
    String savedname, savedsmall,savedlastname,savedemail, savedmedium, savedbig,savedpay,savedfree;
    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 1;
    private static final int PERMISSION_ACCESS_FINE_LOCATION = 1;
    public String ename,eemail,epassword;


    EditText etName,etPassword,etMail;
   //-------------------On Create
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            //check if the user loged in before and saved prefereces.................................
        savedpay="no";
        savedfree="free";
            //loadData();
        //ελεγχος αν έχει χρησιμοποιηση ξανα ο χρηστης και αποθυκευτικαν τα στοιχεια του

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


         alert = new ViewDialog();

         valueSelector = (ValueSelector) findViewById(R.id.valueSelector);
        valueSelector.setMinValue(0);
        valueSelector.setMaxValue(10);
         motoSelector = (MotoSelector) findViewById(R.id.motoSelector);
        motoSelector.setMinValue(0);
        motoSelector.setMaxValue(10);
      //get a reference to the textview on the log.xml file.

        ImageView email= (ImageView)findViewById(R.id.email_image);



        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(ctx, "Πατησες medium", Toast.LENGTH_SHORT).show();

                if(small_Vehicles!=0||medium_Vehicles!=0||big_Vehicles!=0){
                alert.EmailDialog(MainActivity.this, "Sign up");
                    Log.e("response111: ", savedname+ "");
                    Log.e("response111: ", savedsmall+ "");


                }else{
                    dialogBox2();
                }
            }
        });


       ////////////////// ///FB LOGin//////////////////////////////////////////////

        LoginButton fbloginButton = (LoginButton) findViewById(R.id.login_button);
        fbloginButton.setBackgroundResource(R.drawable.login);
       // fbloginButton.setReadPermissions("public_profile", "email","user_friends");
        fbloginButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        fbloginButton.setLoginBehavior(LoginBehavior.NATIVE_WITH_FALLBACK);

        callbackManager = CallbackManager.Factory.create();
        //fbloginButton.registerCallback(callbackManager, mCallBack);

        fbloginButton.registerCallback(callbackManager, null);

        // fbloginButton.registerCallback(callbackManager,null);

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {

                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {

                                        Log.e("response111: ", response + "");
                                        try {

                                            user = new User(); ///o user me atrributes name,lastname,fbID,email

                                            String mystring =object.getString("name").toString();
                                            String firstName = mystring.split(" ",2)[0].replaceAll("'", " ");//first fb name
                                           // Log.i("FACEBOOK NAME 11isssss", "[" +  firstName+  "]++++++++++++++");
                                            String lastName = mystring.split(" ",2)[1].replaceAll("'", " ");// second fb name

                                            user.NAME =firstName.replaceAll("'", " ");
                                            user.LAST_NAME=lastName.replaceAll("'", " ");
                                           // user.facebookID = object.getString("id").toString();
                                            user.EMAIL = object.getString("email").toString();
                                          //  user.GENDER = object.getString("gender").toString();

                        if(small_Vehicles!=0||medium_Vehicles!=0||big_Vehicles!=0){

                                            String sC= String.valueOf(small_Vehicles);
                                            String mC= String.valueOf(medium_Vehicles);
                                            String bC= String.valueOf(big_Vehicles);
                                            saveData(user.NAME,user.LAST_NAME,user.EMAIL,sC,mC,bC,savedfree,savedpay);
                                            BackGround b = new BackGround();
                                            b.execute(user.NAME, user.LAST_NAME, user.EMAIL);
                        }else{
                            dialogBox2();
                        }
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                        //Toast.makeText(RegisterActivity.this,"welcome "+user.name+"", Toast.LENGTH_SHORT).show();
                                        //finish();
                                    }

                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender, birthday");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }
                    @Override
                    public void onCancel() {
                        // App code
                    }
                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

//.........................................END LOG IN VIA FACEBOOK.......................................

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // Your code here

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                carN ();
            }
        }, 400);
        return super.dispatchTouchEvent(ev);
    }

    public void register_register(View v) {
        //name = etName.getText().toString().replaceAll("'", " ");
        //last_name = etLastName.getText().toString();
        //email = etMail.getText().toString();
       // password = etPasswordLog.getText().toString();
        //rep_password = etConfPassword.getText().toString();
        BackGround b = new BackGround();

        /*
        if (name !=null  && !name.isEmpty()) {
            // if (last_name != null && !last_name.isEmpty()) {
            if (isEmailValid(email)) {   ///καλει την μέθοδο isEmailValid και αν true τοτε εκτελει την μεθοδο b
                if (password !=null  && !password.isEmpty()) {
                    if (password.equals(rep_password)) {

                        NAME=name.replaceAll("'", " ");
                        LAST_NAME=last_name.replaceAll("'", " ");
                        EMAIL=email;
                        PASSWORD=password;
                        b.execute(NAME, LAST_NAME, EMAIL, PASSWORD, rep_password);
                    } else {

                        Toast.makeText(ctx, "Προσοχή Οι κωδικοί δεν ταυτίζονται!  ", Toast.LENGTH_LONG).show();
                    }
                }else {

                    Toast.makeText(ctx, "Έισάγεται τον κωδικό σας", Toast.LENGTH_LONG).show();
                }

            } else {
                dialogBox(); //καλει την μεθοδο dialogBox εφόσων η μέθοδος is EmailValid είναι false

            }
        } else {
            Toast.makeText(ctx, "Συμπληρώστε το Όνομα Χρήστη σας", Toast.LENGTH_LONG).show();

        }
*/
    }

    class BackGround extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String name = params[0];
            String last_name= params[1];
            String email = params[2];
            String data = "";
            int tmp;
            try {

                HttpURLConnection urlConnection = null;

                JSONObject object = null;
                InputStream inStream = null;
                URL url = new URL("aaaaaaaaaaaa.php");

                try {
                    url = new URL(url.toString());
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setDoOutput(true);
                    urlConnection.setDoInput(true);
                    urlConnection.connect();
                    inStream = urlConnection.getInputStream();
                    BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
                    String temp, response = "";
                    while ((temp = bReader.readLine()) != null) {
                        response += temp;
                    }
                    object = (JSONObject) new JSONTokener(response).nextValue();
                    // Log.i("Json  isssss", "[" + object + "]}}}}}}}}}}}}}}}}}}}}}}}}}}}}{{{{{{{{{{{{{{}}}");
                    JSONArray resultTable = null;

                    resultTable = object.getJSONArray("results of php file");
                    for (int i = 0; i < resultTable.length(); i++) {

                        // Storing  JSON item in a Variable

                        JSONObject place = resultTable.getJSONObject(i);
                        //PER_NAME = place.getString("name");
                      //  ADDRESS = place.getString("vicinity");
                        //LAT = place.getJSONObject("geometry").getJSONObject("location").getString("lat");
                       // LNG = place.getJSONObject("geometry").getJSONObject("location").getString("lng");
                        //ID = place.getString("place_id");
                    }
/*
                    Intent intent = new Intent(MainActivity.this, UserActivity.class);
                    intent.putExtra("vehicles", total_vehicles);
                    intent.putExtra("size", car_size);
                    startActivity(intent);
                    */
                } catch (Exception e) {

                } finally {
                    if (inStream != null) {
                        try {
                            // this will close the bReader as well
                            inStream.close();

                        } catch (IOException ignored) {
                        }
                    }

                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

            }

            catch (MalformedURLException e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }

            return data;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    ///-------------------------------------------------------------Dialog boxes
        public class ViewDialog {

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

                        small_Vehicles=small_Vehicles+1;
                        Toast.makeText(ctx, "Πατησες small", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                medium.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ctx, "Πατησες medium", Toast.LENGTH_SHORT).show();
                        medium_Vehicles=medium_Vehicles+1;
                        dialog.dismiss();
                    }
                });
                big.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ctx, "Πατησες big", Toast.LENGTH_SHORT).show();
                        big_Vehicles=big_Vehicles+1;
                        dialog.dismiss();
                    }
                });

                dialog.show();


            }


            public void EmailDialog(Activity activity, String msg){
                final Dialog emailDialog = new Dialog(activity);
                emailDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                emailDialog.setCancelable(false);
                emailDialog.setContentView(R.layout.email_box);
                // final RelativeLayout timoni = (RelativeLayout) findViewById(R.id.relative);
                //final LinearLayout timoni = (LinearLayout) findViewById(R.id.linear);

                final Button sign_up = (Button) emailDialog.findViewById(R.id.sign_up);

                final ImageView sign_in = (ImageView) emailDialog.findViewById(R.id.sign_in);
                final ImageView x_button = (ImageView) emailDialog.findViewById(R.id.x_image);
                etName = (EditText) emailDialog.findViewById(R.id.etUserName);
                etPassword = (EditText) emailDialog.findViewById(R.id.etPasswordLog);
                etMail = (EditText) emailDialog.findViewById(R.id.etMail);


                sign_up.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ename = etName.getText().toString().replaceAll("'", " ");
                        eemail = etMail.getText().toString();
                        epassword = etPassword.getText().toString();
                        String sC2= String.valueOf(small_Vehicles);
                        String mC2= String.valueOf(medium_Vehicles);
                        String bC2= String.valueOf(big_Vehicles);

                        if (ename !=null  && !ename.isEmpty()) {
                            // if (last_name != null && !last_name.isEmpty()) {
                            if (isEmailValid(eemail)) {   ///καλει την μέθοδο isEmailValid και αν true τοτε εκτελει την μεθοδο b
                                if (epassword !=null  && !epassword.isEmpty()) {
                                    saveData(ename,eemail,epassword,sC2,mC2,bC2,savedfree,savedpay);
                                    Intent intent = new Intent(MainActivity.this,UserActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    MainActivity.this.startActivity(intent);




                                    emailDialog.dismiss();
                                }else {

                                    Toast.makeText(ctx, "Enter your password", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(ctx, "This email is invalid, please enter a new one", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            Toast.makeText(ctx, "Enter your Username", Toast.LENGTH_SHORT).show();

                        }



                        Toast.makeText(ctx, "Welcome to SPACERPK  "+ename, Toast.LENGTH_SHORT).show();
                        //Intent intent = new Intent(MainActivity.this,UserActivity.class);

                        //pername ta stoixeia to xrhsth sthn main activity
                     // MainActivity.this.startActivity(intent);
                       // overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );

                    }
                });

                sign_in.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(ctx, "Πατησες medium", Toast.LENGTH_SHORT).show();

                        emailDialog.dismiss();
                    }
                });

                x_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(ctx, "Πατησες medium", Toast.LENGTH_SHORT).show();

                        emailDialog.dismiss();
                    }
                });

                emailDialog.show();


            }


    }

    //----------------------------------end of Dialog boxes
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    ///---------------------------------κλάση που δημιουργείτε ο Χρήστης
    public class User {

        public String NAME;
        public String LAST_NAME;
        public String EMAIL;

    }


    ////μεθοδος για τον υπολογισμο των συνολικων αυτοκινητων που θα επιλέξει ο χρήστης
    public void carN () {

         carN = valueSelector.getValue();
         motoN = motoSelector.getValue();

       String str = carN.replaceAll("\\D+","");
        String strM = motoN.replaceAll("\\D+","");


        carNum=Integer.valueOf(str);
        motoNum=Integer.valueOf(strM);

        if (carNum!=temp && carNum>temp){
            alert.showDialog(MainActivity.this, "Pick your Car");
            temp=carNum;
        }else{
            temp=carNum;

        }


        Log.e("CARnum: ", carNum + "");
        Log.e("response111: ", motoN + "");


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


    public void dialogBox2()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setIcon(R.drawable.ic_locked);
        alertDialogBuilder.setTitle("Login Failed");
        alertDialogBuilder.setMessage("You have to choose your type of Vehicle");
        alertDialogBuilder.setNegativeButton("Try Again",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        LoginManager.getInstance().logOut();

                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.colorBackround);

    }



    public void dialogBox()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setIcon(R.drawable.ic_alert);
        alertDialogBuilder.setTitle("Προσοχή!");
        alertDialogBuilder.setMessage(" Το e-mail δεν είναι έγκυρο!");
        alertDialogBuilder.setNegativeButton("Επαναληψη e-mail",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.white);
    }


}
