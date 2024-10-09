package com.watercanedelivery.app.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.watercanedelivery.app.BaseFragment
import com.watercanedelivery.app.R
import com.watercanedelivery.app.adapter.HomeScreenListAdapter
import com.watercanedelivery.app.data.HomeItemModel
import com.watercanedelivery.app.data.HomeResponseModelDTO
import com.watercanedelivery.app.databinding.FragmentHomeBinding
import com.watercanedelivery.app.interfaces.IItemClickListenrer
import com.watercanedelivery.app.utils.APIResult
import com.watercanedelivery.app.utils.CustomProgressDialog
import com.watercanedelivery.app.viewmodel.HomeViewModel
import com.watercanedelivery.utils.Constant
import com.watercanedelivery.utils.SharedPreferenceUtil
import javax.inject.Inject


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : BaseFragment(), IItemClickListenrer {

     var customProgressDialog: CustomProgressDialog?=null

    @Inject
    lateinit var sharedPreferenceUtil: SharedPreferenceUtil

    @Inject
    lateinit var homeViewModel: HomeViewModel

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var homeBinding: FragmentHomeBinding
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

        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        getGridviewAdapter()
        if (isNetworkConnected())
            homeViewModel.homeDataValue(sharedPreferenceUtil.getData(Constant.USER_ID)!!)

        homeResult()
        // Inflate the layout for this fragment
        return homeBinding.root
    }

    private fun homeResult() {
        homeViewModel.homeLiveData.observe(viewLifecycleOwner, Observer {

            when (it.status) {
                APIResult.Status.LOADING -> {
                    customProgressDialog = CustomProgressDialog.show(requireActivity(), true, false)
//                    showProgress(this)
                }
                APIResult.Status.ERROR -> {
                    customProgressDialog?.dismiss()
//                    hideProgress()


                }
                APIResult.Status.SUCCESS -> {
//                    hideProgress()
                    customProgressDialog?.dismiss()

                    it.data.let { data -> homeDataValues(data) }


                }
            }

        })

    }

    private fun homeDataValues(data: HomeResponseModelDTO?) {
        homeBinding?.txtviewDeliverValue.text = data?.orders!!.toString()
        homeBinding?.txtviewTakenCane.text = data?.cane!!.toString()
        homeBinding?.txtviewTodayKmValue.text = data?.totalKm!!.toString()
        homeBinding?.txtviewExpenseValue.text = data?.totalExpenses!!.toString()

    }

    private fun getGridviewAdapter() {
        val homeItemModelArrayList: java.util.ArrayList<HomeItemModel> =
            java.util.ArrayList<HomeItemModel>()
        homeItemModelArrayList.add(HomeItemModel("Customer List", R.drawable.ic_cus_list))
        homeItemModelArrayList.add(
            HomeItemModel(
                "Vechicle Allowance",
                R.drawable.ic_travel_allowance
            )
        )
        homeItemModelArrayList.add(HomeItemModel("Expenses", R.drawable.ic_financial))
// set a GridLayoutManager with default vertical orientation and 3 number of columns
// set a GridLayoutManager with default vertical orientation and 3 number of columns
        val gridLayoutManager = GridLayoutManager(
            requireActivity().getApplicationContext(),
            2
        )
        homeBinding.recviewHome.layoutManager = gridLayoutManager
        val customAdapter =
            HomeScreenListAdapter(homeItemModelArrayList, requireActivity(), this)
        homeBinding.recviewHome.setAdapter(customAdapter) // set the Adapter to RecyclerView

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun itemClick(pos: Int, view: View) {

        when (pos) {
            0 -> {
                Navigation.findNavController(view)
                    .navigate(R.id.action_homeFragment_to_customerListFragment)
            }
            1 -> {
                Navigation.findNavController(view)
                    .navigate(R.id.action_homeFragment_to_vechicleAllowanceFragment)

            }
            2 -> {
                Navigation.findNavController(view)
                    .navigate(R.id.action_homeFragment_to_expenseListFragment)

            }
        }
    }
}