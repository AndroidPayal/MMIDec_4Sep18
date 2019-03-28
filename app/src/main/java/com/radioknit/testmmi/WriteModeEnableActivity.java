package com.radioknit.testmmi;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;

import static com.radioknit.testmmi.MainActivity.isConnected;
import static com.radioknit.testmmi.MainActivity.sendMessage;

public class WriteModeEnableActivity extends AppCompatActivity {

    private static final String TAG = "WriteModeEnabled";
    private Context mContext;
    ArrayList<String> arrCommandValueList;
    private Button btnWriteModeEnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_write_mode_enable);

        generateId();
        createObj();
        registerEvent();


    }


    private void registerEvent() {
        btnWriteModeEnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String str1 = Integer.toHexString(18);
                String str2 = Integer.toHexString(17);
                String str3 = Integer.toHexString(112);
                String str4 = Integer.toHexString(0);
                String str5 = Integer.toHexString(5);
                String str6 = Integer.toHexString(87);
                String str7 = Integer.toHexString(239);
                String str8 = Integer.toHexString(80);

                int a1 = Integer.parseInt(str1,16);
                int a2 = Integer.parseInt(str2,16);
                int a3 = Integer.parseInt(str3,16);
                int a4 = Integer.parseInt(str4,16);
                int a5 = Integer.parseInt(str5,16);
                int a6 = Integer.parseInt(str6,16);
                int a7 = Integer.parseInt(str7,16);
                int a8 = Integer.parseInt(str8,16);

                int[] sendValChkSum={a1, a2, a3, a4, a5, a6};

                String strChkSum= CalculateCheckSum.calculateChkSum(sendValChkSum);

                byte[] br1 = {(byte)a1,(byte) a2 , (byte) a3,(byte) a4,(byte)a5,(byte) a6,(byte) a7, (byte) a8};

                String asciiString  = String.format("%04x", a1).substring(2,4)+String.format("%04x", a2).substring(2,4)+String.format("%04x", a3).substring(2,4)+String.format("%04x", a4).substring(2,4)+String.format("%04x", a5).substring(2,4)+String.format("%04x", a6).substring(2,4) ;
               *//* int sumSendString  = 0;
                for(int i = 0; i<asciiString.length(); i++){
                    sumSendString = sumSendString + Integer.parseInt(String.format("%04x", (int) asciiString.charAt(i)).substring(2,4));
                }
                asciiString = asciiString +String.valueOf(sumSendString).substring(1,3)+ "\r";*//*
                asciiString = asciiString + strChkSum + "\r";
                Log.e(TAG, "asciiString = "+ asciiString);

                if (isConnected()) {
                    sendMessage(asciiString.getBytes());
                }
                else {
                    Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String str12 = Integer.toHexString(18);
                String str22 = Integer.toHexString(17);
                String str32 = Integer.toHexString(112);
                String str42 = Integer.toHexString(1);
                String str52 = Integer.toHexString(9);
                String str62 = Integer.toHexString(87);
                String str72 = Integer.toHexString(244);
                String str82 = Integer.toHexString(80);

                int a12 = Integer.parseInt(str12,16);
                int a22 = Integer.parseInt(str22,16);
                int a32 = Integer.parseInt(str32,16);
                int a42 = Integer.parseInt(str42,16);
                int a52 = Integer.parseInt(str52,16);
                int a62 = Integer.parseInt(str62,16);
                int a72 = Integer.parseInt(str72,16);
                int a82 = Integer.parseInt(str82,16);

                int[] sendValChkSum1={a12, a22, a32, a42, a52, a62};

                String strChkSum1= CalculateCheckSum.calculateChkSum(sendValChkSum1);

                byte[] br2 = {(byte)a12,(byte) a22 , (byte) a32,(byte) a42,(byte)a52,(byte) a62,(byte) a72, (byte) a82};

                String asciiString1  = String.format("%04x", a12).substring(2,4)+String.format("%04x", a22).substring(2,4)+String.format("%04x", a32).substring(2,4)+String.format("%04x", a42).substring(2,4)+String.format("%04x", a52).substring(2,4)+String.format("%04x", a62).substring(2,4) ;
                *//*int sumSendString1  = 0;
                for(int i = 0; i<asciiString1.length(); i++){
                    sumSendString1 = sumSendString1 + Integer.parseInt(String.format("%04x", (int) asciiString1.charAt(i)).substring(2,4));
                }
                asciiString1 = asciiString1 +String.valueOf(sumSendString1).substring(1,3)+ "\r";*//*
                asciiString1 = asciiString1 + strChkSum1 + "\r";
                Log.e(TAG, "asciiString1 = "+ asciiString1);

                if (isConnected()) {
                    sendMessage(asciiString1.getBytes());
                }
                else {
                    Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
                    return;
                }*/


                cmdWriteMode();
                //cmdWriteMode();

                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                cmdWriteMode();
                Toast.makeText(getApplicationContext(), "Write mode enabled", Toast.LENGTH_SHORT).show();
                /*int[] sendChkSumRead1={0x12, 0x11, 0x50, 0x00, 0x00, 0x00};
                String strChkSumRead1=CalculateCheckSum.calculateChkSum(sendChkSumRead1);
                String sendStrRead1="121150000000"+strChkSumRead1+ "\r";
                Log.e(TAG, "sendStrRead1 = "+ sendStrRead1);
                if (isConnected()) {
                    connector.write(sendStrRead1.getBytes());
                }
                else {
                    Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
                }

                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                int[] sendChkSumRead2={0x12, 0x11, 0x50, 0x01, 0x00, 0x00};
                String strChkSumRead2=CalculateCheckSum.calculateChkSum(sendChkSumRead2);
                String sendStrRead2="121150010000"+strChkSumRead2+ "\r";
                Log.e(TAG, "sendStrRead2 = "+ sendStrRead2);
                if (isConnected()) {
                    connector.write(sendStrRead2.getBytes());
                }
                else {
                    Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
                }*/

            }
        });

    }

//TODO : protocol for parameters
    private void cmdWriteMode(){

        String str1 = Integer.toHexString(18);
        String str2 = Integer.toHexString(17);
        String str3 = Integer.toHexString(112);
        String str4 = Integer.toHexString(0);
        String str5 = Integer.toHexString(5);
        String str6 = Integer.toHexString(87);
        String str7 = Integer.toHexString(239);
        String str8 = Integer.toHexString(80);

        int a1 = Integer.parseInt(str1,16);
        int a2 = Integer.parseInt(str2,16);
        int a3 = Integer.parseInt(str3,16);
        int a4 = Integer.parseInt(str4,16);
        int a5 = Integer.parseInt(str5,16);
        int a6 = Integer.parseInt(str6,16);
        int a7 = Integer.parseInt(str7,16);
        int a8 = Integer.parseInt(str8,16);

        Log.d(TAG, "cmdWriteMode: a1="+a1 + " a2="+a2 + " a3="+a3+" a4="+a4 + " a5="+a5+" a6="+a6);

        int[] sendValChkSum={a1, a2, a3, a4, a5, a6};

        String strChkSum= CalculateCheckSum.calculateChkSum(sendValChkSum);

        byte[] br1 = {(byte)a1,(byte) a2 , (byte) a3,(byte) a4,(byte)a5,(byte) a6,(byte) a7, (byte) a8};

        String asciiString  = String.format("%04x", a1).substring(2,4)+String.format("%04x", a2).substring(2,4)+String.format("%04x", a3).substring(2,4)+String.format("%04x", a4).substring(2,4)+String.format("%04x", a5).substring(2,4)+String.format("%04x", a6).substring(2,4) ;
               /* int sumSendString  = 0;
                for(int i = 0; i<asciiString.length(); i++){
                    sumSendString = sumSendString + Integer.parseInt(String.format("%04x", (int) asciiString.charAt(i)).substring(2,4));
                }
                asciiString = asciiString +String.valueOf(sumSendString).substring(1,3)+ "\r";*/
        asciiString = asciiString + strChkSum + "\r";
//        Log.e(TAG, "asciiString = "+ asciiString);

        if (isConnected()) {
            sendMessage(asciiString.getBytes());
        }
        else {
            Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String str12 = Integer.toHexString(18);
        String str22 = Integer.toHexString(17);
        String str32 = Integer.toHexString(112);
        String str42 = Integer.toHexString(1);
        String str52 = Integer.toHexString(9);
        String str62 = Integer.toHexString(87);
        String str72 = Integer.toHexString(244);
        String str82 = Integer.toHexString(80);

        int a12 = Integer.parseInt(str12,16);
        int a22 = Integer.parseInt(str22,16);
        int a32 = Integer.parseInt(str32,16);
        int a42 = Integer.parseInt(str42,16);
        int a52 = Integer.parseInt(str52,16);
        int a62 = Integer.parseInt(str62,16);
        int a72 = Integer.parseInt(str72,16);
        int a82 = Integer.parseInt(str82,16);

        int[] sendValChkSum1={a12, a22, a32, a42, a52, a62};

        String strChkSum1= CalculateCheckSum.calculateChkSum(sendValChkSum1);

        byte[] br2 = {(byte)a12,(byte) a22 , (byte) a32,(byte) a42,(byte)a52,(byte) a62,(byte) a72, (byte) a82};

        String asciiString1  = String.format("%04x", a12).substring(2,4)+String.format("%04x", a22).substring(2,4)+String.format("%04x", a32).substring(2,4)+String.format("%04x", a42).substring(2,4)+String.format("%04x", a52).substring(2,4)+String.format("%04x", a62).substring(2,4) ;
                /*int sumSendString1  = 0;
                for(int i = 0; i<asciiString1.length(); i++){
                    sumSendString1 = sumSendString1 + Integer.parseInt(String.format("%04x", (int) asciiString1.charAt(i)).substring(2,4));
                }
                asciiString1 = asciiString1 +String.valueOf(sumSendString1).substring(1,3)+ "\r";*/
        asciiString1 = asciiString1 + strChkSum1 + "\r";
//        Log.e(TAG, "asciiString1 = "+ asciiString1);

        if (isConnected()) {
            sendMessage(asciiString1.getBytes());
        }
        else {
            Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
            return;
        }


    }

    private void createObj() {
        getSupportActionBar().setTitle("Write Mode");
        mContext = WriteModeEnableActivity.this;
        arrCommandValueList = new ArrayList<String>();

    }

    private void generateId() {
        btnWriteModeEnable = (Button)findViewById(R.id.btnEnableWriteMode);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }


}
