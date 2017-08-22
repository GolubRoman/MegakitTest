package com.golub.golubroman.megakittest.Cars.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.golub.golubroman.megakittest.Cars.CarModel;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by roman on 21.08.17.
 */

public class DBQueries{

    final static String column_ID = "_ID";
    final static String tableCARS = "tableCARS";
    final static String columnCARNAME = "carname";
    final static String columnCAROWNER = "carowner";
    final static String columnCARCOLOR = "carcolor";
    final static String columnCARPHOTO = "carphoto";


    private Context context;
    private static DBHelper dbh;
    private static SQLiteDatabase db;

    private static int carsCnt = 0;



    public static List<CarModel> getTable(Context context){
        dbh = new DBHelper(context);
        List<CarModel> favoritesList = new ArrayList<>();
        String querySQL = "SELECT * FROM " + tableCARS;
        db = dbh.getWritableDatabase();

//        Check for empty base
        Cursor cursor = db.rawQuery(querySQL, null);
        if(!cursor.moveToFirst()) {
            return new ArrayList<>();
        }else{favoritesList.add(fillModel(cursor));}
//        if we have no elements further
        if(!cursor.moveToNext()) {
            return favoritesList;
        }
//        adding the element to favoritesList from database with fillModel method
        do{
            favoritesList.add(fillModel(cursor));
        }while (cursor.moveToNext());
        cursor.close();
        db.close();
        return favoritesList;
    }

    public static CarModel fillModel(Cursor cursor){
//        initializing of car model
        String carName = cursor.getString(cursor.getColumnIndex(columnCARNAME));
        String carOwner = cursor.getString(cursor.getColumnIndex(columnCAROWNER));
        String carColor = cursor.getString(cursor.getColumnIndex(columnCARCOLOR));
        String carPhoto = cursor.getString(cursor.getColumnIndex(columnCARPHOTO));
        String id = cursor.getString(cursor.getColumnIndex(column_ID));

        CarModel carModel = new CarModel(id, carName, carOwner, carColor);
        carModel.setCarPhoto(carPhoto);
        carsCnt++;
        return carModel;
    }

    public static void addToDatabase(CarModel carModel, Context context){
//        adding to database some object of car model class
        dbh = new DBHelper(context);
        db = dbh.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String carName = carModel.getCarName();
        String carOwner = carModel.getCarOwner();
        String carColor = carModel.getCarColor();
        String carPhoto = carModel.getCarPhoto();
        if(carPhoto == null) carPhoto = "null";

        String id = new BigInteger(50, new SecureRandom()).toString();
        cv.put(column_ID, id);
        cv.put(columnCARNAME, carName);
        cv.put(columnCAROWNER, carOwner);
        cv.put(columnCARCOLOR, carColor);
        cv.put(columnCARPHOTO, carPhoto);
//        inserting of the object to database
        db.insert(tableCARS, null, cv);
        db.close();
    }

    public static void deleteElementFromDatabase(Context context, CarModel carModel){
//        removing element from database
        dbh = new DBHelper(context);
        db = dbh.getWritableDatabase();
//        searching of the element by id
        db.delete(tableCARS, column_ID + " = ?", new String[]{carModel.getId()});
        db.close();
    }

    public static void changeElementFromDatabase(Context context, CarModel carModel){
//        changing of element by first deleting it and then adding new one
        deleteElementFromDatabase(context, carModel);
        addToDatabase(carModel, context);
        db.close();
    }

}
