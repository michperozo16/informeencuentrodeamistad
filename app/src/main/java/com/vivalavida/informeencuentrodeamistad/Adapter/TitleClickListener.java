package com.vivalavida.informeencuentrodeamistad.Adapter;

import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.vivalavida.informeencuentrodeamistad.Model.userModel;

public interface TitleClickListener {
    void onTitleClick(userModel userModel, int adapterPosition, ConstraintLayout rootTitle, TextView title);
}
