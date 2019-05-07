package com.arctouch.codechallenge.view

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

abstract class ScrollListener : RecyclerView.OnScrollListener() {

    private var previousTotal = 0
    private var isLoading = false

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        var visibleItemCount: Int
        var totalItemCount: Int
        var firstVisibleItem: Int

        with(recyclerView!!) {
            visibleItemCount = childCount
            totalItemCount = layoutManager?.itemCount!!
            firstVisibleItem = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        }

        if (isLoading && totalItemCount > previousTotal) {
            isLoading = false
            previousTotal = totalItemCount
        } else if (!isLoading && totalItemCount - visibleItemCount <= firstVisibleItem) {
            isLoading = true
            loadMore()
        }
    }

    abstract fun loadMore()
}