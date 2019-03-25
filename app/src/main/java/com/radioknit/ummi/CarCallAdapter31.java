package com.radioknit.ummi;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static com.radioknit.ummi.CarCallActivity31.showState;
import static com.radioknit.ummi.CarCallActivity31.showStateDown;
import static com.radioknit.ummi.CarCallActivity31.showStateUp;
import static com.radioknit.ummi.MainActivity.isConnected;
import static com.radioknit.ummi.MainActivity.sendMessage;


/**
 * Created by nishant on 24/4/17.
 */
/*callLopCop() means calling lop and cop (device's floor indicators) for user selected floors*/

public class CarCallAdapter31 extends BaseAdapter {

    private static final String TAG = "Tag_CarCallAdapter31";
    public static boolean SHOW_TAG = true;

    private Context mContext;
    protected ViewHolder mViewHolder;
    private View view;
    private LayoutInflater layoutInflater;
    private String cop1Calls;
    private String cop2Calls;
    private int callIndex;
    private CarCallIndicatorSignalListner mCarCallIndicatorSignalListner;
    private String strUpDnCalls;
    private int floorNo;
    public static TextView textViewStateAll[]=new TextView[32];
    public  static ImageView imageViewUpAll[] = new ImageView[32];
    public static ImageView imageViewDownAll[] = new ImageView[32];
    private Drawable drawableSel = null, drawableNotSel = null;

    public interface customListener {

    }


    public CarCallAdapter31(Context context, String cop1Calls , String cop2Calls , CarCallIndicatorSignalListner carCallIndicatorSignalListner){
        mContext = context;
        layoutInflater = LayoutInflater.from(context);
        this.cop1Calls = cop1Calls;
        this.cop2Calls = cop2Calls;
        mCarCallIndicatorSignalListner = carCallIndicatorSignalListner;

    }
    public void notoifyChange(String cop1Calls,String cop2Calls){
        this.cop1Calls=cop1Calls;
        this.cop2Calls=cop2Calls;
    }

    public CarCallAdapter31(Context context, String strUpDnCalls , int floorNO, CarCallIndicatorSignalListner carCallIndicatorSignalListner){
        mContext = context;
        layoutInflater = LayoutInflater.from(context);
        this.strUpDnCalls = strUpDnCalls;
        this.floorNo = floorNO;
        mCarCallIndicatorSignalListner = carCallIndicatorSignalListner;

    }

    class ViewHolder {

        TextView txtFloorNumber;
        ImageView imgUp;
        ImageView imgDown;
        LinearLayout llItem_call;
    }

    @Override
    public int getCount() {
        return 32;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        //==== getView() FUNCTION GET CALLED FOR EACH ITEM OF LIST VIEW=================

        final ViewHolder viewHolder ;

        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_calles, null);
            viewHolder = new ViewHolder();
            viewHolder.txtFloorNumber = (TextView) convertView.findViewById(R.id.tv_call_item_floor_no);
            viewHolder.imgUp = (ImageView) convertView.findViewById(R.id.img_calls_up_indicator);
            viewHolder.imgDown = (ImageView) convertView.findViewById(R.id.img_calls_dn_indicator);
            viewHolder.llItem_call = (LinearLayout) convertView.findViewById(R.id.llItems_calls);
//            if((15 - position) == 1)
//
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        final ViewHolder holder = (ViewHolder) view.getTag();
//        drawable = ContextCompat.getDrawable(mContext, R.drawable.circular_text_view_selected);
        drawableSel = mContext.getResources().getDrawable(R.drawable.circular_text_view_selected);
        drawableNotSel=mContext.getResources().getDrawable(R.drawable.circular_text_view);
//        drawable = mContext.getDrawable(R.drawable.circular_text_view_selected);

        //========POSITION = 0 FOR TOP_FIRST ITEM===========and increases downwords=======
        viewHolder.txtFloorNumber.setText(""+(31-(position)));
        textViewStateAll[position]= viewHolder.txtFloorNumber;
        imageViewDownAll[position]= viewHolder.imgDown;
        imageViewUpAll[position]= viewHolder.imgUp;

        try {
            //=======removing visibility of top up_image and last down_image=========
            if (position == 0 )
                imageViewUpAll[position].setVisibility(View.INVISIBLE);
            if (position == 31)
                imageViewDownAll[position].setVisibility(View.INVISIBLE);

           /* imageViewUpAll[0].setVisibility(View.INVISIBLE);
            imageViewDownAll[31].setVisibility(View.INVISIBLE);*/
        }catch (Exception e){
            //Catch exception
            Log.d(TAG, "getView: except:"+e);
        }

//        Log.d(TAG, "getView: position = "+position + "textViewStateAll "+showState[position]);
//        Log.d(TAG, "getView: position = "+position + "imageViewUpAll "+showStateUp[position]);
//        Log.d(TAG, "getView: position = "+position + "imageViewDownAll "+showStateDown[position]);

        //===textViewStateAll = array of textView having Floor numbers
        if(showState[position]==0){
            textViewStateAll[position].setBackground(drawableNotSel);
        }
        else if(showState[position]==1){
            textViewStateAll[position].setBackground(drawableSel);
        }

        if(showStateUp[position]==0){
            imageViewUpAll[position].setImageResource(R.drawable.up_arraow);
        }
        else if(showStateUp[position]==1){
            imageViewUpAll[position].setImageResource(R.drawable.up_green);
        }

        if(showStateDown[position]==0){
            imageViewDownAll[position].setImageResource(R.drawable.down_arr);
        }
        else if(showStateDown[position]==1){
            imageViewDownAll[position].setImageResource(R.drawable.down_green);
        }

        //for holding the position on selected item
        // for maintaining the car call state.

//        if((15-callIndex) == position){
//            Log.e(TAG, "Floor hilight = "+ (15- callIndex));
//            holder.txtFloorNumber.setBackgroundColor(mContext.getResources().getColor(R.color.red));
//        }
        if(SHOW_TAG)Log.d(TAG, "cop1Calls obj val = "+cop1Calls);

/*
        if(Utils.isObjNotNull(cop1Calls)) {
          //  if(SHOW_TAG)Log.d(TAG, "cop1Calls not null");

            if (Utils.isStringNotNull(cop1Calls)) {

                if(SHOW_TAG)Log.d(TAG, "cop1Calls string = "+cop1Calls);

                if (cop1Calls.charAt(0) == '1') {
                    callIndex = 8;
                    if ((Const.NO_OF_FLOORS - callIndex) == position) {
                        viewHolder.txtFloorNumber.setBackground(drawableNotSel);
                    }
                }
                if (cop1Calls.charAt(1) == '1') {
                    callIndex = 7;
                    if ((Const.NO_OF_FLOORS - callIndex) == position) {
                        viewHolder.txtFloorNumber.setBackground(drawableNotSel);
                    }
                }
                if (cop1Calls.charAt(2) == '1') {
                    callIndex = 6;
                    if ((Const.NO_OF_FLOORS - callIndex) == position) {
                        viewHolder.txtFloorNumber.setBackground(drawableNotSel);
                    }
                }
                if (cop1Calls.charAt(3) == '1') {
                    callIndex = 5;
                    if ((Const.NO_OF_FLOORS - callIndex) == position) {
                        viewHolder.txtFloorNumber.setBackground(drawableNotSel);
                    }
                }
                if (cop1Calls.charAt(4) == '1') {
                    callIndex = 4;
                    if ((Const.NO_OF_FLOORS - callIndex) == position) {
                        viewHolder.txtFloorNumber.setBackground(drawableNotSel);
                    }
                }
                if (cop1Calls.charAt(5) == '1') {
                    callIndex = 3;
                    if ((Const.NO_OF_FLOORS - callIndex) == position) {
                        viewHolder.txtFloorNumber.setBackground(drawableNotSel);
                    }
                }
                if (cop1Calls.charAt(6) == '1') {
                    callIndex = 2;
                    if ((Const.NO_OF_FLOORS - callIndex) == position) {
                        viewHolder.txtFloorNumber.setBackground(drawableNotSel);
                    }
                }
                if (cop1Calls.charAt(7) == '1') {
                    callIndex = 1;
                    if ((Const.NO_OF_FLOORS - callIndex) == position) {
                        viewHolder.txtFloorNumber.setBackground(drawableNotSel);
                    }
                }
            }
        }

        if(Utils.isObjNotNull(cop2Calls)) {
            if (Utils.isStringNotNull(cop2Calls)) {
                if (cop2Calls.charAt(0) == '1') {
                    callIndex = 16;
                    if ((Const.NO_OF_FLOORS - callIndex) == position) {
                        viewHolder.txtFloorNumber.setBackground(drawableNotSel);
                    }
                }
                if (cop2Calls.charAt(1) == '1') {
                    callIndex = 15;
                    if ((Const.NO_OF_FLOORS - callIndex) == position) {
                        viewHolder.txtFloorNumber.setBackground(drawableNotSel);
                    }
                }
                if (cop2Calls.charAt(2) == '1') {
                    callIndex = 14;
                    if ((Const.NO_OF_FLOORS - callIndex) == position) {
                        viewHolder.txtFloorNumber.setBackground(drawableNotSel);
                    }
                }
                if (cop2Calls.charAt(3) == '1') {
                    callIndex = 13;
                    if ((Const.NO_OF_FLOORS - callIndex) == position) {
                        viewHolder.txtFloorNumber.setBackground(drawableNotSel);
                    }
                }
                if (cop2Calls.charAt(4) == '1') {
                    callIndex = 12;
                    if ((Const.NO_OF_FLOORS - callIndex) == position) {
                        viewHolder.txtFloorNumber.setBackground(drawableNotSel);
                    }
                }
                if (cop2Calls.charAt(5) == '1') {
                    callIndex = 11;
                    if ((Const.NO_OF_FLOORS - callIndex) == position) {
                        viewHolder.txtFloorNumber.setBackground(drawableNotSel);
                    }
                }
                if (cop2Calls.charAt(6) == '1') {
                    callIndex = 10;
                    if ((Const.NO_OF_FLOORS - callIndex) == position) {
                        viewHolder.txtFloorNumber.setBackground(drawableNotSel);
                    }
                }
                if (cop2Calls.charAt(7) == '1') {
                    callIndex = 9;
                    if ((Const.NO_OF_FLOORS - callIndex) == position) {
                        viewHolder.txtFloorNumber.setBackground(drawableNotSel);
                    }
                }
            }
        }
*/

        viewHolder.txtFloorNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*viewHolder.txtFloorNumber.setBackground(drawableNotSel);
                    mCarCallIndicatorSignalListner.sendCarCallIndicatorSignal(position);*/
//                    viewHolder.txtFloorNumber.setTag(position);
                    if(SHOW_TAG)Log.d(TAG, "txtFloorNumber clicked placecall = 1");
                //    Toast.makeText(mContext, "Clicked : text : "+(31-position), Toast.LENGTH_SHORT).show();

                    //viewHolder.txtFloorNumber.setBackground(drawableSel);
                    callLopCop((31-position),1,viewHolder);//placecall =0000 0001 /*call from cop*/
                }
            });

        viewHolder.imgUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //mCarCallIndicatorSignalListner.sendUpCallIndicatorSignal(position);

                    if(SHOW_TAG)Log.d(TAG, "imgUp clicked placecall = 2");
                 //   Toast.makeText(mContext, "Clicked : imgUp : "+(31-position), Toast.LENGTH_SHORT).show();

                    //viewHolder.imgUp.setImageResource(R.drawable.up_green);
                    callLopCop((31-position),2,viewHolder);//placecall = 0000 0010 /*up call from lop*/
                }
            });

        viewHolder.imgDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //mCarCallIndicatorSignalListner.sendDnCallIndicatorSignal(position);

                    if(SHOW_TAG)Log.d(TAG, "imgDown clicked placecall = 4");
                //    Toast.makeText(mContext, "Clicked : imgDown : "+(31-position), Toast.LENGTH_SHORT).show();

                    //viewHolder.imgDown.setImageResource(R.drawable.down_green);
                    callLopCop((31-position),4,viewHolder);//place call = 0000 0100 /*down call from lop*/
                }
            });

       //     showUpDnCalls(viewHolder,strUpDnCalls, position);

        return convertView;
    }

    private void showUpDnCalls(ViewHolder holder,String strUpDnCalls, int position) {

        if(SHOW_TAG)Log.d(TAG, "showUpDnCalls()");

        if(Utils.isObjNotNull(strUpDnCalls)) {
            if (strUpDnCalls.charAt(7) == '1') {
                if (floorNo == position) {
                    holder.imgDown.setImageResource(R.drawable.down_black_arraw);
                }
            }
            if (strUpDnCalls.charAt(6) == '1') {
                if (floorNo == position) {
                    holder.imgUp.setImageResource(R.drawable.up_black_arrow);
                }
            }
        }
    }

    public interface  CarCallIndicatorSignalListner{
          void sendCarCallIndicatorSignal(int position);
          void sendUpCallIndicatorSignal(int position);
          void sendDnCallIndicatorSignal(int position);
    }


    public void callLopCop(int flrNo, int placeCall, ViewHolder viewHolder) {

        if(SHOW_TAG)Log.d(TAG, "calllopcop() flor no = "+flrNo +" placecall = "+placeCall );

        int a1 = 18;
        int a2 = 65;
        int a3 = flrNo;
        int a4 = placeCall;
        int a5 = 67;
        int a6 = 76;
        int[] sendValChkSum={a1, a2, a3, a4, a5, a6};
        String strChkSum= CalculateCheckSum.calculateChkSum(sendValChkSum);

        if(SHOW_TAG)Log.d(TAG, "checksum = "+strChkSum);


        String asciiString  = String.format("%04x", a1).substring(2,4)+String.format("%04x", a2).substring(2,4)+String.format("%04x", a3).substring(2,4)+String.format("%04x", a4).substring(2,4)+String.format("%04x", a5).substring(2,4)+String.format("%04x", a6).substring(2,4) ;
        asciiString = asciiString + strChkSum + "\r";
      //  Log.e(TAG, "asciiString = "+ asciiString);
        if(isConnected()) {

            if(SHOW_TAG)Log.d(TAG, "===========sending msg==== = "+asciiString + " bytes form = "+asciiString.getBytes());
            sendMessage(asciiString.getBytes());

            //Change button color if device is connected we sent command
            switch (placeCall){
                case 1:
                    viewHolder.txtFloorNumber.setBackground(drawableSel);
                    break;
                case 2:
                    viewHolder.imgUp.setImageResource(R.drawable.up_green);
                    break;
                case 4:
                    viewHolder.imgDown.setImageResource(R.drawable.down_green);
                    break;
            }
        }
    }


    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
