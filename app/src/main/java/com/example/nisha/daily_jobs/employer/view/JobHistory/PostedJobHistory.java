package com.example.nisha.daily_jobs.employer.view.JobHistory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nisha.daily_jobs.DailyJobs;
import com.example.nisha.daily_jobs.R;

import com.example.nisha.daily_jobs.employer.controller.EmployerHome;
import com.example.nisha.daily_jobs.employer.model.DAO.EmployerHistory;
import com.example.nisha.daily_jobs.employer.view.PostJob.PostJob;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class JobDetails {
    private String ID;
    private String jobTitle;
    private String jobDesc;
    private String jobStartDate;
    private String jobLocation;
    private String timeDuration, payPerHour,employerName,employerEmail,employerPhone;

    public JobDetails(@NonNull String id, @NonNull String jobTitle,@NonNull String jobDesc,@NonNull String jobLocation,String timeDuration,@NonNull String payPerHour, String jobStartDate,
                         String employerName, String employerEmail, String employerPhone ) {
        this.ID=id;
        this.jobTitle = jobTitle;
        this.jobDesc = jobDesc;
        this.jobLocation = jobLocation;
        this.timeDuration = timeDuration;
        this.payPerHour = payPerHour;
        this.jobStartDate = jobStartDate;
        this.employerName= employerName;
        this.employerEmail = employerEmail;
        this.employerPhone = employerPhone;
    }

    public String getId() {
        return ID;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public String getStartDate() {
        return jobStartDate;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public String getPayPerHour() {
        return payPerHour;
    }

    public String gettimeDuration() {
        return timeDuration;
    }

    public String getEmployerName() {
        return employerName;
    }
    public String getEmployerEmail() {
        return employerEmail;
    }
    public String getEmployerPhone() {
        return employerPhone;
    }



}

public class PostedJobHistory extends Fragment {
    private ExpandableListView expandableListView;
    private List<Map<String, String>> savedjobsData=null;
    private List<String> groupData;
    private ArrayList<JobDetails> jobDetails;
    Fragment myfragment;

    String employerName;
    ImageView img;
    ExpandableListAdapter expListAdapter;
    ArrayList<String> groupString;
    Map<String, List<String>> childData;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final LayoutInflater inflateAgain= inflater;
        final View jobsView = inflater.inflate(R.layout.fragment_posted_job_history, container, false);

        myfragment = this;

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyData", getActivity().MODE_PRIVATE);
        employerName = sharedPreferences.getString("employerName", "hk");

        //Refresh the Fragment
        img=(ImageView) jobsView.findViewById(R.id.refreshId);
     //   ImageView img = (ImageView) getView().findViewById(R.id.refreshId);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getActivity(),
                        "Refresh is clicked!!",
                        Toast.LENGTH_LONG).show();

                //added  to refresh the current fragment
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(myfragment).attach(myfragment).commit();
        expListAdapter.notifyDataSetChanged();

            }
        });


        if(jobDetails!=null){
            jobDetails.clear();
        }
        jobDetails=new ArrayList<JobDetails>();
            EmployerHistory jobsQuery=new EmployerHistory();
            for(ParseObject o:jobsQuery.getJobs(employerName)) {
                JobDetails _jd = new JobDetails(o.getObjectId(), o.getString("jobName"), o.getString("jobDesc"), o.getString("jobLocation"),
                        o.getString("jobTimeDuration"), o.getString("payPerHour"), o.getString("jobStartDate"),o.getString("employerName"),o.getString("employerEmail"),o.getString("employerPhone"));
                jobDetails.add(_jd);
            }

        groupString=new ArrayList<String>();
        for(JobDetails jd:jobDetails){
            groupString.add(jd.getJobTitle());
        }

        childData=new HashMap<String, List<String>>();
        for(JobDetails jd:jobDetails){
            List<String> _temp=new ArrayList<String>();
            _temp.add("Description: "+jd.getJobDesc());
            _temp.add("Location: "+jd.getJobLocation());
            _temp.add("Time Duration(in hours): "+jd.gettimeDuration());
            _temp.add("Pay Per Hour: "+jd.getPayPerHour());
            _temp.add("Employer Name: "+jd.getEmployerName());
            _temp.add("Employer Email: "+jd.getEmployerEmail());
            _temp.add("Employer Phone: "+jd.getEmployerPhone());
            childData.put(jd.getJobTitle(),_temp);
        }
        expandableListView = (ExpandableListView) jobsView.findViewById(R.id.expandableListViewJobHistory);
        expListAdapter = new ExpandableListAdapter(getActivity(), groupString, childData);

        expandableListView.setAdapter(expListAdapter);
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        expandableListView.setIndicatorBounds(width - getDipsFromPixel(35), width
                - getDipsFromPixel(5));
        expListAdapter.notifyDataSetChanged();

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

    private class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Activity context;
        private Map<String, List<String>> laptopCollections;
        private List<String> laptops;

        public ExpandableListAdapter(Activity context, List<String> laptops,
                                     Map<String, List<String>> laptopCollections) {
            this.context = context;
            this.laptopCollections = laptopCollections;
            this.laptops = laptops;
        }

        public Object getChild(int groupPosition, int childPosition) {
            return laptopCollections.get(laptops.get(groupPosition)).get(childPosition);
        }

        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        public View getChildView(final int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            final String laptop = (String) getChild(groupPosition, childPosition);
            LayoutInflater inflater = context.getLayoutInflater();

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.childlayout_job_history, null);
            }

            TextView item = (TextView) convertView.findViewById(R.id.text4);

            item.setText(laptop);
            return convertView;
        }

        public int getChildrenCount(int groupPosition) {
            return laptopCollections.get(laptops.get(groupPosition)).size();
        }

        public Object getGroup(int groupPosition) {
            return laptops.get(groupPosition);
        }

        public int getGroupCount() {
            return laptops.size();
        }

        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            String laptopName = (String) getGroup(groupPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.grouplayout_job_history,null);
            }
            TextView item = (TextView) convertView.findViewById(R.id.text3);
            item.setTypeface(null, Typeface.BOLD);
            item.setText(laptopName);
            return convertView;
        }

        public boolean hasStableIds() {
            return true;
        }

        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
    private void setGroupIndicatorToRight() {
        /* Get the screen width */
    }
    public int getDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }
}