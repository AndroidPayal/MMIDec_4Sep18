package com.radioknit.mmidec;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static com.radioknit.mmidec.MainActivity.isConnected;
import static com.radioknit.mmidec.MainActivity.sendMessage;
import static com.radioknit.mmidec.MainActivity.str11LvlDnSlip;
import static com.radioknit.mmidec.MainActivity.str11LvlPos1;
import static com.radioknit.mmidec.MainActivity.str11LvlPos2;
import static com.radioknit.mmidec.MainActivity.str11LvlUpSlip;

public class LevelFunctionActivity extends AppCompatActivity {

    private static final String TAG = "LevelFunctionActivity";
    private Context mContext;
    ArrayList<String> arrCommandValueList;
    private Spinner spinFloorNumber;
    private ArrayAdapter<String> adapter;
    private Button btnSetSlipValue;
    private Button btnViewSlipValue;
    private TextView txtViewValue;
    private EditText edtSetSlipValue;
    private RadioGroup rdogpUpDownSlip;
    //private Button btnAutoLevelFunction;
    private Button btnSetLevelFunction;
    private Button btnViewLevelFunction;
    private LinearLayout llViewLevelFunction;
    private int counter = 0, cntSetText = 0;
    private RelativeLayout rlViewLevelFunctionData;
    private ScrollView svSetLevelFunctionData;
    private Button btnSetValues;
    private Button btnGetValues;
    private StringBuffer mOutStringBuffer;
    public static final int FALGUPSLIP = 1;
    public static final int FLAGDOWNSLIP = 2;
    private BluetoothAdapter bluetoothAdapter;

    // =============

    private OutputStream outputStream;
    private static final String DEVICE_NAME = "DEVICE_NAME";
    private static final String LOG = "LOG";
    private ProgressDialog pd;
    private StringBuffer completReceivedString, posDataString;
    int cntViewCmdAll=0, cntViewCmd=1;
    int upSlipValue, dnSlipValue, flrValue=0;
    String chkViewLocAdd="";
    TextView textViewDataPos[]=new TextView[32];
    TextView textViewDataUp[]=new TextView[32];
    TextView textViewDataDn[]=new TextView[32];
    final Handler myHandlerChk = new Handler();
    int spinFlr = 0, flagUpDn = 0;
    private Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_level_function);
        posDataString = new StringBuffer();

        generateId();
        createObj();
        registerEvent();
        myHandlerChk.postDelayed(checkDataContinue, 0);
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
        btnSetSlipValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callSetLevelFunction();
            }
        });


        /*btnAutoLevelFunction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAutoLevelFunction();
            }
        });*/

        btnViewSlipValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callViewLevelFunction();
            }
        });

        btnSetValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                svSetLevelFunctionData.setVisibility(View.VISIBLE);
                rlViewLevelFunctionData.setVisibility(View.GONE);
            }
        });

        btnGetValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                svSetLevelFunctionData.setVisibility(View.GONE);
                rlViewLevelFunctionData.setVisibility(View.VISIBLE);
            }
        });

        btnViewLevelFunction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 0;

                if (isConnected()) {
                    pd = ProgressDialog.show(mContext, "", "Please wait", true);
                } else {
                    Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
                }
                boolean b = ha.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //call function

                        if (counter == 0) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 1) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 2) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 3) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 4) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 5) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 6) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 7) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 8) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 9) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 10) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 11) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 12) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 13) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 14) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 15) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 16) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 17) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 18) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 19) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 20) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 21) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 22) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 23) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 24) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 25) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 26) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 27) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 28) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 29) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 30) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 31) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 32) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 33) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 34) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 35) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 36) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 37) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 38) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 39) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 40) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 41) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 42) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 43) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 44) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 45) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 46) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 47) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 48) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 49) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 50) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        }else if (counter == 51) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 52) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 53) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 54) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 55) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 56) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 57) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 58) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 59) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 60) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        }else if (counter == 61) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 62) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 63) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 64) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 65) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 66) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 67) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 68) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 69) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 70) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        }else if (counter == 71) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 72) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 73) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 74) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 75) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 76) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 77) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 78) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 79) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 80) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        }else if (counter == 81) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 82) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 83) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 84) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 85) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 86) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 87) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 88) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 89) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 90) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        }else if (counter == 91) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 92) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 93) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 94) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 95) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 96) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 97) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 98) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 99) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 100) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        }else if (counter == 101) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 102) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 103) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 104) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 105) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 106) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 107) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 108) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 109) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 110) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        }else if (counter == 111) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 112) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 113) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 114) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 115) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 116) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 117) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 118) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 119) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 120) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        }else if (counter == 121) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 122) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 123) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 124) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 125) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 126) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        } else if (counter == 127) {
                            callViewAllLevelFunction(counter);
                            delay();
                            counter++;
                        }
                        else if (counter == 128) {
                            //counter++;
                            //Log.e(TAG, "counter = " + counter);
                            if (isConnected()) {
                                pd.dismiss();
                            }
                            //ha.removeCallbacks();
                            //delay();
                            //showReceivedDataNew();

                        }
                        ha.postDelayed(this, 500);
                    }
                }, 500);
            }
        });
    }

    private void createObj() {
        getSupportActionBar().setTitle("Level Function");
        mContext = LevelFunctionActivity.this;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        arrCommandValueList = new ArrayList<String>();

        for (int i = 0; i <= 31; i++) {
            arrCommandValueList.add(String.valueOf(i));
        }

        adapter = new ArrayAdapter<String>(mContext, R.layout.list_item, arrCommandValueList);

        spinFloorNumber.setAdapter(adapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.level_function_item, null);
        TextView tvFloorNo = (TextView) view.findViewById(R.id.tvLevelFunctionFlrNum);
        TextView tvFloorPos = (TextView) view.findViewById(R.id.tvLevelFunctionFlrPos);
        TextView tvFloorUpSlip = (TextView) view.findViewById(R.id.tvLevelFunctionUpSlip);
        TextView tvFloorDnSlip = (TextView) view.findViewById(R.id.tvLevelFunctionDnSlip);
        tvFloorNo.setText("Flr");
        tvFloorPos.setText("Pos");
        tvFloorUpSlip.setText("Up");
        tvFloorDnSlip.setText("Down");
        llViewLevelFunction.addView(view);

        for(int flrNo=0; flrNo<=31; flrNo++) {
            LayoutInflater inflater1 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View view1 = inflater1.inflate(R.layout.level_function_item, null);
            TextView tvFloorNo1 = (TextView) view1.findViewById(R.id.tvLevelFunctionFlrNum);
            TextView tvFloorPos1 = (TextView) view1.findViewById(R.id.tvLevelFunctionFlrPos);
            TextView tvFloorUpSlip1 = (TextView) view1.findViewById(R.id.tvLevelFunctionUpSlip);
            TextView tvFloorDnSlip1 = (TextView) view1.findViewById(R.id.tvLevelFunctionDnSlip);
            tvFloorNo1.setText(""+flrNo);
            tvFloorPos1.setText("");
            tvFloorUpSlip1.setText("");
            tvFloorDnSlip1.setText("");
            textViewDataPos[flrNo] = tvFloorPos1;
            textViewDataUp[flrNo] = tvFloorUpSlip1;
            textViewDataDn[flrNo] = tvFloorDnSlip1;
            llViewLevelFunction.addView(view1);
        }
    }

    private void generateId() {
        btnSetSlipValue = (Button)  findViewById(R.id.btnSetSlipValue);
        spinFloorNumber = (Spinner) findViewById(R.id.levelFrag_floorNumber);
        edtSetSlipValue = (EditText)findViewById(R.id.edtLevelFunctionSlipValue);
        rdogpUpDownSlip = (RadioGroup)findViewById(R.id.rdogpUpDownSlip);
       // btnAutoLevelFunction = (Button)findViewById(R.id.btnAutoLevelFunction);
        btnViewSlipValue = (Button) findViewById(R.id.btnViewValue);
        txtViewValue = (TextView) findViewById(R.id.textViewValue);

        btnSetValues = (Button) findViewById(R.id.btnLevelFunctionSetValues);
        btnGetValues = (Button)findViewById(R.id.btnLevelFunctionViewValues);
        llViewLevelFunction = (LinearLayout)findViewById(R.id.llLevelFunction);
        svSetLevelFunctionData = (ScrollView) findViewById(R.id.svSetLevelFunctionValue);
        rlViewLevelFunctionData = (RelativeLayout)findViewById(R.id.rlViewLevelFunctionValue);
        btnViewLevelFunction = (Button)findViewById(R.id.btnViewLevelFunction);
    }

    int a1 = 18;
    int a2 = 17;
    int a3 = 112;
    int a4;
    int a5;
    int a6 = 00;
    int flag;

    public void callSetLevelFunction() {

        switch (rdogpUpDownSlip.getCheckedRadioButtonId()){
            case R.id.rdoUpSlip :
                flag = FALGUPSLIP;
                break;
            case R.id.rdoDownSlip :
                flag = FLAGDOWNSLIP;
                break;
        }

        int t = spinFloorNumber.getSelectedItemPosition();
//        int t = Integer.valueOf(String.valueOf(spinFloorNumber.getSelectedItemPosition()) , 16);
     //   Log.e(TAG, "t = "+ t);
        if(t == 0){
            if(flag == FALGUPSLIP){
                a4 = 02;
            }else {
                a4 = 03;
            }
        }else if(t == 1){
            if(flag == FALGUPSLIP){
                a4 = 06;
            }else {
                a4 = 07;
            }
        }else if(t == 2){
            if(flag == FALGUPSLIP){
                a4 = 10;
            }else {
                a4 = 11;
            }
        }else if(t == 3){
            if(flag == FALGUPSLIP){
                a4 = 14;
            }else {
                a4 = 15;
            }
        }else if(t == 4){
            if(flag == FALGUPSLIP){
                a4 = 18;
            }else {
                a4 = 19;
            }
        }else if(t == 5){
            if(flag == FALGUPSLIP){
                a4 = 22;
            }else {
                a4 = 23;
            }
        }else if(t == 6){
            if(flag == FALGUPSLIP){
                a4 = 26;
            }else {
                a4 = 27;
            }
        }else if(t == 7){
            if(flag == FALGUPSLIP){
                a4 = 30;
            }else {
                a4 = 31;
            }
        }else if(t == 8){
            if(flag == FALGUPSLIP){
                a4 = 34;
            }else {
                a4 = 35;
            }
        }else if(t == 9){
            if(flag == FALGUPSLIP){
                a4 = 38;
            }else {
                a4 = 39;
            }
        }else if(t == 10){
            if(flag == FALGUPSLIP){
                a4 = 42;
            }else {
                a4 = 43;
            }
        }else if(t == 11){
            if(flag == FALGUPSLIP){
                a4 = 46;
            }else {
                a4 = 47;
            }
        }else if(t == 12){
            if(flag == FALGUPSLIP){
                a4 = 50;
            }else {
                a4 = 51;
            }
        }else if(t == 13){
            if(flag == FALGUPSLIP){
                a4 = 54;
            }else {
                a4 = 55;
            }
        }else if(t == 14){
            if(flag == FALGUPSLIP){
                a4 = 58;
            }else {
                a4 = 59;
            }
        }else if(t == 15){
            if(flag == FALGUPSLIP){
                a4 = 62;
            }else {
                a4 = 63;
            }
        }else if(t == 16){
            if(flag == FALGUPSLIP){
                a4 = 66;
            }else {
                a4 = 67;
            }
        }else if(t == 17){
            if(flag == FALGUPSLIP){
                a4 = 70;
            }else {
                a4 = 71;
            }
        }else if(t == 18){
            if(flag == FALGUPSLIP){
                a4 = 74;
            }else {
                a4 = 75;
            }
        }else if(t == 19){
            if(flag == FALGUPSLIP){
                a4 = 78;
            }else {
                a4 = 79;
            }
        }else if(t == 20){
            if(flag == FALGUPSLIP){
                a4 = 82;
            }else {
                a4 = 83;
            }
        }else if(t == 21){
            if(flag == FALGUPSLIP){
                a4 = 86;
            }else {
                a4 = 87;
            }
        }else if(t == 22){
            if(flag == FALGUPSLIP){
                a4 = 90;
            }else {
                a4 = 91;
            }
        }else if(t == 23){
            if(flag == FALGUPSLIP){
                a4 = 94;
            }else {
                a4 = 95;
            }
        }else if(t == 24){
            if(flag == FALGUPSLIP){
                a4 = 98;
            }else {
                a4 = 99;
            }
        }else if(t == 25){
            if(flag == FALGUPSLIP){
                a4 = 102;
            }else {
                a4 = 103;
            }
        }else if(t == 26){
            if(flag == FALGUPSLIP){
                a4 = 106;
            }else {
                a4 = 107;
            }
        }else if(t == 27){
            if(flag == FALGUPSLIP){
                a4 = 110;
            }else {
                a4 = 111;
            }
        }else if(t == 28){
            if(flag == FALGUPSLIP){
                a4 = 114;
            }else {
                a4 = 115;
            }
        }else if(t == 29){
            if(flag == FALGUPSLIP){
                a4 = 118;
            }else {
                a4 = 119;
            }
        }else if(t == 30){
            if(flag == FALGUPSLIP){
                a4 = 122;
            }else {
                a4 = 123;
            }
        }else if(t == 31){
            if(flag == FALGUPSLIP){
                a4 = 126;
            }else {
                a4 = 127;
            }
        }

        if(Utils.isStringNotNull(edtSetSlipValue.getText().toString())) {
            if (Integer.parseInt(edtSetSlipValue.getText().toString()) > 255) {
                Utils.showToastMsg(mContext, "Slip value must be in range of 0-255");
            } else {
                a5 = Integer.parseInt(edtSetSlipValue.getText().toString());
                int[] sendValChkSum={a1, a2, a3, a4, a5, a6};
                String strChkSum= CalculateCheckSum.calculateChkSum(sendValChkSum);
                String asciiString  = String.format("%04x", a1).substring(2,4)+String.format("%04x", a2).substring(2,4)+String.format("%04x", a3).substring(2,4)+String.format("%04x", a4).substring(2,4)+String.format("%04x", a5).substring(2,4)+String.format("%04x", a6).substring(2,4);
                asciiString = asciiString + strChkSum + "\r";
              //  Log.e(TAG, "asciiString = "+ asciiString);


                if (isConnected()) {
                    //connector.write(asciiString.getBytes());
                    sendMessage(asciiString.getBytes());
                }
                else {
                    Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
                }

            }
        }else {
            Utils.showToastMsg(mContext,"Please Enter The Level Value");
        }
    }

    private void callAutoLevelFunction() {
        if (!isConnected()) {
            Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
            return;
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LevelFunctionActivity.this);
        alertDialogBuilder.setTitle("Confirmation");
        //alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage("Are you sure, you want to auto level?");
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                int a1 = 18;
                int a2 = 17;
                int a3 = 76;
                int a4 = 00;
                int a5 = 00;
                int a6 = 00;

                int[] sendValChkSum={a1, a2, a3, a4, a5, a6};
                String strChkSum= CalculateCheckSum.calculateChkSum(sendValChkSum);
                String asciiString  = String.format("%04x", a1).substring(2,4)+String.format("%04x", a2).substring(2,4)+String.format("%04x", a3).substring(2,4)+String.format("%04x", a4).substring(2,4)+String.format("%04x", a5).substring(2,4)+String.format("%04x", a6).substring(2,4);
                asciiString = asciiString + strChkSum + "\r";
             //   Log.e(TAG, "asciiString = "+ asciiString);

                if (isConnected()) {
//            connector.write(br1);
                    //connector.write(asciiString.getBytes());
                    sendMessage(asciiString.getBytes());
                }
                else {
                    Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
                }
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

    String chkLocAdd="";
    private void callViewLevelFunction(){
        //completReceivedString.setLength(0);
        cntSetText = 0;
        int a1 = 18;
        int a2 = 17;
        int a3 = 80;
        int a4 = 00;
        int a5 = 00;
        int a6 = 00;

        switch (rdogpUpDownSlip.getCheckedRadioButtonId()){
            case R.id.rdoUpSlip :
                flag = FALGUPSLIP;
                break;
            case R.id.rdoDownSlip :
                flag = FLAGDOWNSLIP;
                break;
        }

        int t = spinFloorNumber.getSelectedItemPosition();
//        int t = Integer.valueOf(String.valueOf(spinFloorNumber.getSelectedItemPosition()) , 16);
       // Log.e(TAG, "t = "+ t);
        if(t == 0){
            if(flag == FALGUPSLIP){
                a4 = 02;
            }else {
                a4 = 03;
            }
        }else if(t == 1){
            if(flag == FALGUPSLIP){
                a4 = 06;
            }else {
                a4 = 07;
            }
        }else if(t == 2){
            if(flag == FALGUPSLIP){
                a4 = 10;
            }else {
                a4 = 11;
            }
        }else if(t == 3){
            if(flag == FALGUPSLIP){
                a4 = 14;
            }else {
                a4 = 15;
            }
        }else if(t == 4){
            if(flag == FALGUPSLIP){
                a4 = 18;
            }else {
                a4 = 19;
            }
        }else if(t == 5){
            if(flag == FALGUPSLIP){
                a4 = 22;
            }else {
                a4 = 23;
            }
        }else if(t == 6){
            if(flag == FALGUPSLIP){
                a4 = 26;
            }else {
                a4 = 27;
            }
        }else if(t == 7){
            if(flag == FALGUPSLIP){
                a4 = 30;
            }else {
                a4 = 31;
            }
        }else if(t == 8){
            if(flag == FALGUPSLIP){
                a4 = 34;
            }else {
                a4 = 35;
            }
        }else if(t == 9){
            if(flag == FALGUPSLIP){
                a4 = 38;
            }else {
                a4 = 39;
            }
        }else if(t == 10){
            if(flag == FALGUPSLIP){
                a4 = 42;
            }else {
                a4 = 43;
            }
        }else if(t == 11){
            if(flag == FALGUPSLIP){
                a4 = 46;
            }else {
                a4 = 47;
            }
        }else if(t == 12){
            if(flag == FALGUPSLIP){
                a4 = 50;
            }else {
                a4 = 51;
            }
        }else if(t == 13){
            if(flag == FALGUPSLIP){
                a4 = 54;
            }else {
                a4 = 55;
            }
        }else if(t == 14){
            if(flag == FALGUPSLIP){
                a4 = 58;
            }else {
                a4 = 59;
            }
        }else if(t == 15){
            if(flag == FALGUPSLIP){
                a4 = 62;
            }else {
                a4 = 63;
            }
        }else if(t == 16){
            if(flag == FALGUPSLIP){
                a4 = 66;
            }else {
                a4 = 67;
            }
        }else if(t == 17){
            if(flag == FALGUPSLIP){
                a4 = 70;
            }else {
                a4 = 71;
            }
        }else if(t == 18){
            if(flag == FALGUPSLIP){
                a4 = 74;
            }else {
                a4 = 75;
            }
        }else if(t == 19){
            if(flag == FALGUPSLIP){
                a4 = 78;
            }else {
                a4 = 79;
            }
        }else if(t == 20){
            if(flag == FALGUPSLIP){
                a4 = 82;
            }else {
                a4 = 83;
            }
        }else if(t == 21){
            if(flag == FALGUPSLIP){
                a4 = 86;
            }else {
                a4 = 87;
            }
        }else if(t == 22){
            if(flag == FALGUPSLIP){
                a4 = 90;
            }else {
                a4 = 91;
            }
        }else if(t == 23){
            if(flag == FALGUPSLIP){
                a4 = 94;
            }else {
                a4 = 95;
            }
        }else if(t == 24){
            if(flag == FALGUPSLIP){
                a4 = 98;
            }else {
                a4 = 99;
            }
        }else if(t == 25){
            if(flag == FALGUPSLIP){
                a4 = 102;
            }else {
                a4 = 103;
            }
        }else if(t == 26){
            if(flag == FALGUPSLIP){
                a4 = 106;
            }else {
                a4 = 107;
            }
        }else if(t == 27){
            if(flag == FALGUPSLIP){
                a4 = 110;
            }else {
                a4 = 111;
            }
        }else if(t == 28){
            if(flag == FALGUPSLIP){
                a4 = 114;
            }else {
                a4 = 115;
            }
        }else if(t == 29){
            if(flag == FALGUPSLIP){
                a4 = 118;
            }else {
                a4 = 119;
            }
        }else if(t == 30){
            if(flag == FALGUPSLIP){
                a4 = 122;
            }else {
                a4 = 123;
            }
        }else if(t == 31){
            if(flag == FALGUPSLIP){
                a4 = 126;
            }else {
                a4 = 127;
            }
        }


        //a5 = Integer.parseInt(edtSetSlipValue.getText().toString());
                int[] sendValChkSum={a1, a2, a3, a4, a5, a6};
                String strChkSum= CalculateCheckSum.calculateChkSum(sendValChkSum);
                String asciiString  = String.format("%04x", a1).substring(2,4)+String.format("%04x", a2).substring(2,4)+String.format("%04x", a3).substring(2,4)+String.format("%04x", a4).substring(2,4)+String.format("%04x", a5).substring(2,4)+String.format("%04x", a6).substring(2,4);
                asciiString = asciiString + strChkSum + "\r";
                chkLocAdd = String.format("%04x", a4).substring(2,4);
             //   Log.e(TAG, "asciiString = "+ asciiString);
             //   Log.e(TAG, "chkLocAdd = "+ chkLocAdd);

                if (isConnected()) {
                    //connector.write(asciiString.getBytes());
                    sendMessage(asciiString.getBytes());
                    //delay();
                    cntSetText = 1;
                    spinFlr = t;
                    flagUpDn = flag;
                    //setTextViewValue(t,flag);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();

                }

    }


    public void setTextViewValue(int flrNo,int flag){
        if(flag==FALGUPSLIP){
            if(!str11LvlUpSlip[flrNo].equals("")){
                int decimal = Integer.parseInt(str11LvlUpSlip[flrNo], 16);
                txtViewValue.setText(String.format("%s",decimal));
                cntSetText = 0;
            }
        }
        else {
            if(!str11LvlDnSlip[flrNo].equals("")){
                int decimal = Integer.parseInt(str11LvlDnSlip[flrNo], 16);
                txtViewValue.setText(String.format("%s",decimal));
                cntSetText = 0;
            }
        }
    }

    private void callViewAllLevelFunction(int fnNum) {

       // completReceivedString.setLength(0);
        int a1 = 18;
        int a2 = 17;
        int a3 = 80;
        int a4;
        int a5 = 00;
        int a6 = 00;
            a4 = fnNum;
            int[] sendValChkSum={a1, a2, a3, a4, a5, a6};
            String strChkSum= CalculateCheckSum.calculateChkSum(sendValChkSum);
            String asciiString  = String.format("%04x", a1).substring(2,4)+String.format("%04x", a2).substring(2,4)+String.format("%04x", a3).substring(2,4)+String.format("%04x", a4).substring(2,4)+String.format("%04x", a5).substring(2,4)+String.format("%04x", a6).substring(2,4);
            asciiString = asciiString + strChkSum + "\r";
            //chkViewLocAdd = String.format("%04x", a4).substring(2,4);
          //  Log.e(TAG, "asciiString = "+ asciiString);
         //   Log.e(TAG, "chkViewLocAdd = "+ chkViewLocAdd);

            if (isConnected()) {
                //connector.write(asciiString.getBytes());
                sendMessage(asciiString.getBytes());
            }
            else {
                Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
                pd.dismiss();
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
          /*  case R.id.menu_search:
                final int REQUEST_CONNECT_DEVICE = 2;
                Intent serverIntent = null;
                serverIntent = new Intent(LevelFunctionActivity.this, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
                return true;*/
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
                setTextViewValue(spinFlr, flagUpDn);
            }
            myHandlerChk.postDelayed(this, 100);
        }

    };
    public void showReceivedDataNew() {

        if(!str11LvlPos1[0].equals("")&&!str11LvlPos2[0].equals("")){
            int posData = Integer.parseInt(str11LvlPos1[0]+str11LvlPos2[0], 16);
            textViewDataPos[0].setText(String.format("%s",posData));
        }
        if(!str11LvlUpSlip[0].equals("")){
            int upSlipValue = Integer.parseInt(str11LvlUpSlip[0], 16);
            textViewDataUp[0].setText(String.format("%s",upSlipValue));
        }
        if(!str11LvlDnSlip[0].equals("")){
            int dnSlipValue = Integer.parseInt(str11LvlDnSlip[0], 16);
            textViewDataDn[0].setText(String.format("%s",dnSlipValue));
        }


        if(!str11LvlPos1[1].equals("")&&!str11LvlPos2[1].equals("")){
            int posData = Integer.parseInt(str11LvlPos1[1]+str11LvlPos2[1], 16);
            textViewDataPos[1].setText(String.format("%s",posData));
        }
        if(!str11LvlUpSlip[1].equals("")){
            int upSlipValue = Integer.parseInt(str11LvlUpSlip[1], 16);
            textViewDataUp[1].setText(String.format("%s",upSlipValue));
        }
        if(!str11LvlDnSlip[1].equals("")){
            int dnSlipValue = Integer.parseInt(str11LvlDnSlip[1], 16);
            textViewDataDn[1].setText(String.format("%s",dnSlipValue));
        }

        if(!str11LvlPos1[2].equals("")&&!str11LvlPos2[2].equals("")){
            int posData = Integer.parseInt(str11LvlPos1[2]+str11LvlPos2[2], 16);
            textViewDataPos[2].setText(String.format("%s",posData));
        }
        if(!str11LvlUpSlip[2].equals("")){
            int upSlipValue = Integer.parseInt(str11LvlUpSlip[2], 16);
            textViewDataUp[2].setText(String.format("%s",upSlipValue));
        }
        if(!str11LvlDnSlip[2].equals("")){
            int dnSlipValue = Integer.parseInt(str11LvlDnSlip[2], 16);
            textViewDataDn[2].setText(String.format("%s",dnSlipValue));
        }

        if(!str11LvlPos1[3].equals("")&&!str11LvlPos2[3].equals("")){
            int posData = Integer.parseInt(str11LvlPos1[3]+str11LvlPos2[3], 16);
            textViewDataPos[3].setText(String.format("%s",posData));
        }
        if(!str11LvlUpSlip[3].equals("")){
            int upSlipValue = Integer.parseInt(str11LvlUpSlip[3], 16);
            textViewDataUp[3].setText(String.format("%s",upSlipValue));
        }
        if(!str11LvlDnSlip[3].equals("")){
            int dnSlipValue = Integer.parseInt(str11LvlDnSlip[3], 16);
            textViewDataDn[3].setText(String.format("%s",dnSlipValue));
        }

        if(!str11LvlPos1[4].equals("")&&!str11LvlPos2[4].equals("")){
            int posData = Integer.parseInt(str11LvlPos1[4]+str11LvlPos2[4], 16);
            textViewDataPos[4].setText(String.format("%s",posData));
        }
        if(!str11LvlUpSlip[4].equals("")){
            int upSlipValue = Integer.parseInt(str11LvlUpSlip[4], 16);
            textViewDataUp[4].setText(String.format("%s",upSlipValue));
        }
        if(!str11LvlDnSlip[4].equals("")){
            int dnSlipValue = Integer.parseInt(str11LvlDnSlip[4], 16);
            textViewDataDn[4].setText(String.format("%s",dnSlipValue));
        }

        if(!str11LvlPos1[5].equals("")&&!str11LvlPos2[5].equals("")){
            int posData = Integer.parseInt(str11LvlPos1[5]+str11LvlPos2[5], 16);
            textViewDataPos[5].setText(String.format("%s",posData));
        }
        if(!str11LvlUpSlip[5].equals("")){
            int upSlipValue = Integer.parseInt(str11LvlUpSlip[5], 16);
            textViewDataUp[5].setText(String.format("%s",upSlipValue));
        }
        if(!str11LvlDnSlip[5].equals("")){
            int dnSlipValue = Integer.parseInt(str11LvlDnSlip[5], 16);
            textViewDataDn[5].setText(String.format("%s",dnSlipValue));
        }

        if(!str11LvlPos1[6].equals("")&&!str11LvlPos2[6].equals("")){
            int posData = Integer.parseInt(str11LvlPos1[6]+str11LvlPos2[6], 16);
            textViewDataPos[6].setText(String.format("%s",posData));
        }
        if(!str11LvlUpSlip[6].equals("")){
            int upSlipValue = Integer.parseInt(str11LvlUpSlip[6], 16);
            textViewDataUp[6].setText(String.format("%s",upSlipValue));
        }
        if(!str11LvlDnSlip[6].equals("")){
            int dnSlipValue = Integer.parseInt(str11LvlDnSlip[6], 16);
            textViewDataDn[6].setText(String.format("%s",dnSlipValue));
        }

        if(!str11LvlPos1[7].equals("")&&!str11LvlPos2[7].equals("")){
            int posData = Integer.parseInt(str11LvlPos1[7]+str11LvlPos2[7], 16);
            textViewDataPos[7].setText(String.format("%s",posData));
        }
        if(!str11LvlUpSlip[7].equals("")){
            int upSlipValue = Integer.parseInt(str11LvlUpSlip[7], 16);
            textViewDataUp[7].setText(String.format("%s",upSlipValue));
        }
        if(!str11LvlDnSlip[7].equals("")){
            int dnSlipValue = Integer.parseInt(str11LvlDnSlip[7], 16);
            textViewDataDn[7].setText(String.format("%s",dnSlipValue));
        }

        if(!str11LvlPos1[8].equals("")&&!str11LvlPos2[8].equals("")){
            int posData = Integer.parseInt(str11LvlPos1[8]+str11LvlPos2[8], 16);
            textViewDataPos[8].setText(String.format("%s",posData));
        }
        if(!str11LvlUpSlip[8].equals("")){
            int upSlipValue = Integer.parseInt(str11LvlUpSlip[8], 16);
            textViewDataUp[8].setText(String.format("%s",upSlipValue));
        }
        if(!str11LvlDnSlip[8].equals("")){
            int dnSlipValue = Integer.parseInt(str11LvlDnSlip[8], 16);
            textViewDataDn[8].setText(String.format("%s",dnSlipValue));
        }

        if(!str11LvlPos1[9].equals("")&&!str11LvlPos2[9].equals("")){
            int posData = Integer.parseInt(str11LvlPos1[9]+str11LvlPos2[9], 16);
            textViewDataPos[9].setText(String.format("%s",posData));
        }
        if(!str11LvlUpSlip[9].equals("")){
            int upSlipValue = Integer.parseInt(str11LvlUpSlip[9], 16);
            textViewDataUp[9].setText(String.format("%s",upSlipValue));
        }
        if(!str11LvlDnSlip[9].equals("")){
            int dnSlipValue = Integer.parseInt(str11LvlDnSlip[9], 16);
            textViewDataDn[9].setText(String.format("%s",dnSlipValue));
        }

        if(!str11LvlPos1[10].equals("")&&!str11LvlPos2[10].equals("")){
            int posData = Integer.parseInt(str11LvlPos1[10]+str11LvlPos2[10], 16);
            textViewDataPos[10].setText(String.format("%s",posData));
        }
        if(!str11LvlUpSlip[10].equals("")){
            int upSlipValue = Integer.parseInt(str11LvlUpSlip[10], 16);
            textViewDataUp[10].setText(String.format("%s",upSlipValue));
        }
        if(!str11LvlDnSlip[10].equals("")){
            int dnSlipValue = Integer.parseInt(str11LvlDnSlip[10], 16);
            textViewDataDn[10].setText(String.format("%s",dnSlipValue));
        }

        if(!str11LvlPos1[11].equals("")&&!str11LvlPos2[11].equals("")){
            int posData = Integer.parseInt(str11LvlPos1[11]+str11LvlPos2[11], 16);
            textViewDataPos[11].setText(String.format("%s",posData));
        }
        if(!str11LvlUpSlip[11].equals("")){
            int upSlipValue = Integer.parseInt(str11LvlUpSlip[11], 16);
            textViewDataUp[11].setText(String.format("%s",upSlipValue));
        }
        if(!str11LvlDnSlip[11].equals("")){
            int dnSlipValue = Integer.parseInt(str11LvlDnSlip[11], 16);
            textViewDataDn[11].setText(String.format("%s",dnSlipValue));
        }

        if(!str11LvlPos1[12].equals("")&&!str11LvlPos2[12].equals("")){
            int posData = Integer.parseInt(str11LvlPos1[12]+str11LvlPos2[12], 16);
            textViewDataPos[12].setText(String.format("%s",posData));
        }
        if(!str11LvlUpSlip[12].equals("")){
            int upSlipValue = Integer.parseInt(str11LvlUpSlip[12], 16);
            textViewDataUp[12].setText(String.format("%s",upSlipValue));
        }
        if(!str11LvlDnSlip[12].equals("")){
            int dnSlipValue = Integer.parseInt(str11LvlDnSlip[12], 16);
            textViewDataDn[12].setText(String.format("%s",dnSlipValue));
        }

        if(!str11LvlPos1[13].equals("")&&!str11LvlPos2[13].equals("")){
            int posData = Integer.parseInt(str11LvlPos1[13]+str11LvlPos2[13], 16);
            textViewDataPos[13].setText(String.format("%s",posData));
        }
        if(!str11LvlUpSlip[13].equals("")){
            int upSlipValue = Integer.parseInt(str11LvlUpSlip[13], 16);
            textViewDataUp[13].setText(String.format("%s",upSlipValue));
        }
        if(!str11LvlDnSlip[13].equals("")){
            int dnSlipValue = Integer.parseInt(str11LvlDnSlip[13], 16);
            textViewDataDn[13].setText(String.format("%s",dnSlipValue));
        }

        if(!str11LvlPos1[14].equals("")&&!str11LvlPos2[14].equals("")){
            int posData = Integer.parseInt(str11LvlPos1[14]+str11LvlPos2[14], 16);
            textViewDataPos[14].setText(String.format("%s",posData));
        }
        if(!str11LvlUpSlip[14].equals("")){
            int upSlipValue = Integer.parseInt(str11LvlUpSlip[14], 16);
            textViewDataUp[14].setText(String.format("%s",upSlipValue));
        }
        if(!str11LvlDnSlip[14].equals("")){
            int dnSlipValue = Integer.parseInt(str11LvlDnSlip[14], 16);
            textViewDataDn[14].setText(String.format("%s",dnSlipValue));
        }

        if(!str11LvlPos1[15].equals("")&&!str11LvlPos2[15].equals("")){
            int posData = Integer.parseInt(str11LvlPos1[15]+str11LvlPos2[15], 16);
            textViewDataPos[15].setText(String.format("%s",posData));
        }
        if(!str11LvlUpSlip[15].equals("")){
            int upSlipValue = Integer.parseInt(str11LvlUpSlip[15], 16);
            textViewDataUp[15].setText(String.format("%s",upSlipValue));
        }
        if(!str11LvlDnSlip[15].equals("")){
            int dnSlipValue = Integer.parseInt(str11LvlDnSlip[15], 16);
            textViewDataDn[15].setText(String.format("%s",dnSlipValue));
        }

        if(!str11LvlPos1[16].equals("")&&!str11LvlPos2[16].equals("")){
            int posData = Integer.parseInt(str11LvlPos1[16]+str11LvlPos2[16], 16);
            textViewDataPos[16].setText(String.format("%s",posData));
        }
        if(!str11LvlUpSlip[16].equals("")){
            int upSlipValue = Integer.parseInt(str11LvlUpSlip[16], 16);
            textViewDataUp[16].setText(String.format("%s",upSlipValue));
        }
        if(!str11LvlDnSlip[16].equals("")){
            int dnSlipValue = Integer.parseInt(str11LvlDnSlip[16], 16);
            textViewDataDn[16].setText(String.format("%s",dnSlipValue));
        }

        if(!str11LvlPos1[17].equals("")&&!str11LvlPos2[17].equals("")){
            int posData = Integer.parseInt(str11LvlPos1[17]+str11LvlPos2[17], 16);
            textViewDataPos[17].setText(String.format("%s",posData));
        }
        if(!str11LvlUpSlip[17].equals("")){
            int upSlipValue = Integer.parseInt(str11LvlUpSlip[17], 16);
            textViewDataUp[17].setText(String.format("%s",upSlipValue));
        }
        if(!str11LvlDnSlip[17].equals("")){
            int dnSlipValue = Integer.parseInt(str11LvlDnSlip[17], 16);
            textViewDataDn[17].setText(String.format("%s",dnSlipValue));
        }

        if(!str11LvlPos1[18].equals("")&&!str11LvlPos2[18].equals("")){
            int posData = Integer.parseInt(str11LvlPos1[18]+str11LvlPos2[18], 16);
            textViewDataPos[18].setText(String.format("%s",posData));
        }
        if(!str11LvlUpSlip[18].equals("")){
            int upSlipValue = Integer.parseInt(str11LvlUpSlip[18], 16);
            textViewDataUp[18].setText(String.format("%s",upSlipValue));
        }
        if(!str11LvlDnSlip[18].equals("")){
            int dnSlipValue = Integer.parseInt(str11LvlDnSlip[18], 16);
            textViewDataDn[18].setText(String.format("%s",dnSlipValue));
        }

        if(!str11LvlPos1[19].equals("")&&!str11LvlPos2[19].equals("")){
            int posData = Integer.parseInt(str11LvlPos1[19]+str11LvlPos2[19], 16);
            textViewDataPos[19].setText(String.format("%s",posData));
        }
        if(!str11LvlUpSlip[19].equals("")){
            int upSlipValue = Integer.parseInt(str11LvlUpSlip[19], 16);
            textViewDataUp[19].setText(String.format("%s",upSlipValue));
        }
        if(!str11LvlDnSlip[19].equals("")){
            int dnSlipValue = Integer.parseInt(str11LvlDnSlip[19], 16);
            textViewDataDn[19].setText(String.format("%s",dnSlipValue));
        }

        if(!str11LvlPos1[20].equals("")&&!str11LvlPos2[20].equals("")){
            int posData = Integer.parseInt(str11LvlPos1[20]+str11LvlPos2[20], 16);
            textViewDataPos[20].setText(String.format("%s",posData));
        }
        if(!str11LvlUpSlip[20].equals("")){
            int upSlipValue = Integer.parseInt(str11LvlUpSlip[20], 16);
            textViewDataUp[20].setText(String.format("%s",upSlipValue));
        }
        if(!str11LvlDnSlip[20].equals("")){
            int dnSlipValue = Integer.parseInt(str11LvlDnSlip[20], 16);
            textViewDataDn[20].setText(String.format("%s",dnSlipValue));
        }

        if(!str11LvlPos1[21].equals("")&&!str11LvlPos2[21].equals("")){
            int posData = Integer.parseInt(str11LvlPos1[21]+str11LvlPos2[21], 16);
            textViewDataPos[21].setText(String.format("%s",posData));
        }
        if(!str11LvlUpSlip[21].equals("")){
            int upSlipValue = Integer.parseInt(str11LvlUpSlip[21], 16);
            textViewDataUp[21].setText(String.format("%s",upSlipValue));
        }
        if(!str11LvlDnSlip[21].equals("")){
            int dnSlipValue = Integer.parseInt(str11LvlDnSlip[21], 16);
            textViewDataDn[21].setText(String.format("%s",dnSlipValue));
        }

        if(!str11LvlPos1[22].equals("")&&!str11LvlPos2[22].equals("")){
            int posData = Integer.parseInt(str11LvlPos1[22]+str11LvlPos2[22], 16);
            textViewDataPos[22].setText(String.format("%s",posData));
        }
        if(!str11LvlUpSlip[22].equals("")){
            int upSlipValue = Integer.parseInt(str11LvlUpSlip[22], 16);
            textViewDataUp[22].setText(String.format("%s",upSlipValue));
        }
        if(!str11LvlDnSlip[22].equals("")){
            int dnSlipValue = Integer.parseInt(str11LvlDnSlip[22], 16);
            textViewDataDn[22].setText(String.format("%s",dnSlipValue));
        }

        if(!str11LvlPos1[23].equals("")&&!str11LvlPos2[23].equals("")){
            int posData = Integer.parseInt(str11LvlPos1[23]+str11LvlPos2[23], 16);
            textViewDataPos[23].setText(String.format("%s",posData));
        }
        if(!str11LvlUpSlip[23].equals("")){
            int upSlipValue = Integer.parseInt(str11LvlUpSlip[23], 16);
            textViewDataUp[23].setText(String.format("%s",upSlipValue));
        }
        if(!str11LvlDnSlip[23].equals("")){
            int dnSlipValue = Integer.parseInt(str11LvlDnSlip[23], 16);
            textViewDataDn[23].setText(String.format("%s",dnSlipValue));
        }

        if(!str11LvlPos1[24].equals("")&&!str11LvlPos2[24].equals("")){
            int posData = Integer.parseInt(str11LvlPos1[24]+str11LvlPos2[24], 16);
            textViewDataPos[24].setText(String.format("%s",posData));
        }
        if(!str11LvlUpSlip[24].equals("")){
            int upSlipValue = Integer.parseInt(str11LvlUpSlip[24], 16);
            textViewDataUp[24].setText(String.format("%s",upSlipValue));
        }
        if(!str11LvlDnSlip[24].equals("")){
            int dnSlipValue = Integer.parseInt(str11LvlDnSlip[24], 16);
            textViewDataDn[24].setText(String.format("%s",dnSlipValue));
        }

        if(!str11LvlPos1[25].equals("")&&!str11LvlPos2[25].equals("")){
            int posData = Integer.parseInt(str11LvlPos1[25]+str11LvlPos2[25], 16);
            textViewDataPos[25].setText(String.format("%s",posData));
        }
        if(!str11LvlUpSlip[25].equals("")){
            int upSlipValue = Integer.parseInt(str11LvlUpSlip[25], 16);
            textViewDataUp[25].setText(String.format("%s",upSlipValue));
        }
        if(!str11LvlDnSlip[25].equals("")){
            int dnSlipValue = Integer.parseInt(str11LvlDnSlip[25], 16);
            textViewDataDn[25].setText(String.format("%s",dnSlipValue));
        }

        if(!str11LvlPos1[26].equals("")&&!str11LvlPos2[26].equals("")){
            int posData = Integer.parseInt(str11LvlPos1[26]+str11LvlPos2[26], 16);
            textViewDataPos[26].setText(String.format("%s",posData));
        }
        if(!str11LvlUpSlip[26].equals("")){
            int upSlipValue = Integer.parseInt(str11LvlUpSlip[26], 16);
            textViewDataUp[26].setText(String.format("%s",upSlipValue));
        }
        if(!str11LvlDnSlip[26].equals("")){
            int dnSlipValue = Integer.parseInt(str11LvlDnSlip[26], 16);
            textViewDataDn[26].setText(String.format("%s",dnSlipValue));
        }

        if(!str11LvlPos1[27].equals("")&&!str11LvlPos2[27].equals("")){
            int posData = Integer.parseInt(str11LvlPos1[27]+str11LvlPos2[27], 16);
            textViewDataPos[27].setText(String.format("%s",posData));
        }
        if(!str11LvlUpSlip[27].equals("")){
            int upSlipValue = Integer.parseInt(str11LvlUpSlip[27], 16);
            textViewDataUp[27].setText(String.format("%s",upSlipValue));
        }
        if(!str11LvlDnSlip[27].equals("")){
            int dnSlipValue = Integer.parseInt(str11LvlDnSlip[27], 16);
            textViewDataDn[27].setText(String.format("%s",dnSlipValue));
        }

        if(!str11LvlPos1[28].equals("")&&!str11LvlPos2[28].equals("")){
            int posData = Integer.parseInt(str11LvlPos1[28]+str11LvlPos2[28], 16);
            textViewDataPos[28].setText(String.format("%s",posData));
        }
        if(!str11LvlUpSlip[28].equals("")){
            int upSlipValue = Integer.parseInt(str11LvlUpSlip[28], 16);
            textViewDataUp[28].setText(String.format("%s",upSlipValue));
        }
        if(!str11LvlDnSlip[28].equals("")){
            int dnSlipValue = Integer.parseInt(str11LvlDnSlip[28], 16);
            textViewDataDn[28].setText(String.format("%s",dnSlipValue));
        }

        if(!str11LvlPos1[29].equals("")&&!str11LvlPos2[29].equals("")){
            int posData = Integer.parseInt(str11LvlPos1[29]+str11LvlPos2[29], 16);
            textViewDataPos[29].setText(String.format("%s",posData));
        }
        if(!str11LvlUpSlip[29].equals("")){
            int upSlipValue = Integer.parseInt(str11LvlUpSlip[29], 16);
            textViewDataUp[29].setText(String.format("%s",upSlipValue));
        }
        if(!str11LvlDnSlip[29].equals("")){
            int dnSlipValue = Integer.parseInt(str11LvlDnSlip[29], 16);
            textViewDataDn[29].setText(String.format("%s",dnSlipValue));
        }

        if(!str11LvlPos1[30].equals("")&&!str11LvlPos2[30].equals("")){
            int posData = Integer.parseInt(str11LvlPos1[30]+str11LvlPos2[30], 16);
            textViewDataPos[30].setText(String.format("%s",posData));
        }
        if(!str11LvlUpSlip[30].equals("")){
            int upSlipValue = Integer.parseInt(str11LvlUpSlip[30], 16);
            textViewDataUp[30].setText(String.format("%s",upSlipValue));
        }
        if(!str11LvlDnSlip[30].equals("")){
            int dnSlipValue = Integer.parseInt(str11LvlDnSlip[30], 16);
            textViewDataDn[30].setText(String.format("%s",dnSlipValue));
        }

        if(!str11LvlPos1[31].equals("")&&!str11LvlPos2[31].equals("")){
            int posData = Integer.parseInt(str11LvlPos1[31]+str11LvlPos2[31], 16);
            textViewDataPos[31].setText(String.format("%s",posData));
        }
        if(!str11LvlUpSlip[31].equals("")){
            int upSlipValue = Integer.parseInt(str11LvlUpSlip[31], 16);
            textViewDataUp[31].setText(String.format("%s",upSlipValue));
        }
        if(!str11LvlDnSlip[31].equals("")){
            int dnSlipValue = Integer.parseInt(str11LvlDnSlip[31], 16);
            textViewDataDn[31].setText(String.format("%s",dnSlipValue));
        }

    }

    // =========================================================================
    /*public void appendLog1(String message, boolean hexMode, boolean outgoing, boolean clean) {

        StringBuffer msg = new StringBuffer();

        completReceivedString.append(message);
        String receivedString = completReceivedString.toString();
        Log.e(TAG, "receivedString = "+ receivedString);
        if(receivedString.contains("111250")){
            try {

                int index=receivedString.lastIndexOf("111250");
                //Log.e(TAG, "Loacation data = "+ receivedString.substring(index+10,index+12));
                if(receivedString.substring(index+6,index+8).equals(chkLocAdd) && receivedString.substring(index+10,index+12).equals("52")){
                    String strDecData = receivedString.substring(index+8,index+10);
                    int decimal = Integer.parseInt(strDecData, 16);
                    txtViewValue.setText(String.format("%s",decimal));
                    Log.e(TAG, "decimal = "+ decimal);
                }
                if(receivedString.substring(index+6,index+8).equals(chkViewLocAdd) && receivedString.substring(index+10,index+12).equals("52")){
                    String strDecData = receivedString.substring(index+8,index+10);
                    if(cntViewCmd<3){
                        posDataString.append(strDecData);
                        cntViewCmdAll=cntViewCmdAll+1;
                        cntViewCmd=cntViewCmd+1;
                        callViewAllLevelFunction();
                    }
                    else if(cntViewCmd==3){
                        upSlipValue = Integer.parseInt(strDecData, 16);
                        Log.e(TAG, "upSlipValue = "+ upSlipValue);
                        cntViewCmdAll=cntViewCmdAll+1;
                        cntViewCmd=cntViewCmd+1;
                        callViewAllLevelFunction();
                    }
                    else if(cntViewCmd==4){
                        dnSlipValue = Integer.parseInt(strDecData, 16);
                        Log.e(TAG, "dnSlipValue = "+ dnSlipValue);
                        int posData = Integer.parseInt(posDataString.toString(), 16);
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        final View view = inflater.inflate(R.layout.level_function_item, null);
                        TextView tvFloorNo = (TextView) view.findViewById(R.id.tvLevelFunctionFlrNum);
                        TextView tvFloorPos = (TextView) view.findViewById(R.id.tvLevelFunctionFlrPos);
                        TextView tvFloorUpSlip = (TextView) view.findViewById(R.id.tvLevelFunctionUpSlip);
                        TextView tvFloorDnSlip = (TextView) view.findViewById(R.id.tvLevelFunctionDnSlip);
                        tvFloorNo.setText(String.format("%s",flrValue));
                        tvFloorPos.setText(String.format("%s",posData));
                        tvFloorUpSlip.setText(String.format("%s",upSlipValue));
                        tvFloorDnSlip.setText(String.format("%s",dnSlipValue));
                        llViewLevelFunction.addView(view);
                        cntViewCmdAll=cntViewCmdAll+1;
                        cntViewCmd=1;
                        flrValue=flrValue+1;
                        posDataString.setLength(0);
                        callViewAllLevelFunction();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }*/

}
