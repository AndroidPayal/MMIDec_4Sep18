package com.radioknit.testmmi;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.radioknit.testmmi.MainActivity.isConnected;
import static com.radioknit.testmmi.MainActivity.sendMessage;

public class SetDateTimeActivity extends AppCompatActivity {

    private static final String TAG = "Tag_SetDataTimeAct";
    private Context mContext;
    private TextView mTvDeviceName;
    private Button btnDone;
    private Button btnCancel;
    private TextView txtDate;
    private TextView txtTime;
    private Button btnSetDate;
    private Button btnsetTime;

    private DatePicker datePicker;
    private Calendar calendar;
    private int myear, mmonth, mday;
    private int mHour, mMinute;
    private TextView txtCommands;
    private BluetoothAdapter bluetoothAdapter;

    // =============

    private OutputStream outputStream;
    private static final String DEVICE_NAME = "DEVICE_NAME";
    private static final String LOG = "LOG";

    private static final SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm:ss.SSS");
    private Menu menu;
    final Handler myHandlerChk = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_set_date_time);

        generateId();
        createObj();
        registerEvent();
        myHandlerChk.postDelayed(checkDataContinue, 0);
    }


    private void registerEvent() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateCommand =  "12F331031700";
                String timeCommand =  "12F2"+mHour+mMinute+"0000";

                String str1 = Integer.toHexString(18);
                String str2 = Integer.toHexString(243);
//                String str3 = Integer.toHexString(mday);
//                String str4 = Integer.toHexString(mmonth +1);
                String str3 = String.valueOf(mday);
                String str4 = String.valueOf(mmonth + 1);
                String yr = String.valueOf(myear).substring(2,4);
//                Log.e(TAG, "Yr = "+ yr);
//                String str5 = Integer.toHexString(Integer.parseInt(yr));
                                             String str5 = String.valueOf(yr);
                String str6 = Integer.toHexString(68);

                Log.d(TAG, "btnDone: str1 ="+str1 + " str2="+str2 + " str3= "+str3);
/*
D/Tag_SetDataTimeAct: btnDone: str1 =12 str2=f3 str3= 1
E/Tag_SetDataTimeAct: asciiString = 12f30103194492
* */
                int sum = 0;
//                Log.e(TAG, "day1 = "+mday+" month1 = "+ (mmonth+1)+" year1 = "+Integer.parseInt(yr));
                sum = sum+ 18+243+mday+(mmonth+1)+Integer.parseInt(yr)+68;
                if(Integer.parseInt(yr) >= 10){
                    sum = sum +6;
                }

                if((mmonth+1) >= 10){
                    sum = sum +6;
                }
                if(mday >= 10){
                    sum = sum + 6;
                }
                String sumHex = String.format("%04x", sum+6);
                Log.e(TAG, " Sum =  "+ sum);
                Log.e(TAG, " sumHex =  "+ sumHex);
                String msb = sumHex.substring(1,2);
                Log.e(TAG,"Msb = "+ msb);
                String lsb = sumHex.substring(2,4);
                Log.e(TAG,"lsb = "+ lsb);
                int msb1 = (Integer.parseInt(msb) | 50);//00110010
                Log.e(TAG,"msb1 = "+ msb1);

                String finalString = str1+str2+str3+str4+str5+str6+lsb+msb1;

                int a1 = Integer.parseInt(str1,16);
                int a2 = Integer.parseInt(str2,16);
                int a3 = Integer.parseInt(str3,16);
                int a4 = Integer.parseInt(str4,16);
                int a5 = Integer.parseInt(str5,16);

                Log.d(TAG, "onClick: str5="+str5 +" a5="+a5);
/*2019-03-04 12:15:31.750 22894-22894/com.radioknit.mmidec D/Tag_SetDataTimeAct: onClick: str5=19 a5=25*/

                int a6 = Integer.parseInt(str6,16);
                int a7 = Integer.parseInt(lsb,16);
                int a8 = Integer.parseInt(String.valueOf(msb1),16);

                Log.e(TAG, " a3 = "+ a3 +" a4 = "+ a4 +" a5 = "+a5);

                int[] sendValChkSum={a1, a2, a3, a4, a5, a6};

/**
* 2019-03-04 11:49:37.578 18805-18805/com.radioknit.mmidec D/Tag_SetDataTimeAct: btnDone: str1 =12 str2=f3 str3= 4
 * 2019-03-04 11:49:37.579 18805-18805/Tag_SetDataTimeAct:  Sum =  361
 * 2019-03-04 11:49:37.579 18805-18805/Tag_SetDataTimeAct:  sumHex =  016f
 * 2019-03-04 11:49:37.579 18805-18805/Tag_SetDataTimeAct: Msb = 1
 * 2019-03-04 11:49:37.579 18805-18805/Tag_SetDataTimeAct: lsb = 6f
 * 2019-03-04 12:08:44.840 21503-21503/Tag_SetDataTimeAct: msb1 = 51
 * 2019-03-04 11:49:37.579 18805-18805/Tag_SetDataTimeAct:  a3 = 4 a4 = 3 a5 = 25
 * 2019-03-04 11:49:37.584 18805-18805/Tag_SetDataTimeAct: asciiString = 12f304031944 strChkSum = 95
 * 2019-03-04 11:49:37.584 18805-18805/Tag_SetDataTimeAct: asciiString to send= 12f30403194495
*/
/*
* 2019-03-04 15:40:53.158 8822-8822/? E/Tag_SetDataTimeAct: simple a3 = 20 foramted a3 = 0014
2019-03-04 15:40:53.159 8822-8822/? E/Tag_SetDataTimeAct: simple a1 = 18 foramted a3 = 0012
2019-03-04 15:40:53.160 8822-8822/? E/Tag_SetDataTimeAct: simple a2 = 243 foramted a2 = 00f3
2019-03-04 15:40:53.160 8822-8822/? E/Tag_SetDataTimeAct: simple a4 = 3 foramted a4 = 0003
2019-03-04 15:40:53.161 8822-8822/? E/Tag_SetDataTimeAct: simple a5 = 25 foramted a5 = 0019
2019-03-04 15:40:53.162 8822-8822/? E/Tag_SetDataTimeAct: simple a6 = 68 foramted a6 = 0044
2019-03-04 15:40:53.167 8822-8822/? E/Tag_SetDataTimeAct: asciiString = 12f314031944 strChkSum = 96
2019-03-04 15:40:53.167 8822-8822/? E/Tag_SetDataTimeAct: asciiString to send= 12f31403194496*/

                String strChkSum= CalculateCheckSum.calculateChkSum(sendValChkSum);

                byte[] br1 = {(byte)0x12,(byte) 0xf3 , (byte) a5,(byte) a4,(byte)a3,(byte) a6,(byte) a7, (byte) a8};
                Log.e(TAG, "simple a3 = "+a3+" foramted a3 = "+ String.format("%04x", a3));
                Log.e(TAG, "simple a1 = "+a1+" foramted a3 = "+ String.format("%04x", a1));
                Log.e(TAG, "simple a2 = "+a2+" foramted a2 = "+ String.format("%04x", a2));
                Log.e(TAG, "simple a4 = "+a4+" foramted a4 = "+ String.format("%04x", a4));
                Log.e(TAG, "simple a5 = "+a5+" foramted a5 = "+ String.format("%04x", a5));
                Log.e(TAG, "simple a6 = "+a6+" foramted a6 = "+ String.format("%04x", a6));


/*FOR DATE 14 3 19
2019-03-04 12:35:55.045 26998-26998/Tag_SetDataTimeAct: simple a3 = 20 foramted a3 = 0014
* 2019-03-04 12:40:07.903 27772-27772/Tag_SetDataTimeAct: simple a1 = 18 foramted a3 = 0012
2019-03-04 12:40:07.904 27772-27772/Tag_SetDataTimeAct: simple a2 = 243 foramted a2 = 00f3
2019-03-04 12:40:07.904 27772-27772/Tag_SetDataTimeAct: simple a4 = 3 foramted a4 = 0003
2019-03-04 12:40:07.905 27772-27772/Tag_SetDataTimeAct: simple a5 = 25 foramted a5 = 0019
2019-03-04 12:40:07.906 27772-27772/Tag_SetDataTimeAct: simple a6 = 68 foramted a6 = 0044*/

   String asciiString  = String.format("%04x", a1).substring(2,4)+String.format("%04x", a2).substring(2,4)+String.format("%04x", a3).substring(2,4)+String.format("%04x", a4).substring(2,4)+String.format("%04x", a5).substring(2,4)+String.format("%04x", a6).substring(2,4) ;
                Log.e(TAG, "asciiString = "+asciiString +" strChkSum = "+strChkSum);
                /*int sumSendString  = 0;
                for(int i = 0; i<asciiString.length(); i++){
                    sumSendString = sumSendString + Integer.parseInt(String.format("%04x", (int) asciiString.charAt(i)).substring(2,4));}
                Log.e(TAG, "sumSendString = "+ sumSendString);*/
                //asciiString = asciiString +String.valueOf(sumSendString).substring(1,3)+ "\r";
                asciiString = asciiString + strChkSum + "\r";
                Log.e("Tag_timer", "asciiString date= "+ asciiString);

                if (isConnected()) {
//                  connector.write(br1);
                    //connector.write(asciiString.getBytes());
                    sendMessage(asciiString.getBytes());
                }
                else {
                    Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
                }
//
//                Utils.calculateHexaValue(br1.toString());

                try {
                    Thread.sleep(50);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // For time command
                int sum2 = 0;
                String str21 = Integer.toHexString(242);
//                String str31 = Integer.toHexString(mHour);
//                String str41 = Integer.toHexString(mMinute);
                String str31 = String.valueOf(mHour);
                String str41 = String.valueOf(mMinute);
                sum2 = sum2+ 18+243+mHour+mMinute+68+68;
                if(mHour >= 10){
                    sum2 = sum2 + 6;
                }

                if(mMinute >= 10){
                    sum2 = sum2 + 6;
                }

                String sumHex2 = String.format("%04x", sum2 + 6);
//                Log.e(TAG, " Sum time =  "+ sum2);
//                Log.e(TAG, " sumHex time =  "+ sumHex2);
                String msb2 = sumHex2.substring(1,2);
//                Log.e(TAG,"Msb time= "+ msb2);
                String lsb2 = sumHex2.substring(2,4);
//                Log.e(TAG,"lsb time = "+ lsb2);
                int msb3 = (Integer.parseInt(msb2) | 50);
//                Log.e(TAG, "OR Result  time =" +(Integer.parseInt(msb2) | 50));


                int b1 = Integer.parseInt(str1,16);
                int b2 = Integer.parseInt(str21,16);
                int b3 = Integer.parseInt(str31,16);
                int b4 = Integer.parseInt(str41,16);
                int b5 = Integer.parseInt(str6,16);
                int b6 = Integer.parseInt(str6,16);
                int b7 = Integer.parseInt(lsb2,16);
                int b8 = Integer.parseInt(String.valueOf(msb3),16);


                int[] sendValChkSum1={b1, b2, b3, b4, b5, b6};

                String strChkSum1= CalculateCheckSum.calculateChkSum(sendValChkSum1);


                Log.e(TAG, "Input String b values  = "+b1 +"    "+b2+"     "+ b3+"   " + b4 +"   "+b5+"   "+ b6+"   "+b7+"   "+b8 );


//                Log.e(TAG, "Char = "+ (char) a1 );

       /* *****   byte[] br2 = {(byte)0x12,(byte) 0xf2 , (byte) b3,(byte) b4,(byte)b5,(byte) b6,(byte) b7, (byte) b8};*/
//                Log.e(TAG, "br2 = "+ new String(br2));

                String asciiString1  = String.format("%04x", b1).substring(2,4)+String.format("%04x", b2).substring(2,4)+String.format("%04x", b3).substring(2,4)+String.format("%04x", b4).substring(2,4)+String.format("%04x", b5).substring(2,4)+String.format("%04x", b6).substring(2,4) ;
                Log.e(TAG, "asciiString time = "+asciiString);
/*                int sumSendString1  = 0;
                for(int i = 0; i<asciiString1.length(); i++){
                    sumSendString1 = sumSendString1 + Integer.parseInt(String.format("%04x", (int) asciiString1.charAt(i)).substring(2,4));
                }
                Log.e(TAG, "sumSendString = "+ sumSendString);
                asciiString1 = asciiString1 +String.valueOf(sumSendString1).substring(1,3)+ "\r";*/
                asciiString1 = asciiString1 + strChkSum1 + "\r";
//                Log.e(TAG, "asciiString = "+ asciiString1);


                Log.d("Tag_timer", "ascii string time= "+asciiString1);

                if (isConnected()) {
//                    connector.write(br1);
                    //connector.write(asciiString1.getBytes());
                    sendMessage(asciiString1.getBytes());
                }
                else {
                    Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
                }

//                txtCommands.setText(br1 +" \n \n"+ br2);

            }
        });

        btnSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate(myear, mmonth+1, mday);
                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                myear = year;
                                mmonth = monthOfYear;
                                mday = dayOfMonth;

                            }
                        }, myear, mmonth,mday );
                datePickerDialog.show();
            }
        });


        btnsetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        txtTime.setText(hourOfDay + "-" +  minute);
                        mHour = hourOfDay;
                        mMinute = minute;
                    }
                },  mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

    }

    private void createObj() {

        getSupportActionBar().setTitle("Date And Time");

        mContext = SetDateTimeActivity.this;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        calendar = Calendar.getInstance();
        myear = calendar.get(Calendar.YEAR);

        mmonth = calendar.get(Calendar.MONTH);
        mday = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(myear, mmonth+1, mday);
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);


    }

    private void generateId() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateandTime = sdf.format(new Date());

        btnDone = (Button)findViewById(R.id.btnSetDateTime_Done);
        btnCancel = (Button)findViewById(R.id.btnSetDateTime_Cancel);
        btnSetDate = (Button) findViewById(R.id.btnSetDateTimeFrag_setDate);
        btnsetTime = (Button)findViewById(R.id.btnSetDateTimeFrag_setTime);
        txtDate = (TextView)findViewById(R.id.txtSetDateTimeFrag_Date);
        txtTime = (TextView)findViewById(R.id.txtSetDateTimeFrag_Time);
        txtCommands = (TextView) findViewById(R.id.tvCommands);
    }

    private void showDate(int year, int month, int day) {
        txtDate.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
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
            myHandlerChk.postDelayed(this, 100);
            //this postdelay making a contineous callback every 100 millisecond
        }

    };


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
                serverIntent = new Intent(SetDateTimeActivity.this, DeviceListActivity.class);
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


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.device_control_activity, menu);
        return true;
    }
    // ============================================================================


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_search:
                if (super.isAdapterReady()) {
                    if (isConnected()) stopConnection();
                    else startDeviceListActivity();
                } else {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                }
                return true;

            case R.id.wroteModeEnable :
                Intent intent = new Intent(mContext, WriteModeEnableActivity.class);
                startActivityForResult(intent,WRITE_MODE_ENABLE );
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    // ============================================================================
*/




}
