package com.radioknit.ummi;


import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.OutputStream;
import java.util.ArrayList;

import static com.radioknit.ummi.MainActivity.count_loader_ProgrammableParameter;
import static com.radioknit.ummi.MainActivity.count_loader_SpeedSelection;
import static com.radioknit.ummi.MainActivity.isConnected;
import static com.radioknit.ummi.MainActivity.sendMessage;
import static com.radioknit.ummi.MainActivity.str11Speed;


public class SpeedSelectionActivity extends AppCompatActivity {

    private static final String TAG = "SpeedSelectionActivity";
    private Context mContext;
    ArrayList<String> arrCommandValueList;
    private ArrayAdapter<String> adapter;
    private Button btnSetSpeedCommandSelection;
    private Spinner spinWhenLiftIsStoped;
    private Spinner spinPowerUpMode;
    private Spinner spinPowerUpModeWithClossed;
    private Spinner spinRelevelSpeed;
    private Spinner spinARDMode;
    private Spinner spinHitofSlowDown;
    private Spinner spinDeaccelerationMode;
    private Spinner spinRunSpeedOne;
    private Spinner spinRunSpeedTwo;
    private Spinner spinRunSpeedThree;
    private Spinner spinRunSpeedFour;
    private Button btnViewSpeedCommand;
    private int counter = 0;
    private TextView txtRunSpeedFour;
    private TextView txtWhenLiftIsStoped;
    private TextView txtPowerUpMode;
    private TextView txtPowerUpWithClosed;
    private TextView txtMaintanceSpeed;
    private TextView txtARDMode;
    private TextView txtHitOnSlowDown;
    private TextView txtDesccelarationMode;
    private TextView txtRunSpeedOne;
    private TextView txtRunSpeedTwo;
    private TextView txtRunSpeedThree;
    private BluetoothAdapter bluetoothAdapter;

    // =============

    private OutputStream outputStream;
    private static final String DEVICE_NAME = "DEVICE_NAME";
    private static final String LOG = "LOG";

    private ProgressDialog pd;
    private StringBuffer completReceivedString;

    private static boolean receiveFlag = false;
    private static String strTemp = "";
    final Handler myHandlerChk = new Handler();
    private Menu menu;

    private LinearLayout llSetSpeed;
    private RelativeLayout rlViewSpeed;
    private Button btnSetSSValues;
    private Button btnGetSSValues;
    private Spinner spinCmd;
    ArrayAdapter<String> adapterCmd;
    ArrayList<String> arrCmdValue;
    ArrayAdapter<String> adapterCmdValue;
    Spinner spinCmdValue;
    Button btnSetCmd;
    Button btnViewValue;
    private int cntSetText = 0;
    private TextView txtViewValue;
    int spinFlr = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_speed_selection);

        generateId();
        createObj();
        registerEvent();
        myHandlerChk.postDelayed(checkDataContinue, 0);
    }


    private void generateId() {
        btnSetSpeedCommandSelection = (Button) findViewById(R.id.btnSetSpeedCommandSelection);
        spinWhenLiftIsStoped = (Spinner)findViewById(R.id.spinWhenLiftIsStopped);
        spinPowerUpMode = (Spinner)findViewById(R.id.spinPowerUpMode);
        spinPowerUpModeWithClossed = (Spinner)findViewById(R.id.spinPowerUpWithClossed);
        spinRelevelSpeed = (Spinner)findViewById(R.id.spinMaintanceSpeed);
        spinARDMode = (Spinner)findViewById(R.id.spinARDMode);
        spinHitofSlowDown = (Spinner)findViewById(R.id.spinHitOnSlowDown);
        spinDeaccelerationMode = (Spinner)findViewById(R.id.spinDeaccelerationMode);
        spinRunSpeedOne = (Spinner)findViewById(R.id.spinRunSpeedOne);
        spinRunSpeedTwo = (Spinner)findViewById(R.id.spinRunSpeedTwo);
        spinRunSpeedThree = (Spinner)findViewById(R.id.spinRunSpeedThree);
        spinRunSpeedFour = (Spinner)findViewById(R.id.spinRunSpeedFour);
        btnViewSpeedCommand = (Button)findViewById(R.id.btnViewSpeedCommandSelection);

        txtWhenLiftIsStoped = (TextView)findViewById(R.id.tvDefalutWhenLiftIsStopped);
        txtPowerUpMode = (TextView)findViewById(R.id.tvDefalutpowerUpMode);
        txtPowerUpWithClosed = (TextView)findViewById(R.id.tvDefalutPowerUpWithClossed);
        txtMaintanceSpeed = (TextView)findViewById(R.id.tvDefalultMaintanceSpeed);
        txtARDMode = (TextView)findViewById(R.id.tvDefaultARDMode);
        txtHitOnSlowDown = (TextView)findViewById(R.id.tvDefaultHitOnSlowDown);
        txtDesccelarationMode = (TextView)findViewById(R.id.tvDefaultDeaccelarationMode);
        txtRunSpeedOne = (TextView)findViewById(R.id.tvDefaultRunSpeedOne);
        txtRunSpeedTwo = (TextView)findViewById(R.id.tvDefaultRunSpeedTwo);
        txtRunSpeedThree = (TextView)findViewById(R.id.tvDefaultRunSpeedThree);
        txtRunSpeedFour = (TextView)findViewById(R.id.tvDefaultRunSpeedFour);

        llSetSpeed = (LinearLayout) findViewById(R.id.llSpeedCmdSetValues);
        rlViewSpeed = (RelativeLayout) findViewById(R.id.rlViewSpeedCmdValues);
        btnSetSSValues = (Button) findViewById(R.id.btnSetSpeedCmd);
        btnGetSSValues = (Button) findViewById(R.id.btnViewSpeedCmd);
        spinCmd = (Spinner) findViewById(R.id.spin_cmd);
        btnSetCmd = (Button) findViewById(R.id.btnSetCmd);
        btnViewValue = (Button) findViewById(R.id.btnViewValue);
        txtViewValue = (TextView)findViewById(R.id.textViewValue);
        spinCmdValue = (Spinner)findViewById(R.id.spin_value);

    }


    private void createObj() {
        getSupportActionBar().setTitle("Speed Selection");
        mContext = SpeedSelectionActivity.this;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        arrCommandValueList = new ArrayList<String>();

        arrCommandValueList.add("NC");
        for(int i = 0; i< 8; i++){
            arrCommandValueList.add(String.valueOf(i));
        }

        adapter = new ArrayAdapter<String>(mContext, R.layout.list_item, arrCommandValueList);

        spinWhenLiftIsStoped.setAdapter(adapter);
        spinPowerUpMode.setAdapter(adapter);
        spinPowerUpModeWithClossed.setAdapter(adapter);
        spinRelevelSpeed.setAdapter(adapter);
        spinARDMode.setAdapter(adapter);
        spinHitofSlowDown.setAdapter(adapter);
        spinDeaccelerationMode.setAdapter(adapter);
        spinRunSpeedOne.setAdapter(adapter);
        spinRunSpeedTwo.setAdapter(adapter);
        spinRunSpeedThree.setAdapter(adapter);
        spinRunSpeedFour.setAdapter(adapter);

        adapterCmd = new ArrayAdapter<String>(mContext, R.layout.list_item, getResources().getStringArray(R.array.arr_speed_selection));

        spinCmd.setAdapter(adapterCmd);

        arrCmdValue = new ArrayList<String>();
        adapterCmdValue = new ArrayAdapter<String>(mContext, R.layout.list_item, arrCmdValue);
        adapterCmdValue.add("Select Value");
        for (int i = 0; i < 8; i++) {
            adapterCmdValue.add(String.valueOf(i));
        }
        spinCmdValue.setAdapter(adapterCmdValue);

    }
    void delay(){
        try {
            Thread.sleep(50);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void delaySet(){
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void registerEvent() {
        final Handler ha = new Handler();
        btnSetSpeedCommandSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected()){
                    callWhenLiftIsStoped();
                    delaySet();
                    callPowerUpMode();
                    delaySet();
                    callPowerUpModeWithClossed();
                    delaySet();
                    callMaintanceSpeed();
                    delaySet();
                    callARDMode();
                    delaySet();
                    callHotOfSlow();
                    delaySet();
                    callDeaccelrationMode();
                    delaySet();
                    callRunSpeedOne();
                    delaySet();
                    callRunSpeedTwo();
                    delaySet();
                    callRunSpeedThree();
                    delaySet();
                    callRunSpeedFour();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnViewSpeedCommand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //completReceivedString.setLength(0);
                counter = 0;
                if (isConnected()) {
                    pd = ProgressDialog.show(mContext, "", "Please wait", true);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
                }
               /* boolean b = ha.postDelayed(new Runnable() {
                    @Override
                    public void run() {*/
                        //call function
                        Log.d(TAG, "run:inside count ="+counter);
                        if (counter == 0) {
                            callViewSpeedSelectionCommand(counter);
                            delay();
                            counter++;
                        } else if (counter == 1) {
                                callViewSpeedSelectionCommand(counter);
                            delay();
                                counter++;
                        } else if (counter == 2) {
                                callViewSpeedSelectionCommand(counter);
                            delay();
                                counter++;
                        } else if (counter == 3) {
                                callViewSpeedSelectionCommand(counter);
                            delay();
                                counter++;
                        } else if (counter == 4) {
                                callViewSpeedSelectionCommand(counter);
                            delay();
                                counter++;
                        } else if (counter == 5) {
                                callViewSpeedSelectionCommand(counter);
                            delay();
                                counter++;
                        } else if (counter == 6) {
                                callViewSpeedSelectionCommand(counter);
                            delay();
                                counter++;
                        } else if (counter == 7) {
                                callViewSpeedSelectionCommand(counter);
                            delay();
                                counter++;
                        } else if (counter == 8) {
                                callViewSpeedSelectionCommand(counter);
                            delay();
                                counter++;
                        } else if (counter == 9) {
                                callViewSpeedSelectionCommand(counter);
                            delay();
                                counter++;
                        }else if (counter == 10) {
                                callViewSpeedSelectionCommand(counter);
                            delay();
                                counter++;
                        }else if(counter == 11){
                                counter++;
                            if(isConnected()){
                               // pd.dismiss();
                                Log.d(TAG, "run: count = "+count_loader_SpeedSelection);
                                //todo change payal
                                int timeout = 0;
                                while (true) {
                                    if (count_loader_SpeedSelection == 11) {
                                        if (pd.isShowing())
                                            pd.dismiss();
                                        break;
                                    } else {
                                        if (timeout == 3){
                                            Log.d("Tag_counter", "run: timeout = "+timeout);
                                            Toast.makeText(mContext, "TimeOut! Device is slow", Toast.LENGTH_SHORT).show();
                                            if (pd.isShowing())
                                                pd.dismiss();
                                            break;
                                        }
                                        delay();
                                        timeout ++;
                                    }
                                }
                            }

                                showReceivedDataNew();
                       }
                      //  ha.postDelayed(this, 500);
                    }
              /*  }, 500);
    }*/
        });


        btnSetSSValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llSetSpeed.setVisibility(View.VISIBLE);
                rlViewSpeed.setVisibility(View.GONE);
            }
        });

        btnGetSSValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llSetSpeed.setVisibility(View.GONE);
                rlViewSpeed.setVisibility(View.VISIBLE);
            }
        });

        btnViewValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cntSetText = 0;
                int floorNo = Integer.valueOf(String.valueOf(spinCmd.getSelectedItemPosition()));

                int a1 = 18;
                int a2 = 17;
                int a3 = 80;
                int a4 = 240 + floorNo;
                int a5 = 00;
                int a6 = 00;

                int[] sendValChkSum={a1, a2, a3, a4, a5, a6};

                String strChkSum= CalculateCheckSum.calculateChkSum(sendValChkSum);

                int sum = a1 + a2 + a3 + a4 + a5;
                String sumHex = String.format("%04x", sum);
                String asciiString  = String.format("%04x", a1).substring(2,4)+String.format("%04x", a2).substring(2,4)+String.format("%04x", a3).substring(2,4)+String.format("%04x", a4).substring(2,4)+String.format("%04x", a5).substring(2,4)+String.format("%04x", a6).substring(2,4) ;
                asciiString = asciiString + strChkSum + "\r";
//                Log.e(TAG, "asciiString = "+ asciiString);

                if (isConnected()) {
                    spinFlr = floorNo;
                    cntSetText = 1;
                    sendMessage(asciiString.getBytes());
                }
            }
        });


        btnSetCmd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callSetCmd();
            }
        });
    }

    //TODO : protocol for parameter
    public void callSetCmd(){
        if(spinCmdValue.getSelectedItem().toString().equals("Select Value")){
            Toast.makeText(getApplicationContext(),"Please select value",Toast.LENGTH_SHORT).show();
            return;
        }
        int floorNo = Integer.valueOf(String.valueOf(spinCmd.getSelectedItemPosition()));
        int a1 = 18;
        int a2 = 17;
        int a3 = 112;
        int a4 = 240 + floorNo;
        int a5 = Integer.parseInt(spinCmdValue.getSelectedItem().toString());
        int a6 = 00;

        int[] sendValChkSum = {a1, a2, a3, a4, a5, a6};
        String strChkSum = CalculateCheckSum.calculateChkSum(sendValChkSum);
        String asciiString = String.format("%04x", a1).substring(2, 4) + String.format("%04x", a2).substring(2, 4) + String.format("%04x", a3).substring(2, 4) + String.format("%04x", a4).substring(2, 4) + String.format("%04x", a5).substring(2, 4) + String.format("%04x", a6).substring(2, 4);
        asciiString = asciiString + strChkSum + "\r";
//        Log.e(TAG, "asciiString = " + asciiString);

        if (isConnected()) {
            sendMessage(asciiString.getBytes());
        } else {
            Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
        }

    }



    public void callWhenLiftIsStoped() {
        if(!spinWhenLiftIsStoped.getSelectedItem().toString().equals("NC")) {
            int a1 = 18;
            int a2 = 17;
            int a3 = 112;
            int a4 = 240;
            int a5 = Integer.parseInt(spinWhenLiftIsStoped.getSelectedItem().toString());
            int a6 = 00;


            int[] sendValChkSum = {a1, a2, a3, a4, a5, a6};
            String strChkSum = CalculateCheckSum.calculateChkSum(sendValChkSum);
            String asciiString = String.format("%04x", a1).substring(2, 4) + String.format("%04x", a2).substring(2, 4) + String.format("%04x", a3).substring(2, 4) + String.format("%04x", a4).substring(2, 4) + String.format("%04x", a5).substring(2, 4) + String.format("%04x", a6).substring(2, 4);
            asciiString = asciiString + strChkSum + "\r";
//            Log.e(TAG, "asciiString = " + asciiString);

            if (isConnected()) {
                //connector.write(asciiString.getBytes());
                sendMessage(asciiString.getBytes());
            }
        /*else {
            Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
        }*/
        }
    }

    public void callPowerUpMode() {
        if(!spinPowerUpMode.getSelectedItem().toString().equals("NC")) {
            int a1 = 18;
            int a2 = 17;
            int a3 = 112;
            int a4 = 241;
            int a5 = Integer.parseInt(spinPowerUpMode.getSelectedItem().toString());
            int a6 = 00;

            int[] sendValChkSum = {a1, a2, a3, a4, a5, a6};
            String strChkSum = CalculateCheckSum.calculateChkSum(sendValChkSum);
            String asciiString = String.format("%04x", a1).substring(2, 4) + String.format("%04x", a2).substring(2, 4) + String.format("%04x", a3).substring(2, 4) + String.format("%04x", a4).substring(2, 4) + String.format("%04x", a5).substring(2, 4) + String.format("%04x", a6).substring(2, 4);
            asciiString = asciiString + strChkSum + "\r";
//            Log.e(TAG, "asciiString = " + asciiString);

            if (isConnected()) {
                //connector.write(asciiString.getBytes());
                sendMessage(asciiString.getBytes());
            }
        /*else {
            Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
        }*/
        }
    }

    public void callPowerUpModeWithClossed() {
        if(!spinPowerUpModeWithClossed.getSelectedItem().toString().equals("NC")) {
            int a1 = 18;
            int a2 = 17;
            int a3 = 112;
            int a4 = 242;
            int a5 = Integer.parseInt(spinPowerUpModeWithClossed.getSelectedItem().toString());
            int a6 = 00;

            int[] sendValChkSum = {a1, a2, a3, a4, a5, a6};
            String strChkSum = CalculateCheckSum.calculateChkSum(sendValChkSum);
            String asciiString = String.format("%04x", a1).substring(2, 4) + String.format("%04x", a2).substring(2, 4) + String.format("%04x", a3).substring(2, 4) + String.format("%04x", a4).substring(2, 4) + String.format("%04x", a5).substring(2, 4) + String.format("%04x", a6).substring(2, 4);
            asciiString = asciiString + strChkSum + "\r";
//            Log.e(TAG, "asciiString = " + asciiString);

            if (isConnected()) {
                //connector.write(asciiString.getBytes());
                sendMessage(asciiString.getBytes());
            }
        /*else {
            Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
        }*/
        }
    }

    public void callMaintanceSpeed() {
        if(!spinRelevelSpeed.getSelectedItem().toString().equals("NC")) {
            int a1 = 18;
            int a2 = 17;
            int a3 = 112;
            int a4 = 243;
            int a5 = Integer.parseInt(spinRelevelSpeed.getSelectedItem().toString());
            int a6 = 00;

            int[] sendValChkSum = {a1, a2, a3, a4, a5, a6};
            String strChkSum = CalculateCheckSum.calculateChkSum(sendValChkSum);
            String asciiString = String.format("%04x", a1).substring(2, 4) + String.format("%04x", a2).substring(2, 4) + String.format("%04x", a3).substring(2, 4) + String.format("%04x", a4).substring(2, 4) + String.format("%04x", a5).substring(2, 4) + String.format("%04x", a6).substring(2, 4);
            asciiString = asciiString + strChkSum + "\r";
//            Log.e(TAG, "asciiString = " + asciiString);

            if (isConnected()) {
                //connector.write(asciiString.getBytes());
                sendMessage(asciiString.getBytes());
            }
        /*else {
            Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
        }*/
        }
    }

    public void callARDMode() {
        if(!spinARDMode.getSelectedItem().toString().equals("NC")) {
            int a1 = 18;
            int a2 = 17;
            int a3 = 112;
            int a4 = 244;
            int a5 = Integer.parseInt(spinARDMode.getSelectedItem().toString());
            int a6 = 00;

            int[] sendValChkSum = {a1, a2, a3, a4, a5, a6};
            String strChkSum = CalculateCheckSum.calculateChkSum(sendValChkSum);
            String asciiString = String.format("%04x", a1).substring(2, 4) + String.format("%04x", a2).substring(2, 4) + String.format("%04x", a3).substring(2, 4) + String.format("%04x", a4).substring(2, 4) + String.format("%04x", a5).substring(2, 4) + String.format("%04x", a6).substring(2, 4);
            asciiString = asciiString + strChkSum + "\r";
//            Log.e(TAG, "asciiString = " + asciiString);

            if (isConnected()) {
                //connector.write(asciiString.getBytes());
                sendMessage(asciiString.getBytes());
            }
        /*else {
            Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
        }*/
        }

    }

    public void callHotOfSlow() {
        if(!spinHitofSlowDown.getSelectedItem().toString().equals("NC")) {
            int a1 = 18;
            int a2 = 17;
            int a3 = 112;
            int a4 = 245;
            int a5 = Integer.parseInt(spinHitofSlowDown.getSelectedItem().toString());
            int a6 = 00;

            int[] sendValChkSum = {a1, a2, a3, a4, a5, a6};
            String strChkSum = CalculateCheckSum.calculateChkSum(sendValChkSum);
            String asciiString = String.format("%04x", a1).substring(2, 4) + String.format("%04x", a2).substring(2, 4) + String.format("%04x", a3).substring(2, 4) + String.format("%04x", a4).substring(2, 4) + String.format("%04x", a5).substring(2, 4) + String.format("%04x", a6).substring(2, 4);
            asciiString = asciiString + strChkSum + "\r";
//            Log.e(TAG, "asciiString = " + asciiString);

            if (isConnected()) {
                //connector.write(asciiString.getBytes());
                sendMessage(asciiString.getBytes());
            }
        /*else {
            Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
        }*/
        }
    }

    public void callDeaccelrationMode() {
        if(!spinDeaccelerationMode.getSelectedItem().toString().equals("NC")) {
            int a1 = 18;
            int a2 = 17;
            int a3 = 112;
            int a4 = 246;
            int a5 = Integer.parseInt(spinDeaccelerationMode.getSelectedItem().toString());
            int a6 = 00;

            int[] sendValChkSum = {a1, a2, a3, a4, a5, a6};
            String strChkSum = CalculateCheckSum.calculateChkSum(sendValChkSum);
            String asciiString = String.format("%04x", a1).substring(2, 4) + String.format("%04x", a2).substring(2, 4) + String.format("%04x", a3).substring(2, 4) + String.format("%04x", a4).substring(2, 4) + String.format("%04x", a5).substring(2, 4) + String.format("%04x", a6).substring(2, 4);
            asciiString = asciiString + strChkSum + "\r";
//            Log.e(TAG, "asciiString = " + asciiString);

            if (isConnected()) {
                //connector.write(asciiString.getBytes());
                sendMessage(asciiString.getBytes());
            }
        /*else {
            Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
        }*/
        }
    }

    public void callRunSpeedOne() {
        if(!spinRunSpeedOne.getSelectedItem().toString().equals("NC")) {
            int a1 = 18;
            int a2 = 17;
            int a3 = 112;
            int a4 = 247;
            int a5 = Integer.parseInt(spinRunSpeedOne.getSelectedItem().toString());
            int a6 = 00;

            int[] sendValChkSum = {a1, a2, a3, a4, a5, a6};
            String strChkSum = CalculateCheckSum.calculateChkSum(sendValChkSum);
            String asciiString = String.format("%04x", a1).substring(2, 4) + String.format("%04x", a2).substring(2, 4) + String.format("%04x", a3).substring(2, 4) + String.format("%04x", a4).substring(2, 4) + String.format("%04x", a5).substring(2, 4) + String.format("%04x", a6).substring(2, 4);
            asciiString = asciiString + strChkSum + "\r";
//            Log.e(TAG, "asciiString = " + asciiString);

            if (isConnected()) {
                //connector.write(asciiString.getBytes());
                sendMessage(asciiString.getBytes());
            }
       /* else {
            Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
        }
*/
        }
    }

    public void callRunSpeedTwo() {
        if(!spinRunSpeedTwo.getSelectedItem().toString().equals("NC")) {
            int a1 = 18;
            int a2 = 17;
            int a3 = 112;
            int a4 = 248;
            int a5 = Integer.parseInt(spinRunSpeedTwo.getSelectedItem().toString());
            int a6 = 00;

            int[] sendValChkSum = {a1, a2, a3, a4, a5, a6};
            String strChkSum = CalculateCheckSum.calculateChkSum(sendValChkSum);
            String asciiString = String.format("%04x", a1).substring(2, 4) + String.format("%04x", a2).substring(2, 4) + String.format("%04x", a3).substring(2, 4) + String.format("%04x", a4).substring(2, 4) + String.format("%04x", a5).substring(2, 4) + String.format("%04x", a6).substring(2, 4);
            asciiString = asciiString + strChkSum + "\r";
//            Log.e(TAG, "asciiString = " + asciiString);

            if (isConnected()) {
                // connector.write(asciiString.getBytes());
                sendMessage(asciiString.getBytes());
            }
       /* else {
            Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
        }*/
        }
    }

    public void callRunSpeedThree() {
        if(!spinRunSpeedThree.getSelectedItem().toString().equals("NC")) {
            int a1 = 18;
            int a2 = 17;
            int a3 = 112;
            int a4 = 249;
            int a5 = Integer.parseInt(spinRunSpeedThree.getSelectedItem().toString());
            int a6 = 00;

            int[] sendValChkSum = {a1, a2, a3, a4, a5, a6};
            String strChkSum = CalculateCheckSum.calculateChkSum(sendValChkSum);
            String asciiString = String.format("%04x", a1).substring(2, 4) + String.format("%04x", a2).substring(2, 4) + String.format("%04x", a3).substring(2, 4) + String.format("%04x", a4).substring(2, 4) + String.format("%04x", a5).substring(2, 4) + String.format("%04x", a6).substring(2, 4);
            asciiString = asciiString + strChkSum + "\r";
//            Log.e(TAG, "asciiString = " + asciiString);

            if (isConnected()) {
                // connector.write(asciiString.getBytes());
                sendMessage(asciiString.getBytes());
            }
        /*else {
            Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
        }*/
        }
    }
    public void callRunSpeedFour() {
        if(!spinRunSpeedFour.getSelectedItem().toString().equals("NC")) {
            int a1 = 18;
            int a2 = 17;
            int a3 = 112;
            int a4 = 250;
            int a5 = Integer.parseInt(spinRunSpeedFour.getSelectedItem().toString());
            int a6 = 00;

            int[] sendValChkSum = {a1, a2, a3, a4, a5, a6};
            String strChkSum = CalculateCheckSum.calculateChkSum(sendValChkSum);
            String asciiString = String.format("%04x", a1).substring(2, 4) + String.format("%04x", a2).substring(2, 4) + String.format("%04x", a3).substring(2, 4) + String.format("%04x", a4).substring(2, 4) + String.format("%04x", a5).substring(2, 4) + String.format("%04x", a6).substring(2, 4);
            asciiString = asciiString + strChkSum + "\r";
//            Log.e(TAG, "asciiString = " + asciiString);

            if (isConnected()) {
                //connector.write(asciiString.getBytes());
                sendMessage(asciiString.getBytes());
            }
        /*else {
            Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
        }*/
        }
    }


    private void callViewSpeedSelectionCommand(int value) {
        int a1 = 18;
        int a2 = 17;
        int a3 = 80;
        int a4 = 240 + value;
        int a5 = 00;
        int a6 = 00;

        int[] sendValChkSum={a1, a2, a3, a4, a5, a6};

        String strChkSum= CalculateCheckSum.calculateChkSum(sendValChkSum);

        int sum = a1 + a2 + a3 + a4 + a5;
        String sumHex = String.format("%04x", sum);

        /*String msb = sumHex.substring(1, 2);
        String lsb = sumHex.substring(2, 4);
        int a7 = Integer.parseInt(lsb, 16);
        int msb2 = (Integer.parseInt(msb) | 50);
        int a8 = Integer.parseInt(String.valueOf(msb2),16);

        byte[] br2 = {(byte) a1, (byte) a2, (byte) a3, (byte) a4, (byte) a5, (byte) a6};
        byte[] br1 = {(byte) a1, (byte) a2, (byte) a3, (byte) a4, (byte) a5, (byte) a6, (byte) a7, (byte) a8};
*/
        String asciiString  = String.format("%04x", a1).substring(2,4)+String.format("%04x", a2).substring(2,4)+String.format("%04x", a3).substring(2,4)+String.format("%04x", a4).substring(2,4)+String.format("%04x", a5).substring(2,4)+String.format("%04x", a6).substring(2,4) ;
/*        int sumSendString  = 0;
        for(int i = 0; i<asciiString.length(); i++){
            sumSendString = sumSendString + Integer.parseInt(String.format("%04x", (int) asciiString.charAt(i)).substring(2,4));
        }
        asciiString = asciiString +String.valueOf(sumSendString).substring(1,3)+ "\r";*/
        asciiString = asciiString + strChkSum + "\r";
//        Log.e(TAG, "asciiString = "+ asciiString);

        if (isConnected()) {
//            connector.write(br1);
            //connector.write(asciiString.getBytes());
            sendMessage(asciiString.getBytes());
        }
        /*else {
            Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
        }*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_write_mode, menu);
        this.menu = menu;
        return true;
    }
    // ============================================================================


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.wroteModeEnable :
                Intent intent = new Intent(mContext, WriteModeEnableActivity.class);
                startActivity(intent);
                return true;
          /*  case R.id.menu_search:
                final int REQUEST_CONNECT_DEVICE = 2;
                Intent serverIntent = null;
                serverIntent = new Intent(SpeedSelectionActivity.this, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //startActivity(new Intent(mContext, MainActivity.class));
        myHandlerChk.removeCallbacks(checkDataContinue);
        finish();
    }

    // ============================================================================

    private Runnable checkDataContinue = new Runnable() {

        public void run() {
            if (isConnected()) {
                try{
                    menu.findItem(R.id.menu_search).setIcon(ContextCompat.getDrawable(mContext, R.drawable.grn_bt));
                }
                catch (Exception e){
                    //Catch
                }
            }
            else {
                try{
                    menu.findItem(R.id.menu_search).setIcon(ContextCompat.getDrawable(mContext, R.drawable.red_bt));
                }
                catch (Exception e){
                    //Catch
                }
            }
            //showReceivedDataNew();
            if(cntSetText==1){
                setTextViewValue(spinFlr);
            }
            myHandlerChk.postDelayed(this, 100);
        }

    };

    public void setTextViewValue(int flrNo){

        if(!str11Speed[flrNo].equals("")){
            txtViewValue.setText(str11Speed[flrNo]);
            //cntSetText = 0;
        }

    }


    public void showReceivedDataNew(){

        if(!str11Speed[0].equals("")){
            txtWhenLiftIsStoped.setText(str11Speed[0]);
        }
        if(!str11Speed[1].equals("")){
            txtPowerUpMode.setText(str11Speed[1]);
        }
        if(!str11Speed[2].equals("")){
            txtPowerUpWithClosed.setText(str11Speed[2]);
        }
        if(!str11Speed[3].equals("")){
            txtMaintanceSpeed.setText(str11Speed[3]);
        }
        if(!str11Speed[4].equals("")){
            txtARDMode.setText(str11Speed[4]);
        }
        if(!str11Speed[5].equals("")){
            txtHitOnSlowDown.setText(str11Speed[5]);
        }
        if(!str11Speed[6].equals("")){
            txtDesccelarationMode.setText(str11Speed[6]);
        }
        if(!str11Speed[7].equals("")){
            txtRunSpeedOne.setText(str11Speed[7]);
        }
        if(!str11Speed[8].equals("")){
            txtRunSpeedTwo.setText(str11Speed[8]);
        }
        if(!str11Speed[9].equals("")){
            txtRunSpeedThree.setText(str11Speed[9]);
        }
        if(!str11Speed[10].equals("")){
            txtRunSpeedFour.setText(str11Speed[10]);
        }

    }

}
