package com.example.superspanners;
import java.util.TimerTask;
import java.util.logging.Handler;

public class Carsin extends TimerTask{


    private MainActivity parent;

    //----------------------------------------------------------------
    // Constructor
    //----------------------------------------------------------------
    public Carsin(MainActivity parent)
    {
        this.parent = parent;
    }

    public void run()
    {
        if(MainActivity.run == true) {
            if (Shared.Data.carsopen == 0)
                return;
            Shared.Data.queue = Shared.Data.carsinrate / Shared.Data.carsopen;
            Shared.Data.carqueue = Shared.Data.carqueue + Shared.Data.queue;
            parent.updateReservations.sendEmptyMessage(0);
        }

    }


}
