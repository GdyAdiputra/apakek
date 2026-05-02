package com.example.pb_final;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final EditText userName = findViewById(R.id.usermameTxt_sign);
        final EditText passWord = findViewById(R.id.passwordTxt_sign);
        final EditText Email = findViewById(R.id.emailTxt_sign);
        final EditText Ktp = findViewById(R.id.ktp_sign);
        final EditText Phone = findViewById(R.id.phone_sign);
        final EditText PhoneEme = findViewById(R.id.phoneEme_sign);
        final EditText DOB = findViewById(R.id.dob_sign);
        final EditText Status = findViewById(R.id.status_sign);
        final EditText NamaIbu = findViewById(R.id.namaIbu_sign);
        final EditText NamaAyah = findViewById(R.id.namaAyah_sign);

        Button signIn = findViewById(R.id.btnsignin_sign);
        Button logIn = findViewById(R.id.btnLog_sign);

        //going back to log in
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Log = new Intent(SignUp.this, Login.class);
                startActivity(Log);
                finish();

            }
        });


        //signing up and put data to database
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = userName.getText().toString().trim();
                final String password = passWord.getText().toString().trim();
                final String email = Email.getText().toString();
                final String ktp = Ktp.getText().toString();
                final String phone = Phone.getText().toString();
                final String phoneEme = PhoneEme.getText().toString();
                final String dob = DOB.getText().toString();
                final String status = Status.getText().toString();
                final String namaIbu = NamaIbu.getText().toString();
                final String namaAyah = NamaAyah.getText().toString();



                if (!username.isEmpty() && !password.isEmpty() && !email.isEmpty() &&
                        !ktp.isEmpty() && !phone.isEmpty() && !phoneEme.isEmpty() &&
                        !dob.isEmpty() && !status.isEmpty() && !namaIbu.isEmpty() && !namaAyah.isEmpty()) {


                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[10];
                            field[0] = "username";
                            field[1] = "password";
                            field[2] = "email";
                            field[3] = "KTP";
                            field[4] = "phone";
                            field[5] = "phoneEmergency";
                            field[6] = "namaIbu";
                            field[7] = "namaAyah";
                            field[8] = "dateOfBirth";
                            field[9] = "status";


                            String[] data = new String[10];
                            data[0] = username;
                            data[1] = password;
                            data[2] = email;
                            data[3] = ktp;
                            data[4] = phone;
                            data[5] = phoneEme;
                            data[6] = namaIbu;
                            data[7] = namaAyah;
                            data[8] = dob;
                            data[9] = status;



                            PutData putData = new PutData("http://10.4.8.255/loginRegister/signup.php", "POST", field, data);

                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    final String result = putData.getResult();


                                    Handler handler = new Handler(Looper.getMainLooper());
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(SignUp.this, result, Toast.LENGTH_SHORT).show();
                                            if (result.equals("Sign Up Success")) {
                                                Intent log = new Intent(SignUp.this, Login.class);
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
                    Toast.makeText(SignUp.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}