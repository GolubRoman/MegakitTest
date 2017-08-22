package com.golub.golubroman.megakittest.Cars;

/**
 * Created by roman on 21.08.17.
 */

public class CarModel {

    private String carName, carPhoto;
    private String id;


    public CarModel(String carName){
        this.carName = carName;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarPhoto() {
        return carPhoto;
    }

    public void setCarPhoto(String carPhoto) {
        this.carPhoto = carPhoto;
    }
}
