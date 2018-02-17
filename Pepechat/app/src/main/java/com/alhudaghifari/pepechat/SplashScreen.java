package com.alhudaghifari.pepechat;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    private static final int GOTOLOGINPAGE = 1;
    private static final int GOTOHOMEPAGE = 2;
    //time in milliseconds
    private static final long SPLASHTIME = 2000;

    private SessionManager session;

    private ImageView splash;
    private TextView tittleapps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

//        splash = (ImageView) findViewById(R.id.splashscreen);

        tittleapps = (TextView) findViewById(R.id.titleapp);

        // Session manager
        session = new SessionManager(getApplicationContext());

        Message msg = new Message();

        if (session.isLoggedIn())
            msg.what = GOTOHOMEPAGE;
        else
            msg.what = GOTOLOGINPAGE;

        splashHandler.sendMessageDelayed(msg, SPLASHTIME);
    }


    //handler for splash screen
    private Handler splashHandler = new Handler() {
        /* (non-Javadoc)
         * @see android.os.Handler#handleMessage(android.os.Message)
         */
        @Override
        public void handleMessage(Message msg) {
            Intent intentpindah;
            switch (msg.what) {
                case GOTOLOGINPAGE:
                    //remove SplashScreen from view
//                    splash.setVisibility(View.GONE);
                    tittleapps.setVisibility(View.GONE);
                    intentpindah = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(intentpindah);
                    finish();
                    break;
                case GOTOHOMEPAGE:
//                    splash.setVisibility(View.GONE);
                    tittleapps.setVisibility(View.GONE);
                    intentpindah = new Intent(SplashScreen.this, Users.class);
                    startActivity(intentpindah);
                    finish();
                    break;
            }
            super.handleMessage(msg);
        }
    };
}
