package com.golub.golubroman.megakittest;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.golub.golubroman.megakittest.Cars.CarModel;
import com.golub.golubroman.megakittest.Cars.CarsFragment;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by roman on 21.08.17.
 */

public class MainActivity extends AppCompatActivity implements MainMVP.VtPInterface{

    @BindView(R.id.add_btn) FloatingActionButton addBtn;
    @BindView(R.id.search_view) SearchView searchView;

    @OnClick(R.id.add_btn) public void onAddClicked(){ addClicked(); }

    private final int STORAGE_CODE = 999, STORAGE_ACTIVITY_CODE = 1000,
        PICK_IMAGE = 1001, PICK_ACTIVITY_IMAGE = 1002;

    private MainMVP.PtVInterface presenter;
    private ImageView dialogPhoto;
    private String carPhoto;
    private Fragment carsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = new MainPresenter(this);

//        switching of shadow from action bar
        getSupportActionBar().setElevation(0);

        carsFragment = CarsFragment.newInstance();
//        replacing CarsFragment on Activity in fragment container
        replaceCarsFragment(carsFragment);
        setSearchViewListeners();
        searchView.setIconifiedByDefault(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == STORAGE_CODE){
//            if permission requested from CarsFragment
            pickImage(PICK_IMAGE, grantResults);
        }else if(requestCode == STORAGE_ACTIVITY_CODE){
//            if permission requested from MainActivity
            pickImage(PICK_ACTIVITY_IMAGE, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE){
//            if image picked from CarsFragment
            if(data != null && data.getData() != null){
//            setting resources for photo binding
                ((CarsFragment)carsFragment).carModel.setCarPhoto(data.getData().toString());
                ((CarsFragment)carsFragment).dialogPhoto.setImageURI(data.getData());
            }else {
//             displaying message if photo uploading failed
                Toast.makeText(this, "Photo uploading failed", Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode == PICK_ACTIVITY_IMAGE){
//            setting resources for photo binding
            if(data != null && data.getData() != null){
                dialogPhoto.setImageURI(data.getData());
                carPhoto = data.getData().toString();
            }else {
//             displaying message if photo uploading failed
                Toast.makeText(this, "Photo uploading failed", Toast.LENGTH_SHORT).show();
            }
        }
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
        final Button photoEdit = ButterKnife.findById(dialogView, R.id.add_photo);
        final ImageView imageView = ButterKnife.findById(dialogView, R.id.photo);


        dialogPhoto = imageView;
        photoEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestStorage();
            }
        });

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
//        requesting permission for picking photo from gallery
        photoEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestStorage();
            }
        });
//        showing the alert dialog
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    private void onOkBtnClicked(String carName, String carOwner, String carColor){
//        adding to database some new item
        CarModel carModel = new CarModel(carName, carOwner, carColor);
        carModel.setCarPhoto(carPhoto);
        presenter.addToDatabase(carModel);
//        updating and showing it on screen
        ((CarsFragment)carsFragment).updateDatabase();
    }

    public void setSearchViewListeners(){
//        setting listener for search view to get search results
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                if user clicked button submit
                return false;
            }
            @Override
            public boolean onQueryTextChange(String query) {
//                if user change text in searchview he will get search results
//                which contain query phrase
                presenter.runSearch(query);
                return true;
            }
        });

    }

    private void requestStorage(){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_ACTIVITY_CODE);
    }

    private void pickImage(int requestCode, int[] grantResults){
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), requestCode);
        }else{
            Toast.makeText(this, "Enable permission to attach photo", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void displaySearchResults(List<CarModel> carModels) {
//      updating the list to display search results
        ((CarsFragment)carsFragment).updateWithTheSearch(carModels);
    }
}