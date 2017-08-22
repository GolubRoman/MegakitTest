package com.golub.golubroman.megakittest.Cars;

/**
 * Created by roman on 21.08.17.
 */

public class CarModel {

    private String carName, carOwner, carColor, carPhoto;
    private String id;

    public CarModel(String carName, String carOwner, String carColor){
        this.carName = carName;
        this.carOwner = carOwner;
        this.carColor = carColor;
    }

    public CarModel(String id, String carName, String carOwner, String carColor){
        this.id = id;
        this.carName = carName;
        this.carOwner = carOwner;
        this.carColor = carColor;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarOwner() {
        return carOwner;
    }

    public void setCarOwner(String carOwner) {
        this.carOwner = carOwner;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
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
