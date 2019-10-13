package com.moonlyte.myjavalibrary;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moonlyte.myjavalibrary.adapter.PhotosAdapter;
import com.moonlyte.myjavalibrary.api.API;
import com.moonlyte.myjavalibrary.interfaces.ListAction;
import com.moonlyte.myjavalibrary.model.Photos;
import com.moonlyte.myjavalibrary.network.ApiClient;
import com.moonlyte.myjavalibrary.progressHUD.ProgressHUD;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PhotosActivity extends AppCompatActivity implements ListAction, View.OnClickListener {
    private API api;
    private RecyclerView photosRV;
    private PhotosAdapter photosAdapter;
    private ProgressHUD progressHUD;
    private Toolbar toolbar;
    private ImageView backIV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        this.api = ApiClient.getApiClient().create(API.class);
        initComponents();
        Integer albumId=getIntent().getIntExtra("albumId",0);
        getAlbumPhotos(albumId);
    }
    private void initComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        backIV = (ImageView) toolbar.findViewById(R.id.backIV);
        TextView toolbarTitleTv = (TextView) findViewById(R.id.toolbarTitleTv);
        toolbarTitleTv.setText("Photos");
        backIV.setOnClickListener(this);

        photosRV = findViewById(R.id.photosRV);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        photosRV.setLayoutManager(mLayoutManager);

        /**
         Simple GridLayoutManager that spans two columns
         **/
        @SuppressLint("WrongConstant") GridLayoutManager manager = new GridLayoutManager(this, 2,
                GridLayoutManager.VERTICAL, false);
        photosRV.setLayoutManager(manager);
    }
    private void loadPhotos(List<Photos> albumnCall) {
        photosAdapter = new PhotosAdapter(  PhotosActivity.this,albumnCall, this);
        photosRV.setAdapter(photosAdapter);
        photosAdapter.notifyDataSetChanged();
    }
    private void getAlbumPhotos(Integer albumId) {
        progressHUD = ProgressHUD.show(PhotosActivity.this, true, false, null);
        Call<List<Photos>> photosCall = this.api.getAlbumPhotos(albumId);
        photosCall.enqueue(new Callback<List<Photos>>() {
            @Override
            public void onResponse(Call<List<Photos>> call, Response<List<Photos>> response) {
                if (!response.body().isEmpty()) {
                    loadPhotos(response.body());
                }
                hideDialog();
            }

            @Override
            public void onFailure(Call<List<Photos>> call, Throwable t) {
                Toast.makeText(PhotosActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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
