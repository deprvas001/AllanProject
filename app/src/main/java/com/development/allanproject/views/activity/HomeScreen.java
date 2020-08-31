package com.development.allanproject.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivityHomeScreenBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class HomeScreen extends BaseActivity implements View.OnClickListener {
ActivityHomeScreenBinding screenBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        ImageView imageView = (ImageView)findViewById(R.id.notification);
        ImageView profileImage = (ImageView)findViewById(R.id.profile_image);
        profileImage.setOnClickListener(this);
        imageView.setOnClickListener(this);
     //   screenBinding = DataBindingUtil.setContentView(this,R.layout.activity_home_screen);
      //  screenBinding.notification.setOnClickListener(this);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();*/
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.notification:
                startActivity(new Intent(HomeScreen.this, NotificationScreen.class));
                break;

            case R.id.profile_image:
                startActivity(new Intent(HomeScreen.this, MyProfile.class));
                break;
        }
    }
}
