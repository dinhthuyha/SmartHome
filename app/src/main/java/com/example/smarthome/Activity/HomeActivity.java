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
import com.example.smarthome.Utils.OnClickItem;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;
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
        homeTypeModelList.add(new HomeTypeModel(R.raw.living_room, "living room"));
        homeTypeModelList.add(new HomeTypeModel(R.raw.bathroom, "Bath room"));
        homeTypeModelList.add(new HomeTypeModel(R.raw.kitchen, "Kitchen room"));
        homeTypeModelList.add(new HomeTypeModel(R.raw.bed_room, "Bed room"));
        homeTypeModelList.add(new HomeTypeModel(R.raw.jym_room, "Jym room"));
        homeTypeModelList.add(new HomeTypeModel(R.raw.studi_room, "Study room"));
        rvMusicTypes.setAdapter(homeAdapter);
        rvMusicTypes.setLayoutManager(gridLayoutManager);
        rvMusicTypes.setItemAnimator(new ScaleInAnimator());

    }

    @Override
    public void onClick(View view, int position, boolean isLongClick) {
        Log.d(TAG, "onClick: "+position);
        EventBus.getDefault().postSticky(new OnClickItem(homeTypeModelList.get(position), position));
        FragmentUtils.openFragment(getSupportFragmentManager(),R.id.ll_main, new ZoomFragment());
    }


}
