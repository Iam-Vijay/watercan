package com.watercanedelivery.app.ui.fragment

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.watercanedelivery.app.BaseFragment
import com.watercanedelivery.app.R
import com.watercanedelivery.app.data.CommonResponseDTO
import com.watercanedelivery.app.databinding.FragmentVechicleAllowanceAddBinding
import com.watercanedelivery.app.utils.APIResult
import com.watercanedelivery.app.utils.CustomProgressDialog
import com.watercanedelivery.app.viewmodel.VechicelAllowanceViewModel
import com.watercanedelivery.utils.Constant
import com.watercanedelivery.utils.SharedPreferenceUtil
import java.util.*
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [VechicleAllowanceAddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VechicleAllowanceAddFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
     var customProgressDialog: CustomProgressDialog?=null

    @Inject
    lateinit var sharedPreferenceUtil: SharedPreferenceUtil

    @Inject
    lateinit var vechicleViewModel: VechicelAllowanceViewModel
    var vechicleAllowanceAddBinding: FragmentVechicleAllowanceAddBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private fun getDateToPicker(
        context: Context,
        editTextDate: TextView
    ) {
        val cldr = Calendar.getInstance()
        val day: Int
        val month: Int
        val year: Int
        day = cldr[Calendar.DAY_OF_MONTH]
        month = cldr[Calendar.MONTH]
        year = cldr[Calendar.YEAR]
        /*     if (longToDate > 0) {
                 if (toDate != null && !toDate.isEmpty()) {
                     val array = toDate.split("/".toRegex()).toTypedArray()
                     day = array[1].toInt()
                     month = array[0].toInt() - 1
                     year = array[2].toInt()
                 } else {
                     day = cldr[Calendar.DAY_OF_MONTH]
                     month = cldr[Calendar.MONTH]
                     year = cldr[Calendar.YEAR]
                 }
             } else {
                 day = cldr[Calendar.DAY_OF_MONTH]
                 month = cldr[Calendar.MONTH]
                 year = cldr[Calendar.YEAR]
             }*/
        // date picker dialog
        var picker: DatePickerDialog? = null
        picker = DatePickerDialog(
            context, R.style.MyDatePickerStyle,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                editTextDate.text = (monthOfYear + 1).toString() + "/" + dayOfMonth + "/" + year
                /* filteredDateToServer =
                     (monthOfYear + 1).toString() + "-" + dayOfMonth + "-" + year
                 filteredDateToDisplay =
                     (monthOfYear + 1).toString() + "/" + dayOfMonth + "/" + year*/
                editTextDate.setError("", null)

                cldr[year, monthOfYear] = dayOfMonth
            }, year, month, day
        )
        picker.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vechicleAllowanceAddBinding =
            FragmentVechicleAllowanceAddBinding.inflate(inflater, container, false)
        addVechAllowResult()
        vechicleAllowanceAddBinding?.edtviewChooseDate?.setOnClickListener(object :
            View.OnClickListener {
            override fun onClick(p0: View?) {
                getDateToPicker(
                    requireActivity(),

                    vechicleAllowanceAddBinding?.edtviewChooseDate!!
                )
            }

        })
        vechicleAllowanceAddBinding?.btnSubmit?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if (validateEditValue(
                        vechicleAllowanceAddBinding?.edtviewChooseDate!!,
                        "Choose the date"
                    )
                ) {
                    if (validateEditValue(
                            vechicleAllowanceAddBinding?.edtviewStartKmValue!!,
                            "Enter Start Km"
                        )
                    ) {
                        if (validateEditValue(
                                vechicleAllowanceAddBinding?.edtviewEndKmValue!!, "Enter End Km"
                            )
                        ) {
                            val entryDate =
                                vechicleAllowanceAddBinding?.edtviewChooseDate?.text.toString()

                            val startKm =
                                vechicleAllowanceAddBinding?.edtviewStartKmValue?.text.toString()
                            val endKm =
                                vechicleAllowanceAddBinding?.edtviewEndKmValue?.text.toString()
                            val staff_id = sharedPreferenceUtil?.getData(Constant.USER_ID)
                            if (isNetworkConnected())
                            vechicleViewModel.addVechAllow(staff_id!!, startKm, endKm, entryDate)
                        }
                    }
                }

            }

        })
        // Inflate the layout for this fragment
        return vechicleAllowanceAddBinding?.root
    }

    private fun addVechAllowResult() {

        vechicleViewModel.vechicleAddLiveData.observe(viewLifecycleOwner, Observer {

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

                    it.data.let { data -> vechAdd(data) }


                }
            }
        })
    }

    private fun vechAdd(commonResponseDTO: CommonResponseDTO?) {

        if (commonResponseDTO?.status!!) {
            Toast.makeText(requireActivity(), commonResponseDTO.msg, Toast.LENGTH_SHORT).show()
            Navigation.findNavController(vechicleAllowanceAddBinding?.btnSubmit!!)
                .navigate(R.id.action_vechicleAllowanceAddFragment_to_vechicleAllowanceFragment);

        } else {
            Toast.makeText(requireActivity(), commonResponseDTO.msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        vechicleAllowanceAddBinding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment VechicleAllowanceAddFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            VechicleAllowanceAddFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}