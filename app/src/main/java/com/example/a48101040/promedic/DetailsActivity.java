package com.example.a48101040.promedic;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a48101040.promedic.data.Ailment;
import com.example.a48101040.promedic.utilities.MyConstants;
import com.example.a48101040.promedic.utilities.MySingleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 48101040 on 2/3/2018.
 */

public class DetailsActivity extends AppCompatActivity {

    private Ailment currentAilment;
    private List<String> mCategoryNamesList = new ArrayList<>();    //not used; but just so we can send to Edit Activity
    public static final String TAG = "Benny Work";

    FloatingActionButton fab;

    WebView nameWebView;
    WebView introductionWebView;
    WebView aetiologyWebView;
    WebView categoryWebView;
    WebView pathophysiologyWebView;
    WebView clinicalFeaturesWebView;
    WebView differentialDiagnosisWebView;
    WebView complicationsWebView;
    WebView investigationsWebView;
    WebView treatmentObjectivesWebView;
    WebView nonDrugTreatmentWebView;
    WebView drugTreatmentWebView;
    WebView supportiveMeasuresWebView;
    WebView notableAdverseDrugReactionsWebView;
    WebView cautionsWebView;
    WebView preventionWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ailment_details);

        if(getSupportActionBar() != null){
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("AILMENT DETAILS");
        }

        fab = (FloatingActionButton)findViewById(R.id.fabEditAilment);

        nameWebView = (WebView)findViewById(R.id.wbName);
        introductionWebView = (WebView)findViewById(R.id.wbIntroduction);
        aetiologyWebView = (WebView)findViewById(R.id.wbAetiology);
        categoryWebView = (WebView) findViewById(R.id.wbCategory);
        pathophysiologyWebView = (WebView)findViewById(R.id.wbPathophysiology);
        clinicalFeaturesWebView = (WebView)findViewById(R.id.wbClinicalFeatures);
        differentialDiagnosisWebView = (WebView)findViewById(R.id.wbDifferentialDiagnosis);
        complicationsWebView = (WebView)findViewById(R.id.wbComplications);
        investigationsWebView = (WebView)findViewById(R.id.wbInvestigations);
        treatmentObjectivesWebView = (WebView)findViewById(R.id.wbTreatmentObjectives);
        nonDrugTreatmentWebView = (WebView)findViewById(R.id.wbNonDrugTreatment);
        drugTreatmentWebView = (WebView)findViewById(R.id.wbDrugTreatment);
        supportiveMeasuresWebView = (WebView)findViewById(R.id.wbSupportiveMeasures);
        notableAdverseDrugReactionsWebView = (WebView)findViewById(R.id.wbNotableAdverseDrugReactions);
        cautionsWebView = (WebView)findViewById(R.id.wbCaution);
        preventionWebView = (WebView)findViewById(R.id.wbPrevention);

        //Retrieve Data
        currentAilment = MySingleton.getInstance().retrieveCurrentAilment();
        mCategoryNamesList = MySingleton.getInstance().retrieveCategoriesList();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startEditActivityIntent = new Intent(getApplicationContext(), NewOrEditAilment.class);

                MySingleton.getInstance().saveActionCode(0);        //0 is Code for Edit Operation

                /*
                startEditActivityIntent.putExtra(MyConstants.ACTION_CODE_KEY, 0);
                startEditActivityIntent.putStringArrayListExtra(MyConstants.LIST_OF_CATEGORIES_KEY,(ArrayList<String>) mCategoryNamesList);

                Bundle myExtras = new Bundle();
                myExtras.putParcelable(MyConstants.PARCELABLE_AILMENT_KEY, currentAilment);

                startEditActivityIntent.putExtras(myExtras);
                */
                startActivity(startEditActivityIntent);
            }
        });


        /*
        if(savedInstanceState == null){         //New (Fresh) Activity
            //Parent activity sent in a bundle with the intent
            Intent sourceIntent = getIntent();
            Bundle extras = sourceIntent.getExtras();
            if(extras != null){
                currentAilment = (Ailment)extras.getParcelable(MyConstants.PARCELABLE_AILMENT_KEY);
                mCategoryNamesList = extras.getStringArrayList(MyConstants.LIST_OF_CATEGORIES_KEY);
            }
        } else {    //Device Rotate
            currentAilment = MySingleton.getInstance().retrieveCurrentAilment();
            mCategoryNamesList = MySingleton.getInstance().retrieveCategoriesList();
        }
        */

        if(currentAilment != null){
            writeFields();
        } else {
            Toast.makeText(this, "Null Ailment", Toast.LENGTH_LONG).show();
        }
    }

    void loadWebView(WebView webView, String strTextToLoad){
        String myText ="";
        myText += "<html><body style=\"font-size:18; font-family:helvetica\"><p align=\"justify\">";
        myText += strTextToLoad + "</p></body></html>";
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webView.loadData(myText, "text/html", "utf-8");
    }

    void writeFields(){
        loadWebView(nameWebView, currentAilment.Name);
        loadWebView(introductionWebView, currentAilment.Introduction);
        loadWebView(aetiologyWebView, currentAilment.Aetiology);
        loadWebView(categoryWebView, currentAilment.Category);
        loadWebView(pathophysiologyWebView, currentAilment.PathoPhysiology);
        loadWebView(clinicalFeaturesWebView, currentAilment.ClinicalFeatures);
        loadWebView(differentialDiagnosisWebView, currentAilment.DifferentialDiagnosis);
        loadWebView(complicationsWebView, currentAilment.Complications);
        loadWebView(investigationsWebView, currentAilment.Investigations);
        loadWebView(treatmentObjectivesWebView, currentAilment.TreatmentObjectives);
        loadWebView(nonDrugTreatmentWebView, currentAilment.NonDrugTreatment);
        loadWebView(drugTreatmentWebView, currentAilment.DrugTreatment);
        loadWebView(supportiveMeasuresWebView, currentAilment.SupportiveMeasures);
        loadWebView(notableAdverseDrugReactionsWebView, currentAilment.NotableAdverseDrugReactions);
        loadWebView(cautionsWebView, currentAilment.Caution);
        loadWebView(preventionWebView, currentAilment.Prevention);
    }
}
