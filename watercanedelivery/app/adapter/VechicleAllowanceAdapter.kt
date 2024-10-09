package com.watercanedelivery.app.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.watercanedelivery.app.data.CustomersDTO
import com.watercanedelivery.app.data.StaffAllowanceDTO
import com.watercanedelivery.app.databinding.ListCustomerRowBinding
import com.watercanedelivery.app.databinding.ListVechicleAllowanceBinding
import com.watercanedelivery.app.interfaces.IItemClickListenrer


class VechicleAllowanceAdapter(
    var list: ArrayList<StaffAllowanceDTO?>,
    var context: Context, var iItemClickListener: IItemClickListenrer
) :
    RecyclerView.Adapter<VechicleAllowanceAdapter.ViewHolder>() {
    private val TAG: String = javaClass.simpleName
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val vechicleAllowanceBinding =
            ListVechicleAllowanceBinding.inflate(LayoutInflater.from(context), parent, false)



        return ViewHolder(vechicleAllowanceBinding, list!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list.get(position)!!, context)
        Log.i(TAG, "onBindViewHolder: " + Gson().toJson(list))
    }

    fun addItems(postItems: ArrayList<StaffAllowanceDTO>) {
        list.addAll(postItems!!)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder constructor(
        val binding: ListVechicleAllowanceBinding,
        var list: ArrayList<StaffAllowanceDTO?>
    ) :

        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(data: StaffAllowanceDTO, context: Context) {

            binding.txtviewDate.text = data.entry_date
            binding.txtviewDiffernce.text = data.totalKm
            binding.txtviewInKm.text = data.startKm
            binding.txtviewOutKm.text = data.endKm

        }

        override fun onClick(view: View?) {
            /*  when (view) {
                  binding.imgviewFavIcon -> {
                      iRecipeListener.clickItemAdd(adapterPosition, list, true)
                  }
                  binding.cardviewFeatureRecipe -> {
                      iRecipeListener.clickRecipe(list[adapterPosition]?.id!!)

                  }

              }*/


        }


    }
}
