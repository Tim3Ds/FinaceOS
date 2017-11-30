package comtim3ds.github.financeos.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import comtim3ds.github.financeos.data.financeOSContract.*;

/**
 * Created by TinMa on 11/30/2017.
 */

public class financeOSDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "financeOS.db";
    private static final int DB_VERSION = 1;
    public financeOSDbHelper(Context context){
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_financeOS_TABLE = "CREATE TABLE " +
                financeOSEntry.TABLE_NAME + " (" +
                financeOSEntry._ID + " INTIGER PRIMARY KEY AUTOINCREMENT," +
                financeOSEntry.COLUMN_Type + " TEXT NOT NULL, " +
                financeOSEntry.COLUMN_Item_Name + " TEXT NOT NULL, " +
                financeOSEntry.COLUMN_Item_Value + " INTEGER NOT NULL, " +
                financeOSEntry.COLUMN_Expected_Date + " TIMESTAMP DEFAULT" +
                ");";
    
        db.execSQL(SQL_CREATE_financeOS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // if you ever update the contract or table make sure you use this function to properly
        // merge the old db with the new db
        db.execSQL("DROP TABLE IF EXISTS " + financeOSContract.financeOSEntry.TABLE_NAME);
        onCreate(db);
    }
}
