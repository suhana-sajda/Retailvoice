package com.retailvoice.sellerapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.TextView;

import com.retailvoice.sellerapp.models.Cart;
import com.retailvoice.sellerapp.MyApplication;
import com.retailvoice.sellerapp.R;
import com.retailvoice.sellerapp.fragments.StockFragment;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RegisterActivity extends BaseActivity {

    MyApplication myApp;
//    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DrawerLayout mDrawerLayout;
    TextView textItemCount, textTotal, tvCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myApp = MyApplication.getApplication();

        textItemCount = (TextView) findViewById(R.id.tvItemCount);
        textTotal = (TextView) findViewById(R.id.tvTotal);
        tvCheckout = (TextView) findViewById(R.id.tvCheckout);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        final ActionBar actionBar = getSupportActionBar();

        Realm.init(getApplicationContext());

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

//        tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(viewPager);

//        if (actionBar != null) {
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
        tvCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, CheckoutActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCart();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        adapter.addFragment(new KeypadFragment(), "KEYPAD");
        adapter.addFragment(new StockFragment(), "STOCK");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public void updateCart(){ updateCart(myApp.getCart());}

    public void updateCart(Cart cart) {
        int itemCount = cart.getItemCount();
        textItemCount.setText(Integer.toString(itemCount));
        float cartTotal = cart.getTotal();
        textTotal.setText( getResources().getString(R.string.cart_price, cartTotal) );

    }

    // Normalise to 2 decimal places
    String normalise(float number){
        String floatAsString = "" + Math.round(number * 100)/100.00f;
        if(floatAsString.matches(".+\\.."))
            return floatAsString + "0";

        return floatAsString;
    }
}
