package com.example.superspanners;
import java.sql.Time;
import java.util.TimerTask;

public class Trucksout extends TimerTask {

    private MainActivity parent;

    //----------------------------------------------------------------
    // Constructor
    //----------------------------------------------------------------
    public Trucksout(MainActivity parent)
    {
        this.parent = parent;
    }

    public void run()
    {
        if(MainActivity.run == true && Shared.Data.truckqueue >0) {
            Shared.Data.truckqueue = Shared.Data.truckqueue - Shared.Data.trucksoutrate;
            parent.updateReservations.sendEmptyMessage(0);
        }

    }
}