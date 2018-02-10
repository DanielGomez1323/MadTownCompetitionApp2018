package com.example.pollo.madtowncompetitionapp2018;

import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TabbedScouting extends AppCompatActivity {
    SQLiteDatabase myDB = null;
    int autoID;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_scouting);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues c = new ContentValues();
                Intent i = getIntent();
                //String scoutName = i.getStringExtra("scoutName");
                String teamColor = i.getStringExtra("teamColor");
                String teamNumber = i.getStringExtra("teamNumber");
                String matchNumber = i.getStringExtra("matchNumber");

                List<Fragment> f = mSectionsPagerAdapter.getFragments();/*getSupportFragmentManager().getFragments();*/
                AutoFragment autoFragment = (AutoFragment) f.get(0);/*getSupportFragmentManager().findFragmentByTag(f.get(0).getTag());*/
                Bundle ab = autoFragment.getData();
                String robotPosition = ab.getString("robotPosition");
                String baseLineCrossed = ab.getString("baseLineCrossed");
                String autoHighCubePlaced = ab.getString("autoHighCubePlaced");
                String autoLowCubePlaced = ab.getString("autoLowCubePlaced");

                TeleopFragment teleopFragment = (TeleopFragment) f.get(1);/*getSupportFragmentManager().findFragmentByTag(f.get(1).getTag());*/
                Bundle tb = teleopFragment.getData();
                String highCubesPlaced = tb.getString("highCubesPlaced");
                String lowCubesPlaced = tb.getString("lowCubesPlaced");
                String vaultCubesPlaced = tb.getString("vaultCubesPlaced");
                String climbTime = tb.getString("climbTime");
                String climbSuccess = tb.getString("climbSuccess");

                NotesFragment notesFragment = (NotesFragment) f.get(2);/*getSupportFragmentManager().findFragmentByTag(f.get(2).getTag());*/
                Bundle nb = notesFragment.getData();
                String robotNotes = nb.getString("robotNotes");

                //c.put("scoutName", scoutName);
                c.put("teamColor", teamColor);

                c.put("teamNumber", teamNumber);
                c.put("matchNumber", matchNumber);

                c.put("robotPosition", robotPosition);
                c.put("baseLineCrossed", baseLineCrossed);
                c.put("autoHighCubePlaced", autoHighCubePlaced);
                c.put("autoLowCubePlaced", autoLowCubePlaced);

                c.put("highCubesPlaced", highCubesPlaced);
                c.put("lowCubesPlaced", lowCubesPlaced);
                c.put("vaultCubesPlaced", vaultCubesPlaced);
                c.put("climbTime", climbTime);
                c.put("climbSuccess", climbSuccess);

                c.put("robotNotes", robotNotes);


                myDB = openOrCreateDatabase("FRC", MODE_PRIVATE, null);
                try {
                    myDB.insertOrThrow("PowerUp", null, c);
                }catch (SQLException s){
                    Toast.makeText(getApplicationContext(), "Oopsie Doopsie", Toast.LENGTH_SHORT).show();
                }
                if (myDB != null){
                    myDB.close();
                }
                Intent list = new Intent(getApplicationContext(), UploadData.class);
                startActivity(list);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tabbed_scouting, menu);
        return true;
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tabbed_scouting, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     * @author NotTaylorAnderson
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private List<Fragment> fragments = new ArrayList<>(3);
        public List<Fragment> getFragments(){return fragments;}

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch(position){
                case 0:
                    AutoFragment autoFragment = AutoFragment.newInstance();
                    fragments.add(autoFragment);
                    return autoFragment;
                case 1:
                    TeleopFragment teleopFragment = TeleopFragment.newInstance();
                    fragments.add(teleopFragment);
                    return teleopFragment;
                case 2:
                    NotesFragment notesFragment = NotesFragment.newInstance();
                    fragments.add(notesFragment);
                    return notesFragment;
            }
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Auto";
                case 1:
                    return "Teleop";
                case 2:
                    return "Notes";
            }
            return null;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }
    }
}
