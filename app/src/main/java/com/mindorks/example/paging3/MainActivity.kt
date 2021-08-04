package com.mindorks.example.paging3

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mindorks.example.paging.databinding.ActivityMainBinding
import com.mindorks.example.paging3.adapter.MovieLoadStateAdapter
import com.mindorks.example.paging3.adapter.StackListAdapter
import com.mindorks.example.paging3.data.APIService
import com.mindorks.example.paging3.events.StackEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    private lateinit var stackListAdapter: StackListAdapter

    private val mTag = "MainActivity"

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setContentView(R.layout.activity_main)
        setupViewModel()

        //stack list
        setupStackList()
        setupStackView()

        //main list
        //setupList()
        //setupView()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, MainViewModelFactory(APIService.getStackApiService()))[MainViewModel::class.java]
    }

    private fun setupStackList() {
        stackListAdapter = StackListAdapter()
        stackListAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {

            override fun onChanged() {
                super.onChanged()
                Log.d(mTag, "registerAdapterDataObserver : onChange")
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                super.onItemRangeChanged(positionStart, itemCount)
                Log.d(mTag, "registerAdapterDataObserver : onItemRangeChanged")
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
                super.onItemRangeChanged(positionStart, itemCount, payload)
                Log.d(mTag, "registerAdapterDataObserver : onItemRangeChanged2")
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                Log.d(mTag, "registerAdapterDataObserver : onItemRangeInserted | positionStart = $positionStart | itemCount = $itemCount ")

                if (positionStart > 0) {
                    //hide progressbar here, if it is greater than 0
                    binding.progressBar.visibility = View.GONE
                }

            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                Log.d(mTag, "registerAdapterDataObserver : onItemRangeRemoved")
            }

            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount)
                Log.d(mTag, "registerAdapterDataObserver : onItemRangeMoved")
            }

            override fun onStateRestorationPolicyChanged() {
                super.onStateRestorationPolicyChanged()
                Log.d(mTag, "registerAdapterDataObserver : onStateRestorationPolicyChanged")
            }
        })
        binding.recyclerView.apply {
            val decoration = DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            layoutManager = LinearLayoutManager(context)
            //setHasFixedSize(true)
            adapter = stackListAdapter.withLoadStateFooter(
                footer = MovieLoadStateAdapter {
                    binding.progressBar.visibility = View.GONE
                    stackListAdapter.retry()
                }
            )
        }
    }

    private fun setupStackView() {
        lifecycleScope.launch {
            viewModel.stackListData.collect {
                stackListAdapter.submitData(it)

                /*mainListAdapter.loadStateFlow.collect { loadState ->
                    when (loadState.append) {
                        is LoadState.NotLoading -> {
                            Log.d(mTag, "State = NotLoading")
                        }
                        is LoadState.Loading -> {
                            Log.d(mTag, "State = Loading")
                        }
                        is LoadState.Error -> {
                            Log.d(mTag, "State = Error")
                        }
                        else -> {
                            Log.d(mTag, "State = else")
                        }
                    }
                }*/


            }
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun stackoverflowStatus(stackEvent: StackEvent) {
        binding.progressBar.visibility = View.GONE
        binding.tvError.visibility = View.VISIBLE
        binding.tvError.text = stackEvent.message
    }


}