package com.example.superspanners;
import java.sql.Time;
import java.util.TimerTask;

public class Carsout extends TimerTask {

    private MainActivity parent;

    //----------------------------------------------------------------
    // Constructor
    //----------------------------------------------------------------
    public Carsout(MainActivity parent)
    {
        this.parent = parent;
    }

    public void run()
    {
        if(MainActivity.run == true && Shared.Data.carqueue >0) {
            if(Shared.Data.carqueue == 1)
                Shared.Data.carqueue = Shared.Data.carqueue -1;
            else
                Shared.Data.carqueue = Shared.Data.carqueue - Shared.Data.carsoutrate;
            parent.updateReservations.sendEmptyMessage(0);
        }
    }
}
