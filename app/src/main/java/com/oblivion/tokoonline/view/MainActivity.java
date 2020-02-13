package com.oblivion.tokoonline.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.oblivion.tokoonline.R;
import com.oblivion.tokoonline.Utils.NetworkConnection;
import com.oblivion.tokoonline.fragment.AccountFragment;
import com.oblivion.tokoonline.fragment.FavoriteFragment;
import com.oblivion.tokoonline.fragment.HomeFragment;
import com.oblivion.tokoonline.fragment.NoConnectionFragment;
import com.oblivion.tokoonline.fragment.SellFragment;

public class MainActivity extends AppCompatActivity {

    private Fragment homeFragment, favoriteFragment, accountFragment, sellFragment, networkFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String activity = getIntent().getStringExtra("activity");

        homeFragment = new HomeFragment();
        favoriteFragment = new FavoriteFragment();
        accountFragment = new AccountFragment();
//      mystoreFragment = new MystoreFragment();
        sellFragment = new SellFragment();
        networkFragment = new NoConnectionFragment();


        if (!NetworkConnection.isConnected(MainActivity.this)){

            setFragment(networkFragment);

        }else {
            if (activity == null){
                initComponent();
                setFragment(homeFragment);
            }else {
                if (activity.equals("settingActivity")){
                    initComponent();
                    setFragment(accountFragment);
                }else if(activity.equals("nextsellActivity")){
                    initComponent();
                    setFragment(sellFragment);
                }
            }
        }

    }

    private void initComponent(){

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.home:
                        setFragment(homeFragment);
                        return true;

                    case R.id.favorite:
                        setFragment(favoriteFragment);
                        return true;

                    case R.id.sell:
                        setFragment(sellFragment);
                        return true;

//                    case R.id.my_store:
//                        setFragment(mystoreFragment);
//                        return true;

                    case R.id.account:
                        setFragment(accountFragment);
                        return true;


                    default:
                        return false;

                }

            }
        });

    }

    private void setFragment(Fragment fragment){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment, "fragment");
        fragmentTransaction.commit();

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.topbar_menu, menu);
//
//        return true;
//    }

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Klik sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }



}
