package com.example.travelapplication;

import android.os.Bundle;

import com.example.travelapplication.ui.adapters.HistoryPagerAdapter;
import com.example.travelapplication.ui.history.AllFragment;
import com.example.travelapplication.ui.history.CancelledFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager2.widget.ViewPager2;

import com.example.travelapplication.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Xử lý tab layout của lịch sử đơn hàng
        TabLayout tabLayout = findViewById(R.id.tabHistory);
        ViewPager2 viewPager = findViewById(R.id.viewHistory);

        HistoryPagerAdapter adapter = new HistoryPagerAdapter(getSupportFragmentManager(), getLifecycle());

        adapter.addFragment(new AllFragment(), "Tất cả");
        adapter.addFragment(new CancelledFragment(), "Đơn đã hủy");
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(adapter.getTitleList().get(position))
        ).attach();
    }

}