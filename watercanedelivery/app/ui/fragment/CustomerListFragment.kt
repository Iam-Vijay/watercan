package com.watercanedelivery.app.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.watercanedelivery.app.BaseFragment

import com.watercanedelivery.app.R
import com.watercanedelivery.app.adapter.CustomerScreenListAdapter
import com.watercanedelivery.app.data.CustomerResponseDTO
import com.watercanedelivery.app.data.CustomersDTO
import com.watercanedelivery.app.data.cutomer.CustomerListRespDTO
import com.watercanedelivery.app.databinding.FragmentCustomerListBinding
import com.watercanedelivery.app.interfaces.IItemClickListenrer
import com.watercanedelivery.app.utils.APIResult
import com.watercanedelivery.app.utils.CustomProgressDialog
import com.watercanedelivery.app.utils.NetworkUtils.isNetworkConnected
import com.watercanedelivery.app.viewmodel.CustomerViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CustomerListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CustomerListFragment : BaseFragment(), View.OnClickListener, IItemClickListenrer {
    var customAdapter: CustomerScreenListAdapter? = null
    private val TAG: String? = javaClass.simpleName

    @Inject
    lateinit var customerViewModel: CustomerViewModel

    var customProgressDialog: CustomProgressDialog? = null

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
     var fragmentCustomerListBinding: FragmentCustomerListBinding?=null
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
        fragmentCustomerListBinding =
            FragmentCustomerListBinding.inflate(inflater, container, false)
        fragmentCustomerListBinding?.btnHome?.setOnClickListener(this)
        fragmentCustomerListBinding?.btnCustomer?.setOnClickListener(this)
        if (isNetworkConnected())
            customerResult()

        customerViewModel.customerData()
        // Inflate the layout for this fragment
        fragmentCustomerListBinding?.searchviewCustomerList?.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true

            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (customAdapter != null)
                    customAdapter?.filter?.filter(newText)
                return true
            }

        })
        return fragmentCustomerListBinding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (customProgressDialog != null && customProgressDialog!!.isShowing)
            customProgressDialog!!.dismiss()
        fragmentCustomerListBinding=null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CustomerListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CustomerListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun customerRecyclerviewListAdapter(customerList: ArrayList<com.watercanedelivery.app.data.cutomer.CustomersDTO?>?) {
        val gridLayoutManager = LinearLayoutManager(
            requireActivity().getApplicationContext()

        )
        fragmentCustomerListBinding?.recyclerviewCustList?.layoutManager = gridLayoutManager
        customAdapter =
            CustomerScreenListAdapter(customerList!!, requireActivity(), this)
        fragmentCustomerListBinding?.recyclerviewCustList?.setAdapter(customAdapter) // set the Adapter to RecyclerView
    }

    override fun onClick(view: View?) {

        when (view?.id) {
            R.id.btn_home -> {
                Navigation.findNavController(view)
                    .navigate(R.id.action_customerListFragment_to_homeFragment);
            }
            /* R.id.btn_customer -> {
                 Navigation.findNavController(view)
                     .navigate(R.id.action_vechicleAllowanceFragment_to_customerListFragment);
             }*/
        }
    }

    override fun itemClick(pos: Int, view: View) {


    }

    override fun cusItemClick(id: String, view: View) {
        Log.i(TAG, "cusItemClick: " + id)
        val action =
            CustomerListFragmentDirections.actionCustomerListFragmentToCustomerViewFragment(id)
        Navigation.findNavController(view)
            .navigate(action);
    }

    override fun onResume() {
        super.onResume()

    }

    fun customerResult() {
        customerViewModel.customerLiveData.observe(viewLifecycleOwner, Observer {

            when (it.status) {

                APIResult.Status.LOADING -> {
                    customProgressDialog = CustomProgressDialog.show(requireActivity(), true, false)
//                    showProgress(this)
                }
                APIResult.Status.ERROR -> {
                    customProgressDialog!!.dismiss()
//                    hideProgress()

                }
                APIResult.Status.SUCCESS -> {
//                    hideProgress()
                    customProgressDialog!!.dismiss()

                    it.data.let { data -> customerListData(data) }


                }
            }
        })
    }

    private fun customerListData(customerResponseDTO: CustomerListRespDTO?) {

        if (customerResponseDTO?.status!!) {
            if (customerResponseDTO.customers != null && customerResponseDTO.customers.size > 0)
                customerRecyclerviewListAdapter(customerResponseDTO.customers)

            Log.i(TAG, "customerListData: " + Gson().toJson(customerResponseDTO))
        }
    }

}