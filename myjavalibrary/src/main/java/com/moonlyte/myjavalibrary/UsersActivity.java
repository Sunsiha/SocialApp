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

import com.moonlyte.myjavalibrary.adapter.UsersAdapter;
import com.moonlyte.myjavalibrary.api.API;
import com.moonlyte.myjavalibrary.interfaces.ListAction;
import com.moonlyte.myjavalibrary.model.Users;
import com.moonlyte.myjavalibrary.network.ApiClient;
import com.moonlyte.myjavalibrary.progressHUD.ProgressHUD;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UsersActivity extends AppCompatActivity implements ListAction {
    UsersAdapter usersAdapter;
    RecyclerView usersRV;
    private API api;
    private ProgressHUD progressHUD;
    Toolbar toolbar;
    ImageView backIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.api = ApiClient.getApiClient().create(API.class);
        initComponents();
    }

    private void initComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        backIV = (ImageView) toolbar.findViewById(R.id.backIV);
        backIV.setVisibility(View.GONE);
        TextView toolbarTitleTv = (TextView) findViewById(R.id.toolbarTitleTv);
        toolbarTitleTv.setText("Users - Launch Library Activity");

        usersRV = findViewById(R.id.usersRV);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        usersRV.setLayoutManager(mLayoutManager);
        getUsers();
    }

    private void loadUsers(List<Users> usersCall) {
        usersAdapter = new UsersAdapter(usersCall, this);
        usersRV.setAdapter(usersAdapter);
        usersAdapter.notifyDataSetChanged();
    }

    /*The App starts and lists all the users together with his name, email and address.
     */
    private void getUsers() {
        progressHUD = ProgressHUD.show(UsersActivity.this, true, false, null);
        Call<List<Users>> usersCall = this.api.getUsersOld();
        usersCall.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                if (!response.body().isEmpty()) {
                    loadUsers(response.body());
                }
                hideDialog();
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                Toast.makeText(UsersActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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
}
