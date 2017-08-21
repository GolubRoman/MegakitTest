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
//        setting up the list of cars models from database
        carModels = DBQueries.getTable(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());
//        initializing of adapter and its customClickListener
        carsAdapter = new CarsAdapter(getActivity(), carModels, new CustomClickListener() {
            @Override
            public void onLongClick(int position, View view) {
                showPopupMenu(view, carModels.get(position), position);
            }

            @Override
            public void onShortClick(int position, View view) {
                showPopupMenu(view, carModels.get(position), position);
            }
        });
        carsRecycler.setAdapter(carsAdapter);
        carsRecycler.setLayoutManager(linearLayoutManager);
//        checking if cars list empty
        if(carModels.size() > 0) {
            carsRecycler.setVisibility(View.VISIBLE);
            noInfoTextView.setVisibility(View.GONE);
        }
//        adding scroll listener to recycler view for button add animation
        carsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int begPog = addBtn.getScrollY();
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING){
//                    if recyclerview is dragged
                    addBtn.animate().translationYBy(500);
                }else{
//                    if recyclerview is stopped
                    addBtn.animate().cancel();
                    addBtn.animate().translationY(begPog);
                }
            }
        });
    }

    private void showPopupMenu(View view, final CarModel carModel, final int position){
//        method for getting popup menu when clicking on recyclerview item
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.inflate(R.menu.popup);
//        setting actions on different choosing cases
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
//                        delete item
                        DBQueries.deleteElementFromDatabase(getActivity(), carModel);
                        carsAdapter.notifyItemChanged(position);
                        updateDatabase();
                        return true;
                    case R.id.edit:
//                        edit item

                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }

    public void updateDatabase(){
//        method for updating database
//        getting information from database in cars list
        carModels = DBQueries.getTable(getActivity());
        carsAdapter.setListObjects(carModels);
//        checking if cars list is empty
        if(carModels.size() >= 0){
            carsRecycler.setVisibility(View.VISIBLE);
            noInfoTextView.setVisibility(View.GONE);
        }
    }


}
