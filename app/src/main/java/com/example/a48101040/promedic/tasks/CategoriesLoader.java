package com.example.a48101040.promedic.tasks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.AsyncTaskLoader;

import com.example.a48101040.promedic.R;
import com.example.a48101040.promedic.db.DbHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 48101040 on 2/3/2018.
 */

public class CategoriesLoader extends AsyncTaskLoader<List<String>> {

    private DbHelper mMyDbHelper;

    public CategoriesLoader(Context context, DbHelper dbHelper) {
        super(context);
        this.mMyDbHelper = dbHelper;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<String> loadInBackground() {
        List<String> categoriesList = new ArrayList<>();
        SQLiteDatabase db = mMyDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DbHelper.CATEGORIES_TABLE_NAME + ";", null);
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                categoriesList.add(cursor.getString(cursor.getColumnIndex(DbHelper.CATEGORY_COL_NAME)));
            }
        }
        cursor.close();
        return categoriesList;
    }

    @Override
    public void deliverResult(List<String> data) {
        super.deliverResult(data);
    }
}
