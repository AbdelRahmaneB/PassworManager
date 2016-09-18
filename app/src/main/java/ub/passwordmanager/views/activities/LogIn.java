package ub.passwordmanager.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ub.passwordmanager.R;
import ub.passwordmanager.Services.Service_ImportExportDataBase;
import ub.passwordmanager.Services.Service_UserAccount;
import ub.passwordmanager.appConfig.AppConfig;

public class LogIn extends AppCompatActivity {

    private EditText tv_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        tv_pwd = (EditText) findViewById(R.id.t_password_login);
        TextView tv_userName = (TextView) findViewById(R.id.l_username_login);
        tv_userName.setText(AppConfig.getInstance().getCurrentUser()); // Set the name of the login

        Button logInButton = (Button) findViewById(R.id.bt__login);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkTheLogIn();
            }
        });

        TextView forgotPwd = (TextView) findViewById(R.id.l_forgotPassword);
        forgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PasswordRecovery.class));
            }
        });


    }

    /**
     * The role of this function is to check if the field are correct
     */
    private void checkTheLogIn() {
        if (!TextUtils.isEmpty(tv_pwd.getText().toString())) {

            // check the login in the database
            checkLogInInformation();

        } else {
            TextInputLayout pwd = (TextInputLayout) findViewById(R.id.input_pwd_login);
            pwd.setError(getResources().getString(R.string.logIn_empty_password));
        }
    }

    /**
     * The role of this function is to test if the login
     * exist in the database and then redirect the user accordingly
     */
    private void checkLogInInformation() {
        try {
            // set the global password for the app
            // (It's initialised here because generate an error when trying to decrypt)
            AppConfig.getInstance().setCurrentPassword(tv_pwd.getText().toString());

            if (Service_UserAccount.getInstance().verifyAuthenticationData(getBaseContext())) {
                // Change the last connection date
                Service_UserAccount.getInstance().setLastConnection(getBaseContext());

                // Start the MainActivity
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            } else {
                Toast.makeText(getBaseContext(),
                        "Password incorrect !!",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
