package ecg.imaginatio.subin.classtester;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;


public class ToastExample {


    private OutputStream mmOutputStream;
    private Context context;
    private static ToastExample instance;
    private BluetoothAdapter myBluetooth;
    BluetoothSocket btSocket = null;
    private BluetoothDevice positive;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    static final String address = "30:14:06:26:06:55";
    public ToastExample() {
        this.instance = this;
    }

    public static ToastExample instance() {
        if(instance == null) {
            instance = new ToastExample();
        }
        return instance;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void showMessage(String message) {

      // Toast.makeText(this.context, "hee haaa", Toast.LENGTH_LONG).show();
        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        if(!myBluetooth.isEnabled())
        {
            //Ask to the user turn the bluetooth on
            Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            context.startActivity(turnBTon);
            Toast.makeText(this.context, "switch it on", Toast.LENGTH_SHORT).show();

        }
        else if(myBluetooth.isEnabled())
        {
            btactivity();
        }
        else
        {
            Toast.makeText(this.context, "didint work", Toast.LENGTH_LONG).show();
        }


    }
    public void btactivity()
    {
        Toast.makeText(this.context, "calling", Toast.LENGTH_LONG).show();
        Runnable runnable = new Runnable() {
            public void run() {

                try {

                    positive = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = positive.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    btSocket.connect();

                }catch (Exception e)
                {
                }
                if(btSocket.isConnected())
                {

                    try {
                        mmOutputStream = btSocket.getOutputStream();

                    } catch (IOException e) {

                    }
                    try {
                        mmOutputStream.write('a');
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();
    }

    }

