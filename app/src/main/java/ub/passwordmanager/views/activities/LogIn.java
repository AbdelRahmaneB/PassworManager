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

import java.util.List;

import ub.passwordmanager.Models.UserAccountModel;
import ub.passwordmanager.R;
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
                if (!TextUtils.isEmpty(tv_pwd.getText().toString())) {
                    // ToDo : Test the login and password



                    AppConfig.getInstance().setCurrentPassword(tv_pwd.getText().toString());
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                } else {
                    TextInputLayout pwd = (TextInputLayout) findViewById(R.id.input_pwd_login);
                    pwd.setError(getResources().getString(R.string.logIn_empty_password));
                }

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
}
