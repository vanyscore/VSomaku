package com.example.vsomaku.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vsomaku.R
import com.example.vsomaku.activities.PostActivity
import com.example.vsomaku.data.Post
import kotlinx.android.synthetic.main.rcv_item_post.view.*

class PostsAdapter(private val context : Context,
                   private val posts : List<Post>) : RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(LayoutInflater.from(context).inflate(R.layout.rcv_item_post, parent, false))
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bindInfo(posts[position])
    }


    inner class PostViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener {
                val post = posts[adapterPosition]
                val intent = Intent(context, PostActivity::class.java)
                intent.putExtra(PostActivity.POST_KEY, post)
                context.startActivity(intent)
            }
        }

        fun bindInfo(post : Post) {
            itemView.tv_title.text = post.title
            itemView.tv_description.text = post.description
        }
    }
}