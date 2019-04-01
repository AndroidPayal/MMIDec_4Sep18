package com.radioknit.testmmi;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import pl.droidsonroids.gif.GifImageView;

import static com.radioknit.testmmi.PrefUtils.PREFS_LOGIN_PASSWORD_KEY;
import static com.radioknit.testmmi.PrefUtils.PREFS_LOGIN_USERNAME_KEY;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Tag_MainActivity";
    private static final boolean D = true;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 2;

    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    // Name of the connected device
    private String mConnectedDeviceName = null;
    // Array adapter for the conversation thread
    private ArrayAdapter<String> mConversationArrayAdapter;
    // String buffer for outgoing messages
    private static StringBuffer mOutStringBuffer;
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the chat services
    private static BluetoothChatService mChatService = null;
    //private static BluetoothChatService connector;
    BluetoothDevice device;
    int tryBTConnect=0;
    public static int btConnect=0;
    static StringBuffer msgAppend=new StringBuffer();
    static StringBuffer msgAppendChk=new StringBuffer();
    private boolean needClean;

    private static TextView txtDate;
    private static TextView txtTime;
    private static TextView txtFloorNumber;
    //    private static ImageView imgUp;
//    private static ImageView imgDown;
    private static GifImageView imgUp;
    private static GifImageView imgDown;
    private String remaningString = "";
    private String checkSum = "";
    private static TextView tvRunningStatus;

    private ImageView imgMainDisplay;
    private ImageView imgCarCall;
    private ImageView imgIoIndicator;
    private ImageView imgDateAndTime;
    private ImageView imgProgramCode;
    private ImageView imgDisplayPattern;
    private ImageView imgDeviceID;
    private ImageView imgProgrammableParameter;
    private ImageView imgSpeedSelectionCommand;
    private ImageView imgFloorCallBlocking;
    private ImageView imgLevelFunction;
    private ImageView imgErrorLog;
    private ImageView imgAutoLevel;
    private String temp1 = "";
    private static TextView mTxtEror;
    private static LinearLayout llError;
    private MainActivity mContext;
    private ArrayList<String> arrPattern;
    public static String str05Flr = "", str05Stat0 = "", str05Err = "00", str13HexCarCallsCop1 = "", str13HexCarCallsCop2 = "",
     str11ChkFlr = "", str11HexSwitchData = "", str71IOValues1 = "", str77IOValues2 = "", str11StopDelay = "", str11TransitDelay = "",
     str11BreakHiPulse = "", str11NoOfFloors = "", str11DoorOpenTime = "", str11DoorCloseTime = "", str11DoorKeepOpenTime = "",
     str11ClockDivide = "", str11ControlBit = "", str11FireFloor = "", str11HomeFloor = "", str11CompulsoryStop = "", str11ParkingFloor = "",
     str13MainCarCall = "", str05MainFloor = "", str06MainPre = "", str71MainSafety = "",

        //TODO payal
        str23HexCarCop2CallByte1 = "", str23HexCarCop2CallByte2="";
    ImageView imgDummy1;
   // public static String errorReceivedString="";
    public static ArrayList<String> strViewErrorStack;// = {"", "", "", "", "", "", "", "", "", ""};



    public static int count_IDs=0;//count 17 for 15 flr , and count 34  for 31 flr +Cop1+2
    public static int count_loader_ReadPLC = 0;
    public static int count_loader_DisplayPattern = 0;
    public static int count_loader_ProgrammableParameter = 0;
    public static int count_loader_SpeedSelection = 0;
    public static int count_loader_FlrCallBlocking = 0;
    public static int count_loader_ViewLevel = 0;



/*     str11DisFlr0 = "", str11DisFlr1 = "", str11DisFlr2 = "", str11DisFlr3 = "", str11DisFlr4 = "", str11DisFlr5 = "", str11DisFlr6 = "",
     str11DisFlr7 = "", str11DisFlr8 = "", str11DisFlr9 = "", str11DisFlr10 = "", str11DisFlr11 = "", str11DisFlr12 = "", str11DisFlr13 = "",
     str11DisFlr14 = "", str11DisFlr15 = "", str11DisFlr16 = "", str11DisFlr17 = "", str11DisFlr18 = "", str11DisFlr19 = "", str11DisFlr20 = "",
     str11DisFlr21 = "", str11DisFlr22 = "", str11DisFlr23 = "", str11DisFlr24 = "", str11DisFlr25 = "", str11DisFlr26 = "", str11DisFlr27 = "",
     str11DisFlr28 = "", str11DisFlr29 = "", str11DisFlr30 = "", str11DisFlr31 = "";*/

    public static String[] str11DisFlr = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    public static String[] str11f1Id = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    public static String[] str11f1Param = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    public static String[] str11Speed = {"", "", "", "", "", "", "", "", "", "", ""};
    public static String[] str11FlrClBlk = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    public static String[] str11LvlPos1 = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    public static String[] str11LvlPos2 = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    public static String[] str11LvlUpSlip = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    public static String[] str11LvlDnSlip = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    private Menu menu;
    final Handler myHandlerChk = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        strViewErrorStack = new ArrayList<>();
        strViewErrorStack.clear();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        tryBTConnect=0;
        btConnect=0;
        BufferedReader input = null;
        File file = null;
        try {
            file = new File(getFilesDir(), "MyFile"); // Pass getFilesDir() and "MyFile" to read file

            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = input.readLine()) != null) {
                buffer.append(line);
            }
            Log.e(TAG, "BT address:" + buffer.toString());


            try{
                device = mBluetoothAdapter.getRemoteDevice(buffer.toString());
                mChatService.connect(device);
            }
            catch (Exception e){
                tryBTConnect=tryBTConnect+1;
                //mChatService.connect(device);
                //Toast.makeText(this,"First time clicked",Toast.LENGTH_SHORT).show();

            }

           // Log.d(TAG, buffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        generateId();
        createObj();
        registerEvent();
        myHandlerChk.postDelayed(checkDataContinue, 0);
    }

    private void registerEvent() {
        imgMainDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(MainActivity.this, MainDisplayActivity.class);
                i1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i1);
            }
        });
        imgCarCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(MainActivity.this, CarCallActivity31.class);
                i1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i1);
            }
        });
        imgIoIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(MainActivity.this, IOIndicatorActivity.class);
                i1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i1);
            }
        });
        imgDateAndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(MainActivity.this, SetDateTimeActivity.class);
                i1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i1);
            }
        });
        imgProgramCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(MainActivity.this, ProgramCodeActivity.class);
                i1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i1);
            }
        });
        imgDisplayPattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(MainActivity.this, DisplayPatternActivity.class);
                i1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i1);
            }
        });
        imgDeviceID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(MainActivity.this, DeviceIDActivity.class);
                i1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i1);
            }
        });
        imgProgrammableParameter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(MainActivity.this, ProgrammablePrarmeterActivity.class);
                i1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i1);
            }
        });
        imgSpeedSelectionCommand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(MainActivity.this, SpeedSelectionActivity.class);
                i1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i1);
            }
        });
        imgFloorCallBlocking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(MainActivity.this, FloorCallBlockingActivity.class);
                i1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i1);
            }
        });
        imgLevelFunction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(MainActivity.this, LevelFunctionActivity.class);
                i1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i1);
            }
        });
        imgAutoLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(MainActivity.this, AutoLevelActivity.class);
                i1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i1);
            }
        });
        imgErrorLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(MainActivity.this, ViewErrorLogActivity.class);
                i1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i1);
            }
        });

        imgDummy1.setVisibility(View.INVISIBLE);
       /* imgDummy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(MainActivity.this, CarCallActivity.class);
                i1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i1);
            }
        });*/
    }

    private void createObj() {
        mContext = MainActivity.this;
        String[] tempPattern = getResources().getStringArray(R.array.arr_display_pattern);
        arrPattern = new ArrayList<>(Arrays.asList(tempPattern));
        //bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    private void generateId() {
        txtDate = (TextView) findViewById(R.id.tvDate);
        txtTime = (TextView) findViewById(R.id.tvTime);
        txtFloorNumber = (TextView)findViewById(R.id.tvFloorNumber);
        imgDown = (GifImageView)findViewById(R.id.imgArrowDown);
        imgUp  = (GifImageView)findViewById(R.id.imgArrowUp);
//        tvRunningStatus = (TextView)findViewById(R.id.tvRunnigstatus);

        imgMainDisplay = (ImageView)findViewById(R.id.imgMainDisplay);
        imgCarCall = (ImageView)findViewById(R.id.imgCarCall);
        imgIoIndicator = (ImageView)findViewById(R.id.imgIoIndicatore);
        imgDateAndTime = (ImageView)findViewById(R.id.imgDateTime);
        imgProgramCode = (ImageView)findViewById(R.id.imgProgramCode);
        imgDisplayPattern = (ImageView)findViewById(R.id.imgDisplayPattern);
        imgDeviceID = (ImageView)findViewById(R.id.imgDeviceID);
        imgProgrammableParameter = (ImageView)findViewById(R.id.imgProgramableParameter);
        imgSpeedSelectionCommand = (ImageView)findViewById(R.id.imgSpeedSelectionCommand);
        imgFloorCallBlocking = (ImageView)findViewById(R.id.imgFloorCallBlocking);
        imgLevelFunction = (ImageView)findViewById(R.id.imgLevelFunction);
        imgErrorLog = (ImageView)findViewById(R.id.imgErrorLog);
        imgAutoLevel = (ImageView)findViewById(R.id.imgAutoLevel);
        mTxtEror = (TextView)findViewById(R.id.tvError);
        llError = (LinearLayout)findViewById(R.id.llError);

        imgDummy1 = findViewById(R.id.imgDummy1);

        Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blinking_animation);
        mTxtEror.startAnimation(startAnimation);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateandTime = sdf.format(new Date());

        // txtDate.setText(currentDateandTime);
    }


    public void appendLog1(String message, boolean hexMode, boolean outgoing, boolean clean) {
        try {
            StringBuffer msg = new StringBuffer();

            msg.append(hexMode ? UtilsBluetooth.toHex1(message) : message);
            if (outgoing) msg.append("\n");

            //txtLog.append(msg);
            //  shwLog.append(msg);
        //    Log.e(TAG, "appendlog1() msgOld = "+ msg);

            msgAppend.append(msg);

      //      Log.e(TAG, "appendlog1() msgAppend = "+ msgAppend.toString());

            String strReceived = msgAppend.toString();
            processReceivedData(strReceived);
            msgAppend.setLength(0);
        }
        catch (Exception e)
        {
            //Not Supposed to happen.
            //throw new RuntimeException();
        }
    }

    public void processReceivedData(String strReceived){

           // Log.e(TAG, "strReceived = "+ strReceived);
            int indexOd = strReceived.indexOf("\r");
            String temp = strReceived.substring(0, indexOd);
        Log.e(TAG, "processReceivedData: temp = "+temp +"  index0d = "+indexOd);
           // Log.e(TAG, "temp = "+ temp);

        if (temp.startsWith("ee")){
           // errorReceivedString = temp;
            strViewErrorStack.add(temp);
           // Log.d(TAG, "processReceivedData: error="+errorReceivedString);
        }
        //TODO:Protocol Broadcasting (status
        if(temp.startsWith("05")){
            try{
                //if(strReceived.substring(indexA+16,indexA+17).equals("\r")){
                    //String strComplete;
                    str05Flr = strReceived.substring(4,6);
                    str05Stat0 = strReceived.substring(6,8);
              //  Log.e(TAG, "str05Flr = "+ str05Flr);
                    int floorNo = Integer.parseInt(str05Flr, 16);
                    String hexFour = String.format("%04x", Integer.parseInt(str05Stat0, 16));
                    String strBinaryFour = Utils.hexToBin(hexFour);//00110110

               Log.e(TAG, "processReceivedData(if 05) =flr " + str05Flr +" str05Stat0="+str05Stat0+ " flrNo ="+floorNo + " hexFour="+hexFour+ " strBinaryFour="+strBinaryFour);

                txtFloorNumber.setText("" + floorNo);

                    if (strBinaryFour.charAt(7) == '1') {//up direction
                        if (strBinaryFour.charAt(5) == '1') {//lift is running
                            imgUp.setVisibility(View.VISIBLE);
                            imgDown.setVisibility(View.GONE);
                            imgUp.setImageResource(R.drawable.up_flashing);
                       //     tvRunningStatus.setText("Up Running");
                        } else {//lift is in stady mode
                            imgUp.setVisibility(View.VISIBLE);
                            imgDown.setVisibility(View.GONE);
                            imgUp.setImageResource(R.drawable.up_arraow);
//                          tvRunningStatus.setText("Up");
                        }

                    } else if (strBinaryFour.charAt(6) == '1') {//down direction
                        if (strBinaryFour.charAt(5) == '1') {//lift is running
                            imgDown.setVisibility(View.VISIBLE);
                            imgUp.setVisibility(View.GONE);
                            imgDown.setImageResource(R.drawable.down_flashing);
                //            Log.e(TAG, "Down Running");
//                          tvRunningStatus.setText("Down Running");
                        } else {//lift is in stady mode
                            imgDown.setVisibility(View.VISIBLE);
                            imgUp.setVisibility(View.GONE);
                            imgDown.setImageResource(R.drawable.down_arr);
                   //         Log.e(TAG, "Down Study");
//                          tvRunningStatus.setText("Down");
                        }
                    }
                    //=======================================================
                    else if (strBinaryFour.charAt(7) == '0') {
             //           Log.d("Tag_check", "processReceivedData: bit 7 is 0");
                        imgUp.setVisibility(View.GONE);
                        imgDown.setVisibility(View.GONE);

                    } else if (strBinaryFour.charAt(6) == '0') {
                //        Log.d("Tag_check", "processReceivedData: bit 6 is 0");

                        imgUp.setVisibility(View.GONE);
                        imgDown.setVisibility(View.GONE);
                    }
                    //=======================================================

                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy | HH:mm");
                    String currentDateandTime = sdf.format(new Date());
         /*           Log.e(TAG, "processReceivedData :currentDateandTime = "+ currentDateandTime);
//05370030400056
                Log.d(TAG, "processReceivedData: errorCheck : "+temp.substring(4,6)+" parsed="+Integer.parseInt(temp.substring(4, 6), 16));
                Log.d(TAG, "processReceivedData: errorCheck2 : "+str05Err+" parsed="+(Integer.parseInt(str05Err, 16)));*/


                    if (Integer.parseInt(temp.substring(4, 6), 16) == 80) {
                        if((Integer.parseInt(str05Err, 16) != 80)) {
                            str05Err = str05Flr;
                            llError.setVisibility(View.VISIBLE);
                            mTxtEror.setText(" Error: 80 - Break Sw Fault | " + currentDateandTime);
                            mTxtEror.setSelected(true);
                            mTxtEror.setSingleLine();
                        }
                    } else if (Integer.parseInt(temp.substring(4, 6), 16) == 81) {
                        if((Integer.parseInt(str05Err, 16) != 81)) {
                            str05Err = str05Flr;
                            llError.setVisibility(View.VISIBLE);
                            mTxtEror.setText(" Error: 81 - Encoder dir Error | " + currentDateandTime);
                            mTxtEror.setSelected(true);
                            mTxtEror.setSingleLine();
                        }
                    } else if (Integer.parseInt(temp.substring(4, 6), 16) == 82) {
                        if((Integer.parseInt(str05Err, 16) != 82)) {
                            str05Err = str05Flr;
                            llError.setVisibility(View.VISIBLE);
                            mTxtEror.setText(" Error: 82 - Encoder no pulse or reset sw stucked up | " + currentDateandTime);
                            mTxtEror.setSelected(true);
                            mTxtEror.setSingleLine();
                        }
                    } else if (Integer.parseInt(temp.substring(4, 6), 16) == 83) {
                        if((Integer.parseInt(str05Err, 16) != 83)) {
                            str05Err = str05Flr;
                            llError.setVisibility(View.VISIBLE);
                            mTxtEror.setText(" Error: 83 - Motor Thermal | " + currentDateandTime);
                            mTxtEror.setSelected(true);
                            mTxtEror.setSingleLine();
                        }
                    } else if (Integer.parseInt(temp.substring(4, 6), 16) == 84) {
                        if((Integer.parseInt(str05Err, 16) != 84)) {
                            str05Err = str05Flr;
                            llError.setVisibility(View.VISIBLE);
                            mTxtEror.setText(" Error: 84 - Door open fault | " + currentDateandTime);
                            mTxtEror.setSelected(true);
                            mTxtEror.setSingleLine();
                        }
                    } else if (Integer.parseInt(temp.substring(4, 6), 16) == 85) {
                        if((Integer.parseInt(str05Err, 16) != 85)) {
                            str05Err = str05Flr;
                            llError.setVisibility(View.VISIBLE);
                            mTxtEror.setText(" Error: 85 - Door close fault | " + currentDateandTime);
                            mTxtEror.setSelected(true);
                            mTxtEror.setSingleLine();
                        }
                    } else if (Integer.parseInt(temp.substring(4, 6), 16) == 86) {
                        if((Integer.parseInt(str05Err, 16) != 86)) {
                            str05Err = str05Flr;
                            llError.setVisibility(View.VISIBLE);
                            mTxtEror.setText(" Error: 86 - Power fault | " + currentDateandTime);
                            mTxtEror.setSelected(true);
                            mTxtEror.setSingleLine();
                        }
                    } else if (Integer.parseInt(temp.substring(4, 6), 16) == 87) {
                        if((Integer.parseInt(str05Err, 16) != 87)) {
                            str05Err = str05Flr;
                            llError.setVisibility(View.VISIBLE);
                            mTxtEror.setText(" Error: 87 - Final limit Cut | " + currentDateandTime);
                            mTxtEror.setSelected(true);
                            mTxtEror.setSingleLine();
                        }
                    } else if (Integer.parseInt(temp.substring(4, 6), 16) == 88) {
                        if((Integer.parseInt(str05Err, 16) != 88)) {
                            str05Err = str05Flr;
                            llError.setVisibility(View.VISIBLE);
                            mTxtEror.setText(" Error: 88 - Drive fault | " + currentDateandTime);
                            mTxtEror.setSelected(true);
                            mTxtEror.setSingleLine();
                        }
                    } else if (Integer.parseInt(temp.substring(4, 6), 16) == 89) {
                        if((Integer.parseInt(str05Err, 16) != 89)) {
                            str05Err = str05Flr;
                            llError.setVisibility(View.VISIBLE);
                            mTxtEror.setText(" Error: 89 - A.R.D. Mode Detect | " + currentDateandTime);
                            mTxtEror.setSelected(true);
                            mTxtEror.setSingleLine();
                        }
                    } else if (Integer.parseInt(temp.substring(4, 6), 16) == 90) {
                        if((Integer.parseInt(str05Err, 16) != 90)) {
                            str05Err = str05Flr;
                            llError.setVisibility(View.VISIBLE);
                            mTxtEror.setText(" Error: 90 - Terminal Switch Error | " + currentDateandTime);
                            mTxtEror.setSelected(true);
                            mTxtEror.setSingleLine();
                        }
                    } else if (Integer.parseInt(temp.substring(4, 6), 16) == 91) {
                        if((Integer.parseInt(str05Err, 16) != 91)) {
                            str05Err = str05Flr;
                            llError.setVisibility(View.VISIBLE);
                            mTxtEror.setText(" Error: 91 - Important floor tried to block | " + currentDateandTime);
                            mTxtEror.setSelected(true);
                            mTxtEror.setSingleLine();
                        }
                    } else if (Integer.parseInt(temp.substring(4, 6), 16) == 92) {
                        if((Integer.parseInt(str05Err, 16) != 92)) {
                            str05Err = str05Flr;
                            llError.setVisibility(View.VISIBLE);
                            mTxtEror.setText(" Error: 92 - Terminal Error | " + currentDateandTime);
                            mTxtEror.setSelected(true);
                            mTxtEror.setSingleLine();
                        }
                    } else if (Integer.parseInt(temp.substring(4, 6), 16) == 93) {
                        if((Integer.parseInt(str05Err, 16) != 93)) {
                            str05Err = str05Flr;
                            llError.setVisibility(View.VISIBLE);
                            mTxtEror.setText(" Error: 93 - Terminal Error | " + currentDateandTime);
                            mTxtEror.setSelected(true);
                            mTxtEror.setSingleLine();
                        }
                    } else if (Integer.parseInt(temp.substring(4, 6), 16) == 94) {
                        if((Integer.parseInt(str05Err, 16) != 94)) {
                            str05Err = str05Flr;
                            llError.setVisibility(View.VISIBLE);
                            mTxtEror.setText(" Error: 94 - Reserved | " + currentDateandTime);
                            mTxtEror.setSelected(true);
                            mTxtEror.setSingleLine();
                        }
                    } else if (Integer.parseInt(temp.substring(4, 6), 16) == 95) {
                        if((Integer.parseInt(str05Err, 16) != 95)) {
                            str05Err = str05Flr;
                            llError.setVisibility(View.VISIBLE);
                            mTxtEror.setText(" Error: 95 - Reserved | " + currentDateandTime);
                            mTxtEror.setSelected(true);
                            mTxtEror.setSingleLine();
                        }
                    } else if (Integer.parseInt(temp.substring(4, 6), 16) == 96) {
                        if((Integer.parseInt(str05Err, 16) != 96)) {
                            str05Err = str05Flr;
                            llError.setVisibility(View.VISIBLE);
                            mTxtEror.setText(" Error: 96 - Encoder Pulse and fin position mismatch | " + currentDateandTime);
                            mTxtEror.setSelected(true);
                            mTxtEror.setSingleLine();
                        }
                    } else if (Integer.parseInt(temp.substring(4, 6), 16) == 97) {
                        if((Integer.parseInt(str05Err, 16) != 97)) {
                            str05Err = str05Flr;
                            llError.setVisibility(View.VISIBLE);
                            mTxtEror.setText(" Error: 97 - Door zone switch error | " + currentDateandTime);
                            mTxtEror.setSelected(true);
                            mTxtEror.setSingleLine();
                        }
                    } /*else {
                        txtFloorNumber.setText("" + floorNo);
                    }*/
          //      Log.d("Tag_check", "processReceivedData: flr no="+floorNo);
             //   txtFloorNumber.setText("" + floorNo);
                str05MainFloor = temp;
                //}
            }
            catch (Exception e){
                //Catch Exception
                Log.e(TAG, "processReceivedData:except = "+e );
            }

        }

        //TODO:Protocol for PreAnnouncing
        if(temp.startsWith("06")){
            str06MainPre = temp;
        }

        //TODO:Protocol time
        if (temp.startsWith("11f2")) {//11f2217f744dfd
            String hrs = (temp.substring(4, 6));//21
            String min = (temp.substring(6, 8));//7f

           /* Log.d("Tag_timer", "processReceivedData: hex hour="+hrs+ " min="+min);
            Log.d("Tag_timer", "processReceivedData: decimal hour="+Integer.parseInt(hrs,16)
            + " min="+Integer.parseInt(min,16));*/
            //decimal hour=33 min=127

            txtTime.setText("" + hrs + " : " + min);
           // txtTime.setText("" + Integer.parseInt(hrs,16) + " : " + Integer.parseInt(min,16));
            temp = "";
        }

        //TODO:Protocol for Date
        if (temp.startsWith("11f3")) {
            String date = (temp.substring(8, 10));//(temp.substring(4, 6));
            String month = (temp.substring(6, 8));
            String year =  (temp.substring(4, 6));//(temp.substring(8, 10));

        /*    Log.d("Tag_timer", "processReceivedData:hex date = "+date
                    +" month ="+ month
                    +" yr = "+ year);
            Log.d("Tag_timer", "processReceivedData:decimal date = "+Integer.parseInt(date,16)
            +" month ="+ Integer.parseInt(month ,16)
            +" yr = "+ Integer.parseInt(year,16));*/
            //decimal date = 254 month =30 yr = 35

                txtDate.setText(date + " / " + month+ " / " +"20" + year  );
           // txtDate.setText(Integer.parseInt(date,16) +" / " + Integer.parseInt(month ,16)+ " / " + "20" + Integer.parseInt(year,16)  );
            temp = "";
        }
        //TODo : Protocol for COP1
        if(temp.startsWith("1311")){
            Log.d(TAG, "processReceivedData: temp1311 : "+temp);
            int index = temp.indexOf("1311");
            str13HexCarCallsCop1 = temp.substring(index+6,index+8);
            str13HexCarCallsCop2 = temp.substring(index+8,index+10);

         //   Log.d(TAG, "processReceivedData: temp1311 : "+temp+ " str13HexCarCallsCop1="+str13HexCarCallsCop1+ " str13HexCarCallsCop2 ="+str13HexCarCallsCop2);

            str13MainCarCall = temp;
        }

        //TODo : Protocol for COP2 //update payal
        if(temp.startsWith("2311")) {
            Log.d(TAG, "processReceivedData: temp2311 : " + temp);
            int index = temp.indexOf("2311");
            str23HexCarCop2CallByte1 = temp.substring(index+6,index+8);
            str23HexCarCop2CallByte2 = temp.substring(index+8,index+10);

            //add this too here str13MainCarCall = temp;
        }

        //TODO:Protocol for LOP
        if(temp.contains("114c50")){
            int index = temp.indexOf("114c50");
            //index = position of first 1 in 114c50 = 2 /*32114c50000184*/
            str11ChkFlr=temp.substring(index-2,index);//32
            str11HexSwitchData = temp.substring(index+8,index+10);//01
            /*01 (hex) = 0000 0001 (bin)
            * working on its binary with 6th bit and 7th bit only*/

            Log.d(TAG, "processReceivedData: temp114c50 : str11ChkFlr="+str11ChkFlr + " str11HexSwitchData=" +str11HexSwitchData);
        }

        //TODO:Protocol broadcasting I/Os
        if(temp.startsWith("71")){
            str71IOValues1 = temp;
        }

        //TODO:Protocol Broadcasting (External Inputs
        if(temp.startsWith("77")){
            str77IOValues2 = temp;
        }

        //TODO:Protocols for parameters
        if(temp.startsWith("111250")) {

            Log.d(TAG, "processReceivedData: count = "+count_loader_ViewLevel);

            String sum = Utils.calculateChecksumValueNew(temp);
          //  Log.e(TAG, "" + sum.substring(2, 4) + " -- " + temp.substring(temp.length() - 2, temp.length()) + " temp = " + temp);

            if (sum.substring(2, 4).equalsIgnoreCase(temp.substring(temp.length() - 2, temp.length()))) {
                String locationAddress = temp.substring(6, 8);

                int data = Integer.parseInt(temp.substring(8, 10), 16);
                String strData = temp.substring(8, 10);
            //    Log.e(TAG, "locationAddress = " + locationAddress + " data = " + data);
                if (locationAddress.equalsIgnoreCase("82")) {
                    if (str11StopDelay.equals("")){
                        count_loader_ReadPLC++;
                    }
                    str11StopDelay = (Integer.toString(data) + " ");
                    //str11StopDelay = (strData + " ");
                } else if (locationAddress.equalsIgnoreCase("83")) {
                    if (str11TransitDelay.equals("")){
                        count_loader_ReadPLC++;
                    }
                    str11TransitDelay = (Integer.toString(data) + " ");
                    //str11TransitDelay = (strData + " ");
                } else if (locationAddress.equalsIgnoreCase("84")) {
                    if (str11BreakHiPulse.equals("")){
                        count_loader_ReadPLC++;
                    }
                    str11BreakHiPulse = (Integer.toString(data) + " ");
                    //str11BreakHiPulse = (strData + " ");
                } else if (locationAddress.equalsIgnoreCase("85")) {
                    if (str11NoOfFloors.equals("")){
                        count_loader_ReadPLC++;
                    }
                    str11NoOfFloors = (Integer.toString(data) + " ");
                    //str11NoOfFloors = (strData + " ");
                } else if (locationAddress.equalsIgnoreCase("86")) {
                    if (str11DoorOpenTime.equals("")){
                        count_loader_ReadPLC++;
                    }
                    str11DoorOpenTime = (Integer.toString(data) + " ");
                    //str11DoorOpenTime = (strData + " ");
                } else if (locationAddress.equalsIgnoreCase("87")) {
                    if (str11DoorCloseTime.equals("")){
                        count_loader_ReadPLC++;
                    }
                    str11DoorCloseTime = (Integer.toString(data) + " ");
                    //str11DoorCloseTime = (strData + " ");
                } else if (locationAddress.equalsIgnoreCase("88")) {
                    if (str11DoorKeepOpenTime.equals("")){
                        count_loader_ReadPLC++;
                    }
                    str11DoorKeepOpenTime = (Integer.toString(data) + " ");
                    //str11DoorKeepOpenTime = (strData + " ");
                } else if (locationAddress.equalsIgnoreCase("89")) {
                    if (str11ClockDivide.equals("")){
                        count_loader_ReadPLC++;
                    }
                    str11ClockDivide = (Integer.toString(data) + " ");
                    //str11ClockDivide = (strData + " ");
                } else if (locationAddress.equalsIgnoreCase("8A")) {
                    if (str11ControlBit.equals("")){
                        count_loader_ReadPLC++;
                    }
                    str11ControlBit = (Integer.toString(data) + " ");
                    //str11ControlBit = (strData + " ");
                } else if (locationAddress.equalsIgnoreCase("8B")) {
                    if (str11FireFloor.equals("")){
                        count_loader_ReadPLC++;
                    }
                    str11FireFloor = (Integer.toString(data) + " ");
                    //str11FireFloor = (strData + " ");
                    //TempSharedPreference.setKeyFiremanFloor(mContext, "" + data);
                } else if (locationAddress.equalsIgnoreCase("8C")) {
                    if (str11HomeFloor.equals("")){
                        count_loader_ReadPLC++;
                    }
                    str11HomeFloor = (Integer.toString(data) + " ");
                    //str11HomeFloor = (strData + " ");
                    //TempSharedPreference.setKeyHomeFloor(mContext, "" + data);
                } else if (locationAddress.equalsIgnoreCase("8D")) {
                    if (str11CompulsoryStop.equals("")){
                        count_loader_ReadPLC++;
                    }
                    str11CompulsoryStop = (Integer.toString(data) + " ");
                    //str11CompulsoryStop = (strData + " ");
                    //TempSharedPreference.setKeyCompulsoryStop(mContext, "" + data);
                } else if (locationAddress.equalsIgnoreCase("8E")) {
                    if (str11ParkingFloor.equals("")){
                        count_loader_ReadPLC++;
                    }
                    str11ParkingFloor = (Integer.toString(data) + " ");
                    //str11ParkingFloor = (strData + " ");
                    //TempSharedPreference.setKeyParkingFloor(mContext, "" + data);
                }




                //============================================================

                else if (locationAddress.equalsIgnoreCase("C0")) {
                    if (str11DisFlr[0].equals("")){
                        count_loader_DisplayPattern++;
                    }
                    str11DisFlr[0] = ("" + arrPattern.get(data));
                } else if (locationAddress.equalsIgnoreCase("C1")) {
                    if (str11DisFlr[1].equals("")){
                        count_loader_DisplayPattern++;
                    }
                    str11DisFlr[1] = ("" + arrPattern.get(data));
                } else if (locationAddress.equalsIgnoreCase("C2")) {
                    if (str11DisFlr[2].equals("")){
                        count_loader_DisplayPattern++;
                    }
                    str11DisFlr[2] = ("" + arrPattern.get(data));
                } else if (locationAddress.equalsIgnoreCase("C3")) {
                    if (str11DisFlr[3].equals("")){
                        count_loader_DisplayPattern++;
                    }
                    str11DisFlr[3] = ("" + arrPattern.get(data));
                } else if (locationAddress.equalsIgnoreCase("C4")) {
                    if (str11DisFlr[4].equals("")){
                        count_loader_DisplayPattern++;
                    }
                    str11DisFlr[4] = ("" + arrPattern.get(data));
                } else if (locationAddress.equalsIgnoreCase("C5")) {
                    if (str11DisFlr[5].equals("")){
                        count_loader_DisplayPattern++;
                    }
                    str11DisFlr[5] = ("" + arrPattern.get(data));
                } else if (locationAddress.equalsIgnoreCase("C6")) {
                    if (str11DisFlr[6].equals("")){
                        count_loader_DisplayPattern++;
                    }
                    str11DisFlr[6] = ("" + arrPattern.get(data));
                } else if (locationAddress.equalsIgnoreCase("C7")) {
                    if (str11DisFlr[7].equals("")){
                        count_loader_DisplayPattern++;
                    }
                    str11DisFlr[7] = ("" + arrPattern.get(data));
                } else if (locationAddress.equalsIgnoreCase("C8")) {
                    if (str11DisFlr[8].equals("")){
                        count_loader_DisplayPattern++;
                    }
                    str11DisFlr[8] = ("" + arrPattern.get(data));
                } else if (locationAddress.equalsIgnoreCase("C9")) {
                    if (str11DisFlr[9].equals("")){
                        count_loader_DisplayPattern++;
                    }
                    str11DisFlr[9] = ("" + arrPattern.get(data));
                } else if (locationAddress.equalsIgnoreCase("CA")) {
                    if (str11DisFlr[10].equals("")){
                        count_loader_DisplayPattern++;
                    }
                    str11DisFlr[10] = ("" + arrPattern.get(data));
                } else if (locationAddress.equalsIgnoreCase("CB")) {
                    if (str11DisFlr[11].equals("")){
                        count_loader_DisplayPattern++;
                    }
                    str11DisFlr[11] = ("" + arrPattern.get(data));
                } else if (locationAddress.equalsIgnoreCase("CC")) {
                    if (str11DisFlr[12].equals("")){
                        count_loader_DisplayPattern++;
                    }
                    str11DisFlr[12] = ("" + arrPattern.get(data));
                } else if (locationAddress.equalsIgnoreCase("CD")) {
                    if (str11DisFlr[13].equals("")){
                        count_loader_DisplayPattern++;
                    }
                    str11DisFlr[13] = ("" + arrPattern.get(data));
                } else if (locationAddress.equalsIgnoreCase("CE")) {
                    if (str11DisFlr[14].equals("")){
                        count_loader_DisplayPattern++;
                    }
                    str11DisFlr[14] = ("" + arrPattern.get(data));
                } else if (locationAddress.equalsIgnoreCase("CF")) {
                    if (str11DisFlr[15].equals("")){
                        count_loader_DisplayPattern++;
                    }
                    str11DisFlr[15] = ("" + arrPattern.get(data));
                } else if (locationAddress.equalsIgnoreCase("D0")) {
                    if (str11DisFlr[16].equals("")){
                        count_loader_DisplayPattern++;
                    }
                    str11DisFlr[16] = ("" + arrPattern.get(data));
                } else if (locationAddress.equalsIgnoreCase("D1")) {
                    if (str11DisFlr[17].equals("")){
                        count_loader_DisplayPattern++;
                    }
                    str11DisFlr[17] = ("" + arrPattern.get(data));
                } else if (locationAddress.equalsIgnoreCase("D2")) {
                    if (str11DisFlr[18].equals("")){
                        count_loader_DisplayPattern++;
                    }
                    str11DisFlr[18] = ("" + arrPattern.get(data));
                } else if (locationAddress.equalsIgnoreCase("D3")) {
                    if (str11DisFlr[19].equals("")){
                        count_loader_DisplayPattern++;
                    }
                    str11DisFlr[19] = ("" + arrPattern.get(data));
                } else if (locationAddress.equalsIgnoreCase("D4")) {
                    if (str11DisFlr[20].equals("")){
                        count_loader_DisplayPattern++;
                    }
                    str11DisFlr[20] = ("" + arrPattern.get(data));
                } else if (locationAddress.equalsIgnoreCase("D5")) {
                    if (str11DisFlr[21].equals("")){
                        count_loader_DisplayPattern++;
                    }
                    str11DisFlr[21] = ("" + arrPattern.get(data));
                } else if (locationAddress.equalsIgnoreCase("D6")) {
                    if (str11DisFlr[22].equals("")){
                        count_loader_DisplayPattern++;
                    }
                    str11DisFlr[22] = ("" + arrPattern.get(data));
                } else if (locationAddress.equalsIgnoreCase("D7")) {
                    if (str11DisFlr[23].equals("")){
                        count_loader_DisplayPattern++;
                    }
                    str11DisFlr[23] = ("" + arrPattern.get(data));
                } else if (locationAddress.equalsIgnoreCase("D8")) {
                    if (str11DisFlr[24].equals("")){
                        count_loader_DisplayPattern++;
                    }
                    str11DisFlr[24] = ("" + arrPattern.get(data));
                } else if (locationAddress.equalsIgnoreCase("D9")) {
                    if (str11DisFlr[25].equals("")){
                        count_loader_DisplayPattern++;
                    }
                    str11DisFlr[25] = ("" + arrPattern.get(data));
                } else if (locationAddress.equalsIgnoreCase("DA")) {
                    if (str11DisFlr[26].equals("")){
                        count_loader_DisplayPattern++;
                    }
                    str11DisFlr[26] = ("" + arrPattern.get(data));
                } else if (locationAddress.equalsIgnoreCase("DB")) {
                    if (str11DisFlr[27].equals("")){
                        count_loader_DisplayPattern++;
                    }
                    str11DisFlr[27] = ("" + arrPattern.get(data));
                } else if (locationAddress.equalsIgnoreCase("DC")) {
                    if (str11DisFlr[28].equals("")){
                        count_loader_DisplayPattern++;
                    }
                    str11DisFlr[28] = ("" + arrPattern.get(data));
                } else if (locationAddress.equalsIgnoreCase("DD")) {
                    if (str11DisFlr[29].equals("")){
                        count_loader_DisplayPattern++;
                    }
                    str11DisFlr[29] = ("" + arrPattern.get(data));
                } else if (locationAddress.equalsIgnoreCase("DE")) {
                    if (str11DisFlr[30].equals("")){
                        count_loader_DisplayPattern++;
                    }
                    str11DisFlr[30] = ("" + arrPattern.get(data));
                } else if (locationAddress.equalsIgnoreCase("DF")) {
                    if (str11DisFlr[31].equals("")){
                        count_loader_DisplayPattern++;
                    }
                    str11DisFlr[31] = ("" + arrPattern.get(data));
                }


                else if (locationAddress.equalsIgnoreCase("F0")) {
                    if (str11Speed[0].equals("")){
                        count_loader_SpeedSelection++;
                    }
                    str11Speed[0] = (data + " ");
                } else if (locationAddress.equalsIgnoreCase("F1")) {
                    if (str11Speed[1].equals("")){
                        count_loader_SpeedSelection++;
                    }
                    str11Speed[1] = (data + " ");
                } else if (locationAddress.equalsIgnoreCase("F2")) {
                    if (str11Speed[2].equals("")){
                        count_loader_SpeedSelection++;
                    }
                    str11Speed[2] = (data + " ");
                } else if (locationAddress.equalsIgnoreCase("F3")) {
                    if (str11Speed[3].equals("")){
                        count_loader_SpeedSelection++;
                    }
                    str11Speed[3] = (data + " ");
                } else if (locationAddress.equalsIgnoreCase("F4")) {
                    if (str11Speed[4].equals("")){
                        count_loader_SpeedSelection++;
                    }
                    str11Speed[4] = (data + " ");
                } else if (locationAddress.equalsIgnoreCase("F5")) {
                    if (str11Speed[5].equals("")){
                        count_loader_SpeedSelection++;
                    }
                    str11Speed[5] = (data + " ");
                } else if (locationAddress.equalsIgnoreCase("F6")) {
                    if (str11Speed[6].equals("")){
                        count_loader_SpeedSelection++;
                    }
                    str11Speed[6] = (data + " ");
                } else if (locationAddress.equalsIgnoreCase("F7")) {
                    if (str11Speed[7].equals("")){
                        count_loader_SpeedSelection++;
                    }
                    str11Speed[7] = (data + " ");
                } else if (locationAddress.equalsIgnoreCase("F8")) {
                    if (str11Speed[8].equals("")){
                        count_loader_SpeedSelection++;
                    }
                    str11Speed[8] = (data + " ");
                } else if (locationAddress.equalsIgnoreCase("F9")) {
                    if (str11Speed[9].equals("")){
                        count_loader_SpeedSelection++;
                    }
                    str11Speed[9] = (data + " ");
                } else if (locationAddress.equalsIgnoreCase("FA")) {
                    if (str11Speed[10].equals("")){
                        count_loader_SpeedSelection++;
                    }
                    str11Speed[10] = (data + " ");
                }








                //=====================================================

                else if (locationAddress.equalsIgnoreCase("A0")) {
                    if (str11FlrClBlk[0].equals("")){
                        count_loader_FlrCallBlocking++;
                    }
                    str11FlrClBlk[0] = Integer.toString(data);
                   //   Log.e(TAG,"Data:"+str11FlrClBlk[0]);
                } else if (locationAddress.equalsIgnoreCase("A1")) {
                    if (str11FlrClBlk[1].equals("")){
                        count_loader_FlrCallBlocking++;
                    }
                    str11FlrClBlk[1] = Integer.toString(data);
                } else if (locationAddress.equalsIgnoreCase("A2")) {
                    if (str11FlrClBlk[2].equals("")){
                        count_loader_FlrCallBlocking++;
                    }
                    str11FlrClBlk[2] = Integer.toString(data);
                } else if (locationAddress.equalsIgnoreCase("A3")) {
                    if (str11FlrClBlk[3].equals("")){
                        count_loader_FlrCallBlocking++;
                    }
                    str11FlrClBlk[3] = Integer.toString(data);
                } else if (locationAddress.equalsIgnoreCase("A4")) {
                    if (str11FlrClBlk[4].equals("")){
                        count_loader_FlrCallBlocking++;
                    }
                    str11FlrClBlk[4] = Integer.toString(data);
                } else if (locationAddress.equalsIgnoreCase("A5")) {
                    if (str11FlrClBlk[5].equals("")){
                        count_loader_FlrCallBlocking++;
                    }
                    str11FlrClBlk[5] = Integer.toString(data);
                } else if (locationAddress.equalsIgnoreCase("A6")) {
                    if (str11FlrClBlk[6].equals("")){
                        count_loader_FlrCallBlocking++;
                    }
                    str11FlrClBlk[6] = Integer.toString(data);
                } else if (locationAddress.equalsIgnoreCase("A7")) {
                    if (str11FlrClBlk[7].equals("")){
                        count_loader_FlrCallBlocking++;
                    }
                    str11FlrClBlk[7] = Integer.toString(data);
                } else if (locationAddress.equalsIgnoreCase("A8")) {
                    if (str11FlrClBlk[8].equals("")){
                        count_loader_FlrCallBlocking++;
                    }
                    str11FlrClBlk[8] = Integer.toString(data);
                } else if (locationAddress.equalsIgnoreCase("A9")) {
                    if (str11FlrClBlk[9].equals("")){
                        count_loader_FlrCallBlocking++;
                    }
                    str11FlrClBlk[9] = Integer.toString(data);
                } else if (locationAddress.equalsIgnoreCase("AA")) {
                    if (str11FlrClBlk[10].equals("")){
                        count_loader_FlrCallBlocking++;
                    }
                    str11FlrClBlk[10] = Integer.toString(data);
                } else if (locationAddress.equalsIgnoreCase("AB")) {
                    if (str11FlrClBlk[11].equals("")){
                        count_loader_FlrCallBlocking++;
                    }
                    str11FlrClBlk[11] = Integer.toString(data);
                } else if (locationAddress.equalsIgnoreCase("AC")) {
                    if (str11FlrClBlk[12].equals("")){
                        count_loader_FlrCallBlocking++;
                    }
                    str11FlrClBlk[12] = Integer.toString(data);
                } else if (locationAddress.equalsIgnoreCase("AD")) {
                    if (str11FlrClBlk[13].equals("")){
                        count_loader_FlrCallBlocking++;
                    }
                    str11FlrClBlk[13] = Integer.toString(data);
                } else if (locationAddress.equalsIgnoreCase("AE")) {
                    if (str11FlrClBlk[14].equals("")){
                        count_loader_FlrCallBlocking++;
                    }
                    str11FlrClBlk[14] = Integer.toString(data);
                } else if (locationAddress.equalsIgnoreCase("AF")) {
                    if (str11FlrClBlk[15].equals("")){
                        count_loader_FlrCallBlocking++;
                    }
                    str11FlrClBlk[15] = Integer.toString(data);
                } else if (locationAddress.equalsIgnoreCase("B0")) {
                    if (str11FlrClBlk[16].equals("")){
                        count_loader_FlrCallBlocking++;
                    }
                    str11FlrClBlk[16] = Integer.toString(data);
                } else if (locationAddress.equalsIgnoreCase("B1")) {
                    if (str11FlrClBlk[17].equals("")){
                        count_loader_FlrCallBlocking++;
                    }
                    str11FlrClBlk[17] = Integer.toString(data);
                } else if (locationAddress.equalsIgnoreCase("B2")) {
                    if (str11FlrClBlk[18].equals("")){
                        count_loader_FlrCallBlocking++;
                    }
                    str11FlrClBlk[18] = Integer.toString(data);
                } else if (locationAddress.equalsIgnoreCase("B3")) {
                    if (str11FlrClBlk[19].equals("")){
                        count_loader_FlrCallBlocking++;
                    }
                    str11FlrClBlk[19] = Integer.toString(data);
                } else if (locationAddress.equalsIgnoreCase("B4")) {
                    if (str11FlrClBlk[20].equals("")){
                        count_loader_FlrCallBlocking++;
                    }
                    str11FlrClBlk[20] = Integer.toString(data);
                } else if (locationAddress.equalsIgnoreCase("B5")) {
                    if (str11FlrClBlk[21].equals("")){
                        count_loader_FlrCallBlocking++;
                    }
                    str11FlrClBlk[21] = Integer.toString(data);
                } else if (locationAddress.equalsIgnoreCase("B6")) {
                    if (str11FlrClBlk[22].equals("")){
                        count_loader_FlrCallBlocking++;
                    }
                    str11FlrClBlk[22] = Integer.toString(data);
                } else if (locationAddress.equalsIgnoreCase("B7")) {
                    if (str11FlrClBlk[23].equals("")){
                        count_loader_FlrCallBlocking++;
                    }
                    str11FlrClBlk[23] = Integer.toString(data);
                } else if (locationAddress.equalsIgnoreCase("B8")) {
                    if (str11FlrClBlk[24].equals("")){
                        count_loader_FlrCallBlocking++;
                    }
                    str11FlrClBlk[24] = Integer.toString(data);
                } else if (locationAddress.equalsIgnoreCase("B9")) {
                    if (str11FlrClBlk[25].equals("")){
                        count_loader_FlrCallBlocking++;
                    }
                    str11FlrClBlk[25] = Integer.toString(data);
                } else if (locationAddress.equalsIgnoreCase("BA")) {
                    if (str11FlrClBlk[26].equals("")){
                        count_loader_FlrCallBlocking++;
                    }
                    str11FlrClBlk[26] = Integer.toString(data);
                } else if (locationAddress.equalsIgnoreCase("BB")) {
                    if (str11FlrClBlk[27].equals("")){
                        count_loader_FlrCallBlocking++;
                    }
                    str11FlrClBlk[27] = Integer.toString(data);
                } else if (locationAddress.equalsIgnoreCase("BC")) {
                    if (str11FlrClBlk[28].equals("")){
                        count_loader_FlrCallBlocking++;
                    }
                    str11FlrClBlk[28] = Integer.toString(data);
                } else if (locationAddress.equalsIgnoreCase("BD")) {
                    if (str11FlrClBlk[29].equals("")){
                        count_loader_FlrCallBlocking++;
                    }
                    str11FlrClBlk[29] = Integer.toString(data);
                } else if (locationAddress.equalsIgnoreCase("BE")) {
                    if (str11FlrClBlk[30].equals("")){
                        count_loader_FlrCallBlocking++;
                    }
                    str11FlrClBlk[30] = Integer.toString(data);
                } else if (locationAddress.equalsIgnoreCase("BF")) {
                    if (str11FlrClBlk[31].equals("")){
                        count_loader_FlrCallBlocking++;
                    }
                    str11FlrClBlk[31] = Integer.toString(data);
                }



                //===================================================

                else if (locationAddress.equalsIgnoreCase("00")) {
                    if (str11LvlPos1[0].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos1[0] = strData;
                } else if (locationAddress.equalsIgnoreCase("01")) {
                    if (str11LvlPos2[0].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos2[0] = strData;
                } else if (locationAddress.equalsIgnoreCase("02")) {
                    if (str11LvlUpSlip[0].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlUpSlip[0] = strData;
                } else if (locationAddress.equalsIgnoreCase("03")) {
                    if (str11LvlDnSlip[0].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlDnSlip[0] = strData;
                }

                else if (locationAddress.equalsIgnoreCase("04")) {
                    if (str11LvlPos1[1].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos1[1] = strData;
                } else if (locationAddress.equalsIgnoreCase("05")) {
                    if (str11LvlPos2[1].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos2[1] = strData;
                } else if (locationAddress.equalsIgnoreCase("06")) {
                    if (str11LvlUpSlip[1].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlUpSlip[1] = strData;
                } else if (locationAddress.equalsIgnoreCase("07")) {
                    if (str11LvlDnSlip[1].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlDnSlip[1] = strData;
                }

                else if (locationAddress.equalsIgnoreCase("08")) {
                    if (str11LvlPos1[2].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos1[2] = strData;
                } else if (locationAddress.equalsIgnoreCase("09")) {
                    if (str11LvlPos2[2].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos2[2] = strData;
                } else if (locationAddress.equalsIgnoreCase("0A")) {
                    if (str11LvlUpSlip[2].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlUpSlip[2] = strData;
                } else if (locationAddress.equalsIgnoreCase("0B")) {
                    if (str11LvlDnSlip[2].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlDnSlip[2] = strData;
                }

                else if (locationAddress.equalsIgnoreCase("0C")) {
                    if (str11LvlPos1[3].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos1[3] = strData;
                } else if (locationAddress.equalsIgnoreCase("0D")) {
                    if (str11LvlPos2[3].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos2[3] = strData;
                } else if (locationAddress.equalsIgnoreCase("0E")) {
                    if (str11LvlUpSlip[3].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlUpSlip[3] = strData;
                } else if (locationAddress.equalsIgnoreCase("0F")) {
                    if (str11LvlDnSlip[3].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlDnSlip[3] = strData;
                }

                else if (locationAddress.equalsIgnoreCase("10")) {
                    if (str11LvlPos1[4].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos1[4] = strData;
                } else if (locationAddress.equalsIgnoreCase("11")) {
                    if (str11LvlPos2[4].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos2[4] = strData;
                } else if (locationAddress.equalsIgnoreCase("12")) {
                    if (str11LvlUpSlip[4].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlUpSlip[4] = strData;
                } else if (locationAddress.equalsIgnoreCase("13")) {
                    if (str11LvlDnSlip[4].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlDnSlip[4] = strData;
                }

                else if (locationAddress.equalsIgnoreCase("14")) {
                    if (str11LvlPos1[5].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos1[5] = strData;
                } else if (locationAddress.equalsIgnoreCase("15")) {
                    if (str11LvlPos2[5].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos2[5] = strData;
                } else if (locationAddress.equalsIgnoreCase("16")) {
                    if (str11LvlUpSlip[5].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlUpSlip[5] = strData;
                } else if (locationAddress.equalsIgnoreCase("17")) {
                    if (str11LvlDnSlip[5].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlDnSlip[5] = strData;
                }

                else if (locationAddress.equalsIgnoreCase("18")) {
                    if (str11LvlPos1[6].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos1[6] = strData;
                } else if (locationAddress.equalsIgnoreCase("19")) {
                    if (str11LvlPos2[6].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos2[6] = strData;
                } else if (locationAddress.equalsIgnoreCase("1A")) {
                    if (str11LvlUpSlip[6].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlUpSlip[6] = strData;
                } else if (locationAddress.equalsIgnoreCase("1B")) {
                    if (str11LvlDnSlip[6].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlDnSlip[6] = strData;
                }

                else if (locationAddress.equalsIgnoreCase("1C")) {
                    if (str11LvlPos1[7].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos1[7] = strData;
                } else if (locationAddress.equalsIgnoreCase("1D")) {
                    if (str11LvlPos2[7].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos2[7] = strData;
                } else if (locationAddress.equalsIgnoreCase("1E")) {
                    if (str11LvlUpSlip[7].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlUpSlip[7] = strData;
                } else if (locationAddress.equalsIgnoreCase("1F")) {
                    if (str11LvlDnSlip[7].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlDnSlip[7] = strData;
                }

                else if (locationAddress.equalsIgnoreCase("20")) {
                    if (str11LvlPos1[8].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos1[8] = strData;
                } else if (locationAddress.equalsIgnoreCase("21")) {
                    if (str11LvlPos2[8].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos2[8] = strData;
                } else if (locationAddress.equalsIgnoreCase("22")) {
                    if (str11LvlUpSlip[8].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlUpSlip[8] = strData;
                } else if (locationAddress.equalsIgnoreCase("23")) {
                    if (str11LvlDnSlip[8].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlDnSlip[8] = strData;
                }

                else if (locationAddress.equalsIgnoreCase("24")) {
                    if (str11LvlPos1[9].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos1[9] = strData;
                } else if (locationAddress.equalsIgnoreCase("25")) {
                    if (str11LvlPos2[9].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos2[9] = strData;
                } else if (locationAddress.equalsIgnoreCase("26")) {
                    if (str11LvlUpSlip[9].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlUpSlip[9] = strData;
                } else if (locationAddress.equalsIgnoreCase("27")) {
                    if (str11LvlDnSlip[9].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlDnSlip[9] = strData;
                }

                else if (locationAddress.equalsIgnoreCase("28")) {
                    if (str11LvlPos1[10].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos1[10] = strData;
                } else if (locationAddress.equalsIgnoreCase("29")) {
                    if (str11LvlPos2[10].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos2[10] = strData;
                } else if (locationAddress.equalsIgnoreCase("2A")) {
                    if (str11LvlUpSlip[10].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlUpSlip[10] = strData;
                } else if (locationAddress.equalsIgnoreCase("2B")) {
                    if (str11LvlDnSlip[10].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlDnSlip[10] = strData;
                }

                else if (locationAddress.equalsIgnoreCase("2C")) {
                    if (str11LvlPos1[11].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos1[11] = strData;
                } else if (locationAddress.equalsIgnoreCase("2D")) {
                    if (str11LvlPos2[11].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos2[11] = strData;
                } else if (locationAddress.equalsIgnoreCase("2E")) {
                    if (str11LvlUpSlip[11].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlUpSlip[11] = strData;
                } else if (locationAddress.equalsIgnoreCase("2F")) {
                    if (str11LvlDnSlip[11].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlDnSlip[11] = strData;
                }

                else if (locationAddress.equalsIgnoreCase("30")) {
                    if (str11LvlPos1[12].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos1[12] = strData;
                } else if (locationAddress.equalsIgnoreCase("31")) {
                    if (str11LvlPos2[12].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos2[12] = strData;
                } else if (locationAddress.equalsIgnoreCase("32")) {
                    if (str11LvlUpSlip[12].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlUpSlip[12] = strData;
                } else if (locationAddress.equalsIgnoreCase("33")) {
                    if (str11LvlDnSlip[12].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlDnSlip[12] = strData;
                }

                else if (locationAddress.equalsIgnoreCase("34")) {
                    if (str11LvlPos1[13].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos1[13] = strData;
                } else if (locationAddress.equalsIgnoreCase("35")) {
                    if (str11LvlPos2[13].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos2[13] = strData;
                } else if (locationAddress.equalsIgnoreCase("36")) {
                    if (str11LvlUpSlip[13].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlUpSlip[13] = strData;
                } else if (locationAddress.equalsIgnoreCase("37")) {
                    if (str11LvlDnSlip[13].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlDnSlip[13] = strData;
                }

                else if (locationAddress.equalsIgnoreCase("38")) {
                    if (str11LvlPos1[14].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos1[14] = strData;
                } else if (locationAddress.equalsIgnoreCase("39")) {
                    if (str11LvlPos2[14].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos2[14] = strData;
                } else if (locationAddress.equalsIgnoreCase("3A")) {
                    if (str11LvlUpSlip[14].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlUpSlip[14] = strData;
                } else if (locationAddress.equalsIgnoreCase("3B")) {
                    if (str11LvlDnSlip[14].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlDnSlip[14] = strData;
                }

                else if (locationAddress.equalsIgnoreCase("3C")) {
                    if (str11LvlPos1[15].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos1[15] = strData;
                } else if (locationAddress.equalsIgnoreCase("3D")) {
                    if (str11LvlPos2[15].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos2[15] = strData;
                } else if (locationAddress.equalsIgnoreCase("3E")) {
                    if (str11LvlUpSlip[15].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlUpSlip[15] = strData;
                } else if (locationAddress.equalsIgnoreCase("3F")) {
                    if (str11LvlDnSlip[15].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlDnSlip[15] = strData;
                }

                else if (locationAddress.equalsIgnoreCase("40")) {
                    if (str11LvlPos1[16].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos1[16] = strData;
                } else if (locationAddress.equalsIgnoreCase("41")) {
                    if (str11LvlPos2[16].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos2[16] = strData;
                } else if (locationAddress.equalsIgnoreCase("42")) {
                    if (str11LvlUpSlip[16].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlUpSlip[16] = strData;
                } else if (locationAddress.equalsIgnoreCase("43")) {
                    if (str11LvlDnSlip[16].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlDnSlip[16] = strData;
                }

                else if (locationAddress.equalsIgnoreCase("44")) {
                    if (str11LvlPos1[17].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos1[17] = strData;
                } else if (locationAddress.equalsIgnoreCase("45")) {
                    if (str11LvlPos2[17].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos2[17] = strData;
                } else if (locationAddress.equalsIgnoreCase("46")) {
                    if (str11LvlUpSlip[17].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlUpSlip[17] = strData;
                } else if (locationAddress.equalsIgnoreCase("47")) {
                    if (str11LvlDnSlip[17].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlDnSlip[17] = strData;
                }

                else if (locationAddress.equalsIgnoreCase("48")) {
                    if (str11LvlPos1[18].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos1[18] = strData;
                } else if (locationAddress.equalsIgnoreCase("49")) {
                    if (str11LvlPos2[18].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos2[18] = strData;
                } else if (locationAddress.equalsIgnoreCase("4A")) {
                    if (str11LvlUpSlip[18].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlUpSlip[18] = strData;
                } else if (locationAddress.equalsIgnoreCase("4B")) {
                    if (str11LvlDnSlip[18].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlDnSlip[18] = strData;
                }

                else if (locationAddress.equalsIgnoreCase("4C")) {
                    if (str11LvlPos1[19].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos1[19] = strData;
                } else if (locationAddress.equalsIgnoreCase("4D")) {
                    if (str11LvlPos2[19].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos2[19] = strData;
                } else if (locationAddress.equalsIgnoreCase("4E")) {
                    if (str11LvlUpSlip[19].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlUpSlip[19] = strData;
                } else if (locationAddress.equalsIgnoreCase("4F")) {
                    if (str11LvlDnSlip[19].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlDnSlip[19] = strData;
                }

                else if (locationAddress.equalsIgnoreCase("50")) {
                    if (str11LvlPos1[20].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos1[20] = strData;
                } else if (locationAddress.equalsIgnoreCase("51")) {
                    if (str11LvlPos2[20].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos2[20] = strData;
                } else if (locationAddress.equalsIgnoreCase("52")) {
                    if (str11LvlUpSlip[20].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlUpSlip[20] = strData;
                } else if (locationAddress.equalsIgnoreCase("53")) {
                    if (str11LvlDnSlip[20].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlDnSlip[20] = strData;
                }

                else if (locationAddress.equalsIgnoreCase("54")) {
                    if (str11LvlPos1[21].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos1[21] = strData;
                } else if (locationAddress.equalsIgnoreCase("55")) {
                    if (str11LvlPos2[21].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos2[21] = strData;
                } else if (locationAddress.equalsIgnoreCase("56")) {
                    if (str11LvlUpSlip[21].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlUpSlip[21] = strData;
                } else if (locationAddress.equalsIgnoreCase("57")) {
                    if (str11LvlDnSlip[21].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlDnSlip[21] = strData;
                }

                else if (locationAddress.equalsIgnoreCase("58")) {
                    if (str11LvlPos1[22].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos1[22] = strData;
                } else if (locationAddress.equalsIgnoreCase("59")) {
                    if (str11LvlPos2[22].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos2[22] = strData;
                } else if (locationAddress.equalsIgnoreCase("5A")) {
                    if (str11LvlUpSlip[22].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlUpSlip[22] = strData;
                } else if (locationAddress.equalsIgnoreCase("5B")) {
                    if (str11LvlDnSlip[22].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlDnSlip[22] = strData;
                }

                else if (locationAddress.equalsIgnoreCase("5C")) {
                    if (str11LvlPos1[23].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos1[23] = strData;
                } else if (locationAddress.equalsIgnoreCase("5D")) {
                    if (str11LvlPos2[23].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos2[23] = strData;
                } else if (locationAddress.equalsIgnoreCase("5E")) {
                    if (str11LvlUpSlip[23].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlUpSlip[23] = strData;
                } else if (locationAddress.equalsIgnoreCase("5F")) {
                    if (str11LvlDnSlip[23].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlDnSlip[23] = strData;
                }

                else if (locationAddress.equalsIgnoreCase("60")) {
                    if (str11LvlPos1[24].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos1[24] = strData;
                } else if (locationAddress.equalsIgnoreCase("61")) {
                    if (str11LvlPos2[24].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos2[24] = strData;
                } else if (locationAddress.equalsIgnoreCase("62")) {
                    if (str11LvlUpSlip[24].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlUpSlip[24] = strData;
                } else if (locationAddress.equalsIgnoreCase("63")) {
                    if (str11LvlDnSlip[24].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlDnSlip[24] = strData;
                }

                else if (locationAddress.equalsIgnoreCase("64")) {
                    if (str11LvlPos1[25].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos1[25] = strData;
                } else if (locationAddress.equalsIgnoreCase("65")) {
                    if (str11LvlPos2[25].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos2[25] = strData;
                } else if (locationAddress.equalsIgnoreCase("66")) {
                    if (str11LvlUpSlip[25].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlUpSlip[25] = strData;
                } else if (locationAddress.equalsIgnoreCase("67")) {
                    if (str11LvlDnSlip[25].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlDnSlip[25] = strData;
                }

                else if (locationAddress.equalsIgnoreCase("68")) {
                    if (str11LvlPos1[26].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos1[26] = strData;
                } else if (locationAddress.equalsIgnoreCase("69")) {
                    if (str11LvlPos2[26].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos2[26] = strData;
                } else if (locationAddress.equalsIgnoreCase("6A")) {
                    if (str11LvlUpSlip[26].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlUpSlip[26] = strData;
                } else if (locationAddress.equalsIgnoreCase("6B")) {
                    if (str11LvlDnSlip[26].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlDnSlip[26] = strData;
                }

                else if (locationAddress.equalsIgnoreCase("6C")) {
                    if (str11LvlPos1[27].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos1[27] = strData;
                } else if (locationAddress.equalsIgnoreCase("6D")) {
                    if (str11LvlPos2[27].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos2[27] = strData;
                } else if (locationAddress.equalsIgnoreCase("6E")) {
                    if (str11LvlUpSlip[27].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlUpSlip[27] = strData;
                } else if (locationAddress.equalsIgnoreCase("6F")) {
                    if (str11LvlDnSlip[27].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlDnSlip[27] = strData;
                }

                else if (locationAddress.equalsIgnoreCase("70")) {
                    if (str11LvlPos1[28].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos1[28] = strData;
                } else if (locationAddress.equalsIgnoreCase("71")) {
                    if (str11LvlPos2[28].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos2[28] = strData;
                } else if (locationAddress.equalsIgnoreCase("72")) {
                    if (str11LvlUpSlip[28].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlUpSlip[28] = strData;
                } else if (locationAddress.equalsIgnoreCase("73")) {
                    if (str11LvlDnSlip[28].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlDnSlip[28] = strData;
                }

                else if (locationAddress.equalsIgnoreCase("74")) {
                    if (str11LvlPos1[29].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos1[29] = strData;
                } else if (locationAddress.equalsIgnoreCase("75")) {
                    if (str11LvlPos2[29].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos2[29] = strData;
                } else if (locationAddress.equalsIgnoreCase("76")) {
                    if (str11LvlUpSlip[29].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlUpSlip[29] = strData;
                } else if (locationAddress.equalsIgnoreCase("77")) {
                    if (str11LvlDnSlip[29].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlDnSlip[29] = strData;
                }

                else if (locationAddress.equalsIgnoreCase("78")) {
                    if (str11LvlPos1[30].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos1[30] = strData;
                } else if (locationAddress.equalsIgnoreCase("79")) {
                    if (str11LvlPos2[30].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos2[30] = strData;
                } else if (locationAddress.equalsIgnoreCase("7A")) {
                    if (str11LvlUpSlip[30].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlUpSlip[30] = strData;
                } else if (locationAddress.equalsIgnoreCase("7B")) {
                    if (str11LvlDnSlip[30].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlDnSlip[30] = strData;
                }

                else if (locationAddress.equalsIgnoreCase("7C")) {
                    if (str11LvlPos1[31].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos1[31] = strData;
                } else if (locationAddress.equalsIgnoreCase("7D")) {
                    if (str11LvlPos2[31].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlPos2[31] = strData;
                } else if (locationAddress.equalsIgnoreCase("7E")) {
                    if (str11LvlUpSlip[31].equals("")){
                        count_loader_ViewLevel++;
                    }
                    str11LvlUpSlip[31] = strData;
                } else if (locationAddress.equalsIgnoreCase("7F")) {
                    if (str11LvlDnSlip[31].equals("")){
                        count_loader_ViewLevel++;
                    }
                    Log.d(TAG, "processReceivedData: inside last if");
                    str11LvlDnSlip[31] = strData;
                }
                /*else if (locationAddress.equalsIgnoreCase("C0")) {
                    str11DisFlr0 = ("" + arrPattern.get(data));
                } else if (locationAddress.equalsIgnoreCase("C1")) {
                    str11DisFlr1 = ("" + arrPattern.get(data));
                }else if (locationAddress.equalsIgnoreCase("C2")) {
                    str11DisFlr2 = ("" + arrPattern.get(data));
                }else if (locationAddress.equalsIgnoreCase("C3")) {
                    str11DisFlr3 = ("" + arrPattern.get(data));
                }else if (locationAddress.equalsIgnoreCase("C4")) {
                    str11DisFlr4 = ("" + arrPattern.get(data));
                }else if (locationAddress.equalsIgnoreCase("C5")) {
                    str11DisFlr5 = ("" + arrPattern.get(data));
                }else if (locationAddress.equalsIgnoreCase("C6")) {
                    str11DisFlr6 = ("" + arrPattern.get(data));
                }else if (locationAddress.equalsIgnoreCase("C7")) {
                    str11DisFlr7 = ("" + arrPattern.get(data));
                }else if (locationAddress.equalsIgnoreCase("C8")) {
                    str11DisFlr8 = ("" + arrPattern.get(data));
                }else if (locationAddress.equalsIgnoreCase("C9")) {
                    str11DisFlr9 = ("" + arrPattern.get(data));
                }else if (locationAddress.equalsIgnoreCase("CA")) {
                    str11DisFlr10 = ("" + arrPattern.get(data));
                }else if (locationAddress.equalsIgnoreCase("CB")) {
                    str11DisFlr11 = ("" + arrPattern.get(data));
                }else if (locationAddress.equalsIgnoreCase("CC")) {
                    str11DisFlr12 = ("" + arrPattern.get(data));
                }else if (locationAddress.equalsIgnoreCase("CD")) {
                    str11DisFlr13 = ("" + arrPattern.get(data));
                }else if (locationAddress.equalsIgnoreCase("CE")) {
                    str11DisFlr14 = ("" + arrPattern.get(data));
                }else if (locationAddress.equalsIgnoreCase("CF")) {
                    str11DisFlr15 = ("" + arrPattern.get(data));
                }else if (locationAddress.equalsIgnoreCase("D0")) {
                    str11DisFlr16 = ("" + arrPattern.get(data));
                }else if (locationAddress.equalsIgnoreCase("D1")) {
                    str11DisFlr17 = ("" + arrPattern.get(data));
                }else if (locationAddress.equalsIgnoreCase("D2")) {
                    str11DisFlr18 = ("" + arrPattern.get(data));
                }else if (locationAddress.equalsIgnoreCase("D3")) {
                    str11DisFlr19 = ("" + arrPattern.get(data));
                }else if (locationAddress.equalsIgnoreCase("D4")) {
                    str11DisFlr20 = ("" + arrPattern.get(data));
                }else if (locationAddress.equalsIgnoreCase("D5")) {
                    str11DisFlr21 = ("" + arrPattern.get(data));
                }else if (locationAddress.equalsIgnoreCase("D6")) {
                    str11DisFlr22 = ("" + arrPattern.get(data));
                }else if (locationAddress.equalsIgnoreCase("D7")) {
                    str11DisFlr23 = ("" + arrPattern.get(data));
                }else if (locationAddress.equalsIgnoreCase("D8")) {
                    str11DisFlr24 = ("" + arrPattern.get(data));
                }else if (locationAddress.equalsIgnoreCase("D9")) {
                    str11DisFlr25 = ("" + arrPattern.get(data));
                }else if (locationAddress.equalsIgnoreCase("DA")) {
                    str11DisFlr26 = ("" + arrPattern.get(data));
                }else if (locationAddress.equalsIgnoreCase("DB")) {
                    str11DisFlr27 = ("" + arrPattern.get(data));
                }else if (locationAddress.equalsIgnoreCase("DC")) {
                    str11DisFlr28 = ("" + arrPattern.get(data));
                }else if (locationAddress.equalsIgnoreCase("DD")) {
                    str11DisFlr29 = ("" + arrPattern.get(data));
                }else if (locationAddress.equalsIgnoreCase("DE")) {
                    str11DisFlr30 = ("" + arrPattern.get(data));
                }else if (locationAddress.equalsIgnoreCase("DF")) {
                    str11DisFlr31 = ("" + arrPattern.get(data));
                }*/
            }
        }

        //TODO:Protocol for Serial ID
        if(temp.startsWith("11f1")){
            String sum = Utils.calculateChecksumValueNew(temp);
        //     Log.e("Tag_DeviceIdsM", "" + sum.substring(2, 4) + " -- " + temp.substring(temp.length() - 2, temp.length()) );

            if (sum.substring(2, 4).equalsIgnoreCase(temp.substring(temp.length() - 2, temp.length()))) {
                String locationAddress = temp.substring(6, 8);

                int data = Integer.parseInt(temp.substring(8, 10), 16);

              //  Log.e("Tag_DeviceIdsM", "locationAddress = " + locationAddress + " data = " + data);
//                        Utils.showToastMsg(getActivity(), " Data = "+data +" char =  "+ temp.charAt(index - 1));
                String lsb = temp.substring(8, 10);//this is msb
                String sb = temp.substring(6, 8);
                String msb = temp.substring(4, 6);//this is lsb
                String id = lsb + sb + msb;//it will msb + sb + lsb
                int device = Integer.parseInt(temp.substring(10, 12));
                Log.e("Tag_DeviceIdsMain", "Device  = " + device + " Id :" + id+ " temp = " + temp+ " count= "+count_IDs);

                //todo: ==================change by payal
               /* for (int i=0; i<=31 ; i++){//wrong this code is
                    str11f1Id[i] = "";

                }*/
              //  Log.d("Tag_DeviceIdsM", "===========array blank===============");


                switch (device) {
                    case 0:
                        if (str11f1Id[0].equals(""))
                        {
                            count_IDs++;
                        }
                        str11f1Id[0] = id;
                        break;
                    case 1:
                        if (str11f1Id[1].equals(""))
                        {
                            count_IDs++;
                        }
                        str11f1Id[1] = id;
                        break;
                    case 2:
                        if (str11f1Id[2].equals(""))
                        {
                            count_IDs++;
                        }
                        str11f1Id[2] = id;
                        break;
                    case 3:
                        if (str11f1Id[3].equals(""))
                        {
                            count_IDs++;
                        }
                        str11f1Id[3] = id;
                        break;
                    case 4:
                        if (str11f1Id[4].equals(""))
                        {
                            count_IDs++;
                        }
                        str11f1Id[4] = id;
                        break;
                    case 5:
                        if (str11f1Id[5].equals(""))
                        {
                            count_IDs++;
                        }
                        str11f1Id[5] = id;
                        break;
                    case 6:
                        if (str11f1Id[6].equals(""))
                        {
                            count_IDs++;
                        }
                        str11f1Id[6] = id;
                        break;
                    case 7:
                        if (str11f1Id[7].equals(""))
                        {
                            count_IDs++;
                        }
                        str11f1Id[7] = id;
                        break;
                    case 8:
                        if (str11f1Id[8].equals(""))
                        {
                            count_IDs++;
                        }
                        str11f1Id[8] = id;
                        break;
                    case 9:
                        if (str11f1Id[9].equals(""))
                        {
                            count_IDs++;
                        }
                        str11f1Id[9] = id;
                        break;
                    case 10:
                        if (str11f1Id[10].equals(""))
                        {
                            count_IDs++;
                        }
                        str11f1Id[10] = id;
                        break;
                    case 11:
                        if (str11f1Id[11].equals(""))
                        {
                            count_IDs++;
                        }
                        str11f1Id[11] = id;
                        break;
                    case 12:
                        if (str11f1Id[12].equals(""))
                        {
                            count_IDs++;
                        }
                        str11f1Id[12] = id;
                        break;
                    case 13:if (str11f1Id[13].equals(""))
                        {
                            count_IDs++;
                        }
                        str11f1Id[13] = id;
                        break;
                    case 14:if (str11f1Id[14].equals(""))
                        {
                            count_IDs++;
                        }
                        str11f1Id[14] = id;
                        break;
                    case 15:if (str11f1Id[15].equals(""))
                        {
                            count_IDs++;
                        }
                        str11f1Id[15] = id;
                        break;
                    case 16:if (str11f1Id[16].equals(""))
                        {
                            count_IDs++;
                        }
                        str11f1Id[16] = id;
                        break;
                    case 17:if (str11f1Id[17].equals(""))
                        {
                            count_IDs++;
                        }
                        str11f1Id[17] = id;
                        break;
                    case 18:if (str11f1Id[18].equals(""))
                        {
                            count_IDs++;
                        }
                        str11f1Id[18] = id;
                        break;
                    case 19:if (str11f1Id[19].equals(""))
                        {
                            count_IDs++;
                        }
                        str11f1Id[19] = id;
                        break;
                    case 20:if (str11f1Id[20].equals(""))
                        {
                            count_IDs++;
                        }
                        str11f1Id[20] = id;
                        break;
                    case 21:if (str11f1Id[21].equals(""))
                        {
                            count_IDs++;
                        }
                        str11f1Id[21] = id;
                        break;
                    case 22:if (str11f1Id[22].equals(""))
                        {
                            count_IDs++;
                        }
                        str11f1Id[22] = id;
                        break;
                    case 23:if (str11f1Id[23].equals(""))
                        {
                            count_IDs++;
                        }
                        str11f1Id[23] = id;
                        break;
                    case 24:if (str11f1Id[24].equals(""))
                        {
                            count_IDs++;
                        }
                        str11f1Id[24] = id;
                        break;
                    case 25:if (str11f1Id[25].equals(""))
                        {
                            count_IDs++;
                        }
                        str11f1Id[25] = id;
                        break;
                    case 26:if (str11f1Id[26].equals(""))
                        {
                            count_IDs++;
                        }
                        str11f1Id[26] = id;
                        break;
                    case 27:if (str11f1Id[27].equals(""))
                        {
                            count_IDs++;
                        }
                        str11f1Id[27] = id;
                        break;
                    case 28:if (str11f1Id[28].equals(""))
                        {
                            count_IDs++;
                        }
                        str11f1Id[28] = id;
                        break;
                    case 29:if (str11f1Id[29].equals(""))
                        {
                            count_IDs++;
                        }
                        str11f1Id[29] = id;
                        break;
                    case 30:if (str11f1Id[30].equals(""))
                        {
                            count_IDs++;
                        }
                        str11f1Id[30] = id;
                        break;
                    case 31:if (str11f1Id[31].equals(""))
                        {
                            count_IDs++;
                        }
                        str11f1Id[31] = id;
                        break;
                    case 32:if (str11f1Id[32].equals(""))
                        {
                            count_IDs++;
                        }
                        str11f1Id[32] = id;
                        break;
                    case 33:if (str11f1Id[33].equals(""))
                        {
                            count_IDs++;
                        }
                        str11f1Id[33] = id;
                        break;






                        //============================================================
                    case 41:
                        if (str11f1Param[0].equals("")){
                            count_loader_ProgrammableParameter++;
                        }
                        str11f1Param[0] = id;
                        break;
                    case 42:
                        if (str11f1Param[1].equals("")){
                            count_loader_ProgrammableParameter++;
                        }
                        str11f1Param[1] = id;
                        break;
                    case 43:
                        if (str11f1Param[2].equals("")){
                            count_loader_ProgrammableParameter++;
                        }
                        str11f1Param[2] = id;
                        break;
                    case 44:
                        if (str11f1Param[3].equals("")){
                            count_loader_ProgrammableParameter++;
                        }
                        str11f1Param[3] = id;
                        break;
                    case 45:
                        if (str11f1Param[4].equals("")){
                            count_loader_ProgrammableParameter++;
                        }
                        str11f1Param[4] = id;
                        break;
                    case 46:
                        if (str11f1Param[5].equals("")){
                            count_loader_ProgrammableParameter++;
                        }
                        str11f1Param[5] = id;
                        break;
                    case 47:
                        if (str11f1Param[6].equals("")){
                            count_loader_ProgrammableParameter++;
                        }
                        str11f1Param[6] = id;
                        break;
                    case 48:
                        if (str11f1Param[7].equals("")){
                            count_loader_ProgrammableParameter++;
                        }
                        str11f1Param[7] = id;
                        break;
                    case 49:
                        if (str11f1Param[8].equals("")){
                            count_loader_ProgrammableParameter++;
                        }
                        str11f1Param[8] = id;
                        break;
                    case 50:
                        if (str11f1Param[9].equals("")){
                            count_loader_ProgrammableParameter++;
                        }
                        str11f1Param[9] = id;
                        break;
                    case 51:
                        if (str11f1Param[10].equals("")){
                            count_loader_ProgrammableParameter++;
                        }
                        str11f1Param[10] = id;
                        break;
                    case 52:
                        if (str11f1Param[11].equals("")){
                            count_loader_ProgrammableParameter++;
                        }
                        str11f1Param[11] = id;
                        break;
                    case 53:
                        if (str11f1Param[12].equals("")){
                            count_loader_ProgrammableParameter++;
                        }
                        str11f1Param[12] = id;
                        break;
                    case 54:
                        if (str11f1Param[13].equals("")){
                            count_loader_ProgrammableParameter++;
                        }
                        str11f1Param[13] = id;
                        break;
                    case 55:
                        if (str11f1Param[14].equals("")){
                            count_loader_ProgrammableParameter++;
                        }
                        str11f1Param[14] = id;
                        break;
                }
            }
        }
    }








    /**
     * Sends a message.
     *
     * @param message
     *            A string of text to send.
     */
    public static void sendMessage(byte[] message) {
        // Check that we're actually connected before trying anything
        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
            /*Toast.makeText(this.getApplicationContext(), R.string.not_connected, Toast.LENGTH_SHORT)
                    .show();*/

            return;
        }

        // Check that there's actually something to send
        if (message.length> 0) {
            msgAppend.setLength(0);
            // Get the message bytes and tell the BluetoothConnectionRoom to write
            //byte[] send = message.getBytes();
            mChatService.write(message);

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
            //mOutEditText.setText(mOutStringBuffer);
        }
    }

    public static boolean isConnected(){

        return (mChatService.getState() == BluetoothChatService.STATE_CONNECTED);

        /*if (mChatService.getState() == BluetoothChatService.STATE_CONNECTED) {
            return true;
        }else {
            return false;
        }*/
    }

    @Override
    public void onStart() {
        super.onStart();
       /* if (D)
            Log.e(TAG, "++ ON START ++");*/

        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the chat session
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                askLocationPermission();
            }


            if (mChatService == null)
                setupChat();
        }
    }

    private void askLocationPermission() {
        //Prompt the user once explanation has been shown
        if (ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission
                .ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ||
        ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }//else permission already granted
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
         //   Toast.makeText(this, "Scanning Bluetooth devices", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public synchronized void onResume() {
        super.onResume();


       /* if (D)
            Log.e(TAG, "+ ON RESUME +");*/

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity
        // returns.
        /* if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't
            // started already
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
                // Start the Bluetooth chat services
                mChatService.start();
            }
        }*/


        Log.d(TAG, "onResume: mChatService="+mChatService);
        if (mChatService != null) {//if changed to == device will not get connected autometically
           /* if (llError.getVisibility() == View.VISIBLE){
                llError.setVisibility(View.GONE);
            }*/
            Log.d(TAG, "onResume: mChatService state="+mChatService.getState());
            // Only if the state is STATE_NONE, do we know that we haven't
            // started already
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
                // Start the Bluetooth chat services
                mChatService.start();
            }
            if (mChatService.getState() == BluetoothChatService.STATE_CONNECTING){
                //if device connection was lost and retrying again
                if (llError.getVisibility() == View.VISIBLE){
                    llError.setVisibility(View.GONE);
                }
            }
        }//else setupChat();//payal trial
    }

    private Runnable checkDataContinue = new Runnable() {

        public void run() {
            try {
                if (isConnected()) {
                    try{
                    /*if(mConnectedDeviceName!=null){
                        setStatus("Connected to "+ mConnectedDeviceName);
                    }*/

                        menu.findItem(R.id.menu_search).setIcon(ContextCompat.getDrawable(mContext, R.drawable.grn_bt));
                    }
                    catch (Exception e){
                        //Catch
                    }
                }
                else {
                    try{
                        // setStatus(R.string.title_not_connected);
                        menu.findItem(R.id.menu_search).setIcon(ContextCompat.getDrawable(mContext, R.drawable.red_bt));
                    }
                    catch (Exception e){
                        //Catch
                    }
                }

            }catch (Exception e){
                //Catch null pointer exception
            }
            myHandlerChk.postDelayed(this, 100);
        }

    };

    private void setupChat() {
      //  Log.d(TAG, "setupChat()");


        // Initialize the BluetoothChatService to perform bluetooth connections
        mChatService = new BluetoothChatService(this, mHandler);

        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
    }
    @Override
    public synchronized void onPause() {
        super.onPause();
        /*if (D)
            Log.e(TAG, "- ON PAUSE -");*/
    }

    @Override
    public void onStop() {
        super.onStop();
        /*if (D)
            Log.e(TAG, "-- ON STOP --");*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the Bluetooth chat services
        if (mChatService != null)
            mChatService.stop();
       /* if (D)
            Log.e(TAG, "--- ON DESTROY ---");*/
    }

    private final void setStatus(int resId) {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle(resId);
        actionBar.show();
    }

    private final void setStatus(CharSequence subTitle) {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle(subTitle);
        actionBar.show();
    }

    // The Handler that gets information back from the BluetoothChatService
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    if (D)
                        Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                    //final ActionBar actionBar = getSupportActionBar();
                    switch (msg.arg1) {
                        case BluetoothChatService.STATE_CONNECTED:
                            /*setStatus(getString(R.string.title_connected_to,
                                    mConnectedDeviceName));*/
                            /*setStatus(getString(R.string.title_connected_to,
                                    mConnectedDeviceName));*/
                           // menu.getItem(0).setIcon(ContextCompat.getDrawable(mContext, R.drawable.grn_bt));
                            setStatus("Connected to "+ mConnectedDeviceName);

                            //actionBar.setSubtitle("Connected to "+ mConnectedDeviceName);
                           /* //for AutoConnect
                            btConnect=0;
                            btConnect=btConnect+1;*/

                            // mConversationArrayAdapter.clear();
                            break;
                        case BluetoothChatService.STATE_CONNECTING:
                            setStatus(R.string.title_connecting);
                            //actionBar.setSubtitle(getString(R.string.title_connecting));
                            break;
                        case BluetoothChatService.STATE_LISTEN:
                           /* Toast.makeText(getApplicationContext(),
                                    "Listening Mode",
                                    Toast.LENGTH_SHORT).show();
*/
                            if(tryBTConnect==1){
                                mChatService.connect(device);
                                tryBTConnect=0;
                                return;
                            }
                            /*if(btConnect==1){
                                mChatService.connect(device);
                            }*/
                        case BluetoothChatService.STATE_NONE:
                            /*try {
                                menu.getItem(0).setIcon(ContextCompat.getDrawable(mContext, R.drawable.red_bt));
                            }
                            catch (Exception e){
                                //
                            }*/
                            setStatus(R.string.title_not_connected);
                            //actionBar.setSubtitle(getString(R.string.title_not_connected));

                            break;
                    }
                    break;
                case MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);

                    Log.d(TAG, "handleMessage: sent="+writeMessage);
                   // Toast.makeText(mContext, "sent "+writeMessage, Toast.LENGTH_SHORT).show();
                    //mConversationArrayAdapter.add("Me:  " + writeMessage);
                    break;
                case MESSAGE_READ:
                    /*byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    txtFpi.setText(readMessage);
                   *//* mConversationArrayAdapter.add(mConnectedDeviceName + ":  "
                            + readMessage);*/
                    final String readMessage = (String) msg.obj;
                    if (readMessage != null) {
                     //   Log.d(TAG, "handleMessage: in msg="+readMessage);
                        appendLog1(readMessage, false, false, needClean);
                    }
                 //   Toast.makeText(mContext, "Read data", Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    Toast.makeText(getApplicationContext(),
                            "Connected to " + mConnectedDeviceName,
                            Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(),
                            msg.getData().getString(TOAST), Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
        }
    };




    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if (D)
            Log.d(TAG, "onActivityResult " + resultCode);*/
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                   /* Toast.makeText(getApplicationContext(),
                            "Connection Request",
                            Toast.LENGTH_SHORT).show();*/
                    connectDevice(data);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    setupChat();
                } else {
                    // User did not enable Bluetooth or an error occurred
                  //  Log.d(TAG, "BT not enabled");
                    Toast.makeText(this, R.string.bt_not_enabled_leaving,
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    private void connectDevice(Intent data) {
        // Get the device MAC address
        String address = data.getExtras().getString(
                DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mChatService.connect(device);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.device_control_activity, menu);
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        this.menu = menu;
        return true;
    }
    // ============================================================================


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_search:
              //  mChatService.stop(); //this line was
                Intent serverIntent = null;
                serverIntent = new Intent(MainActivity.this, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
                return true;

            case R.id.logout:
                createDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    void createDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setIcon(R.drawable.logout);
        alertDialogBuilder.setTitle("Confirmation");

        alertDialogBuilder.setMessage("Are you sure, You want to Logout?");

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                PrefUtils.saveToPrefs(MainActivity.this, PREFS_LOGIN_USERNAME_KEY, "");
                PrefUtils.saveToPrefs(MainActivity.this, PREFS_LOGIN_PASSWORD_KEY, "");
                Intent i1 = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i1);
                finish();
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
