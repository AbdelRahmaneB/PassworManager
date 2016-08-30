package ub.passwordmanager.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import ub.passwordmanager.R;
import ub.passwordmanager.views.activities.SignIn;

/**
 * Description : Splash Screen
 * In this class we will decide if we have to send the user to
 * the login view or the sign in view
 */
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        // Set the timer for 2 sec
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), SignIn.class));
            }
        }, 2000);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
