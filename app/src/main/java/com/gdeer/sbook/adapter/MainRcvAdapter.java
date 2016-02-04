package com.gdeer.sbook.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gdeer.sbook.R;
import com.gdeer.sbook.bean.MainItem;

import java.util.List;

/**
 * Created by Gdeer on 2016/1/23.
 */
public class MainRcvAdapter extends RecyclerView.Adapter<MainRcvAdapter.ViewHolder> {

    private List<MainItem> mDataList;
    private ItemClickListener mItemClickListener;

    public MainRcvAdapter(List<MainItem> mDataList) {
        this.mDataList = mDataList;
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
        holder.bookName.setText(mDataList.get(position).getBookName());
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView bookName;
        public ViewHolder(View itemView) {
            super(itemView);
            bookName = (TextView) itemView.findViewById(R.id.book_name);
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setItemOnClickListener(ItemClickListener listener){
        this.mItemClickListener = listener;
    }
}