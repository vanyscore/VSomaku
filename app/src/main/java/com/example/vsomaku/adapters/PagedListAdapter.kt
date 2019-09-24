package com.example.vsomaku.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.vsomaku.R
import com.example.vsomaku.activities.PostActivity
import com.example.vsomaku.data.Post
import kotlinx.android.synthetic.main.rcv_item_post.view.*

class PagedListAdapter(private val context : Context) :
    PagedListAdapter<Post, com.example.vsomaku.adapters.PagedListAdapter.PostViewHolder>(PostDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(LayoutInflater.from(context).inflate(R.layout.rcv_item_post, parent, false))
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) holder.bind(item)
    }

    inner class PostViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        fun bind(post : Post) {
            itemView.tv_title.text = post.title
            itemView.tv_description.text = post.description

            itemView.setOnClickListener {
                val intent = Intent(context, PostActivity::class.java)
                intent.putExtra(PostActivity.POST_KEY, post)
                context.startActivity(intent)
            }
        }
    }
}