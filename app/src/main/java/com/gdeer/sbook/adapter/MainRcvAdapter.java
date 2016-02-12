package com.gdeer.sbook.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.gdeer.sbook.R;
import com.gdeer.sbook.bean.Book;
import com.gdeer.sbook.bean.MainItem;

import java.util.List;

/**
 * Created by Gdeer on 2016/1/23.
 */
public class MainRcvAdapter extends RecyclerView.Adapter<MainRcvAdapter.ViewHolder> {

    private List<Book> mDataList;
    private ItemClickListener mItemClickListener;
    public RequestQueue mQueue;//用于NetworkImageView加载图片
    private ImageLoader imageLoader;//用于NetworkImageView加载图片


    public MainRcvAdapter(List<Book> mDataList,RequestQueue mQueue) {
        this.mDataList = mDataList;
        this.mQueue = mQueue;
        imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
            }

            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_main, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (mItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
        holder.bookName.setText(mDataList.get(position).getTitle());
        holder.bookInfo.setText(mDataList.get(position).getPublisher()+"/"+mDataList.get(position).getPubdate());
        holder.bookAuthor.setText(mDataList.get(position).getAuthor().get(0));
        holder.bookPicture.setImageUrl(mDataList.get(position).getImages().getLarge(),imageLoader);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView bookName;
        TextView bookInfo;
        TextView bookAuthor;
        NetworkImageView bookPicture;
        public ViewHolder(View itemView) {
            super(itemView);
            bookName = (TextView) itemView.findViewById(R.id.book_name);
            bookInfo = (TextView) itemView.findViewById(R.id.book_info);
            bookAuthor = (TextView) itemView.findViewById(R.id.book_author);
            bookPicture = (NetworkImageView) itemView.findViewById(R.id.book_picture);
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setItemOnClickListener(ItemClickListener listener){
        this.mItemClickListener = listener;
    }
}