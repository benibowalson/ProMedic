package com.example.a48101040.promedic;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.a48101040.promedic.adapters.AilmentRecyclerAdapter;
import com.example.a48101040.promedic.data.Ailment;
import com.example.a48101040.promedic.db.DbHelper;
import com.example.a48101040.promedic.dialogs.AboutDialog;
import com.example.a48101040.promedic.loaders.AilmentsLoader;
import com.example.a48101040.promedic.loaders.CategoriesLoader;
import com.example.a48101040.promedic.utilities.MyConstants;
import com.example.a48101040.promedic.utilities.MyDividerItemDecoration;
import com.example.a48101040.promedic.utilities.MySingleton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements AilmentRecyclerAdapter.IListenToClicks {

    private DbHelper mDbHelper;
    private List<Ailment> mAilmentsList = new ArrayList<>();
    private List<String> mCategoriesList = new ArrayList<>();
    private RecyclerView myRecyclerView;
    private android.support.v7.widget.SearchView mSearchView;
    private AilmentRecyclerAdapter myRecyclerAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    static final int AILMENTS_LOADER_ID = 1;
    static final int CATEGORIES_LOADER_ID = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup Toolbar
        Toolbar myAppToolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(myAppToolbar);

        getSupportActionBar().setTitle(R.string.toolbar_title);

        //Setup RecyclerView
        myRecyclerView = (RecyclerView) findViewById(R.id.rv_Recycler);
        whiteNotificationBar(myRecyclerView);
        myRecyclerAdapter = new AilmentRecyclerAdapter(this, mAilmentsList, this);

        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        myRecyclerView.setLayoutManager(mLinearLayoutManager);

        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setItemAnimator(new DefaultItemAnimator());
        myRecyclerView.addItemDecoration(new MyDividerItemDecoration(this, MyDividerItemDecoration.VERTICAL_LIST, 36));
        myRecyclerView.setAdapter(myRecyclerAdapter);


        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewOrEditAilmentActivity();
            }
        });

        mDbHelper = new DbHelper(this);

        if(savedInstanceState == null){
            loadMyData();
        } else {
            getSupportLoaderManager().initLoader(AILMENTS_LOADER_ID, null, myAilmentsLoaderCallbacks);
        }
    }

    void loadMyData(){
        getSupportLoaderManager().initLoader(CATEGORIES_LOADER_ID, null, myCategoriesLoaderCallbacks);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (android.support.v7.widget.SearchView) menu.findItem(R.id.mnuSearch).getActionView();
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setMaxWidth(Integer.MAX_VALUE);
        mSearchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                myRecyclerAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myRecyclerAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnuSearch:
                return true;
            case R.id.mnuNewAilment:
                startNewOrEditAilmentActivity();
                return true;
            case R.id.mnuCategories:
                return true;
            case R.id.mnuAbout:
                showDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!mSearchView.isIconified()) {
            mSearchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }



    private android.support.v4.app.LoaderManager.LoaderCallbacks<List<Ailment>> myAilmentsLoaderCallbacks = new android.support.v4.app.LoaderManager.LoaderCallbacks<List<Ailment>>() {
        @Override
        public android.support.v4.content.Loader<List<Ailment>> onCreateLoader(int id, Bundle args) {
            return new AilmentsLoader(getApplicationContext(), mDbHelper);
        }

        @Override
        public void onLoadFinished(android.support.v4.content.Loader<List<Ailment>> loader, List<Ailment> data) {
            mAilmentsList = data;
            myRecyclerAdapter.swapData(data);
            MySingleton.getInstance().saveAilmentsList(data);
            myRecyclerAdapter.notifyDataSetChanged();
        }

        @Override
        public void onLoaderReset(android.support.v4.content.Loader<List<Ailment>> loader) {
            myRecyclerAdapter.swapData(null);
        }
    };

    private android.support.v4.app.LoaderManager.LoaderCallbacks<List<String>> myCategoriesLoaderCallbacks = new android.support.v4.app.LoaderManager.LoaderCallbacks<List<String>>() {
        @Override
        public android.support.v4.content.Loader<List<String>> onCreateLoader(int id, Bundle args) {
            return new CategoriesLoader(getApplicationContext(), mDbHelper);
        }

        @Override
        public void onLoadFinished(android.support.v4.content.Loader<List<String>> loader, List<String> data) {
            mCategoriesList = data;
            MySingleton.getInstance().saveCategoriesList(mCategoriesList);    //Hold data in Singleton class

            if(mCategoriesList.size() > 0){
                getSupportLoaderManager().initLoader(AILMENTS_LOADER_ID, null, myAilmentsLoaderCallbacks);
            } else {
                //Write categories into db for first time
                mCategoriesList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.categoriesArray)));
                for(String aCategoryName:mCategoriesList){
                    ContentValues contentValues = new ContentValues();
                    //contentValues.put(DbHelper.ID_FOR_CATEGORY_TABLE, null);
                    contentValues.put(DbHelper.CATEGORY_COL_NAME, aCategoryName);
                    SQLiteDatabase db = mDbHelper.getWritableDatabase();
                    long insertResult = db.insert(DbHelper.CATEGORIES_TABLE_NAME, null, contentValues);
                }
            }
        }

        @Override
        public void onLoaderReset(android.support.v4.content.Loader<List<String>> loader) {

        }
    };

    @Override
    public void onAilmentNameClicked(int clickedPos) {
        Ailment anAilment = mAilmentsList.get(clickedPos);
        MySingleton.getInstance().saveCurrentAilment(anAilment);    //Save current Ailment for use in Details Activity
        //Details Activity
        Intent detailsActivityStartIntent = new Intent(this, DetailsActivity.class);
        startActivity(detailsActivityStartIntent);
    }

    void startNewOrEditAilmentActivity(){       //actionCode 1/0 for New/Edit Ailment
        Intent intent = new Intent(this, NewOrEditAilment.class);
        MySingleton.getInstance().saveActionCode(1);        //1 is ActionCode for New Ailment (as opposed to an Edit Operation)
        startActivity(intent);
    }

    void showDialog(){
        AboutDialog myAboutDialog = new AboutDialog();
        myAboutDialog.show(getFragmentManager(), "About ProMedic");
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }
}
