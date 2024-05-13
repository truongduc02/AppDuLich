package com.example.travelapplication.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.travelapplication.ui.dashboard.TourBacFragment;
import com.example.travelapplication.ui.dashboard.TourNamFragment;
import com.example.travelapplication.ui.dashboard.TourTrungFragment;

public class ViewPageAdapter extends FragmentStateAdapter {


    public ViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new TourNamFragment();
            case 1:
                return new TourTrungFragment();
            case 2:
                return new TourBacFragment();

            default:
                return new TourNamFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
