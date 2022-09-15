package com.example.besinkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.besinkitabi.R
import com.example.besinkitabi.databinding.FragmentDetailsBinding
import com.example.besinkitabi.databinding.RecRowBinding
import com.example.besinkitabi.util.downloadImage
import com.example.besinkitabi.util.makePlaceHolder
import com.example.besinkitabi.viewmodel.FoodDetailsViewModel
import kotlinx.android.synthetic.main.fragment_details.*


class DetailsFragment : Fragment() {
    private lateinit var viewModel : FoodDetailsViewModel
    private  var foodId = 0
    private lateinit var dataBinding : FragmentDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_details,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            foodId = DetailsFragmentArgs.fromBundle(it).foodId
        }
        viewModel = ViewModelProviders.of(this).get(FoodDetailsViewModel::class.java)
        viewModel.getRoomData(foodId)

        observeLiveData()
    }

    fun observeLiveData(){
        viewModel.foodLiveData.observe(viewLifecycleOwner, Observer { food ->
            food?.let {
                dataBinding.selectFood = it
            }
        })
    }
}