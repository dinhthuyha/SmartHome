package com.example.smarthome.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Adapter.HomeAdapter;
import com.example.smarthome.Adapter.ItemClickListener;
import com.example.smarthome.Fragment.ZoomFragment;
import com.example.smarthome.Model.HomeTypeModel;
import com.example.smarthome.R;
import com.example.smarthome.Utils.FragmentUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class HomeActivity extends AppCompatActivity implements ItemClickListener {
    HomeAdapter homeAdapter;
    List<HomeTypeModel> homeTypeModelList = new ArrayList<>();
    @BindView(R.id.rv_music_types)
    RecyclerView rvMusicTypes;
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        homeAdapter = new HomeAdapter(homeTypeModelList, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                this,
                2,
                LinearLayoutManager.VERTICAL,
                false
        );
        //chia anh ra lam hai cot
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position % 3 == 0 ? 2 : 1;
            }
        });
        homeTypeModelList.add(new HomeTypeModel(R.drawable.genre_x2_7, "living room"));
        homeTypeModelList.add(new HomeTypeModel(R.drawable.genre_x2_7, "living room"));
        homeTypeModelList.add(new HomeTypeModel(R.drawable.genre_x2_7, "living room"));
        homeTypeModelList.add(new HomeTypeModel(R.drawable.genre_x2_7, "living room"));
        rvMusicTypes.setAdapter(homeAdapter);
        rvMusicTypes.setLayoutManager(gridLayoutManager);
        rvMusicTypes.setItemAnimator(new SlideInLeftAnimator());
    }

    @Override
    public void onClick(View view, int position, boolean isLongClick) {
        Log.d(TAG, "onClick: "+position);
        FragmentUtils.openFragment(getSupportFragmentManager(),R.id.ll_main, new ZoomFragment());
    }
}
