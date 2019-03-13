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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import static com.radioknit.mmidec.MainActivity.isConnected;
import static com.radioknit.mmidec.MainActivity.str71IOValues1;
import static com.radioknit.mmidec.MainActivity.str77IOValues2;


public class IOIndicatorActivity extends AppCompatActivity {

    private static final String TAG = "IOIndicatorActivity";
    private Context mContext;
    private BluetoothAdapter bluetoothAdapter;
    private Button btnInputSignals;
    private Button btnoutputSignals;
    private LinearLayout llInputSignals;
    private LinearLayout llOutputSignals;
    private static CheckBox chk_OP_RunUp;
    private static CheckBox chk_OP_RunDn;
    private static CheckBox chk_OP_Speed_1_OP;
    private static CheckBox chk_OP_Speed_2_OP;
    private static CheckBox chk_OP_ARD_Relay;
    private static CheckBox chk_OP_CT_Relay;
    private static CheckBox chk_OP_Speed_3_OP;
    private static CheckBox chk_OP_MainContact;
    private static CheckBox chk_OP_DC_OP;
    private static CheckBox chk_OP_DO_OP;
    private static CheckBox chk_OP_Blank_1;
    private static CheckBox chk_OP_Break_Sig_1;
    private static CheckBox chk_OP_Break_Sig_2;
    private static CheckBox chk_OP_Blank_2;
    private static CheckBox chk_OP_Blank_3;
    private static CheckBox chk_OP_Blank_4;
    private static CheckBox chk_io_MC_Room_ins;
    private static CheckBox chk_io_ARDIp;
    private static CheckBox chk_io_SldnSwUp2;
    private static CheckBox chk_io_SldnSwDn2;
    private static CheckBox chk_io_MotorTherm;
    private static CheckBox chk_io_FiremanSw;
    private static CheckBox chk_io_CtIp;
    private static CheckBox chk_io_BreakSwitch;
    private static CheckBox chk_io_Encoder_ch_A;
    private static CheckBox chk_io_Encoder_ch_BB;
    private static CheckBox chk_io_UpStopSw;
    private static CheckBox chk_io_RstDnStop;
    private static CheckBox chk_io_SlowSwDn1;
    private static CheckBox chk_io_SlowSwUp1;
    private static CheckBox chk_io_DoorZoneSw;
    private static CheckBox chk_io_BrkIn;
    private static CheckBox chk_io_AM;
    private static CheckBox chk_io_MntUp;
    private static CheckBox chk_io_MntDn;
    private static CheckBox chk_io_SftEdge;
    private static CheckBox chk_io_IR;
    private static CheckBox chk_io_RunStp;
    private static CheckBox chk_io_Far;
    private static CheckBox chk_io_RRD;
    private static CheckBox chk_io_Blank_2;
    private static CheckBox chk_io_Blank_3;
    private static CheckBox chk_io_Blank_4;
    private static CheckBox chk_io_Blank_5;
    private static CheckBox chk_io_Blank_6;
    private static CheckBox chk_io_Blank_7;
    private static CheckBox chk_io_Blank_8;
    private static CheckBox chk_io_Blank_9;
    private Menu menu;
    // =============

    private static final String DEVICE_NAME = "DEVICE_NAME";
    private static final String LOG = "LOG";
    final Handler myHandlerChk = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ioindicator);

        generateId();
        createObj();
        registerEvent();
        myHandlerChk.postDelayed(checkDataContinue, 0);
    }

    private void generateId() {

        btnInputSignals = (Button) findViewById(R.id.btnInputSignals);
        btnoutputSignals = (Button)findViewById(R.id.btnOutputSignals);
        llInputSignals = (LinearLayout) findViewById(R.id.llInout_Signals);
        llOutputSignals = (LinearLayout)findViewById(R.id.llOutputSignals);
        chk_OP_RunUp = (CheckBox)findViewById(R.id.rdo_OP_Run_up);
        chk_OP_RunDn = (CheckBox)findViewById(R.id.rdo_OP_Run_dn);
        chk_OP_Speed_1_OP = (CheckBox)findViewById(R.id.rdo_OP_Speed_1_op);
        chk_OP_Speed_2_OP = (CheckBox)findViewById(R.id.rdo_OP_Speed_2_op);
        chk_OP_ARD_Relay = (CheckBox)findViewById(R.id.rdo_OP_ARD_Relay);
        chk_OP_CT_Relay = (CheckBox)findViewById(R.id.rdo_OP_CT_Realy);
        chk_OP_Speed_3_OP = (CheckBox)findViewById(R.id.rdo_OP_Speed_3_op);
        chk_OP_MainContact = (CheckBox)findViewById(R.id.rdo_OP_Main_contact);
        chk_OP_DC_OP = (CheckBox)findViewById(R.id.rdo_OP_DC_op);
        chk_OP_DO_OP = (CheckBox)findViewById(R.id.rdo_OP_DO_op);
        chk_OP_Break_Sig_1 = (CheckBox)findViewById(R.id.rdo_OP_Break_Sig_1);
        chk_OP_Break_Sig_2 = (CheckBox)findViewById(R.id.rdo_OP_Break_Sig_2);
        chk_OP_Blank_1 = (CheckBox)findViewById(R.id.rdo_OP_Blank_1);
        chk_OP_Blank_2 = (CheckBox)findViewById(R.id.rdo_OP_Blank_2);
        chk_OP_Blank_3 = (CheckBox)findViewById(R.id.rdo_OP_Blank_3);
        chk_OP_Blank_4 = (CheckBox)findViewById(R.id.rdo_OP_Blank_4);

        chk_io_MC_Room_ins = (CheckBox) findViewById(R.id.chMCRoomIns);
        chk_io_ARDIp = (CheckBox) findViewById(R.id.chArdIP);
        chk_io_SldnSwUp2 = (CheckBox) findViewById(R.id.chSldnSwUp2);
        chk_io_SldnSwDn2 = (CheckBox) findViewById(R.id.chSldnSwDn2);
        chk_io_MotorTherm = (CheckBox) findViewById(R.id.chMotorTherm);
        chk_io_FiremanSw = (CheckBox) findViewById(R.id.chFiremanSw);
        chk_io_CtIp = (CheckBox)findViewById(R.id.chCtIP);
        chk_io_BreakSwitch = (CheckBox)findViewById(R.id.chBreakSwitch);
        chk_io_Encoder_ch_A = (CheckBox)findViewById(R.id.chEncoderChA);
        chk_io_Encoder_ch_BB = (CheckBox)findViewById(R.id.chEnccoderChB);
        chk_io_UpStopSw = (CheckBox)findViewById(R.id.chUpStopSw);
        chk_io_RstDnStop = (CheckBox)findViewById(R.id.chRstDnStop);
        chk_io_SlowSwDn1 = (CheckBox)findViewById(R.id.chSlowSwDN1);
        chk_io_SlowSwUp1 = (CheckBox)findViewById(R.id.chSlowSwUP1);
        chk_io_DoorZoneSw = (CheckBox)findViewById(R.id.chDoorZoneSw);
        chk_io_BrkIn = (CheckBox)findViewById(R.id.chBrkIn);
        chk_io_AM = (CheckBox)findViewById(R.id.chAm);
        chk_io_MntUp = (CheckBox)findViewById(R.id.chMntUp);
        chk_io_MntDn = (CheckBox)findViewById(R.id.chMntDn);
        chk_io_SftEdge = (CheckBox)findViewById(R.id.chSftEdge);
        chk_io_IR= (CheckBox)findViewById(R.id.chIR);
        chk_io_RunStp = (CheckBox)findViewById(R.id.chRunSft);
        chk_io_Far = (CheckBox)findViewById(R.id.chFAR);
        chk_io_RRD = (CheckBox)findViewById(R.id.chRRD);
        chk_io_Blank_2 = (CheckBox)findViewById(R.id.rdoBlankTwo);
        chk_io_Blank_3 = (CheckBox)findViewById(R.id.rdoBlank_three);
        chk_io_Blank_4 = (CheckBox)findViewById(R.id.rdoBlank_four);
        chk_io_Blank_5 = (CheckBox)findViewById(R.id.rdoBlank_five);
        chk_io_Blank_6 = (CheckBox)findViewById(R.id.rdoBlank_six);
        chk_io_Blank_7 = (CheckBox)findViewById(R.id.rdoBlank_seven);
        chk_io_Blank_8 = (CheckBox)findViewById(R.id.rdoBlankEight);
        chk_io_Blank_9 = (CheckBox)findViewById(R.id.rdoBlankNine);

    }

    private void registerEvent() {
        btnInputSignals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                llInputSignals.setVisibility(View.VISIBLE);
                llOutputSignals.setVisibility(View.GONE);
            }
        });

        btnoutputSignals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llInputSignals.setVisibility(View.GONE);
                llOutputSignals.setVisibility(View.VISIBLE);
            }
        });
    }

    private void createObj() {
        mContext = getApplicationContext();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        getSupportActionBar().setTitle("IO Indicator");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //startActivity(new Intent(mContext, MainActivity.class));
        myHandlerChk.removeCallbacks(checkDataContinue);
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
            if(!str71IOValues1.equals("")){
                setIOValues(str71IOValues1);
            }
            if(!str77IOValues2.equals("")){
                setIOValuesTwo(str77IOValues2);
            }
            myHandlerChk.postDelayed(this, 0);
        }

    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.device_control_activity, menu);
        this.menu = menu;
        return true;
    }
    // ============================================================================


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

           /* case R.id.menu_search:
                final int REQUEST_CONNECT_DEVICE = 2;
                Intent serverIntent = null;
                serverIntent = new Intent(IOIndicatorActivity.this, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
                return true;
*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // ==========================================================================

    private static void setIOValues(String tempstr) {

//        if(Utils.checkRecivedStringValid(temp)) {
        try {
            int in = tempstr.indexOf("71");
//            Utils.calculateChecksumValue(tempstr);
            //Log.e(TAG, " -----------------------------------");
            String hexTwo = String.format("%04x", Integer.parseInt(tempstr.substring(2,4),16));
            String hexThree = String.format("%04x", Integer.parseInt(tempstr.substring(4,6),16));
            String hexFour = String.format("%04x", Integer.parseInt(tempstr.substring(6,8),16));
            String hexFive = String.format("%04x", Integer.parseInt(tempstr.substring(8,10),16));
//            String hexSix = String.format("%04x", Integer.parseInt(tempstr.substring(12,14),16));
            String hexSix = String.format("%04x", Integer.parseInt(tempstr.substring(10,12),16));

//                Log.e(TAG, "hex Two = " + hexTwo + " bin = " + Utils.hexToBin(hexTwo));
//                Log.e(TAG, "hex Three = " + hexThree + " bin = " + Utils.hexToBin(hexThree));
//                Log.e(TAG, "hex Four = " + hexFour + " bin = " + Utils.hexToBin(hexFour));
//                Log.e(TAG, "hex Five = " + hexFive + " bin = " + Utils.hexToBin(hexFive));
//                Log.e(TAG, "hex Six = " + hexSix + " bin = " + Utils.hexToBin(hexSix));

            String two = Utils.hexToBin(hexTwo);
            String three = Utils.hexToBin(hexThree);
            String four = Utils.hexToBin(hexFour);
            String five = Utils.hexToBin(hexFive);
            String six = Utils.hexToBin(hexSix);

            if (five.charAt(7) == '0') {
                chk_OP_RunUp.setChecked(true);
            } else {
                chk_OP_RunUp.setChecked(false);
            }
            if (five.charAt(6) == '0') {
                chk_OP_RunDn.setChecked(true);
            } else {
                chk_OP_RunDn.setChecked(false);
            }
            if (five.charAt(5) == '0') {
                chk_OP_Speed_1_OP.setChecked(true);
            } else {
                chk_OP_Speed_1_OP.setChecked(false);
            }
            if (five.charAt(4) == '0') {
                chk_OP_Speed_2_OP.setChecked(true);
            } else {
                chk_OP_Speed_2_OP.setChecked(false);
            }
            if (five.charAt(3) == '0') {
                chk_OP_ARD_Relay.setChecked(true);
            } else {
                chk_OP_ARD_Relay.setChecked(false);
            }
            if (five.charAt(2) == '0') {
                chk_OP_CT_Relay.setChecked(true);
            } else {
                chk_OP_CT_Relay.setChecked(false);
            }
            if (five.charAt(1) == '0') {
                chk_OP_Speed_3_OP.setChecked(true);
            } else {
                chk_OP_Speed_3_OP.setChecked(false);
            }
            if (five.charAt(0) == '0') {
                chk_OP_MainContact.setChecked(true);
            } else {
                chk_OP_MainContact.setChecked(false);
            }

            if (six.charAt(7) == '0') {
                chk_OP_DC_OP.setChecked(true);
            } else {
                chk_OP_DC_OP.setChecked(false);
            }
            if (six.charAt(6) == '0') {
                chk_OP_DO_OP.setChecked(true);
            } else {
                chk_OP_DO_OP.setChecked(false);
            }
            if (six.charAt(5) == '0') {
                chk_OP_Blank_1.setChecked(true);
            } else {
                chk_OP_Blank_1.setChecked(false);
            }
            if (six.charAt(4) == '0') {
                chk_OP_Break_Sig_1.setChecked(true);
            } else {
                chk_OP_Break_Sig_1.setChecked(false);
            }
            if (six.charAt(3) == '0') {
                chk_OP_Break_Sig_2.setChecked(true);
            } else {
                chk_OP_Break_Sig_2.setChecked(false);
            }
            if (six.charAt(2) == '0') {
                chk_OP_Blank_2.setChecked(true);
            } else {
                chk_OP_Blank_2.setChecked(false);
            }
            if (six.charAt(1) == '0') {
                chk_OP_Blank_3.setChecked(true);
            } else {
                chk_OP_Blank_3.setChecked(false);
            }
            if (six.charAt(0) == '0') {
                chk_OP_Blank_4.setChecked(true);
            } else {
                chk_OP_Blank_4.setChecked(false);
            }

            // For Input Signals

            if (two.charAt(0) == '1') {
                chk_io_MC_Room_ins.setChecked(true);
            } else {
                chk_io_MC_Room_ins.setChecked(false);
            }
            if (two.charAt(1) == '1') {
                chk_io_ARDIp.setChecked(true);
            } else {
                chk_io_ARDIp.setChecked(false);
            }
            if (two.charAt(2) == '1') {
                chk_io_SldnSwUp2.setChecked(true);
            } else {
                chk_io_SldnSwUp2.setChecked(false);
            }
            if (two.charAt(3) == '1') {
                chk_io_SldnSwDn2.setChecked(true);
            } else {
                chk_io_SldnSwDn2.setChecked(false);
            }
            if (two.charAt(4) == '1') {
                chk_io_MotorTherm.setChecked(true);
            } else {
                chk_io_MotorTherm.setChecked(false);
            }
            if (two.charAt(5) == '1') {
                chk_io_FiremanSw.setChecked(true);
            } else {
                chk_io_FiremanSw.setChecked(false);
            }
            if (two.charAt(6) == '1') {
                chk_io_CtIp.setChecked(true);
            } else {
                chk_io_CtIp.setChecked(false);
            }
            if (two.charAt(7) == '1') {
                chk_io_BreakSwitch.setChecked(true);
            } else {
                chk_io_BreakSwitch.setChecked(false);
            }

            /*if (four.charAt(7) == '1') {
                chk_io_Encoder_ch_BB.setChecked(true);
            } else {
                chk_io_Encoder_ch_BB.setChecked(false);
            }
            if (four.charAt(6) == '1') {
                chk_io_Encoder_ch_A.setChecked(true);
            } else {
                chk_io_Encoder_ch_A.setChecked(false);
            }*/

            if (four.charAt(5) == '1') {
                chk_io_UpStopSw.setChecked(true);
            } else {
                chk_io_UpStopSw.setChecked(false);
            }
            if (four.charAt(4) == '1') {
                chk_io_RstDnStop.setChecked(true);
            } else {
                chk_io_RstDnStop.setChecked(false);
            }
            if (four.charAt(3) == '1') {
                chk_io_SlowSwDn1.setChecked(true);
            } else {
                chk_io_SlowSwDn1.setChecked(false);
            }
            if (four.charAt(2) == '1') {
                chk_io_SlowSwUp1.setChecked(true);
            } else {
                chk_io_SlowSwUp1.setChecked(false);
            }
            if (four.charAt(1) == '1') {
                chk_io_DoorZoneSw.setChecked(true);
            } else {
                chk_io_DoorZoneSw.setChecked(false);
            }
            if (four.charAt(0) == '1') {
                chk_io_BrkIn.setChecked(true);
            } else {
                chk_io_BrkIn.setChecked(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        }
    }

    private static void setIOValuesTwo(String tempStr) {

        try {
            String receivedString = tempStr;
          //  Log.e(TAG, " -----------------------------------");

//            if(Utils.checkRecivedStringValid(temp)) {

            String hexTwo = String.format("%04x", Integer.parseInt(receivedString.substring(2,4),16));
               // Log.e(TAG, "hex Two = " + hexTwo + " bin = " + Utils.hexToBin(hexTwo));
            String two = Utils.hexToBin(hexTwo);

            if (two.charAt(7) == '0') {
                chk_io_AM.setChecked(true);
            } else {
                chk_io_AM.setChecked(false);
            }
            if (two.charAt(6) == '0') {
                chk_io_MntUp.setChecked(true);
            } else {
                chk_io_MntUp.setChecked(false);
            }
            if (two.charAt(5) == '0') {
                chk_io_MntDn.setChecked(true);
            } else {
                chk_io_MntDn.setChecked(false);
            }
            if (two.charAt(4) == '0') {
                chk_io_SftEdge.setChecked(true);
            } else {
                chk_io_SftEdge.setChecked(false);
            }
            if (two.charAt(3) == '0') {
                chk_io_IR.setChecked(true);
            } else {
                chk_io_IR.setChecked(false);
            }
            if (two.charAt(2) == '0') {
                chk_io_RunStp.setChecked(true);
            } else {
                chk_io_RunStp.setChecked(false);
            }
            if (two.charAt(1) == '0') {
                chk_io_Far.setChecked(true);
            } else {
                chk_io_Far.setChecked(false);
            }
            if (two.charAt(0) == '0') {
                chk_io_RRD.setChecked(true);
            } else {
                chk_io_RRD.setChecked(false);
            }
//            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
