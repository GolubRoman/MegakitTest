package com.golub.golubroman.megakittest;

import com.golub.golubroman.megakittest.Cars.CarModel;

import java.util.List;


/**
 * Created by roman on 22.08.17.
 */

public interface MainMVP {
    interface VtPInterface{
        void displaySearchResults(List<CarModel> carModels);
    }

    interface PtVInterface{
        void addToDatabase(CarModel carModel);
        void runSearch(String query);
    }
}
