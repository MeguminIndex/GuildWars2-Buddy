package wbeck.guildwars2buddy;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;

import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import wbeck.guildwars2buddy.Fragments.AccountFragment;
import wbeck.guildwars2buddy.Fragments.DailyFragment;
import wbeck.guildwars2buddy.Fragments.HomeFragment;
import wbeck.guildwars2buddy.Fragments.SettingsFragment;
import wbeck.guildwars2buddy.Gw2_API.Gw2AccoutJsonParse;

//https://romannurik.github.io/AndroidAssetStudio/index.html

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    String src = "https://render.guildwars2.com/file/C6110F52DF5AFE0F00A56F9E143E9732176DDDE9/65015.png";

    HomeFragment homefragment = null;
    SettingsFragment settingfragment = null;
    DailyFragment dailyFragment = null;
    AccountFragment accountFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //load defualt fragment page on app startup
        if (savedInstanceState == null)
        {
             homefragment = new HomeFragment().newInstance("d", "d");
            if (homefragment != null) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.details, homefragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

         NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        SharedPreferences userData = getSharedPreferences("userData", Context.MODE_PRIVATE);
        UserData.apiKey = userData.getString("key","");




//            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
//            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//            if (networkInfo != null && networkInfo.isConnected()) {
//                //  new DownloadImageTask(IM).execute(src);
//
//
//                TextView accoutTV = (TextView) findViewById(R.id.AccoutName);
//                if(accoutTV != null)
//                new Gw2AccoutJsonParse(accoutTV).execute("https://api.guildwars2.com/v2/account");
//
//            }
//            else
//            {
//                String msg = getResources().getString(R.string.noConnection);
//                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
//
//            }


        //Storage.writeTxtFile("test","testData", getApplicationContext());

        //String temp;

        //temp = Storage.openTxtFile("test",getApplicationContext());


    }






    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            //  new DownloadImageTask(IM).execute(src);


            TextView accoutTV = (TextView) findViewById(R.id.AccoutName);
            if(accoutTV != null && !UserData.apiKey.isEmpty())
                new Gw2AccoutJsonParse(accoutTV).execute("https://api.guildwars2.com/v2/account");

        }
        else
        {
            String msg = getResources().getString(R.string.noConnection);
            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();

        }

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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //Fragment fragment  = null;
        //Class fragmentClass = null;

        if (id == R.id.nav_home) {
            // Handle the camera action


            if (homefragment != null) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.details, homefragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
            else
            {
                homefragment = new HomeFragment().newInstance("d", "d");

                if (homefragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.details, homefragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }

        } else if (id == R.id.nav_dailys) {
            if (dailyFragment != null) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.details, dailyFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
            else
            {
                dailyFragment = new DailyFragment().newInstance();

                if (dailyFragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.details, dailyFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        } else if (id == R.id.nav_account) {

            if (accountFragment != null) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.details, accountFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
            else
            {
                accountFragment = new AccountFragment().newInstance("d", "d");

                if (homefragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.details, accountFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }

        } else if (id == R.id.nav_settings) {

            if (settingfragment != null) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.details, settingfragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
            else
            {



                settingfragment = new SettingsFragment().newInstance("d", "d");

                if (settingfragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.details, settingfragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }

        } else if (id == R.id.nav_Reminders) {




        } else if (id == R.id.nav_send) {

        }

        //Set menuItem checked and title
        item.setChecked(true);
        setTitle(item.getTitle());
        //close navigation view
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }



    //Settings fragment event handlers
    public void saveApiKey(View view)
    {
        EditText edTxt = (EditText) findViewById(R.id.apiKeyValue);
        SharedPreferences userData = getSharedPreferences("userData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userData.edit();
        editor.putString("key", edTxt.getText().toString());
        editor.commit();

        UserData.apiKey = edTxt.getText().toString();

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            //  new DownloadImageTask(IM).execute(src);


            TextView accoutTV = (TextView) findViewById(R.id.AccoutName);
            if(accoutTV != null)
                new Gw2AccoutJsonParse(accoutTV).execute("https://api.guildwars2.com/v2/account");




        }
        else
        {
            String msg = getResources().getString(R.string.noConnection);
            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();

        }

    }






}
