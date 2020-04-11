
package com.example.theclockapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.Calendar;
import java.util.jar.Attributes;

public class CustomClock extends View {
    private int minHeight, minWidth, milliHeight, miliWidth = 0;
    private int minPadding=0;
    private int minNumericalSpacing=0;
    private int minRadius = 0;
    private int milliRadius=0;
    private Paint minPaint=null;
    private Rect minRect = new Rect();
    private boolean isInit;
    private int[] mClockHours = {1,2,3,4,5,6,7,8,9,10,11,12};
    SharedPreferences preferences = this.getContext().getSharedPreferences( "ColourSettings", Context.MODE_PRIVATE );

    public CustomClock(Context context, AttributeSet attributeSet)
    {
        super(context, attributeSet);
    }
    public CustomClock(Context context, AttributeSet attributeSet, int defStyleAttr)
    {
        super(context, attributeSet, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        if(!isInit)
        {
            initializeClock();
        }
        //fetching the theme color from shared preferences
        String colour = preferences.getString( "Tcolour","WHITE" );
        if(colour.equals( "DARK" ))
        {
            //if dark make the paint grey
            canvas.drawColor( Color.DKGRAY );
            View parent = (View) this.getParent();
            parent.setBackgroundColor( Color.DKGRAY );
            View masterParent = (View)parent.getParent();
            masterParent.setBackgroundColor( Color.DKGRAY );
        }
        else if(colour.equals( "Light" ))
        {
            //if light make the paint white
            canvas.drawColor( Color.WHITE );
            View parent = (View) this.getParent();
            parent.setBackgroundColor( Color.WHITE );
            View masterParent = (View)parent.getParent();
            masterParent.setBackgroundColor( Color.WHITE );
        }
        else
        {
            canvas.drawColor( Color.DKGRAY );
            View parent = (View) this.getParent();
            parent.setBackgroundColor( Color.DKGRAY );
            View masterParent = (View)parent.getParent();
            masterParent.setBackgroundColor( Color.DKGRAY );
        }
        //draw the outer circle
        drawCircleBorder(canvas);
        //draw the milisecond circle
        drawMiliCircleBorder(canvas);

        drawClockCenter(canvas);
        drawMiliClockCenter(canvas);

        //draw the number on the border
        drawNumericHourBorder(canvas);

        drawMiniClockHands(canvas);
        drawClockHands(canvas);

        postInvalidateDelayed(500);
        invalidate();
    }
    private void initializeClock(){
        minPaint = new Paint();
        minHeight = getHeight();
        minWidth = getWidth();
        minPadding = minNumericalSpacing+50;
        int minAttr = Math.min( minHeight,minWidth );
        minRadius = minAttr/2- minPadding;
        isInit = true;
    }
    private void drawCircleBorder(Canvas canvas)
    {
        minPaint.reset();
        String colour = preferences.getString( "Tcolour","WHITE" );
        if(colour.equals( "Dark" )) {
            minPaint.setColor( Color.DKGRAY );

        }
        else if(colour.equals( "Light" ))
            minPaint.setColor( Color.WHITE );
        else
            minPaint.setColor( Color.parseColor( "#484848" ) );

        DashPathEffect dashPathEffect =new DashPathEffect( new float[]{15,15},(float)3.0 );

        minPaint.setPathEffect( dashPathEffect );
        minPaint.setStyle( Paint.Style.FILL_AND_STROKE );

        minPaint.setAntiAlias( true );
        canvas.drawCircle( minWidth/2,minHeight/2,minRadius+minPadding-10,minPaint );
    }

    private void drawClockCenter(Canvas canvas)
    {
        minPaint.setStyle( Paint.Style.FILL );
        canvas.drawCircle( minWidth/2,minHeight/2,12, minPaint );
    }
    private void drawNumericHourBorder(Canvas canvas){
        String colour = preferences.getString("Tcolour", "WHITE");
        if(colour.equals("Dark")){
            minPaint.setColor(Color.parseColor("#FFFFFF"));
        }else if(colour.equals("Light")){
            minPaint.setColor(Color.parseColor("#787170"));
        }else {
            minPaint.setColor(Color.BLACK);
        }
        int fontSize = (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics());
        minPaint.setTextSize(fontSize);
        for (int hour : mClockHours) {
            String tmp = String.valueOf(hour);
            minPaint.getTextBounds(tmp, 0, tmp.length(), minRect); // for circle-wise bounding
            double angle = Math.PI / 6 * (hour - 3); // as mathematical rule
            int x = (int) (minWidth / 2 + Math.cos(angle) * minRadius - minRect.width() / 2);
            int y = (int) (minHeight / 2 + Math.sin(angle) * minRadius + minRect.height() / 2);
            canvas.drawText(String.valueOf(hour), x, y, minPaint);
        }

    }
    private void drawClockHands(Canvas canvas) {
        Calendar calendar = Calendar.getInstance();
        float hour = calendar.get(Calendar.HOUR_OF_DAY);
        hour = hour > 12 ? hour - 12 : hour;
        drawHandLine(canvas, (hour + calendar.get(Calendar.MINUTE) / 60) * 5f, true, false,false);
        drawHandLine(canvas, calendar.get(Calendar.MINUTE), false, false,true);
        drawHandLine(canvas, calendar.get(Calendar.SECOND), false, true,false);
    }
    private void drawHandLine(Canvas canvas, double moment, boolean isHour, boolean isSecond, boolean isMinute) {
        double angle = Math.PI * moment / 30 - Math.PI / 2;
        int  handRadius = minRadius;

        if (isHour) {

            String colour = preferences.getString("Hcolour", "WHITE");
            if(colour.equals("Green")){
                minPaint.setColor(Color.GREEN);
            }else if(colour.equals("Red")){
                minPaint.setColor(Color.RED);
            }else if(colour.equals("Blue")){
                minPaint.setColor(Color.BLUE);
            }else if(colour.equals("Black")){
                minPaint.setColor(Color.BLACK);
            }else if(colour.equals("Yellow")){
                minPaint.setColor(Color.YELLOW);
            }else {
                minPaint.setColor(Color.BLACK);
            }

            minPaint.setStrokeWidth(20);
            handRadius = minRadius - 180 ;
        }

        if (isMinute) {
            String colour = preferences.getString("Mcolour", "WHITE");
            if(colour.equals("Green")){
                minPaint.setColor(Color.GREEN);
            }else if(colour.equals("Red")){
                minPaint.setColor(Color.RED);
            }else if(colour.equals("Blue")){
                minPaint.setColor(Color.BLUE);
            }else if(colour.equals("Black")){
                minPaint.setColor(Color.BLACK);
            }else if(colour.equals("Yellow")){
                minPaint.setColor(Color.YELLOW);
            }else {
                minPaint.setColor(Color.BLACK);
            }

            minPaint.setStrokeWidth(18);
            handRadius = minRadius - 80 ;
        }

        if (isSecond) {
            String colour = preferences.getString("Scolour", "WHITE");
            if(colour.equals("Green")){
                minPaint.setColor(Color.GREEN);
            }else if(colour.equals("Red")){
                minPaint.setColor(Color.RED);
            }else if(colour.equals("Blue")){
                minPaint.setColor(Color.BLUE);
            }else if(colour.equals("Black")){
                minPaint.setColor(Color.BLACK);
            }else if(colour.equals("Yellow")){
                minPaint.setColor(Color.YELLOW);
            }else {
                minPaint.setColor(Color.BLACK);
            }
            minPaint.setStrokeWidth(7);
            handRadius = minRadius - 80 ;
        }
        canvas
                .drawLine(minWidth / 2, minHeight / 2, (float) (minWidth / 2 + Math.cos(angle) * handRadius),
                        (float) (minHeight / 2 + Math.sin(angle) * handRadius), minPaint);
    }
    private void drawMiliCircleBorder(Canvas canvas) {
        minPaint.reset();
        minPaint.setColor(Color.BLACK);
        minPaint.setStyle(Paint.Style.STROKE);
        minPaint.setStrokeWidth(4);
        minPaint.setAntiAlias(true);
        int minAttr1 = Math.min(minHeight, minWidth);
        milliRadius = minAttr1 / 9;
        miliWidth = (minWidth / 2)/2;
        milliHeight = minHeight / 2;
        canvas.drawCircle(miliWidth,milliHeight , milliRadius, minPaint);
    }

    private void drawMiliClockCenter(Canvas canvas) {
        minPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(miliWidth, milliHeight, 7, minPaint);

    }

    private void drawMiniClockHands(Canvas canvas) {
        Calendar calendar = Calendar.getInstance();
        drawMiniHandLine(canvas, calendar.get(Calendar.MILLISECOND));
    }

    private void drawMiniHandLine(Canvas canvas, double moment) {

        double moment1 = moment / 16.88;
        double angle = Math.PI * moment1 / 30 - Math.PI / 2;
        int handRadius =  milliRadius - 50 ;
        String colour = preferences.getString("Tcolour", "WHITE");
        if(colour.equals("Dark")){
            minPaint.setColor(Color.WHITE);
        }else if(colour.equals("Light")){
            minPaint.setColor(Color.GRAY);
        }else {
            minPaint.setColor(Color.BLACK);
        }
        canvas.drawLine(miliWidth, milliHeight, (float) (miliWidth + Math.cos(angle) * handRadius),
                (float) (milliHeight + Math.sin(angle) * handRadius), minPaint);
    }
}
