package com.example.retrofitlab

import android.content.Context.INPUT_METHOD_SERVICE
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitlab.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener{

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter:ArticleAdapter
    private val articleList = mutableListOf<Articles>()

    private var pais_elegido: String ="us"
    private var categoria: String ="general"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.searchNews.setOnQueryTextListener(this)
        initRecyclerView()
        searchNew(pais_elegido,categoria)
        binding.btnAr.setOnClickListener{ pais_elegido = "ar"
            searchNew(pais_elegido,categoria) }
        binding.btnUa.setOnClickListener{ pais_elegido = "ua"
            searchNew(pais_elegido,categoria)}
        binding.btnMx.setOnClickListener{ pais_elegido = "mx"
            searchNew(pais_elegido,categoria)}
        binding.btnBusiness.setOnClickListener{ categoria = "business"
            searchNew(pais_elegido,categoria)}
        binding.btnGeneral.setOnClickListener{ categoria = "general"
            searchNew(pais_elegido,categoria)}
        binding.btnHealth.setOnClickListener{ categoria = "health"
            searchNew(pais_elegido,categoria)}

    }

    private fun initRecyclerView(){
        adapter = ArticleAdapter(articleList)
        binding.rvNews.layoutManager = LinearLayoutManager(this)
        binding.rvNews.adapter = adapter
    }
    private fun searchNew(pais: String,category: String){
        val api = Retrofit2()
        CoroutineScope(Dispatchers.IO).launch {
            val call = api.getService()?.getNewsByCategory(pais,category,"4b94054dbc6b4b3b9e50d8f62cde4f6c")
            val news: NewsResponse? = call?.body()

            runOnUiThread{
                if (call!!.isSuccessful){
                    if (news?.status.equals("ok")){
                        val articles = news?.articles ?: emptyList()
                        articleList.clear()
                        articleList.addAll(articles)
                        adapter.notifyDataSetChanged()
                    }else{
                        showMessage("Error en webservices")
                    }

                }else{
                    showMessage("Error en retrofit")
                }
                hideKeyBoard()

            }
        }
    }
    private fun hideKeyBoard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }
    override fun onQueryTextSubmit(query: String?): Boolean {
        showMessage(query.toString())
        if (!query.isNullOrEmpty()){
            searchNew(pais_elegido,query.toLowerCase(Locale.ROOT))
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private fun showMessage(message: String){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }


}