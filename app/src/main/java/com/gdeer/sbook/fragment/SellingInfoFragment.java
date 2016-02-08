package com.gdeer.sbook.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gdeer.sbook.R;
import com.gdeer.sbook.adapter.SellerRcvAdapter;
import com.gdeer.sbook.bean.SellerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gdeer on 2016/2/4.
 * email: gdeer00@163.com
 */
public class SellingInfoFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private List<SellerItem> mDataList;
    private SellerRcvAdapter mAdapter;
    private RecyclerView mRcvList;

    public SellingInfoFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SellingInfoFragment newInstance(int sectionNumber) {
        SellingInfoFragment fragment = new SellingInfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_selling_info, container, false);
        mRcvList = (RecyclerView) rootView.findViewById(R.id.rcv_seller);
        mDataList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRcvList.setLayoutManager(layoutManager);

        //临时数据，用于展示效果
        for(int i=0; i<3; i++){
            SellerItem temp = new SellerItem();
            temp.setUserName("卖家"+i);
            temp.setPrice(20.0 + i);
            temp.setArea("上海");
            mDataList.add(temp);
        }

        mAdapter = new SellerRcvAdapter(mDataList);
        mRcvList.setAdapter(mAdapter);

        return rootView;
    }
}