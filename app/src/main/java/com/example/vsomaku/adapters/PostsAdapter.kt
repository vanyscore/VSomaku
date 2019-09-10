package com.example.vsomaku.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vsomaku.R
import com.example.vsomaku.activities.PostActivity
import com.example.vsomaku.data.Post

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
        private val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        private val tvDescription = view.findViewById<TextView>(R.id.tv_description)

        init {
            view.setOnClickListener {
                val post = posts[adapterPosition]
                val intent = Intent(context, PostActivity::class.java)
                intent.putExtra(PostActivity.POST_KEY, post)
                context.startActivity(intent)
            }
        }

        fun bindInfo(post : Post) {
            tvTitle.text = post.title
            tvDescription.text = post.description
        }
    }
}