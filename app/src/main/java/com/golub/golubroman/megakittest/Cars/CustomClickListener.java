package com.golub.golubroman.megakittest.Cars;

import android.view.View;

/**
 * Created by roman on 21.08.17.
 */

public interface CustomClickListener {
//    interface for clicking on recycler view items
//    long click on item
    void onLongClick(int position, View view);
//    short click on item
    void onShortClick(int position, View view);
}
