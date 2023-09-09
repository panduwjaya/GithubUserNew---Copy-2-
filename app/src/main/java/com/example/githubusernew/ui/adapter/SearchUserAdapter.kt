package com.example.githubusernew.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.githubusernew.data.remote.model.ItemsItem
import com.example.githubusernew.databinding.ItemRowUserBinding
import com.example.githubusernew.ui.notediffcallback.NoteDiffCallbackList

class SearchUserAdapter(private val list: ArrayList<ItemsItem>): RecyclerView.Adapter<SearchUserAdapter.UserViewHolder>(){

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    fun setListUser(listNotes: ArrayList<ItemsItem>) {
        val diffCallback = NoteDiffCallbackList(this.list, listNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.list.clear()
        this.list.addAll(listNotes)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class UserViewHolder(val binding: ItemRowUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindData(user: ItemsItem) {

            binding.root.setOnClickListener{
                onItemClickCallback?.onItemClicked(user)
            }

            Glide.with(itemView)
                .load(user.avatarUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions.circleCropTransform())
                .into(binding.ivItemAvatarUrl)

            binding.tvItemUsername.text = user.login
            binding.tvItemHtmlUrl.text = user.htmlUrl
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchUserAdapter.UserViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchUserAdapter.UserViewHolder, position: Int) {
        holder.bindData(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback{
        fun onItemClicked(data: ItemsItem)
    }

}