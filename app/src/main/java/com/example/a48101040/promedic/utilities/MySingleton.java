package com.example.a48101040.promedic.utilities;

import com.example.a48101040.promedic.data.Ailment;

import java.util.List;

/**
 * Created by 48101040 on 2/8/2018.
 */

public class MySingleton {

    public static final String TAG = MySingleton.class
            .getSimpleName();

    private List<Ailment> mAilmentsList;
    private Ailment mCurrentAilment;
    private List<String> mCategoriesList;
    private int mActionCode;

    private static MySingleton mInstance;
    public static synchronized MySingleton getInstance() {
        if(mInstance == null){
            mInstance = new MySingleton();
        }

        return mInstance;
    }

    private MySingleton(){}

    //Ailments List
    public void saveAilmentsList(List<Ailment> ailmentList){
        mAilmentsList = ailmentList;
    }
    public List<Ailment> retrieveAilmentsList(){
        return mAilmentsList;
    }

    //Categories List
    public void saveCategoriesList(List<String> categoriesList){
        mCategoriesList = categoriesList;
    }
    public List<String> retrieveCategoriesList(){
        return mCategoriesList;
    }

    //Current Ailment
    public void saveCurrentAilment(Ailment anAilment){
        mCurrentAilment = anAilment;
    }
    public Ailment retrieveCurrentAilment(){
        return mCurrentAilment;
    }

    //Action Code: New or Edit
    public void saveActionCode(int actionCode){
        mActionCode = actionCode;
    }

    public int retrieveActionCode(){
        return mActionCode;
    }
}
