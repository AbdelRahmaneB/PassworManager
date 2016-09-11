package ub.passwordmanager.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import ub.passwordmanager.R;
import ub.passwordmanager.appConfig.AppConfig;
import ub.passwordmanager.dataBase.DB_Helper;
import ub.passwordmanager.views.activities.LogIn;
import ub.passwordmanager.views.activities.MainActivity;
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
                // Check if the username exist uin the preferences file
                String username = (String) AppConfig.getInstance().getSavedValueFromPreferences(
                        SplashScreen.this,
                        AppConfig.KEY_PREF_STRING,
                        AppConfig.KEY_PREF_USERNAME);

                if (username == null) {
                    startActivity(new Intent(getApplicationContext(), SignIn.class));
                } else {
                    AppConfig.getInstance().setCurrentUser(username);
                    startActivity(new Intent(getApplicationContext(), LogIn.class));
                }
            }
        }, 2000);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
