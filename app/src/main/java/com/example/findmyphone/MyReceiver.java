 package com.example.findmyphone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

 public class MyReceiver extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SmsBroadcastReceiver";
    String msg, phoneNo = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        //retrieves the general action to be performed and display on log

        //AudioManager audioManager = (AudioManager) ContextCompat.getSystemService()
        Log.i(TAG, "Intent Received: " + intent.getAction());
        if (intent.getAction() == SMS_RECEIVED)
        {
            //retrieves a map of extended data from the intent
            Bundle dataBundal = intent.getExtras();
            if (dataBundal != null)
            {
                //creating PDU (Protocol Data Unit) object which is a protocol for transferring message
                Object[] mypdu = (Object[])dataBundal.get("pdus");
                final SmsMessage[] message = new SmsMessage[mypdu.length];
                for (int i = 0; i < mypdu.length; i++)
                {
                    // for build versions >= API Level 23
                    if (Build.VERSION.SDK_INT  >= Build.VERSION_CODES.M)
                    {
                        String format = dataBundal.getString("format");
                        //From PDU we get all object and SmsMessage Object using following Line o fcode
                        message[i] = SmsMessage.createFromPdu((byte[])mypdu[i], format);
                    }
                    else
                    {
                        //API Level 23
                        message[i] = SmsMessage.createFromPdu((byte[]) mypdu[i]);
                    }
                    msg = message[i].getMessageBody();
                    phoneNo = message[i].getOriginatingAddress() ;
                }
                //Toast.makeText(context, "Message: " + msg + "\nNumber: " + phoneNo, Toast.LENGTH_LONG).show();
                String lowerCase = msg.toLowerCase();
                if (lowerCase.equals("general") || lowerCase.equals("vibrate"))
                {
                    MainActivity mainActivity = new MainActivity();
                    mainActivity.ChangeMode(lowerCase);
                }
            }
        }
    }
}
