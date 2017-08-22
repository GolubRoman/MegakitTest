package com.golub.golubroman.megakittest.Cars;


import java.util.List;

/**
 * Created by roman on 21.08.17.
 */

public interface CarsMVP {
    interface VtPInterface{

    }

    interface PtVInterface{
        void onOkDialogClicked(CarModel carModel);
        void onDeletePopupClicked(CarModel carModel);
        List<CarModel> getTableDatabase();
        List<CarModel> updateDatabase();
    }
}
