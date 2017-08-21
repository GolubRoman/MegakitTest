package com.golub.golubroman.megakittest;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.golub.golubroman.megakittest.Cars.CarModel;
import com.golub.golubroman.megakittest.Cars.CarsFragment;
import com.golub.golubroman.megakittest.Cars.Database.DBQueries;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.add_btn) FloatingActionButton addBtn;
    @BindView(R.id.search_view) SearchView searchView;

    @OnClick(R.id.add_btn) public void onAddClicked(){ addClicked(); }

    private Fragment carsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        switching of shadow from action bar
        getSupportActionBar().setElevation(0);

        carsFragment = CarsFragment.newInstance();
//        replacing CarsFragment on Activity in fragment container
        replaceCarsFragment(carsFragment);
    }

    private void replaceCarsFragment(Fragment fragment){
//        method for managing fragments, replacing it in fragment container
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_car_list, fragment).commit();
    }

    public void addClicked(){
//        method for getting alert dialog on screen with the interface for adding new items
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_dialog, null);

//        initializing inner elements from alert dialog

        final EditText nameEdit = ButterKnife.findById(dialogView, R.id.add_name);
        final EditText ownerEdit = ButterKnife.findById(dialogView, R.id.add_owner);
        final ColorPickerView colorEdit = ButterKnife.findById(dialogView, R.id.add_color);

        dialogBuilder.setView(dialogView);
//        managing ok action for alert dialog
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String carName = nameEdit.getText().toString();
                String carOwner = ownerEdit.getText().toString();
                String carColor = String.format("#%06X", 0xFFFFFF & colorEdit.getSelectedColor());
                if(carName.trim().equals("") || carOwner.trim().equals("")){
                    Toast.makeText(MainActivity.this, "Fill all fields!", Toast.LENGTH_SHORT).show();
                }else{
                    onOkBtnClicked(carName, carOwner, carColor);
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

    private void onOkBtnClicked(String carName, String carOwner, String carColor){
//        adding to database some new item
        CarModel carModel = new CarModel(carName, carOwner, carColor);
        DBQueries.addToDatabase(carModel, this);
//        updating and showing it on screen
        ((CarsFragment)carsFragment).updateDatabase();
    }

}