package com.example.nisha.daily_jobs.employer.view.Settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nisha.daily_jobs.R;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class EmployerSettings extends Fragment {
    String employerName;
    Button btnUpdate;
    EditText UpdateUserName, UpdatePassword, UpdateEmail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View empSettings = inflater.inflate(R.layout.fragment_employer_settings, container, false);
        UpdateUserName = (EditText)empSettings.findViewById(R.id.editTextUpdateUsername);
        UpdatePassword = (EditText)empSettings.findViewById(R.id.editTextUpdatePassowrd);
        UpdateEmail = (EditText)empSettings.findViewById(R.id.editTextUpdateEmail);
        btnUpdate = (Button)empSettings.findViewById(R.id.buttonUpdate);

        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyData", getActivity().MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        employerName = sharedPreferences.getString("employerName", "hk");

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(UpdateUserName.getText().toString().equals("") && UpdateEmail.getText().toString().equals("") && UpdatePassword.getText().toString().equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Nothing to Update", Toast.LENGTH_SHORT).show();
                }
                else {
                    final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();

                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("Your Information will be updated");

                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                    ParseQuery<ParseObject> query = ParseQuery.getQuery("EmployerCredentials");
                                    query.whereEqualTo("EmployerName", employerName);
                                    query.findInBackground(new FindCallback<ParseObject>() {
                                        @Override
                                        public void done(List<ParseObject> list, com.parse.ParseException e) {
                                            if (e == null) {
                                                for (final ParseObject nameObj : list) {
                                                    if (!UpdateEmail.getText().toString().equals("")) {
                                                        nameObj.put("EmployerEmail", UpdateEmail.getText().toString());
                                                    }
                                                    if (!UpdateUserName.getText().toString().equals("")) {
                                                        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("JobDetails");
                                                        query2.whereEqualTo("employerName", employerName);

                                                        query2.findInBackground(new FindCallback<ParseObject>() {
                                                            @Override
                                                            public void done(List<ParseObject> list2, com.parse.ParseException e) {
                                                                if (e == null) {
                                                                    for (ParseObject nameObj2 : list2) {
                                                                        nameObj2.put("employerName", UpdateUserName.getText().toString());
                                                                        nameObj2.saveInBackground();
                                                                    }
                                                                    UpdateUserName.setText("");
                                                                }
                                                            }
                                                        });

                                                        nameObj.put("EmployerName",UpdateUserName.getText().toString());
                                                        employerName=UpdateUserName.getText().toString();

                                                        editor.putString("employerName",employerName);
                                                        editor.commit();
                                                    }
                                                    if (!UpdatePassword.getText().toString().equals("")) {
                                                        nameObj.put("EmployerPassword", UpdatePassword.getText().toString());
                                                    }
                                                    nameObj.saveInBackground();

                                                    UpdateEmail.setText("");
                                                    UpdatePassword.setText("");
                                                    }
                                                }
                                                else
                                                {
                                                    Log.d("Post retrieval", "Error: " + e.getMessage());
                                                }
                                            }
                                        });
                                        dialog.dismiss();
                                        Toast.makeText(getActivity().getApplicationContext(), "Information Updated",Toast.LENGTH_SHORT).show();
                                    }
                                });
                    alertDialog.show();
                }
            }}
        );
        return empSettings;
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
}
