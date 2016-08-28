package ub.passwordmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ub.passwordmanager.R;

public class LogIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

//        final ImageView iv = (ImageView) findViewById(R.id.iv_my_visibility_login);
//        iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getBaseContext(), "Button", Toast.LENGTH_SHORT).show();
//                if ("Visible".equals(iv.getTag().toString())) {
//                    iv.setBackgroundResource(R.drawable.ic_visibility_off);
//                    iv.setTag("NotVisible");
//                }else{
//                    iv.setBackgroundResource(R.drawable.ic_visibility);
//                    iv.setTag("Visible");
//                }
//
//            }
//        });

        Button logiButton = (Button) findViewById(R.id.bt__login);
        logiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
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
