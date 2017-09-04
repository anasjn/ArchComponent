package com.pfc.android.archcomponent.model;

import android.view.View;

/**
 * CustomDetailClickListener Listener interface for the onClick event.
 * <p>
 * RecyclerView doesn't come with an onItemClick interface, so we have
 * to implement one in the adapter
 *
 * @author      Ana San Juan
 * @version     1.0
 * @since       1.0
 */
public interface CustomDetailClickListener {
    void onItemClick(View v, int position);
}
