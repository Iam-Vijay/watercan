package com.watercanedelivery.app.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.watercanedelivery.app.R
import com.watercanedelivery.app.data.CustomersDTO
import com.watercanedelivery.app.data.StaffDTO
import com.watercanedelivery.app.data.StaffExpenseDTO
import com.watercanedelivery.app.databinding.ListCustomerRowBinding
import com.watercanedelivery.app.databinding.ListExpenseBinding
import com.watercanedelivery.app.interfaces.IItemClickListenrer
import java.util.logging.Filter


class ExpensesAdapter(
    var list: ArrayList<StaffExpenseDTO?>,
    var context: Context, var iItemClickListener: IItemClickListenrer
) :
    RecyclerView.Adapter<ExpensesAdapter.ViewHolder>(), Filterable {
    var expenseFilterList = ArrayList<StaffExpenseDTO?>()

    init {
        expenseFilterList = list as ArrayList<StaffExpenseDTO?>
    }


    private val TAG: String = javaClass.simpleName
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val listExpenseBinding =
            ListExpenseBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(listExpenseBinding, list)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list.get(position)!!, context)
        Log.i(TAG, "onBindViewHolder: " + Gson().toJson(list))
    }

    fun addItems(postItems: ArrayList<StaffExpenseDTO>) {
        list.addAll(postItems!!)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(
        val binding: ListExpenseBinding,
        var list: ArrayList<StaffExpenseDTO?>
    ) :

        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(data: StaffExpenseDTO, context: Context) {

            binding.txtviewAmount.text = context.resources.getString(R.string.rs).plus(data.amount)
            binding.txtviewDate.text = data.entry_date
            binding.txtviewReason.text = data.remarks
/*            binding.imgvtiewFavIcon.setOnClickListener(this)
            binding.cardviewFeatureRecipe.setOnClickListener(this)
       */
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

    override fun getFilter(): android.widget.Filter {
        return object : android.widget.Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    expenseFilterList = list as ArrayList<StaffExpenseDTO?>
                } else {
                    val resultList = ArrayList<StaffExpenseDTO?>()
                    for (row in expenseFilterList) {
                        if (row?.amount?.toLowerCase()
                                ?.contains(constraint.toString().toLowerCase())!!
                        ) {
                            resultList.add(row)
                        }
                    }
                    expenseFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = expenseFilterList
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, result: FilterResults?) {
                expenseFilterList = result?.values as ArrayList<StaffExpenseDTO?>
            }

        }

    }


}
