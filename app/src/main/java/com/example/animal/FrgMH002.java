package com.example.animal;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import java.util.ArrayList;

public class FrgMH002 extends Fragment {
    private Context mContext;
    private int recyclerAdapterPosition;
    private ArrayList<AnimalType> listAnimal;

    public FrgMH002(int recyclerAdapterPosition, ArrayList<AnimalType> listRecycV) {
        this.recyclerAdapterPosition = recyclerAdapterPosition;
        this.listAnimal = listRecycV;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.viewpager_content, container, false);
        init(v);
        return v;
    }


    private void init(View v) {
        ViewPager pagerContentView = v.findViewById(R.id.view_pager_content);
        MyViewPagerAdapter pagerAdapter = new MyViewPagerAdapter(mContext, listAnimal);
        ImageView upButton = v.findViewById(R.id.iv_backArrow);


        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).showFrg1();
            }
        });


        pagerContentView.setAdapter(pagerAdapter);
        pagerContentView.setCurrentItem(recyclerAdapterPosition);

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }
}
