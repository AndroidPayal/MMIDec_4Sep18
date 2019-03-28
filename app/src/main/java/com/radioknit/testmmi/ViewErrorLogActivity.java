package com.radioknit.testmmi;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
/*
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.radioknit.mminewapp.Constance;
import com.radioknit.mminewapp.DeviceData;
import com.radioknit.mminewapp.bluetooth.DeviceConnector;
import com.radioknit.mminewapp.sharedpreference.TempSharedPreference;*/

import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static com.radioknit.testmmi.MainActivity.MESSAGE_DEVICE_NAME;
import static com.radioknit.testmmi.MainActivity.MESSAGE_READ;
import static com.radioknit.testmmi.MainActivity.MESSAGE_STATE_CHANGE;
import static com.radioknit.testmmi.MainActivity.isConnected;
import static com.radioknit.testmmi.MainActivity.sendMessage;

public class ViewErrorLogActivity extends AppCompatActivity {

    private static final String TAG = "Tag_ViewErrorLogAct";
    private Context mContext;
    ArrayList<String> arrCommandValueList;
    private ArrayAdapter<String> adapter;
    private EditText edtReceivedData;
    private Button btnViewErrorLog;
    private LinearLayout llErrors;
    private int counter;
    private BluetoothAdapter bluetoothAdapter;

    // =============

    private OutputStream outputStream;
    private static final String DEVICE_NAME = "DEVICE_NAME";
    private static final String LOG = "LOG";

    private static final SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm:ss.SSS");

    private static String MSG_NOT_CONNECTED;
    private static String MSG_CONNECTING;
    private static String MSG_CONNECTED;

    private static BluetoothChatService connector;
    private static ViewErrorLogActivity.BluetoothResponseHandler mHandler;
    DeviceListActivity deviceListActivity;

    private boolean hexMode, needClean;
    private boolean show_timings, show_direction;
    private String command_ending;
    private String deviceName;

    private ProgressDialog pd;
    private StringBuffer completReceivedString;
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   PreferenceManager.setDefaultValues(this, R.xml.settings_activity, false);

        if (mHandler == null) mHandler = new ViewErrorLogActivity.BluetoothResponseHandler(this);
        else mHandler.setTarget(this);

        MSG_NOT_CONNECTED = "msg_not_connected";
        MSG_CONNECTING = "msg_connecting";
        MSG_CONNECTED = "msg_connected";
        setContentView(R.layout.activity_view_error_log);

        completReceivedString = new StringBuffer();


        generateId();
        createObj();
        registerEvent();

        if (isConnected() && (savedInstanceState != null)) {
            setDeviceName(savedInstanceState.getString(DEVICE_NAME));
        } else getSupportActionBar().setSubtitle(MSG_NOT_CONNECTED);

    }

    void delay(){
        try {
            Thread.sleep(50);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registerEvent() {


        btnViewErrorLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completReceivedString.setLength(0);
                counter = 0;
                if (isConnected()) {
                pd = ProgressDialog.show(mContext,"","Please wait",true);

                boolean b = handler.postDelayed(checkDataContinue, 500);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    Runnable checkDataContinue = new Runnable() {
        @Override
        public void run() {

            //call function
            if(counter == 0){
                callError(counter);
                delay();
                counter++;
            }else if(counter == 1){
                callError(counter);
                delay();
                counter++;
            }else if(counter ==  2 ){
                callError(counter);
                delay();
                counter++;
            }else if(counter == 3){
                callError(counter);
                delay();
                counter++;
            }else if(counter == 4){
                callError(counter);
                delay();
                counter++;
            }else if(counter == 5){
                callError(counter);
                delay();
                counter++;
            }else if(counter == 6){
                callError(counter);
                delay();
                counter++;
            }else if(counter == 7){
                callError(counter);
                delay();
                counter++;
            }else if(counter == 8){
                callError(counter);
                delay();
                counter++;
            }else if(counter == 9){
                callError(counter);
                delay();
                counter++;
            }else if(counter == 10){
                counter++;
                if (pd.isShowing())
                    pd.dismiss();
                showReceivedDataNew();
            }
            handler.postDelayed(this, 500);

        }
    };
    private void createObj() {
        mContext = ViewErrorLogActivity.this;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        arrCommandValueList = new ArrayList<String>();

    }

    private void generateId() {
        btnViewErrorLog = (Button)  findViewById(R.id.btnViewErrorLog);
        llErrors = (LinearLayout) findViewById(R.id.llChildItems) ;
        edtReceivedData = (EditText) findViewById(R.id.edtReceivedData);

    }

    int a1 = 18;
    int a2 = 244;
    int a3;
    int a4 = 82;
    int a5 = 97;
    int a6 = 109;
//TODO : protocol sending request to view error stack
    public void callError(int errorNo) {

        Log.d(TAG, "callError: errorNo: "+errorNo);
        a3 = errorNo;
        int sum = a1 + a2 + a3 + a4 + a5 + a6;
        int[] sendValChkSum={a1, a2, a3, a4, a5, a6};

        String strChkSum= CalculateCheckSum.calculateChkSum(sendValChkSum);
       /* String sumHex = String.format("%04x", sum);

        String msb = sumHex.substring(1, 2);
        String lsb = sumHex.substring(2, 4);

        int a7 = Integer.parseInt(lsb, 16);
        int msb2 = Integer.parseInt(msb);
        int a8 = msb2 | 80;

        byte[] br2 = {(byte) a1, (byte) a2, (byte) a3, (byte) a4, (byte) a5, (byte) a6};
        byte[] br1 = {(byte) a1, (byte) a2, (byte) a3, (byte) a4, (byte) a5, (byte) a6, (byte) a7, (byte) a8};*/

        String asciiString  = String.format("%04x", a1).substring(2,4)+ String.format("%04x", a2).substring(2,4)+ String.format("%04x", a3).substring(2,4)+ String.format("%04x", a4).substring(2,4)+ String.format("%04x", a5).substring(2,4)+ String.format("%04x", a6).substring(2,4) ;
       /* int sumSendString  = 0;
        for(int i = 0; i<asciiString.length(); i++){
            sumSendString = sumSendString + Integer.parseInt(String.format("%04x", (int) asciiString.charAt(i)).substring(2,4));
        }
        asciiString = asciiString +String.valueOf(sumSendString).substring(1,3)+ "\r";*/
        asciiString = asciiString + strChkSum + "\r";
        Log.e(TAG, "asciiString = "+ asciiString);

        if (isConnected()) {
            //connector.write(asciiString.getBytes());
            sendMessage(asciiString.getBytes());
        }
        else {
            Toast.makeText(getApplicationContext(), "Connect to the device", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       // startActivity(new Intent(mContext, MainActivity.class));
        handler.removeCallbacks(checkDataContinue);
        finish();
    }

    // ============================================================================
    @Override
    public synchronized void onResume() {
        super.onResume();

      /*  Log.e(TAG,"onResume");
        String address = PrefUtils.getPairedDeviceAddress(mContext);
        if(Utils.isStringNotNull(address)) {
            BluetoothDevice device = btAdapter.getRemoteDevice(address);
            if (super.isAdapterReady() && (connector == null)) setupConnector(device);

            setDeviceName(device.getName());
        }*/
    }
    //========================================================================


    /**
     * ???????? ?????????? ??????????
     */
 /*   private boolean isConnected() {
      //  return (connector != null) && (connector.getState() == DeviceConnector.STATE_CONNECTED);
        //return true;
        return (connector.getState() == BluetoothChatService.STATE_CONNECTED);

    }*/
    // ==========================================================================



    private void stopConnection() {
        if (connector != null) {
            connector.stop();
            connector = null;
            deviceName = null;
        }
    }
    // ==========================================================================
   /* @Override
    public synchronized void onPause() {
        super.onPause();
        stopConnection();
    }*/

    /**
     * ?????? ????????? ??? ???????????
     */
    /*private void startDeviceListActivity() {
        stopConnection();
        Intent serverIntent = new Intent(this, DeviceListActivity.class);
        startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
    }*/
    // ============================================================================


    /**
     * ????????? ?????????? ?????? "?????"
     *
     * @return
     */
   /* @Override
    public boolean onSearchRequested() {
        if (super.isAdapterReady()) startDeviceListActivity();
        return false;
    }*/
    // ==========================================================================


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      //inflate(R.menu.device_control_activity, menu);
        return true;
    }*/
    // ============================================================================


 /*   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home :
                startActivity(new Intent(mContext, MainActivity.class));
                finish();
                return true;
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
 */   // ============================================================================


    @Override
    public void onStart() {
        super.onStart();

        // hex mode
        final String mode = Utils.getPrefence(this, "pref_commands_mode");
        this.hexMode = mode.equals("HEX");

        this.command_ending = getCommandEnding();

        // ?????? ??????????? ???? ??????
        this.show_timings = Utils.getBooleanPrefence(this, "pref_log_timing");
        this.show_direction = Utils.getBooleanPrefence(this, "pref_log_direction");
        this.needClean = Utils.getBooleanPrefence(this, "pref_need_clean");
    }
    // ============================================================================


    /**
     * ???????? ?? ???????? ??????? ????????? ???????
     */
    private String getCommandEnding() {
        String result = Utils.getPrefence(this, "pref_commands_ending");
        if (result.equals("\\r\\n")) result = "\r\n";
        else if (result.equals("\\n")) result = "\n";
        else if (result.equals("\\r")) result = "\r";
        else result = "";
        return result;
    }
    // ============================================================================


   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    String address = data.getStringExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    BluetoothDevice device = btAdapter.getRemoteDevice(address);
                  //  if (super.isAdapterReady() && (connector == null)) setupConnector(device);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                super.pendingRequestEnableBt = false;
                if (resultCode != Activity.RESULT_OK) {
                    Utils.log("BT not enabled");
                }
                break;
        }
    }*/
    // ==========================================================================


    /**
     * ????????? ?????????? ? ???????????
     */
  /*  private void setupConnector(BluetoothDevice connectedDevice) {
        stopConnection();

        try {
            String emptyName ="empty_device_name";
            DeviceData data = new DeviceData(connectedDevice, emptyName);
            connector = new DeviceConnector(data, mHandler);
            connector.connect();
        } catch (IllegalArgumentException e) {
            Utils.log("setupConnector failed: " + e.getMessage());
        }
    }*/
    // ==========================================================================

    /**

     * @param message  - ????? ??? ???????????
     * @param outgoing - ??????????? ????????
     */
    public void appendLog(String message, boolean hexMode, boolean outgoing, boolean clean) {

        StringBuffer msg = new StringBuffer();

        msg.append(hexMode ? Utils.toHex1(message) : message);
        if (outgoing) msg.append('\n');
    }


    // =========================================================================
    public void appendLog1(String message, boolean hexMode, boolean outgoing, boolean clean) {

        StringBuffer msg = new StringBuffer();

        completReceivedString.append(message);

    }
    // =========================================================================

    void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
        getSupportActionBar().setSubtitle(deviceName);
        getSupportActionBar().setTitle("Error Log");
    }

    // ==========================================================================

    /**
     * ?????????? ?????? ?????? ?? bluetooth-??????
     */
    private class BluetoothResponseHandler extends Handler {
        private WeakReference<ViewErrorLogActivity> mActivity;

        public BluetoothResponseHandler(ViewErrorLogActivity activity) {
            mActivity = new WeakReference<ViewErrorLogActivity>(activity);
        }

        public void setTarget(ViewErrorLogActivity target) {
            mActivity.clear();
            mActivity = new WeakReference<ViewErrorLogActivity>(target);
        }

        @Override
        public void handleMessage(Message msg) {
            ViewErrorLogActivity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case MESSAGE_STATE_CHANGE:

                        Utils.log("MESSAGE_STATE_CHANGE: " + msg.arg1);
                        final ActionBar bar = getSupportActionBar();
                        switch (msg.arg1) {
                            case BluetoothChatService.STATE_CONNECTED:
                                bar.setSubtitle(MSG_CONNECTED);
                                break;
                            case BluetoothChatService.STATE_CONNECTING:
                                bar.setSubtitle(MSG_CONNECTING);
                                break;
                            case BluetoothChatService.STATE_NONE:
                                bar.setSubtitle(MSG_NOT_CONNECTED);
                                break;
                        }
                        break;

                    case MESSAGE_READ:
//                        final String readMessage = (String) msg.obj;
//                        Log.e(TAG, " received msg = "+ readMessage);
                        final String readMessage = (String) msg.obj;
                        if (readMessage != null) {
                            Log.e(TAG, " readMessage = "+ readMessage);
                            activity.appendLog1(readMessage, false, false, activity.needClean);
                        }
                        break;

                    case MESSAGE_DEVICE_NAME:
                        activity.setDeviceName((String) msg.obj);
                        break;

                   /* case MESSAGE_WRITE:

                        break;

                    case MESSAGE_TOAST:
                        // stub
                        break;*/
                }
            }
        }
    }
    // ==========================================================================


    //TODO : protocol :Receive error code for error no requested
    public void showReceivedDataNew(){
        Log.e(TAG, "ShowReceivedData");

        String receivedString = new String(completReceivedString);
        try {

            Log.e(TAG, "receivedString length = "+ receivedString.length());
            Log.e(TAG, "receivedString length = "+ receivedString);

            if(Utils.isStringNotNull(receivedString)) {
                while (receivedString.length() >= 14) {
                    int index0D = receivedString.indexOf("\r");
                    Log.e(TAG, "index0D = " + index0D);
                    String temp = receivedString.substring(0, index0D);
                    Log.e(TAG, "temp = " + temp);
                    if (temp.startsWith("ee")) {
                        String sum = Utils.calculateChecksumValueNew(temp);
                        Log.e(TAG, "" + sum.substring(2, 4) + " -- " + temp.substring(temp.length() - 2, temp.length()) + " temp = " + temp);

                        if (sum.substring(2, 4).equalsIgnoreCase(temp.substring(temp.length() - 2, temp.length()))) {
                            String errorCode = temp.substring(10, 12);

                            int month = Integer.parseInt(temp.substring(8, 10));
                            int date = Integer.parseInt(temp.substring(6, 8));
                            int min = Integer.parseInt(temp.substring(2, 4));
                            int hrs = Integer.parseInt(temp.substring(4, 6));

                            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View view = inflater.inflate(R.layout.error_item, null);
                            TextView tvErroCode = (TextView) view.findViewById(R.id.tvErrorCode);
                            TextView tvErrorDate = (TextView) view.findViewById(R.id.tvErrorDate);
                            TextView tvErrorTime = (TextView) view.findViewById(R.id.tvErrorTime);
                            TextView tvErrorDiscription = (TextView) view.findViewById(R.id.tvErrorDescription);

                            tvErrorTime.setText("" + min + " : " + hrs);
                            tvErroCode.setText("" + errorCode);
                            tvErrorDate.setText("" + date + "/" + month);
                            tvErrorDiscription.setText("" + getErrorCode(Integer.parseInt(errorCode)));
                            llErrors.addView(view);

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
    }

    private String getErrorCode(int code) {

        if(code == 80){
            return Constance.ERROR_CODE_80;
        }else if(code == 81){
            return Constance.ERROR_CODE_81;
        }else if(code == 82){
            return Constance.ERROR_CODE_82;
        }else if(code == 83){
            return Constance.ERROR_CODE_83;
        }else if(code == 84){
            return Constance.ERROR_CODE_84;
        }else if(code == 85){
            return Constance.ERROR_CODE_85;
        }else if(code == 86){
            return Constance.ERROR_CODE_86;
        }else if(code == 87){
            return Constance.ERROR_CODE_87;
        }else if(code == 88){
            return Constance.ERROR_CODE_88;
        }else if(code == 89){
            return Constance.ERROR_CODE_89;
        }else if(code == 90){
            return Constance.ERROR_CODE_90;
        }else if(code == 91){
            return Constance.ERROR_CODE_91;
        }else if(code == 92){
            return Constance.ERROR_CODE_92;
        }else if(code == 93){
            return Constance.ERROR_CODE_93;
        }else if(code == 94){
            return Constance.ERROR_CODE_94;
        }else if(code == 95){
            return Constance.ERROR_CODE_95;
        }else if(code == 96){
            return Constance.ERROR_CODE_96;
        }else if(code == 97){
            return Constance.ERROR_CODE_97;
        }
        return "Error Not Defined";
    }

}
