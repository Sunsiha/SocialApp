package com.moonlyte.myjavalibrary;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moonlyte.myjavalibrary.adapter.AlbumAdapter;
import com.moonlyte.myjavalibrary.api.API;
import com.moonlyte.myjavalibrary.interfaces.ListAction;
import com.moonlyte.myjavalibrary.model.Albums;
import com.moonlyte.myjavalibrary.network.ApiClient;
import com.moonlyte.myjavalibrary.progressHUD.ProgressHUD;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AlbumsActivity extends AppCompatActivity implements ListAction, View.OnClickListener {
    private RecyclerView albumsRV;
    private API api;
    private AlbumAdapter albumAdapter;
    private ProgressHUD progressHUD;
    private Toolbar toolbar;
    private ImageView backIV;
    int[] rainbow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);
        this.api = ApiClient.getApiClient().create(API.class);
        Integer userId=getIntent().getIntExtra("userId",0);
        initComponents();
        getUserAlbums(userId);
    }

    private void initComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        backIV = (ImageView) toolbar.findViewById(R.id.backIV);
        TextView toolbarTitleTv = (TextView) findViewById(R.id.toolbarTitleTv);
        toolbarTitleTv.setText("Albums");
        backIV.setOnClickListener(this);
        rainbow = this.getResources().getIntArray(R.array.rainbow);
        albumsRV = findViewById(R.id.albumsRV);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        albumsRV.setLayoutManager(mLayoutManager);
    }

    private void loadAlbums(List<Albums> albumnCall) {
        albumAdapter = new AlbumAdapter(albumnCall, this,rainbow);
        albumsRV.setAdapter(albumAdapter);
        albumAdapter.notifyDataSetChanged();
    }
    private void getUserAlbums(Integer userId) {
        progressHUD = ProgressHUD.show(AlbumsActivity.this, true, false, null);
        Call<List<Albums>> albumnCall = this.api.getUserAlbums(userId);
        albumnCall.enqueue(new Callback<List<Albums>>() {
            @Override
            public void onResponse(Call<List<Albums>> call, Response<List<Albums>> response) {
                if (!response.body().isEmpty()) {
                    loadAlbums(response.body());
                }
                hideDialog();
            }

            @Override
            public void onFailure(Call<List<Albums>> call, Throwable t) {
                Toast.makeText(AlbumsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                hideDialog();
            }
        });
    }

    @Override
    public void onItemClick(Integer id) {

    }
    private void hideDialog() {
        if (progressHUD != null && progressHUD.isShowing()) {
            progressHUD.dismiss();
        }
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.backIV) {
            onBackPressed();
        }
    }
}
