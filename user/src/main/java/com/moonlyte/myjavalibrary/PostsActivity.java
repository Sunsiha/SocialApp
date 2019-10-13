package com.moonlyte.myjavalibrary;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.moonlyte.myjavalibrary.adapter.PostsExpandableAdapter;
import com.moonlyte.myjavalibrary.api.API;
import com.moonlyte.myjavalibrary.model.Comments;
import com.moonlyte.myjavalibrary.model.Posts;
import com.moonlyte.myjavalibrary.network.ApiClient;
import com.moonlyte.myjavalibrary.progressHUD.ProgressHUD;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PostsActivity extends AppCompatActivity implements View.OnClickListener {
    private API api;
    private ProgressHUD progressHUD;
    private ExpandableListView expandableListView;
    private PostsExpandableAdapter expandableListAdapter;
    private Toolbar toolbar;
    private ImageView backIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        this.api = ApiClient.getApiClient().create(API.class);
        initComponents();
    }

    private void initComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        backIV = (ImageView) toolbar.findViewById(R.id.backIV);
        TextView toolbarTitleTv = (TextView) findViewById(R.id.toolbarTitleTv);
        toolbarTitleTv.setText("Posts");
        backIV.setOnClickListener(this);

        expandableListView = findViewById(R.id.expandableListView);
        Integer userId = getIntent().getIntExtra("userId", 0);
        getUserPosts(userId);
    }


    private void loadPosts(List<Posts> postsCall) {
        expandableListAdapter = new PostsExpandableAdapter(this, postsCall);
        expandableListView.setAdapter(expandableListAdapter);
    }

    /*In the Posts section, there should be a collapsible to show the comments
    associated with a post. Limit the comments to the first 3.
     */
    private void getUserPosts(Integer id) {
        progressHUD = ProgressHUD.show(PostsActivity.this, true, false, null);
        final Call<List<Posts>> postsCall = this.api.getUserPosts(id);
        postsCall.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, final Response<List<Posts>> response) {
                if (!response.body().isEmpty()) {
                    for (int i = 0; i < response.body().size(); i++) {
                        final int index = i;
                        Integer postId = response.body().get(index).id;
                        Call<List<Comments>> commentsCall = api.getUserComments(postId);
                        commentsCall.enqueue(new Callback<List<Comments>>() {
                            @Override
                            public void onResponse(Call<List<Comments>> call, Response<List<Comments>> commentsResponse) {
                                if (!commentsResponse.body().isEmpty() && commentsResponse.body().size() > 2) {
                                    response.body().get(index).commentsList.add(commentsResponse.body().get(0));
                                    response.body().get(index).commentsList.add(commentsResponse.body().get(1));
                                    response.body().get(index).commentsList.add(commentsResponse.body().get(2));
                                  /* List<Comments>commentsList=new ArrayList<>();
                                   for(int i = 0; i < 3; i++) {
                                       commentsList.add(response.body().get(index).commentsList.add(commentsResponse.body().get(i));
                                   }*/
                                }
                                if (index == response.body().size()) {
                                    loadPosts(response.body());
                                }
                                hideDialog();
                            }

                            @Override
                            public void onFailure(Call<List<Comments>> call, Throwable t) {
                                Toast.makeText(PostsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                                hideDialog();
                            }
                        });
                    }
                    loadPosts(response.body());

                }
            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {
                Toast.makeText(PostsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                hideDialog();
            }
        });

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
