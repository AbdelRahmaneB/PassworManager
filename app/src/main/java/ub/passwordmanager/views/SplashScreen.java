package ub.passwordmanager.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import ub.passwordmanager.R;
import ub.passwordmanager.appConfig.AppConfig;
import ub.passwordmanager.views.activities.LogIn;
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

                // set The parameters for the Password Generator
                setPwdGeneratorPreferences();

                //set the preference language
                setLanguage();

                // Check if the username exist uin the preferences file
                checkIfAccountExists();

            }
        }, 2000);
    }

    /**
     * Function to get and set the language selected by the user
     * by default it's English
     */
    private void setLanguage() {
        if (AppConfig.getInstance().isKeyExist(
                SplashScreen.this,
                AppConfig.KEY_PREF_APP_LANGUAGE)) {

            String lang = AppConfig.getInstance().getSavedValueFromPreferences(
                    SplashScreen.this,
                    AppConfig.KEY_PREF_STRING,
                    AppConfig.KEY_PREF_APP_LANGUAGE
            ).toString();

            AppConfig.getInstance().setAppLanguage(SplashScreen.this, lang);

        }
    }

    /**
     * Function check if the preferences for the Password Generator exists,
     * Otherwise it Create and init the Preferences file.
     */
    private void setPwdGeneratorPreferences() {
        if (!AppConfig.getInstance().isKeyExist(
                SplashScreen.this,
                AppConfig.KEY_PREF_LOWER_CASE)) {

            // Lower Case
            AppConfig.getInstance().saveValueToPreference(
                    SplashScreen.this,
                    AppConfig.KEY_PREF_BOOLEAN,
                    AppConfig.KEY_PREF_LOWER_CASE,
                    true
            );

            // Upper Case
            AppConfig.getInstance().saveValueToPreference(
                    SplashScreen.this,
                    AppConfig.KEY_PREF_BOOLEAN,
                    AppConfig.KEY_PREF_UPPER_CASE,
                    true
            );

            // Symbols Case
            AppConfig.getInstance().saveValueToPreference(
                    SplashScreen.this,
                    AppConfig.KEY_PREF_BOOLEAN,
                    AppConfig.KEY_PREF_USE_SYMBOLS,
                    true
            );

            // Numbers Case
            AppConfig.getInstance().saveValueToPreference(
                    SplashScreen.this,
                    AppConfig.KEY_PREF_BOOLEAN,
                    AppConfig.KEY_PREF_USE_NUMBERS,
                    true
            );
        }
    }

    /**
     * Function that checks if the a UserAccount already exists in the DataBase,
     * and redirect the user to the proper Activity.
     */
    private void checkIfAccountExists() {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
