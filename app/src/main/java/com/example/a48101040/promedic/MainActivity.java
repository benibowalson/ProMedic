package com.example.a48101040.promedic;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.a48101040.promedic.adapters.AilmentRecyclerAdapter;
import com.example.a48101040.promedic.data.Ailment;
import com.example.a48101040.promedic.db.DbHelper;
import com.example.a48101040.promedic.tasks.AilmentsLoader;
import com.example.a48101040.promedic.tasks.CategoriesLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener, AilmentRecyclerAdapter.IListenToClicks {

    private DbHelper mDbHelper;
    private List<Ailment> mAilmentsList = new ArrayList<>();
    private List<String> mCategoriesList = new ArrayList<>();
    private RecyclerView myRecyclerView;
    private AilmentRecyclerAdapter myRecyclerAdapter;
    private GridLayoutManager mGridLayoutManager;
    static final int AILMENTS_LOADER_ID = 1;
    static final int CATEGORIES_LOADER_ID = 2;
    public static final String RECYCLERVIEW_POSITION_STATE_KEY = "recycler_position_state";
    Parcelable recyclerViewState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup Toolbar
        Toolbar myAppToolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(myAppToolbar);

        //Setup RecyclerView
        myRecyclerView = (RecyclerView) findViewById(R.id.rv_Recycler);
        myRecyclerView.setHasFixedSize(true);
        myRecyclerAdapter = new AilmentRecyclerAdapter(MainActivity.this, mAilmentsList, MainActivity.this);
        myRecyclerView.setAdapter(myRecyclerAdapter);

        mGridLayoutManager = new GridLayoutManager(this, 1);
        myRecyclerView.setLayoutManager(mGridLayoutManager);


        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.fab);

        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewOrEditAilmentActivity();
            }
        });

        mDbHelper = new DbHelper(this);

        getSupportLoaderManager().initLoader(CATEGORIES_LOADER_ID, null, myCategoriesLoaderCallbacks);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*getMenuInflater().inflate(R.menu.options_menu, menu);
        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView)menu.findItem(R.id.search).getActionView();
        //SearchView searchView = (SearchView)menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener((android.support.v7.widget.SearchView.OnQueryTextListener) searchView);
        */
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnuNewAilment:
                startNewOrEditAilmentActivity();
                return true;
            case R.id.mnuCategories:
                return true;
            case R.id.mnuNewCategory:
                return true;
            /*case R.id.search:
                onSearchRequested();
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String strSearch = intent.getStringExtra(SearchManager.QUERY);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        recyclerViewState = mGridLayoutManager.onSaveInstanceState();           //Save RecyclerView Position
        outState.putParcelable(RECYCLERVIEW_POSITION_STATE_KEY, recyclerViewState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState != null){
            recyclerViewState = savedInstanceState.getParcelable(RECYCLERVIEW_POSITION_STATE_KEY);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(recyclerViewState != null)
            mGridLayoutManager.onRestoreInstanceState(recyclerViewState);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        myRecyclerAdapter.getFilter().filter(s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        myRecyclerAdapter.getFilter().filter(s);
        return false;
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
        //Details Activity
        Bundle extrasBundle = new Bundle();
        extrasBundle.putParcelable("CLICKED_AILMENT", anAilment);
        Intent detailsActivityStartIntent = new Intent(this, DetailsActivity.class);
        detailsActivityStartIntent.putExtras(extrasBundle);
        startActivity(detailsActivityStartIntent);
    }

    void startNewOrEditAilmentActivity(){       //actionCode 1/0 for New/Edit Ailment
        Intent intent = new Intent(this, NewOrEditAilment.class);
        intent.putExtra("action_code", 1);
        intent.putStringArrayListExtra("category_names_list", (ArrayList<String>)mCategoriesList);  //Cast list to ArrayList
        startActivity(intent);
    }
}
