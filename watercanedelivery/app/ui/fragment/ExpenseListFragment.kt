package com.watercanedelivery.app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.watercanedelivery.app.BaseFragment

import com.watercanedelivery.app.R
import com.watercanedelivery.app.adapter.ExpensesAdapter

import com.watercanedelivery.app.data.StaffExpenseDTO
import com.watercanedelivery.app.data.StaffResponseDTO
import com.watercanedelivery.app.databinding.FragmentExpenseListBinding
import com.watercanedelivery.app.interfaces.IItemClickListenrer
import com.watercanedelivery.app.utils.APIResult
import com.watercanedelivery.app.utils.CustomProgressDialog
import com.watercanedelivery.app.viewmodel.ExpensesViewModel
import com.watercanedelivery.utils.Constant
import com.watercanedelivery.utils.SharedPreferenceUtil
import dagger.android.support.DaggerFragment
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ExpenseListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExpenseListFragment : BaseFragment(), View.OnClickListener, IItemClickListenrer {
    private var customProgressDialog: CustomProgressDialog? = null

    // TODO: Rename and change types of parameters
    private var param1: String? = null

    @Inject
    lateinit var sharedPreferenceUtil: SharedPreferenceUtil

    @Inject
    lateinit var expensesViewModel: ExpensesViewModel
    private var param2: String? = null
    var expenseListBinding: FragmentExpenseListBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        expenseListBinding = FragmentExpenseListBinding.inflate(inflater, container, false)
        expenseListBinding?.btnAddExpense?.setOnClickListener(this)
        expenseListBinding?.btnHome?.setOnClickListener(this)
        expenseListBinding?.btnCustomer?.setOnClickListener(this)
        if (isNetworkConnected())
            expensesViewModel.customerData(sharedPreferenceUtil?.getData(Constant.USER_ID)!!)
        resultExpensesList()
        // Inflate the layout for this fragment
        return expenseListBinding?.root
    }

    override fun onResume() {
        super.onResume()

    }

    private fun resultExpensesList() {
        expensesViewModel.expensesLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status) {

                APIResult.Status.LOADING -> {
                    customProgressDialog = CustomProgressDialog.show(requireActivity(), true, false)
//                    showProgress(this)
                }
                APIResult.Status.ERROR -> {
                    customProgressDialog?.dismiss()
//                    hideProgress()
//                    expenseListBinding?.txtviewNoRecordFound?.visibility = View.VISIBLE


                }
                APIResult.Status.SUCCESS -> {
//                    hideProgress()
                    customProgressDialog?.dismiss()

                    it.data.let { data -> successExpensesList(data) }


                }
            }
        })

    }

    private fun successExpensesList(data: StaffResponseDTO?) {

        expenseRecyclerViewListAdapter(data?.staffExpense!!)
    }

    fun expenseRecyclerViewListAdapter(staffList: ArrayList<StaffExpenseDTO?>) {
        val gridLayoutManager = LinearLayoutManager(
            requireActivity().getApplicationContext()

        )
        expenseListBinding?.recyclerviewCustList?.layoutManager = gridLayoutManager
        val customAdapter =
            ExpensesAdapter(staffList, requireActivity(), this)
        expenseListBinding?.recyclerviewCustList?.setAdapter(customAdapter) // set the Adapter to RecyclerView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ExpenseListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExpenseListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_add_expense -> {
                Navigation.findNavController(view)
                    .navigate(R.id.action_expenseListFragment_to_addNewExpenseFragment)
            }

            R.id.btn_home -> {
                Navigation.findNavController(view)
                    .navigate(R.id.action_expenseListFragment_to_homeFragment)
            }
            R.id.btn_customer -> {
                Navigation.findNavController(view)
                    .navigate(R.id.action_expenseListFragment_to_customerFragment)
            }
        }

    }

    override fun itemClick(pos: Int, view: View) {


    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (customProgressDialog != null && customProgressDialog!!.isShowing)
            customProgressDialog?.dismiss()

        expenseListBinding = null
    }

}