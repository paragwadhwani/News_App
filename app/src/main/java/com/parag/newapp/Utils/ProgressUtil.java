package com.parag.newapp.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.parag.newapp.R;

public class ProgressUtil extends Dialog {

    public ProgressUtil(Context context) {
        super(context);

        WindowManager.LayoutParams wlmp = getWindow().getAttributes();

        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(wlmp);
        setTitle(null);
        setCancelable(false);
        setOnCancelListener(null);
        View view = LayoutInflater.from(context).inflate(
                R.layout.custom_progress_dialog_layout, null);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setContentView(view);

        Animation anim = AnimationUtils.loadAnimation(context, R.anim.scale_animation_logo);
        findViewById(R.id.cardview).startAnimation(anim);


    }
}

