package com.retailvoice.sellerapp.activities;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.retailvoice.sellerapp.R;

public class BaseActivity extends AppCompatActivity {
    DrawerLayout fullView;
    @Override
    public void setContentView(int layoutResID) {
        fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
//        setTitle("Activity Title");
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override public boolean onNavigationItemSelected(MenuItem menuItem) {
//                Snackbar.make(content, menuItem.getTitle() + " pressed", Snackbar.LENGTH_LONG).show();
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        Intent i = new Intent(getApplicationContext(),
                                RegisterActivity.class);
                        startActivity(i);
                        finish();
                        break;
                    case R.id.nav_items:
                        i = new Intent(getApplicationContext(),
                                ItemsActivity.class);
                        startActivity(i);
                        finish();
                        break;
                    case R.id.nav_sales:
                        i = new Intent(getApplicationContext(),
                                SaleActivity.class);
                        startActivity(i);
                        finish();
                        break;
                    case R.id.nav_about_us:
                        i = new Intent(getApplicationContext(),
                                AboutActivity.class);
                        startActivity(i);
                        finish();
                        break;
                    case R.id.nav_privacy_policy:
                        i = new Intent(getApplicationContext(),
                                PrivacyActivity.class);
                        startActivity(i);
                        finish();
                        break;
                    case R.id.nav_orders:
                        i = new Intent(getApplicationContext(),
                                SavedOrdersActivity.class);
                        startActivity(i);
                        finish();
                        break;
                    default:
                        Snackbar.make(fullView, menuItem.getTitle() + " pressed", Snackbar.LENGTH_LONG).show();
                        break;
                }
                menuItem.setChecked(true);
                fullView.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            fullView.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
