package com.golub.golubroman.megakittest.Cars;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.golub.golubroman.megakittest.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by roman on 21.08.17.
 */

public class CarsAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<CarModel> carModels;
    private CustomClickListener customClickListener;

    public CarsAdapter(Context context, List<CarModel> carModels, CustomClickListener customClickListener){
        this.context = context;
        this.carModels = carModels;
        this.customClickListener = customClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CarViewHolder carViewHolder;
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        carViewHolder = new CarViewHolder(view);
//        setting viewholder unrecyclable not to get strange behaviour of viewholder when scrolling
        carViewHolder.setIsRecyclable(false);
        return carViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final CarViewHolder carViewHolder = (CarViewHolder)holder;
//        changing of color of shape from car_color.xml
        carViewHolder.setImage(position);
        carViewHolder.carName.setText(carModels.get(position).getCarName());
        carViewHolder.carOwner.setText(carModels.get(position).getCarOwner());
//        using of interface methods of customClickListener interface to listen long clicks
        carViewHolder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                customClickListener.onLongClick(position, carViewHolder.view);
                return true;
            }
        });
//        using of interface methods of customClickListener interface to listen short clicks

        carViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customClickListener.onShortClick(position, carViewHolder.view);
            }
        });
    }

    public void setListObjects(List<CarModel> carModels){
//        updating of adapter with inner method setListObjects with objects from database
        if(this.carModels.size()>0)
            this.carModels.clear();
        this.carModels = carModels;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return carModels.size();
    }

    class CarViewHolder extends RecyclerView.ViewHolder{
//        Using of ViewHolder pattern to work with specific element of the recyclerview
//        Using Butterknife for binding data
        @BindView(R.id.color) ImageView color;
        @BindView(R.id.car_name) TextView carName;
        @BindView(R.id.car_owner) TextView carOwner;

        private View view;

        public CarViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            view = itemView;
        }

        public void setImage(int position){
//            method for setting Photo if it exists and Color if it doesn`t exist
            if(!carModels.get(position).getCarPhoto().equals("null")) {
//                if photo exists
//                using Glide library for rapid displaying of photo
                Glide.with(context)
                        .load(carModels.get(position).getCarPhoto())
                        .into(color);
            }else{
//                if photo doesn`t exists
                color.setImageDrawable(context.getResources().getDrawable(R.drawable.car_color));
//                changing of color of rectangle on recycler view item
                ((GradientDrawable) color.getDrawable()).setColor(
                        Color.parseColor(carModels.get(position).getCarColor()));
            }
        }
    }
}
