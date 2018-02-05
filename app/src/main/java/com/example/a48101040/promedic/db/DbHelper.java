package com.example.a48101040.promedic.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 48101040 on 1/27/2018.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "ailments.db";
    private static final int DB_VERSION = 1;
    public static final String CATEGORIES_TABLE_NAME = "tblCategories";
    public static final String AILMENTS_TABLE_NAME = "tblAilments";

    public static final String ID_FOR_CATEGORY_TABLE = "id";
    public static final String ID_FOR_AILMENT_TABLE = "id";

    public static final String NAME_COL_NAME = "Name";
    public static final String CATEGORY_COL_NAME = "Category";
    public static final String INTRODUCTION_COL_NAME = "Introduction";
    public static final String AETIOLOGY_COL_NAME = "Aetiology";
    public static final String PATHOPHYSIOLOGY_COL_NAME = "Pathophysiology";
    public static final String CLINICAL_FEATURES_COL_NAME = "ClinicalFeatures";
    public static final String DIFFERENTIAL_DIAGNOSIS_COL_NAME = "DifferentialDiagnosis";
    public static final String COMPLICATIONS_COL_NAME = "Complications";
    public static final String INVESTIGATIONS_COL_NAME = "Investigations";
    public static final String TREATMENT_OBJECTIVES_COL_NAME = "TreatmentObjectives";
    public static final String NON_DRUG_TREATMENT_COL_NAME = "NonDrugTreatment";
    public static final String DRUG_TREATMENT_COL_NAME = "DrugTreatment";
    public static final String SUPPORTIVE_MEASURES_COL_NAME = "SupportiveMeasures";
    public static final String NOTABLE_ADVERSE_DRUG_REACTIONS_COL_NAME = "NotableAdverseDrugReactions";
    public static final String CAUTION_COL_NAME = "Caution";
    public static final String PREVENTION_COL_NAME = "Prevention";


    public DbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //Create First Table: Ailments
        String strAilmentsTableCreate = "create table " + AILMENTS_TABLE_NAME + "(";
        strAilmentsTableCreate += ID_FOR_AILMENT_TABLE + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ";
        strAilmentsTableCreate += NAME_COL_NAME + " TEXT, ";
        strAilmentsTableCreate += CATEGORY_COL_NAME + " TEXT, ";
        strAilmentsTableCreate += INTRODUCTION_COL_NAME + " TEXT, ";
        strAilmentsTableCreate += AETIOLOGY_COL_NAME + " TEXT, ";
        strAilmentsTableCreate += PATHOPHYSIOLOGY_COL_NAME + " TEXT, ";
        strAilmentsTableCreate += CLINICAL_FEATURES_COL_NAME + " TEXT, ";
        strAilmentsTableCreate += DIFFERENTIAL_DIAGNOSIS_COL_NAME + " TEXT, ";
        strAilmentsTableCreate += COMPLICATIONS_COL_NAME + " TEXT, ";
        strAilmentsTableCreate += INVESTIGATIONS_COL_NAME + " TEXT, ";
        strAilmentsTableCreate += TREATMENT_OBJECTIVES_COL_NAME + " TEXT, ";
        strAilmentsTableCreate += NON_DRUG_TREATMENT_COL_NAME + " TEXT, ";
        strAilmentsTableCreate += DRUG_TREATMENT_COL_NAME + " TEXT, ";
        strAilmentsTableCreate += SUPPORTIVE_MEASURES_COL_NAME + " TEXT, ";
        strAilmentsTableCreate += NOTABLE_ADVERSE_DRUG_REACTIONS_COL_NAME + " TEXT, ";
        strAilmentsTableCreate += CAUTION_COL_NAME + " TEXT, ";
        strAilmentsTableCreate += PREVENTION_COL_NAME + " TEXT);";

        sqLiteDatabase.execSQL(strAilmentsTableCreate);

        //Create Second Table: Categories
        String strCategoriesTableCreate = "create table " + CATEGORIES_TABLE_NAME + "(";
        strCategoriesTableCreate += ID_FOR_CATEGORY_TABLE + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ";
        strCategoriesTableCreate += CATEGORY_COL_NAME + " TEXT);";

        sqLiteDatabase.execSQL(strCategoriesTableCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AILMENTS_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CATEGORIES_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
