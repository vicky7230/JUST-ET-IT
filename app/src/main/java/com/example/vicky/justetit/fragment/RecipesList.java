package com.example.vicky.justetit.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vicky.justetit.R;
import com.example.vicky.justetit.activity.RecipeDetailWebViewActivity;
import com.example.vicky.justetit.adapter.RecipesListAdapter;
import com.example.vicky.justetit.pojo.Recipes;
import com.example.vicky.justetit.pojo.Result;
import com.example.vicky.justetit.util.RetrofitApi;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RecipesList extends Fragment {

    private static String TAG = RecipesList.class.getSimpleName();
    private List<Result> resultList;
    private ListView recipesListView;
    private RecipesListAdapter recipesListAdapter;
    private ProgressBar progressBarLoading;
    private boolean isLoading = false;
    private int pageNumber = 1;
    private LinearLayout refreshButtonAndText;
    private ImageButton refreshButton;

    public RecipesList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipes_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUI();
        makeNetworkRequest(pageNumber);
    }

    private void initUI() {
        progressBarLoading = (ProgressBar) getView().findViewById(R.id.loading_progress_bar);
        refreshButtonAndText = (LinearLayout) getView().findViewById(R.id.refresh_button_and_text);
        refreshButton = (ImageButton) getView().findViewById(R.id.refresh_button);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeNetworkRequest(pageNumber);
            }
        });
        initRecipeListView();
    }

    private void initRecipeListView() {
        recipesListView = (ListView) getView().findViewById(R.id.recipes_list);
        View headerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.recipes_list_header, null, false);
        View footerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.recipes_list_footer, null, false);
        recipesListView.addFooterView(footerView);
        recipesListView.addHeaderView(headerView);

        recipesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    Intent intent = new Intent(getActivity(), RecipeDetailWebViewActivity.class);
                    intent.setData(Uri.parse(resultList.get(position - 1).getHref()));
                    startActivity(intent);
                }
            }
        });
        recipesListView.setOnScrollListener(new AbsListView.OnScrollListener() {
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
                    makeNetworkRequest(pageNumber);
                }

            }
        });
    }

    private void makeNetworkRequest(final int p) {
        RetrofitApi.ApiInterface apiInterface = RetrofitApi.getApiInterfaceInstance();
        Call<Recipes> recipesCall = apiInterface.recipes(String.valueOf(p));
        recipesCall.enqueue(new Callback<Recipes>() {
            @Override
            public void onResponse(Response<Recipes> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    Recipes recipes = response.body();
                    progressBarLoading.setVisibility(View.GONE);
                    refreshButtonAndText.setVisibility(View.GONE);
                    recipesListView.setVisibility(View.VISIBLE);
                    if (pageNumber == 1) {
                        resultList = recipes.getResults();
                        recipesListAdapter = new RecipesListAdapter(getActivity(), resultList);
                        recipesListView.setAdapter(recipesListAdapter);
                    } else {
                        resultList.addAll(recipes.getResults());
                        recipesListAdapter.notifyDataSetChanged();
                    }
                    isLoading = false;
                    ++pageNumber;
                }
            }

            @Override
            public void onFailure(Throwable t) {
                progressBarLoading.setVisibility(View.GONE);
                recipesListView.setVisibility(View.GONE);
                refreshButtonAndText.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Network Error!!! Check your network settings.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
