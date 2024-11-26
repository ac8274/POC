package com.example.poc.CustomViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Joystick extends View {
    private float mCirceleX;
    private float mCirceleY;
    private Paint mPaintCircle;
    private static final float mCircleRadius = 100;

    public Joystick(Context context)
    {
        super(context);
        init(null);
    }

    public Joystick(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs);
    }

    public Joystick(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }
    public Joystick(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public void init(@Nullable AttributeSet set)
    {
        mCirceleX = 0;
        mCirceleY =0;
        mPaintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintCircle.setColor(Color.parseColor("#FF0000"));
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas)
    {
        if(0f == mCirceleX && 0f == mCirceleY) {
            mCirceleX = getWidth() / 2;
            mCirceleY = getHeight() / 2;
        }

        canvas.drawCircle(mCirceleX,mCirceleY,mCircleRadius,mPaintCircle);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        boolean value = super.onTouchEvent(event);

        if (event.getAction() == MotionEvent.ACTION_MOVE)  // if on my view was preformed the action of moving
        {
            if(Math.pow(event.getX() - mCirceleX,2) + Math.pow(event.getY() - mCirceleY,2) < Math.pow(mCircleRadius,2)) //check if the touched part is inside my joystickCenter
            {
                mCirceleY = event.getY(); // set new position X of joystickCenter
                mCirceleX = event.getX(); // set new position X of joystickCenter

                postInvalidate(); // update the UI of changes
                return true;
            }
            return value;
        }

        return value;
    }
}
