package com.example.oleg.spacepark;

import java.util.ArrayList;


import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aigestudio.wheelpicker.WheelPicker;
import com.aigestudio.wheelpicker.WheelPickerDateData;

import java.util.ArrayList;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

public class CarSize extends AppCompatActivity  implements WheelPicker.OnItemSelectedListener, View.OnClickListener,WheelPickerDateData.OnItemSelectedListener {

    private FeatureCoverFlow coverFlow;
    private CoverFlowAdapter adapter;
    private ArrayList<Game> games;

    private WheelPicker wheelLeft;

    private WheelPickerDateData wheelRight;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_size);
        coverFlow = (FeatureCoverFlow) findViewById(R.id.coverflow);

        ////carousel layout
        settingDummyData();
        adapter = new CoverFlowAdapter(this, games);
        coverFlow.setAdapter(adapter);
        coverFlow.setOnScrollPositionListener(onScrollListener());



        coverFlow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                Toast.makeText(CarSize.this, "SELECTING "+arg2, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                Toast.makeText(CarSize.this, "NOTHING", Toast.LENGTH_SHORT).show();
            }
        });





//weel layouts
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        wheelLeft = (WheelPicker) findViewById(R.id.main_wheel_left);
        wheelLeft.setOnItemSelectedListener(this);

        wheelRight = (WheelPickerDateData) findViewById(R.id.main_wheel_right);
        wheelRight.setOnItemSelectedListener((WheelPickerDateData.OnItemSelectedListener) this);




    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(CarSize.this, UserActivity.class);

        startActivity(intent);
    }

    private FeatureCoverFlow.OnScrollPositionListener onScrollListener() {
        return new FeatureCoverFlow.OnScrollPositionListener() {
            @Override
            public void onScrolledToPosition(int position) {
                Log.v("MainActiivty", "position: " + position);
            }

            @Override
            public void onScrolling() {
                Log.i("MainActivity", "scrolling");
            }
        };
    }

    private void settingDummyData() {
        games = new ArrayList<>();
        //  games.add(new Game(R., "Assassin Creed 3"));

        games.add(new Game("A"));
        games.add(new Game("B"));
        games.add(new Game("C"));
        games.add(new Game("D"));
        games.add(new Game("E"));
        games.add(new Game("F"));
        games.add(new Game("G"));
        games.add(new Game("H"));
        games.add(new Game("I"));
        games.add(new Game("J"));
        games.add(new Game("K"));
        games.add(new Game("L"));
        games.add(new Game("M"));
        games.add(new Game("N"));
        games.add(new Game("O"));
        games.add(new Game("P"));
        games.add(new Game("Q"));
        games.add(new Game("R"));
        games.add(new Game("S"));
        games.add(new Game("T"));
        games.add(new Game("U"));
        games.add(new Game("V"));
        games.add(new Game("W"));
        games.add(new Game("X"));
        games.add(new Game("Y"));
        games.add(new Game("Z"));





    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemSelected(WheelPicker picker, Object data, int position) {


        String text = "";
        switch (picker.getId()) {
            case R.id.main_wheel_left:
                text = "Left:";
                break;

            case R.id.main_wheel_right:
                text = "Right:";
                break;
        }
        Toast.makeText(this, text + String.valueOf(data), Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onItemSelected(WheelPickerDateData picker, Object data, int position) {
        String text = "";
        switch (picker.getId()) {
            case R.id.main_wheel_left:
                text = "Left:";
                break;

            case R.id.main_wheel_right:
                text = "Right:";
                break;
        }
        Toast.makeText(this, text + String.valueOf(data), Toast.LENGTH_SHORT).show();

    }
}