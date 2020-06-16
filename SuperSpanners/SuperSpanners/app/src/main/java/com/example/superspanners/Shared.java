package com.example.superspanners;

import android.os.Handler;

public enum Shared {

    Data;


    public final int WORKER_THREAD_PAUSE = 1;  // seconds
    public  final int carsoutrate = 2;
    public  final int trucksoutrate =1;
    public int info[][]= new int[5][3];
    public int carsinrate=0;
    public int trucksinrate=0;
    public int carsopen=0;
    public int trucksopen=0;
    public int queue=0;
    public int queue2=0;
    public int carqueue=0;
    public int truckqueue=0;
    
}
