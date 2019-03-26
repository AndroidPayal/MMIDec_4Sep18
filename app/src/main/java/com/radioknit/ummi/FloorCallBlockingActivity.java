package com.radioknit.ummi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.radioknit.ummi.MainActivity.count_loader_FlrCallBlocking;
import static com.radioknit.ummi.MainActivity.isConnected;
import static com.radioknit.ummi.MainActivity.sendMessage;
import static com.radioknit.ummi.MainActivity.str11FlrClBlk;

public class FloorCallBlockingActivity extends AppCompatActivity {

    private static final String TAG = "FloorCallBlocking";
    private String connectedDeviceName;
    ArrayList<String> arrCommandValueList;
    private Spinner spinSelectFloor;
    private ArrayAdapter<String> adapter;
    private Button btnSetFloorCallBlocking;
    private RadioGroup rdoGropFloorCallBlocking;
    private Button btnViewFloorCallBlocking;
    int counter = 0;
    private LinearLayout llFloorBlockingData;
    private LinearLayout llSetFloorCallBlockignData;
    private RelativeLayout rlViewFloorCallBlockingData;
    private Button btnSetValues;
    private Button btnViewValues;
    private Context mContext;
    private ProgressDialog pd;
    CheckBox checkBoxHuData[] = new CheckBox[32], checkBoxHdData[] = new CheckBox[32], checkBoxCcData[] = new CheckBox[32];
    final Handler myHandlerChk = new Handler();
    private StringBuffer completReceivedString;
    private Menu menu;
    private Spinner spinSelectCallBlock;
    private Button btnViewSingleFloorCall;
    private TextView txtViewValue;
    private int cntSetText = 0;
    int spinFlr = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_floor_call_blocking);
        generateId();
        createObj();
        registerEvent();
        myHandlerChk.postDelayed(checkDataContinue, 0);

    }


    private void generateId() {
        btnSetFloorCallBlocking = (Button) findViewById(R.id.btnSetFloorCallBlocking);
        btnViewFloorCallBlocking = (Button) findViewById(R.id.btnViewFloorCallBlocking);
        spinSelectFloor = (Spinner) findViewById(R.id.spinSeclectFloorNumber);
        //rdoGropFloorCallBlocking = (RadioGroup) findViewById(R.id.rdogpFloorCallBlocking);
        llFloorBlockingData = (LinearLayout) findViewById(R.id.llFloorBlockingData);
        llSetFloorCallBlockignData = (LinearLayout) findViewById(R.id.llSetFlooorCallBlockingData);
        rlViewFloorCallBlockingData = (RelativeLayout) findViewById(R.id.rlFloorCallBlockingViewData);
        btnSetValues = (Button) findViewById(R.id.btnFloorCallBlockingSetValues);
        btnViewValues = (Button) findViewById(R.id.btnFloorCallBlockingViewValues);
        spinSelectCallBlock = (Spinner)findViewById(R.id.spinSelectCallBlock);
        btnViewSingleFloorCall = (Button)findViewById(R.id.btnViewSingleFloorCall);
        txtViewValue = (TextView)findViewById(R.id.textViewValue);
    }


    private void createObj() {
        getSupportActionBar().setTitle("Floor Call Blocking");
        mContext = FloorCallBlockingActivity.this;
        arrCommandValueList = new ArrayList<String>();


        for (int i = 0; i <= 31; i++) {
            arrCommandValueList.add(String.valueOf(i));
        }

        adapter = new ArrayAdapter<String>(mContext, R.layout.list_item, arrCommandValueList);

        spinSelectFloor.setAdapter(adapter);

        for (int flrNo = 0; flrNo <= 31; flrNo++) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View view = inflater.inflate(R.layout.floor_call_blocking_item, null);
            TextView tvFloorNo = (TextView) view.findViewById(R.id.tvfloor_blocking_FloorNumber);
            CheckBox chkBoxHu = (CheckBox) view.findViewById(R.id.checkBoxHU);
            CheckBox chkBoxHd = (CheckBox) view.findViewById(R.id.checkBoxHD);
            CheckBox chkBoxCc = (CheckBox) view.findViewById(R.id.checkBoxCC);

            checkBoxHuData[flrNo] = chkBoxHu;
            checkBoxHdData[flrNo] = chkBoxHd;
            checkBoxCcData[flrNo] = chkBoxCc;
            tvFloorNo.setText("" + flrNo);
            llFloorBlockingData.addView(view);
        }
    }


    void delay() {
        try {
            Thread.sleep(50);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registerEvent() {
        final Handler ha = new Handler();
        btnSetFloorCallBlocking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callStopDelay();
            }
        });

        btnSetValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llSetFloorCallBlockignData.setVisibility(View.VISIBLE);
                rlViewFloorCallBlockingData.setVisibility(View.GONE);
            }
        });

        btnViewValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llSetFloorCallBlockignData.setVisibility(View.GONE);
                rlViewFloorCallBlockingData.setVisibility(View.VISIBLE);
            }
        });


        btnViewSingleFloorCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callViewSingleFloorCallBlock();
            }
        });

        btnViewFloorCallBlocking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //llFloorBlockingData.removeAllViews();
                //completReceivedString.setLength(0);
                counter = 0;
                if (isConnected()) {
                    pd = ProgressDialog.show(mContext, "", "Please wait", true);
                    boolean b = ha.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //call function
                            if (counter == 0) {
                                callViewCallBlocking(counter);
                                delay();
                                counter++;
                            } else if (counter == 1) {
                                callViewCallBlocking(counter);
                                delay();
                                counter++;
                            } else if (counter == 2) {
                                callViewCallBlocking(counter);
                                delay();
                                counter++;
                            } else if (counter == 3) {
                                callViewCallBlocking(counter);
                                delay();
                                counter++;
                            } else if (counter == 4) {
                                callViewCallBlocking(counter);
                                delay();
                                counter++;
                            } else if (counter == 5) {
                                callViewCallBlocking(counter);
                                delay();
                                counter++;
                            } else if (counter == 6) {
                                callViewCallBlocking(counter);
                                delay();
                                counter++;
                            } else if (counter == 7) {
                                callViewCallBlocking(counter);
                                delay();
                                counter++;
                            } else if (counter == 8) {
                                callViewCallBlocking(counter);
                                delay();
                                counter++;
                            } else if (counter == 9) {
                                callViewCallBlocking(counter);
                                delay();
                                counter++;
                            } else if (counter == 10) {
                                callViewCallBlocking(counter);
                                delay();
                                counter++;
                            } else if (counter == 11) {
                                callViewCallBlocking(counter);
                                delay();
                                counter++;
                            } else if (counter == 12) {
                                callViewCallBlocking(counter);
                                delay();
                                counter++;
                            } else if (counter == 13) {
                                callViewCallBlocking(counter);
                                delay();
                                counter++;
                            } else if (counter == 14) {
                                callViewCallBlocking(counter);
                                delay();
                                counter++;
                            } else if (counter == 15) {
                                callViewCallBlocking(counter);
                                delay();
                                counter++;
                            } else if (counter == 16) {
                                callViewCallBlocking(counter);
                                delay();
                                counter++;
                            } else if (counter == 17) {
                                callViewCallBlocking(counter);
                                delay();
                                counter++;
                            } else if (counter == 18) {
                                callViewCallBlocking(counter);
                                delay();
                                counter++;
                            } else if (counter == 19) {
                                callViewCallBlocking(counter);
                                delay();
                                counter++;
                            } else if (counter == 20) {
                                callViewCallBlocking(counter);
                                delay();
                                counter++;
                            } else if (counter == 21) {
                                callViewCallBlocking(counter);
                                delay();
                                counter++;
                            } else if (counter == 22) {
                                callViewCallBlocking(counter);
                                delay();
                                counter++;
                            } else if (counter == 23) {
                                callViewCallBlocking(counter);
                                delay();
                                counter++;
                            } else if (counter == 24) {
                                callViewCallBlocking(counter);
                                delay();
                                counter++;
                            } else if (counter == 25) {
                                callViewCallBlocking(counter);
                                delay();
                                counter++;
                            } else if (counter == 26) {
                                callViewCallBlocking(counter);
                                delay();
                                counter++;
                            } else if (counter == 27) {
                                callViewCallBlocking(counter);
                                delay();
                                counter++;
                            } else if (counter == 28) {
                                callViewCallBlocking(counter);
                                delay();
                                counter++;
                            } else if (counter == 29) {
                                callViewCallBlocking(counter);
                                delay();
                                counter++;
                            } else if (counter == 30) {
                                callViewCallBlocking(counter);
                                delay();
                                counter++;
                            } else if (counter == 31) {
                                callViewCallBlocking(counter);
                                delay();
                                counter++;
                            } else if (counter == 32) {
                                //counter++;
                                if (isConnected()){
                                    //pd.dismiss();
                                    Log.d("Tag_speed", "run: count = "+count_loader_FlrCallBlocking);
                                    //todo change payal
                                    int timeout = 0;
                                    while (true) {
                                        if (count_loader_FlrCallBlocking == 32) {
                                            if (pd.isShowing())
                                                pd.dismiss();
                                            break;
                                        } else {
                                            if (timeout == 3){
                                                Log.d("Tag_counter", "run: timeout = "+timeout);
                                                Toast.makeText(mContext, "TimeOut! Device is slow", Toast.LENGTH_SHORT).show();
                                                if (pd.isShowing())
                                                    pd.dismiss();
                                                break;
                                            }
                                            delay();
                                            timeout ++;
                                        }
                                    }

                                }

                                //if(completReceivedString.toString().contains("11250bf")){
                                showReceivedDataNew();
                                counter++;
                                //}

                            }
                            ha.postDelayed(this, 500);
                        }
                    }, 500);
                } else {
                    Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
                }
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

       /* switch (rdoGropFloorCallBlocking.getCheckedRadioButtonId()) {
            case R.id.rdoBtnNoCallBlocking:
                a5 = 00;
                break;
            case R.id.rdoBtnOnlyCarCallBlock:
                a5 = 01;
                break;

            case R.id.rdoBtnHallUpCallBlock:
                a5 = 02;
                break;
            case R.id.rdoBtnCarPlusHalllUpCallBlock:
                a5 = 03;
                break;

            case R.id.rdoBtnHallDownCallBlock:
                a5 = 04;
                break;
            case R.id.rdoBtnCarPlusHallDownCallBlock:
                a5 = 05;
                break;
            case R.id.rdoBtnHallUpPlusDownCallsBlock:
                a5 = 06;
                break;

            case R.id.rdoBtnCarPlusHallUpPlusHallDownCallBlock:
                a5 = 07;
                break;
        }*/

        int valCallBlock = spinSelectCallBlock.getSelectedItemPosition();

        switch (valCallBlock){
            case 0:
                a5 = 00;
                break;
            case 1:
                a5 = 01;
                break;
            case 2:
                a5 = 02;
                break;
            case 3:
                a5 = 03;
                break;
            case 4:
                a5 = 04;
                break;
            case 5:
                a5 = 05;
                break;
            case 6:
                a5 = 07;
                break;
            case 7:
                a5 = 06;
                break;

        }

        a4 = 160 + Integer.parseInt(spinSelectFloor.getSelectedItem().toString());
        int[] sendValChkSum = {a1, a2, a3, a4, a5, a6};
        String strChkSum = CalculateCheckSum.calculateChkSum(sendValChkSum);
        String asciiString = String.format("%04x", a1).substring(2, 4) + String.format("%04x", a2).substring(2, 4) + String.format("%04x", a3).substring(2, 4) + String.format("%04x", a4).substring(2, 4) + String.format("%04x", a5).substring(2, 4) + String.format("%04x", a6).substring(2, 4);
        asciiString = asciiString + strChkSum + "\r";
      //  Log.e(TAG, "asciiString = " + asciiString);

        if (isConnected()) {
            //connector.write(asciiString.getBytes());
            sendMessage(asciiString.getBytes());
        } else {
            Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
        }

    }

    private void callViewCallBlocking(int i) {
        int a1 = 18;
        int a2 = 17;
        int a3 = 80;
        int a4;
        int a5 = 00;
        int a6 = 00;

        a4 = 160 + i;
        int[] sendValChkSum = {a1, a2, a3, a4, a5, a6};

        String strChkSum = CalculateCheckSum.calculateChkSum(sendValChkSum);
        /*int sum = a1 + a2 + a3 + a4 + a5 + a6 ;
        String sumHex = String.format("%04x", sum);

        String msb = sumHex.substring(1, 2);
        String lsb = sumHex.substring(2, 4);

        int a7 = Integer.parseInt(lsb, 16);
        int msb2 = (Integer.parseInt(msb) | 50);
        int a8 = Integer.parseInt(String.valueOf(msb2),16);

        byte[] br2 = {(byte) a1, (byte) a2, (byte) a3, (byte) a4, (byte) a5, (byte) a6};
        byte[] br1 = {(byte) a1, (byte) a2, (byte) a3, (byte) a4, (byte) a5, (byte) a6, (byte) a7, (byte) a8};*/

        String asciiString = String.format("%04x", a1).substring(2, 4) + String.format("%04x", a2).substring(2, 4) + String.format("%04x", a3).substring(2, 4) + String.format("%04x", a4).substring(2, 4) + String.format("%04x", a5).substring(2, 4) + String.format("%04x", a6).substring(2, 4);
      /*  int sumSendString  = 0;
        for(int j = 0; j<asciiString.length(); j++){
            sumSendString = sumSendString + Integer.parseInt(String.format("%04x", (int) asciiString.charAt(j)).substring(2,4));
        }
        asciiString = asciiString +String.valueOf(sumSendString).substring(1,3)+ "\r";*/
        asciiString = asciiString + strChkSum + "\r";
      //  Log.e(TAG, "asciiString = " + asciiString);

        if (isConnected()) {
//            connector.write(br1);
            //connector.write(asciiString.getBytes());
            sendMessage(asciiString.getBytes());
        } else {
            Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
        }

    }


    void callViewSingleFloorCallBlock(){
        cntSetText = 0;
        int floorNo = spinSelectFloor.getSelectedItemPosition();

        int a1 = 18;
        int a2 = 17;
        int a3 = 80;
        int a4;
        int a5 = 00;
        int a6 = 00;

        a4 = 160 + floorNo;

        int[] sendValChkSum={a1, a2, a3, a4, a5, a6};

        String asciiString  = String.format("%04x", a1).substring(2,4)+String.format("%04x", a2).substring(2,4)+String.format("%04x", a3).substring(2,4)+String.format("%04x", a4).substring(2,4)+String.format("%04x", a5).substring(2,4)+String.format("%04x", a6).substring(2,4) ;
        String strChkSum= CalculateCheckSum.calculateChkSum(sendValChkSum);
        asciiString = asciiString + strChkSum + "\r";

     //   Log.e(TAG, "asciiString = "+ asciiString);

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

        if(!str11FlrClBlk[flrNo].equals("")){
            String[] strBlock = getResources().getStringArray(R.array.arr_flr_call_blocking);
            switch (str11FlrClBlk[flrNo]){
                case "0":
                    txtViewValue.setText(strBlock[0]);
                    break;
                case "1":
                    txtViewValue.setText(strBlock[1]);
                    break;
                case "2":
                    txtViewValue.setText(strBlock[2]);
                    break;
                case "3":
                    txtViewValue.setText(strBlock[3]);
                    break;
                case "4":
                    txtViewValue.setText(strBlock[4]);
                    break;
                case "5":
                    txtViewValue.setText(strBlock[5]);
                    break;
                case "6":
                    txtViewValue.setText(strBlock[7]);
                    break;
                case "7":
                    txtViewValue.setText(strBlock[6]);
                    break;

            }
            //txtViewValue.setText(str11DisFlr[flrNo]);
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
          /*  case R.id.menu_search:
                final int REQUEST_CONNECT_DEVICE = 2;
                Intent serverIntent = null;
                serverIntent = new Intent(FloorCallBlockingActivity.this, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
                return true;*/
            case R.id.wroteModeEnable:
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
            //showReceivedDataNew();
            myHandlerChk.postDelayed(this, 0);
        }

    };


    public void showReceivedDataNew() {

        if (!str11FlrClBlk[0].equals("")) {
            checkBoxHuData[0].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxHdData[0].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxCcData[0].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            getDiscriptionFromCode(str11FlrClBlk[0],checkBoxHuData[0],checkBoxHdData[0],checkBoxCcData[0]);
        }
        if (!str11FlrClBlk[1].equals("")) {
            checkBoxHuData[1].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxHdData[1].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxCcData[1].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            getDiscriptionFromCode(str11FlrClBlk[1],checkBoxHuData[1],checkBoxHdData[1],checkBoxCcData[1]);
        }
        if (!str11FlrClBlk[2].equals("")) {
            checkBoxHuData[2].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxHdData[2].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxCcData[2].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            getDiscriptionFromCode(str11FlrClBlk[2],checkBoxHuData[2],checkBoxHdData[2],checkBoxCcData[2]);
        }
        if (!str11FlrClBlk[3].equals("")) {
            checkBoxHuData[3].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxHdData[3].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxCcData[3].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            getDiscriptionFromCode(str11FlrClBlk[3],checkBoxHuData[3],checkBoxHdData[3],checkBoxCcData[3]);
        }
        if (!str11FlrClBlk[4].equals("")) {
            checkBoxHuData[4].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxHdData[4].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxCcData[4].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            getDiscriptionFromCode(str11FlrClBlk[4],checkBoxHuData[4],checkBoxHdData[4],checkBoxCcData[4]);
        }
        if (!str11FlrClBlk[5].equals("")) {
            checkBoxHuData[5].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxHdData[5].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxCcData[5].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            getDiscriptionFromCode(str11FlrClBlk[5],checkBoxHuData[5],checkBoxHdData[5],checkBoxCcData[5]);
        }
        if (!str11FlrClBlk[6].equals("")) {
            checkBoxHuData[6].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxHdData[6].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxCcData[6].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            getDiscriptionFromCode(str11FlrClBlk[6],checkBoxHuData[6],checkBoxHdData[6],checkBoxCcData[6]);
        }
        if (!str11FlrClBlk[7].equals("")) {
            checkBoxHuData[7].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxHdData[7].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxCcData[7].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            getDiscriptionFromCode(str11FlrClBlk[7],checkBoxHuData[7],checkBoxHdData[7],checkBoxCcData[7]);
        }
        if (!str11FlrClBlk[8].equals("")) {
            checkBoxHuData[8].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxHdData[8].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxCcData[8].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            getDiscriptionFromCode(str11FlrClBlk[8],checkBoxHuData[8],checkBoxHdData[8],checkBoxCcData[8]);
        }
        if (!str11FlrClBlk[9].equals("")) {
            checkBoxHuData[9].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxHdData[9].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxCcData[9].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            getDiscriptionFromCode(str11FlrClBlk[9],checkBoxHuData[9],checkBoxHdData[9],checkBoxCcData[9]);
        }
        if (!str11FlrClBlk[10].equals("")) {
            checkBoxHuData[10].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxHdData[10].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxCcData[10].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            getDiscriptionFromCode(str11FlrClBlk[10],checkBoxHuData[10],checkBoxHdData[10],checkBoxCcData[10]);
        }
        if (!str11FlrClBlk[11].equals("")) {
            checkBoxHuData[11].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxHdData[11].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxCcData[11].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            getDiscriptionFromCode(str11FlrClBlk[11],checkBoxHuData[11],checkBoxHdData[11],checkBoxCcData[11]);
        }
        if (!str11FlrClBlk[12].equals("")) {
            checkBoxHuData[12].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxHdData[12].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxCcData[12].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            getDiscriptionFromCode(str11FlrClBlk[12],checkBoxHuData[12],checkBoxHdData[12],checkBoxCcData[12]);
        }
        if (!str11FlrClBlk[13].equals("")) {
            checkBoxHuData[13].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxHdData[13].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxCcData[13].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            getDiscriptionFromCode(str11FlrClBlk[13],checkBoxHuData[13],checkBoxHdData[13],checkBoxCcData[13]);
        }
        if (!str11FlrClBlk[14].equals("")) {
            checkBoxHuData[14].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxHdData[14].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxCcData[14].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            getDiscriptionFromCode(str11FlrClBlk[14],checkBoxHuData[14],checkBoxHdData[14],checkBoxCcData[14]);
        }
        if (!str11FlrClBlk[15].equals("")) {
            checkBoxHuData[15].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxHdData[15].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxCcData[15].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            getDiscriptionFromCode(str11FlrClBlk[15],checkBoxHuData[15],checkBoxHdData[15],checkBoxCcData[15]);
        }

        if (!str11FlrClBlk[16].equals("")) {
            checkBoxHuData[16].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxHdData[16].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxCcData[16].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            getDiscriptionFromCode(str11FlrClBlk[16],checkBoxHuData[16],checkBoxHdData[16],checkBoxCcData[16]);
        }
        if (!str11FlrClBlk[17].equals("")) {
            checkBoxHuData[17].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxHdData[17].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxCcData[17].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            getDiscriptionFromCode(str11FlrClBlk[17],checkBoxHuData[17],checkBoxHdData[17],checkBoxCcData[17]);
        }
        if (!str11FlrClBlk[18].equals("")) {
            checkBoxHuData[18].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxHdData[18].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxCcData[18].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            getDiscriptionFromCode(str11FlrClBlk[18],checkBoxHuData[18],checkBoxHdData[18],checkBoxCcData[18]);
        }
        if (!str11FlrClBlk[19].equals("")) {
            checkBoxHuData[19].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxHdData[19].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxCcData[19].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            getDiscriptionFromCode(str11FlrClBlk[19],checkBoxHuData[19],checkBoxHdData[19],checkBoxCcData[19]);
        }
        if (!str11FlrClBlk[20].equals("")) {
            checkBoxHuData[20].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxHdData[20].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxCcData[20].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            getDiscriptionFromCode(str11FlrClBlk[20],checkBoxHuData[20],checkBoxHdData[20],checkBoxCcData[20]);
        }
        if (!str11FlrClBlk[21].equals("")) {
            checkBoxHuData[21].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxHdData[21].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxCcData[21].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            getDiscriptionFromCode(str11FlrClBlk[21],checkBoxHuData[21],checkBoxHdData[21],checkBoxCcData[21]);
        }
        if (!str11FlrClBlk[22].equals("")) {
            checkBoxHuData[22].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxHdData[22].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxCcData[22].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            getDiscriptionFromCode(str11FlrClBlk[22],checkBoxHuData[22],checkBoxHdData[22],checkBoxCcData[22]);
        }
        if (!str11FlrClBlk[23].equals("")) {
            checkBoxHuData[23].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxHdData[23].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxCcData[23].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            getDiscriptionFromCode(str11FlrClBlk[23],checkBoxHuData[23],checkBoxHdData[23],checkBoxCcData[23]);
        }
        if (!str11FlrClBlk[24].equals("")) {
            checkBoxHuData[24].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxHdData[24].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxCcData[24].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            getDiscriptionFromCode(str11FlrClBlk[24],checkBoxHuData[24],checkBoxHdData[24],checkBoxCcData[24]);
        }
        if (!str11FlrClBlk[25].equals("")) {
            checkBoxHuData[25].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxHdData[25].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxCcData[25].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            getDiscriptionFromCode(str11FlrClBlk[25],checkBoxHuData[25],checkBoxHdData[25],checkBoxCcData[25]);
        }
        if (!str11FlrClBlk[26].equals("")) {
            checkBoxHuData[26].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxHdData[26].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxCcData[26].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            getDiscriptionFromCode(str11FlrClBlk[26],checkBoxHuData[26],checkBoxHdData[26],checkBoxCcData[26]);
        }
        if (!str11FlrClBlk[27].equals("")) {
            checkBoxHuData[27].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxHdData[27].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxCcData[27].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            getDiscriptionFromCode(str11FlrClBlk[27],checkBoxHuData[27],checkBoxHdData[27],checkBoxCcData[27]);
        }
        if (!str11FlrClBlk[28].equals("")) {
            checkBoxHuData[28].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxHdData[28].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxCcData[28].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            getDiscriptionFromCode(str11FlrClBlk[28],checkBoxHuData[28],checkBoxHdData[28],checkBoxCcData[28]);
        }
        if (!str11FlrClBlk[29].equals("")) {
            checkBoxHuData[29].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxHdData[29].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxCcData[29].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            getDiscriptionFromCode(str11FlrClBlk[29],checkBoxHuData[29],checkBoxHdData[29],checkBoxCcData[29]);
        }
        if (!str11FlrClBlk[30].equals("")) {
            checkBoxHuData[30].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxHdData[30].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxCcData[30].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            getDiscriptionFromCode(str11FlrClBlk[30],checkBoxHuData[30],checkBoxHdData[30],checkBoxCcData[30]);
        }
        if (!str11FlrClBlk[31].equals("")) {
            checkBoxHuData[31].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxHdData[31].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            checkBoxCcData[31].setButtonDrawable(R.drawable.custom_checkbox_flrblk);
            getDiscriptionFromCode(str11FlrClBlk[31],checkBoxHuData[31],checkBoxHdData[31],checkBoxCcData[31]);
        }
       /* String receivedString = new String(completReceivedString);
        Log.e(TAG, "ShowReceivedData"+receivedString);
        try {
            Log.e(TAG, "receivedString lenght = "+ receivedString.length());
            if(receivedString.contains("\r")) {
                Log.e(TAG, "contains true ");
            }

            if(Utils.isStringNotNull(receivedString)) {
                while (receivedString.length() >= 14) {
                    int index0D = receivedString.indexOf("\r");

                    Log.e(TAG, "indexOD = " + index0D);

                    String temp = receivedString.substring(index0D - 14, index0D);
                    Log.e(TAG, "temp = " + temp);
                    if (temp.startsWith("111250")) {
                        String sum = Utils.calculateChecksumValueNew(temp);
                        Log.e(TAG, "" + sum.substring(2, 4) + " -- " + temp.substring(temp.length() - 2, temp.length()) + " temp = " + temp);

                        if (sum.substring(2, 4).equalsIgnoreCase(temp.substring(temp.length() - 2, temp.length()))) {
                            String locationAddress = temp.substring(6, 8);

                            int data = Integer.parseInt(temp.substring(8, 10), 16);

                            Log.e(TAG, "locationAddress = " + locationAddress + " data = " + data);
//                        Utils.showToastMsg(getActivity(), " Data = "+data +" char =  "+ temp.charAt(index - 1));

                            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            final View view = inflater.inflate(R.layout.floor_call_blocking_item, null);
                            TextView tvFloorNo = (TextView) view.findViewById(R.id.tvfloor_blocking_FloorNumber);
                            TextView tvFloorData = (TextView) view.findViewById(R.id.tvfloor_blocking_Data);
                             checkBoxHu = (CheckBox) view.findViewById(R.id.checkBoxHU);
                             checkBoxHd = (CheckBox) view.findViewById(R.id.checkBoxHD);
                             checkBoxCc = (CheckBox) view.findViewById(R.id.checkBoxCC);

                            if (locationAddress.equalsIgnoreCase("A0")) {

                                tvFloorNo.setText("" + 0);
                                getDiscriptionFromCode(data);
                                //tvFloorData.setText("" + getDiscriptionFromCode(data));
                                llFloorBlockingData.addView(view);
                            } else if (locationAddress.equalsIgnoreCase("A1")) {

                                tvFloorNo.setText("" + 1);
                                getDiscriptionFromCode(data);
                                //tvFloorData.setText("" + getDiscriptionFromCode(data));
                                llFloorBlockingData.addView(view);
                            } else if (locationAddress.equalsIgnoreCase("A2")) {

                                tvFloorNo.setText("" + 2);
                                getDiscriptionFromCode(data);
                                //tvFloorData.setText("" + getDiscriptionFromCode(data));
                                llFloorBlockingData.addView(view);
                            } else if (locationAddress.equalsIgnoreCase("A3")) {

                                tvFloorNo.setText("" + 3);
                                getDiscriptionFromCode(data);
                                //tvFloorData.setText("" + getDiscriptionFromCode(data));
                                llFloorBlockingData.addView(view);
                            } else if (locationAddress.equalsIgnoreCase("A4")) {

                                tvFloorNo.setText("" + 4);
                                getDiscriptionFromCode(data);
                                //tvFloorData.setText("" + getDiscriptionFromCode(data));
                                llFloorBlockingData.addView(view);
                            } else if (locationAddress.equalsIgnoreCase("A5")) {
                                tvFloorNo.setText("" + 5);
                                getDiscriptionFromCode(data);
                                //tvFloorData.setText("" + getDiscriptionFromCode(data));
                                llFloorBlockingData.addView(view);
                            } else if (locationAddress.equalsIgnoreCase("A6")) {

                                tvFloorNo.setText("" + 6);
                                getDiscriptionFromCode(data);
                                //tvFloorData.setText("" + getDiscriptionFromCode(data));
                                llFloorBlockingData.addView(view);
                            } else if (locationAddress.equalsIgnoreCase("A7")) {

                                tvFloorNo.setText("" + 7);
                                getDiscriptionFromCode(data);
                                //tvFloorData.setText("" + getDiscriptionFromCode(data));
                                llFloorBlockingData.addView(view);
                            } else if (locationAddress.equalsIgnoreCase("A8")) {

                                tvFloorNo.setText("" + 8);
                                getDiscriptionFromCode(data);
                                //tvFloorData.setText("" + getDiscriptionFromCode(data));
                                llFloorBlockingData.addView(view);
                            } else if (locationAddress.equalsIgnoreCase("A9")) {

                                tvFloorNo.setText("" + 9);
                                getDiscriptionFromCode(data);
                                //tvFloorData.setText("" + getDiscriptionFromCode(data));
                                llFloorBlockingData.addView(view);
                            } else if (locationAddress.equalsIgnoreCase("AA")) {

                                tvFloorNo.setText("" + 10);
                                getDiscriptionFromCode(data);
                                //tvFloorData.setText("" + getDiscriptionFromCode(data));
                                llFloorBlockingData.addView(view);
                            } else if (locationAddress.equalsIgnoreCase("AB")) {

                                tvFloorNo.setText("" + 11);
                                getDiscriptionFromCode(data);
                                //tvFloorData.setText("" + getDiscriptionFromCode(data));
                                llFloorBlockingData.addView(view);
                            } else if (locationAddress.equalsIgnoreCase("AC")) {

                                tvFloorNo.setText("" + 12);
                                getDiscriptionFromCode(data);
                                //tvFloorData.setText("" + getDiscriptionFromCode(data));
                                llFloorBlockingData.addView(view);
                            } else if (locationAddress.equalsIgnoreCase("AD")) {

                                tvFloorNo.setText("" + 13);
                                getDiscriptionFromCode(data);
                                //tvFloorData.setText("" + getDiscriptionFromCode(data));
                                llFloorBlockingData.addView(view);
                            } else if (locationAddress.equalsIgnoreCase("AE")) {

                                tvFloorNo.setText("" + 14);
                                getDiscriptionFromCode(data);
                                //tvFloorData.setText("" + getDiscriptionFromCode(data));
                                llFloorBlockingData.addView(view);
                            } else if (locationAddress.equalsIgnoreCase("AF")) {

                                tvFloorNo.setText("" + 15);
                                getDiscriptionFromCode(data);
                                //tvFloorData.setText("" + getDiscriptionFromCode(data));
                                llFloorBlockingData.addView(view);
                            } else if (locationAddress.equalsIgnoreCase("B0")) {

                                tvFloorNo.setText("" + 16);
                                getDiscriptionFromCode(data);
                                //tvFloorData.setText("" + getDiscriptionFromCode(data));
                                llFloorBlockingData.addView(view);
                            } else if (locationAddress.equalsIgnoreCase("B1")) {

                                tvFloorNo.setText("" + 17);
                                getDiscriptionFromCode(data);
                                //tvFloorData.setText("" + getDiscriptionFromCode(data));
                                llFloorBlockingData.addView(view);
                            } else if (locationAddress.equalsIgnoreCase("B2")) {

                                tvFloorNo.setText("" + 18);
                                getDiscriptionFromCode(data);
                                //tvFloorData.setText("" + getDiscriptionFromCode(data));
                                llFloorBlockingData.addView(view);

                            } else if (locationAddress.equalsIgnoreCase("B3")) {

                                tvFloorNo.setText("" + 19);
                                getDiscriptionFromCode(data);
                                //tvFloorData.setText("" + getDiscriptionFromCode(data));
                                llFloorBlockingData.addView(view);
                            } else if (locationAddress.equalsIgnoreCase("B4")) {

                                tvFloorNo.setText("" + 20);
                                getDiscriptionFromCode(data);
                                //tvFloorData.setText("" + getDiscriptionFromCode(data));
                                llFloorBlockingData.addView(view);
                            } else if (locationAddress.equalsIgnoreCase("B5")) {

                                tvFloorNo.setText("" + 21);
                                getDiscriptionFromCode(data);
                                //tvFloorData.setText("" + getDiscriptionFromCode(data));
                                llFloorBlockingData.addView(view);
                            } else if (locationAddress.equalsIgnoreCase("B6")) {

                                tvFloorNo.setText("" + 22);
                                getDiscriptionFromCode(data);
                                //tvFloorData.setText("" + getDiscriptionFromCode(data));
                                llFloorBlockingData.addView(view);
                            } else if (locationAddress.equalsIgnoreCase("B7")) {

                                tvFloorNo.setText("" + 23);
                                getDiscriptionFromCode(data);
                                //tvFloorData.setText("" + getDiscriptionFromCode(data));
                                llFloorBlockingData.addView(view);
                            } else if (locationAddress.equalsIgnoreCase("B8")) {

                                tvFloorNo.setText("" + 24);
                                getDiscriptionFromCode(data);
                                //tvFloorData.setText("" + getDiscriptionFromCode(data));
                                llFloorBlockingData.addView(view);
                            } else if (locationAddress.equalsIgnoreCase("B9")) {

                                tvFloorNo.setText("" + 25);
                                getDiscriptionFromCode(data);
                                //tvFloorData.setText("" + getDiscriptionFromCode(data));
                                llFloorBlockingData.addView(view);
                            } else if (locationAddress.equalsIgnoreCase("BA")) {

                                tvFloorNo.setText("" + 26);
                                getDiscriptionFromCode(data);
                                //tvFloorData.setText("" + getDiscriptionFromCode(data));
                                llFloorBlockingData.addView(view);
                            } else if (locationAddress.equalsIgnoreCase("BB")) {

                                tvFloorNo.setText("" + 27);
                                getDiscriptionFromCode(data);
                                //tvFloorData.setText("" + getDiscriptionFromCode(data));
                                llFloorBlockingData.addView(view);
                            } else if (locationAddress.equalsIgnoreCase("BC")) {

                                tvFloorNo.setText("" + 28);
                                getDiscriptionFromCode(data);
                                //tvFloorData.setText("" + getDiscriptionFromCode(data));
                                llFloorBlockingData.addView(view);
                            } else if (locationAddress.equalsIgnoreCase("BD")) {

                                tvFloorNo.setText("" + 29);
                                getDiscriptionFromCode(data);
                                //tvFloorData.setText("" + getDiscriptionFromCode(data));
                                llFloorBlockingData.addView(view);
                            } else if (locationAddress.equalsIgnoreCase("BE")) {

                                tvFloorNo.setText("" + 30);
                                getDiscriptionFromCode(data);
                                //tvFloorData.setText("" + getDiscriptionFromCode(data));
                                llFloorBlockingData.addView(view);
                            } else if (locationAddress.equalsIgnoreCase("BF")) {

                                tvFloorNo.setText("" + 31);
                                getDiscriptionFromCode(data);
                                //tvFloorData.setText("" + getDiscriptionFromCode(data));
                                llFloorBlockingData.addView(view);
                            }
                            temp = "";

                        }
                        receivedString = receivedString.substring(index0D + 1, receivedString.length());
                        Log.e(TAG, "Sum ===== " + sum);
                    } else {
                        receivedString = receivedString.substring(index0D + 1, receivedString.length());
                    }

                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
*/
        }

    public void getDiscriptionFromCode(String code, CheckBox checkBoxHu, CheckBox checkBoxHd, CheckBox checkBoxCc){
        String discription = "";
        switch(code){
            case "0" :
                checkBoxHu.setChecked(true);
                checkBoxHd.setChecked(true);
                checkBoxCc.setChecked(true);
                break;
            case "1" :
                checkBoxHu.setChecked(true);
                checkBoxHd.setChecked(true);
                checkBoxCc.setChecked(false);
                break;
            case "2" :
                checkBoxHu.setChecked(false);
                checkBoxHd.setChecked(true);
                checkBoxCc.setChecked(true);
                break;
            case "3" :
                checkBoxHu.setChecked(false);
                checkBoxHd.setChecked(true);
                checkBoxCc.setChecked(false);
                break;
            case "4" :
                checkBoxHu.setChecked(true);
                checkBoxHd.setChecked(false);
                checkBoxCc.setChecked(true);
                break;
            case "5" :
                checkBoxHu.setChecked(true);
                checkBoxHd.setChecked(false);
                checkBoxCc.setChecked(false);
                break;
            case "6" :
                checkBoxHu.setChecked(false);
                checkBoxHd.setChecked(false);
                checkBoxCc.setChecked(true);
                break;
            case "7" :
                checkBoxHu.setChecked(false);
                checkBoxHd.setChecked(false);
                checkBoxCc.setChecked(false);
                break;
        }
        //return discription;
    }


}

