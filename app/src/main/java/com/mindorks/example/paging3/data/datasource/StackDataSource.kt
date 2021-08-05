package com.mindorks.example.paging3.data.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mindorks.example.paging3.data.APIService
import com.mindorks.example.paging3.data.APIService.Companion.ORDER_TYPE
import com.mindorks.example.paging3.data.APIService.Companion.PAGE_SIZE
import com.mindorks.example.paging3.data.APIService.Companion.SORT_TYPE
import com.mindorks.example.paging3.data.response.StackResponse
import com.mindorks.example.paging3.events.StackEvent
import org.greenrobot.eventbus.EventBus

class StackDataSource(private val apiService: APIService) : PagingSource<Int, StackResponse.Item>() {

    private val mTag = "StackDataSource"

    private val responseData = arrayListOf<StackResponse.Item>()
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StackResponse.Item> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = apiService.getStackListData(currentLoadingPageKey, PAGE_SIZE, ORDER_TYPE, SORT_TYPE)
            val data = response.body()?.items ?: emptyList()

            responseData.addAll(data)

            Log.d(mTag, "try : responseData = ${responseData.size}")
            Log.d(mTag, "try : data = ${data.size}")

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1
            val nextKey = if (data.isNotEmpty()) currentLoadingPageKey + 1 else null

            return LoadResult.Page(data = responseData, prevKey = prevKey, nextKey = nextKey)

        } catch (e: Throwable) {
            Log.d(mTag, "catch : responseData   = ${responseData.size}")
            Log.d(mTag, "catch : message        = ${e.message}")

            /**
             * if [responseData] size is 0 then do eventBus else no need to handle scenario
             */
            if (responseData.isEmpty()) {
                EventBus.getDefault().post(StackEvent(e.message))
            }
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, StackResponse.Item>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}