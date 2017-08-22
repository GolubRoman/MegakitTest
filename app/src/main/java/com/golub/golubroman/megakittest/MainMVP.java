package com.golub.golubroman.megakittest;

import com.golub.golubroman.megakittest.Cars.CarModel;
import com.golub.golubroman.megakittest.Cars.OwnerModel;

import java.util.List;


/**
 * Created by roman on 22.08.17.
 */

public interface MainMVP {
    interface VtPInterface{
        void displaySearchResults(List<CarModel> carModels, List<OwnerModel> ownerModels);
    }

    interface PtVInterface{
        void addToDatabase(CarModel carModel, OwnerModel ownerModel);
        void runSearch(String query);
    }
}
