package com.example.news360.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news360.data.models.Article
import com.example.news360.databinding.ItemArticlePreviewBinding

class NewsRecycleAdapter: RecyclerView.Adapter<NewsRecycleAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(val binding:ItemArticlePreviewBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder = ArticleViewHolder (
        ItemArticlePreviewBinding.inflate(LayoutInflater.from(parent.context),parent, false)
    )

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.binding.article = article
        holder.binding.root.apply {
            article.urlToImage?.let {
                Glide.with(this).load(it).into(holder.binding.ivArticleImage)
            }
            setOnClickListener {
                onItemClickListener?.let {
                    Log.d("TAG", "onBindViewHolder: ${article.toString()}")
                    it(article)
                }
            }
        }
    }


    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this,differCallback)


    private var onItemClickListener:((Article)->Unit)?=null
    fun setOnItemClickListener(listener:(Article)->Unit){
        onItemClickListener = listener
    }
}