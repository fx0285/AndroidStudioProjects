package com.example.superspanners;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AlertDialog;
import android.view.MenuInflater;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import java.util.Timer;

import android.os.Handler;
import android.os.Message;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    Button lane1,lane2,lane3,lane4,lane5, start, stop, reset;
    protected TextView queue1, queue2, queue3, queue4, queue5, truckcount, carcount;
    SeekBar carseek, truckseek;
    private Timer timer;
    Button lanes[] = new Button[5];
    TextView queues[]= new TextView[5];
    public static boolean run=true;



    // KEEP IN MIND:  In this application, one simulation second equals one real-world minute!!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);

        lane1=findViewById(R.id.lane1);
        lane1.setOnClickListener(this);
        lane2=findViewById(R.id.lane2);
        lane2.setOnClickListener(this);
        lane3=findViewById(R.id.lane3);
        lane3.setOnClickListener(this);
        lane4=findViewById(R.id.lane4);
        lane4.setOnClickListener(this);
        lane5=findViewById(R.id.lane5);
        lane5.setOnClickListener(this);

        queue1=findViewById(R.id.queue1);
        queue2=findViewById(R.id.queue2);
        queue3=findViewById(R.id.queue3);
        queue4=findViewById(R.id.queue4);
        queue5=findViewById(R.id.queue5);

        start=findViewById(R.id.start);
        stop=findViewById(R.id.stop);
        reset=findViewById(R.id.reset);

        carseek=findViewById(R.id.carseek);
        truckseek=findViewById(R.id.truckseek);
        carcount=findViewById(R.id.carscount);
        truckcount=findViewById(R.id.truckscount);


        lanes[0]=lane1;
        lanes[1]=lane2;
        lanes[2]=lane3;
        lanes[3]=lane4;
        lanes[4]=lane5;

        queues[0]=queue1;
        queues[1]=queue2;
        queues[2]=queue3;
        queues[3]=queue4;
        queues[4]=queue5;

        carseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int result;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                carcount.setText(progress + "");
                result=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Shared.Data.carsinrate=result;
            }
        });

        truckseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int result;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                truckcount.setText(progress + "");
                result=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Shared.Data.trucksinrate=result;
            }
        });

    }


    public void startRepeatedTimerTask(View v)
    {
        run=true;

        // Reset counter
        Shared.Data.carsinrate = 0;
        Shared.Data.trucksinrate=0;
        if (timer != null) timer.cancel();
        Shared.Data.carsinrate= carseek.getProgress();
        Shared.Data.trucksinrate=truckseek.getProgress();

        for(int i=0;i<5;i++){
            if(Shared.Data.info[i][1]==1){
                queues[i].setText(String.valueOf(Shared.Data.carqueue));

                if(Shared.Data.carsopen ==0)
                    return;

                Shared.Data.queue= Shared.Data.carsinrate / Shared.Data.carsopen;

                timer = new Timer();
                timer.schedule(
                        new Carsin(this),
                        (Shared.Data.WORKER_THREAD_PAUSE * 1000),
                        (Shared.Data.WORKER_THREAD_PAUSE * 1000));
                timer.schedule(new Carsout(this),(Shared.Data.WORKER_THREAD_PAUSE * 1000),
                        (Shared.Data.WORKER_THREAD_PAUSE * 1000));

            }
            else if(Shared.Data.info[i][1]==2){
                queues[i].setText(String.valueOf(Shared.Data.truckqueue));

                if(Shared.Data.trucksopen ==0)
                    return;

                Shared.Data.queue2= Shared.Data.trucksinrate / Shared.Data.trucksopen;

                timer = new Timer();
                timer.schedule(
                        new Trucksin(this),
                        (Shared.Data.WORKER_THREAD_PAUSE * 1000),
                        (Shared.Data.WORKER_THREAD_PAUSE * 1000));
                timer.schedule(new Trucksout(this),(Shared.Data.WORKER_THREAD_PAUSE * 1000),
                        (Shared.Data.WORKER_THREAD_PAUSE * 1000));

            }

        }


    }



    public Handler updateReservations = new Handler()
    {

        //------------------------------------------------------------
        // handleMessage
        //------------------------------------------------------------
        @Override
        public void handleMessage(Message msg)
        {

            for(int i=0;i<5;i++){
                if(Shared.Data.info[i][1]==1){
                    queues[i].setText(String.valueOf(Shared.Data.carqueue));

                }
                else if(Shared.Data.info[i][1]==2){
                    queues[i].setText(String.valueOf(Shared.Data.truckqueue));

                }


                if(Integer.parseInt((String)queues[i].getText())< 5 ){
                    queues[i].setBackgroundColor(Color.parseColor("#a4c639"));
                }
                else if(Integer.parseInt((String)queues[i].getText())>  5 && Integer.parseInt((String)queues[i].getText())< 26){
                    queues[i].setBackgroundColor(Color.parseColor("#ff8c00"));
                }
                else if(Integer.parseInt((String)queues[i].getText())> 25){
                    queues[i].setBackgroundColor(Color.parseColor("#ff0000"));
                }

            }

        }

    };

    //menu inflater
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //menu handler
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == R.id.about) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("SuperSpanners! Version: 1.5 By: Tesneem Algahim")
                    .setCancelable(false)
                    .setPositiveButton("Cool!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }

        return super.onOptionsItemSelected(item);
    }

    //stop the timer
    public void stopTimerTask(View v)
    {

        // Cancel timer if  exists
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
        timer = null;
        run=false;


    }


    //reset button will reset all values here
    public void reset(View v){
        if (timer != null){
            timer.cancel();
            timer.purge();}
        timer = null;
        run =false;

        //reset buttons and labels
        for(int i=0;i<5;i++){
            lanes[i].setText("CLOSED");
            lanes[i].setTextColor(Color.parseColor("#E60909"));
            Shared.Data.info[i][1]=0;

            queues[i].setText("0");
            queues[i].setBackgroundColor(Color.parseColor("#a4c639"));
            queues[i].setTextColor(Color.parseColor("#000000"));

        }

        carcount.setText(0 + "");
        truckcount.setText(0+ "");
        carseek.setProgress(0);
        truckseek.setProgress(0);


        Shared.Data.carsinrate=0;
        Shared.Data.trucksinrate=0;
        Shared.Data.carsopen=0;
        Shared.Data.trucksopen=0;
        Shared.Data.queue=0;
        Shared.Data.queue2=0;
        Shared.Data.carqueue=0;
        Shared.Data.truckqueue=0;
    }


    //handles changes to buttons
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lane1:
                if(Shared.Data.info[0][1]==0){                                      //if lane is closed, open for cars
                    lane1.setText("CARS ONLY");
                    lane1.setTextColor(Color.parseColor("#0009ff"));
                    Shared.Data.info[0][1]=1;                                       //cars
                    Shared.Data.info[0][0]=1;                                       // open
                    Shared.Data.carsopen++;                                         //add a car
                }
                else if(Shared.Data.info[0][1]==1){                                //if cars, make trucks
                    lane1.setText("TRUCKS ONLY");
                    lane1.setTextColor(Color.parseColor("#0009ff"));
                    Shared.Data.info[0][1]=2;                                       //trucks
                    Shared.Data.info[0][0]=1;
                    Shared.Data.carsopen--;                                             // minus a car
                    Shared.Data.trucksopen++;                                       // add a truck
                }
                else if(Shared.Data.info[0][1]==2){  //if trucks, close
                    lane1.setText("CLOSED");
                    lane1.setTextColor(Color.parseColor("#E60909"));
                    Shared.Data.info[0][1]=0;
                    Shared.Data.info[0][0]=0;
                    Shared.Data.trucksopen--;                                       // minus a truck
                    } //close the lane
                break;
            case R.id.lane2:
                if(Shared.Data.info[1][1]==0){
                    lane2.setText("CARS ONLY");
                    lane2.setTextColor(Color.parseColor("#0009ff"));
                    Shared.Data.info[1][1]=1;
                    Shared.Data.info[1][0]=1;
                    Shared.Data.carsopen++;
                }
                else if(Shared.Data.info[1][1]==1){
                    lane2.setText("TRUCKS ONLY");
                    lane2.setTextColor(Color.parseColor("#0009ff"));
                    Shared.Data.info[1][1]=2;
                    Shared.Data.info[1][0]=1;
                    Shared.Data.carsopen--;
                    Shared.Data.trucksopen++;}
                else if(Shared.Data.info[1][1]==2){
                    lane2.setText("CLOSED");
                    lane2.setTextColor(Color.parseColor("#E60909"));
                    Shared.Data.info[1][1]=0;
                    Shared.Data.info[1][0]=0;
                    Shared.Data.trucksopen--;}
                break;
            case R.id.lane3:
                if(Shared.Data.info[2][1]==0){
                    lane3.setText("CARS ONLY");
                    lane3.setTextColor(Color.parseColor("#0009ff"));
                    Shared.Data.info[2][1]=1;
                    Shared.Data.info[2][0]=1;
                    Shared.Data.carsopen++;}
                else if(Shared.Data.info[2][1]==1){
                    lane3.setText("TRUCKS ONLY");
                    lane3.setTextColor(Color.parseColor("#0009ff"));
                    Shared.Data.info[2][1]=2;
                    Shared.Data.info[2][0]=1;
                    Shared.Data.carsopen--;
                    Shared.Data.trucksopen++;}
                else if(Shared.Data.info[2][1]==2){
                    lane3.setText("CLOSED");
                    lane3.setTextColor(Color.parseColor("#E60909"));
                    Shared.Data.info[2][1]=0;
                    Shared.Data.info[2][0]=0;
                    Shared.Data.trucksopen--;}
                break;
            case R.id.lane4:
                if(Shared.Data.info[3][1]==0){
                    lane4.setText("CARS ONLY");
                    lane4.setTextColor(Color.parseColor("#0009ff"));
                    Shared.Data.info[3][1]=1;
                    Shared.Data.info[3][0]=1;
                    Shared.Data.carsopen++;}
                else if(Shared.Data.info[3][1]==1){
                    lane4.setText("TRUCKS ONLY");
                    lane4.setTextColor(Color.parseColor("#0009ff"));
                    Shared.Data.info[3][1]=2;
                    Shared.Data.info[3][0]=1;
                    Shared.Data.carsopen--;
                    Shared.Data.trucksopen++;}
                else if(Shared.Data.info[3][1]==2){
                    lane4.setText("CLOSED");
                    lane4.setTextColor(Color.parseColor("#E60909"));
                    Shared.Data.info[3][1]=0;
                    Shared.Data.info[3][0]=0;
                    Shared.Data.trucksopen--;}
                break;
            case R.id.lane5:
                if(Shared.Data.info[4][1]==0){
                    lane5.setText("CARS ONLY");
                    lane5.setTextColor(Color.parseColor("#0009ff"));
                    Shared.Data.info[4][1]=1;
                    Shared.Data.info[4][0]=1;
                    Shared.Data.carsopen++;}
                else if(Shared.Data.info[4][1]==1){
                    lane5.setText("TRUCKS ONLY");
                    lane5.setTextColor(Color.parseColor("#0009ff"));
                    Shared.Data.info[4][1]=2;
                    Shared.Data.info[4][0]=1;
                    Shared.Data.carsopen--;
                    Shared.Data.trucksopen++;}
                else if(Shared.Data.info[4][1]==2){
                    lane5.setText("CLOSED");
                    lane5.setTextColor(Color.parseColor("#E60909"));
                    Shared.Data.info[4][1]=0;
                    Shared.Data.info[4][0]=0;
                    Shared.Data.trucksopen--;}
                break;
        }
    }

}
