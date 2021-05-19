package com.example.retrofitlab
import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.retrofitlab.databinding.ItemNewBinding
import com.squareup.picasso.Picasso


class ArticleAdapter (private val articles: List<Articles>): RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0:ViewGroup,p1:Int):ViewHolder{
        val v = LayoutInflater.from(p0.context)
        return ViewHolder(v.inflate(R.layout.item_new,p0,false))
    }
    override fun getItemCount():Int = articles.size

    override fun onBindViewHolder(holder:ViewHolder,position:Int){
        val item:Articles= articles[position]
        holder.bindItems(item)
    }
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        private val binding = ItemNewBinding.bind(itemView)
        @SuppressLint("SetTextI18n")
        fun bindItems(item:Articles){
            Picasso.get().load(item.urlToImage).into(binding.imgArticle)
            binding.txtDescription.text = item.description
        }
    }
}