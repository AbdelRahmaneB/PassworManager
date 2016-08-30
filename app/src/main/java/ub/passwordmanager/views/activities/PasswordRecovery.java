package ub.passwordmanager.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;

import ub.passwordmanager.R;
import ub.passwordmanager.appConfig.AppConfig;

public class PasswordRecovery extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);
        setTitle(R.string.title_password_recovery);

        Button bt_recover = (Button) findViewById(R.id.bt_recover);
        bt_recover.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                TextInputLayout in_email = (TextInputLayout) findViewById(R.id.input_email_recovery);
                EditText tv_email = (EditText) findViewById(R.id.t_email_recovery);

                if (TextUtils.isEmpty(tv_email.getText().toString())) {
                    in_email.setError(getResources().getString(R.string.empty_email_error_signIn));
                    return;
                }

                if (AppConfig.getInstance().isEmailValid(tv_email.getText().toString())) {
                    // TODO : Do the actions to recovery the password


                    startActivity(new Intent(getApplicationContext(), LogIn.class));
                } else {
                    in_email.setError(getResources().getString(R.string.invalid_email_error_signIn));
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), LogIn.class));
    }

}
