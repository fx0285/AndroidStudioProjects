package com.example.dairydartsinventory;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Gravity;

public class MainActivity extends AppCompatActivity {

    private EditText milkGallons;
    private Button addButton;
    private Button resetButton;
    private EditText eggCount;
    private TextView milkUnits;
    private TextView eggUnits;
    private TextView milkStatus;
    private TextView eggStatus;
    private EditText time;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton= (Button) findViewById(R.id.button);
        resetButton= (Button) findViewById(R.id.button2);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                milkGallons = (EditText) (findViewById(R.id.MilkEditText));
                eggCount= (EditText) findViewById(R.id.EggsEditText);
                milkUnits=(TextView) findViewById(R.id.milkCosttextView);
                eggUnits=(TextView) findViewById(R.id.EggsUnittextView3);
                milkStatus= (TextView) findViewById(R.id.MilkStatustextView2);
                eggStatus= (TextView) findViewById(R.id.EggsStatustextView4);
                time= (EditText) (findViewById(R.id.TimeEditText));

                int milk= Integer.parseInt(milkGallons.getText().toString());
                int eggs= Integer.parseInt(eggCount.getText().toString());
                String Time =(time.getText().toString());

               if(milk < 10){
                   milkStatus.setText("LOW");
               }
               else
                   milkStatus.setText("OKAY");

               if(eggs <10){
                   eggStatus.setText("LOW");
               }
               else
                   eggStatus.setText("OKAY");

               double milkresult= milk*3.25;
               double eggresult=eggs*2.5;

               milkUnits.setText(milkresult + "");
               eggUnits.setText(eggresult + "");

                toast = Toast.makeText(getApplicationContext(),
                        "Store: 59; inventory time: "+ Time +" am; milk count: " + milk +" ; eggs count: " + eggs, Toast.LENGTH_SHORT);
                //toast.setGravity(Gravity.TOP | Gravity.END, 0, 0);
                toast.show();


            }
        });


        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                milkGallons = (EditText) (findViewById(R.id.MilkEditText));
                eggCount= (EditText) findViewById(R.id.EggsEditText);
                milkUnits=(TextView) findViewById(R.id.milkCosttextView);
                eggUnits=(TextView) findViewById(R.id.EggsUnittextView3);
                milkStatus= (TextView) findViewById(R.id.MilkStatustextView2);
                eggStatus= (TextView) findViewById(R.id.EggsStatustextView4);
                time= (EditText) (findViewById(R.id.TimeEditText));

                milkGallons.setText("");
                eggCount.setText("");
                milkUnits.setText("");
                eggUnits.setText("");
                milkStatus.setText("");
                eggStatus.setText("");
                time.setText("");
            }
        });
    }
}
