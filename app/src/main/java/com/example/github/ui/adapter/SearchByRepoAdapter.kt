package com.example.github.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.github.R
import com.example.github.data.models.GetUserRepo
import com.example.github.data.models.Itemss
import com.example.github.data.models.SearchByRepositoryResponse
import com.example.github.databinding.ItemSearchBinding
import com.squareup.picasso.Picasso

class SearchByRepoAdapter: RecyclerView.Adapter<SearchByRepoAdapter.PersonViewHolder>(){

    inner class PersonViewHolder(private val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemss: Itemss){
            binding.username.text = itemss.name
            Picasso.get().load(itemss.owner.avatar_url).into(binding.repoImage)
            binding.repoName.text = itemss.name
            binding.repoTitle.text = itemss.description
            binding.language.text = itemss.language
            binding.starNum.text = itemss.stargazers_count.toString()
        }
    }
    var models = listOf<Itemss>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_search,parent,false)
        val binding = ItemSearchBinding.bind(v)
        return PersonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(models[position])
    }

    override fun getItemCount(): Int = models.size


}