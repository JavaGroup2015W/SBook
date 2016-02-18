package com.gdeer.sbook.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.gdeer.sbook.R;
import com.gdeer.sbook.Tool.GsonRequest;
import com.gdeer.sbook.bean.Book;
import com.gdeer.sbook.bean.SearchResult;
import com.gdeer.sbook.fragment.dummy.DummyContent;
import com.gdeer.sbook.fragment.dummy.DummyContent.DummyItem;


public class BookInfoFragment extends Fragment {


    private static final String ARG_BOOK_ID = "BookId";
    private String ID;
    private RequestQueue mQueue;
    private TextView book_title,book_writer,book_pages,book_publisher,book_pubdate,book_price,book_summary,book_catalog;
    private NetworkImageView book_pic;
    private ImageLoader imageLoader;

    //向豆瓣请求相应ID所代表的书籍的信息的GsonRequest
    private GsonRequest<Book> getRequest(String ID){
        GsonRequest<Book> gsonRequest = new GsonRequest<Book>("https://api.douban.com/v2/book/"+ID+"?fields=id,title,images,author,publisher,pubdate,price,summary,pages,catalog",Book.class,
                new Response.Listener<Book>(){
                    @Override
                    public void onResponse(Book book){
                        book_pic.setImageUrl(book.getImages().getLarge(), imageLoader);
                        book_title.setText(book.getTitle());
                        if(book.getAuthor().size()>0){book_writer.setText(book.getAuthor().get(0));}//豆瓣的数据有时会空，要处理，不然会崩溃，其它数据也有可能是空，先处理作者，之后再处理其它
                        book_pages.setText(book.getPages());
                        book_publisher.setText(book.getPublisher());
                        book_pubdate.setText(book.getPubdate());
                        book_price.setText(book.getPrice());
                        book_summary.setText(book.getSummary());
                        book_catalog.setText(book.getCatalog());
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        return gsonRequest;
    }



    public static BookInfoFragment newInstance(String ID) {
        BookInfoFragment fragment = new BookInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_BOOK_ID, ID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //获取书籍ID
        if (getArguments() != null) {
           ID = getArguments().getString(ARG_BOOK_ID);
        }

        //初始化请求队列
        mQueue = Volley.newRequestQueue(getActivity());

        //初始化ImageLoader，用于NetworkImageView的加载与显示
        imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String url, Bitmap bitmap) {}
            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookinfo, container, false);
        book_pic = (NetworkImageView) view.findViewById(R.id.book_pic);
        book_summary = (TextView) view.findViewById(R.id.book_summary);
        book_catalog = (TextView) view.findViewById(R.id.book_catalog);
        book_price = (TextView) view.findViewById(R.id.book_price);
        book_pages = (TextView) view.findViewById(R.id.book_page);
        book_pubdate = (TextView) view.findViewById(R.id.book_pubdate);
        book_publisher = (TextView) view.findViewById(R.id.book_publisher);
        book_title = (TextView) view.findViewById(R.id.book_title);
        book_writer = (TextView) view.findViewById(R.id.book_writer);

        //将请求添加到队列
        mQueue.add(getRequest(ID));

        return view;
    }


}
