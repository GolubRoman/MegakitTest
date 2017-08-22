package com.golub.golubroman.megakittest;

import android.content.Context;

import com.golub.golubroman.megakittest.Cars.CarModel;
import com.golub.golubroman.megakittest.Cars.Database.DBQueries;
import com.golub.golubroman.megakittest.Cars.OwnerModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roman on 22.08.17.
 */

public class MainPresenter implements MainMVP.PtVInterface{

    private MainMVP.VtPInterface view;
    private Context context;

    public MainPresenter(MainMVP.VtPInterface view){
        this.view = view;
        this.context = (MainActivity)view;
    }

    @Override
    public void addToDatabase(CarModel carModel, OwnerModel ownerModel) {
//      adding car model to database
        String id = DBQueries.addCarToDatabase(carModel, context);
        DBQueries.addOwnerToDatabase(ownerModel, id, context);
    }

    @Override
    public void runSearch(String query) {
        List<CarModel> carModels = DBQueries.getCarTable(context);
        List<OwnerModel> ownerModels = DBQueries.getOwnerTable(context);

        List<CarModel> filteredCarList = new ArrayList<>();
        List<OwnerModel> filteredOwnerList = new ArrayList<>();
//      search through the list of recycler view items
        query = query.toLowerCase();
        for(int i = 0; i < carModels.size(); i++){
//          if recycler view item contains query phrase
            if(carModels.get(i).getCarName().toLowerCase().contains(query) ||
                    ownerModels.get(i).getOwnerName().toLowerCase().contains(query)){
                filteredCarList.add(carModels.get(i));
                filteredOwnerList.add(ownerModels.get(i));
            }
        }
//      running interface method for displaying search results
        view.displaySearchResults(filteredCarList, filteredOwnerList);
    }
}
