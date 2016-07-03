package rishabh.example.com.screendimmer2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int prog=0;

    MyService mService;
    TextView textView;
    SeekBar seekBar;
    Intent intent;
    Boolean status=false,mBound=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(this, MyService.class);


        textView=(TextView)findViewById(R.id.textView);
        seekBar=(SeekBar)findViewById(R.id.seekBar);

        prog=seekBar.getProgress();
        textView.setText("Brightness Level ("+prog+"%)");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                prog = seekBar.getProgress();
                textView.setText("Brightness Level (" + prog + "%)");

                if(status) {

                    mService.setColor(prog);

                   // Toast.makeText(MainActivity.this,"Hit The Start Button",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
    public void buttonSwitch(View view){
        Button buttonSwitch=(Button)view;
        if(buttonSwitch.getText().toString().equals("START")){
            buttonSwitch.setText("STOP");

            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
            startService(intent);
            status=true;


        }else if(buttonSwitch.getText().toString().equals("STOP")){

            buttonSwitch.setText("START");

           unbindService(serviceConnection);
            status=false;
        }

    }

    ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MyService.LocalBinder binder = (MyService.LocalBinder) iBinder;
            mService = binder.getService();
            mBound = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBound = false;
        }
    };



}