package com.example.oleg.spacepark;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Parker extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parker);
    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Parker.this, UserActivity.class);

        startActivity(intent);
    }
}
