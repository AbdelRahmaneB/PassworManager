package ub.passwordmanager.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import ub.passwordmanager.R;

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
                // TODO : Do the actions to recovery the password
                startActivity(new Intent(getApplicationContext(),LogIn.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),LogIn.class));
    }
}
