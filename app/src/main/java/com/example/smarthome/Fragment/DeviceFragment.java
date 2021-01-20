package com.example.smarthome.Fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.smarthome.Adapter.DeviceAdapter;
import com.example.smarthome.Adapter.ItemClickListener;
import com.example.smarthome.Model.DeviceModel;
import com.example.smarthome.Model.FirebaseModel;
import com.example.smarthome.Model.HomeTypeModel;
import com.example.smarthome.R;
import com.example.smarthome.Utils.DatabaseFirebase;
import com.example.smarthome.Utils.OnClickItem;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeviceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeviceFragment extends Fragment implements ItemClickListener {
    Unbinder unbinder;
    String id;
    FirebaseModel firebaseModel;
    String nameDevice;
    @BindView(R.id.add)
    FloatingActionButton add;
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    List<HomeTypeModel> deviceList;
    DeviceAdapter deviceAdapter;
    private static final String TAG = "DeviceFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DeviceFragment() {
        // Required empty public constructor
    }

//    public static DeviceFragment newInstance(String param1, String param2) {
//        DeviceFragment fragment = new DeviceFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_divice, container, false);
        unbinder = ButterKnife.bind(this, view);

        deviceList = new ArrayList<>();

        deviceAdapter = new DeviceAdapter(deviceList, this);
        recyclerView.setItemAnimator(new FadeInLeftAnimator());
        recyclerView.setAdapter(deviceAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        return view;
    }


    @Override
    public void onClick(View view, int position, boolean isLongClick) {

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
        dialog.setContentView(R.layout.add_device);
        EditText txt = dialog.findViewById(R.id.txt_result);


        Button oke = dialog.findViewById(R.id.buttonOk);
        dialog.setCancelable(false);
        oke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deviceList.add(new HomeTypeModel(R.raw.quat, txt.getText().toString()));
                deviceAdapter.notifyDataSetChanged();

                //lay dl tu broker ve up len firebase
                firebaseModel = new FirebaseModel("0123456", "p");
                DatabaseFirebase.pushDataFirebase(firebaseModel, id, nameDevice, txt.getText().toString());

                nameDevice = txt.getText().toString();

//                EventBus.getDefault().postSticky(new DeviceModel(nameDevice, id));

                dialog.cancel();

            }
        });

        dialog.show();
    }

    @Subscribe(sticky = true)
    public void onReceivedTopSong(DeviceModel deviceModel) {
//        HomeTypeModel model = home.getHomeTypeModel();
//        Picasso.get().load(model.image).into(ivType);
//        tvType.setText(model.nameRoom);
//        id = home.getId().toString();
        nameDevice = deviceModel.getNameDevice();
        id = deviceModel.getId();
        Log.d(TAG, "onReceivedTopSong: " + deviceModel.getId());
        Log.d(TAG, "onReceivedTopSong: " + deviceModel.getNameDevice());

    }
}