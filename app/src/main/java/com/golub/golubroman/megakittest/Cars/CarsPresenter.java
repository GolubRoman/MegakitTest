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
    private List<OwnerModel> ownerModels;


    public CarsPresenter(CarsMVP.VtPInterface view){
        this.view = view;
        this.context = ((CarsFragment)view).getActivity();
    }

    @Override
    public void onOkDialogClicked(CarModel carModel, OwnerModel ownerModel) {
        DBQueries.changeElementFromDatabase(context, carModel, ownerModel);
    }

    @Override
    public void onDeletePopupClicked(CarModel carModel, OwnerModel ownerModel) {
        DBQueries.deleteCarFromDatabase(context, carModel);
        DBQueries.deleteOwnerFromDatabase(context, ownerModel);
    }

    @Override
    public List<CarModel> getCarDatabase() {
        carModels = DBQueries.getCarTable(context);
        return carModels;
    }

    @Override
    public List<OwnerModel> getOwnerDatabase() {
        ownerModels = DBQueries.getOwnerTable(context);
        return ownerModels;
    }



}
