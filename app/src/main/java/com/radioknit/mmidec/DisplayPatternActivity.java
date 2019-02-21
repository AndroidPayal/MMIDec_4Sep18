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
import android.view.LayoutInflater;
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

import java.util.ArrayList;
import java.util.Arrays;

import static com.radioknit.mmidec.MainActivity.isConnected;
import static com.radioknit.mmidec.MainActivity.sendMessage;
import static com.radioknit.mmidec.MainActivity.str11DisFlr;
import static com.radioknit.mmidec.MainActivity.str11f1Id;


public class DisplayPatternActivity extends AppCompatActivity {

    private static final String TAG = "DisplayPatternActivity";
    ArrayList<String> arrCommandValueList;
    private Spinner spinSelectFloor;
    private Spinner spinDisplayPattern;
    private ArrayAdapter<String> adapter;
    private Button btnSetDisplayPattern;
    private Button btnViewDisplayPattern;
    private LinearLayout llViewDisplayPattern;
    private int counter = 0;
    private RelativeLayout rlViewDisplayPatternData;
    private LinearLayout llSetDispplayPatternData;
    private Button btnSetValues;
    private Button btnGetValues;
    private ArrayList<String> arrPattern;
    private Context mContext;
    TextView textViewData[]=new TextView[32];
    private ProgressDialog pd;
    private static boolean receiveFlag = false;
    final Handler myHandlerChk = new Handler();
    private Menu menu;
    private Button btnViewOneDisplayPattern;
    private TextView txtViewValue;
    private int cntSetText = 0;
    int spinFlr = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_display_pattern);
        generateId();
        createObj();
        registerEvent();
        myHandlerChk.postDelayed(checkDataContinue, 0);
    }

    private void generateId() {
        btnSetDisplayPattern = (Button) findViewById(R.id.btnSetDisplayPattern);
        spinSelectFloor = (Spinner) findViewById(R.id.spin_fragdisplay_pattern_floor_no);
        spinDisplayPattern = (Spinner) findViewById(R.id.spin_fragdiaplay_pattern_display);
        btnViewDisplayPattern = (Button)findViewById(R.id.btnViewDisplayPattern);
        llViewDisplayPattern = (LinearLayout)findViewById(R.id.llDisplayPattern);
        llSetDispplayPatternData = (LinearLayout)findViewById(R.id.llSetDisplayPatternValue);
        rlViewDisplayPatternData = (RelativeLayout)findViewById(R.id.rlViewDisplayPatternValue);
        btnSetValues = (Button) findViewById(R.id.btnDisplayPatternSetValues);
        btnGetValues = (Button)findViewById(R.id.btnDisplayPatternViewValues);
        btnViewOneDisplayPattern = (Button)findViewById(R.id.btnViewOneDisplayPattern);
        txtViewValue = (TextView)findViewById(R.id.txtViewValue);

    }

    private void createObj() {
        getSupportActionBar().setTitle("Display Pattern");
        mContext = DisplayPatternActivity.this;
        arrCommandValueList = new ArrayList<String>();

        for (int i = 0; i <= 31; i++) {
            arrCommandValueList.add(String.valueOf(i));
        }

        adapter = new ArrayAdapter<String>(mContext, R.layout.list_item, arrCommandValueList);

        spinSelectFloor.setAdapter(adapter);

        String[] tempPattern = getResources().getStringArray(R.array.arr_display_pattern);
        arrPattern = new ArrayList<>(Arrays.asList(tempPattern));

        for(int flrNo=0; flrNo<=31; flrNo++) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.display_pattern_item, null);
        TextView tvFloorNo = (TextView) view.findViewById(R.id.tvfloor_blocking_FloorNumber);
        TextView tvFloorData = (TextView) view.findViewById(R.id.tvfloor_blocking_Data);
            textViewData[flrNo] = tvFloorData;
            tvFloorNo.setText("" + flrNo);
            tvFloorData.setText("");
            llViewDisplayPattern.addView(view);
        }
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
        btnSetDisplayPattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callStopDelay();
            }
        });

        btnSetValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llSetDispplayPatternData.setVisibility(View.VISIBLE);
                rlViewDisplayPatternData.setVisibility(View.GONE);
            }
        });

        btnGetValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llSetDispplayPatternData.setVisibility(View.GONE);
                rlViewDisplayPatternData.setVisibility(View.VISIBLE);
            }
        });

        btnViewOneDisplayPattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callViewSingleDeviceId();
            }
        });

        btnViewDisplayPattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //llViewDisplayPattern.removeAllViews();
                counter = 0;

                if (isConnected()) {
                    pd = ProgressDialog.show(mContext, "", "Please wait", true);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
                }
                boolean b = ha.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //call function

                        if(counter == 0){
                            callViewDisplayPattern(counter);
                            delay();
                            counter++;
                        }else if(counter == 1){
                                callViewDisplayPattern(counter);
                                delay();
                                counter++;
                        }else if(counter ==  2 ){
                                callViewDisplayPattern(counter);
                                delay();
                                counter++;
                        }else if(counter == 3){
                                callViewDisplayPattern(counter);
                                delay();
                                counter++;
                        }else if(counter == 4){
                                callViewDisplayPattern(counter);
                                delay();
                                counter++;
                        }else if(counter == 5){
                                callViewDisplayPattern(counter);
                            delay();
                                counter++;
                        }else if(counter == 6){
                                callViewDisplayPattern(counter);
                            delay();
                                counter++;
                        }else if(counter == 7){
                                callViewDisplayPattern(counter);
                            delay();
                                counter++;
                        }else if(counter == 8){
                                callViewDisplayPattern(counter);
                            delay();
                                counter++;
                        }else if(counter == 9){
                                callViewDisplayPattern(counter);
                            delay();
                                counter++;
                        }else if(counter == 10){
                                callViewDisplayPattern(counter);
                            delay();
                                counter++;
                        }else if(counter == 11){
                                callViewDisplayPattern(counter);
                            delay();
                                counter++;
                        }else if(counter == 12){
                                callViewDisplayPattern(counter);
                            delay();
                                counter++;
                        }else if(counter == 13){
                                callViewDisplayPattern(counter);
                            delay();
                                counter++;
                        }else if(counter == 14){
                                callViewDisplayPattern(counter);
                            delay();
                                counter++;
                        }else if(counter == 15){
                                callViewDisplayPattern(counter);
                            delay();
                                counter++;
                        }else if(counter == 16){
                                callViewDisplayPattern(counter);
                            delay();
                                counter++;
                        }else if(counter == 17){
                                callViewDisplayPattern(counter);
                            delay();
                                counter++;
                        }else if(counter == 18){
                                callViewDisplayPattern(counter);
                            delay();
                                counter++;
                        }else if(counter == 19){
                                callViewDisplayPattern(counter);
                            delay();
                                counter++;
                        }else if(counter == 20){
                                callViewDisplayPattern(counter);
                            delay();
                                counter++;
                        }else if(counter == 21){
                                callViewDisplayPattern(counter);
                            delay();
                                counter++;
                        }else if(counter == 22){
                                callViewDisplayPattern(counter);
                            delay();
                                counter++;
                        }else if(counter == 23){
                                callViewDisplayPattern(counter);
                            delay();
                                counter++;
                        }else if(counter == 24){
                                callViewDisplayPattern(counter);
                            delay();
                                counter++;
                        }else if(counter == 25){
                                callViewDisplayPattern(counter);
                            delay();
                                counter++;
                        }else if(counter == 26){
                                callViewDisplayPattern(counter);
                            delay();
                                counter++;
                        }else if(counter == 27){
                                callViewDisplayPattern(counter);
                            delay();
                                counter++;
                        }else if(counter == 28){
                                callViewDisplayPattern(counter);
                            delay();
                                counter++;
                        }else if(counter == 29){
                                callViewDisplayPattern(counter);
                            delay();
                                counter++;
                        }else if(counter == 30){
                                callViewDisplayPattern(counter);
                            delay();
                                counter++;
                        }else if(counter == 31){
                                callViewDisplayPattern(counter);
                            delay();
                                counter++;
                        }else if(counter == 32){
                                counter++;
                             //   Log.e(TAG, "counter = "+counter);
                            if(isConnected()){
                                pd.dismiss();
                            }
                            delay();
                                showReceivedDataNew();

                        }
                        ha.postDelayed(this, 500);
                    }
                }, 500);
            }

        });
    }


    int a1 = 18;
    int a2 = 17;
    int a3 = 112;
    int a4;
    int a5;
    int a6 = 00;

    public void callStopDelay() {

        a4 = 192 + Integer.parseInt(spinSelectFloor.getSelectedItem().toString());
        if (spinDisplayPattern.getSelectedItemPosition() == 55) {
            a5 = 55;
        } else if (spinDisplayPattern.getSelectedItemPosition() == 56) {
            a5 = 251;
        } else if (spinDisplayPattern.getSelectedItemPosition() == 57) {
            a5 = 252;
        } else if (spinDisplayPattern.getSelectedItemPosition() == 58) {
            a5 = 253;
        } else if (spinDisplayPattern.getSelectedItemPosition() == 59) {
            a5 = 254;
        } else if (spinDisplayPattern.getSelectedItemPosition() == 60) {
            a5 = 255;
        } else {
            a5 = spinDisplayPattern.getSelectedItemPosition();
        }

        int[] sendValChkSum={a1, a2, a3, a4, a5, a6};
        String strChkSum= CalculateCheckSum.calculateChkSum(sendValChkSum);
        String asciiString  = String.format("%04x", a1).substring(2,4)+String.format("%04x", a2).substring(2,4)+String.format("%04x", a3).substring(2,4)+String.format("%04x", a4).substring(2,4)+String.format("%04x", a5).substring(2,4)+String.format("%04x", a6).substring(2,4);
        asciiString = asciiString + strChkSum + "\r";
       // Log.e(TAG, "asciiString = "+ asciiString);

        if (isConnected()) {
            //connector.write(asciiString.getBytes());
            sendMessage(asciiString.getBytes());
        }
        else {
            Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
        }
    }

    private void callViewDisplayPattern(int floorNo) {
        int a1 = 18;
        int a2 = 17;
        int a3 = 80;
        int a4 = 192+ floorNo;
        int a5 = 00;
        int a6 = 00;


        int[] sendValChkSum={a1, a2, a3, a4, a5, a6};

        String strChkSum= CalculateCheckSum.calculateChkSum(sendValChkSum);

        /*int sum = a1 + a2 + a3 + a4 + a5 + a6;
        String sumHex = String.format("%04x", sum);

        String msb = sumHex.substring(1, 2);
        String lsb = sumHex.substring(2, 4);

        int a7 = Integer.parseInt(lsb, 16);
        int msb2 = (Integer.parseInt(msb) | 50);
        int a8 = Integer.parseInt(String.valueOf(msb2), 16);

        byte[] br2 = {(byte) a1, (byte) a2, (byte) a3, (byte) a4, (byte) a5, (byte) a6,(byte) '\r'};
        byte[] br1 = {(byte) a1, (byte) a2, (byte) a3, (byte) a4, (byte) a5, (byte) a6, (byte) a7, (byte) a8};*/

        String asciiString  = String.format("%04x", a1).substring(2,4)+String.format("%04x", a2).substring(2,4)+String.format("%04x", a3).substring(2,4)+String.format("%04x", a4).substring(2,4)+String.format("%04x", a5).substring(2,4)+String.format("%04x", a6).substring(2,4) ;
       /* int sumSendString  = 0;
        for(int i = 0; i<asciiString.length(); i++){
            sumSendString = sumSendString + Integer.parseInt(String.format("%04x", (int) asciiString.charAt(i)).substring(2,4));
        }*/
        //asciiString = asciiString +String.valueOf(sumSendString).substring(1,3)+ "\r";
        asciiString = asciiString + strChkSum + "\r";
     //   Log.e(TAG, "asciiString = "+ asciiString);

        if (isConnected()) {
//            connector.write(br1);
            //connector.write(asciiString.getBytes());
            sendMessage(asciiString.getBytes());
        }
        /*else {
            Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
        }*/
    }


    void callViewSingleDeviceId(){
        cntSetText = 0;
        int floorNo = spinSelectFloor.getSelectedItemPosition();

        int a1 = 18;
        int a2 = 17;
        int a3 = 80;
        int a4 = 192+ floorNo;
        int a5 = 00;
        int a6 = 00;


        int[] sendValChkSum={a1, a2, a3, a4, a5, a6};

        String asciiString  = String.format("%04x", a1).substring(2,4)+String.format("%04x", a2).substring(2,4)+String.format("%04x", a3).substring(2,4)+String.format("%04x", a4).substring(2,4)+String.format("%04x", a5).substring(2,4)+String.format("%04x", a6).substring(2,4) ;
        String strChkSum= CalculateCheckSum.calculateChkSum(sendValChkSum);
        asciiString = asciiString + strChkSum + "\r";

      //  Log.e(TAG, "asciiString = "+ asciiString);

        if (isConnected()) {
            sendMessage(asciiString.getBytes());

            cntSetText = 1;
            spinFlr = spinSelectFloor.getSelectedItemPosition();
         //   Log.e(TAG, "spinFlr = "+ spinFlr);
           /* if(!str11f1Id[spinFlr].equals("")){

                txtViewValue.setText(str11f1Id[spinFlr]);
            }*/
        } else {
            Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
        }
    }

    public void setTextViewValue(int flrNo){

        if(!str11DisFlr[flrNo].equals("")){
            txtViewValue.setText(str11DisFlr[flrNo]);
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
                    menu.getItem(0).setIcon(ContextCompat.getDrawable(mContext, R.drawable.grn_bt));
                }
                catch (Exception e){
                    //Catch
                }
            }
            else {
                try{
                    menu.getItem(0).setIcon(ContextCompat.getDrawable(mContext, R.drawable.red_bt));
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
    public void showReceivedDataNew() {
        //Log.e(TAG, "ShowReceivedData");

        if(!str11DisFlr[0].equals("")){
            textViewData[0].setText(str11DisFlr[0]);
        }
        if(!str11DisFlr[1].equals("")){
            textViewData[1].setText(str11DisFlr[1]);
        }
        if(!str11DisFlr[2].equals("")){
            textViewData[2].setText(str11DisFlr[2]);
        }
        if(!str11DisFlr[3].equals("")){
            textViewData[3].setText(str11DisFlr[3]);
        }
        if(!str11DisFlr[4].equals("")){
            textViewData[4].setText(str11DisFlr[4]);
        }
        if(!str11DisFlr[5].equals("")){
            textViewData[5].setText(str11DisFlr[5]);
        }
        if(!str11DisFlr[6].equals("")){
            textViewData[6].setText(str11DisFlr[6]);
        }
        if(!str11DisFlr[7].equals("")){
            textViewData[7].setText(str11DisFlr[7]);
        }
        if(!str11DisFlr[8].equals("")){
            textViewData[8].setText(str11DisFlr[8]);
        }
        if(!str11DisFlr[9].equals("")){
            textViewData[9].setText(str11DisFlr[9]);
        }
        if(!str11DisFlr[10].equals("")){
            textViewData[10].setText(str11DisFlr[10]);
        }
        if(!str11DisFlr[11].equals("")){
            textViewData[11].setText(str11DisFlr[11]);
        }
        if(!str11DisFlr[12].equals("")){
            textViewData[12].setText(str11DisFlr[12]);
        }
        if(!str11DisFlr[13].equals("")){
            textViewData[13].setText(str11DisFlr[13]);
        }
        if(!str11DisFlr[14].equals("")){
            textViewData[14].setText(str11DisFlr[14]);
        }
        if(!str11DisFlr[15].equals("")){
            textViewData[15].setText(str11DisFlr[15]);
        }
        if(!str11DisFlr[16].equals("")){
            textViewData[16].setText(str11DisFlr[16]);
        }
        if(!str11DisFlr[17].equals("")){
            textViewData[17].setText(str11DisFlr[17]);
        }
        if(!str11DisFlr[18].equals("")){
            textViewData[18].setText(str11DisFlr[18]);
        }
        if(!str11DisFlr[19].equals("")){
            textViewData[19].setText(str11DisFlr[19]);
        }
        if(!str11DisFlr[20].equals("")){
            textViewData[20].setText(str11DisFlr[20]);
        }
        if(!str11DisFlr[21].equals("")){
            textViewData[21].setText(str11DisFlr[21]);
        }
        if(!str11DisFlr[22].equals("")){
            textViewData[22].setText(str11DisFlr[22]);
        }
        if(!str11DisFlr[23].equals("")){
            textViewData[23].setText(str11DisFlr[23]);
        }
        if(!str11DisFlr[24].equals("")){
            textViewData[24].setText(str11DisFlr[24]);
        }
        if(!str11DisFlr[25].equals("")){
            textViewData[25].setText(str11DisFlr[25]);
        }
        if(!str11DisFlr[26].equals("")){
            textViewData[26].setText(str11DisFlr[26]);
        }
        if(!str11DisFlr[27].equals("")){
            textViewData[27].setText(str11DisFlr[27]);
        }
        if(!str11DisFlr[28].equals("")){
            textViewData[28].setText(str11DisFlr[28]);
        }
        if(!str11DisFlr[29].equals("")){
            textViewData[29].setText(str11DisFlr[29]);
        }
        if(!str11DisFlr[30].equals("")){
            textViewData[30].setText(str11DisFlr[30]);
        }
        if(!str11DisFlr[31].equals("")){
            textViewData[31].setText(str11DisFlr[31]);
        }

/*        if(!str11DisFlr0.equals("")){
            textViewData[0].setText(str11DisFlr0);
        }
        if(!str11DisFlr1.equals("")){
            textViewData[1].setText(str11DisFlr1);
        }
        if(!str11DisFlr2.equals("")){
            textViewData[2].setText(str11DisFlr2);
        }
        if(!str11DisFlr3.equals("")){
            textViewData[3].setText(str11DisFlr3);
        }
        if(!str11DisFlr4.equals("")){
            textViewData[4].setText(str11DisFlr4);
        }
        if(!str11DisFlr5.equals("")){
            textViewData[5].setText(str11DisFlr5);
        }
        if(!str11DisFlr6.equals("")){
            textViewData[6].setText(str11DisFlr6);
        }
        if(!str11DisFlr7.equals("")){
            textViewData[7].setText(str11DisFlr7);
        }
        if(!str11DisFlr8.equals("")){
            textViewData[8].setText(str11DisFlr8);
        }
        if(!str11DisFlr9.equals("")){
            textViewData[9].setText(str11DisFlr9);
        }
        if(!str11DisFlr10.equals("")){
            textViewData[10].setText(str11DisFlr10);
        }
        if(!str11DisFlr11.equals("")){
            textViewData[11].setText(str11DisFlr11);
        }
        if(!str11DisFlr12.equals("")){
            textViewData[12].setText(str11DisFlr12);
        }
        if(!str11DisFlr13.equals("")){
            textViewData[13].setText(str11DisFlr13);
        }
        if(!str11DisFlr14.equals("")){
            textViewData[14].setText(str11DisFlr14);
        }
        if(!str11DisFlr15.equals("")){
            textViewData[15].setText(str11DisFlr15);
        }
        if(!str11DisFlr16.equals("")){
            textViewData[16].setText(str11DisFlr16);
        }
        if(!str11DisFlr17.equals("")){
            textViewData[17].setText(str11DisFlr17);
        }
        if(!str11DisFlr18.equals("")){
            textViewData[18].setText(str11DisFlr18);
        }
        if(!str11DisFlr19.equals("")){
            textViewData[19].setText(str11DisFlr19);
        }
        if(!str11DisFlr20.equals("")){
            textViewData[20].setText(str11DisFlr20);
        }
        if(!str11DisFlr21.equals("")){
            textViewData[21].setText(str11DisFlr21);
        }
        if(!str11DisFlr22.equals("")){
            textViewData[22].setText(str11DisFlr22);
        }
        if(!str11DisFlr23.equals("")){
            textViewData[23].setText(str11DisFlr23);
        }
        if(!str11DisFlr24.equals("")){
            textViewData[24].setText(str11DisFlr24);
        }
        if(!str11DisFlr25.equals("")){
            textViewData[25].setText(str11DisFlr25);
        }
        if(!str11DisFlr26.equals("")){
            textViewData[26].setText(str11DisFlr26);
        }
        if(!str11DisFlr27.equals("")){
            textViewData[27].setText(str11DisFlr27);
        }
        if(!str11DisFlr28.equals("")){
            textViewData[28].setText(str11DisFlr28);
        }
        if(!str11DisFlr29.equals("")){
            textViewData[29].setText(str11DisFlr29);
        }
        if(!str11DisFlr30.equals("")){
            textViewData[30].setText(str11DisFlr30);
        }
        if(!str11DisFlr31.equals("")){
            textViewData[31].setText(str11DisFlr31);
        }*/
    }
}
