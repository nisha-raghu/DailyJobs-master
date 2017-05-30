package com.example.nisha.daily_jobs.employer.view.LoginAuthentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nisha.daily_jobs.R;
import com.parse.ParseObject;


public class EmployerSignUp extends AppCompatActivity {

    Button btnSignUp;
    EditText textBoxUserName, textBoxPassword, textBoxConfirmPassword, textBoxEmail,textBoxPhone;
    TextView signInLink;
    public static String EMPLOYER_EMAIL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_employer_sign_up);

        btnSignUp = (Button) findViewById(R.id.buttonEmployerSignUp);
        textBoxPassword = (EditText) findViewById(R.id.textBoxPassword);
        textBoxUserName = (EditText) findViewById(R.id.textBoxEmployerName);
        textBoxConfirmPassword = (EditText) findViewById(R.id.textBoxConfirmPassword);
        textBoxEmail = (EditText) findViewById(R.id.textBoxEmail);
        signInLink = (TextView) findViewById(R.id.signInLink);
        textBoxPhone = (EditText) findViewById(R.id.textBoxPhone);

        signInLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EmployerLogin.class);
                startActivity(intent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(textBoxPassword.getText().toString(), " "+textBoxConfirmPassword.getText().toString());
                if ((textBoxUserName.getText().toString().equals("")) || textBoxPassword.getText().toString().equals("") ||
                        textBoxEmail.getText().toString().equals("") || textBoxConfirmPassword.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "All fields must be filled",
                            Toast.LENGTH_LONG).show();
                }


                else if(!textBoxPassword.getText().toString().equals( textBoxConfirmPassword.getText().toString())) {
                    Toast.makeText(getApplicationContext(),
                            "Password are not same",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    ParseObject testObject = new ParseObject("EmployerCredentials");
                    testObject.put("EmployerName", textBoxUserName.getText().toString());
                    testObject.put("EmployerEmail", textBoxEmail.getText().toString());
                    testObject.put("EmployerPassword", textBoxPassword.getText().toString());
                    testObject.put("EmployerPhone",textBoxPhone.getText().toString());
                    testObject.saveInBackground();

                    Intent intent = new Intent(getApplicationContext(), EmployerLogin.class);
                    intent.putExtra(EMPLOYER_EMAIL, textBoxEmail.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }

}
