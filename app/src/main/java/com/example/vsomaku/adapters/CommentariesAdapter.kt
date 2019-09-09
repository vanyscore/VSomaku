package com.example.vsomaku.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vsomaku.R
import com.example.vsomaku.data.Comment

class CommentariesAdapter(private val context : Context, private val comments : List<Comment>)
    : RecyclerView.Adapter<CommentariesAdapter.CommentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(LayoutInflater.from(context).inflate(R.layout.rcv_item_comment, parent, false))
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bindInfo(comments[position])
    }


    inner class CommentViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val tvName = view.findViewById<TextView>(R.id.tv_name)
        private val tvEmail = view.findViewById<TextView>(R.id.tv_email)
        private val tvText = view.findViewById<TextView>(R.id.tv_text)

        fun bindInfo(comment : Comment) {
            tvName.text = comment.name
            tvEmail.text = comment.email
            tvText.text = comment.text
        }
    }
}