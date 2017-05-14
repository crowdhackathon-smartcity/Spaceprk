package com.example.oleg.spacepark;

/**
 * Created by Oleg on 13-MAY-17.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;
//import static com.google.android.gms.analytics.internal.zzy.v;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>  {
    String[] titles;
    TypedArray icons;
    Context context;
    RecyclerViewAdapter ctsx=this;
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String PREFS_NAME2 = "MyPrefsFile2";
    public String freePark,payPark;
    public static final String DEFAULT="N/A";
    String savedname, savedsmall,savedlastname,savedemail, savedmedium, savedbig,savedpay,savedfree;
    // The default constructor to receive titles,icons and context from MainActivity.
   RecyclerViewAdapter ctx=this;
    RecyclerViewAdapter(String[] titles , TypedArray icons , Context context){

        this.titles = titles;
        this.icons = icons;
        this.context = context;
    }

    /**
     *Its a inner class to RecyclerViewAdapter Class.
     *This ViewHolder class implements View.OnClickListener to handle click events.
     *If the itemType==1 ; it implies that the view is a single row_item with TextView and ImageView.
     *This ViewHolder describes an item view with respect to its place within the RecyclerView.
     *For every item there is a ViewHolder associated with it .
     */

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        TextView navTitle;
        TextView userName;
        ImageView navIcon;
        Context context;
        Intent intent;
        Switch switchButton;
        Switch switchButton2;


        public ViewHolder(View drawerItem , int itemType , final Context context){

            super(drawerItem);
            this.intent = intent;
            this.context = context;
            loadData();



            drawerItem.setOnClickListener(this);
            if(itemType==1){
                navTitle = (TextView) itemView.findViewById(R.id.tv_NavTitle);
                navIcon = (ImageView) itemView.findViewById(R.id.iv_NavIcon);//εικονιδιο humburger
                switchButton = (Switch) itemView.findViewById(R.id.switch1);
                switchButton2 = (Switch) itemView.findViewById(R.id.switch2);


                switchButton.setChecked(true);


                loadData();

                /*
                Log.e("Free: ", savedfree + "");
                Log.e("pay: ", savedpay + "");
                Log.e("name: ", savedname + "");

                Log.e("lastname: ", savedlastname + "");

                Log.e("medium: ", savedmedium + "");
*/
                if (savedfree.equals("no")){
                    switchButton.setChecked(false);
                }else if (savedfree.equals("free")){
                    switchButton.setChecked(true);
                }


                if (savedfree.equals("no")){
                    switchButton2.setChecked(false);
                }else if (savedfree.equals("pay")){
                    switchButton2.setChecked(true);

                }

                switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked==true) {
                            Log.e("response1111111: ", "asads" + "");
                            freePark="free";
                            saveData(savedname,savedlastname,savedemail,savedsmall,savedmedium,savedbig,freePark,savedpay);
                            navTitle.setTextColor(Color.parseColor("#FF7DD8E6"));
                            navIcon.setImageResource(R.drawable.menu_freepres);


                            Toast.makeText(context, "Free parking enabled", Toast.LENGTH_SHORT).show();

                        }else{
                            Log.e("response1111111: ", "closed" + "");
                            freePark="no";
                            saveData(savedname,savedlastname,savedemail,savedsmall,savedmedium,savedbig,freePark,savedpay);
                            navTitle.setTextColor(Color.parseColor("#808080"));
                            navIcon.setImageResource(R.drawable.menu_freep);
                            Toast.makeText(context, "Free parking disabled", Toast.LENGTH_SHORT).show();
                        }
                        // true if the switch is in the On position
                    }
                });
                switchButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked==true) {
                            Log.e("response2222222: ", "asads" + "");
                            payPark="pay";
                            navTitle.setTextColor(Color.parseColor("#FF7DD8E6"));
                            navIcon.setImageResource(R.drawable.menu_payment_pressed);
                            saveData(savedname,savedlastname,savedemail,savedsmall,savedmedium,savedbig,savedfree,payPark);
                            loadData();
                            Log.e("Free-------------: ", savedfree + "");
                            Log.e("pay-----------------: ", savedpay + "");
                            Log.e("name-----------------: ", savedname + "");

                            Log.e("lastname-------------: ", savedlastname + "");

                            Log.e("medium--------------: ", savedmedium + "");
                            Toast.makeText(context, "Payment parking enabled", Toast.LENGTH_SHORT).show();
                        }else{
                            Log.e("response2222222: ", "closed" + "");
                            payPark="no";
                            saveData(savedname,savedlastname,savedemail,savedsmall,savedmedium,savedbig,savedfree,payPark);
                            navTitle.setTextColor(Color.parseColor("#808080"));
                            navIcon.setImageResource(R.drawable.menu_payment);

                            Toast.makeText(context, "Payment parking disabled", Toast.LENGTH_SHORT).show();
                        }
                        // true if the switch is in the On position
                    }
                });
            }
            else if (itemType==0){

                userName = (TextView) itemView.findViewById(R.id.username);
                userName.setText(savedname);
            }
        }





        /**
         *This defines onClick for every item with respect to its position.
         */
        //facebook
        //public String FACEBOOK_URL = "https://www.facebook.com/%CE%A4%CE%BF-24%CF%89%CF%81%CE%BF-1790507544532623/";
       // public String FACEBOOK_PAGE_ID = "1790507544532623";

        //method to get the right URL to use in the intent
     /*   public String getFacebookPageURL(Context context) {
            PackageManager packageManager = context.getPackageManager();
            try {
                int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;//Checks if FB is even installed.
                if (versionCode >= 3002850) { //newer versions of fb app
                    return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
                } else { //older versions of fb app
                    return "fb://page/" + FACEBOOK_PAGE_ID;
                }
            } catch (PackageManager.NameNotFoundException e) {
                return FACEBOOK_URL; //normal web url
            }
        }
        */
        //end facebook

        @Override
        public void onClick(View v) {

            UserActivity mainActivity = (UserActivity) context;

            switch (getPosition()){
                case 1:
//My Vehicles


                    break;
                case 2:
//Free Parking
                    // Intent intent2 = new Intent(v.getContext(), LikeUs.class);
                    //context.startActivity(intent2);
                    break;
//Payment
                case 3:


                    break;
                case 4:
//Mycard
                    Toast.makeText(v.getContext(), "My Card", Toast.LENGTH_SHORT).show();



                    break;


//Help
                case 5:

                    Toast.makeText(v.getContext(), "Help", Toast.LENGTH_SHORT).show();

                    break;

                case 6:
//Settings
                    Toast.makeText(v.getContext(), "Settings", Toast.LENGTH_SHORT).show();

                    break;
                case 7:
//Parker

                    Intent intentParker = new Intent(v.getContext(), Parker.class);
                    intentParker.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intentParker.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intentParker);


                    Toast.makeText(v.getContext(), "Parker", Toast.LENGTH_SHORT).show();

                    break;
            }
        }
    }

    /**
     *This is called�every time�when we need a new ViewHolder and a new ViewHolder is required for every item in RecyclerView.
     *Then this ViewHolder is passed to onBindViewHolder to display items.
     */

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(viewType==1){
            View itemLayout =   layoutInflater.inflate(R.layout.drawer_item_layout,null);
            return new ViewHolder(itemLayout,viewType,context);
        }
        else if (viewType==0) {
            View itemHeader = layoutInflater.inflate(R.layout.header,null);
            return new ViewHolder(itemHeader,viewType,context);
        }

        return null;
    }

    /**
     *This method is called by RecyclerView.Adapter to display the data at the specified position.�
     *This method should update the contents of the itemView to reflect the item at the given position.
     *So here , if position!=0 it implies its a row_item and we set the title and icon of the view.
     */

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {

        if(position!=0){

            holder.navTitle.setText(titles[position - 1]);
            holder.navIcon.setImageResource(icons.getResourceId(position-1,-1));
            if(position==2){
                holder.switchButton.setVisibility(View.VISIBLE);
                holder.navTitle.setTextColor(Color.parseColor("#FF7DD8E6"));
                holder.navIcon.setImageResource(R.drawable.menu_freepres);

            }
            if(position==3){
                holder.switchButton2.setVisibility(View.VISIBLE);


            }

        }

    }

    /**
     *It returns the total no. of items . We +1 count to include the header view.
     *So , it the total count is 5 , the method returns 6.
     *This 6 implies that there are 5 row_items and 1 header view with header at position zero.
     */

    @Override
    public int getItemCount() {
        return titles.length+1;
    }


    /**
     *This methods returns 0 if the position of the item is '0'.
     *If the position is zero its a header view and if its anything else
     *its a row_item with a title and icon.
     */

    @Override
    public int getItemViewType(int position) {
        if(position==0)return 0;
        else return 1;
    }


    public void saveData(String name, String last_name, String email, String smallV, String mediumV, String bigV,String freeP,String payP) {
        context.getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
                .edit()
                .putString("NAME", name)
                .putString("LAST_NAME",last_name)
                .putString("EMAIL",email)
                .putString("FREE",freeP)
                .putString("PAYMENT",payP)

                .commit();
        context.getSharedPreferences(PREFS_NAME2,MODE_PRIVATE)
                .edit()
                .putString("SMALL", smallV)
                .putString("MEDIUM", mediumV)
                .putString("BIG", bigV)
                .commit();

    }

    public void loadData() {
        SharedPreferences pref = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        savedname = pref.getString("NAME", DEFAULT);
        savedemail=pref.getString("EMAIL",DEFAULT);
        savedlastname=pref.getString("LAST_NAME",DEFAULT);
        savedfree=pref.getString("FREE",DEFAULT);
        savedpay=pref.getString("PAYMENT",DEFAULT);

        SharedPreferences pref2 = context.getSharedPreferences(PREFS_NAME2, MODE_PRIVATE);
        savedsmall = pref2.getString("SMALL", DEFAULT);
        savedmedium = pref2.getString("MEDIUM", DEFAULT);
        savedbig = pref2.getString("BIG", DEFAULT);

    }

}


