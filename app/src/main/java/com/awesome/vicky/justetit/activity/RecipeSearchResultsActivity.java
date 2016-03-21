package com.awesome.vicky.justetit.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.awesome.vicky.justetit.R;
import com.awesome.vicky.justetit.adapter.RecipesListAdapter;
import com.awesome.vicky.justetit.pojo.Recipes;
import com.awesome.vicky.justetit.pojo.Result;
import com.awesome.vicky.justetit.util.RetrofitApi;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RecipeSearchResultsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private List<Result> resultList;
    private ListView searchRecipesListView;
    private RecipesListAdapter recipesListAdapter;
    private ProgressBar progressBarLoading;
    private boolean isLoading = false;
    private int pageNumber = 1;
    private LinearLayout refreshButtonAndText;
    private ImageButton refreshButton;
    private String query;
    private View footerView;
    private LinearLayout noResultsTextAndImage;
    private boolean isActivityVisible = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search_results);
        initUI();
        handleIntent(getIntent());
    }

    private void initUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_search);
        setSupportActionBar(toolbar);
        noResultsTextAndImage = (LinearLayout) findViewById(R.id.no_results_text_and_image);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Search Results");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        progressBarLoading = (ProgressBar) findViewById(R.id.loading_progress_bar_search);
        initRefreshButtonAndText();
        initSearchRecipeListView();
    }

    private void initSearchRecipeListView() {
        searchRecipesListView = (ListView) findViewById(R.id.recipes_list_search);
        footerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.recipes_list_footer, null, false);
        searchRecipesListView.addFooterView(footerView);

        searchRecipesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RecipeSearchResultsActivity.this, RecipeDetailWebViewActivity.class);
                intent.setData(Uri.parse(resultList.get(position).getHref()));
                startActivity(intent);
            }
        });
        searchRecipesListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (view.getAdapter() == null)
                    return;
                if (view.getAdapter().getCount() == 0)
                    return;
                int l = visibleItemCount + firstVisibleItem;
                if (l >= totalItemCount && !isLoading) {
                    // It is time to add new data. We call the listener
                    isLoading = true;
                    makeNetworkRequest(query, pageNumber);
                }

            }
        });
    }

    private void makeNetworkRequest(String query, int p) {
        RetrofitApi.ApiInterface apiInterface = RetrofitApi.getApiInterfaceInstance();
        Call<Recipes> recipesCall = apiInterface.search(query, String.valueOf(p));
        recipesCall.enqueue(new Callback<Recipes>() {
            @Override
            public void onResponse(Response<Recipes> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    Recipes recipes = response.body();
                    progressBarLoading.setVisibility(View.GONE);
                    refreshButtonAndText.setVisibility(View.GONE);
                    searchRecipesListView.setVisibility(View.VISIBLE);
                    if (pageNumber == 1) {
                        resultList = recipes.getResults();
                        recipesListAdapter = new RecipesListAdapter(RecipeSearchResultsActivity.this, resultList);
                        searchRecipesListView.setAdapter(recipesListAdapter);
                    } else {
                        resultList.addAll(recipes.getResults());
                        recipesListAdapter.notifyDataSetChanged();
                    }
                    if (recipes.getResults().size() == 0) {
                        searchRecipesListView.removeFooterView(footerView);
                        //footerView.setVisibility(View.GONE);
                    }
                    if (resultList.size() == 0) {
                        noResultsTextAndImage.setVisibility(View.VISIBLE);
                    }
                    isLoading = false;
                    ++pageNumber;
                } else {
                    progressBarLoading.setVisibility(View.GONE);
                    searchRecipesListView.removeFooterView(footerView);
                    if (pageNumber == 1)
                        noResultsTextAndImage.setVisibility(View.VISIBLE);
                    //Toast.makeText(RecipeSearchResultsActivity.this, "Ya I'm tghe Bug", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                progressBarLoading.setVisibility(View.GONE);
                searchRecipesListView.setVisibility(View.GONE);
                refreshButtonAndText.setVisibility(View.VISIBLE);
                if (isActivityVisible)
                    Toast.makeText(RecipeSearchResultsActivity.this, "Network Error!!! Check your network settings.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initRefreshButtonAndText() {
        refreshButtonAndText = (LinearLayout) findViewById(R.id.refresh_button_and_text_search);
        refreshButton = (ImageButton) findViewById(R.id.refresh_button_search);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshButtonAndText.setVisibility(View.GONE);
                progressBarLoading.setVisibility(View.VISIBLE);
                makeNetworkRequest(query, pageNumber);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
            showResults();
        }
    }

    private void showResults() {
        makeNetworkRequest(query, pageNumber);
        Toast.makeText(RecipeSearchResultsActivity.this, "Searching....", Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActivityVisible = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActivityVisible = false;
    }
}

