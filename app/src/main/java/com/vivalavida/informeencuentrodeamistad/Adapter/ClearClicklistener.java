package com.vivalavida.informeencuentrodeamistad.Adapter;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.vivalavida.informeencuentrodeamistad.Model.userModel;

public interface ClearClicklistener {
    void onClearClick(userModel userModel, int adapterPosition, ConstraintLayout clear, String correo);
}
