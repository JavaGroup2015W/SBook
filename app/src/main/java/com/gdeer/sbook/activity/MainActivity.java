package com.gdeer.sbook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.gdeer.sbook.R;
import com.gdeer.sbook.Tool.GsonRequest;
import com.gdeer.sbook.adapter.MainRcvAdapter;
import com.gdeer.sbook.bean.Book;
import com.gdeer.sbook.bean.MainItem;
import com.gdeer.sbook.bean.SearchResult;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mRcvList;
    private List<Book> mDataList;
    private MainRcvAdapter mRcvAdapter;
    private SwipeRefreshLayout mSwipeRefresh;
    private RequestQueue mQueue;
    private static final String ARG_BOOK_ID = "BookId";

    //向豆瓣请求数据的GsonRequest，暂时先设置为获取标签为“小说”的书
    GsonRequest<SearchResult> gsonRequest = new GsonRequest<SearchResult>("https://api.douban.com/v2/book/search?tag=小说&fields=id,title,images,author,publisher,pubdate",SearchResult.class,
            new Response.Listener<SearchResult>(){
                @Override
                public void onResponse(SearchResult searchResult){
                    mDataList = searchResult.getBooks();
                    Log.e("TAG", mDataList.get(0).getTitle());
                    setRcv();
                    }
            },new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("TAG", error.getMessage(), error);
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQueue = Volley.newRequestQueue(this);
        mQueue.add(gsonRequest);
        setToolbarAndNav();
        setFab();

        setSwipeRefreshLayout();
    }


    public void setToolbarAndNav() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void setFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });
    }



    public void setRcv() {
        mRcvList = (RecyclerView) findViewById(R.id.rcv_main);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        mRcvList.setLayoutManager(layoutManager);

        mRcvAdapter = new MainRcvAdapter(mDataList,mQueue);
        mRcvAdapter.setItemOnClickListener(new MainRcvAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String ID = mDataList.get(position).getId();
                Intent intent = new Intent(MainActivity.this, SellingBookDetail.class);
                intent.putExtra(ARG_BOOK_ID,ID);
                startActivity(intent);
            }
        });
        mRcvList.setAdapter(mRcvAdapter);
    }

    public void setSwipeRefreshLayout() {
        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRcvAdapter.notifyDataSetChanged();
                        mSwipeRefresh.setRefreshing(false);
                    }
                }, 600);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search_main) {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivityForResult(intent, 1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_first_page) {
            // Handle the camera action
        } else if (id == R.id.nav_message) {
            Intent intent = new Intent(MainActivity.this, MessageActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
