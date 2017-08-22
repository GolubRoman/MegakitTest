package com.golub.golubroman.megakittest.Cars;

/**
 * Created by roman on 21.08.17.
 */

public class OwnerModel {

    private String ownerName;
    private String id;

    public OwnerModel(String ownerName){
        this.ownerName = ownerName;
    }

    public OwnerModel(String id, String ownerName){
        this.id = id;
        this.ownerName = ownerName;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
