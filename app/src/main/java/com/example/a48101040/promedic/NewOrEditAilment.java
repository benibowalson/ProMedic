package com.example.a48101040.promedic;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a48101040.promedic.data.Ailment;
import com.example.a48101040.promedic.db.DbHelper;
import com.example.a48101040.promedic.utilities.MyConstants;
import com.example.a48101040.promedic.utilities.MySingleton;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by 48101040 on 2/2/2018.
 */

public class NewOrEditAilment extends AppCompatActivity {

    DbHelper mHelper;
    int mEditID;
    int actionCode;
    Ailment currentAilment;
    List<String> mCategoryNamesList = new ArrayList<>();
    ArrayAdapter<String> mSpinnerAdapter;

    EditText nameET;
    EditText introductionET;
    EditText aetiologyET;
    Spinner categorySpinner;
    EditText pathophysiologyET;
    EditText clinicalFeaturesET;
    EditText differentialDiagnosisET;
    EditText complicationsET;
    EditText investigationsET;
    EditText treatmentObjectivesET;
    EditText nonDrugTreatmentET;
    EditText drugTreatmentET;
    EditText supportiveMeasuresET;
    EditText notableAdverseDrugReactionsET;
    EditText cautionsET;
    EditText preventionET;

    Button saveButton;
    Button cancelButton;

    String strName;
    String strIntroduction;
    String strCategory;
    String strAetiology;
    String strPathophysiology;
    String strClinicalFeatures;
    String strDifferentialDiagnosis;
    String strComplications;
    String strInvestigations;
    String strTreatmentObjectives;
    String strNonDrugTreatment;
    String strDrugTreatment;
    String strSupportiveMeasures;
    String strNotableAdverseDrugReactions;
    String strCautions;
    String strPreventions;

    public static final Pattern strPattern = Pattern.compile("^[\\w]{1,}$");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_ailment);

        //Retrieve Data
        actionCode = MySingleton.getInstance().retrieveActionCode();
        mCategoryNamesList =  MySingleton.getInstance().retrieveCategoriesList();
        currentAilment = MySingleton.getInstance().retrieveCurrentAilment();

        if(getSupportActionBar() != null){
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(((actionCode == 1)? "NEW ":"EDIT ") + "AILMENT");
        }

        nameET = (EditText)findViewById(R.id.txtAilmentName);
        introductionET = (EditText)findViewById(R.id.txtIntroduction);
        aetiologyET = (EditText)findViewById(R.id.txtAetiology);
        categorySpinner = (Spinner)findViewById(R.id.spnCategory);
        pathophysiologyET = (EditText)findViewById(R.id.txtPathophysiology);
        clinicalFeaturesET = (EditText)findViewById(R.id.txtClinicalFeatures);
        differentialDiagnosisET = (EditText)findViewById(R.id.txtDifferentialDiagnosis);
        complicationsET = (EditText)findViewById(R.id.txtComplications);
        investigationsET = (EditText)findViewById(R.id.txtInvestigations);
        treatmentObjectivesET = (EditText)findViewById(R.id.txtTreatmentObjectives);
        nonDrugTreatmentET = (EditText)findViewById(R.id.txtNonDrugTreatment);
        drugTreatmentET = (EditText)findViewById(R.id.txtDrugTreatment);
        supportiveMeasuresET = (EditText)findViewById(R.id.txtSupportiveMeasures);
        notableAdverseDrugReactionsET = (EditText)findViewById(R.id.txtNotableAdverseDrugReactions);
        cautionsET = (EditText)findViewById(R.id.txtCaution);
        preventionET = (EditText)findViewById(R.id.txtPrevention);

        saveButton = (Button) findViewById(R.id.btnSave);
        cancelButton = (Button) findViewById(R.id.btnCancel);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveButton.setEnabled(false);
                cancelButton.setEnabled(false);
                strName = nameET.getText().toString().trim();
                if(ailmentNameExists(strName)){
                    saveButton.setEnabled(true);
                    cancelButton.setEnabled(true);
                    Toast.makeText(NewOrEditAilment.this, strName + " already exists!", Toast.LENGTH_LONG).show();
                    return;
                }

                saveAilment(actionCode);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMainActivity();
            }
        });
        populateSpinner();
        if(actionCode == 0) writeFields();
    }

    private void saveAilment(int actionCode){

        strName = nameET.getText().toString().trim();
        strIntroduction = introductionET.getText().toString().toLowerCase();
        strAetiology = aetiologyET.getText().toString().trim();
        strCategory = categorySpinner.getSelectedItem().toString().trim();
        strPathophysiology = pathophysiologyET.getText().toString().trim();
        strClinicalFeatures = clinicalFeaturesET.getText().toString().trim();
        strDifferentialDiagnosis = differentialDiagnosisET.getText().toString().trim();
        strComplications = complicationsET.getText().toString().trim();
        strInvestigations = investigationsET.getText().toString().trim();
        strTreatmentObjectives = treatmentObjectivesET.getText().toString().trim();
        strNonDrugTreatment = nonDrugTreatmentET.getText().toString().trim();
        strDrugTreatment = drugTreatmentET.getText().toString().trim();
        strSupportiveMeasures = supportiveMeasuresET.getText().toString().trim();
        strNotableAdverseDrugReactions = notableAdverseDrugReactionsET.getText().toString().trim();
        strCautions = cautionsET.getText().toString().trim();
        strPreventions = preventionET.getText().toString().trim();

        ContentValues contentValues = new ContentValues();

        //contentValues.put(DbHelper.ID_FOR_AILMENT_TABLE, 0);
        contentValues.put(DbHelper.NAME_COL_NAME, strName);
        contentValues.put(DbHelper.CATEGORY_COL_NAME, strCategory);
        contentValues.put(DbHelper.INTRODUCTION_COL_NAME, strIntroduction);
        contentValues.put(DbHelper.AETIOLOGY_COL_NAME, strAetiology);
        contentValues.put(DbHelper.PATHOPHYSIOLOGY_COL_NAME, strPathophysiology);
        contentValues.put(DbHelper.CLINICAL_FEATURES_COL_NAME, strClinicalFeatures);
        contentValues.put(DbHelper.DIFFERENTIAL_DIAGNOSIS_COL_NAME, strDifferentialDiagnosis);
        contentValues.put(DbHelper.COMPLICATIONS_COL_NAME, strComplications);
        contentValues.put(DbHelper.INVESTIGATIONS_COL_NAME, strInvestigations);
        contentValues.put(DbHelper.TREATMENT_OBJECTIVES_COL_NAME, strTreatmentObjectives);
        contentValues.put(DbHelper.NON_DRUG_TREATMENT_COL_NAME, strNonDrugTreatment);
        contentValues.put(DbHelper.DRUG_TREATMENT_COL_NAME, strDrugTreatment);
        contentValues.put(DbHelper.SUPPORTIVE_MEASURES_COL_NAME, strSupportiveMeasures);
        contentValues.put(DbHelper.NOTABLE_ADVERSE_DRUG_REACTIONS_COL_NAME, strNotableAdverseDrugReactions);
        contentValues.put(DbHelper.CAUTION_COL_NAME, strCautions);
        contentValues.put(DbHelper.PREVENTION_COL_NAME, strPreventions);
        String strQuery;

        long dbOpResult;
        mHelper = new DbHelper(getApplicationContext());
        SQLiteDatabase db = mHelper.getWritableDatabase();
        if(actionCode == 1){    //New
            dbOpResult = db.insert(DbHelper.AILMENTS_TABLE_NAME, null, contentValues);
            //insert returns NEWLY INSERTED id
        } else {    //edit
            dbOpResult = db.update(DbHelper.AILMENTS_TABLE_NAME, contentValues, DbHelper.ID_FOR_AILMENT_TABLE + " =? ", new String[]{Integer.toString(currentAilment.ID)});
            //update returns NUMBER OF ROWS AFFECTED
        }

        if (dbOpResult > -1){       //Successful Operation
            startMainActivity();
        } else {                    //Failed Operation
            Toast.makeText(this, "FAILED: Database Operation", Toast.LENGTH_LONG).show();
            saveButton.setEnabled(true);
            cancelButton.setEnabled(true);
        }
    }

    void writeFields(){
        nameET.setText(currentAilment.Name);
        categorySpinner.setSelection(mSpinnerAdapter.getPosition(currentAilment.Category));
        introductionET.setText(currentAilment.Introduction);
        aetiologyET.setText(currentAilment.Aetiology);
        pathophysiologyET.setText(currentAilment.PathoPhysiology);
        clinicalFeaturesET.setText(currentAilment.ClinicalFeatures);
        differentialDiagnosisET.setText(currentAilment.DifferentialDiagnosis);
        complicationsET.setText(currentAilment.Complications);
        investigationsET.setText(currentAilment.Investigations);
        treatmentObjectivesET.setText(currentAilment.TreatmentObjectives);
        nonDrugTreatmentET.setText(currentAilment.NonDrugTreatment);
        drugTreatmentET.setText(currentAilment.DrugTreatment);
        supportiveMeasuresET.setText(currentAilment.SupportiveMeasures);
        notableAdverseDrugReactionsET.setText(currentAilment.NotableAdverseDrugReactions);
        cautionsET.setText(currentAilment.Caution);
        preventionET.setText(currentAilment.Prevention);
    }

    void populateSpinner(){
        mSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mCategoryNamesList);
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(mSpinnerAdapter);
    }

    void startMainActivity(){
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);
    }

    boolean ailmentNameExists(String anAilmentName){
        boolean itExists = false;
        List<Ailment> tempListOfAilments = new ArrayList<>();
        tempListOfAilments = MySingleton.getInstance().retrieveAilmentsList();
        for(Ailment anAilment:tempListOfAilments){
            if(anAilment.Name.toLowerCase().equals(anAilmentName.toLowerCase())){
                if(actionCode == 1 || (actionCode == 0 && anAilment.ID != currentAilment.ID)){
                    itExists = true;
                    break;
                }
            }
        }

        return itExists;
    }
}
