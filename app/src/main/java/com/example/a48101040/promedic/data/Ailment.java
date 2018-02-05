package com.example.a48101040.promedic.data;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.a48101040.promedic.db.DbHelper;

/**
 * Created by 48101040 on 1/27/2018.
 */

public class Ailment implements Parcelable {

    public String Name;
    public String Category;
    public String Introduction;
    public String Aetiology;
    public String PathoPhysiology;
    public String ClinicalFeatures;
    public String DifferentialDiagnosis;
    public String Complications;
    public String Investigations;
    public String TreatmentObjectives;
    public String NonDrugTreatment;
    public String DrugTreatment;
    public String SupportiveMeasures;
    public String NotableAdverseDrugReactions;
    public String Caution;
    public String Prevention;

    public Ailment(String Name, String Category, String Introduction, String Aetiology, String PathoPhysiology, String ClinicalFeatures, String DifferentialDiagnosis, String Complications,
                   String Investigations, String TreatmentObjectives, String NonDrugTreatment, String DrugTreatment, String SupportiveMeasures,
                   String NotableAdverseDrugReactions, String Caution, String Prevention){
        this.Name = Name;
        this.Category = Category;
        this.Introduction = Introduction;
        this.DifferentialDiagnosis = DifferentialDiagnosis;
        this.Complications = Complications;
        this.Aetiology = Aetiology;
        this.PathoPhysiology = PathoPhysiology;
        this.ClinicalFeatures = ClinicalFeatures;
        this.Investigations = Investigations;
        this.TreatmentObjectives = TreatmentObjectives;
        this.NonDrugTreatment = NonDrugTreatment;
        this.DrugTreatment = DrugTreatment;
        this.SupportiveMeasures = SupportiveMeasures;
        this.NotableAdverseDrugReactions = NotableAdverseDrugReactions;
        this.Caution = Caution;
        this.Prevention = Prevention;
    }

    public Ailment(Cursor cursor){
        this.Name = cursor.getString(cursor.getColumnIndex(DbHelper.NAME_COL_NAME));
        this.Category = cursor.getString(cursor.getColumnIndex(DbHelper.CATEGORY_COL_NAME));
        this.Introduction = cursor.getString(cursor.getColumnIndex(DbHelper.INTRODUCTION_COL_NAME));
        this.DifferentialDiagnosis = cursor.getString(cursor.getColumnIndex(DbHelper.DIFFERENTIAL_DIAGNOSIS_COL_NAME));
        this.Complications = cursor.getString(cursor.getColumnIndex(DbHelper.COMPLICATIONS_COL_NAME));
        this.Aetiology = cursor.getString(cursor.getColumnIndex(DbHelper.AETIOLOGY_COL_NAME));
        this.PathoPhysiology = cursor.getString(cursor.getColumnIndex(DbHelper.PATHOPHYSIOLOGY_COL_NAME));
        this.ClinicalFeatures = cursor.getString(cursor.getColumnIndex(DbHelper.CLINICAL_FEATURES_COL_NAME));
        this.Investigations = cursor.getString(cursor.getColumnIndex(DbHelper.INVESTIGATIONS_COL_NAME));
        this.TreatmentObjectives = cursor.getString(cursor.getColumnIndex(DbHelper.TREATMENT_OBJECTIVES_COL_NAME));
        this.NonDrugTreatment = cursor.getString(cursor.getColumnIndex(DbHelper.NON_DRUG_TREATMENT_COL_NAME));
        this.DrugTreatment = cursor.getString(cursor.getColumnIndex(DbHelper.DRUG_TREATMENT_COL_NAME));
        this.SupportiveMeasures = cursor.getString(cursor.getColumnIndex(DbHelper.SUPPORTIVE_MEASURES_COL_NAME));
        this.NotableAdverseDrugReactions = cursor.getString(cursor.getColumnIndex(DbHelper.NOTABLE_ADVERSE_DRUG_REACTIONS_COL_NAME));
        this.Caution = cursor.getString(cursor.getColumnIndex(DbHelper.CAUTION_COL_NAME));
        this.Prevention = cursor.getString(cursor.getColumnIndex(DbHelper.PREVENTION_COL_NAME));
    }


    private Ailment(Parcel in){
        Name = in.readString();
        Category = in.readString();
        Introduction = in.readString();
        DifferentialDiagnosis = in.readString();
        Complications = in.readString();
        Aetiology = in.readString();
        PathoPhysiology = in.readString();
        ClinicalFeatures = in.readString();
        Investigations = in.readString();
        TreatmentObjectives = in.readString();
        NonDrugTreatment = in.readString();
        DrugTreatment = in.readString();
        SupportiveMeasures = in.readString();
        NotableAdverseDrugReactions = in.readString();
        Caution = in.readString();
        Prevention = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "Ailment " + Name;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(Name);
        parcel.writeString(Category);
        parcel.writeString(Introduction);
        parcel.writeString(DifferentialDiagnosis);
        parcel.writeString(Complications);
        parcel.writeString(Aetiology);
        parcel.writeString(PathoPhysiology);
        parcel.writeString(ClinicalFeatures);
        parcel.writeString(Investigations);
        parcel.writeString(TreatmentObjectives);
        parcel.writeString(NonDrugTreatment);
        parcel.writeString(DrugTreatment);
        parcel.writeString(SupportiveMeasures);
        parcel.writeString(NotableAdverseDrugReactions);
        parcel.writeString(Caution);
        parcel.writeString(Prevention);
    }

    public final static Parcelable.Creator<Ailment> CREATOR = new Parcelable.Creator<Ailment>(){
        @Override
        public Ailment createFromParcel(Parcel source) {
            return new Ailment(source);
        }

        @Override
        public Ailment[] newArray(int i){
            return new Ailment[i];
        }
    };

}
