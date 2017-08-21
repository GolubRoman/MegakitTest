package com.golub.golubroman.megakittest.Cars;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.golub.golubroman.megakittest.Cars.Database.DBQueries;
import com.golub.golubroman.megakittest.MainActivity;
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
                        carsAdapter.notifyItemRangeChanged(position, carModels.size());
                        updateDatabase();
                        return true;
                    case R.id.edit:
//                        edit item
                        editClicked(carModel);
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
        if(carModels.size() > 0){
            carsRecycler.setVisibility(View.VISIBLE);
            noInfoTextView.setVisibility(View.GONE);
        }else if(carModels.size() == 0){
            carsRecycler.setVisibility(View.GONE);
            noInfoTextView.setVisibility(View.VISIBLE);
        }
    }

    public void updateWithTheSearch(List<CarModel> carModels){
//        method for updating recycler view with the data, got from search
//        getting data from searchView
        this.carModels = carModels;
        carsAdapter.setListObjects(carModels);
//        checking if cars list is empty
        if(carModels.size() > 0){
            carsRecycler.setVisibility(View.VISIBLE);
            noInfoTextView.setVisibility(View.GONE);
        }else if(carModels.size() == 0){
            carsRecycler.setVisibility(View.GONE);
            noInfoTextView.setVisibility(View.VISIBLE);
        }
    }

    public void editClicked(final CarModel carModel){
//        method for getting alert dialog on screen with the interface for adding new items
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_dialog, null);

//        initializing inner elements from alert dialog
        final TextView title = ButterKnife.findById(dialogView, R.id.title);
        final EditText nameEdit = ButterKnife.findById(dialogView, R.id.add_name);
        final EditText ownerEdit = ButterKnife.findById(dialogView, R.id.add_owner);
        final ColorPickerView colorEdit = ButterKnife.findById(dialogView, R.id.add_color);

        title.setText("Edit Car");
        nameEdit.setText(carModel.getCarName());
        ownerEdit.setText(carModel.getCarOwner());

        dialogBuilder.setView(dialogView);
//        managing ok action for alert dialog
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String carName = nameEdit.getText().toString();
                String carOwner = ownerEdit.getText().toString();
                String carColor = String.format("#%06X", 0xFFFFFF & colorEdit.getSelectedColor());
                if(carName.trim().equals("") || carOwner.trim().equals("")){
                    Toast.makeText(getActivity(), "Fill all fields!", Toast.LENGTH_SHORT).show();
                }else{
                    carModel.setCarName(carName);
                    carModel.setCarOwner(carOwner);
                    carModel.setCarColor(carColor);
                    DBQueries.changeElementFromDatabase(getActivity(), carModel);
                    updateDatabase();
                }
            }
//            managing cancel action for alert dialog
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
//        showing the alert dialog
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

}
