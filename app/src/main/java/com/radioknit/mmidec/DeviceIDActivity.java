package com.radioknit.mmidec;


import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static com.radioknit.mmidec.MainActivity.isConnected;
import static com.radioknit.mmidec.MainActivity.sendMessage;
import static com.radioknit.mmidec.MainActivity.str11f1Id;

public class DeviceIDActivity extends AppCompatActivity {

    private static final String TAG = "Tag_DeviceIDActivity";
    private String connectedDeviceName;
    ArrayList<String> arrCommandValueList;
    private Spinner spinDeviceID;
    private ArrayAdapter<String> adapter;
    private Button btnSetDeviceID;
    private EditText edtDeviceID;
    private TextView txtDeviceID;
    private Button btnViewDeviceID;
    private LinearLayout llSetDeveiceID;
    private RelativeLayout rlViewDeviceID;
    private int FLAG_DEVEICE_ID = 1;
    private Button btnSetValues;
    private Button btnGetValues;
    private TextView txtLPB_0;
    private TextView txtLPB_1;
    private TextView txtLPB_2;
    private TextView txtLPB_3;
    private TextView txtLPB_4;
    private TextView txtLPB_5;
    private TextView txtLPB_6;
    private TextView txtLPB_7;
    private TextView txtLPB_8;
    private TextView txtLPB_9;
    private TextView txtLPB_10;
    private TextView txtLPB_11;
    private TextView txtLPB_12;
    private TextView txtLPB_13;
    private TextView txtLPB_14;
    private TextView txtLPB_15;
    private TextView txtLPB_16;
    private TextView txtLPB_17;
    private TextView txtLPB_18;
    private TextView txtLPB_19;
    private TextView txtLPB_20;
    private TextView txtLPB_21;
    private TextView txtLPB_22;
    private TextView txtLPB_23;
    private TextView txtLPB_24;
    private TextView txtLPB_25;
    private TextView txtLPB_26;
    private TextView txtLPB_27;
    private TextView txtLPB_28;
    private TextView txtLPB_29;
    private TextView txtLPB_30;
    private TextView txtLPB_31;
    private TextView txtCOP_1;
    private TextView txtCOP_2;
    private TextView txtViewValue;
    private Button btnViewSingleValue;
    private int counter = 1, cntSetText = 0;
    int spinFlr = 0;
    private Context mContext;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_device_id);
        generateId();
        createObj();
        registerEvent();
        myHandlerChk.postDelayed(checkDataContinue, 0);

    }

    private void generateId() {
        btnSetDeviceID = (Button) findViewById(R.id.btnSetDeviceID);
        spinDeviceID = (Spinner) findViewById(R.id.spin_deviceID);
        edtDeviceID = (EditText) findViewById(R.id.edtDeviceID);
        txtDeviceID = (TextView) findViewById(R.id.tvDeviceID);
        btnViewDeviceID = (Button) findViewById(R.id.btnViewDeviceID);
        llSetDeveiceID = (LinearLayout) findViewById(R.id.llSetValues);
        rlViewDeviceID = (RelativeLayout) findViewById(R.id.rlViewDeviceIDValues);
        btnSetValues = (Button) findViewById(R.id.btnSetDeviceIDValues);
        btnGetValues = (Button) findViewById(R.id.btnViewDeviceIDValues);
        txtLPB_0 = (TextView) findViewById(R.id.tvLPB_0_floor);
        txtLPB_1 = (TextView) findViewById(R.id.tvLPB_1_floor);
        txtLPB_2 = (TextView) findViewById(R.id.tvLPB_2_floor);
        txtLPB_3 = (TextView) findViewById(R.id.tvLPB_3_floor);
        txtLPB_4 = (TextView) findViewById(R.id.tvLPB_4_floor);
        txtLPB_5 = (TextView) findViewById(R.id.tvLPB_5_floor);
        txtLPB_6 = (TextView) findViewById(R.id.tvLPB_6_floor);
        txtLPB_7 = (TextView) findViewById(R.id.tvLPB_7_floor);
        txtLPB_8 = (TextView) findViewById(R.id.tvLPB_8_floor);
        txtLPB_9 = (TextView) findViewById(R.id.tvLPB_9_floor);
        txtLPB_10 = (TextView) findViewById(R.id.tvLPB_10_floor);
        txtLPB_11 = (TextView) findViewById(R.id.tvLPB_11_floor);
        txtLPB_12 = (TextView) findViewById(R.id.tvLPB_12_floor);
        txtLPB_13 = (TextView) findViewById(R.id.tvLPB_13_floor);
        txtLPB_14 = (TextView) findViewById(R.id.tvLPB_14_floor);
        txtLPB_15 = (TextView) findViewById(R.id.tvLPB_15_floor);
        txtLPB_16 = (TextView) findViewById(R.id.tvLPB_16_floor);
        txtLPB_17 = (TextView) findViewById(R.id.tvLPB_17_floor);
        txtLPB_18 = (TextView) findViewById(R.id.tvLPB_18_floor);
        txtLPB_19 = (TextView) findViewById(R.id.tvLPB_19_floor);
        txtLPB_20 = (TextView) findViewById(R.id.tvLPB_20_floor);
        txtLPB_21 = (TextView) findViewById(R.id.tvLPB_21_floor);
        txtLPB_22 = (TextView) findViewById(R.id.tvLPB_22_floor);
        txtLPB_23 = (TextView) findViewById(R.id.tvLPB_23_floor);
        txtLPB_24 = (TextView) findViewById(R.id.tvLPB_24_floor);
        txtLPB_25 = (TextView) findViewById(R.id.tvLPB_25_floor);
        txtLPB_26 = (TextView) findViewById(R.id.tvLPB_26_floor);
        txtLPB_27 = (TextView) findViewById(R.id.tvLPB_27_floor);
        txtLPB_28 = (TextView) findViewById(R.id.tvLPB_28_floor);
        txtLPB_29 = (TextView) findViewById(R.id.tvLPB_29_floor);
        txtLPB_30 = (TextView) findViewById(R.id.tvLPB_30_floor);
        txtLPB_31 = (TextView) findViewById(R.id.tvLPB_31_floor);
        txtCOP_1 = (TextView) findViewById(R.id.tvCop_1);
        txtCOP_2 = (TextView) findViewById(R.id.tvCop_2);
        txtViewValue = (TextView) findViewById(R.id.textViewValue);
        btnViewSingleValue = (Button) findViewById(R.id.btnViewValue);
    }


    private void createObj() {
        getSupportActionBar().setTitle("Device ID");
        mContext = DeviceIDActivity.this;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        arrCommandValueList = new ArrayList<String>();


        for (int i = 0; i <= 31; i++) {
            arrCommandValueList.add(String.valueOf(i));
        }

        adapter = new ArrayAdapter<String>(mContext, R.layout.list_item, getResources().getStringArray(R.array.arr_device_id));

        spinDeviceID.setAdapter(adapter);

    }

    void delay(){
        try {
            Thread.sleep(50);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void registerEvent() {
        final Handler ha = new Handler();
        btnSetDeviceID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callSetDeviceID();
            }
        });


        btnViewSingleValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callViewSingleDeviceId();
            }
        });


        btnViewDeviceID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //completReceivedString.setLength(0);
                counter = 0;
                 //pd = ProgressDialog.show(mContext,"","Please wait",true);
                if (isConnected()) {
                    pd = ProgressDialog.show(mContext, "", "Please wait", true);
                    boolean b = ha.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            //call function
                            if (counter == 0) {
                                callViewDeviceId(counter);
                                delay();
                                counter++;
                            }else if (counter == 1) {
                                callViewDeviceId(counter);
                                delay();
                                counter++;
                            } else if (counter == 2) {
                                callViewDeviceId(counter);
                                delay();
                                counter++;
                            } else if (counter == 3) {
                                callViewDeviceId(counter);
                                delay();
                                counter++;
                            } else if (counter == 4) {
                                callViewDeviceId(counter);
                                delay();
                                counter++;
                            } else if (counter == 5) {
                                callViewDeviceId(counter);
                                delay();
                                counter++;
                            } else if (counter == 6) {
                                callViewDeviceId(counter);
                                delay();
                                counter++;
                            } else if (counter == 7) {
                                callViewDeviceId(counter);
                                delay();
                                counter++;
                            } else if (counter == 8) {
                                callViewDeviceId(counter);
                                delay();
                                counter++;
                            } else if (counter == 9) {
                                callViewDeviceId(counter);
                                delay();
                                counter++;
                            } else if (counter == 10) {
                                callViewDeviceId(counter);
                                delay();
                                counter++;
                            } else if (counter == 11) {
                                callViewDeviceId(counter);
                                delay();
                                counter++;
                            } else if (counter == 12) {
                                callViewDeviceId(counter);
                                delay();
                                counter++;
                            } else if (counter == 13) {
                                callViewDeviceId(counter);
                                delay();
                                counter++;
                            } else if (counter == 14) {
                                callViewDeviceId(counter);
                                delay();
                                counter++;
                            } else if (counter == 15) {
                                callViewDeviceId(counter);
                                delay();
                                counter++;
                            } else if (counter == 16) {
                                callViewDeviceId(counter);
                                delay();
                                counter++;
                            } else if (counter == 17) {
                                callViewDeviceId(counter);
                                delay();
                                counter++;
                            } else if (counter == 18) {
                                callViewDeviceId(counter);
                                delay();
                                counter++;
                            } else if (counter == 19) {
                                callViewDeviceId(counter);
                                delay();
                                counter++;
                            } else if (counter == 20) {
                                callViewDeviceId(counter);
                                delay();
                                counter++;
                            } else if (counter == 21) {
                                callViewDeviceId(counter);
                                delay();
                                counter++;
                            } else if (counter == 22) {
                                callViewDeviceId(counter);
                                delay();
                                counter++;
                            } else if (counter == 23) {
                                callViewDeviceId(counter);
                                delay();
                                counter++;
                            } else if (counter == 24) {
                                callViewDeviceId(counter);
                                delay();
                                counter++;
                            } else if (counter == 25) {
                                callViewDeviceId(counter);
                                delay();
                                counter++;
                            } else if (counter == 26) {
                                callViewDeviceId(counter);
                                delay();
                                counter++;
                            } else if (counter == 27) {
                                callViewDeviceId(counter);
                                delay();
                                counter++;
                            } else if (counter == 28) {
                                callViewDeviceId(counter);
                                delay();
                                counter++;
                            } else if (counter == 29) {
                                callViewDeviceId(counter);
                                delay();
                                counter++;
                            } else if (counter == 30) {
                                callViewDeviceId(counter);
                                delay();
                                counter++;
                            } else if (counter == 31) {
                                callViewDeviceId(counter);
                                delay();
                                counter++;
                            } else if (counter == 32) {
                                callViewDeviceId(counter);
                                delay();
                                counter++;
                            } else if (counter == 33) {
                                callViewDeviceId(counter);
                                delay();
                                counter++;
                            }else if(counter == 34){
                                callViewDeviceId(counter);
                                delay();
                                counter++;
                                if(isConnected()){
                                    pd.dismiss();
                                }
                                showReceivedDataNew();
                            }

                            ha.postDelayed(this, 500);
                        }
                    }, 500);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnSetValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlViewDeviceID.setVisibility(View.GONE);
                llSetDeveiceID.setVisibility(View.VISIBLE);
            }
        });

        btnGetValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlViewDeviceID.setVisibility(View.VISIBLE);
                llSetDeveiceID.setVisibility(View.GONE);
            }
        });
    }


    int a1 = 18;
    int a2 = 241;
    int a3;
    int a4;
    int a5;
    int a6;

    public void callSetDeviceID() {

        String temp = edtDeviceID.getText().toString();
        a3 = Integer.valueOf(String.valueOf(spinDeviceID.getSelectedItemPosition()), 16);
        if (Utils.isStringNotNull(temp)) {
            if (temp.length() == 5) {
                a4 = Integer.valueOf(temp.substring(0, 1), 16);
                a5 = Integer.valueOf(temp.substring(1, 3), 16);
                a6 = Integer.valueOf(temp.substring(3, 5), 16);
            } else {
                Utils.showToastMsg(mContext, "Device Id must be of 5 characters");
            }
        }

        int[] sendValChkSum={a1, a2, a3, a4, a5, a6};
        String strChkSum= CalculateCheckSum.calculateChkSum(sendValChkSum);
        String asciiString  = String.format("%04x", a1).substring(2,4)+String.format("%04x", a2).substring(2,4)+String.format("%04x", a3).substring(2,4)+String.format("%04x", a4).substring(2,4)+String.format("%04x", a5).substring(2,4)+String.format("%04x", a6).substring(2,4);
        asciiString = asciiString + strChkSum + "\r";
      //  Log.e(TAG, "asciiString = "+ asciiString);

        if (isConnected()) {
//            connector.write(br1);
            //connector.write(asciiString.getBytes());
            sendMessage(asciiString.getBytes());
        } else {
            Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
        }
    }


    void callViewSingleDeviceId(){
        cntSetText = 0;
        int a1 = 18;
        int a2 = 241;
        int a3;
        int a4 = 0;
        int a5 = 0;
        int a6 = 0;

        a3 = Integer.valueOf(String.valueOf(spinDeviceID.getSelectedItemPosition()), 16);

        int[] br2 = {0x12, 0xF1, 0x00, 0x00, 0x00, 0x00};
        br2[2]=a3;
        String strCmd=String.format("%02x",br2[0])+String.format("%02x",br2[1])+String.format("%02x",br2[2])+String.format("%02x",br2[3])+String.format("%02x",br2[4])+String.format("%02x",br2[5]);
      //  Log.e(TAG, "strCmd = "+ strCmd);

        String asciiString  = String.format("%04x", a1).substring(2,4)+String.format("%04x", a2).substring(2,4)+String.format("%04x", a3).substring(2,4)+String.format("%04x", a4).substring(2,4)+String.format("%04x", a5).substring(2,4)+String.format("%04x", a6).substring(2,4) ;
        String strChkSum= CalculateCheckSum.calculateChkSum(br2);
        asciiString = asciiString + strChkSum + "\r";

     //   Log.e(TAG, "asciiString = "+ asciiString);

        if (isConnected()) {
            sendMessage(asciiString.getBytes());

            cntSetText = 1;
            spinFlr = spinDeviceID.getSelectedItemPosition();
           /* if(!str11f1Id[spinFlr].equals("")){
                txtViewValue.setText(str11f1Id[spinFlr]);
            }*/
        } else {
            Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
        }
    }

    public void setTextViewValue(int flrNo){

            if(!str11f1Id[flrNo].equals("")){
                txtViewValue.setText(str11f1Id[flrNo]);
                //cntSetText = 0;
            }

    }

    private void callViewDeviceId(int value) {

        Log.d(TAG, "callViewDeviceId: Sending counter="+value);

        int a1 = 18;
        int a2 = 241;
        int a3;
        int a4 = 0;
        int a5 = 0;
        int a6 = 0;

        String temp = edtDeviceID.getText().toString();
        a3 = Integer.valueOf(String.valueOf(value), 16);
      //  Log.e(TAG, "a3 = "+ String.format("%02x",a3));
        int sum = a1 + a2 + a3 + a4 + a5 + a6;
        String sumHex = String.format("%04x", sum);
        String msb = sumHex.substring(1, 2);
        String lsb = sumHex.substring(2, 4);

        int a7 = Integer.parseInt(lsb, 16);
        int msb2 = (Integer.parseInt(msb) | 50);
        int a8 = Integer.parseInt(String.valueOf(msb2), 16);

        int[] br2 = {0x12, 0xF1, 0x00, 0x00, 0x00, 0x00};
        br2[2]=a3;
        String strCmd=String.format("%02x",br2[0])+String.format("%02x",br2[1])+String.format("%02x",br2[2])+String.format("%02x",br2[3])+String.format("%02x",br2[4])+String.format("%02x",br2[5]);
      //  Log.e(TAG, "strCmd = "+ strCmd);
        /*int sumCmdString  = 0;
        for(int i = 0; i<strCmd.length(); i++){
            sumCmdString = sumCmdString + strCmd.charAt(i);
        }
        Log.e(TAG, "sumCmdString = "+ Integer.toString(sumCmdString,16).substring(1,3));
        byte[] br1 = {(byte) a1, (byte) a2, (byte) a3, (byte) a4, (byte) a5, (byte) a6, (byte) a7, (byte) a8};*/

        String asciiString  = String.format("%04x", a1).substring(2,4)+String.format("%04x", a2).substring(2,4)+String.format("%04x", a3).substring(2,4)+String.format("%04x", a4).substring(2,4)+String.format("%04x", a5).substring(2,4)+String.format("%04x", a6).substring(2,4) ;
       /* int sumSendString  = 0;
        for(int i = 0; i<asciiString.length(); i++){
            sumSendString = sumSendString + Integer.parseInt(String.format("%04x", (int) asciiString.charAt(i)).substring(2,4));
        }
        Log.e(TAG, "sumSendString = "+ sumSendString);*/
        //asciiString = asciiString +String.valueOf(sumSendString).substring(1,3)+ "\r";

        //asciiString = asciiString +Integer.toString(sumCmdString,16).substring(1,3)+ "\r";
        String strChkSum= CalculateCheckSum.calculateChkSum(br2);
        asciiString = asciiString + strChkSum + "\r";

      //  Log.e(TAG, "asciiString = "+ asciiString);

        if (isConnected()) {
//            connector.write(br1);
            //connector.write(asciiString.getBytes());
            sendMessage(asciiString.getBytes());
        } else {
            Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
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

            case R.id.wroteModeEnable:
                Intent intent = new Intent(mContext, WriteModeEnableActivity.class);
                startActivity(intent);
                return true;
       /*     case R.id.menu_search:
                final int REQUEST_CONNECT_DEVICE = 2;
                Intent serverIntent = null;
                serverIntent = new Intent(DeviceIDActivity.this, DeviceListActivity.class);
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

    // ==========================================================================
    private Runnable checkDataContinue = new Runnable() {

        public void run() {
            showReceivedDataNew();
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
            myHandlerChk.postDelayed(this, 0);
        }

    };
    public void showReceivedDataNew(){
        if(!str11f1Id[0].equals("")){
            txtLPB_0.setText(str11f1Id[0]);
        }
        if(!str11f1Id[1].equals("")){
            txtLPB_1.setText(str11f1Id[1]);
        }
        if(!str11f1Id[2].equals("")){
            txtLPB_2.setText(str11f1Id[2]);
        }
        if(!str11f1Id[3].equals("")){
            txtLPB_3.setText(str11f1Id[3]);
        }
        if(!str11f1Id[4].equals("")){
            txtLPB_4.setText(str11f1Id[4]);
        }
        if(!str11f1Id[5].equals("")){
            txtLPB_5.setText(str11f1Id[5]);
        }
        if(!str11f1Id[6].equals("")){
            txtLPB_6.setText(str11f1Id[6]);
        }
        if(!str11f1Id[7].equals("")){
            txtLPB_7.setText(str11f1Id[7]);
        }
        if(!str11f1Id[8].equals("")){
            txtLPB_8.setText(str11f1Id[8]);
        }
        if(!str11f1Id[9].equals("")){
            txtLPB_9.setText(str11f1Id[9]);
        }
        if(!str11f1Id[10].equals("")){
            txtLPB_10.setText(str11f1Id[10]);
        }
        if(!str11f1Id[11].equals("")){
            txtLPB_11.setText(str11f1Id[11]);
        }
        if(!str11f1Id[12].equals("")){
            txtLPB_12.setText(str11f1Id[12]);
        }
        if(!str11f1Id[13].equals("")){
            txtLPB_13.setText(str11f1Id[13]);
        }
        if(!str11f1Id[14].equals("")){
            txtLPB_14.setText(str11f1Id[14]);
        }
        if(!str11f1Id[15].equals("")){
            txtLPB_15.setText(str11f1Id[15]);
        }
        if(!str11f1Id[16].equals("")){
            txtLPB_16.setText(str11f1Id[16]);
        }
        if(!str11f1Id[17].equals("")){
            txtLPB_17.setText(str11f1Id[17]);
        }
        if(!str11f1Id[18].equals("")){
            txtLPB_18.setText(str11f1Id[18]);
        }
        if(!str11f1Id[19].equals("")){
            txtLPB_19.setText(str11f1Id[19]);
        }
        if(!str11f1Id[20].equals("")){
            txtLPB_20.setText(str11f1Id[20]);
        }
        if(!str11f1Id[21].equals("")){
            txtLPB_21.setText(str11f1Id[21]);
        }
        if(!str11f1Id[22].equals("")){
            txtLPB_22.setText(str11f1Id[22]);
        }
        if(!str11f1Id[23].equals("")){
            txtLPB_23.setText(str11f1Id[23]);
        }
        if(!str11f1Id[24].equals("")){
            txtLPB_24.setText(str11f1Id[24]);
        }
        if(!str11f1Id[25].equals("")){
            txtLPB_25.setText(str11f1Id[25]);
        }
        if(!str11f1Id[26].equals("")){
            txtLPB_26.setText(str11f1Id[26]);
        }
        if(!str11f1Id[27].equals("")){
            txtLPB_27.setText(str11f1Id[27]);
        }
        if(!str11f1Id[28].equals("")){
            txtLPB_28.setText(str11f1Id[28]);
        }
        if(!str11f1Id[29].equals("")){
            txtLPB_29.setText(str11f1Id[29]);
        }
        if(!str11f1Id[30].equals("")){
            txtLPB_30.setText(str11f1Id[30]);
        }
        if(!str11f1Id[31].equals("")){
            txtLPB_31.setText(str11f1Id[31]);
        }
        if(!str11f1Id[32].equals("")){
            txtCOP_1.setText(str11f1Id[32]);
        }
        if(!str11f1Id[33].equals("")){
            txtCOP_2.setText(str11f1Id[33]);
        }
    }
}
