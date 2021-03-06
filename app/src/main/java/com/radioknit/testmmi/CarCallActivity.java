package com.radioknit.testmmi;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.io.OutputStream;

import static com.radioknit.testmmi.MainActivity.isConnected;
import static com.radioknit.testmmi.MainActivity.str11ChkFlr;
import static com.radioknit.testmmi.MainActivity.str11HexSwitchData;
import static com.radioknit.testmmi.MainActivity.str13HexCarCallsCop1;
import static com.radioknit.testmmi.MainActivity.str13HexCarCallsCop2;

public class CarCallActivity extends AppCompatActivity implements CarCallAdapter.CarCallIndicatorSignalListner {
    //debugging
    private static final String TAG = "Tag_CarCallActivity";
    private static boolean  SHOW_TAG =false;
    private static final int REQUEST_CONNECT_DEVICE = 2;

    private static Context mContext;
    private static ListView listFloorsIndicator;
    private OutputStream outputStream;
    private static final String DEVICE_NAME = "DEVICE_NAME";
    private static final String LOG = "LOG";
    public static int showState[]=new int[16], showStateUp[] = new int[16], showStateDown[] = new int[16];
    //final Handler ha = new Handler();
    final Handler myHandlerChk = new Handler();
    CarCallAdapter adapter;
    private Menu menu;
    String str13HexCarCallsCop1N = "", str13HexCarCallsCop2N = "", str11ChkFlrN = "", str11HexSwitchDataN = "" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_call);

        if (SHOW_TAG) Log.d(TAG, "onCreate() ");

        createObj();
        generateId();
        registerEvent();
        //checkDataContinue();
        myHandlerChk.postDelayed(checkDataContinue, 0);
    }

    private void createObj() {

        if (SHOW_TAG) Log.d(TAG, "createObj() ");

        getSupportActionBar().setTitle("Car Call");
        mContext = CarCallActivity.this;

    }

    private void registerEvent() {

    }

    private void generateId() {
        if (SHOW_TAG) Log.d(TAG, "generateId() ");

        listFloorsIndicator = (ListView)findViewById(R.id.lstFloorIndicator);
        adapter = new CarCallAdapter(getApplicationContext(), "00000000","00000000" , this);
        for(int pos=0;pos<=15;pos++){
            showState[pos] = 0 ;
            showStateUp[pos] = 0;
            showStateDown[pos] = 0;
        }
        listFloorsIndicator.setAdapter(adapter);
        str13HexCarCallsCop1N = "";
        str13HexCarCallsCop2N = "";
        str11ChkFlrN = "";
        str11HexSwitchDataN = "";
    }

    @Override
    public void sendCarCallIndicatorSignal(int position) {

    }

    @Override
    public void sendUpCallIndicatorSignal(int position) {

    }

    @Override
    public void sendDnCallIndicatorSignal(int position) {

    }

    /*void checkDataContinue(){

        boolean b = ha.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!str13HexCarCallsCop1.equals("") && !str13HexCarCallsCop2.equals("")){
                    showCarCalls(str13HexCarCallsCop1, str13HexCarCallsCop2);
                }
                if(!str11ChkFlr.equals("") && !str11HexSwitchData.equals("")){
                    showUpDnCalls(str11ChkFlr, str11HexSwitchData);
                }
                ha.postDelayed(this, 100);
            }
        }, 100);
    }*/


    private Runnable checkDataContinue = new Runnable() {

        public void run() {

            if(!str13HexCarCallsCop1.equals("") && !str13HexCarCallsCop2.equals("")){
                if(SHOW_TAG)Log.d(TAG, "============run: CHECKING DATA CONTINEOUSLY=============");
                if(SHOW_TAG)
                    Log.d(TAG, "str13HexCarCallsCop1 = "+str13HexCarCallsCop1
                            + "str13HexCarCallsCop2 = "+ str13HexCarCallsCop2
                            + "str13HexCarCallsCop1N = " + str13HexCarCallsCop1N
                            + "str13HexCarCallsCop2N = " + str13HexCarCallsCop2N
                            + "str11ChkFlr = "+ str11ChkFlr
                            + "str11ChkFlrN = " + str11ChkFlrN
                            + "str11HexSwitchData=" + str11HexSwitchData
                            + "str11HexSwitchDataN = "+ str11HexSwitchDataN);

                if(!str13HexCarCallsCop1.equals(str13HexCarCallsCop1N) || !str13HexCarCallsCop2.equals(str13HexCarCallsCop2N)){
                    str13HexCarCallsCop1N = str13HexCarCallsCop1;
                    str13HexCarCallsCop2N = str13HexCarCallsCop2;

                    if (SHOW_TAG) Log.d(TAG, "++++++++++++++calling showCarCalls()+++++++++++++");

                    showCarCalls(str13HexCarCallsCop1, str13HexCarCallsCop2);
                }
            }
            if(!str11ChkFlr.equals("") && !str11HexSwitchData.equals("")) {
                if(SHOW_TAG)Log.d(TAG, "============run: CHECKING DATA CONTINEOUSLY=============");
                if(SHOW_TAG)
                    Log.d(TAG, "str13HexCarCallsCop1 = "+str13HexCarCallsCop1
                            + "str13HexCarCallsCop2 = "+ str13HexCarCallsCop2
                            + "str13HexCarCallsCop1N = " + str13HexCarCallsCop1N
                            + "str13HexCarCallsCop2N = " + str13HexCarCallsCop2N
                            + "str11ChkFlr = "+ str11ChkFlr
                            + "str11ChkFlrN = " + str11ChkFlrN
                            + "str11HexSwitchData=" + str11HexSwitchData
                            + "str11HexSwitchDataN = "+ str11HexSwitchDataN);

                if (!str11ChkFlr.equals(str11ChkFlrN) || !str11HexSwitchData.equals(str11HexSwitchDataN)) {
                    str11ChkFlrN = str11ChkFlr;
                    str11HexSwitchDataN = str11HexSwitchData;

                    if (SHOW_TAG) Log.d(TAG, "===============calling showUpDnCalls() ");

                    showUpDnCalls(str11ChkFlr, str11HexSwitchData);

                    //==============TODO :change by payal================
                    str13HexCarCallsCop1 = "";str13HexCarCallsCop2="";str13HexCarCallsCop1N="";
                    str13HexCarCallsCop2N="";str11ChkFlr="";str11ChkFlrN="";
                    str11HexSwitchData="";str11HexSwitchDataN="";

                }
            }
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
            myHandlerChk.postDelayed(this, 1000);
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

         /*   case R.id.menu_search:
                Intent serverIntent = null;
                serverIntent = new Intent(CarCallActivity.this, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
                return true;*/

            default:
                return super.onOptionsItemSelected(item);
        }
    }







     void showCarCalls(String strCarCallCop1, String strCarCallCop2) {

       /* CarCallAdapter adapter1 = new CarCallAdapter(mContext, "00000000","00000000",(CarCallAdapter.CarCallIndicatorSignalListner) this);
        listFloorsIndicator.setAdapter(adapter1);*/

        try {
            //int index = strCarCall.lastIndexOf("1311");
           /* String hexCarCallsCop1 = strCarCallCop1;
            String hexCarCallsCop2 = strCarCallCop2;*/
            String strcallCop1 = Utils.hexToBin(strCarCallCop1);
            String strcallCop2 = Utils.hexToBin(strCarCallCop2);
            //Log.e(TAG, "hexCarCallsCop1 = "+hexCarCallsCop1);
            //Log.e(TAG, "strcallCop1 = "+strcallCop1);
            //Log.e(TAG, "hexCarCallsCop2 = "+hexCarCallsCop2);
            //Log.e(TAG, "strcallCop2 = "+strcallCop2);
            String  strCallCopCombine = strcallCop2 + strcallCop1;
            Log.d(TAG, "showCarCalls: strCallCopCombine="+strCallCopCombine);

            for(int indexCop=0; indexCop <=15; indexCop++) {//showState[indexCop =0]  then florPos=15
                if (strCallCopCombine.charAt(indexCop) == '0') {
                    showState[indexCop] = 0;
                } else if(strCallCopCombine.charAt(indexCop) == '1') {
                    showState[indexCop] = 1;
                }
            }
            adapter.notifyDataSetChanged();

            /* CarCallAdapter adapter = new CarCallAdapter(mContext, strcallCop1, strcallCop2, (CarCallAdapter.CarCallIndicatorSignalListner) this);
            listFloorsIndicator.setAdapter(adapter); */
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void showUpDnCalls(String strChkFlr, String hexSwitchData) {

       /* CarCallAdapter adapter1 = new CarCallAdapter(mContext, "00000000","00000000", (CarCallAdapter.CarCallIndicatorSignalListner)this);
        listFloorsIndicator.setAdapter(adapter1);
*/
       /*
        * 03-07 10:35:12.227 /Tag_MainActivity: appendlog1() msgAppend = 3
        * 2019-03-07 10:35:12.231 /Tag_MainActivity: appendlog1() msgAppend = 32114c500
        * 2019-03-07 10:35:12.245 /Tag_MainActivity: appendlog1() msgAppend = 32114c50000184
        * 2019-03-07 10:35:12.245 /Tag_MainActivity: processReceivedData: temp = 32114c50000184  index0d = 14
        * 2019-03-07 10:35:12.245 /Tag_MainActivity: processReceivedData: temp114c50 : str11ChkFlr=32 str11HexSwitchData=01
        * 2019-03-07 10:35:12.566 /Tag_CarCallActivity: showUpDnCalls() : hexSwitchData = 01 binSwitchData=00000001 strChkFlr=32
       */
        try {
            //int index = strUpDn.lastIndexOf("114c");
            //String strChkFlr=strUpDn.substring(index-2,index);
           // Log.e(TAG, "strChkFlr = "+ strChkFlr);
            //String hexSwitchData = strUpDn.substring(index+8,index+10);
            String binSwitchData = Utils.hexToBin(hexSwitchData);
            Log.e(TAG, "showUpDnCalls() : hexSwitchData = "+ hexSwitchData +" binSwitchData="+binSwitchData + " strChkFlr="+strChkFlr);

            //TODO :payal change 
            // /*replaced :charAt(0) with charAt(7)
            // and charAt(1) with charAt(6)*/
            if(strChkFlr.equals("30")){
                if(binSwitchData.charAt(0)=='0'){
                    showStateDown[15]=0;//position = 15 for bottom most item = first floor
                }
                else if(binSwitchData.charAt(0)=='1'){
                    showStateDown[15]=1;
                }
                if(binSwitchData.charAt(1)=='0'){
                    showStateUp[15]=0;
                }
                else if(binSwitchData.charAt(1)=='1'){
                    showStateUp[15]=1;
                }
            }
            if(strChkFlr.equals("31")){
                if(binSwitchData.charAt(0)=='0'){
                    showStateDown[14]=0;
                }
                else if(binSwitchData.charAt(0)=='1'){
                    showStateDown[14]=1;
                }
                if(binSwitchData.charAt(1)=='0'){
                    showStateUp[14]=0;
                }
                else if(binSwitchData.charAt(1)=='1'){
                    showStateUp[14]=1;
                }
            }
            if(strChkFlr.equals("32")){
                if(binSwitchData.charAt(0)=='0'){
                    showStateDown[13]=0;
                }
                else if(binSwitchData.charAt(0)=='1'){
                    showStateDown[13]=1;
                }
                if(binSwitchData.charAt(1)=='0'){
                    showStateUp[13]=0;
                }
                else if(binSwitchData.charAt(1)=='1'){
                    showStateUp[13]=1;
                }
            }
            if(strChkFlr.equals("33")){
                if(binSwitchData.charAt(0)=='0'){
                    showStateDown[12]=0;
                }
                else if(binSwitchData.charAt(0)=='1'){
                    showStateDown[12]=1;
                }
                if(binSwitchData.charAt(1)=='0'){
                    showStateUp[12]=0;
                }
                else if(binSwitchData.charAt(1)=='1'){
                    showStateUp[12]=1;
                }
            }
            if(strChkFlr.equals("34")){
                if(binSwitchData.charAt(0)=='0'){
                    showStateDown[11]=0;
                }
                else if(binSwitchData.charAt(0)=='1'){
                    showStateDown[11]=1;
                }
                if(binSwitchData.charAt(1)=='0'){
                    showStateUp[11]=0;
                }
                else if(binSwitchData.charAt(1)=='1'){
                    showStateUp[11]=1;
                }
            }
            if(strChkFlr.equals("35")){
                if(binSwitchData.charAt(0)=='0'){
                    showStateDown[10]=0;
                }
                else if(binSwitchData.charAt(0)=='1'){
                    showStateDown[10]=1;
                }
                if(binSwitchData.charAt(1)=='0'){
                    showStateUp[10]=0;
                }
                else if(binSwitchData.charAt(1)=='1'){
                    showStateUp[10]=1;
                }
            }
            if(strChkFlr.equals("36")){
                if(binSwitchData.charAt(0)=='0'){
                    showStateDown[9]=0;
                }
                else if(binSwitchData.charAt(0)=='1'){
                    showStateDown[9]=1;
                }
                if(binSwitchData.charAt(1)=='0'){
                    showStateUp[9]=0;
                }
                else if(binSwitchData.charAt(1)=='1'){
                    showStateUp[9]=1;
                }
            }
            if(strChkFlr.equals("37")){
                if(binSwitchData.charAt(0)=='0'){
                    showStateDown[8]=0;
                }
                else if(binSwitchData.charAt(0)=='1'){
                    showStateDown[8]=1;
                }
                if(binSwitchData.charAt(1)=='0'){
                    showStateUp[8]=0;
                }
                else if(binSwitchData.charAt(1)=='1'){
                    showStateUp[8]=1;
                }
            }
            if(strChkFlr.equals("38")){
                if(binSwitchData.charAt(0)=='0'){
                    showStateDown[7]=0;
                }
                else if(binSwitchData.charAt(0)=='1'){
                    showStateDown[7]=1;
                }
                if(binSwitchData.charAt(1)=='0'){
                    showStateUp[7]=0;
                }
                else if(binSwitchData.charAt(1)=='1'){
                    showStateUp[7]=1;
                }
            }
            if(strChkFlr.equals("39")){
                if(binSwitchData.charAt(0)=='0'){
                    showStateDown[6]=0;
                }
                else if(binSwitchData.charAt(0)=='1'){
                    showStateDown[6]=1;
                }
                if(binSwitchData.charAt(1)=='0'){
                    showStateUp[6]=0;
                }
                else if(binSwitchData.charAt(1)=='1'){
                    showStateUp[6]=1;
                }
            }
            if(strChkFlr.equals("3a")){
                if(binSwitchData.charAt(0)=='0'){
                    showStateDown[5]=0;
                }
                else if(binSwitchData.charAt(0)=='1'){
                    showStateDown[5]=1;
                }
                if(binSwitchData.charAt(1)=='0'){
                    showStateUp[5]=0;
                }
                else if(binSwitchData.charAt(1)=='1'){
                    showStateUp[5]=1;
                }
            }
            if(strChkFlr.equals("3b")){
                if(binSwitchData.charAt(0)=='0'){
                    showStateDown[4]=0;
                }
                else if(binSwitchData.charAt(0)=='1'){
                    showStateDown[4]=1;
                }
                if(binSwitchData.charAt(1)=='0'){
                    showStateUp[4]=0;
                }
                else if(binSwitchData.charAt(1)=='1'){
                    showStateUp[4]=1;
                }
            }
            if(strChkFlr.equals("3c")){
                if(binSwitchData.charAt(0)=='0'){
                    showStateDown[3]=0;
                }
                else if(binSwitchData.charAt(0)=='1'){
                    showStateDown[3]=1;
                }
                if(binSwitchData.charAt(1)=='0'){
                    showStateUp[3]=0;
                }
                else if(binSwitchData.charAt(1)=='1'){
                    showStateUp[3]=1;
                }
            }
            if(strChkFlr.equals("3d")){
                if(binSwitchData.charAt(0)=='0'){
                    showStateDown[2]=0;
                }
                else if(binSwitchData.charAt(0)=='1'){
                    showStateDown[2]=1;
                }
                if(binSwitchData.charAt(1)=='0'){
                    showStateUp[2]=0;
                }
                else if(binSwitchData.charAt(1)=='1'){
                    showStateUp[2]=1;
                }
            }
            if(strChkFlr.equals("3e")){
                if(binSwitchData.charAt(0)=='0'){
                    showStateDown[1]=0;
                }
                else if(binSwitchData.charAt(0)=='1'){
                    showStateDown[1]=1;
                }
                if(binSwitchData.charAt(1)=='0'){
                    showStateUp[1]=0;
                }
                else if(binSwitchData.charAt(1)=='1'){
                    showStateUp[1]=1;
                }
            }
            if(strChkFlr.equals("3f")){
                if(binSwitchData.charAt(0)=='0'){
                    showStateDown[0]=0;
                }
                else if(binSwitchData.charAt(0)=='1'){
                    showStateDown[0]=1;
                }
                if(binSwitchData.charAt(1)=='0'){
                    showStateUp[0]=0;
                }
                else if(binSwitchData.charAt(1)=='1'){
                    showStateUp[0]=1;
                }
            }
            adapter.notifyDataSetChanged();

            /*String hexUpDnCalls = String.format("%04x", (int) strUpDn.charAt(index + 3));
            String strUpDnCalls = Utils.hexToBin(hexUpDnCalls);
            String floorNo = String.format("%04x", (int) strUpDn.charAt(index - 2));
            int flrNo = Integer.parseInt(floorNo) - 30;*/

            /*CarCallAdapter adapter = new CarCallAdapter(mContext, strUpDnCalls, flrNo,(CarCallAdapter.CarCallIndicatorSignalListner) this);
            listFloorsIndicator.setAdapter(adapter);*/
        }catch (Exception e){
            e.printStackTrace();
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
}
