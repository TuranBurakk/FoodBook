package com.example.besinkitabi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.besinkitabi.R
import com.example.besinkitabi.databinding.RecRowBinding
import com.example.besinkitabi.model.Food
import com.example.besinkitabi.view.ListFragmentDirections
import kotlinx.android.synthetic.main.rec_row.view.*

class FoodRecyclerAdapter(val foodList: ArrayList<Food>) : RecyclerView.Adapter<FoodRecyclerAdapter.FoodViewHolder>(),FoodClickListener{
    class FoodViewHolder(var view : RecRowBinding): RecyclerView.ViewHolder(view.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<RecRowBinding>(inflater,R.layout.rec_row,parent,false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.view.food = foodList[position]
        holder.view.listener = this
    }

    override fun getItemCount(): Int {
       return foodList.size
    }
    fun foodListUpdate(newFoodList : List<Food>){
        foodList.clear()
        foodList.addAll(newFoodList)
        notifyDataSetChanged()

    }

    override fun foodClick(view: View) {
        val uuid = view.food_uuid.text.toString().toIntOrNull()
        uuid?.let {
            val action = ListFragmentDirections.actionListFragmentToDetailsFragment(it)
            Navigation.findNavController(view).navigate(action)
        }

    }
}