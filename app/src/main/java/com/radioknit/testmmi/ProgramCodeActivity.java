package com.radioknit.testmmi;


import android.app.ProgressDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import static com.radioknit.testmmi.MainActivity.count_loader_ReadPLC;
import static com.radioknit.testmmi.MainActivity.isConnected;
import static com.radioknit.testmmi.MainActivity.sendMessage;
import static com.radioknit.testmmi.MainActivity.str11BreakHiPulse;
import static com.radioknit.testmmi.MainActivity.str11ClockDivide;
import static com.radioknit.testmmi.MainActivity.str11CompulsoryStop;
import static com.radioknit.testmmi.MainActivity.str11ControlBit;
import static com.radioknit.testmmi.MainActivity.str11DoorCloseTime;
import static com.radioknit.testmmi.MainActivity.str11DoorKeepOpenTime;
import static com.radioknit.testmmi.MainActivity.str11DoorOpenTime;
import static com.radioknit.testmmi.MainActivity.str11FireFloor;
import static com.radioknit.testmmi.MainActivity.str11HomeFloor;
import static com.radioknit.testmmi.MainActivity.str11NoOfFloors;
import static com.radioknit.testmmi.MainActivity.str11ParkingFloor;
import static com.radioknit.testmmi.MainActivity.str11StopDelay;
import static com.radioknit.testmmi.MainActivity.str11TransitDelay;

public class ProgramCodeActivity extends AppCompatActivity {

    private static final String TAG = "ProgramCodeActivity";
    ArrayList<String> arrCommandValueList;
    ArrayList<String> arrBreakPulseList;
    ArrayList<String> arrNoOfFloorValueList;
    ArrayList<String> arrControlBit;
    ArrayList<String> arrDoorOpenTime;
    ArrayList<String> arrClockDivide;
    ArrayList<String> arrFireFloor;
    private Spinner spinStopDelay;
    private ArrayAdapter<String> adapter;
    private Spinner spinTransitDelay;
    private Spinner spinBreakHipulse;
    private Spinner spinNoOfFloors;
    private Spinner spinDoorOpenTime;
    private Spinner spinDoorCloseTime;
    private Spinner spinDoorKeepOpenTime;
    private Spinner spinClockDivide;
    private Spinner spinControlBit;
    private Spinner spinFireFloor;
    private Spinner spinHomeFloor;
    private Spinner spinCompulsaryStop;
    private Spinner spinParkingFloor;
    private TextView txtStopDelay;
    private TextView txtTransitDelay;
    private TextView txtBreakHiPulse;
    private TextView txtNoOfFloors;
    private TextView txtDoorOpenTime;
    private TextView txtDoorCloseTime;
    private TextView txtDoorDoorKeepOpenTime;
    private TextView txtClockDivide;
    private TextView txtControlBit;
    private TextView txtFireFloor;
    private TextView txtHomeFloor;
    private TextView txtCompulsaryStop;
    private TextView txtParkingFloor;
    private ArrayAdapter<String> adapterNoOfFloor;
    private ArrayAdapter<String> adapterBreakPulse;
    private ArrayAdapter<String> adapterControlBit;
    private ArrayAdapter<String> adapterDoorOpenTime;
    private ArrayAdapter<String> adapterClockDivide;
    private ArrayAdapter<String> adapterFireFloor;
    private Button btnSetProgramCode;
    private Button btnReadPlc;
    private Context mContext;

    private LinearLayout llSetProgramCode;
    private RelativeLayout rlViewProgramCode;
    private Button btnSetPCValues;
    private Button btnGetPCValues;
    private Spinner spinCmd;
    ArrayAdapter<String> adapterCmd;
    ArrayList<String> arrCmdValue;
    ArrayAdapter<String> adapterCmdValue;
    Spinner spinCmdValue;

    private int counter = 1;
    private ProgressDialog pd;
    private StringBuffer completReceivedString;
    private static boolean receiveFlag = false;
    final Handler myHandlerChk = new Handler();
    private Menu menu;

    Button btnSetCmd;
    Button btnViewValue;
    private int cntSetText = 0;
    private TextView txtViewValue;
    int spinFlr = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // Log.e(TAG, "onCreate()");

        setContentView(R.layout.activity_program_code);

        completReceivedString = new StringBuffer();

        generateId();
        createObj();
        registerEvent();
        myHandlerChk.postDelayed(checkDataContinue, 0);

    }


    void delaySet(){
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void delayRead(){
        try {
            Thread.sleep(50);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registerEvent() {
        final Handler ha = new Handler();
        btnSetProgramCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected()) {
                    callStopDelay();
                    delaySet();
                    callTransitDelay();
                    delaySet();
                    callBreakHiPulseDelay();
                    delaySet();
                    callNoOfFloors();
                    delaySet();
                    callDoorOpenTime();
                    delaySet();
                    callDoorCloseTime();
                    delaySet();
                    callDoorKeepOpenTime();
                    delaySet();
                    callClockDivivde();
                    delaySet();
                    callControlBit();
                    delaySet();
                    callFireFloor();
                    delaySet();
                    callHomeFloor();
                    delaySet();
                    callCompulsaryStop();
                    delaySet();
                    callParkingFloor();
                    delaySet();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
                }
            }
        });




        btnReadPlc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completReceivedString.setLength(0);
                counter = 1;
                if (isConnected()) {
                    pd = ProgressDialog.show(mContext, "", "Please wait", true);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
                }
                boolean b = ha.postDelayed(new Runnable() {


                    @Override
                    public void run() {
                        if(counter == 1){
                            callReadPLC(130);
                            delayRead();
                            counter++;
                        }else if(counter ==  2 ){
                                callReadPLC(131);
                                delayRead();
                                counter++;
                        }else if(counter == 3){
                                callReadPLC(132);
                                delayRead();
                                counter++;
                        }else if(counter == 4){
                                callReadPLC(133);
                                delayRead();
                                counter++;
                        }else if(counter == 5){
                                callReadPLC(134);
                            delayRead();
                                counter++;
                        }else if(counter == 6){
                                callReadPLC(135);
                            delayRead();
                                counter++;
                        }else if(counter == 7){
                                callReadPLC(136);
                            delayRead();
                                counter++;
                        }else if(counter == 8){
                                callReadPLC(137);
                            delayRead();
                                counter++;
                        }else if(counter == 9){
                                callReadPLC(138);
                            delayRead();
                                counter++;
                        }else if(counter == 10){
                                callReadPLC(139);
                            delayRead();
                                counter++;
                        }else if(counter == 11){
                                callReadPLC(140);
                            delayRead();
                                counter++;
                        }else if(counter == 12){
                                callReadPLC(141);
                            delayRead();
                                counter++;
                        }else if(counter == 13){
                                callReadPLC(142);
                            delayRead();
                                counter++;
                        }else if(counter == 14){
                            if (isConnected()) {
                                Log.d("Tag_counter", "run: count val = "+count_loader_ReadPLC);

                               //todo change payal
                                int timeout = 0;
                                while (true) {
                                    if (count_loader_ReadPLC == 13) {
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
                                        delayRead();
                                        timeout ++;
                                    }
                                }
                            }

                                showReceivedDataNew();
                                counter++;
                        }
                        if (counter<15)
                             ha.postDelayed(this, 500);
                    }
                }, 500);
            }
        });


        btnSetPCValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llSetProgramCode.setVisibility(View.VISIBLE);
                rlViewProgramCode.setVisibility(View.GONE);
            }
        });

        btnGetPCValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llSetProgramCode.setVisibility(View.GONE);
                rlViewProgramCode.setVisibility(View.VISIBLE);
            }
        });

        btnViewValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cntSetText = 0;
                int floorNo = Integer.valueOf(String.valueOf(spinCmd.getSelectedItemPosition()));
                int locVal = 130 + floorNo;

                int a1 = 18;
                int a2 = 17;
                int a3 = 80;
                int a4 = locVal;
                int a5 = 00;
                int a6 = 00;

                int[] sendValChkSum={a1, a2, a3, a4, a5, a6};
                String strChkSum= CalculateCheckSum.calculateChkSum(sendValChkSum);

                String asciiString  = String.format("%04x", a1).substring(2,4)+String.format("%04x", a2).substring(2,4)+String.format("%04x", a3).substring(2,4)+String.format("%04x", a4).substring(2,4)+String.format("%04x", a5).substring(2,4)+String.format("%04x", a6).substring(2,4) ;
                asciiString = asciiString + strChkSum + "\r";
//                Log.e(TAG, "asciiString = "+ asciiString);
                if(isConnected()) {
                    spinFlr = floorNo;
                    cntSetText = 1;
                    //connector.write(asciiString.getBytes());
                    sendMessage(asciiString.getBytes());
                }


                //callReadPLC(locVal);
            }
        });


        spinCmd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(adapterCmdValue!=null){
                    adapterCmdValue.clear();
                }
                adapterCmdValue.add("Select Value");
                // your code here
                switch (position){
                    case 0:
                        for (int i = 0; i <= 255; i++) {
                            adapterCmdValue.add(String.valueOf(i));
                        }
                        spinCmdValue.setAdapter(adapterCmdValue);
                        break;
                    case 1:
                        for (int i = 0; i <= 255; i++) {
                            adapterCmdValue.add(String.valueOf(i));
                        }
                        spinCmdValue.setAdapter(adapterCmdValue);
                        break;
                    case 2:
                        for (int i = 0; i <= 255; i++) {
                            adapterCmdValue.add(String.valueOf(i));
                        }
                        spinCmdValue.setAdapter(adapterCmdValue);
                        break;
                    case 3:
                        for (int i = 0; i <= 31; i++) {
                            adapterCmdValue.add(String.valueOf(i));
                        }
                        spinCmdValue.setAdapter(adapterCmdValue);
                        break;
                    case 4:
                        for (int i = 0; i < 16; i++) {
                            adapterCmdValue.add(String.valueOf(i));
                        }
                        spinCmdValue.setAdapter(adapterCmdValue);
                        break;
                    case 5:
                        for (int i = 0; i < 16; i++) {
                            adapterCmdValue.add(String.valueOf(i));
                        }
                        spinCmdValue.setAdapter(adapterCmdValue);
                        break;
                    case 6:
                        for (int i = 0; i < 16; i++) {
                            adapterCmdValue.add(String.valueOf(i));
                        }
                        spinCmdValue.setAdapter(adapterCmdValue);
                        break;
                    case 7:
                        for (int i = 0; i < 16; i++) {
                            adapterCmdValue.add(String.valueOf(i));
                        }
                        spinCmdValue.setAdapter(adapterCmdValue);
                        break;
                    case 8:
                        for (int i = 0; i <= 255; i++) {
                            adapterCmdValue.add(String.valueOf(i));
                        }
                        spinCmdValue.setAdapter(adapterCmdValue);
                        break;
                    case 9:
                        for (int i = 0; i <= 31; i++) {
                            adapterCmdValue.add(String.valueOf(i));
                        }
                        adapterCmdValue.add(String.valueOf(255));
                        spinCmdValue.setAdapter(adapterCmdValue);
                        break;
                    case 10:
                        for (int i = 0; i <= 31; i++) {
                            adapterCmdValue.add(String.valueOf(i));
                        }
                        adapterCmdValue.add(String.valueOf(255));
                        spinCmdValue.setAdapter(adapterCmdValue);
                        break;
                    case 11:
                        for (int i = 0; i <= 31; i++) {
                            adapterCmdValue.add(String.valueOf(i));
                        }
                        adapterCmdValue.add(String.valueOf(255));
                        spinCmdValue.setAdapter(adapterCmdValue);
                        break;
                    case 12:
                        for (int i = 0; i <= 31; i++) {
                            adapterCmdValue.add(String.valueOf(i));
                        }
                        adapterCmdValue.add(String.valueOf(255));
                        spinCmdValue.setAdapter(adapterCmdValue);
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        btnSetCmd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callSetCmd();
            }
        });
    }

    private void createObj() {
        getSupportActionBar().setTitle("Program Code");
        mContext = ProgramCodeActivity.this;

        arrCommandValueList = new ArrayList<String>();

        arrBreakPulseList=new ArrayList<String>();
        arrCommandValueList = new ArrayList<String>();
        arrNoOfFloorValueList = new ArrayList<String>();
        arrControlBit = new ArrayList<String>();
        arrDoorOpenTime = new ArrayList<String>();
        arrFireFloor = new ArrayList<String>();
        arrClockDivide = new ArrayList<String>();

        arrCommandValueList.add("NC");
        arrBreakPulseList.add("NC");
       // arrCommandValueList.add("NC");
        arrNoOfFloorValueList.add("NC");
        arrControlBit.add("NC");
        arrDoorOpenTime.add("NC");
        arrFireFloor.add("NC");
        arrClockDivide.add("NC");

        for (int i = 0; i <= 255; i++) {
            arrCommandValueList.add(String.valueOf(i));
        }

        for (int i = 0; i <= 255; i++) {
            arrBreakPulseList.add(String.valueOf(i));
        }

        for (int i = 0; i <= 31; i++) {
            arrNoOfFloorValueList.add(String.valueOf(i));
        }

        for (int i = 0; i <= 255; i++) {
            arrControlBit.add(String.valueOf(i));
        }

        for (int i = 0; i < 16; i++) {
            arrDoorOpenTime.add(String.valueOf(i));
        }

        for (int i = 0; i < 8; i++) {
            arrClockDivide.add(String.valueOf(i));
        }

        for (int i = 0; i <= 31; i++) {
            arrFireFloor.add(String.valueOf(i));
        }
        arrFireFloor.add(String.valueOf(255));

        adapter = new ArrayAdapter<String>(mContext, R.layout.list_item, arrCommandValueList);
        adapterBreakPulse= new ArrayAdapter<String>(mContext, R.layout.list_item, arrBreakPulseList);
        adapterNoOfFloor = new ArrayAdapter<String>(mContext, R.layout.list_item, arrNoOfFloorValueList);
        adapterControlBit = new ArrayAdapter<String>(mContext, R.layout.list_item, arrControlBit);
        adapterDoorOpenTime = new ArrayAdapter<String>(mContext, R.layout.list_item, arrDoorOpenTime);
        adapterClockDivide = new ArrayAdapter<String>(mContext, R.layout.list_item, arrClockDivide);
        adapterFireFloor = new ArrayAdapter<String>(mContext, R.layout.list_item, arrFireFloor);

        spinStopDelay.setAdapter(adapter);
        spinTransitDelay.setAdapter(adapter);
        spinBreakHipulse.setAdapter(adapter);
        spinNoOfFloors.setAdapter(adapterNoOfFloor);
        spinFireFloor.setAdapter(adapterFireFloor);
        spinHomeFloor.setAdapter(adapterFireFloor);
        spinCompulsaryStop.setAdapter(adapterFireFloor);
        spinParkingFloor.setAdapter(adapterFireFloor);
        spinControlBit.setAdapter(adapterControlBit);
        spinDoorOpenTime.setAdapter(adapterDoorOpenTime);
        spinDoorCloseTime.setAdapter(adapterDoorOpenTime);
        spinDoorKeepOpenTime.setAdapter(adapterDoorOpenTime);
        spinClockDivide.setAdapter(adapterDoorOpenTime);

        adapterCmd = new ArrayAdapter<String>(mContext, R.layout.list_item, getResources().getStringArray(R.array.arr_program_code));

        spinCmd.setAdapter(adapterCmd);

        arrCmdValue = new ArrayList<String>();
        adapterCmdValue = new ArrayAdapter<String>(mContext, R.layout.list_item, arrCmdValue);
        spinCmdValue.setAdapter(adapterCmdValue);

    }

    private void generateId() {

        llSetProgramCode = (LinearLayout) findViewById(R.id.llProgram_code_SetValues);
        rlViewProgramCode = (RelativeLayout) findViewById(R.id.rlViewProgram_code_Values);
        btnSetPCValues = (Button) findViewById(R.id.btnSetProgram_code);
        btnGetPCValues = (Button) findViewById(R.id.btnViewProgram_code);
        spinCmd = (Spinner) findViewById(R.id.spin_cmd);
        btnSetCmd = (Button) findViewById(R.id.btnSetCmd);
        btnViewValue = (Button) findViewById(R.id.btnViewValue);
        txtViewValue = (TextView)findViewById(R.id.textViewValue);
        spinCmdValue = (Spinner)findViewById(R.id.spin_value);

        btnSetProgramCode = (Button) findViewById(R.id.btnSetProgramCodes);
        btnReadPlc = (Button)findViewById(R.id.btnProgram_code_read_plc);
        spinStopDelay = (Spinner) findViewById(R.id.spinStopDelay);
        spinTransitDelay = (Spinner) findViewById(R.id.spinTransitDelay);
        spinBreakHipulse = (Spinner) findViewById(R.id.spinBreakHiPulse);
        spinNoOfFloors = (Spinner) findViewById(R.id.spinNoOfFloor);
        spinDoorOpenTime = (Spinner) findViewById(R.id.spinDoorOpenTime);
        spinDoorCloseTime = (Spinner) findViewById(R.id.spinDoorCloseTime);
        spinDoorKeepOpenTime = (Spinner) findViewById(R.id.spinDoorKeepOpenTime);
        spinClockDivide = (Spinner) findViewById(R.id.spinClockDivide);
        spinControlBit = (Spinner) findViewById(R.id.spinControlBit);
        spinFireFloor = (Spinner) findViewById(R.id.spinFireFloor);
        spinHomeFloor = (Spinner) findViewById(R.id.spinHomeFloor);
        spinCompulsaryStop = (Spinner) findViewById(R.id.spinCompulsaryStop);
        spinParkingFloor = (Spinner) findViewById(R.id.spinParkingFloor);

        txtStopDelay = (TextView) findViewById(R.id.tvStopDelayDefalut);
        txtTransitDelay = (TextView) findViewById(R.id.tvTransitDelay);
        txtBreakHiPulse = (TextView) findViewById(R.id.tvBreakHiPulse);
        txtNoOfFloors = (TextView) findViewById(R.id.tvNoOfFloor);
        txtDoorOpenTime = (TextView) findViewById(R.id.tvDoorOpenTime);
        txtDoorCloseTime = (TextView) findViewById(R.id.tvDoorCloseTime);
        txtDoorDoorKeepOpenTime = (TextView) findViewById(R.id.tvDoorKeepOpenTime);
        txtClockDivide = (TextView) findViewById(R.id.tvClockDivide);
        txtControlBit = (TextView) findViewById(R.id.tvControlBit);
        txtFireFloor = (TextView) findViewById(R.id.tvFireFloor);
        txtHomeFloor = (TextView) findViewById(R.id.tvHomeFloor);
        txtCompulsaryStop = (TextView) findViewById(R.id.tvCompulsaryStop);
        txtParkingFloor = (TextView) findViewById(R.id.tvParkingFloor);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //startActivity(new Intent(mContext, MainActivity.class));
        myHandlerChk.removeCallbacks(checkDataContinue);
        finish();
    }


    public void callSetCmd(){
       if(spinCmdValue.getSelectedItem().toString().equals("Select Value")){
           Toast.makeText(getApplicationContext(),"Please select value",Toast.LENGTH_SHORT).show();
           return;
       }

        int a1 = 18;
        int a2 = 17;
        int a3 = 112;
        int a4 = 130;
        int a5 = Integer.parseInt(spinCmdValue.getSelectedItem().toString());
        int a6 = 00;

        int floorNo = Integer.valueOf(String.valueOf(spinCmd.getSelectedItemPosition()));

        switch (floorNo) {
            case 0:
                a4 = 130;
                break;
            case 1:
                a4 = 131;
                break;
            case 2:
                a4 = 132;
                break;
            case 3:
                a4 = 133;
                break;
            case 4:
                a4 = 134;
                break;
            case 5:
                a4 = 135;
                break;
            case 6:
                a4 = 136;
                break;
            case 7:
                a4 = 137;
                break;
            case 8:
                a4 = 138;
                break;
            case 9:
                a4 = 139;
                break;
            case 10:
                a4 = 140;
                break;
            case 11:
                a4 = 141;
                break;
            case 12:
                a4 = 142;
                break;
        }



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


    // ==========================================================================

    public void callStopDelay() {
        if(!spinStopDelay.getSelectedItem().toString().equals("NC")) {
            int a1 = 18;
            int a2 = 17;
            int a3 = 112;
            int a4 = 130;
            int a5 = Integer.parseInt(spinStopDelay.getSelectedItem().toString());
            int a6 = 00;

            int[] sendValChkSum = {a1, a2, a3, a4, a5, a6};
            String strChkSum = CalculateCheckSum.calculateChkSum(sendValChkSum);
            String asciiString = String.format("%04x", a1).substring(2, 4) + String.format("%04x", a2).substring(2, 4) + String.format("%04x", a3).substring(2, 4) + String.format("%04x", a4).substring(2, 4) + String.format("%04x", a5).substring(2, 4) + String.format("%04x", a6).substring(2, 4);
            asciiString = asciiString + strChkSum + "\r";
//            Log.e(TAG, "asciiString = " + asciiString);

            if (isConnected()) {
//            connector.write(br1);
                //connector.write(asciiString.getBytes());
                sendMessage(asciiString.getBytes());
            } else {
                Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void callTransitDelay() {
        if(!spinTransitDelay.getSelectedItem().toString().equals("NC")) {
            int a1 = 18;
            int a2 = 17;
            int a3 = 112;
            int a4 = 131;
            int a5 = Integer.parseInt(spinTransitDelay.getSelectedItem().toString());
            int a6 = 00;

            int[] sendValChkSum = {a1, a2, a3, a4, a5, a6};
            String strChkSum = CalculateCheckSum.calculateChkSum(sendValChkSum);
            String asciiString = String.format("%04x", a1).substring(2, 4) + String.format("%04x", a2).substring(2, 4) + String.format("%04x", a3).substring(2, 4) + String.format("%04x", a4).substring(2, 4) + String.format("%04x", a5).substring(2, 4) + String.format("%04x", a6).substring(2, 4);
            asciiString = asciiString + strChkSum + "\r";
//            Log.e(TAG, "asciiString = " + asciiString);

            if (isConnected()) {
//            connector.write(br1);
                //connector.write(asciiString.getBytes());
                sendMessage(asciiString.getBytes());
            } else {
                Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void callBreakHiPulseDelay() {
        if(!spinBreakHipulse.getSelectedItem().toString().equals("NC")) {
            int a1 = 18;
            int a2 = 17;
            int a3 = 112;
            int a4 = 132;
            int a5 = Integer.parseInt(spinBreakHipulse.getSelectedItem().toString());
            int a6 = 00;

            int[] sendValChkSum = {a1, a2, a3, a4, a5, a6};
            String strChkSum = CalculateCheckSum.calculateChkSum(sendValChkSum);
            String asciiString = String.format("%04x", a1).substring(2, 4) + String.format("%04x", a2).substring(2, 4) + String.format("%04x", a3).substring(2, 4) + String.format("%04x", a4).substring(2, 4) + String.format("%04x", a5).substring(2, 4) + String.format("%04x", a6).substring(2, 4);
            asciiString = asciiString + strChkSum + "\r";
//            Log.e(TAG, "asciiString = " + asciiString);

            if (isConnected()) {
//            connector.write(br1);
                //connector.write(asciiString.getBytes());
                sendMessage(asciiString.getBytes());
            } else {
                Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void callNoOfFloors() {
        if(!spinNoOfFloors.getSelectedItem().toString().equals("NC")) {
            int a1 = 18;
            int a2 = 17;
            int a3 = 112;
            int a4 = 133;
            int a5 = Integer.parseInt(spinNoOfFloors.getSelectedItem().toString());
            int a6 = 00;

            int[] sendValChkSum = {a1, a2, a3, a4, a5, a6};
            String strChkSum = CalculateCheckSum.calculateChkSum(sendValChkSum);
            String asciiString = String.format("%04x", a1).substring(2, 4) + String.format("%04x", a2).substring(2, 4) + String.format("%04x", a3).substring(2, 4) + String.format("%04x", a4).substring(2, 4) + String.format("%04x", a5).substring(2, 4) + String.format("%04x", a6).substring(2, 4);
            asciiString = asciiString + strChkSum + "\r";
//            Log.e(TAG, "asciiString = " + asciiString);

            if (isConnected()) {
//            connector.write(br1);
                //connector.write(asciiString.getBytes());
                sendMessage(asciiString.getBytes());
            } else {
                Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void callDoorOpenTime() {
        if(!spinDoorOpenTime.getSelectedItem().toString().equals("NC")) {
            int a1 = 18;
            int a2 = 17;
            int a3 = 112;
            int a4 = 134;
            int a5 = Integer.parseInt(spinDoorOpenTime.getSelectedItem().toString());
            int a6 = 00;

            int[] sendValChkSum = {a1, a2, a3, a4, a5, a6};
            String strChkSum = CalculateCheckSum.calculateChkSum(sendValChkSum);
            String asciiString = String.format("%04x", a1).substring(2, 4) + String.format("%04x", a2).substring(2, 4) + String.format("%04x", a3).substring(2, 4) + String.format("%04x", a4).substring(2, 4) + String.format("%04x", a5).substring(2, 4) + String.format("%04x", a6).substring(2, 4);
            asciiString = asciiString + strChkSum + "\r";
//            Log.e(TAG, "asciiString = " + asciiString);
            if (isConnected()) {
//            connector.write(br1);
                //connector.write(asciiString.getBytes());
                sendMessage(asciiString.getBytes());
            } else {
                Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void callDoorCloseTime() {
        if(!spinDoorCloseTime.getSelectedItem().toString().equals("NC")) {
            int a1 = 18;
            int a2 = 17;
            int a3 = 112;
            int a4 = 135;
            int a5 = Integer.parseInt(spinDoorCloseTime.getSelectedItem().toString());
            int a6 = 00;

            int[] sendValChkSum = {a1, a2, a3, a4, a5, a6};
            String strChkSum = CalculateCheckSum.calculateChkSum(sendValChkSum);
            String asciiString = String.format("%04x", a1).substring(2, 4) + String.format("%04x", a2).substring(2, 4) + String.format("%04x", a3).substring(2, 4) + String.format("%04x", a4).substring(2, 4) + String.format("%04x", a5).substring(2, 4) + String.format("%04x", a6).substring(2, 4);
            asciiString = asciiString + strChkSum + "\r";
//            Log.e(TAG, "asciiString = " + asciiString);

            if (isConnected()) {
//            connector.write(br1);
                //connector.write(asciiString.getBytes());
                sendMessage(asciiString.getBytes());
            } else {
                Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void callDoorKeepOpenTime() {
        if(!spinDoorKeepOpenTime.getSelectedItem().toString().equals("NC")) {
            int a1 = 18;
            int a2 = 17;
            int a3 = 112;
            int a4 = 136;
            int a5 = Integer.parseInt(spinDoorKeepOpenTime.getSelectedItem().toString());
            int a6 = 00;

            int[] sendValChkSum = {a1, a2, a3, a4, a5, a6};
            String strChkSum = CalculateCheckSum.calculateChkSum(sendValChkSum);
            String asciiString = String.format("%04x", a1).substring(2, 4) + String.format("%04x", a2).substring(2, 4) + String.format("%04x", a3).substring(2, 4) + String.format("%04x", a4).substring(2, 4) + String.format("%04x", a5).substring(2, 4) + String.format("%04x", a6).substring(2, 4);
            asciiString = asciiString + strChkSum + "\r";
//            Log.e(TAG, "asciiString = " + asciiString);
            if (isConnected()) {
//            connector.write(br1);
                //connector.write(asciiString.getBytes());
                sendMessage(asciiString.getBytes());
            } else {
                Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void callClockDivivde() {
        if(!spinClockDivide.getSelectedItem().toString().equals("NC")) {
            int a1 = 18;
            int a2 = 17;
            int a3 = 112;
            int a4 = 137;
            int a5 = Integer.parseInt(spinClockDivide.getSelectedItem().toString());
            int a6 = 00;

            int[] sendValChkSum = {a1, a2, a3, a4, a5, a6};
            String strChkSum = CalculateCheckSum.calculateChkSum(sendValChkSum);
            String asciiString = String.format("%04x", a1).substring(2, 4) + String.format("%04x", a2).substring(2, 4) + String.format("%04x", a3).substring(2, 4) + String.format("%04x", a4).substring(2, 4) + String.format("%04x", a5).substring(2, 4) + String.format("%04x", a6).substring(2, 4);
            asciiString = asciiString + strChkSum + "\r";
//            Log.e(TAG, "asciiString = " + asciiString);

            if (isConnected()) {
//            connector.write(br1);
                //connector.write(asciiString.getBytes());
                sendMessage(asciiString.getBytes());
            } else {
                Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void callControlBit() {
        if(!spinControlBit.getSelectedItem().toString().equals("NC")) {
            int a1 = 18;
            int a2 = 17;
            int a3 = 112;
            int a4 = 138;
            int a5 = Integer.parseInt(spinControlBit.getSelectedItem().toString());
            int a6 = 00;

            int[] sendValChkSum = {a1, a2, a3, a4, a5, a6};
            String strChkSum = CalculateCheckSum.calculateChkSum(sendValChkSum);
            String asciiString = String.format("%04x", a1).substring(2, 4) + String.format("%04x", a2).substring(2, 4) + String.format("%04x", a3).substring(2, 4) + String.format("%04x", a4).substring(2, 4) + String.format("%04x", a5).substring(2, 4) + String.format("%04x", a6).substring(2, 4);
            asciiString = asciiString + strChkSum + "\r";
//            Log.e(TAG, "asciiString = " + asciiString);

            if (isConnected()) {
//            connector.write(br1);
                //connector.write(asciiString.getBytes());
                sendMessage(asciiString.getBytes());
            } else {
                Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void callFireFloor() {
        if(!spinFireFloor.getSelectedItem().toString().equals("NC")) {
            int a1 = 18;
            int a2 = 17;
            int a3 = 112;
            int a4 = 139;
            int a5 = Integer.parseInt(spinFireFloor.getSelectedItem().toString());
            int a6 = 00;

            int[] sendValChkSum = {a1, a2, a3, a4, a5, a6};
            String strChkSum = CalculateCheckSum.calculateChkSum(sendValChkSum);
            String asciiString = String.format("%04x", a1).substring(2, 4) + String.format("%04x", a2).substring(2, 4) + String.format("%04x", a3).substring(2, 4) + String.format("%04x", a4).substring(2, 4) + String.format("%04x", a5).substring(2, 4) + String.format("%04x", a6).substring(2, 4);
            asciiString = asciiString + strChkSum + "\r";
//            Log.e(TAG, "asciiString = " + asciiString);

            if (isConnected()) {
//            connector.write(br1);
                //connector.write(asciiString.getBytes());
                sendMessage(asciiString.getBytes());
            } else {
                Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void callHomeFloor() {
        if(!spinHomeFloor.getSelectedItem().toString().equals("NC")) {
            int a1 = 18;
            int a2 = 17;
            int a3 = 112;
            int a4 = 140;
            int a5 = Integer.parseInt(spinHomeFloor.getSelectedItem().toString());
            int a6 = 00;

            int[] sendValChkSum = {a1, a2, a3, a4, a5, a6};
            String strChkSum = CalculateCheckSum.calculateChkSum(sendValChkSum);
            String asciiString = String.format("%04x", a1).substring(2, 4) + String.format("%04x", a2).substring(2, 4) + String.format("%04x", a3).substring(2, 4) + String.format("%04x", a4).substring(2, 4) + String.format("%04x", a5).substring(2, 4) + String.format("%04x", a6).substring(2, 4);
            asciiString = asciiString + strChkSum + "\r";
//            Log.e(TAG, "asciiString = " + asciiString);

            if (isConnected()) {
//            connector.write(br1);
                //connector.write(asciiString.getBytes());
                sendMessage(asciiString.getBytes());
            } else {
                Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void callCompulsaryStop() {
        if(!spinCompulsaryStop.getSelectedItem().toString().equals("NC")) {
            int a1 = 18;
            int a2 = 17;
            int a3 = 112;
            int a4 = 141;
            int a5 = Integer.parseInt(spinCompulsaryStop.getSelectedItem().toString());
            int a6 = 00;

            int[] sendValChkSum = {a1, a2, a3, a4, a5, a6};
            String strChkSum = CalculateCheckSum.calculateChkSum(sendValChkSum);
            String asciiString = String.format("%04x", a1).substring(2, 4) + String.format("%04x", a2).substring(2, 4) + String.format("%04x", a3).substring(2, 4) + String.format("%04x", a4).substring(2, 4) + String.format("%04x", a5).substring(2, 4) + String.format("%04x", a6).substring(2, 4);
            asciiString = asciiString + strChkSum + "\r";
//            Log.e(TAG, "asciiString = " + asciiString);

            if (isConnected()) {
//            connector.write(br1);
                //connector.write(asciiString.getBytes());
                sendMessage(asciiString.getBytes());
            } else {
                Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void callParkingFloor() {
        if(!spinParkingFloor.getSelectedItem().toString().equals("NC")) {
            int a1 = 18;
            int a2 = 17;
            int a3 = 112;
            int a4 = 142;
            int a5 = Integer.parseInt(spinParkingFloor.getSelectedItem().toString());
            int a6 = 00;

            int[] sendValChkSum = {a1, a2, a3, a4, a5, a6};
            String strChkSum = CalculateCheckSum.calculateChkSum(sendValChkSum);
            String asciiString = String.format("%04x", a1).substring(2, 4) + String.format("%04x", a2).substring(2, 4) + String.format("%04x", a3).substring(2, 4) + String.format("%04x", a4).substring(2, 4) + String.format("%04x", a5).substring(2, 4) + String.format("%04x", a6).substring(2, 4);
            asciiString = asciiString + strChkSum + "\r";
//            Log.e(TAG, "asciiString = " + asciiString);

            if (isConnected()) {
//            connector.write(br1);
                //connector.write(asciiString.getBytes());
                sendMessage(asciiString.getBytes());
            } else {
                Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void callReadPLC(int locationAddress) {

        int a1 = 18;
        int a2 = 17;
        int a3 = 80;
        int a4 = locationAddress;
        int a5 = 00;
        int a6 = 00;

        int[] sendValChkSum={a1, a2, a3, a4, a5, a6};
        String strChkSum= CalculateCheckSum.calculateChkSum(sendValChkSum);

/*        int sum = a1 + a2 + a3 + a4 + a5;
        String sumHex = String.format("%04x", sum);
        String hex = Integer.toHexString(locationAddress);
        String s1 = Integer.toHexString(locationAddress);
        int s2 = Integer.parseInt(s1, 16);

        String msb = sumHex.substring(1, 2);
        String lsb = sumHex.substring(2, 4);

        int a7 = Integer.parseInt(lsb, 16);
        int msb2 = (Integer.parseInt(msb) | 50);
        int a8 = Integer.parseInt(String.valueOf(msb2),16);

        byte[] br2 = {(byte) a1, (byte) a2, (byte) a3, (byte) locationAddress, (byte) a5, (byte) a6,(byte) '\r'};
        byte[] br1 = {(byte) a1, (byte) a2, (byte) a3, (byte) locationAddress, (byte) a5, (byte) a6, (byte) a7, (byte) a8};*/

        String asciiString  = String.format("%04x", a1).substring(2,4)+String.format("%04x", a2).substring(2,4)+String.format("%04x", a3).substring(2,4)+String.format("%04x", a4).substring(2,4)+String.format("%04x", a5).substring(2,4)+String.format("%04x", a6).substring(2,4) ;
        /*int sumSendString  = 0;
        for(int i = 0; i<asciiString.length(); i++){
            sumSendString = sumSendString + Integer.parseInt(String.format("%04x", (int) asciiString.charAt(i)).substring(2,4));
        }
        asciiString = asciiString +String.valueOf(sumSendString).substring(1,3)+ "\r";*/

        asciiString = asciiString + strChkSum + "\r";
//        Log.e(TAG, "asciiString = "+ asciiString);
        if(isConnected()) {
            //connector.write(asciiString.getBytes());
            sendMessage(asciiString.getBytes());
        }
    }


    private Runnable checkDataContinue = new Runnable() {

        public void run() {
          // showReceivedDataNew();
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

            if(cntSetText==1){
                setTextViewValue(spinFlr);
            }
            myHandlerChk.postDelayed(this, 100);
        }

    };


    public void setTextViewValue(int flrNo){

        switch (flrNo){
            case 0:
                if (!str11StopDelay.equals("")) {
                    txtViewValue.setText(str11StopDelay);
                }
                break;
            case 1:
                if (!str11TransitDelay.equals("")) {
                    txtViewValue.setText(str11TransitDelay);
                }
                break;
            case 2:
                if (!str11BreakHiPulse.equals("")) {
                    txtViewValue.setText(str11BreakHiPulse);
                }
                break;
            case 3:
                if (!str11NoOfFloors.equals("")) {
                    txtViewValue.setText(str11NoOfFloors);
                }
                break;
            case 4:
                if (!str11DoorOpenTime.equals("")) {
                    txtViewValue.setText(str11DoorOpenTime);
                }
                break;
            case 5:
                if (!str11DoorCloseTime.equals("")) {
                    txtViewValue.setText(str11DoorCloseTime);
                }
                break;
            case 6:
                if (!str11DoorKeepOpenTime.equals("")) {
                    txtViewValue.setText(str11DoorKeepOpenTime);
                }
                break;
            case 7:
                if (!str11ClockDivide.equals("")) {
                    txtViewValue.setText(str11ClockDivide);
                }
                break;
            case 8:
                if (!str11ControlBit.equals("")) {
                    txtViewValue.setText(str11ControlBit);
                }
                break;
            case 9:
                if (!str11FireFloor.equals("")) {
                    txtViewValue.setText(str11FireFloor);
                }
                break;
            case 10:
                if (!str11HomeFloor.equals("")) {
                    txtViewValue.setText(str11HomeFloor);
                }
                break;
            case 11:
                if (!str11CompulsoryStop.equals("")) {
                    txtViewValue.setText(str11CompulsoryStop);
                }
                break;
            case 12:
                if (!str11ParkingFloor.equals("")) {
                    txtViewValue.setText(str11ParkingFloor);
                }
                break;

        }
    }

    public void showReceivedDataNew() {
        //Log.e(TAG, "ShowReceivedData");

        if (!str11StopDelay.equals("")) {
            txtStopDelay.setText(str11StopDelay);
        }
        if (!str11TransitDelay.equals("")) {
            txtTransitDelay.setText(str11TransitDelay);
        }
        if (!str11BreakHiPulse.equals("")) {
            txtBreakHiPulse.setText(str11BreakHiPulse);
        }
        if (!str11NoOfFloors.equals("")) {
            txtNoOfFloors.setText(str11NoOfFloors);
        }
        if (!str11DoorOpenTime.equals("")) {
            txtDoorOpenTime.setText(str11DoorOpenTime);
        }
        if (!str11DoorCloseTime.equals("")) {
            txtDoorCloseTime.setText(str11DoorCloseTime);
        }
        if (!str11DoorKeepOpenTime.equals("")) {
            txtDoorDoorKeepOpenTime.setText(str11DoorKeepOpenTime);
        }
        if (!str11ClockDivide.equals("")) {
            txtClockDivide.setText(str11ClockDivide);
        }
        if (!str11ControlBit.equals("")) {
            txtControlBit.setText(str11ControlBit);
        }
        if (!str11FireFloor.equals("")) {
            txtFireFloor.setText(str11FireFloor);
            //TempSharedPreference.setKeyFiremanFloor(mContext, "" + data);
        }
        if (!str11HomeFloor.equals("")) {
            txtHomeFloor.setText(str11HomeFloor);
            //TempSharedPreference.setKeyHomeFloor(mContext, "" + data);
        }
        if (!str11CompulsoryStop.equals("")) {
            txtCompulsaryStop.setText(str11CompulsoryStop);
            //TempSharedPreference.setKeyCompulsoryStop(mContext, "" + data);
        }
        if (!str11ParkingFloor.equals("")) {
            txtParkingFloor.setText(str11ParkingFloor);
            //TempSharedPreference.setKeyParkingFloor(mContext, "" + data);
        }
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
                serverIntent = new Intent(ProgramCodeActivity.this, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}