package com.runba.unicod3.btmanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.util.LogWriter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private static final int REQUEST_ENABLE_BT = 1;
    Button btnLeft, btnRight,btnUp, btnDown, btnSelect, btnStart, btnA, btnB, btnC, btnD;
    private ArrayAdapter<String> mDeviceList;
    private ProgressDialog mProgressDlg;
    private BluetoothAdapter mBluetoothAdapter;
    private AlertDialog.Builder builder;
    private ListView device_list;
    private ArrayAdapter<String> modeAdapter;

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

                //Toast.makeText(getApplicationContext(), "Cihazlar Taranıyor...", Toast.LENGTH_SHORT).show();
                try {
                    Intent serverIntent = new Intent(MainActivity.this, DeviceListActivity.class);
                    startActivityForResult(serverIntent, CommonUtil.REQUEST_CONNECT_DEVICE);
                } catch (Exception e) {
                    showToast("Error Occured! " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }
/*

    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                mDeviceList.add(device.getName() + "\n" + device.getAddress());


            }else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                setSupportProgressBarIndeterminateVisibility(false);
                setTitle(R.string.device_list);
                if (mDeviceList.getCount() == 0) {
                    String noDevices = getResources().getText(R.string.none_found).toString();
                    mDeviceList.add(noDevices);
                    setContentView(R.layout.device_list);

                }
            }
        }
    };*/

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
        //unregisterReceiver(mReceiver);
        super.onDestroy();
    }




}
