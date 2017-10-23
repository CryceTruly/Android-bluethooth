package com.crycetruly.bluetoothcheetsheet;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    BluetoothAdapter bluetoothAdapter;
    Button onoff;
private final BroadcastReceiver myreciever=new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: Recieveing.......................");
String action =intent.getAction();

if(action.equals(bluetoothAdapter.ACTION_STATE_CHANGED)){

        int state=intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,BluetoothAdapter.ERROR);
        switch (state) {
            case BluetoothAdapter.STATE_ON:
                Log.d(TAG, "onReceive: STATE ON");
                Toast.makeText(MainActivity.this, "ON", Toast.LENGTH_SHORT).show();
                break;
            case BluetoothAdapter.STATE_TURNING_ON:
                Log.d(TAG, "onReceive: TURNING ON");
                Toast.makeText(MainActivity.this, "TURNING ON", Toast.LENGTH_SHORT).show();
                break;

            case BluetoothAdapter.STATE_TURNING_OFF:
                Log.d(TAG, "onReceive: TURNING OFF");
                Toast.makeText(MainActivity.this, "TURNING OFF", Toast.LENGTH_SHORT).show();
                break;

            case BluetoothAdapter.STATE_OFF:
                Toast.makeText(MainActivity.this, " OFF", Toast.LENGTH_SHORT).show();
                break;

        }

}


    }
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: CREATED");
        onoff=findViewById(R.id.onoff);

        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        onoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableDisableBluetooth();}
        });

    }

    private void enableDisableBluetooth() {
        Log.d(TAG, "enableDisableBluetooth: Enabling....................");
        if (bluetoothAdapter==null){
            Log.d(TAG, "enableDisableBluetooth: Cant find bluetooth");
            Toast.makeText(this, "No btn on device", Toast.LENGTH_SHORT).show();
        }
        if(!bluetoothAdapter.isEnabled()){
            Intent intent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(intent);
            IntentFilter intentFilter=new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);

            registerReceiver(myreciever,intentFilter);
        }
        if(bluetoothAdapter.isEnabled()){
            bluetoothAdapter.disable();
            IntentFilter intentFilter=new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);

            registerReceiver(myreciever,intentFilter);
        }
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
        unregisterReceiver(myreciever);
    }
}
