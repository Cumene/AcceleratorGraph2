package com.example.toikon50_.acceleratorgraph_2;

/**
 * Created by Toikon50-クメン on 2016/05/25.
 */
public class TimeAcc {
    long time;
    float[] accArray;

    public TimeAcc(long time,float[] accArray){
        this.accArray = new float[3];
        this.time = time;
        for(int i = 0;i<this.accArray.length;i++) {
            this.accArray[i] = accArray[i];
        }
    }
}
