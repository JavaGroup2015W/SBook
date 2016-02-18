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
    private int offset=0;
    private static final int LOAD_MORE = 0;//上拉加载更多的模式
    private static final int LOAD_NEW = 1;//搜索时的模式
    int lastVisibleItem;
    String query_saved;
    LinearLayoutManager layoutManager;

    private GsonRequest<SearchResult> getRequest(String content,int LOAD_MODE){
        if(LOAD_MODE == LOAD_NEW){//搜索，得到新结果
            offset = 0;
            GsonRequest<SearchResult> gsonRequest = new GsonRequest<SearchResult>("https://api.douban.com/v2/book/search?q="+content+"&fields=id,title,images,author,publisher,pubdate",SearchResult.class,
                new Response.Listener<SearchResult>(){
                    @Override
                    public void onResponse(SearchResult searchResult){
                        mDataList = searchResult.getBooks();
                        //Log.e("TAG", mDataList.get(0).getTitle());
                        setRcv();
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
            return gsonRequest;}
        else if(LOAD_MODE == LOAD_MORE){//加载更多
            offset += 20;
            GsonRequest<SearchResult> gsonRequest = new GsonRequest<SearchResult>("https://api.douban.com/v2/book/search?q="+content+"&fields=id,title,images,author,publisher,pubdate&start="+offset,SearchResult.class,
                    new Response.Listener<SearchResult>(){
                        @Override
                        public void onResponse(SearchResult searchResult){
                            //mDataList = searchResult.getBooks();
                            mDataList.addAll(searchResult.getBooks());
                            mRcvAdapter.notifyDataSetChanged();
                            //Log.e("TAG", mDataList.get(0).getTitle());
                            //setRcv();
                        }
                    },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("TAG", error.getMessage(), error);
                }
            });
            return gsonRequest;
        }
        return null;
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
        layoutManager = new LinearLayoutManager(SearchActivity.this);
        mRcvList.setLayoutManager(layoutManager);


    }

    public void setRcv() {

        mRcvAdapter = new MainRcvAdapter(mDataList,mQueue);
        mRcvAdapter.setItemOnClickListener(new MainRcvAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String ID = mDataList.get(position).getId();
                Intent intent = new Intent(SearchActivity.this, SellingBookDetail.class);
                intent.putExtra(ARG_BOOK_ID, ID);
                startActivity(intent);
            }
        });
        mRcvList.setAdapter(mRcvAdapter);
        mRcvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mRcvAdapter.getItemCount() && mRcvAdapter.getItemCount() != 1) {
                    mQueue.add(getRequest(query_saved, LOAD_MORE));
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });
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
                query_saved = query;
                mQueue.add(getRequest(query,LOAD_NEW));
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
