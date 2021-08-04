package com.mindorks.example.paging3.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mindorks.example.paging.R
import com.mindorks.example.paging3.data.response.StackResponse
import kotlinx.android.synthetic.main.list_stack_item.view.*

class StackListAdapter : PagingDataAdapter<StackResponse.Item, StackListAdapter.ViewHolder>(DataDifferentiator) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.textViewName.text = "${getItem(position)?.owner?.displayName}(${getItem(position)?.owner?.userId.toString()})"
        holder.itemView.textViewEmail.text = getTags(getItem(position)?.tags)
        holder.itemView.reputation.text = getItem(position)?.owner?.reputation.toString()
        holder.itemView.imageView.load(getItem(position)?.owner?.profileImage.toString())
    }

    private fun getTags(list: List<String?>?): String {
        val stringBuilder = StringBuilder("")
        list?.forEachIndexed { index, s ->
            if (index < list.size - 1) {
                stringBuilder.append("#$s, ")
            } else {
                stringBuilder.append("#$s")
            }
        }
        return "$stringBuilder"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_stack_item, parent, false))
    }

    object DataDifferentiator : DiffUtil.ItemCallback<StackResponse.Item>() {

        override fun areItemsTheSame(oldItem: StackResponse.Item, newItem: StackResponse.Item): Boolean {
            return oldItem.owner?.userId == newItem.owner?.userId
        }

        override fun areContentsTheSame(oldItem: StackResponse.Item, newItem: StackResponse.Item): Boolean {
            return oldItem == newItem
        }
    }

}
