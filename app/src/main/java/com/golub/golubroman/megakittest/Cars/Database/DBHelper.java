package com.golub.golubroman.megakittest.Cars.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by roman on 21.08.17.
 */

public class DBHelper extends SQLiteOpenHelper {

    Context context;

    public DBHelper(Context context) {
        super(context, "MegakitDB", null, 1);
        this.context = context;
    }
    private ContentValues cv;
    final static String column_ID = "_ID";
    final static String tableCARS = "tableCARS";
    final static String tableOWNERS = "tableOWNERS";
    final static String columnCARNAME = "carname";
    final static String columnCAROWNER = "carowner";
    final static String columnCARPHOTO = "carphoto";


    @Override
    public void onCreate(SQLiteDatabase db) {
//       Creating of table with 2 columns: name, photo
        cv = new ContentValues();

        db.execSQL("CREATE TABLE " + tableCARS + " ("
            + column_ID + " TEXT NOT NULL, "
                + columnCARNAME + " TEXT NOT NULL, "
                + columnCARPHOTO + " TEXT NOT NULL"
                    + ");");

        db.execSQL("CREATE TABLE " + tableOWNERS + " ("
                + column_ID + " TEXT NOT NULL, "
                + columnCAROWNER + " TEXT NOT NULL"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        method for case if the app has updated
        db.execSQL("DROP TABLE IF EXISTS " + tableCARS);
        db.execSQL("DROP TABLE IF EXISTS " + tableOWNERS);
        onCreate(db);
    }


}
