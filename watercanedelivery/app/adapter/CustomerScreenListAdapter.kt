package com.watercanedelivery.app.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.watercanedelivery.app.data.StaffExpenseDTO
import com.watercanedelivery.app.data.cutomer.CustomersDTO
import com.watercanedelivery.app.databinding.ListCustomerRowBinding
import com.watercanedelivery.app.interfaces.IItemClickListenrer


class CustomerScreenListAdapter(
    var list: ArrayList<com.watercanedelivery.app.data.cutomer.CustomersDTO?>,
    var context: Context, var iItemClickListener: IItemClickListenrer
) :
    RecyclerView.Adapter<CustomerScreenListAdapter.ViewHolder>(), Filterable {

    var customerListFilter = ArrayList<CustomersDTO?>()

    init {
        customerListFilter = list
    }

    private val TAG: String = javaClass.simpleName
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val listFeatureRecipesBinding =
            ListCustomerRowBinding.inflate(LayoutInflater.from(context), parent, false)



        return ViewHolder(listFeatureRecipesBinding, customerListFilter, iItemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(customerListFilter.get(position), context)
        Log.i(TAG, "onBindViewHolder: " + Gson().toJson(list))
    }

    fun addItems(postItems: ArrayList<CustomersDTO>) {
        list.addAll(postItems)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = customerListFilter.size

    class ViewHolder constructor(
        val binding: ListCustomerRowBinding,
        var list: ArrayList<CustomersDTO?>?, var itemClickListener: IItemClickListenrer
    ) :

        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(data: CustomersDTO?, context: Context) {

            binding.txtviewCusName.text = data?.name
            binding.txtviewAddress.text = data?.address
            binding.txtviewId.text = data?.customerId
            binding.txtviewMobileNumber.text = data?.mobileNo
//             binding.imgvtiewFavIcon.setOnClickListener(this)
            binding.cardviewCus.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            when (view) {

                binding.cardviewCus -> {
                    itemClickListener.cusItemClick(list!![adapterPosition]?.id!!,view)

                }

            }


        }


    }

    override fun getFilter(): android.widget.Filter {
        return object : android.widget.Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    customerListFilter = list as ArrayList<CustomersDTO?>
                } else {
                    val resultList = ArrayList<CustomersDTO?>()
                    for (row in list) {
                        if (row?.mobileNo?.toLowerCase()
                                ?.contains(constraint.toString().toLowerCase())!!
                        ) {
                            resultList.add(row)
                        }
                    }
                    customerListFilter = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = customerListFilter
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, result: FilterResults?) {
                customerListFilter = result?.values as ArrayList<CustomersDTO?>
                notifyDataSetChanged()
            }

        }

    }
}