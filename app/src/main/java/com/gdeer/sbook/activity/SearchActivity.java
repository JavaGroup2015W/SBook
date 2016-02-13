package com.gdeer.sbook.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
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
import com.gdeer.sbook.adapter.SectionsPagerAdapter;
import com.gdeer.sbook.bean.Book;
import com.gdeer.sbook.bean.MainItem;
import com.gdeer.sbook.bean.SearchResult;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private MainRcvAdapter mRcvAdapter;
    private RecyclerView mRcvList;
    private List<Book> mDataList;
    private RequestQueue mQueue;
    private static final String ARG_BOOK_ID = "BookId";


    private GsonRequest<SearchResult> getRequest(String content){
        GsonRequest<SearchResult> gsonRequest = new GsonRequest<SearchResult>("https://api.douban.com/v2/book/search?q="+content+"&fields=id,title,images,author,publisher,pubdate",SearchResult.class,
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
        return gsonRequest;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mQueue = Volley.newRequestQueue(this);
        mRcvList = (RecyclerView) findViewById(R.id.rcv_search);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SearchActivity.this);
        mRcvList.setLayoutManager(layoutManager);


    }

    public void setRcv() {

        mRcvAdapter = new MainRcvAdapter(mDataList,mQueue);
        mRcvAdapter.setItemOnClickListener(new MainRcvAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String ID = mDataList.get(position).getId();
                Intent intent = new Intent(SearchActivity.this, SellingBookDetail.class);
                intent.putExtra(ARG_BOOK_ID,ID);
                startActivity(intent);
            }
        });
        mRcvList.setAdapter(mRcvAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        searchItem.expandActionView();
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setIconified(false);
        searchView.setFocusable(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mQueue.add(getRequest(query));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        searchItem.setChecked(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
