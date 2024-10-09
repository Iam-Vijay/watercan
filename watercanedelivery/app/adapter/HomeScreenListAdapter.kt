package com.watercanedelivery.app.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.watercanedelivery.app.data.HomeItemModel
import com.watercanedelivery.app.databinding.ListCustomerRowBinding
import com.watercanedelivery.app.databinding.ListHomeGridBinding
import com.watercanedelivery.app.interfaces.IItemClickListenrer


class HomeScreenListAdapter(
    var list: ArrayList<HomeItemModel>,
    var context: Context, var iItemClickListener: IItemClickListenrer
) :
    RecyclerView.Adapter<HomeScreenListAdapter.ViewHolder>() {
    private val TAG: String = javaClass.simpleName
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val listHomeGridBinding =
            ListHomeGridBinding.inflate(LayoutInflater.from(context), parent, false)



        return ViewHolder(listHomeGridBinding, list, iItemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list.get(position)!!, context)
        Log.i(TAG, "onBindViewHolder: " + Gson().toJson(list))
    }

    fun addItems(postItems: ArrayList<HomeItemModel>) {
        list.addAll(postItems!!)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder constructor(
        val binding: ListHomeGridBinding,
        var list: ArrayList<HomeItemModel>, var iItemClickListener: IItemClickListenrer
    ) :

        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(data: HomeItemModel, context: Context) {

            binding.imgviewHomeItem.setImageResource(data.drawable)
            binding.txtviewTitle.text = data.title
            binding.mainLayHome.setOnClickListener(this)
            /* binding.imgvtiewFavIcon.setOnClickListener(this)
             binding.cardviewFeatureRecipe.setOnClickListener(this)
  */
        }

        override fun onClick(view: View?) {
            when (view) {
                binding.mainLayHome -> {
                    iItemClickListener.itemClick(adapterPosition,view)
                }


            }


        }


    }
}
