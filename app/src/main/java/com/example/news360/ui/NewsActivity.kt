package com.example.news360.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment

import androidx.navigation.ui.setupWithNavController
import com.example.news360.R
import com.example.news360.databinding.ActivityNewsBinding
import com.example.news360.db.ArticleDatabase
import com.example.news360.repository.NewsRepository


class NewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsBinding
    private lateinit var navController: NavController
    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        //setContentView(R.layout.activity_news)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Find NavController
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.news_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        //viewModel
        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(application,newsRepository)
        viewModel = ViewModelProvider (this,viewModelProviderFactory).get(NewsViewModel::class.java)

        //binding.bottomNavigationView.setupWithNavController(binding.newsNavHostFragment.findNavController())
        binding.bottomNavigationView.setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}