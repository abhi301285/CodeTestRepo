package com.test.code.codetestapp.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.test.code.codetestapp.BuildConfig;
import com.test.code.codetestapp.R;
import com.test.code.codetestapp.data.local.AlbumDatabase;
import com.test.code.codetestapp.data.model.Album;
import com.test.code.codetestapp.network.APIService;
import com.test.code.codetestapp.network.NetworkUtil;
import com.test.code.codetestapp.network.OKHttpHelper;
import com.test.code.codetestapp.ui.CodeTestApp;
import com.test.code.codetestapp.ui.adapter.AlbumAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private APIService mAPIService;
    private AlbumAdapter mAdapter;
    private AlbumDatabase mAlbumDatabase;
    private ProgressBar mProgressBar;
    private TextView mErrorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        getData();

    }

    private void getData() {
        mProgressBar.setVisibility(View.VISIBLE);
        if (NetworkUtil.isNetworkConnected(this)) {
            getAlbumList();
        } else {
            getAlbumFromDatabase();
        }
    }


    private void initViews() {
        mAlbumDatabase = ((CodeTestApp) this.getApplication()).getAppDatabase().albumDatabase();
        mProgressBar = findViewById(R.id.progressBar);
        mErrorText = findViewById(R.id.errorText);
        mRecyclerView = findViewById(R.id.albumList);
        mLayoutManager = new LinearLayoutManager(this
                , LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new AlbumAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void getAlbumList() {
        mAPIService = OKHttpHelper.getAPIService(BuildConfig.WEB_URL);

        Call<List<Album>> call = mAPIService.getAlbumList();
        call.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                if (response.isSuccessful()) {
                    List<Album> albumList = response.body();
                    if (!albumList.isEmpty()) {
                        showRecyclerView();
                        setAlbumList(albumList);
                        setAlbumInDatabase(albumList);
                    } else {
                        showErrorText();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                showErrorText();
                Log.e("MainActivity", "Unable to submit  to API.");
            }
        });
    }


    private void setAlbumList(List<Album> albumList) {
        mAdapter.setAlbumList(albumList);
    }

    private void setAlbumInDatabase(final List<Album> albumList) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mAlbumDatabase.insertMultipleAlbum(albumList);
            }
        }).start();

    }

    private void getAlbumFromDatabase() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Album> albumList = mAlbumDatabase.fetchOAllAlbum();
                if (!albumList.isEmpty()) {
                    showRecyclerView();
                    mAdapter.setAlbumList(albumList);
                } else {
                    showErrorText();
                }
            }
        }).start();

    }

    public void showRecyclerView() {
        mErrorText.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    private void showErrorText() {
        mErrorText.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
    }
}
