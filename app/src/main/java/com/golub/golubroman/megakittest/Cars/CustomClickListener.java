package com.golub.golubroman.megakittest.Cars;

import android.view.View;

/**
 * Created by roman on 21.08.17.
 */

public interface CustomClickListener {
    void onLongClick(int position, View view);
    void onShortClick(int position, View view);
}
