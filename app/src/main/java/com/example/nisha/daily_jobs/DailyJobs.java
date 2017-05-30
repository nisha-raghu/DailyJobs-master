package com.example.nisha.daily_jobs;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.nisha.daily_jobs.user.view.PostedJobDetailsFramentWrap;
import com.example.nisha.daily_jobs.employer.view.LoginAuthentication.EmployerLogin;
import com.parse.Parse;


public class DailyJobs extends ActionBarActivity {

    Button btnCandidate, btnEmployer;
    private static boolean isParseInitialized = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_depot);
        btnCandidate = (Button) findViewById(R.id.buttonCandidate);
        btnEmployer = (Button) findViewById(R.id.buttonEmployer);

        if(isParseInitialized==false) {
            Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                    .applicationId("mynishAppId672")
                    .clientKey("mynishClientId4562")
                    .server("https://warm-refuge-85444.herokuapp.com/parse/")   // '/' important after 'parse'
                    .build());
            isParseInitialized=true;
        }

        btnEmployer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DailyJobs.this, EmployerLogin.class);
                startActivity(intent);
            }
        });

        btnCandidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PostedJobDetailsFramentWrap.class);
                startActivity(intent);
            }
        });
    }
}
