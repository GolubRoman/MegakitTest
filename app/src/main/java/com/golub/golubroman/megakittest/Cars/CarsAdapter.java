package com.golub.golubroman.megakittest.Cars;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
        carViewHolder.setIsRecyclable(false);
        return carViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final CarViewHolder carViewHolder = (CarViewHolder)holder;
        ((GradientDrawable)carViewHolder.color.getDrawable()).setColor(
                        Color.parseColor(carModels.get(position).getCarColor()));
        carViewHolder.carName.setText(carModels.get(position).getCarName());
        carViewHolder.carOwner.setText(carModels.get(position).getCarOwner());
        carViewHolder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                customClickListener.onLongClick(position, carViewHolder.view);
                return true;
            }
        });
    }

    public void setListObjects(List<CarModel> carModels){
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

        @BindView(R.id.color) ImageView color;
        @BindView(R.id.car_name) TextView carName;
        @BindView(R.id.car_owner) TextView carOwner;

        private View view;

        public CarViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            view = itemView;
        }
    }
}
