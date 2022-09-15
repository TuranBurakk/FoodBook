package com.example.besinkitabi.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.besinkitabi.R
import com.example.besinkitabi.adapter.FoodRecyclerAdapter
import com.example.besinkitabi.viewmodel.FoodListViewModel
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment() {
    private lateinit var  viewModel : FoodListViewModel
    private val adapter by lazy { FoodRecyclerAdapter(arrayListOf()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this)[FoodListViewModel::class.java]
        viewModel.refreshData()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        swipeRefresh.setOnRefreshListener {
            Progresbar.visibility = View.VISIBLE
            mistakeTv.visibility = View.GONE
            recyclerView.visibility = View.GONE
            viewModel.refreshFromNet()
            swipeRefresh.isRefreshing = false
        }
        observeLiveData()

    }
    fun observeLiveData(){
        viewModel.foods.observe(viewLifecycleOwner, Observer {
            it?.let {
                recyclerView.visibility = View.VISIBLE
                adapter.foodListUpdate(it)
            }
        })
        viewModel.foodMistake.observe(viewLifecycleOwner, Observer {Booleam ->
            Booleam?.let {
                if (Booleam){
                  mistakeTv.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }else mistakeTv.visibility = View.GONE
            }
        })
        viewModel.foodDownload.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (it){
                    recyclerView.visibility = View.GONE
                    mistakeTv.visibility = View.GONE
                    Progresbar.visibility = View.VISIBLE
                }else Progresbar.visibility = View.GONE
            }
        })
    }
}