package com.intertech.customviews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.MainThread;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.Dialog;
import android.view.View;


import static android.content.Context.MODE_PRIVATE;

/**
 * View to allow the selection of a numeric value by pressing plus/minus buttons.  Pressing and holding
 * a button will update the value repeatedly.
 * <p>
 * This view can be configured with a minimum and maximum value.  There is also a label that will
 * display below the current value.
 * </p>
 *
 */

public class ValueSelector extends RelativeLayout {

    private int minValue = Integer.MIN_VALUE;
    private int maxValue = Integer.MAX_VALUE;
   public String car_number ="10";
    private boolean plusButtonIsPressed = false;
    private boolean minusButtonIsPressed = false;
    private final long REPEAT_INTERVAL_MS = 100l;

    public static final String PREFS_NAME = "MySettingsFile";
    public static final String DEFAULT="N/A";
    public String savedName =DEFAULT, savedLast=DEFAULT,savedPhone=DEFAULT;


    View rootView;
    EditText valueTextView;
    View minusButton;
    View plusButton;

    Handler handler = new Handler();




    public ValueSelector(Context context) {
        super(context);
        init(context);

    }

    public ValueSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public ValueSelector(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    /**
     * Get the current minimum value that is allowed
     *
     * @return
     */
    public int getMinValue() {
        return minValue;
    }

    /**
     * Set the minimum value that will be allowed
     *
     * @param minValue
     */
    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    /**
     * Get the current maximum value that is allowed
     *
     * @return
     */
    public int getMaxValue() {
        return maxValue;
    }

    /**
     * Set the maximum value that will be allowed
     *
     * @param maxValue
     */
    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * Get the current value
     *
     * @return the current value
     */
    public String getValue() {

        return valueTextView.getText().toString();
    }

    /**
     * Set the current value.  If the passed in value exceeds the current min or max, the value
     * will be set to the respective min/max.
     *
     * @param newValue new value
     */
    public void setValue(int newValue) {
        int value = newValue;
        if(newValue < 0) {
            value = 0;
        } else if(newValue < minValue) {
            value = minValue;
        } else if (newValue > maxValue) {
            value = maxValue;
        }

        valueTextView.setText(String.valueOf(value) +" CAR");
    }

    private void init(Context context) {
        rootView = inflate(context, R.layout.value_selector, this);
        valueTextView = (EditText) rootView.findViewById(R.id.valueTextView);

        minusButton = rootView.findViewById(R.id.minusButton);
        plusButton = rootView.findViewById(R.id.plusButton);

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementValue();
            }
        });
        minusButton.setOnLongClickListener(
            new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View arg0) {

                    minusButtonIsPressed = true;
                    handler.post(new AutoDecrementer());
                    return false;
                }
            }
        );
        minusButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if ((event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)) {
                    minusButtonIsPressed = false;
                }
                return false;
            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementValue();
            }
        });
        plusButton.setOnLongClickListener(
            new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View arg0) {
                    plusButtonIsPressed = true;
                    handler.post(new AutoIncrementer());
                    return false;
                }
            }
        );

        plusButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if ((event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)) {
                    plusButtonIsPressed = false;
                }
                return false;
            }
        });
    }

    private void incrementValue() {
        String currentVal = valueTextView.getText().toString();
       // String value = textView.getText().toString();

        currentVal = currentVal.trim().replaceAll("[^0-9]","|").replaceAll("(\\D)\\1+","$1");
        String values[] = currentVal.trim().split("\\|");
        int intValues[] = new int[values.length];
        for(int i = 0; i < values.length; i ++){
            intValues[i] = Integer.parseInt(values[i]);
        }
        //assuming you only care about the first number
        int number = intValues[0];

        if(number < maxValue) {
            valueTextView.setText(String.valueOf(number + 1)+" CAR");
            car_number=String.valueOf(number + 1);

            //alert.showDialog(MainActivity.this, "Pick your Car");

        }
    }

    private void decrementValue() {
        String currentVal = valueTextView.getText().toString();
        // String value = textView.getText().toString();

        currentVal = currentVal.trim().replaceAll("[^0-9]","|").replaceAll("(\\D)\\1+","$1");
        String values[] = currentVal.trim().split("\\|");
        int intValues[] = new int[values.length];
        for(int i = 0; i < values.length; i ++){
            intValues[i] = Integer.parseInt(values[i]);
        }
        //assuming you only care about the first number
        int number = intValues[0];
        if(number == 0) {
            valueTextView.setText(String.valueOf(0)+" CAR");
            car_number=String.valueOf(number + 1);

        }else if(number > minValue) {
            valueTextView.setText(String.valueOf(number - 1)+" CAR");
            car_number=String.valueOf(number + 1);


        }
    }

    private class AutoIncrementer implements Runnable {
        @Override
        public void run() {
            if(plusButtonIsPressed){
                incrementValue();
                handler.postDelayed( new AutoIncrementer(), REPEAT_INTERVAL_MS);
            }
        }
    }
    private class AutoDecrementer implements Runnable {
        @Override
        public void run() {
            if(minusButtonIsPressed){
                decrementValue();
                handler.postDelayed(new AutoDecrementer(), REPEAT_INTERVAL_MS);
            }
        }
    }
    public String CarValue() {
        return car_number;
    }



}
