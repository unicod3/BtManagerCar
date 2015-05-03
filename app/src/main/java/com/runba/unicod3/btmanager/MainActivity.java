package com.runba.unicod3.btmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    Button btnLeft, btnRight,btnUp, btnDown, btnSelect, btnStart, btnA, btnB, btnC, btnD;
    private BluetoothAdapter mBluetoothAdapter;
    protected AlertDialog.Builder builder;
    protected ListView device_list;


    ConnectThread mBluetooth = new ConnectThread();
    String mBluetoothName = "";
    String mBluetoothAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        builder = new AlertDialog.Builder(this);
        mBluetoothAdapter	= BluetoothAdapter.getDefaultAdapter();


        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            showToast("Cihazınız Bluetooth Desteklemiyor!!...");
            finish();
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
        device_list = (ListView)findViewById(R.id.new_devices);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivity(enableBtIntent);
                    showToast("Checking...");
                }
                try {
                    Intent serverIntent = new Intent(MainActivity.this, DeviceListActivity.class);
                    startActivityForResult(serverIntent, Helper.REQUEST_CONNECT_DEVICE);
                } catch (Exception e) {
                    showToast("Error Occurred! " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Helper.REQUEST_CONNECT_DEVICE:
                if (resultCode == Activity.RESULT_OK) {
                    mBluetoothName = data.getExtras().getString(Helper.EXTRA_BLUETOOTH_NAME);
                    mBluetoothAddress = data.getExtras().getString(Helper.EXTRA_BLUETOOTH_ADDRESS);

                   // setBluetoothInfo();
                    showToast(R.string.connectedDevice + mBluetoothName);

                    if (!mBluetoothAddress.equals("")) {
                        TextView textView = (TextView) findViewById(R.id.connStatus);
                        if (!mBluetooth.connect(mBluetoothAddress))
                            textView.setText(R.string.connFailed);
                        else
                            textView.setText(R.string.connSucceed);
                    }
                }
                break;
        }
    }


    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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


    @Override
    public void onPause() {
        if (mBluetoothAdapter != null) {
            if (mBluetoothAdapter.isDiscovering()) {
                mBluetoothAdapter.cancelDiscovery();
            }
        }

        super.onPause();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }




}
