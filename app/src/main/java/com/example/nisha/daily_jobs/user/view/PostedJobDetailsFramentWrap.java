package com.example.nisha.daily_jobs.user.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by nisha on 4/22/2017.
 */

public class PostedJobDetailsFramentWrap extends FragmentActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PostedJobDetails fragmentS1 = new PostedJobDetails();
        getSupportFragmentManager().beginTransaction().add(android.R.id.content, fragmentS1).commit();

    }
}
