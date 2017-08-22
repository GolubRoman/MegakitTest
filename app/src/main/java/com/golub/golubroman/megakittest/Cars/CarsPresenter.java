package com.golub.golubroman.megakittest.Cars;

import android.content.Context;

import com.golub.golubroman.megakittest.Cars.Database.DBQueries;

import java.util.List;

/**
 * Created by roman on 21.08.17.
 */

public class CarsPresenter implements CarsMVP.PtVInterface{

    private Context context;
    private CarsMVP.VtPInterface view;
    private List<CarModel> carModels;

    public CarsPresenter(CarsMVP.VtPInterface view){
        this.view = view;
        this.context = ((CarsFragment)view).getActivity();
    }

    @Override
    public void onOkDialogClicked(CarModel carModel) {
        DBQueries.changeElementFromDatabase(context, carModel);
    }

    @Override
    public void onDeletePopupClicked(CarModel carModel) {
        DBQueries.deleteElementFromDatabase(context, carModel);
    }

    @Override
    public List<CarModel> getTableDatabase() {
        carModels = DBQueries.getTable(context);
        return carModels;
    }

    @Override
    public List<CarModel> updateDatabase() {
//        method for updating database
        carModels = DBQueries.getTable(context);
        return carModels;
    }


}
