package com.oblivion.tokoonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.oblivion.tokoonline.fragment.FavoriteFragment;
import com.oblivion.tokoonline.fragment.HomeFragment;
import com.oblivion.tokoonline.fragment.MystoreFragment;
import com.oblivion.tokoonline.fragment.SellFragment;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initComponent();

    }


    private void initComponent(){

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);

        final Fragment homeFragment = new HomeFragment();
        final Fragment favoriteFragment = new FavoriteFragment();
        final Fragment accountFragment = new HomeFragment();
        final Fragment mystoreFragment = new MystoreFragment();
        final Fragment sellFragment = new SellFragment();

        setFragment(homeFragment);

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

                    case R.id.my_store:
                        setFragment(mystoreFragment);
                        return true;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topbar_menu, menu);

        return true;
    }



}
