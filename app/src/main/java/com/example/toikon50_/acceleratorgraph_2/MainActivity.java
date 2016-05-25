package com.example.toikon50_.acceleratorgraph_2;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {


    TextView tvAcc,tvAccX,tvAccY,tvAccZ;
    Button button;
    GraphView gv;

    SensorManager sm;

    static ArrayList<TimeAcc> allData = new ArrayList<>(5000);
    static ArrayList<TimeAcc> data = new ArrayList<>(1000);
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        tvAcc = (TextView) findViewById(R.id.tvAcc);
        tvAccX = (TextView) findViewById(R.id.tvAccX);
        tvAccY = (TextView) findViewById(R.id.tvAccY);
        tvAccZ = (TextView) findViewById(R.id.tvAccZ);

        button = (Button) findViewById(R.id.button);

        gv = (com.example.toikon50_.acceleratorgraph_2.GraphView) findViewById(R.id.view) ;

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.toikon50_.acceleratorgraph_2/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        sm.unregisterListener(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Sensor> sensors = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (sensors.size() > 0) {
            Sensor s = sensors.get(0);
            sm.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    boolean stopFlag = false;
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(!stopFlag){
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                float[] array = new float[3];
                System.arraycopy(event.values, 0, array, 0, array.length);
                long time = System.currentTimeMillis();

                TimeAcc ta = new TimeAcc(time,array);

                allData.add(ta);
                data.add(ta);

                tvAcc.setText("加速度");
                tvAccX.setText("X軸: " + array[0]);
                tvAccY.setText("Y軸: " + array[1]);
                tvAccZ.setText("Z軸: " + array[2]);

                gv.change();
                break;
        }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.toikon50_.acceleratorgraph_2/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

                @Override
                public boolean onTouchEvent(MotionEvent event){
                    switch(event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            System.out.println("(o・∇・o)");
                            stopFlag = true;
                            break;
                        //グラフの描写が飛ぶのは、時間がその分経過するため

                        case MotionEvent.ACTION_UP:
                            System.out.println("(*>△<)＜ナーンナーンっっ");
                            stopFlag = false;
                            break;
                    }

                    return false;
                }

            public void outputData(View view){
                System.out.println("( ｀ー´)ノ");

                try{

                    FileOutputStream out = openFileOutput("accData.txt",MODE_PRIVATE);

                    for(int i = 0;i<allData.size();i++){
                        float[] array = allData.get(i).accArray;
                        Long time = allData.get(i).time;
                        Float x = array[0];
                Float y = array[1];
                Float z = array[2];
                String str = time+","+x+","+y+","+z+"\n";
                out.write(str.getBytes());
            }

        }catch(IOException e){
            e.printStackTrace();
        }

        this.finish();

    }
}
