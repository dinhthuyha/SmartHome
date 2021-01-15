package com.example.smarthome.Fragment;

import android.app.Dialog;
import android.app.FragmentManagerNonConfig;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smarthome.Adapter.HomeAdapter;
import com.example.smarthome.Adapter.ItemClickListener;
import com.example.smarthome.Adapter.ZoomAdapter;
import com.example.smarthome.Model.HomeTypeModel;
import com.example.smarthome.R;
import com.example.smarthome.Utils.FragmentUtils;
import com.example.smarthome.Utils.OnClickItem;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;

public class HomeFragment extends Fragment implements ItemClickListener {
    Unbinder unbinder;
    HomeAdapter homeAdapter;

    List<HomeTypeModel> homeTypeModelList = new ArrayList<>();
    @BindView(R.id.rv)
    RecyclerView rvMusicTypes;
    private static final String TAG = "HomeActivity";
    Context context;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeTypeModelList.add(new HomeTypeModel(R.raw.living_room, "living room"));
        homeTypeModelList.add(new HomeTypeModel(R.raw.bathroom, "Bath room"));
        homeTypeModelList.add(new HomeTypeModel(R.raw.kitchen, "Kitchen room"));
        homeTypeModelList.add(new HomeTypeModel(R.raw.bed_room, "Bed room"));
        homeTypeModelList.add(new HomeTypeModel(R.raw.jym_room, "Jym room"));
        homeTypeModelList.add(new HomeTypeModel(R.raw.studi_room, "Study room"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        homeAdapter = new HomeAdapter(homeTypeModelList, this, getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                getContext(),
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

        rvMusicTypes.setAdapter(homeAdapter);
        rvMusicTypes.setLayoutManager(gridLayoutManager);
        rvMusicTypes.setItemAnimator(new ScaleInAnimator());



        return view;
    }

    @Override
    public void onClick(View view, int position, boolean isLongClick) {
        Log.d(TAG, "onClick: " + position);
        EventBus.getDefault().postSticky(new OnClickItem(homeTypeModelList.get(position), position));
        FragmentUtils.openFragment(getFragmentManager(), R.id.ll_home_fm, new ZoomFragment());
    }


    @OnClick({R.id.add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add:
                openDialog();
                break;
        }
    }

    private void openDialog() {
        final int check = 0;
        Dialog dialog = new Dialog(getContext());
        dialog.setTitle("Language to translate");
        dialog.setContentView(R.layout.dialod_add);
        dialog.setCancelable(false);
        Button add = dialog.findViewById(R.id.buttonOk);
        EditText txt = dialog.findViewById(R.id.txt_result);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeTypeModelList.add(new HomeTypeModel(R.raw.bathroom, txt.getText().toString()));
                homeAdapter.notifyDataSetChanged();
                Log.d(TAG, "onClick1: "+ homeTypeModelList.size());
                dialog.cancel();
            }
        });
        dialog.show();
    }


}