package com.asdf1st.mydemo.UI.Collapsed;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asdf1st.mydemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {
    View contentView;//当前使用的View
    RecyclerView recyclerView;
    List<ListData> dataList;
    ListDataAdapter adapter;
    public String text="";

    public ListFragment(){

    }

    public static ListFragment newInstance(String str){
         ListFragment listFragment=new ListFragment();
         listFragment.setText(str);
         return listFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (contentView ==null){
            contentView =inflater.inflate(R.layout.fragment_list, container, false);
            initView(contentView);
        }else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，
            // 要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent= (ViewGroup) contentView.getParent();
            if (parent!=null){
                parent.removeView(contentView);
            }
        }


        return contentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
         dataList=new ArrayList<>();
         for (int i=0;i<100;i++){
             dataList.add(new ListData(text+i));
         }
         adapter=new ListDataAdapter(getContext(),dataList);
         recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
         recyclerView.setAdapter(adapter);
    }

    private void initView(View view){
        recyclerView=view.findViewById(R.id.reclclerView);

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
