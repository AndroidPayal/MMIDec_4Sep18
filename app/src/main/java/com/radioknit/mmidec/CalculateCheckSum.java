package com.radioknit.mmidec;

import android.util.Log;

/**
 * Created by soft on 11/12/17.
 */

public class CalculateCheckSum {
public static String TAG = "Tag_checksum";


   public static String calculateChkSum(int[] calValue){
        String strCmd=String.format("%02x",calValue[0])+String.format("%02x",calValue[1])+String.format("%02x",calValue[2])+String.format("%02x",calValue[3])+String.format("%02x",calValue[4])+String.format("%02x",calValue[5]);

       Log.d(TAG, "calculateChkSum: strcmd = "+strCmd
       + "a1="+calValue[0]+"   "+String.format("%02x",calValue[0])
       + " a2 = "+calValue[1]+"   "+String.format("%02x",calValue[1])
       + " a3= "+calValue[2]+"   "+ String.format("%02x",calValue[2])
       + " a4= "+calValue[3]+"   "+String.format("%02x",calValue[3])
       + " a5 = "+calValue[4]+"   "+String.format("%02x",calValue[4])
       + " a6= "+calValue[5]+"   "+String.format("%02x",calValue[5])
       );

        int chkSum  = 0;
        for(int i = 0; i<strCmd.length(); i++){
            chkSum = chkSum + strCmd.charAt(i);

            Log.d(TAG, "calculateChkSum: checksumloop = "+chkSum);
        }
       Log.d(TAG, "calculateChkSum: chksum = "+ chkSum);
      // System.out.println("Checksum: "+chkSum);
       Log.d(TAG, "calculateChkSum: return = "+Integer.toString(chkSum,16));

        return Integer.toString(chkSum,16).substring(1,3);
    }
}

/**============FOR DATE 14/3/19
 * 2019-03-04 15:11:20.790 : calculateChkSum: strcmd = 12f314031944a1=18   12 a2 = 243   f3 a3= 20   14 a4= 3   03 a5 = 25   19 a6= 68   44
 * 2019-03-04 15:11:20.790 : calculateChkSum: checksumloop = 49
 * 2019-03-04 15:11:20.790 : calculateChkSum: checksumloop = 99
 * 2019-03-04 15:11:20.790 : calculateChkSum: checksumloop = 201
 * 2019-03-04 15:11:20.790 : calculateChkSum: checksumloop = 252
 * 2019-03-04 15:11:20.790 : calculateChkSum: checksumloop = 301
 * 2019-03-04 15:11:20.790 : calculateChkSum: checksumloop = 353
 * 2019-03-04 15:11:20.790 : calculateChkSum: checksumloop = 401
 * 2019-03-04 15:11:20.790 : calculateChkSum: checksumloop = 452
 * 2019-03-04 15:11:20.790 : calculateChkSum: checksumloop = 501
 * 2019-03-04 15:11:20.790 : calculateChkSum: checksumloop = 558
 * 2019-03-04 15:11:20.790 : calculateChkSum: checksumloop = 610
 * 2019-03-04 15:11:20.790 : calculateChkSum: checksumloop = 662
 * 2019-03-04 15:11:20.790 : calculateChkSum: chksum = 662
 * 2019-03-04 15:11:20.791 : calculateChkSum: return = 296
 *
 *
 *
 *
 * 2019-03-04 15:11:20.862 : calculateChkSum: strcmd = 12f215114444a1=18   12 a2 = 242   f2 a3= 21   15 a4= 17   11 a5 = 68   44 a6= 68   44
 * 2019-03-04 15:11:20.862 : calculateChkSum: checksumloop = 49
 * 2019-03-04 15:11:20.862 : calculateChkSum: checksumloop = 99
 * 2019-03-04 15:11:20.862 : calculateChkSum: checksumloop = 201
 * 2019-03-04 15:11:20.862 : calculateChkSum: checksumloop = 251
 * 2019-03-04 15:11:20.862 : calculateChkSum: checksumloop = 300
 * 2019-03-04 15:11:20.862 : calculateChkSum: checksumloop = 353
 * 2019-03-04 15:11:20.862 : calculateChkSum: checksumloop = 402
 * 2019-03-04 15:11:20.862 : calculateChkSum: checksumloop = 451
 * 2019-03-04 15:11:20.862 : calculateChkSum: checksumloop = 503
 * 2019-03-04 15:11:20.862 : calculateChkSum: checksumloop = 555
 * 2019-03-04 15:11:20.862 : calculateChkSum: checksumloop = 607
 * 2019-03-04 15:11:20.862 : calculateChkSum: checksumloop = 659
 * 2019-03-04 15:11:20.862 : calculateChkSum: chksum = 659
 * 2019-03-04 15:11:20.862 : calculateChkSum: return = 293*/