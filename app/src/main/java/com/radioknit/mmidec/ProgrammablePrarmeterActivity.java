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
import static com.radioknit.mmidec.MainActivity.str11DisFlr;
import static com.radioknit.mmidec.MainActivity.str11f1Param;

public class ProgrammablePrarmeterActivity extends AppCompatActivity {

    private static final String TAG = "ProgrammableParammeter";
    private Context mContext;
    ArrayList<String> arrCommandValueList;
    private Spinner spinDeviceID;
    private ArrayAdapter<String> adapter;
    private Button btnSetDeviceID;
    private EditText edtDeviceID;
    private Button btnViewProgramableParameter;
    private TextView txtDeviceID;
    private LinearLayout llSetProgmmableParmeter;
    private RelativeLayout rlViewProgrammableParameter;
    private Button btnSetPPValues;
    private Button btnGetPPValues;
    private TextView txtEncoderPPR;
    private TextView txtTravelLenght_1;
    private TextView txtTravelLenght_2;
    private TextView txtTravelLenght_3;
    private TextView txtSlowSpeedDistance_1;
    private TextView txtSlowSpeedDistance_2;
    private TextView txtSlowSpeedDistance_3;
    private TextView txtSlowSpeedDistance_4;
    private TextView txtDiameterOfMainPulley;
    private TextView txtGearBoxRatio;
    private TextView txtCommanUpSlip;
    private TextView txtCommonDnSlip;
    private TextView txtRPMSetting_1;
    private TextView txtRPMSetting_2;
    private TextView txtRPMSetting_3;
    private int counter = 1;
    private Button btnViewValue;
    private TextView txtViewValue;
    private int cntSetText = 0;
    int spinFlr = 0;
    private BluetoothAdapter bluetoothAdapter;

    // =============

    private OutputStream outputStream;
    private static final String DEVICE_NAME = "DEVICE_NAME";
    private static final String LOG = "LOG";
    private ProgressDialog pd;
    private StringBuffer completReceivedString;
    final Handler myHandlerChk = new Handler();
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_programmable_prarmeter);

        generateId();
        createObj();
        registerEvent();
        myHandlerChk.postDelayed(checkDataContinue, 0);
    }


    private void generateId() {
        btnSetDeviceID = (Button) findViewById(R.id.btnSetDeviceID);
        spinDeviceID = (Spinner) findViewById(R.id.spin_deviceID);
        edtDeviceID = (EditText)findViewById(R.id.edtDeviceID);
        btnViewProgramableParameter = (Button)findViewById(R.id.btnViewDeviceID);
//        txtDeviceID = (TextView)view.findViewById(R.id.tvDeviceID);

        llSetProgmmableParmeter = (LinearLayout) findViewById(R.id.llProgrammable_parameter_SetValues);
        rlViewProgrammableParameter = (RelativeLayout) findViewById(R.id.rlViewProgrammable_parameter_Values);
        btnSetPPValues = (Button) findViewById(R.id.btnSetProgrammable_parameter_Values);
        btnGetPPValues = (Button) findViewById(R.id.btnViewProgrammable_parameter_Values);
        txtEncoderPPR = (TextView)findViewById(R.id.tvEncoderPPR);
        txtTravelLenght_1 = (TextView)findViewById(R.id.tvTravelLength_1);
        txtTravelLenght_2 = (TextView)findViewById(R.id.tvTravel_length_2);
        txtTravelLenght_3 = (TextView)findViewById(R.id.tvTravel_length_3);
        txtSlowSpeedDistance_1 = (TextView)findViewById(R.id.tvSlowSpeedDistanceOne);
        txtSlowSpeedDistance_2 = (TextView)findViewById(R.id.tvSlowSpeedDistance_2);
        txtSlowSpeedDistance_3 = (TextView)findViewById(R.id.tvSlowSpeedDistance_3);
        txtSlowSpeedDistance_4 = (TextView)findViewById(R.id.tvSlowSpeedDistance_4);
        txtDiameterOfMainPulley = (TextView)findViewById(R.id.tvDiameterOfMainPulley);
        txtGearBoxRatio = (TextView)findViewById(R.id.tvGareBoxRatio);
        txtCommanUpSlip = (TextView)findViewById(R.id.tvCommonUpSlip);
        txtCommonDnSlip = (TextView)findViewById(R.id.tvCommonDnSlip);
        txtRPMSetting_1 = (TextView)findViewById(R.id.tvRPMSetting_1);
        txtRPMSetting_2 = (TextView)findViewById(R.id.tvRPMSetting_2);
        txtRPMSetting_3 = (TextView)findViewById(R.id.tvRPMSetting_3);
        btnViewValue = (Button)findViewById(R.id.btnViewValue);
        txtViewValue = (TextView)findViewById(R.id.textViewValue);
    }

    private void createObj() {
        getSupportActionBar().setTitle("Programmable Parameter");
        mContext = ProgrammablePrarmeterActivity.this;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        arrCommandValueList = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(mContext, R.layout.list_item, getResources().getStringArray(R.array.arr_deviceid_programable_parameter));

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

        btnViewValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callViewSingleDeviceId();
            }
        });

        btnViewProgramableParameter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // completReceivedString.setLength(0);
                if (isConnected()) {
                   // pd = ProgressDialog.show(mContext, "", "Please wait", true);
                pd = ProgressDialog.show(mContext,"","Please wait",true);
                counter = 0;
                boolean b = ha.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        //call function
                        if (counter == 0) {
                            callViewProgramableparametter(counter);
                            delay();
                            counter++;
                        }else if (counter == 1) {
                            callViewProgramableparametter(counter);
                            delay();
                            counter++;
                        } else if (counter == 2) {
                            callViewProgramableparametter(counter);
                            delay();
                            counter++;
                        } else if (counter == 3) {
                            callViewProgramableparametter(counter);
                            delay();
                            counter++;
                        } else if (counter == 4) {
                            callViewProgramableparametter(counter);
                            delay();
                            counter++;
                        } else if (counter == 5) {
                            callViewProgramableparametter(counter);
                            delay();
                            counter++;
                        } else if (counter == 6) {
                            callViewProgramableparametter(counter);
                            delay();
                            counter++;
                        } else if (counter == 7) {
                            callViewProgramableparametter(counter);
                            delay();
                            counter++;
                        } else if (counter == 8) {
                            callViewProgramableparametter(counter);
                            delay();
                            counter++;
                        } else if (counter == 9) {
                            callViewProgramableparametter(counter);
                            delay();
                            counter++;
                        } else if (counter == 10) {
                            callViewProgramableparametter(counter);
                            delay();
                            counter++;
                        } else if (counter == 11) {
                            callViewProgramableparametter(counter);
                            delay();
                            counter++;
                        } else if (counter == 12) {
                            callViewProgramableparametter(counter);
                            delay();
                            counter++;
                        } else if (counter == 13) {
                            callViewProgramableparametter(counter);
                            delay();
                            counter++;
                        } else if (counter == 14) {
                            callViewProgramableparametter(counter);
                            delay();
                            counter++;

                        }else if(counter == 15){
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


        btnSetPPValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llSetProgmmableParmeter.setVisibility(View.VISIBLE);
                rlViewProgrammableParameter.setVisibility(View.GONE);
            }
        });

        btnGetPPValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llSetProgmmableParmeter.setVisibility(View.GONE);
                rlViewProgrammableParameter.setVisibility(View.VISIBLE);
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
        if(spinDeviceID.getSelectedItemPosition() == 9){
            a3 = 80;
        }else {
            a3 = 65 + Integer.valueOf(String.valueOf(spinDeviceID.getSelectedItemPosition()), 16);
        }
        if(Utils.isStringNotNull(temp)){
            if(temp.length() == 5) {
                a4 = Integer.valueOf(temp.substring(0, 1), 16);
                a5 = Integer.valueOf(temp.substring(1, 3), 16);
                a6 = Integer.valueOf(temp.substring(3, 5), 16);
            }else {
                Utils.showToastMsg(mContext, "Device Id must be of 5 characters");
            }
        }

        int[] sendValChkSum={a1, a2, a3, a4, a5, a6};
        String strChkSum= CalculateCheckSum.calculateChkSum(sendValChkSum);
        String asciiString  = String.format("%04x", a1).substring(2,4)+String.format("%04x", a2).substring(2,4)+String.format("%04x", a3).substring(2,4)+String.format("%04x", a4).substring(2,4)+String.format("%04x", a5).substring(2,4)+String.format("%04x", a6).substring(2,4);
        asciiString = asciiString + strChkSum + "\r";
//        Log.e(TAG, "asciiString = "+ asciiString);

        if (isConnected()) {
            //connector.write(asciiString.getBytes());
            sendMessage(asciiString.getBytes());
        }
        else {
            Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
        }

    }

    private void callViewProgramableparametter(int position ) {
        int a1 = 18;
        int a2 = 241;
        int a3 ;
        int a4 = 00;
        int a5 = 00;
        int a6 = 00;

        if(position == 9){
            a3 = 80;
        }else {
            a3 = 65 + Integer.valueOf(String.valueOf(position), 16);
        }

        int[] sendValChkSum={a1, a2, a3, a4, a5, a6};

        String strChkSum= CalculateCheckSum.calculateChkSum(sendValChkSum);


       /* int sum = a1 + a2 + a3 + a4 + a5 + a6;
        String sumHex = String.format("%04x", sum);

        String msb = sumHex.substring(1, 2);
        String lsb = sumHex.substring(2, 4);

        int a7 = Integer.parseInt(lsb, 16);
        int msb2 = (Integer.parseInt(msb) | 50);
        int a8 = Integer.parseInt(String.valueOf(msb2), 16);

        byte[] br2 = {(byte) a1, (byte) a2, (byte) a3, (byte) a4, (byte) a5, (byte) a6};
        byte[] br1 = {(byte) a1, (byte) a2, (byte) a3, (byte) a4, (byte) a5, (byte) a6, (byte) a7, (byte) a8};
*/
        String asciiString  = String.format("%04x", a1).substring(2,4)+String.format("%04x", a2).substring(2,4)+String.format("%04x", a3).substring(2,4)+String.format("%04x", a4).substring(2,4)+String.format("%04x", a5).substring(2,4)+String.format("%04x", a6).substring(2,4);
/*        int sumSendString  = 0;
        for(int j = 0; j<asciiString.length();j++){
            sumSendString = sumSendString + Integer.parseInt(String.format("%04x", (int) asciiString.charAt(j)).substring(2,4));
        }

        asciiString = asciiString +String.valueOf(sumSendString).substring(1,3)+ "\r";*/

        asciiString = asciiString + strChkSum + "\r";

//        Log.e(TAG, "asciiString = "+ asciiString);

        if (isConnected()) {
            //connector.write(asciiString.getBytes());
            sendMessage(asciiString.getBytes());
        }
        else {
            Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
        }

    }

    void callViewSingleDeviceId(){
        cntSetText = 0;
        int floorNo = Integer.valueOf(String.valueOf(spinDeviceID.getSelectedItemPosition()), 16);
//        Log.e(TAG, "floorNo = "+ floorNo);
        int a1 = 18;
        int a2 = 241;
        int a3 ;
        int a4 = 00;
        int a5 = 00;
        int a6 = 00;

        if(floorNo == 9){
            a3 = 80;
        }else {
            a3 = 65 + floorNo;
        }


        int[] sendValChkSum={a1, a2, a3, a4, a5, a6};

        String asciiString  = String.format("%04x", a1).substring(2,4)+String.format("%04x", a2).substring(2,4)+String.format("%04x", a3).substring(2,4)+String.format("%04x", a4).substring(2,4)+String.format("%04x", a5).substring(2,4)+String.format("%04x", a6).substring(2,4) ;
        String strChkSum= CalculateCheckSum.calculateChkSum(sendValChkSum);
        asciiString = asciiString + strChkSum + "\r";

//        Log.e(TAG, "asciiString = "+ asciiString);

        if (isConnected()) {
            sendMessage(asciiString.getBytes());

            cntSetText = 1;
            spinFlr = spinDeviceID.getSelectedItemPosition();
//            Log.e(TAG, "spinFlr = "+ spinFlr);
           /* if(!str11f1Id[spinFlr].equals("")){
                txtViewValue.setText(str11f1Id[spinFlr]);
            }*/
        } else {
            Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
        }
    }

    public void setTextViewValue(int flrNo){

        if(!str11f1Param[flrNo].equals("")){
            txtViewValue.setText(str11f1Param[flrNo]);
            //cntSetText = 0;
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
            case R.id.menu_search:
                final int REQUEST_CONNECT_DEVICE = 2;
                Intent serverIntent = null;
                serverIntent = new Intent(ProgrammablePrarmeterActivity.this, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        myHandlerChk.removeCallbacks(checkDataContinue);
        //startActivity(new Intent(mContext, MainActivity.class));
        finish();
    }

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
            showReceivedDataNew();
            if(cntSetText==1){
                setTextViewValue(spinFlr);
            }
            myHandlerChk.postDelayed(this, 0);
        }

    };

    // ==========================================================================


    public void showReceivedDataNew(){
        //Log.e(TAG, "ShowReceivedData");
        if(!str11f1Param[0].equals("")){
            txtEncoderPPR.setText(str11f1Param[0]);
        }
        if(!str11f1Param[1].equals("")){
            txtTravelLenght_1.setText(str11f1Param[1]);
        }
        if(!str11f1Param[2].equals("")){
            txtTravelLenght_2.setText(str11f1Param[2]);
        }
        if(!str11f1Param[3].equals("")){
            txtTravelLenght_3.setText(str11f1Param[3]);
        }
        if(!str11f1Param[4].equals("")){
            txtSlowSpeedDistance_1.setText(str11f1Param[4]);
        }
        if(!str11f1Param[5].equals("")){
            txtSlowSpeedDistance_2.setText(str11f1Param[5]);
        }
        if(!str11f1Param[6].equals("")){
            txtSlowSpeedDistance_3.setText(str11f1Param[6]);
        }
        if(!str11f1Param[7].equals("")){
            txtSlowSpeedDistance_4.setText(str11f1Param[7]);
        }
        if(!str11f1Param[8].equals("")){
            txtDiameterOfMainPulley.setText(str11f1Param[8]);
        }
        if(!str11f1Param[9].equals("")){
            txtGearBoxRatio.setText(str11f1Param[9]);
        }
        if(!str11f1Param[10].equals("")){
            txtCommanUpSlip.setText(str11f1Param[10]);
        }
        if(!str11f1Param[11].equals("")){
            txtCommonDnSlip.setText(str11f1Param[11]);
        }
        if(!str11f1Param[12].equals("")){
            txtRPMSetting_1.setText(str11f1Param[12]);
        }
        if(!str11f1Param[13].equals("")){
            txtRPMSetting_2.setText(str11f1Param[13]);
        }
        if(!str11f1Param[14].equals("")){
            txtRPMSetting_3.setText(str11f1Param[14]);
        }

    }

}
