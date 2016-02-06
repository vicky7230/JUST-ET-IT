package com.example.vicky.justetit.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vicky.justetit.R;
import com.example.vicky.justetit.activity.RecipeDetailWebView;
import com.example.vicky.justetit.adapter.RecipesListAdapter;
import com.example.vicky.justetit.pojo.Recipes;
import com.example.vicky.justetit.pojo.Result;
import com.example.vicky.justetit.util.RetrofitApi;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeftOvers extends Fragment {

    private ProgressBar progressBarLoading;
    private ListView leftoversRecipeListView;
    private List<Result> resultList;
    private boolean isLoading = false;
    private RecipesListAdapter recipesListAdapter;
    private int pageNumber = 1;
    private LinearLayout refreshButtonAndText;
    private ImageButton refreshButton;
    private EditText leftoversEditText;
    private String leftovers;
    View footerView;

    public LeftOvers() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_left_overs, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUI();

    }

    private void initUI() {
        progressBarLoading = (ProgressBar) getView().findViewById(R.id.loading_progress_bar_leftovers);
        initLeftoversEditText();
        initRefreshButtonAndText();
        initLeftoversRecipeListView();
    }

    private void initRefreshButtonAndText() {
        refreshButtonAndText = (LinearLayout) getView().findViewById(R.id.refresh_button_and_text_leftovers);
        refreshButton = (ImageButton) getView().findViewById(R.id.refresh_button_leftovers);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeNetworkRequest(leftovers, pageNumber);
            }
        });
    }

    private void initLeftoversEditText() {
        leftoversEditText = (EditText) getView().findViewById(R.id.leftovers_edit_text);
        leftoversEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!leftoversEditText.getText().toString().isEmpty()) {
                        pageNumber = 1;
                        leftoversRecipeListView.setVisibility(View.GONE);
                        leftovers = leftoversEditText.getText().toString();
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        progressBarLoading.setVisibility(View.VISIBLE);
                        makeNetworkRequest(leftovers, pageNumber);
                        Toast.makeText(getActivity(), leftovers, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Please enter leftovers", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void initLeftoversRecipeListView() {
        leftoversRecipeListView = (ListView) getView().findViewById(R.id.leftovers_recipe_list);
        footerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.recipes_list_footer, null, false);
        leftoversRecipeListView.addFooterView(footerView);
        leftoversRecipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), RecipeDetailWebView.class);
                intent.setData(Uri.parse(resultList.get(position).getHref()));
                startActivity(intent);

            }
        });
        leftoversRecipeListView.setOnScrollListener(new AbsListView.OnScrollListener() {
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
                    makeNetworkRequest(leftovers, pageNumber);
                }

            }
        });
    }

    private void makeNetworkRequest(String leftovers, final int p) {
        RetrofitApi.ApiInterface apiInterface = RetrofitApi.getApiInterfaceInstance();
        Call<Recipes> recipesCall = apiInterface.leftovers(leftovers, String.valueOf(p));
        recipesCall.enqueue(new Callback<Recipes>() {
            @Override
            public void onResponse(Response<Recipes> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    Recipes recipes = response.body();
                    progressBarLoading.setVisibility(View.GONE);
                    refreshButtonAndText.setVisibility(View.GONE);
                    if (pageNumber == 1) {
                        resultList = recipes.getResults();
                        recipesListAdapter = new RecipesListAdapter(getActivity(), resultList);
                        leftoversRecipeListView.setAdapter(recipesListAdapter);
                    } else {
                        resultList.addAll(recipes.getResults());
                        recipesListAdapter.notifyDataSetChanged();
                    }
                    leftoversRecipeListView.setVisibility(View.VISIBLE);
                    isLoading = false;
                    ++pageNumber;
                }
            }

            @Override
            public void onFailure(Throwable t) {
                progressBarLoading.setVisibility(View.GONE);
                leftoversRecipeListView.setVisibility(View.GONE);
                refreshButtonAndText.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Network Error!!! Check your network settings.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

