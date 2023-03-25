package com.example.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.github.R
import com.example.github.databinding.ItemRepoBinding
import androidx.recyclerview.widget.DiffUtil
import com.example.github.data.models.GetUserRepo
import com.squareup.picasso.Picasso

class RepoAdapter:
    androidx.recyclerview.widget.ListAdapter<GetUserRepo, RepoAdapter.PersonViewHolder>(diffUtilCallBack){

    inner class PersonViewHolder(private val binding: ItemRepoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(){
            val repo = getItem(adapterPosition)
            binding.username.text = repo.owner.login
            Picasso.get().load("${repo.owner.avatar_url}").into(binding.repoImage)
            binding.repoName.text = repo.name
            binding.repoTitle.text = repo.description
            binding.language.text = repo.language
            binding.starNum.text = repo.stargazers_count.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        return PersonViewHolder(
            ItemRepoBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_repository,parent,false
                )
            )
        )
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind()
    }

    private object diffUtilCallBack: DiffUtil.ItemCallback<GetUserRepo>() {
        override fun areItemsTheSame(oldItem: GetUserRepo, newItem: GetUserRepo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GetUserRepo, newItem: GetUserRepo): Boolean {
            return oldItem == newItem
        }
    }
}