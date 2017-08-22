package com.golub.golubroman.megakittest.Cars;


import java.util.List;

/**
 * Created by roman on 21.08.17.
 */

public interface CarsMVP {
    interface VtPInterface{

    }

    interface PtVInterface{
        void onOkDialogClicked(CarModel carModel, OwnerModel ownerModel);
        void onDeletePopupClicked(CarModel carModel, OwnerModel ownerModel);
        List<CarModel> getCarDatabase();
        List<OwnerModel> getOwnerDatabase();
    }
}
