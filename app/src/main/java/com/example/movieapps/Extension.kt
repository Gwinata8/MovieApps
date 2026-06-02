package com.example.movieapps

import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


fun RecyclerView.setEndlessScrollListener(
    block: () -> Unit
) {
    val handler = Handler(Looper.getMainLooper())
    var runnable: Runnable? = null

    val scrollListener = object : RecyclerView.OnScrollListener() {
        var loading = true

        override fun onScrolled(
            recyclerView: RecyclerView,
            dx: Int,
            dy: Int/*,
            newState: Int*/
        ) {
            if (canScrollVertically(-1)) {
                with(layoutManager as LinearLayoutManager) {
                    val visibleItemCount = childCount
                    val totalItemCount = itemCount
                    val firstVisibleItem = findFirstVisibleItemPosition()

                    if (loading) {
                        if ((visibleItemCount + firstVisibleItem) >= (totalItemCount - 1)) {
                            runnable?.let {
                                handler.removeCallbacks(it)
                            }
                            runnable = Runnable {
                                loading = false
                                block()
                                loading = true
                            }
                            postDelayed(runnable, 500)
                        }
                    }
                }
            }
        }
    }

    addOnScrollListener(scrollListener)
}