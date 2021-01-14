package com.example.smarthome.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Adapter.ItemClickListener;
import com.example.smarthome.Adapter.ZoomAdapter;
import com.example.smarthome.Model.HomeTypeModel;
import com.example.smarthome.R;
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

public class ZoomFragment extends Fragment implements ItemClickListener {
    Unbinder unbinder;
    private static final String TAG = "ZoomFragment";
    List<HomeTypeModel> typeModelListHome = new ArrayList<>();
    @BindView(R.id.iv_type)
    ImageView ivType;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.back)
    ImageView back;
    ZoomAdapter zoomAdapter;

    public ZoomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        typeModelListHome.add(new HomeTypeModel(R.raw.quat, "fan"));
        typeModelListHome.add(new HomeTypeModel(R.raw.tivi, "televition"));
        typeModelListHome.add(new HomeTypeModel(R.raw.dieu_hoa, "air conditioning"));
        typeModelListHome.add(new HomeTypeModel(R.raw.nl, "heater"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_zoom, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        //ham lay vi tri cua text trong
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                Log.d(TAG, "onOffsetChanged: " + i);
                if (i == 0) {

                    toolbar.setBackground(getResources().getDrawable(
                            R.drawable.custom_gradien));
                } else {
                    toolbar.setBackground(null);
                }
            }
        });
        rv.setItemAnimator(new FadeInLeftAnimator());
        zoomAdapter = new ZoomAdapter(typeModelListHome, this);
        rv.setAdapter(zoomAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                getContext(),
                2,
                LinearLayoutManager.VERTICAL,
                false
        );
        rv.setLayoutManager(gridLayoutManager);
        return view;
    }

    @Override
    public void onClick(View view, int position, boolean isLongClick) {
        Log.d(TAG, "onClick: " + position);
    }



    @Subscribe(sticky = true)
    public void onReceivedTopSong(OnClickItem homeTypeModel) {
        HomeTypeModel model = homeTypeModel.homeTypeModel;
        Picasso.get().load(model.image).into(ivType);
        tvType.setText(model.nameRoom);
        Log.d(TAG, "onReceivedTopSong: " + model.nameRoom);
    }

//    @OnClick({R.id.back, R.id.fab})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.back:
//                getActivity().onBackPressed();
//                break;
//            case R.id.fab:
//                openDialog();
//                break;
//        }
//    }
//
//    private void openDialog() {
//        final int check = 0;
//        Dialog dialog = new Dialog(getContext());
//        dialog.setTitle("Language to translate");
//        dialog.setContentView(R.layout.dialod_add);
//        // dialog.setCancelable(false);
//        dialog.show();
//    }
}
