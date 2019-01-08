package com.example.pendaftaranonlinepasien;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.pendaftaranonlinepasien.API.RetrofitClient;
import com.example.pendaftaranonlinepasien.API.RetrofitInterface;
import com.example.pendaftaranonlinepasien.Activities.Data_Pasien.DataPasienActivity;
import com.example.pendaftaranonlinepasien.Activities.Data_Pasien.RiwayatPasienActivity;
import com.example.pendaftaranonlinepasien.Activities.Pendaftaran_Pasien.DaftarBerobatActivity;
import com.example.pendaftaranonlinepasien.Utils.SharedPreferenceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    @BindView(R.id.drawer_layout) protected DrawerLayout mDrawer;
    @BindView(R.id.toolbar) protected Toolbar mToolbar;
    @BindView(R.id.nvView) protected NavigationView nvDrawer;
    @BindView(R.id.flContent) protected FrameLayout frameLayout;

    private ActionBarDrawerToggle drawerToggle;
    public RetrofitInterface retrofitInterface = RetrofitClient.getClient().create(RetrofitInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // Set up a Toolbar to replace ActionBar
        setSupportActionBar(mToolbar);
        // Setup Drawer View
        //setupDrawerContent(nvDrawer);
        nvDrawer.setNavigationItemSelectedListener(this);
        drawerToggle = setupDrawerToggle();
        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);
        /*Fragment fragment = new DaftarKontrolFragmen();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flContent, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();*/

    }

    private ActionBarDrawerToggle setupDrawerToggle(){
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_1) {
           startActivity(new Intent(getApplicationContext(), DataPasienActivity.class));
        } else if (id == R.id.nav_2) {
            startActivity(new Intent(getApplicationContext(), RiwayatPasienActivity.class));
        } else if (id == R.id.nav_3) {
                startActivity(new Intent(getApplicationContext(), DaftarBerobatActivity.class));
        } else if (id == R.id.nav_logout){
            logout();
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout(){
        SharedPreferenceUtils.getInstance(getApplicationContext()).clear();
    }
/*
    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }
*/
    /*public void selectDrawerItem(MenuItem menuItem){
        Fragment fragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()){
            case R.id.nav_fragment_1:
                fragmentClass = DaftarKontrolFragmen.class;
                break;
            case R.id.nav_fragment_2:
                fragmentClass = CariRiwayatFragment.class;
                break;
            default:
                fragmentClass = DaftarKontrolFragmen.class;
        }
        try{
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e){
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set Action Bar Title
        setTitle(menuItem.getTitle());
        // Close the NavDrawer
        mDrawer.closeDrawers();
    }*/
}
