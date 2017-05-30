package com.example.nisha.daily_jobs.employer.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.example.nisha.daily_jobs.DailyJobs;
import com.example.nisha.daily_jobs.employer.view.Settings.EmployerSettings;
import com.example.nisha.daily_jobs.employer.view.PostJob.PostJob;
import com.example.nisha.daily_jobs.employer.view.JobHistory.PostedJobHistory;
import com.example.nisha.daily_jobs.R;

import java.util.List;
import java.util.Locale;
import static com.example.nisha.daily_jobs.employer.view.LoginAuthentication.EmployerLogin.EMPLOYER_NAME;

public class EmployerHome extends ActionBarActivity implements ActionBar.TabListener {

    SectionsPagerAdapter mSectionsPagerAdapter;

    String employerName = "";

    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_candidate_home);

        Intent intent = getIntent();
        if (null != intent) {
            employerName = intent.getStringExtra(EMPLOYER_NAME);
          //  employerEmail= intent.getStringExtra(EMPLOYER_EMAIL);
            SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("employerName", employerName);
          //  editor.putString("employerEmail", employerEmail);
            editor.commit();
        }

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // Add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {

            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }
  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_candidate_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_home) {
            Intent intent = new Intent(EmployerHome.this, DailyJobs.class);
            startActivity(intent);
            return true;
        }
        if(id==R.id.logout) {
           finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // switch to the corresponding page when the given tab is selected,
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        int position = tab.getPosition();

        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        if(fragmentList == null || fragmentList.size() == 0) {
            return;
        }
        for (Fragment fragment : fragmentList) {
            if(fragment instanceof PostedJobHistory) {
            /*    Fragment frg = null;
                Class fragmentClass;
                fragmentClass =  PostedJobHistory.class;*/
                //((PostedJobHistory) fragment).updateFragmentMethod();
            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
           switch (position){
                case 0:
                    return new PostJob();
                case 1:{
                    return new PostedJobHistory();
                }
                case 2:{
                    return new EmployerSettings();
                }
                default:
                    break;
            }
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.Employer_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.Employer_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.Employer_section4).toUpperCase(l);

            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

    }
}
