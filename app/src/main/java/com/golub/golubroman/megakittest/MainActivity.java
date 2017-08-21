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
        getSupportActionBar().setElevation(0);

        carsFragment = CarsFragment.newInstance();
        replaceCarsFragment();
    }

    private void replaceCarsFragment(){
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_car_list, carsFragment).commit();
    }

    public void addClicked(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_dialog, null);

        final EditText nameEdit = ButterKnife.findById(dialogView, R.id.add_name);
        final EditText ownerEdit = ButterKnife.findById(dialogView, R.id.add_owner);
        final ColorPickerView colorEdit = ButterKnife.findById(dialogView, R.id.add_color);

        dialogBuilder.setView(dialogView);
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
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    private void onOkBtnClicked(String carName, String carOwner, String carColor){
        CarModel carModel = new CarModel(carName, carOwner, carColor);
        DBQueries.addToDatabase(carModel, this);
        ((CarsFragment)carsFragment).updateDatabase();
    }

}