package com.radioknit.testmmi;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import pl.droidsonroids.gif.GifImageView;

import static com.radioknit.testmmi.MainActivity.isConnected;
import static com.radioknit.testmmi.MainActivity.str05MainFloor;
import static com.radioknit.testmmi.MainActivity.str06MainPre;
import static com.radioknit.testmmi.MainActivity.str11CompulsoryStop;
import static com.radioknit.testmmi.MainActivity.str11FireFloor;
import static com.radioknit.testmmi.MainActivity.str11HomeFloor;
import static com.radioknit.testmmi.MainActivity.str11ParkingFloor;
import static com.radioknit.testmmi.MainActivity.str13MainCarCall;

public class MainDisplayActivity extends AppCompatActivity {

    private static final String TAG = "MainDisplayActivity";
    private BluetoothAdapter bluetoothAdapter;
    private String temp = "";
    private ListView lstFloorsIndicator;
    private Button btnData;
    private Button btnSecurity;
    private LinearLayout llData;
    private LinearLayout llSecurity;
    private static LinearLayout llWireDiagram;
    private static Button btnDoorOpen;
    private static Button btnDoorClose;
    private static Button btnAttnStart;
    private static Button btnNonStop;
    private static Button btnAuto;
    private static Button btnVIP2;
    private static Button btnDIR;
    private static Button btnStop;
    private String remaningString;
//    private static ImageView imgDn;
//    private static ImageView imgUp;
    private static GifImageView imgDn;
    private static GifImageView imgUp;
    private TextView txtFloorNumber;
    private String connectedDeviceName;
    private StringBuffer outStringBuffer;
    private TextView txtCompulsoruStop;
    private TextView txtParkingFloor;
    private TextView txtHomeLanding;
    private TextView txtFiremanFloor;
    private StringBuffer mOutStringBuffer;
    private static ImageView imgPreUp;
    private static ImageView imgPreDn;
    private static TextView txtPreValue;
    private static Context mContext;
    final Handler myHandlerChk = new Handler();
    // =============
    private Menu menu;
    String strReceivedDataNew = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_display);

        /*getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        generateId();
        createObj();
        registerEvent();
        myHandlerChk.postDelayed(checkDataContinue, 0);
    }

    private void registerEvent() {
        btnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llData.setVisibility(View.VISIBLE);
                llSecurity.setVisibility(View.GONE);
            }
        });

        btnSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llData.setVisibility(View.GONE);
                llSecurity.setVisibility(View.VISIBLE);
            }
        });
    }

    private void createObj() {
        getSupportActionBar().setTitle("Main Display");
        mContext = MainDisplayActivity.this;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }


    private void generateId() {
        btnData = (Button)findViewById(R.id.btn_main_dispaly_data);
        btnSecurity = (Button)findViewById(R.id.btn_main_display_security);
        llData = (LinearLayout)findViewById(R.id.llmain_display_data);
        llSecurity = (LinearLayout)findViewById(R.id.llmina_display_security);
        llWireDiagram = (LinearLayout)findViewById(R.id.llWireDiagram);
        btnDoorOpen = (Button)findViewById(R.id.btnMani_display_doorOpen);
        btnDoorClose = (Button)findViewById(R.id.btnMani_display_doorClose);
        btnAttnStart = (Button)findViewById(R.id.btnMani_display_AttnStart);
        btnNonStop = (Button)findViewById(R.id.btnMani_display_NonStop);
        btnDIR = (Button)findViewById(R.id.btnMani_display_Dir);
        btnAuto = (Button)findViewById(R.id.btnMani_display_Auto);
        btnStop = (Button)findViewById(R.id.btnMani_display_stop);
        btnVIP2 = (Button)findViewById(R.id.btnMani_display_VIP2);
//        imgUp = (ImageView)findViewById(R.id.imgUp);
//        imgDn = (ImageView)findViewById(R.id.imgDwn);
        imgUp = (GifImageView)findViewById(R.id.imgUp);
        imgDn = (GifImageView)findViewById(R.id.imgDwn);
        txtFloorNumber = (TextView)findViewById(R.id.tv_main_display_floorNo);
        txtCompulsoruStop = (TextView)findViewById(R.id.tvCompulsoryStopValue);
        txtParkingFloor = (TextView)findViewById(R.id.tvParkingFloorValue);
        txtHomeLanding = (TextView)findViewById(R.id.tvHomeLandingValue);
        txtFiremanFloor = (TextView)findViewById(R.id.tvFireManFloorValue);
        imgPreUp = (ImageView)findViewById(R.id.imgPreUp);
        imgPreDn = (ImageView)findViewById(R.id.imgPreDn);
        txtPreValue = (TextView) findViewById(R.id.tvPreValue);

        DrawLine drawLine = new DrawLine(getApplicationContext(), getResources().getColor(R.color.green),getResources().getColor(R.color.green),getResources().getColor(R.color.green),getResources().getColor(R.color.green),getResources().getColor(R.color.green));
        llWireDiagram.addView(drawLine);

        strReceivedDataNew = "";
    }

   /* @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(mContext, MainActivity.class));
        finish();
    }
*/
   @Override
   public void onBackPressed() {
       super.onBackPressed();
       myHandlerChk.removeCallbacks(checkDataContinue);
       //startActivity(new Intent(mContext, MainActivity.class));
       finish();
   }

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

          /*  case R.id.menu_search:
                final int REQUEST_CONNECT_DEVICE = 2;
                Intent serverIntent = null;
                serverIntent = new Intent(MainDisplayActivity.this, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
                return true;*/

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Runnable checkDataContinue = new Runnable() {

        public void run() {
            showReceivedData();
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
        }

    };


    private void showReceivedData() {

        if (!str13MainCarCall.equals("")) {
            showCarCalls(str13MainCarCall);
        }

        if(!str05MainFloor.equals("")){
            showFloorNoAndDirection(str05MainFloor);
        }

        if(!str06MainPre.equals("")){
            setPreAnnouncing(str06MainPre);
        }

        if(!str11FireFloor.equals("")){
            txtFiremanFloor.setText(str11FireFloor);
        }

        if(!str11HomeFloor.equals("")){
            txtHomeLanding.setText(str11HomeFloor);
        }

        if(!str11CompulsoryStop.equals("")){
            txtCompulsoruStop.setText(str11CompulsoryStop);
        }

        if(!str11ParkingFloor.equals("")){
            txtParkingFloor.setText(str11ParkingFloor);
        }

        /*if (temp.startsWith("71")){
            setSeftyLoopValues(temp);
        }*/
    }


    // ==========================================================================

    private void showCarCalls(String temp) {

        //setDefaultColur();
        try {
            String receivedString = temp;
            //Log.e(TAG, "-----------------------------------");
            String hexCarCalls = String.format("%04x", Integer.parseInt(receivedString.substring(10,12),16));
            String strCarCalls = Utils.hexToBin(hexCarCalls);
          //  Log.e(TAG, "hexCarCalls" + hexCarCalls);
          //  Log.e(TAG, "strCarCalls" + strCarCalls);

            if (strCarCalls.charAt(7) == '1') {
                btnDoorOpen.setBackgroundColor(mContext.getResources().getColor(R.color.red));
            }else {
                btnDoorOpen.setBackground(mContext.getResources().getDrawable(R.drawable.btn_selector));
            }

            if (strCarCalls.charAt(6) == '1') {
                btnDoorClose.setBackgroundColor(mContext.getResources().getColor(R.color.red));
            }else {
                btnDoorClose.setBackground(mContext.getResources().getDrawable(R.drawable.btn_selector));
            }

            if (strCarCalls.charAt(5) == '1') {
                btnDIR.setBackgroundColor(mContext.getResources().getColor(R.color.red));
            }else {
                btnDIR.setBackground(mContext.getResources().getDrawable(R.drawable.btn_selector));
            }

            if (strCarCalls.charAt(4) == '1') {
                btnNonStop.setBackgroundColor(mContext.getResources().getColor(R.color.red));
            }else {
                btnNonStop.setBackground(mContext.getResources().getDrawable(R.drawable.btn_selector));
            }

            if (strCarCalls.charAt(3) == '1') {
                btnAttnStart.setBackgroundColor(mContext.getResources().getColor(R.color.red));
            }else {
                btnAttnStart.setBackground(mContext.getResources().getDrawable(R.drawable.btn_selector));
            }

            if (strCarCalls.charAt(2) == '1') {
                btnVIP2.setBackgroundColor(mContext.getResources().getColor(R.color.red));
            }else {
                btnVIP2.setBackground(mContext.getResources().getDrawable(R.drawable.btn_selector));
            }

            if (strCarCalls.charAt(1) == '0') {
                btnAuto.setBackgroundColor(mContext.getResources().getColor(R.color.red));
            }else {
                btnAuto.setBackground(mContext.getResources().getDrawable(R.drawable.btn_selector));
            }

            if (strCarCalls.charAt(0) == '1') {
                btnStop.setBackgroundColor(mContext.getResources().getColor(R.color.red));
            }else {
                btnStop.setBackground(mContext.getResources().getDrawable(R.drawable.btn_selector));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @SuppressLint("NewApi")
    public void setDefaultColur(){
        btnDoorOpen.setBackground(mContext.getResources().getDrawable(R.drawable.btn_selector));
        btnDoorClose.setBackground(mContext.getResources().getDrawable(R.drawable.btn_selector));
        btnDIR.setBackground(mContext.getResources().getDrawable(R.drawable.btn_selector));
        btnNonStop.setBackground(mContext.getResources().getDrawable(R.drawable.btn_selector));
        btnAttnStart.setBackground(mContext.getResources().getDrawable(R.drawable.btn_selector));
        btnVIP2.setBackground(mContext.getResources().getDrawable(R.drawable.btn_selector));
        btnAuto.setBackground(mContext.getResources().getDrawable(R.drawable.btn_selector));
        btnStop.setBackground(mContext.getResources().getDrawable(R.drawable.btn_selector));
    }

    public void showFloorNoAndDirection(String receivedData){

        if(receivedData.startsWith("05") && !receivedData.equals(strReceivedDataNew)){
            strReceivedDataNew = receivedData;
            int floorNo = Integer.parseInt(receivedData.substring(4,6), 16);
            String STAT0 = receivedData.substring(6,8);
            String hexFour = String.format("%04x", Integer.parseInt(STAT0, 16));
            String strBinaryFour = Utils.hexToBin(hexFour);
           // Log.e(TAG, "strBinaryFour = "+ strBinaryFour);

            txtFloorNumber.setText(""+floorNo);
          //  Log.e(TAG, "DisplayData = "+ receivedData);


            if (strBinaryFour.charAt(7) == '1') {
                if (strBinaryFour.charAt(5) == '1') {
                    imgUp.setVisibility(View.VISIBLE);
                    imgDn.setVisibility(View.GONE);
                    imgUp.setImageResource(R.drawable.up_flashing);
//                        tvRunningStatus.setText("Up Running");
                        //Log.e(TAG, "Up Running");
                } else {
                    imgUp.setVisibility(View.VISIBLE);
                    imgDn.setVisibility(View.GONE);
                    imgUp.setImageResource(R.drawable.up_arraow);
                        //Log.e(TAG, "Up study");
//                        tvRunningStatus.setText("Up");
                }
            } else if (strBinaryFour.charAt(6) == '1') {
                if (strBinaryFour.charAt(5) == '1') {
                    imgDn.setVisibility(View.VISIBLE);
                    imgUp.setVisibility(View.GONE);
                    imgDn.setImageResource(R.drawable.down_flashing);
                        //Log.e(TAG, "Down Running");
//                        tvRunningStatus.setText("Down Running");
                } else {
                    imgDn.setVisibility(View.VISIBLE);
                    imgUp.setVisibility(View.GONE);
                    imgDn.setImageResource(R.drawable.down_arr);
                        //Log.e(TAG, "Down Study");
//                        tvRunningStatus.setText("Down");
                }
            } else if (strBinaryFour.charAt(7) == '0') {
                imgUp.setVisibility(View.GONE);
                imgDn.setVisibility(View.GONE);

            } else if (strBinaryFour.charAt(6) == '0') {
                imgUp.setVisibility(View.GONE);
                imgDn.setVisibility(View.GONE);
            }





/*            if (strBinaryFour.charAt(7) == '1') {
                if (strBinaryFour.charAt(5) == '1') {
                    imgUp.setVisibility(View.VISIBLE);
                    imgDn.setVisibility(View.GONE);
                    imgUp.setImageResource(R.drawable.up_flashing);
//                        tvRunningStatus.setText("Up Running");
//                        Log.e(TAG, "Up Running");
                } else {
                    imgUp.setVisibility(View.VISIBLE);
                    imgDn.setVisibility(View.GONE);
                    imgUp.setImageResource(R.drawable.up_arraow);
//                        Log.e(TAG, "Up study");
//                        tvRunningStatus.setText("Up");
                }
            } else if (strBinaryFour.charAt(6) == '1') {
                if (strBinaryFour.charAt(5) == '1') {
                    imgDn.setVisibility(View.VISIBLE);
                    imgUp.setVisibility(View.GONE);
                    imgDn.setImageResource(R.drawable.down_flashing);
//                        Log.e(TAG, "Down Running");
//                        tvRunningStatus.setText("Down Running");

                } else {
                    imgDn.setVisibility(View.VISIBLE);
                    imgUp.setVisibility(View.GONE);
                    imgDn.setImageResource(R.drawable.down_arr);
//                        Log.e(TAG, "Down Study");
//                        tvRunningStatus.setText("Down");
                }
            }else if(strBinaryFour.charAt(7) == '0'){
                imgUp.setVisibility(View.GONE);
                imgDn.setVisibility(View.GONE);

            }else if(strBinaryFour.charAt(6) == '0'){
                imgUp.setVisibility(View.GONE);
                imgDn.setVisibility(View.GONE);
            }*/
            temp = "";
        }
    }

    private void setDefaultValues( ){

       /* try {
            if(Utils.isStringNotNull(TempSharedPreference.getKeyFiremanFloor(mContext))){
                txtFiremanFloor.setText(TempSharedPreference.getKeyFiremanFloor(mContext));
            }
            if(Utils.isStringNotNull(TempSharedPreference.getKeyCompulsoryStop(mContext))){
                txtCompulsoruStop.setText(TempSharedPreference.getKeyCompulsoryStop(mContext));
            }
            if(Utils.isStringNotNull(TempSharedPreference.getKeyParkingFloor(mContext))){
                txtParkingFloor.setText(TempSharedPreference.getKeyParkingFloor(mContext));
            }
            if(Utils.isStringNotNull(TempSharedPreference.getKeyHomeFloor(mContext))){
                txtHomeLanding.setText(TempSharedPreference.getKeyHomeFloor(mContext));
            }
        }catch (Exception e){
            e.printStackTrace();
        }*/
    }

    private void setPreAnnouncing(String temp) {
        String receivedString = temp;
        try {
            String hexFloorNo = String.format("%04x", Integer.parseInt(receivedString.substring(4,6)));
            String hexDirection = String.format("%4x", Integer.parseInt(receivedString.substring(2,4)));

            txtPreValue.setText("" + Integer.parseInt(hexFloorNo, 16));
            int dir = Integer.parseInt(hexDirection.trim(), 16);
          //  Log.e(TAG, "Floor No = " + hexFloorNo + " fl = " + Integer.parseInt(hexFloorNo, 16));
          //  Log.e(TAG, "Direction = " + hexDirection + " dir = " + dir);

            if (dir == 0) {
                imgPreDn.setVisibility(View.GONE);
                imgPreUp.setVisibility(View.GONE);
            } else if (dir == 1) {
                imgPreDn.setVisibility(View.VISIBLE);
                imgPreUp.setVisibility(View.GONE);
            } else if (dir == 2) {
                imgPreUp.setVisibility(View.VISIBLE);
                imgPreDn.setVisibility(View.GONE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setSeftyLoopValues(String strReceive) {

        int color1 = mContext.getResources().getColor(R.color.green) ;
        int color2 = mContext.getResources().getColor(R.color.green);
        int color3 = mContext.getResources().getColor(R.color.green);
        int color4 = mContext.getResources().getColor(R.color.green);
        int color5 = mContext.getResources().getColor(R.color.green);

        String receivedString = strReceive;

        try {
            String hexTwo = String.format("%04x", Integer.parseInt(receivedString.substring(4,6)));

                String binary = Utils.hexToBin(hexTwo);

                if(binary.charAt(4) == '0'){
                    color1 = mContext.getResources().getColor(R.color.red);
                }else {
                    color1 = mContext.getResources().getColor(R.color.green);
                }
                if(binary.charAt(3) == '0'){
                    color2 = mContext.getResources().getColor(R.color.red);
                }else {
                    color2 = mContext.getResources().getColor(R.color.green);
                }

                if(binary.charAt(2) == '0'){
                    color3 = mContext.getResources().getColor(R.color.red);
                }else {
                    color3 = mContext.getResources().getColor(R.color.green);
                }

                if(binary.charAt(1) == '0'){
                    color4 = mContext.getResources().getColor(R.color.red);
                }else {
                    color4 = mContext.getResources().getColor(R.color.green);
                }

                if(binary.charAt(0) == '0'){
                    color5 = mContext.getResources().getColor(R.color.red);
                }else {
                    color5 = mContext.getResources().getColor(R.color.green);
                }

                DrawLine drawLine = new DrawLine(mContext, color1,color2,color3,color4,mContext.getResources().getColor(R.color.green));
                llWireDiagram.removeAllViews();
                llWireDiagram.addView(drawLine);
        }catch (Exception e ){
            e.printStackTrace();
        }

    }

}
