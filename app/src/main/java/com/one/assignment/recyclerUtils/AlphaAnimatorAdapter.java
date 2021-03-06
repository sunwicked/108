package com.one.assignment.recyclerUtils;

import android.animation.Animator;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Sunny on 21-05-2016.
 */
public class AlphaAnimatorAdapter<T extends RecyclerView.ViewHolder> extends AnimatorAdapter<T> {

    public AlphaAnimatorAdapter(RecyclerView.Adapter<T> adapter, RecyclerView recyclerView) {
        super(adapter, recyclerView);
    }

    @NonNull
    @Override
    public Animator[] getAnimators(@NonNull View view) {
        return new Animator[0];
    }
}