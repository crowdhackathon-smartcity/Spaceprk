package com.intertech.customviews;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * View to allow the selection of a numeric value by pressing plus/minus buttons.  Pressing and holding
 * a button will update the value repeatedly.
 * <p>
 * This view can be configured with a minimum and maximum value.  There is also a label that will
 * display below the current value.
 * </p>
 *
 */
public class MotoSelector extends RelativeLayout {

    private int minValue = Integer.MIN_VALUE;
    private int maxValue = Integer.MAX_VALUE;

    private boolean plusButtonIsPressed = false;
    private boolean minusButtonIsPressed = false;
    private final long REPEAT_INTERVAL_MS = 100l;

    View rootView;
    TextView valueTextView;
    View minusButton;
    View plusButton;

    Handler handler = new Handler();

    public MotoSelector(Context context) {
        super(context);
        init(context);
    }

    public MotoSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MotoSelector(Context context, AttributeSet attrs, int defStyle) {
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

        valueTextView.setText(String.valueOf(value));
    }

    private void init(Context context) {
        rootView = inflate(context, R.layout.moto_selector, this);
        valueTextView = (TextView) rootView.findViewById(R.id.motoTextView);

        minusButton = rootView.findViewById(R.id.motominusButton);
        plusButton = rootView.findViewById(R.id.motoplusButton);

        minusButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementValue();
            }
        });
        minusButton.setOnLongClickListener(
            new OnLongClickListener() {
                @Override
                public boolean onLongClick(View arg0) {
                    minusButtonIsPressed = true;
                    handler.post(new AutoDecrementer());
                    return false;
                }
            }
        );
        minusButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if ((event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)) {
                    minusButtonIsPressed = false;
                }
                return false;
            }
        });

        plusButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementValue();
            }
        });
        plusButton.setOnLongClickListener(
            new OnLongClickListener() {
                @Override
                public boolean onLongClick(View arg0) {
                    plusButtonIsPressed = true;
                    handler.post(new AutoIncrementer());
                    return false;
                }
            }
        );

        plusButton.setOnTouchListener(new OnTouchListener() {
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
            valueTextView.setText(String.valueOf(number + 1)+" MOTORCYCLE");
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
            valueTextView.setText(String.valueOf(0)+" MOTORCYCLE");
        }else if(number > minValue) {
            valueTextView.setText(String.valueOf(number - 1)+" MOTORCYCLE");
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
}
