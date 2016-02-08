package com.gdeer.sbook.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gdeer.sbook.R;
import com.gdeer.sbook.bean.SellerItem;

import java.util.List;

/**
 * Created by caisi on 2016/2/8.
 */
public class SellerRcvAdapter extends RecyclerView.Adapter<SellerRcvAdapter.ViewHolder>{

    private List<SellerItem> mDataList;
    private onItemClickListener mItemClickListener;

    public SellerRcvAdapter(List<SellerItem> DataList){this.mDataList = DataList;}

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_seller, null);
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
        holder.seller_info.setText(mDataList.get(position).getUserName()+"     "+"价格："+mDataList.get(position).getPrice()+"     "+"地址："+mDataList.get(position).getArea());
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public interface onItemClickListener {
        void onItemClick(View view, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView seller_info;
        public ViewHolder(View itemView) {
            super(itemView);
             seller_info = (TextView) itemView.findViewById(R.id.seller_info);
        }
    }

    public void setItemOnClickListener(onItemClickListener listener){
        this.mItemClickListener = listener;
    }
}
