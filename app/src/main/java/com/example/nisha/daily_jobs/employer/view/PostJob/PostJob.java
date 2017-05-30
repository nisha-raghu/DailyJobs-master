package com.example.nisha.daily_jobs.employer.view.PostJob;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.app.Dialog;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.example.nisha.daily_jobs.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class PostJob extends Fragment {
    String employerName;
    String employerEmail;
    String employerPhone;
    public PostJob() {}

    Button btnPostJob, btnAddDate;
    EditText ETJobTitle, ETCompanyname, ETTimeDuration,ETPayperHr,ETJobLocation;
    DatePicker DPJobDate;

    private int year;
    private int month;
    private int day;
    private TextView DisplayDate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View jobsView = inflater.inflate(R.layout.fragment_post_job, container, false);
        ETJobTitle = (EditText)jobsView.findViewById(R.id.editTextJobTitle);
        ETCompanyname = (EditText)jobsView.findViewById(R.id.editTextCompanyName);
        ETPayperHr = (EditText)jobsView.findViewById(R.id.editTextJobDesc);
        ETJobLocation = (EditText)jobsView.findViewById(R.id.editTextJobLocation);
        btnPostJob = (Button)jobsView.findViewById(R.id.buttonPostJob);
        DPJobDate = (DatePicker)jobsView.findViewById(R.id.datePicker);
        ETTimeDuration = (EditText) jobsView.findViewById(R.id.editTextNumPositions);
        DisplayDate = (TextView) jobsView.findViewById(R.id.textViewDisplayDate);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyData", getActivity().MODE_PRIVATE);
        employerName = sharedPreferences.getString("employerName", "hk");
       // Intent intent = getIntent();
     //   employerEmail = sharedPreferences.getString("employerEmail","hk");

        //to get the employee email and phone
        ParseQuery<ParseObject> query = ParseQuery.getQuery("EmployerCredentials");
        query.whereEqualTo("EmployerName", employerName);
        query.findInBackground(new FindCallback<ParseObject>() {
                                   @Override
                                   public void done(List<ParseObject> list, ParseException e) {
                                       if (list != null) {
                                             if (list.size() > 0) {
                                               ParseObject item = list.get(0);
                                               employerEmail = (String) item.get("EmployerEmail");
                                               employerPhone = (String) item.get("EmployerPhone");
                                           }
                                       }
                                   }
                               });


        // set current date into textview
        DisplayDate.setText(new StringBuilder().append(year).append("-").append(" ").append(month + 1).append("-").append(day));

        btnAddDate = (Button)jobsView.findViewById(R.id.btnAddDate);
        btnAddDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DialogFragment picker = new DatePickerFragment();
                picker.show(getActivity().getFragmentManager(), "datePicker");
            }

        });
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)(getActivity().getSystemService(Context.INPUT_METHOD_SERVICE));
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        btnPostJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((ETJobTitle.getText().toString().matches("")) || ETJobLocation.getText().toString().matches("") ||
                        ETPayperHr.getText().toString().matches("") || ETCompanyname.getText().toString().matches("")) {
                    Toast.makeText(getActivity(),
                            "All fields must be filled",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    ParseObject jobObject = new ParseObject("JobDetails");
                    jobObject.put("jobName", ETJobTitle.getText().toString());

                    jobObject.put("jobDesc", ETCompanyname.getText().toString());
                    jobObject.put("jobLocation", ETJobLocation.getText().toString());
                    jobObject.put("expRequired", 4);
                    jobObject.put("payPerHour", ETPayperHr.getText().toString());

                    jobObject.put("jobStartDate", DisplayDate.getText().toString());
                    jobObject.put("jobTimeDuration", ETTimeDuration.getText().toString());

                    jobObject.put("employerName", employerName);
                    jobObject.put("employerEmail", employerEmail);
                    jobObject.put("employerPhone", employerPhone);


                    jobObject.saveInBackground();
                    Toast.makeText(getActivity(), "Job posted successfully", Toast.LENGTH_LONG).show();
                }
            }
        });
        return jobsView;
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onResume() {
        super.onResume();


    }

    public class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

// Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar c = Calendar.getInstance();
            c.set(year, month, day);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = sdf.format(c.getTime());
            DisplayDate.setText(formattedDate);

        }
    }
}

