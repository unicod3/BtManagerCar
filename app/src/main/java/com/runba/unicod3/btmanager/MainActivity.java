package com.runba.unicod3.btmanager;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Array;


public class MainActivity extends ActionBarActivity {
    private static final int REQUEST_ENABLE_BT = 1;
    Button btnLeft, btnRight,btnUp, btnDown, btnSelect, btnStart, btnA, btnB, btnC, btnD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            Toast.makeText(getApplicationContext(),"Cihazınız Bluetooth Desteklemiyor!!", Toast.LENGTH_LONG).show();
        }


        btnLeft     = (Button)findViewById(R.id.buttonLEFT);
        btnRight    = (Button)findViewById(R.id.buttonRIGHT);
        btnUp       = (Button)findViewById(R.id.buttonUP);
        btnDown     = (Button)findViewById(R.id.buttonDown);
        btnSelect   = (Button)findViewById(R.id.buttonSELECT);
        btnStart    = (Button)findViewById(R.id.buttonSTART);
        btnA        = (Button)findViewById(R.id.buttonA);
        btnB        = (Button)findViewById(R.id.buttonB);
        btnC        = (Button)findViewById(R.id.buttonC);
        btnD        = (Button)findViewById(R.id.buttonD);



        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (!mBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                }


                Toast.makeText(getApplicationContext(),"Cihazlar Taranıyor...", Toast.LENGTH_SHORT).show();



            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
