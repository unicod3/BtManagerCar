package com.runba.unicod3.btmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {
    Button  btnConnect, btnForward, btnBackward, btnLeft, btnRight;
    private BluetoothAdapter mBluetoothAdapter;
    protected AlertDialog.Builder builder;
    protected ListView device_list;
    private Switch btnLights;

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
            showToast(getString(R.string.notSuported));
            finish();
        }

        btnConnect  = (Button)findViewById(R.id.btnConnect);
        btnForward  = (Button)findViewById(R.id.btnForward);
        btnBackward = (Button)findViewById(R.id.btnBackward);
        btnLeft     = (Button)findViewById(R.id.btnLeft);
        btnRight    = (Button)findViewById(R.id.btnRight);
        btnLights   = (Switch)findViewById(R.id.switchLights);
        device_list = (ListView)findViewById(R.id.new_devices);

        //deactivate all buttons
        setButtonStatus(false);

        //Lights Off
        btnLights.setChecked(false);

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivity(enableBtIntent);
                }else {
                    if (!mBluetooth.mBluetoothAddress.equals("")) {//if another connection is already exits then close it first
                        stopAllActivities();
                    }else{
                        try {
                            Intent serverIntent = new Intent(MainActivity.this, DeviceListActivity.class);
                            startActivityForResult(serverIntent, Helper.REQUEST_CONNECT_DEVICE);
                        } catch (Exception e) {
                            showToast(getString(R.string.errorOccured) + ": " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        btnForward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mBluetooth.write("z");
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    mBluetooth.write("f");
                else if (event.getAction() == MotionEvent.ACTION_UP)
                    mBluetooth.write("e");
                return false;
            }
        });

        btnBackward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mBluetooth.write("z");
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    mBluetooth.write("b");
                else if (event.getAction() == MotionEvent.ACTION_UP)
                    mBluetooth.write("e");
                return false;
            }
        });

        btnLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mBluetooth.write("z");
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    mBluetooth.write("l");
                else if (event.getAction() == MotionEvent.ACTION_UP)
                    mBluetooth.write("e");
                return false;
            }
        });

        btnRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mBluetooth.write("z");
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    mBluetooth.write("r");
                else if (event.getAction() == MotionEvent.ACTION_UP)
                    mBluetooth.write("e");
                return false;
            }
        });

        btnLights.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!mBluetooth.check()) {
                    stopAllActivities();
                }

                if(isChecked){
                    //Turn off the lights
                    mBluetooth.write("p");
                }else{
                    //Turn on the lights
                    mBluetooth.write("a");
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
                        if (!mBluetooth.connect(mBluetoothAddress)){
                            setButtonStatus(false); //activate all buttons
                        }else {
                            btnConnect.setText(R.string.connSucceed);
                            setButtonStatus(true); //activate all buttons
                        }
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

    private void setButtonStatus(boolean status){
        btnForward.setEnabled(status);
        btnBackward.setEnabled(status);
        btnLeft.setEnabled(status);
        btnRight.setEnabled(status);
        btnLights.setEnabled(status);
    }

    private  void stopAllActivities(){
        mBluetooth.write("s"); //send Stop Signal before it closes the connection
        btnLights.setChecked(false);
        setButtonStatus(false); //deactivate buttons
        mBluetooth.mBluetoothAddress = ""; // reset address
        mBluetooth.close();//close Connection
        btnConnect.setText(R.string.btnConnect);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
