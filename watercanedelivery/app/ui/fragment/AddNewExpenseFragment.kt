package com.watercanedelivery.app.ui.fragment

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.watercanedelivery.app.BaseFragment
import com.watercanedelivery.app.R
import com.watercanedelivery.app.data.CommonResponseDTO
import com.watercanedelivery.app.databinding.FragmentAddNewExpenseBinding
import com.watercanedelivery.app.utils.APIResult
import com.watercanedelivery.app.utils.CustomProgressDialog
import com.watercanedelivery.app.utils.NetworkUtils.isNetworkConnected
import com.watercanedelivery.app.viewmodel.ExpensesViewModel
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
 * Use the [AddNewExpenseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddNewExpenseFragment : BaseFragment(), View.OnClickListener {
     var customProgressDialog: CustomProgressDialog?=null
    lateinit var fragmentAddNewExpenseBinding: FragmentAddNewExpenseBinding

    @Inject
    lateinit var sharedPreferenceUtil: SharedPreferenceUtil

    @Inject
    lateinit var expenseViewModel: ExpensesViewModel

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        fragmentAddNewExpenseBinding =
            FragmentAddNewExpenseBinding.inflate(inflater, container, false)
        fragmentAddNewExpenseBinding.btnSubmit.setOnClickListener(this)
        addExpensesResult()
        fragmentAddNewExpenseBinding.edtviewChooseDate.setOnClickListener(object :
            View.OnClickListener {
            override fun onClick(p0: View?) {
                getDateToPicker(
                    requireActivity(),
                    "",
                    fragmentAddNewExpenseBinding.edtviewChooseDate
                )

            }

        })
        return fragmentAddNewExpenseBinding.root
        // Inflate the layout for this fragment
    }

    private fun addExpensesResult() {

        expenseViewModel.expensesAddLiveData.observe(viewLifecycleOwner, Observer {

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

                    it.data.let { data -> addExpenses(data) }


                }
            }
        })
    }

    private fun getDateToPicker(
        context: Context,
        toDate: String?,
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
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                editTextDate.text = (monthOfYear + 1).toString() + "/" + dayOfMonth + "/" + year
                /* filteredDateToServer =
                     (monthOfYear + 1).toString() + "-" + dayOfMonth + "-" + year
                 filteredDateToDisplay =
                     (monthOfYear + 1).toString() + "/" + dayOfMonth + "/" + year*/
                editTextDate.setError("", null)

                //                        Log.i(TAG, "onDateSet: " + dayOfMonth + "-" + monthOfYear + "-" + year);
                cldr[year, monthOfYear] = dayOfMonth
//                longToDate = cldr.timeInMillis
            }, year, month, day
        )/*
        if (longFromDate > 0) picker.datePicker.minDate =
            longFromDate else picker.datePicker.minDate =
            cldr.timeInMillis*/

//        picker.getDatePicker().setSpinnersShown(false);
        picker.show()
    }


    private fun addExpenses(commonResponseDTO: CommonResponseDTO?) {

        if (commonResponseDTO?.status!!) {
            Toast.makeText(requireActivity(), commonResponseDTO.msg, Toast.LENGTH_SHORT).show()
            Navigation.findNavController(fragmentAddNewExpenseBinding.btnSubmit)

                .navigate(R.id.action_addNewExpenseFragment_to_expenseListFragment);

        } else {
            Toast.makeText(requireActivity(), commonResponseDTO.msg, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddNewExpenseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddNewExpenseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_submit -> {

                val reason = fragmentAddNewExpenseBinding.txtviewDesc.text.toString()
                val amount = fragmentAddNewExpenseBinding.edtviewAmountValue.text.toString()
                val entryDate = fragmentAddNewExpenseBinding.edtviewChooseDate.text.toString()
                if (validateEditValue(
                        fragmentAddNewExpenseBinding.edtviewChooseDate,
                        "Please choose Date"
                    )

                ) {
                    if (validateEditValue(
                            fragmentAddNewExpenseBinding.edtviewDescValue,
                            "Please enter remarks"
                        )
                    ) {
                        if (validateEditValue(
                                fragmentAddNewExpenseBinding.edtviewAmountValue,
                                "Please enter amount"
                            )

                        ) {

                            if (isNetworkConnected())
                                expenseViewModel.addExpenses(
                                    sharedPreferenceUtil.getData(Constant.USER_ID)!!,
                                    reason,
                                    amount,
                                    entryDate
                                )
                        }
                    }
                }


            }

        }
    }
}