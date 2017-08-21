package com.golub.golubroman.megakittest.Cars;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.golub.golubroman.megakittest.Cars.Database.DBQueries;
import com.golub.golubroman.megakittest.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by roman on 21.08.17.
 */

public class CarsFragment extends Fragment {

    @BindView(R.id.recycler) RecyclerView carsRecycler;
    @BindView(R.id.no_info) TextView noInfoTextView;

    private CarsAdapter carsAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<CarModel> carModels;
    private FloatingActionButton addBtn;

    public static CarsFragment newInstance() {
        
        Bundle args = new Bundle();
        
        CarsFragment fragment = new CarsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_list, container, false);
        ButterKnife.bind(this, view);
        addBtn = ButterKnife.findById(getActivity(), R.id.add_btn);

        carModels = new ArrayList<>();
        setCarsRecycler();
        return view;
    }

    private void setCarsRecycler(){
        carModels = DBQueries.getTable(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());
        carsAdapter = new CarsAdapter(getActivity(), carModels, new CustomClickListener() {
            @Override
            public void onLongClick(int position, View view) {

            }

            @Override
            public void onShortClick(int position, View view) {

            }
        });
        carsRecycler.setAdapter(carsAdapter);
        carsRecycler.setLayoutManager(linearLayoutManager);
        if(carModels.size() > 0) {
            carsRecycler.setVisibility(View.VISIBLE);
            noInfoTextView.setVisibility(View.GONE);
        }
        carsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int begPog = addBtn.getScrollY();
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING){
                    addBtn.animate().translationYBy(500);
                }else{
                    addBtn.animate().cancel();
                    addBtn.animate().translationY(begPog);
                }
            }
        });
    }

    private void showPopupMenu(){
        PopupMenu popupMenu = new PopupMenu(getActivity(), v);
        popupMenu.inflate(R.menu.context_menu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
                        firebaseDatabase.getReference().child("users").child(email).child("cart").child(idPath).
                                removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    moreElements.remove(position);
                                    adapter.notifyItemRemoved(position);
                                    adapter.notifyItemRangeChanged(position, moreElements.size());
                                    orderButton.setVisibility(View.GONE);
                                } else {
                                    Toast.makeText(getActivity(), "Cannot delete this element", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }

    public void updateDatabase(){
        carModels = DBQueries.getTable(getActivity());
        carsAdapter.setListObjects(carModels);
        if(carModels.size() >= 0){
            carsRecycler.setVisibility(View.VISIBLE);
            noInfoTextView.setVisibility(View.GONE);
        }
    }


}
