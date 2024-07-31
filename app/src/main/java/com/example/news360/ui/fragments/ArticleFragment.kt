package com.example.news360.ui.fragments

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import android.webkit.ValueCallback
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.news360.R
import com.example.news360.databinding.FragmentArticleBinding
import com.example.news360.ui.NewsActivity
import com.example.news360.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar


class ArticleFragment : Fragment(R.layout.fragment_article) {
    val TAG:String = "Article-Fragment"
    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: NewsViewModel
    private val args: ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }


    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        val article = args.article
        Log.d(TAG, "onViewCreated: $article")

        //for cookie
        setCookeSettings()
        //web view
        binding.webView.apply {
            webViewClient =

                object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    // Show a progress bar or loading indicator
                    binding.progressBarArtcleFragment.visibility = View.VISIBLE
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    // Hide the progress bar
                    binding.progressBarArtcleFragment.visibility = View.GONE
                }

                @Deprecated("Deprecated in Java", ReplaceWith(
                    "Toast.makeText(context, \"Error: \$description\", Toast.LENGTH_SHORT).show()",
                    "android.widget.Toast",
                    "android.widget.Toast"
                )
                )
                override fun onReceivedError(
                    view: WebView?,
                    errorCode: Int,
                    description: String?,
                    failingUrl: String?
                ) {
                    // Handle errors, e.g., display an error message
                    Toast.makeText(context, "Error: $description", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            Log.d(TAG, "inside web clint: ${article.url}")
            loadUrl(article.url)
            settings.javaScriptEnabled = true

            // if you want to enable zoom feature
            settings.setSupportZoom(true)
            clearHistory()

        } // end web view setup

        //floating action button
        //on click save into database
        binding.fab.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(binding.root,"Article saved successfully",Snackbar.LENGTH_SHORT).show()
        }
    }



    private fun setCookeSettings(){
        val cookieManager: CookieManager = CookieManager.getInstance()
        cookieManager.removeAllCookies(null)
        cookieManager.setAcceptCookie(false)
    }
}