package com.mindorks.example.paging3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.mindorks.example.paging3.data.APIService
import com.mindorks.example.paging3.data.APIService.Companion.PAGE_SIZE
import com.mindorks.example.paging3.data.datasource.StackDataSource

class MainViewModel(private val apiService: APIService) : ViewModel() {

    val stackListData = Pager(PagingConfig(pageSize = PAGE_SIZE)) {
        StackDataSource(apiService)
    }.flow.cachedIn(viewModelScope)

}