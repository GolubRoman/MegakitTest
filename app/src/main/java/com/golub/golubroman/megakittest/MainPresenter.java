package com.golub.golubroman.megakittest;

import android.content.Context;

import com.golub.golubroman.megakittest.Cars.CarModel;
import com.golub.golubroman.megakittest.Cars.Database.DBQueries;

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
    public void addToDatabase(CarModel carModel) {
//      adding car model to database
        DBQueries.addToDatabase(carModel, context);
    }

    @Override
    public void runSearch(String query) {
        List<CarModel> carModels = DBQueries.getTable(context);
        List<CarModel> filteredList = new ArrayList<>();
//      search through the list of recycler view items
        query = query.toLowerCase();
        for(CarModel carmodel : carModels){
//          if recycler view item contains query phrase
            if(carmodel.getCarName().toLowerCase().contains(query) ||
                    carmodel.getCarOwner().toLowerCase().contains(query)){
                filteredList.add(carmodel);
            }
        }
//      running interface method for displaying search results
        view.displaySearchResults(filteredList);
    }
}
