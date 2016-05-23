package com.example.toikon50_.acceleratorgraph_2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class GraphView extends View {

    public GraphView(Context context,AttributeSet attrs){
        super(context,attrs);
    }

    int rate = 12;
    float timeRate = 0.2f;

    @Override
    protected void onDraw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.argb(255, 255, 255, 255));
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

        paint.setColor(Color.argb(255, 0, 0, 0));
        canvas.drawLine(0, getHeight() >> 1, getWidth(), getHeight() / 2, paint);//0m/s^2
        canvas.drawLine(0, (getHeight() / 2) - (10 * rate), getWidth(), getHeight() / 2 - 10 * rate, paint);//10m/s^2
        canvas.drawLine(0, (getHeight() / 2) + (10 * rate), getWidth(), getHeight() / 2 + 10 * rate, paint);//-10m/s^2

        if(!MainActivity.accData.isEmpty()){

            float prevX = getWidth();
            float[] prevY = new float[3];// 0-x軸について 1-y軸について 2-z軸について

            prevY[0] = getHeight() / 2 - MainActivity.accData.get(0)[0] * rate;
            prevY[1] = getHeight() / 2 - MainActivity.accData.get(0)[1] * rate;
            prevY[2] = getHeight() / 2 - MainActivity.accData.get(0)[2]* rate;

            long preTime = MainActivity.timeData.get(0);

            paint.setStyle(Paint.Style.STROKE);

            for (int i = 1; i < MainActivity.accData.size(); i++) {
                float[] now  = MainActivity.accData.get(i);
                long nowTime = MainActivity.timeData.get(i);
                float x = getWidth() - ((nowTime - preTime) * timeRate);
                float[] y = new float[3];

                for(int j = 0;j<y.length;j++) {
                    y[j] = getHeight() / 2 - now[j] * rate;
                }

                final int LINE_WIDTH = 3;

                paint.setColor(Color.argb(255, 255, 0, 0));
                paint.setStrokeWidth(LINE_WIDTH);
                canvas.drawLine(prevX, prevY[0], x, y[0], paint);

                paint.setColor(Color.argb(255, 0, 255, 0));
                paint.setStrokeWidth(LINE_WIDTH);
                canvas.drawLine(prevX, prevY[1], x, y[1], paint);

                paint.setColor(Color.argb(255, 0, 0, 255));
                paint.setStrokeWidth(LINE_WIDTH);
                canvas.drawLine(prevX, prevY[2], x, y[2], paint);
//                System.out.println(x);

                prevX = x;
                float y0 = y[0];
                float y1 = y[1];
                float y2 = y[2];

                prevY[0] = y0;
                prevY[1] = y1;
                prevY[2] = y2;

                if (x < 0) {
                    MainActivity.accData.remove(0);
                    MainActivity.timeData.remove(0);
                }

        }
    }

    }

    public void change() {
        invalidate();
    }
}
