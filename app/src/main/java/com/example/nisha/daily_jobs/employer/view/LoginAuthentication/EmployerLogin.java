package com.example.nisha.daily_jobs.employer.view.LoginAuthentication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nisha.daily_jobs.employer.controller.EmployerHome;
import static com.example.nisha.daily_jobs.employer.view.LoginAuthentication.EmployerSignUp.EMPLOYER_EMAIL;
import com.example.nisha.daily_jobs.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
//import com.example.nisha.daily_jobs.BuildConfig;

import java.util.List;


public class EmployerLogin extends AppCompatActivity {

    Button btnLogin;
    EditText textBoxUserName, textBoxPassword;
    TextView signUpLink;
    public static String EMPLOYER_NAME;

    String employerEmail="";

   // private static boolean isParseInitialized = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

           /* if(isParseInitialized==false) {
                Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                        .applicationId("ghjgjjh878564hgjgdgmmhf9848jhjhjjh")
                        .clientKey("gfhgfghhhfghfhggfgjkkkkk895674895689mdkdb768kkkkkrtyrtyrthn8746746")
                        .server("https://warm-refuge-85444.herokuapp.com/parse/")   // '/' important after 'parse'
                        .build());
                isParseInitialized=true;
            }*/

        setContentView(R.layout.activity_employer_login);

        Intent intent = getIntent();
        if (null != intent) {
            employerEmail= intent.getStringExtra(EMPLOYER_EMAIL);
            SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("employerEmail", employerEmail);
            editor.commit();
         }
        btnLogin = (Button) findViewById(R.id.buttonEmployerSignUp);
        textBoxPassword = (EditText) findViewById(R.id.textBoxPassword);
        textBoxUserName = (EditText) findViewById(R.id.textBoxEmployerName);
        signUpLink = (TextView) findViewById(R.id.signUpLink);

        signUpLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EmployerSignUp.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((textBoxUserName.getText().toString().equals("")) || textBoxPassword.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "UserName or Password is Empty",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("EmployerCredentials");
                    query.whereEqualTo("EmployerName", textBoxUserName.getText().toString());

                    //Password check
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> list, ParseException e) {
                            if (list!= null) {
                                Log.d("EmployerCredentials", "Retrieved  " + list.size() + " EmployerCredentials");
                                if(list.size() > 0) {
                                    ParseObject item = list.get(0);
                                    Log.d("item", item.toString());
                                    String Pass = (String) item.get("EmployerPassword");
                                    Log.d("Pass", Pass);
                                    if(Pass.equals(textBoxPassword.getText().toString())) {
                                        Log.d("Password Authenticated", "Access Allowed");

                                        Intent intent = new Intent(getApplicationContext(), EmployerHome.class);
                                        intent.putExtra(EMPLOYER_NAME, textBoxUserName.getText().toString());

                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),
                                                "UserName or Password Incorrect",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            } else {
                                Log.d("EmployerCredentials", "Error: " + e.getMessage());
                            }
                        }
                    });
                }
            }
        });
    }
   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
