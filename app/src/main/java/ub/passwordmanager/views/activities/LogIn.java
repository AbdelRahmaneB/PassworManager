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

import ub.passwordmanager.R;

public class LogIn extends AppCompatActivity {

    private EditText tv_pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        tv_pwd = (EditText) findViewById(R.id.t_password_login);

        Button logInButton = (Button) findViewById(R.id.bt__login);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(tv_pwd.getText().toString())) {
                    // ToDo : Test the login and password
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }else{
                    TextInputLayout pwd = (TextInputLayout) findViewById(R.id.input_pwd_login);
                    pwd.setError(getResources().getString(R.string.logIn_empty_password));
                }

            }
        });

        TextView forgotPwd = (TextView) findViewById(R.id.l_forgotPassword);
        forgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),PasswordRecovery.class));
            }
        });

    }
}
