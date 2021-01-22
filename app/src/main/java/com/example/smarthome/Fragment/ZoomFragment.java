package com.example.smarthome.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Adapter.ItemClickListener;
import com.example.smarthome.Adapter.ZoomAdapter;
import com.example.smarthome.Model.DeviceModel;
import com.example.smarthome.Model.FirebaseModel;
import com.example.smarthome.Model.FutureAndCodeModel;
import com.example.smarthome.Model.HomeTypeModel;
import com.example.smarthome.Model.Model;
import com.example.smarthome.Model.ReadDeviceModel;
import com.example.smarthome.R;
import com.example.smarthome.Utils.DatabaseFirebase;
import com.example.smarthome.Utils.OnClickItem;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

import com.example.smarthome.Utils.FragmentUtils;

public class ZoomFragment extends Fragment implements ItemClickListener {
    Unbinder unbinder;
    FirebaseModel firebaseModel;
    String id;
    String nameDevice;

    public static FirebaseDatabase firebaseDatabase;
    private static final String TAG = "ZoomFragment";
    List<HomeTypeModel> typeModelListHome;
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_zoom, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        typeModelListHome = new ArrayList<>();

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
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        return view;
    }

    @Override
    public void onClick(View view, int position, boolean isLongClick) {
        Log.d(TAG, "onClick:device name " + nameDevice);
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d(TAG, "onLongClick: "+position);
                return false;
            }
        });
//        FragmentUtils.openFragment(getFragmentManager(), R.id.ll_home_fm, new DeviceFragment());

    }


    @Subscribe(sticky = true)
    public void onReceivedData(OnClickItem homeTypeModel) {

        List<Model> list = new ArrayList<>();
        HomeTypeModel model = homeTypeModel.homeTypeModel;
        Picasso.get().load(model.image).into(ivType);
        tvType.setText(model.nameRoom);
        id = homeTypeModel.id;
        Log.d(TAG, "onReceivedTopSong: " + model.nameRoom);
        Log.d(TAG, "onReceivedID" + homeTypeModel.id);
        getRoom(list, id);

    }

    public void getRoom(final List<Model> list, String id) {
        if( typeModelListHome.size()>0){
            typeModelListHome.clear();
        }
        List<ReadDeviceModel> futureAndCodeModelList = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference(id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "child:"+snapshot.getKey());
                for (DataSnapshot data : snapshot.getChildren()) {
                    Log.d(TAG, "child1:"+data.getKey());

                    typeModelListHome.add(new HomeTypeModel(R.raw.quat, data.getKey()));

                    for (DataSnapshot model : data.getChildren()) {
                        Log.d(TAG, "child2:"+model.getKey());
                        FirebaseModel firebaseModel= model.getValue(FirebaseModel.class);
                        Log.d(TAG, "code:"+ firebaseModel.code);
                        Log.d(TAG, "cmd: "+firebaseModel.cmd);

                    }
                }

                Log.d(TAG, "list device size:" + typeModelListHome.size());
                zoomAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @OnClick({R.id.back, R.id.fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                getActivity().onBackPressed();
                break;
            case R.id.fab:
                openDialog();
                break;
        }
    }

    private void openDialog() {
        final int check = 0;
        Dialog dialog = new Dialog(getContext());
        dialog.setTitle("Language to translate");
        dialog.setContentView(R.layout.add_device);
        EditText txt = dialog.findViewById(R.id.txt_result);


        Button oke = dialog.findViewById(R.id.buttonOk);
        dialog.setCancelable(false);
        oke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeModelListHome.add(new HomeTypeModel(R.raw.quat, txt.getText().toString()));
                zoomAdapter.notifyDataSetChanged();

                Log.d(TAG, "Id:" + id);
                firebaseModel = new FirebaseModel("0123456", "p");
                DatabaseFirebase.pushDataFirebase(firebaseModel, id, txt.getText().toString(), "a");

                nameDevice = txt.getText().toString();

                EventBus.getDefault().postSticky(new DeviceModel(nameDevice, id));

                dialog.cancel();

            }
        });

        dialog.show();
    }
}
