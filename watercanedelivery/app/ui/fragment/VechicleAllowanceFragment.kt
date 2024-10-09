package com.watercanedelivery.app.ui.fragment

import android.opengl.Visibility
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
import com.watercanedelivery.app.adapter.VechicleAllowanceAdapter
import com.watercanedelivery.app.data.StaffAllowanceDTO
import com.watercanedelivery.app.data.VechicelAllowance
import com.watercanedelivery.app.databinding.FragmentVechicleAllowanceBinding
import com.watercanedelivery.app.interfaces.IItemClickListenrer
import com.watercanedelivery.app.utils.APIResult
import com.watercanedelivery.app.utils.CustomProgressDialog
import com.watercanedelivery.app.viewmodel.VechicelAllowanceViewModel
import com.watercanedelivery.utils.Constant
import com.watercanedelivery.utils.SharedPreferenceUtil
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [vechicleAllowanceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VechicleAllowanceFragment : BaseFragment(), View.OnClickListener, IItemClickListenrer {
    var customProgressDialog: CustomProgressDialog? = null

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    @Inject
    lateinit var sharedPreferenceUtil: SharedPreferenceUtil

    @Inject
    lateinit var vechicleViewModel: VechicelAllowanceViewModel
     var fragmentVechicleAllowanceBinding: FragmentVechicleAllowanceBinding?=null
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
        fragmentVechicleAllowanceBinding =
            FragmentVechicleAllowanceBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        fragmentVechicleAllowanceBinding?.btnHome?.setOnClickListener(this)
        fragmentVechicleAllowanceBinding?.btnAddVechAllow?.setOnClickListener(this)
        fragmentVechicleAllowanceBinding?.btnCustomer?.setOnClickListener(this)
        if (isNetworkConnected())
            vechicleViewModel.vechicleData(sharedPreferenceUtil.getData(Constant.USER_ID)!!)

        resultVechAllowanceList()
        return fragmentVechicleAllowanceBinding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (customProgressDialog != null && customProgressDialog!!.isShowing)
            customProgressDialog?.dismiss()
        fragmentVechicleAllowanceBinding=null
    }

    private fun resultVechAllowanceList() {

        vechicleViewModel.vechicleLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                APIResult.Status.LOADING -> {
                    customProgressDialog = CustomProgressDialog.show(requireActivity(), true, false)
//                    showProgress(this)
                }
                APIResult.Status.ERROR -> {
                    customProgressDialog?.dismiss()
//                    fragmentVechicleAllowanceBinding.txtviewNoRecordFound.visibility = View.VISIBLE
//                    hideProgress()

                }
                APIResult.Status.SUCCESS -> {
//                    hideProgress()
                    customProgressDialog?.dismiss()

                    it.data.let { data -> vechList(data) }


                }
            }
        })
    }

    private fun vechList(data: VechicelAllowance?) {

        if (data != null && data.status!! && data.staffAllowance!!.size > 0) {
            vechicleRecyclerviewAdapter(data.staffAllowance)
        } else
            fragmentVechicleAllowanceBinding?.txtviewNoRecordFound?.visibility = View.VISIBLE

    }

    override fun onResume() {
        super.onResume()
    }

    fun vechicleRecyclerviewAdapter(vechList: ArrayList<StaffAllowanceDTO?>) {
        val gridLayoutManager = LinearLayoutManager(
            requireActivity().getApplicationContext()

        )
        fragmentVechicleAllowanceBinding?.recyclerviewCustList?.layoutManager = gridLayoutManager
        val customAdapter =
            VechicleAllowanceAdapter(vechList, requireActivity(), this)
        fragmentVechicleAllowanceBinding?.recyclerviewCustList?.setAdapter(customAdapter) // set the Adapter to RecyclerView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment vechicleAllowanceFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            VechicleAllowanceFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(view: View?) {

        when (view?.id) {
            R.id.btn_home -> {
                Navigation.findNavController(view)
                    .navigate(R.id.action_vechicleAllowanceFragment_to_home_fragment);
            }
            R.id.btn_customer -> {
                Navigation.findNavController(view)
                    .navigate(R.id.action_vechicleAllowanceFragment_to_customerListFragment);
            }
            R.id.btn_add_vech_allow -> {
                Navigation.findNavController(view)
                    .navigate(R.id.action_vechicleAllowanceFragment_to_vechicleAllowanceAddFragment);
            }
        }
    }

    override fun itemClick(pos: Int, view: View) {

    }
}