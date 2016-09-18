package ub.passwordmanager.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import ub.passwordmanager.R;
import ub.passwordmanager.Services.Service_UserAccount;
import ub.passwordmanager.appConfig.AppConfig;

public class PasswordRecovery extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);
        setTitle(R.string.title_password_recovery);

        // set the button listener
        Button bt_recover = (Button) findViewById(R.id.bt_recover);
        bt_recover.setOnClickListener(mRecoverPasswordListener);
    }

    /**
     * Listener for the recovery button
     */
    View.OnClickListener mRecoverPasswordListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // initialise the fields
            TextInputLayout in_email = (TextInputLayout) findViewById(R.id.input_email_recovery);
            EditText tv_email = (EditText) findViewById(R.id.t_email_recovery);
            final TextView tv_password_recovery = (TextView) findViewById(R.id.password_recovery);
            final TextView l_password_recovery = (TextView) findViewById(R.id.l_password_recovery);

            // check if the Email is not empty
            if (TextUtils.isEmpty(tv_email.getText().toString())) {
                // set the error
                in_email.setError(getResources().getString(R.string.empty_email_error_signIn));
                return;
            }else {
                // cancel the error
                in_email.setErrorEnabled(false);
            }

            // check if the Email format is correct
            if (AppConfig.getInstance().isEmailValid(tv_email.getText().toString())) {
                //cancel the error
                in_email.setErrorEnabled(false);

                // get the password from the database
                recoverPassword(tv_password_recovery, tv_email, l_password_recovery);
            } else {
                // set the error
                in_email.setError(getResources().getString(R.string.invalid_email_error_signIn));
            }
        }
    };

    /**
     * Function to get and show the information of the user in the fields of the view.
     */
    private void recoverPassword(TextView mText, EditText tv_email, TextView mLabel) {
        try {
            // get the password from the database
            String mPwd = Service_UserAccount.getInstance().recoverPassword(
                    getBaseContext(),
                    tv_email.getText().toString()
            );

            // show the password for the user
            mText.setText(mPwd);
            mText.setVisibility(View.VISIBLE);
            mLabel.setVisibility(View.VISIBLE);

            // copy the password into clipboard
            AppConfig.copyToClipBoard(PasswordRecovery.this,
                    mPwd);

            // Set the timer for 4 sec
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(), LogIn.class));
                }
            }, 4000);

        } catch (Exception e) {
            Toast.makeText(PasswordRecovery.this, "Email incorrect !!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), LogIn.class));
    }

}
