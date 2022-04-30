package com.mohith.todoapp.gesture

import de.dlyt.yanndroid.oneui.sesl.recyclerview.ItemTouchHelper
import de.dlyt.yanndroid.oneui.view.RecyclerView






abstract class SwipeGesture : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
    override fun onMove(
        p0: RecyclerView,
        p1: RecyclerView.ViewHolder,
        p2: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {

    }

//    override fun onChildDraw(
//        c: Canvas,
//        recyclerView: RecyclerView,
//        viewHolder: RecyclerView.ViewHolder,
//        dX: Float,
//        dY: Float,
//        actionState: Int,
//        isCurrentlyActive: Boolean
//    ) {
//        RecyclerViewSwipeDecorator.Builder(
//            c,
//            recyclerView,
//            viewHolder,
//            dX,
//            dY,
//            actionState,
//            isCurrentlyActive
//        )
//            .addBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.my_background))
//            .addActionIcon(R.drawable.my_icon)
//            .create()
//            .decorate()
//        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//    }
}