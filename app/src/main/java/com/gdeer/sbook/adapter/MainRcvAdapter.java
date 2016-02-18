package com.gdeer.sbook.adapter;

import android.content.ClipData;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.gdeer.sbook.R;
import com.gdeer.sbook.bean.Book;

import java.util.List;

/**
 * Created by Gdeer on 2016/1/23.
 */
public class MainRcvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Book> mDataList;
    private ItemClickListener mItemClickListener;
    public RequestQueue mQueue;//用于NetworkImageView加载图片
    private ImageLoader imageLoader;//用于NetworkImageView加载图片
    public static final int TYPE_ITEM = 0;//普通itemview标志
    public static final int TYPE_FOOT= 1;//底部footview标志


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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM){
            View view = View.inflate(parent.getContext(), R.layout.item_main, null);
            return new ItemViewHolder(view);
        }else if(viewType == TYPE_FOOT){
            View view = View.inflate(parent.getContext(),R.layout.item_main_foot,null);
            return new FootViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof ItemViewHolder){
            if (mItemClickListener != null){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemClickListener.onItemClick(holder.itemView, position);
                    }
                });
            }
            ((ItemViewHolder)holder).bookName.setText(mDataList.get(position).getTitle());
            ((ItemViewHolder)holder).bookInfo.setText(mDataList.get(position).getPublisher() + "/" + mDataList.get(position).getPubdate());
            if(mDataList.get(position).getAuthor().size()>0){
                ((ItemViewHolder)holder).bookAuthor.setText(mDataList.get(position).getAuthor().get(0));//豆瓣的数据有时会空，要处理，不然会崩溃，其它数据也有可能是空，先处理作者，之后再处理其它
            }
            ((ItemViewHolder)holder).bookPicture.setImageUrl(mDataList.get(position).getImages().getLarge(),imageLoader);
        }
        if(holder instanceof FootViewHolder){

        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size()+1;
    }

    @Override
    public int getItemViewType(int position){
        // 最后一个item设置为footerView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOT;
        } else {
            return TYPE_ITEM;
        }
    }



    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView bookName;
        TextView bookInfo;
        TextView bookAuthor;
        NetworkImageView bookPicture;
        public ItemViewHolder(View itemView) {
            super(itemView);
            bookName = (TextView) itemView.findViewById(R.id.book_name);
            bookInfo = (TextView) itemView.findViewById(R.id.book_info);
            bookAuthor = (TextView) itemView.findViewById(R.id.book_author);
            bookPicture = (NetworkImageView) itemView.findViewById(R.id.book_picture);
        }
    }

    class FootViewHolder extends RecyclerView.ViewHolder{
        ProgressBar progressBar;
        public FootViewHolder(View itemView){
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
        }
    }

    public interface ItemClickListener {
        void onItemClick(final View view, int position);
    }

    public void setItemOnClickListener(ItemClickListener listener){
        this.mItemClickListener = listener;
    }
}