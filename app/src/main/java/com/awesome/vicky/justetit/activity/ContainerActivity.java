package com.awesome.vicky.justetit.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.awesome.vicky.justetit.R;
import com.awesome.vicky.justetit.fragment.About;
import com.awesome.vicky.justetit.fragment.Help;
import com.awesome.vicky.justetit.fragment.LeftOvers;
import com.awesome.vicky.justetit.fragment.RecipesList;
import com.awesome.vicky.justetit.util.NetworkStateReceiver;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class ContainerActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private NetworkStateReceiver networkStateReceiver = new NetworkStateReceiver();
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        init();
        setRecipeFragment();
    }

    private void setRecipeFragment() {
        Fragment fragment = new RecipesList();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        setTitle("Recipes");
    }

    private void init() {
        initToolbar();
        initDrawrLayout();
        initNavView();
        initAlertDialog();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initDrawrLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close
        );
        drawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    private void initNavView() {
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                selectDrawerItem(menuItem);
                return true;
            }
        });
    }

    private void initAlertDialog() {
        builder = new AlertDialog.Builder(this);
        builder.setTitle("No Internet connection!");
        builder.setMessage("Please check your network settings..");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(new Intent(Settings.ACTION_SETTINGS));
                dialog.cancel();
            }
        });
        alertDialog = builder.create();
    }

    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.recipe_list:
                fragmentClass = RecipesList.class;
                break;
            case R.id.left_overs:
                fragmentClass = LeftOvers.class;
                break;
            case R.id.about:
                fragmentClass = About.class;
                break;
            case R.id.help:
                fragmentClass = Help.class;
                break;
            default:
                fragmentClass = RecipesList.class;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.container_activity_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "I found this cool app for recipres: JUST ET IT!" +
                    "\n Check it out at: http://justetit.netai.net/");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*EventBus.getDefault().register(this); // register EventBus
        registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*EventBus.getDefault().unregister(this); // unregister EventBus
        unregisterReceiver(networkStateReceiver);*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

   /* *//**
     * called from broadcast receiver when post is called with eventbus
     *
     * @param event
     *//*
    public void onEventMainThread(NetworkStateChanged event) {
        if (!event.isInternetConnected()) {
            *//*if (!alertDialog.isShowing())
                alertDialog.show();*//*
            Toast.makeText(ContainerActivity.this, "Check your network connection", Toast.LENGTH_SHORT).show();
        }
    }*/
}
