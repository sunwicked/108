package com.one.assignment.recyclerUtils;

/**
 * Created by sunny on 24-05-2016.
 * src : https://gist.github.com/iPaulPro/5d43325ac7ae579760a9
 * @author Paul Burke (ipaulpro)
 */
public interface ItemTouchHelperAdapter {

    /**
     * Called when an item has been dragged far enough to trigger a move. This is called every time
     * an item is shifted, and not at the end of a "drop" event.
     */
    boolean onItemMove(int fromPosition, int toPosition);


    /**
     * Called when an item has been dismissed by a swipe.
     */
    void onItemDismiss(int position);
}