package com.example.travelapplication.ui.my_interface;

import com.example.travelapplication.ui.model.Category;
import com.example.travelapplication.ui.model.Tour;

public interface IClickItemTourListener {
    void onClickItemTour(Tour tour);
    void onClickAddToCart(Tour tour);

}
