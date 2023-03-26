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
import com.example.github.data.models.SearchByUserName.Items
import com.example.github.databinding.ItemSearchBinding
import com.example.github.databinding.ItemSearchbynameBinding
import com.squareup.picasso.Picasso

class SearchByUserAdapter: RecyclerView.Adapter<SearchByUserAdapter.PersonViewHolder>(){

    inner class PersonViewHolder(private val binding: ItemSearchbynameBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemss: Items){
            binding.username.text = itemss.login
            Picasso.get().load(itemss.avatar_url).into(binding.userImage)
        }
    }
    var models = listOf<Items>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_searchbyname,parent,false)
        val binding = ItemSearchbynameBinding.bind(v)
        return PersonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(models[position])
    }

    override fun getItemCount(): Int = models.size


}