package com.example.findmyphone;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Here in MainActivity we will write code for permission
    private static final int MY_PERMISSION_REQUEST_RECEIVE_SMS = 0;

    static AudioManager audioManager1;
    static AudioManager audioManager2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioManager1 = (AudioManager) getSystemService(getApplicationContext().AUDIO_SERVICE);
        audioManager2 = (AudioManager) getSystemService(getApplicationContext().AUDIO_SERVICE);

        Button general = findViewById(R.id.general);
        Button vibrate = findViewById(R.id.vibrate);
        general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                ChangeMode("general");
            }
        });
        vibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                ChangeMode("vibrate");
            }
        });
        //Check if the permission is not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            // If the permission is not been granted then check if the user has denied the permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS))
            {
                //Do nothing as user has denied
            }
            else
            {
                //A popup will appear asking for required permission i.e Allow or Deny
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, MY_PERMISSION_REQUEST_RECEIVE_SMS);
            }
        }

    }//onCreate
    //after getting the result of permission requests the results will be passed this method
    @Override
    public void onRequestPermissionsResult(int requestcode, String permissions[], int[] grantResults){
        // will check the result code
        switch (requestcode)
        {
            case MY_PERMISSION_REQUEST_RECEIVE_SMS:
            {
                // check whether the length of grantResults is greater than 0 ans is equal to PERMISSION_GRANTED
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //Now broadcast receiver will work in background
                    Toast.makeText(this, "Thank you for permitting!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "Well I can't do anything until you permit me", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    public static void ChangeMode(String mode)
    {
        switch (mode){
            case "general":
                audioManager1.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                break;
            case "vibrate":
                audioManager2.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                break;
                default:
                    //Toast.makeText(this, "No Change!", Toast.LENGTH_SHORT).show();

        }
    }

}
