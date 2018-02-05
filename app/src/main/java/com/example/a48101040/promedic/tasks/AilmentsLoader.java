package com.example.a48101040.promedic.tasks;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.AsyncTaskLoader;

import com.example.a48101040.promedic.data.Ailment;
import com.example.a48101040.promedic.db.DbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 48101040 on 2/2/2018.
 */

public class AilmentsLoader extends AsyncTaskLoader<List<Ailment>> {

    private DbHelper mDBHelper;

    public AilmentsLoader(Context context, DbHelper dbHelper){
        super(context);
        this.mDBHelper = dbHelper;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Ailment> loadInBackground() {
        List<Ailment> AilmentsList = new ArrayList<>();
        String strQuery = "select * from " + DbHelper.AILMENTS_TABLE_NAME + ";";
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(strQuery, null);
        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                AilmentsList.add(new Ailment(cursor));
            }
        }
        cursor.close();

        return AilmentsList;
    }

    @Override
    public void deliverResult(List<Ailment> data) {
        super.deliverResult(data);
    }
}
