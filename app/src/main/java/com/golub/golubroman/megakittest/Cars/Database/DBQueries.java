package com.golub.golubroman.megakittest.Cars.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.golub.golubroman.megakittest.Cars.CarModel;
import com.golub.golubroman.megakittest.Cars.OwnerModel;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by roman on 21.08.17.
 */

public class DBQueries{

    final static String column_ID = "_ID";
    final static String tableCARS = "tableCARS";
    final static String tableOWNERS = "tableOWNERS";
    final static String columnCARNAME = "carname";
    final static String columnCAROWNER = "carowner";
    final static String columnCARCOLOR = "carcolor";
    final static String columnCARPHOTO = "carphoto";


    private Context context;
    private static DBHelper dbh;
    private static SQLiteDatabase db;

    private static int carsCnt = 0, ownerCnt = 0;



    public static List<CarModel> getCarTable(Context context){
        dbh = new DBHelper(context);
        List<CarModel> favoritesList = new ArrayList<>();
        String querySQL = "SELECT * FROM " + tableCARS;
        db = dbh.getWritableDatabase();

//        Check for empty base
        Cursor cursor = db.rawQuery(querySQL, null);
        if(!cursor.moveToFirst()) {
            return new ArrayList<>();
        }else{favoritesList.add(fillCarModel(cursor));}
//        if we have no elements further
        if(!cursor.moveToNext()) {
            return favoritesList;
        }
//        adding the element to favoritesList from database with fillModel method
        do{
            favoritesList.add(fillCarModel(cursor));
        }while (cursor.moveToNext());
        cursor.close();
        db.close();
        return favoritesList;
    }

    public static List<OwnerModel> getOwnerTable(Context context){
        dbh = new DBHelper(context);
        List<OwnerModel> favoritesList = new ArrayList<>();
        String querySQL = "SELECT * FROM " + tableOWNERS;
        db = dbh.getWritableDatabase();

//        Check for empty base
        Cursor cursor = db.rawQuery(querySQL, null);
        if(!cursor.moveToFirst()) {
            return new ArrayList<>();
        }else{favoritesList.add(fillOwnerModel(cursor));}
//        if we have no elements further
        if(!cursor.moveToNext()) {
            return favoritesList;
        }
//        adding the element to favoritesList from database with fillModel method
        do{
            favoritesList.add(fillOwnerModel(cursor));
        }while (cursor.moveToNext());
        cursor.close();
        db.close();
        return favoritesList;
    }

    public static CarModel fillCarModel(Cursor cursor){
//        initializing of car model
        String carName = cursor.getString(cursor.getColumnIndex(columnCARNAME));
        String carPhoto = cursor.getString(cursor.getColumnIndex(columnCARPHOTO));
        String id = cursor.getString(cursor.getColumnIndex(column_ID));

        CarModel carModel = new CarModel(carName);
        carModel.setId(id);
        carModel.setCarPhoto(carPhoto);
        carsCnt++;
        return carModel;
    }

    public static OwnerModel fillOwnerModel(Cursor cursor){
//        initializing of owner model
        String carOwner = cursor.getString(cursor.getColumnIndex(columnCAROWNER));
        String id = cursor.getString(cursor.getColumnIndex(column_ID));

        OwnerModel ownerModel = new OwnerModel(id, carOwner);
        ownerCnt++;
        return ownerModel;
    }

    public static String addCarToDatabase(CarModel carModel, Context context){
//        adding to database some object of car model class
        dbh = new DBHelper(context);
        db = dbh.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String carName = carModel.getCarName();
        String carPhoto = carModel.getCarPhoto();
        if(carPhoto == null) carPhoto = "null";

        String id = new BigInteger(50, new SecureRandom()).toString();
        cv.put(column_ID, id);
        cv.put(columnCARNAME, carName);
        cv.put(columnCARPHOTO, carPhoto);
//        inserting of the object to database
        db.insert(tableCARS, null, cv);
        db.close();
        return id;
    }

    public static String addOwnerToDatabase(OwnerModel ownerModel, String id, Context context){
//        adding to database some object of car model class
        dbh = new DBHelper(context);
        db = dbh.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String carOwner = ownerModel.getOwnerName();

        cv.put(column_ID, id);
        cv.put(columnCAROWNER, carOwner);
//        inserting of the object to database
        db.insert(tableOWNERS, null, cv);
        db.close();
        return id;
    }

    public static void deleteCarFromDatabase(Context context, CarModel carModel){
//        removing element from database
        dbh = new DBHelper(context);
        db = dbh.getWritableDatabase();
//        searching of the element by id
        db.delete(tableCARS, column_ID + " = ?", new String[]{carModel.getId()});
        db.close();
    }

    public static void deleteOwnerFromDatabase(Context context, OwnerModel ownerModel){
//        removing element from database
        dbh = new DBHelper(context);
        db = dbh.getWritableDatabase();
//        searching of the element by id
        db.delete(tableOWNERS, column_ID + " = ?", new String[]{ownerModel.getId()});
        db.close();
    }

    public static void changeElementFromDatabase(Context context, CarModel carModel, OwnerModel ownerModel){
//        changing of element by first deleting it and then adding new one
        deleteCarFromDatabase(context, carModel);
        deleteOwnerFromDatabase(context, ownerModel);
        String id = addCarToDatabase(carModel, context);
        addOwnerToDatabase(ownerModel, id, context);
        db.close();
    }

}
