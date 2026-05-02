package com.example.pb_final;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        final EditText userName = findViewById(R.id.username_log);
        final EditText passWord = findViewById(R.id.password_log);
        final EditText Email = findViewById(R.id.email_log);
        userName.setText("");
        Button logIn = findViewById(R.id.btnSub_log);
        Button signIn = findViewById(R.id.btnReg_log);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Log = new Intent(Login.this, SignUp.class);
                startActivity(Log);
                finish();

            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = userName.getText().toString().trim();
                final String password = passWord.getText().toString().trim();
                final String email = Email.getText().toString();

                if (!username.equals("") && !password.equals("") && !email.equals("")) {


                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[3];
                            field[0] = "username";
                            field[1] = "password";
                            field[2] = "email";

                            String[] data = new String[3];
                            data[0] = username;
                            data[1] = password;
                            data[2] = email;

                            PutData putData = new PutData("http://10.4.8.255/loginRegister/login.php", "POST", field, data);

                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    final String result = putData.getResult();


                                    Handler handler = new Handler(Looper.getMainLooper());
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(Login.this, result, Toast.LENGTH_SHORT).show();
                                            if (result.equals("Login Success")) {
                                                Intent log = new Intent(Login.this, MainActivity.class);
                                                startActivity(log);
                                                finish();
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    }).start();

                } else {
                    Toast.makeText(Login.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}