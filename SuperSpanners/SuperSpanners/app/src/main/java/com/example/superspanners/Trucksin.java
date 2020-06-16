package com.example.superspanners;
import java.util.TimerTask;
import java.util.logging.Handler;

public class Trucksin extends TimerTask{


    private MainActivity parent;

    //----------------------------------------------------------------
    // Constructor
    //----------------------------------------------------------------
    public Trucksin(MainActivity parent)
    {
        this.parent = parent;
    }

    public void run()
    {
        if(MainActivity.run == true) {
            if (Shared.Data.trucksopen == 0)
                return;
            Shared.Data.queue2 = Shared.Data.trucksinrate / Shared.Data.trucksopen;
            Shared.Data.truckqueue = Shared.Data.truckqueue + Shared.Data.queue2;
            parent.updateReservations.sendEmptyMessage(0);

        }
    }


}
