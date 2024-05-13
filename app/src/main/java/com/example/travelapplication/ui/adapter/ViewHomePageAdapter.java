package com.example.travelapplication.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.travelapplication.ui.dashboard.CartFragment;
import com.example.travelapplication.ui.dashboard.PeopleFragment;
import com.example.travelapplication.ui.dashboard.HomeFragment;
import com.example.travelapplication.ui.dashboard.RatingFragment;

public class ViewHomePageAdapter extends FragmentStateAdapter {


    public ViewHomePageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new RatingFragment();
            case 2:
                return new PeopleFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
