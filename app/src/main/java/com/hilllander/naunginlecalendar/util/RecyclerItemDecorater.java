package com.hilllander.naunginlecalendar.util;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by khunzohn on 11/10/15.
 */
public class RecyclerItemDecorater extends RecyclerView.ItemDecoration {

    private final int itemOffset;

    public RecyclerItemDecorater(int itemOffset) {
        this.itemOffset = itemOffset;
    }

    public RecyclerItemDecorater(@NonNull Context context, @DimenRes int itemOffsetId) {
        this(context.getResources().getDimensionPixelOffset(itemOffsetId));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(itemOffset, itemOffset, itemOffset, itemOffset);
    }
}
