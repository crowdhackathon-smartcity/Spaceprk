package com.example.oleg.spacepark;

import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;


public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
		
    }

}
