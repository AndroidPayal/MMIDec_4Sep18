package com.radioknit.mmidec;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;


import pl.droidsonroids.gif.GifImageView;

import static com.radioknit.mmidec.MainActivity.isConnected;
import static com.radioknit.mmidec.MainActivity.sendMessage;
import static com.radioknit.mmidec.MainActivity.str05MainFloor;

/**
 * Created by soft on 20/6/18.
 */

public class AutoLevelActivity extends AppCompatActivity {
    private static final String TAG = "AutoLevelActivity";
    private Context mContext;
    private Menu menu;
    final Handler myHandlerChk = new Handler();
    private Button btnAutoLevelFunction;
    private static GifImageView imgDn;
    private static GifImageView imgUp;
    private TextView txtFloorNumber;
    String strReceivedDataNew = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_auto_level);

        generateId();
        createObj();
        registerEvent();
        myHandlerChk.postDelayed(checkDataContinue, 0);

    }


    private void createObj() {
        getSupportActionBar().setTitle("Auto Level");
        mContext = AutoLevelActivity.this;
    }


    private void registerEvent() {
        btnAutoLevelFunction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAutoLevelFunction();
            }
        });

    }


    private void callAutoLevelFunction() {
        if (!isConnected()) {
            Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
            return;
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AutoLevelActivity.this);
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
                //Log.e(TAG, "asciiString = "+ asciiString);

                if (isConnected()) {
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


    private void generateId() {
        btnAutoLevelFunction = (Button)findViewById(R.id.btnAutoLevelFunction);
        imgUp = (GifImageView)findViewById(R.id.imgUp);
        imgDn = (GifImageView)findViewById(R.id.imgDwn);
        txtFloorNumber = (TextView)findViewById(R.id.tv_main_display_floorNo);
        strReceivedDataNew = "";
    }


    private Runnable checkDataContinue = new Runnable() {

        public void run() {
            showReceivedData();
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

            myHandlerChk.postDelayed(this, 100);
        }

    };

    private void showReceivedData() {
        if(!str05MainFloor.equals("")){
            showFloorNoAndDirection(str05MainFloor);
        }
    }


    public void showFloorNoAndDirection(String receivedData){

        if(receivedData.startsWith("05") && !receivedData.equals(strReceivedDataNew)){
            strReceivedDataNew = receivedData;
            int floorNo = Integer.parseInt(receivedData.substring(4,6), 16);
            String STAT0 = receivedData.substring(6,8);
            String hexFour = String.format("%04x", Integer.parseInt(STAT0, 16));
            String strBinaryFour = Utils.hexToBin(hexFour);

            txtFloorNumber.setText(""+floorNo);
            if (strBinaryFour.charAt(7) == '1') {
                if (strBinaryFour.charAt(5) == '1') {
                    imgUp.setVisibility(View.VISIBLE);
                    imgDn.setVisibility(View.GONE);
                    imgUp.setImageResource(R.drawable.up_flashing);
                } else {
                    imgUp.setVisibility(View.VISIBLE);
                    imgDn.setVisibility(View.GONE);
                    imgUp.setImageResource(R.drawable.up_arraow);
                }
            } else if (strBinaryFour.charAt(6) == '1') {
                if (strBinaryFour.charAt(5) == '1') {
                    imgDn.setVisibility(View.VISIBLE);
                    imgUp.setVisibility(View.GONE);
                    imgDn.setImageResource(R.drawable.down_flashing);
                } else {
                    imgDn.setVisibility(View.VISIBLE);
                    imgUp.setVisibility(View.GONE);
                    imgDn.setImageResource(R.drawable.down_arr);
                }
            }else if(strBinaryFour.charAt(7) == '0'){
                imgUp.setVisibility(View.GONE);
                imgDn.setVisibility(View.GONE);

            }else if(strBinaryFour.charAt(6) == '0'){
                imgUp.setVisibility(View.GONE);
                imgDn.setVisibility(View.GONE);
            }
            //temp = "";
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

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


