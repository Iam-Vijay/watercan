package com.watercanedelivery.app.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.watercanedelivery.app.BaseFragment
import com.watercanedelivery.app.R
import com.watercanedelivery.app.data.BrandDTO
import com.watercanedelivery.app.data.BrandResponseDTO
import com.watercanedelivery.app.data.ViewCustomerDTO
import com.watercanedelivery.app.data.cutomer.UpdateCustomerDTO
import com.watercanedelivery.app.databinding.FragmentCustomerViewBinding
import com.watercanedelivery.app.utils.APIResult
import com.watercanedelivery.app.utils.CustomProgressDialog
import com.watercanedelivery.app.viewmodel.CustomerViewModel
import com.watercanedelivery.utils.Constant
import com.watercanedelivery.utils.SharedPreferenceUtil
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CustomerViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CustomerViewFragment : BaseFragment(), CompoundButton.OnCheckedChangeListener {
    private var nowPaidAmountGetValues: Int? = 0
    private var caneCount: Int = 0
    private var caneAmount: String = "0 Rs"
    private var quantity: String? = null
    private var brandValue: String? = null
    private var cusId: String? = null
    private lateinit var brandItems: Array<String?>
    private var balance: Int = 0
    private var TAG: String = javaClass.simpleName

    @Inject
    lateinit var customerViewModel: CustomerViewModel
    private var customProgressDialog: CustomProgressDialog? = null
    var fragmentCustomerViewBinding: FragmentCustomerViewBinding? = null

    @Inject
    lateinit var sharedPreferenceUtil: SharedPreferenceUtil

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
        // Inflate the layout for this fragment
        fragmentCustomerViewBinding =
            FragmentCustomerViewBinding.inflate(inflater, container, false)
        /*    fragmentCustomerViewBinding?.radioGroupAmount?.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener {

                    radioGroup, i ->
                caneAmount = i.toString()
            })*/

        fun EditText.disableTextSelection() {
            this.setCustomSelectionActionModeCallback(object : android.view.ActionMode.Callback {
                override fun onActionItemClicked(
                    mode: android.view.ActionMode?,
                    item: MenuItem?
                ): Boolean {
                    return false
                }

                override fun onCreateActionMode(
                    mode: android.view.ActionMode?,
                    menu: Menu?
                ): Boolean {
                    return false
                }

                override fun onPrepareActionMode(
                    mode: android.view.ActionMode?,
                    menu: Menu?
                ): Boolean {
                    return false
                }

                override fun onDestroyActionMode(mode: android.view.ActionMode?) {
                }
            })
        }


        fragmentCustomerViewBinding?.imgOutcaneEdit?.setOnClickListener(object :
            View.OnClickListener {
            override fun onClick(p0: View?) {
                if (fragmentCustomerViewBinding?.edtviewOutcane1?.visibility == View.GONE) {


                    fragmentCustomerViewBinding?.edtviewOutcane1?.visibility = View.VISIBLE
                    fragmentCustomerViewBinding?.edtviewOutcane2?.visibility = View.VISIBLE
                    fragmentCustomerViewBinding?.edtviewOutcane3?.visibility = View.VISIBLE
                    fragmentCustomerViewBinding?.edtviewOutcane4?.visibility = View.VISIBLE
                    fragmentCustomerViewBinding?.edtviewOutcane5?.visibility = View.VISIBLE
                } else {
                    fragmentCustomerViewBinding?.edtviewOutcane1?.visibility = View.GONE
                    fragmentCustomerViewBinding?.edtviewOutcane2?.visibility = View.GONE
                    fragmentCustomerViewBinding?.edtviewOutcane3?.visibility = View.GONE
                    fragmentCustomerViewBinding?.edtviewOutcane4?.visibility = View.GONE
                    fragmentCustomerViewBinding?.edtviewOutcane5?.visibility = View.GONE
                }
            }

        })
        fragmentCustomerViewBinding?.checkboxOutCane1?.setOnCheckedChangeListener(this)
        fragmentCustomerViewBinding?.checkboxOutCane2?.setOnCheckedChangeListener(this)
        fragmentCustomerViewBinding?.checkboxOutCane3?.setOnCheckedChangeListener(this)
        fragmentCustomerViewBinding?.checkboxOutCane4?.setOnCheckedChangeListener(this)
        fragmentCustomerViewBinding?.checkboxOutCane5?.setOnCheckedChangeListener(this)
        fragmentCustomerViewBinding?.radioGroupAmount?.setOnCheckedChangeListener(object :
            RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
                // This will get the radiobutton that has changed in its check state
                val checkedRadioButton =
                    group.findViewById(checkedId) as RadioButton
                // This puts the value (true/false) into the variable
                val isChecked = checkedRadioButton.isChecked
                fragmentCustomerViewBinding?.edtviewNowPaid?.setText("")
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {

                    caneAmount = checkedRadioButton.text.toString()
                    fragmentCustomerViewBinding?.edtviewTotAmount?.setText(
                        (caneCount * caneAmount.replace(
                            " Rs",
                            ""
                        ).trim().toInt()).toString()
                    )

                    fragmentCustomerViewBinding?.edtviewAmount?.setText(
                        caneAmount.replace(
                            " Rs",
                            ""
                        ).trim().toString()
                    )
                    fragmentCustomerViewBinding?.edtviewTotPayAmount?.setText(
                        (caneCount * caneAmount.replace(
                            " Rs",
                            ""
                        ).trim().toInt()).plus(
                            fragmentCustomerViewBinding?.edtviewPrevBal?.text.toString()
                                .toInt()
                        ).toString()
                    )
                    Log.i(
                        TAG,
                        "caneamount*cane: " + caneCount + " " + caneCount?.times(
                            caneAmount?.replace(" Rs", "")?.trim()?.toInt()!!
                        )
                    )

                    // Changes the textview's text to "Checked: example radiobutton text"
//                    Toast.makeText(context, checkedRadioButton.text, Toast.LENGTH_SHORT).show()
                }
            }
        })

        fragmentCustomerViewBinding?.btnSubmit?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if (validateEditValue(
                        fragmentCustomerViewBinding?.edtviewNowPaid!!,
                        "Please enter amount"
                    )
                ) {
                    val balance = fragmentCustomerViewBinding?.edtviewBalance?.text.toString()
                    val nowPaid = fragmentCustomerViewBinding?.edtviewNowPaid?.text.toString()
                    val totPayAmount =
                        fragmentCustomerViewBinding?.edtviewTotPayAmount?.text.toString()
                    val prevBal = fragmentCustomerViewBinding?.edtviewPrevBal?.text.toString()
                    val totAmount = fragmentCustomerViewBinding?.edtviewTotAmount?.text.toString()

                    val amount = fragmentCustomerViewBinding?.edtviewAmount?.text.toString()
                    val outCane1 = fragmentCustomerViewBinding?.edtviewOutcane1?.text.toString()
                    val outCane2 = fragmentCustomerViewBinding?.edtviewOutcane2?.text.toString()
                    val outCane3 = fragmentCustomerViewBinding?.edtviewOutcane3?.text.toString()
                    val outCane4 = fragmentCustomerViewBinding?.edtviewOutcane4?.text.toString()
                    val outCane5 = fragmentCustomerViewBinding?.edtviewOutcane5?.text.toString()
                    val inCane1 = fragmentCustomerViewBinding?.edtviewIncane1?.text.toString()
                    val inCane2 = fragmentCustomerViewBinding?.edtviewIncane2?.text.toString()
                    val inCane3 = fragmentCustomerViewBinding?.edtviewIncane3?.text.toString()
                    val inCane4 = fragmentCustomerViewBinding?.edtviewIncane4?.text.toString()
                    val inCane5 = fragmentCustomerViewBinding?.edtviewIncane5?.text.toString()
                    val updateCustomerDTO =
                        UpdateCustomerDTO(
                            sharedPreferenceUtil.getData(Constant.USER_ID),
                            cusId,
                            brandValue,
                            quantity,
                            caneAmount.replace(" Rs", ""),
                            totPayAmount,
                            nowPaid,
                            balance,
                            inCane1,
                            inCane2,
                            inCane3,
                            inCane4,
                            inCane5,
                            outCane1,
                            outCane2,
                            outCane3,
                            outCane4,
                            outCane5
                        )

                    if (isNetworkConnected())
                        customerViewModel.updateOrder(updateCustomerDTO)

                }
            }

        })
        fragmentCustomerViewBinding?.edtviewOutcane1?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                fragmentCustomerViewBinding?.checkboxOutCane1?.setText(p0)

            }

        })
        fragmentCustomerViewBinding?.edtviewOutcane2?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                fragmentCustomerViewBinding?.checkboxOutCane2?.setText(p0)

            }

        })
        fragmentCustomerViewBinding?.edtviewOutcane3?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                fragmentCustomerViewBinding?.checkboxOutCane3?.setText(p0)

            }

        })
        fragmentCustomerViewBinding?.edtviewOutcane4?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                fragmentCustomerViewBinding?.checkboxOutCane4?.setText(p0)

            }

        })
        fragmentCustomerViewBinding?.edtviewOutcane5?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                fragmentCustomerViewBinding?.checkboxOutCane5?.setText(p0)

            }

        })


        fragmentCustomerViewBinding?.edtviewNowPaid?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {


            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                nowPaidAmountGetValues =
                    fragmentCustomerViewBinding?.edtviewNowPaid?.text?.length
                if (p0!!.isNotEmpty()) {

                    if (fragmentCustomerViewBinding?.edtviewTotPayAmount?.text.toString()
                            .isNotEmpty()
                    ) {
                        var totPay =
                            fragmentCustomerViewBinding?.edtviewTotPayAmount?.text.toString()
                                .toInt()
                        var nowPaid = fragmentCustomerViewBinding?.edtviewNowPaid?.text.toString()
                            .toInt()

                        if (totPay >= nowPaid) {
                            balance =
                                fragmentCustomerViewBinding?.edtviewTotPayAmount?.text.toString()
                                    .toInt()
                                    .minus(
                                        fragmentCustomerViewBinding?.edtviewNowPaid?.text.toString()
                                            .toInt()
                                    )

                            fragmentCustomerViewBinding?.edtviewBalance?.setText(balance.toString())
                        } else {
                            fragmentCustomerViewBinding?.edtviewNowPaid?.getText()
                                ?.delete(
                                    nowPaidAmountGetValues!!.minus(1),
                                    nowPaidAmountGetValues!!
                                );

                            Toast.makeText(
                                requireActivity(),
                                "Amount is greater than payable",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    fragmentCustomerViewBinding?.edtviewBalance?.setText("0")

                }
            }

        })

        fragmentCustomerViewBinding?.projectSpinner?.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                brandValue = p0?.getItemAtPosition(p2).toString()
            }

        })
        return fragmentCustomerViewBinding?.root
    }

    private fun resultCusView() {

        customerViewModel.customerViewLiveData.observe(viewLifecycleOwner, Observer {

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

                    it.data.let { data -> customerViewData(data) }


                }
            }

        })
    }

    private fun resultBrand() {

        customerViewModel.brandLiveData.observe(viewLifecycleOwner, Observer {

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

                    it.data.let { data -> brandData(data) }


                }
            }

        })
    }

    var brandDTO = ArrayList<BrandDTO?>()

    private fun brandData(data: BrandResponseDTO?) {

        brandDTO = data?.brand!!
        brandItems =
            arrayOfNulls<String>(data?.brand!!.size)
        for (i in 0 until data?.brand!!.size) {
            //Storing names to string array
            brandItems[i] = data.brand.get(i)?.name
        }

        val brandAdapter: ArrayAdapter<String?> = ArrayAdapter<String?>(
            requireActivity(),
            android.R.layout.simple_spinner_item,
            brandItems
        )

        fragmentCustomerViewBinding?.projectSpinner?.adapter = brandAdapter
    }


    private fun customerViewData(data: ViewCustomerDTO?) {
        quantity = data?.orderDetail?.quantity
        when (quantity) {
            "1" -> {
                fragmentCustomerViewBinding?.checkboxOutCane1?.isEnabled = true
                fragmentCustomerViewBinding?.checkboxOutCane1?.isChecked = true
                fragmentCustomerViewBinding?.edtviewOutcane1?.isEnabled = true
            }
            "2" -> {
                fragmentCustomerViewBinding?.checkboxOutCane1?.isEnabled = true
                fragmentCustomerViewBinding?.checkboxOutCane1?.isChecked = true
                fragmentCustomerViewBinding?.edtviewOutcane1?.isEnabled = true
                fragmentCustomerViewBinding?.checkboxOutCane2?.isEnabled = true
                fragmentCustomerViewBinding?.checkboxOutCane2?.isChecked = true
                fragmentCustomerViewBinding?.edtviewOutcane2?.isEnabled = true
            }
            "3" -> {
                fragmentCustomerViewBinding?.checkboxOutCane1?.isEnabled = true
                fragmentCustomerViewBinding?.checkboxOutCane1?.isChecked = true
                fragmentCustomerViewBinding?.edtviewOutcane1?.isEnabled = true
                fragmentCustomerViewBinding?.checkboxOutCane2?.isEnabled = true
                fragmentCustomerViewBinding?.checkboxOutCane2?.isChecked = true
                fragmentCustomerViewBinding?.edtviewOutcane2?.isEnabled = true
                fragmentCustomerViewBinding?.checkboxOutCane3?.isEnabled = true
                fragmentCustomerViewBinding?.checkboxOutCane3?.isChecked = true
                fragmentCustomerViewBinding?.edtviewOutcane3?.isEnabled = true
            }
            "4" -> {
                fragmentCustomerViewBinding?.checkboxOutCane1?.isEnabled = true
                fragmentCustomerViewBinding?.checkboxOutCane1?.isChecked = true
                fragmentCustomerViewBinding?.edtviewOutcane1?.isEnabled = true
                fragmentCustomerViewBinding?.checkboxOutCane2?.isEnabled = true
                fragmentCustomerViewBinding?.checkboxOutCane2?.isChecked = true
                fragmentCustomerViewBinding?.edtviewOutcane2?.isEnabled = true
                fragmentCustomerViewBinding?.checkboxOutCane3?.isEnabled = true
                fragmentCustomerViewBinding?.checkboxOutCane3?.isChecked = true
                fragmentCustomerViewBinding?.edtviewOutcane3?.isEnabled = true
                fragmentCustomerViewBinding?.checkboxOutCane4?.isEnabled = true
                fragmentCustomerViewBinding?.checkboxOutCane4?.isChecked = true
                fragmentCustomerViewBinding?.edtviewOutcane4?.isEnabled = true
            }
            "5" -> {
                fragmentCustomerViewBinding?.checkboxOutCane1?.isEnabled = true
                fragmentCustomerViewBinding?.checkboxOutCane1?.isChecked = true
                fragmentCustomerViewBinding?.edtviewOutcane1?.isEnabled = true
                fragmentCustomerViewBinding?.checkboxOutCane2?.isEnabled = true
                fragmentCustomerViewBinding?.checkboxOutCane2?.isChecked = true
                fragmentCustomerViewBinding?.edtviewOutcane2?.isEnabled = true
                fragmentCustomerViewBinding?.checkboxOutCane3?.isEnabled = true
                fragmentCustomerViewBinding?.checkboxOutCane3?.isChecked = true
                fragmentCustomerViewBinding?.edtviewOutcane3?.isEnabled = true
                fragmentCustomerViewBinding?.checkboxOutCane4?.isEnabled = true
                fragmentCustomerViewBinding?.checkboxOutCane4?.isChecked = true
                fragmentCustomerViewBinding?.edtviewOutcane4?.isEnabled = true
                fragmentCustomerViewBinding?.checkboxOutCane5?.isEnabled = true
                fragmentCustomerViewBinding?.checkboxOutCane5?.isChecked = true
                fragmentCustomerViewBinding?.edtviewOutcane5?.isEnabled = true
            }
        }
//        caneAmount = data?.orderDetail?.cane_amount
        fragmentCustomerViewBinding?.txtviewCusName?.setText(data!!.orderDetail!!.name)
        fragmentCustomerViewBinding?.edtviewIncane1?.setText(data!!.inCane!!.inCane1)
        fragmentCustomerViewBinding?.edtviewIncane2?.setText(data!!.inCane!!.inCane2)
        fragmentCustomerViewBinding?.edtviewIncane3?.setText(data!!.inCane!!.inCane3)
        fragmentCustomerViewBinding?.edtviewIncane4?.setText(data!!.inCane!!.inCane4)
        fragmentCustomerViewBinding?.edtviewIncane5?.setText(data!!.inCane!!.inCane5)
        fragmentCustomerViewBinding?.edtviewOutcane1?.setText(data!!.orderDetail!!.outCane1)
        fragmentCustomerViewBinding?.edtviewOutcane2?.setText(data!!.orderDetail!!.outCane2)
        fragmentCustomerViewBinding?.edtviewOutcane3?.setText(data!!.orderDetail!!.outCane3)
        fragmentCustomerViewBinding?.edtviewOutcane4?.setText(data!!.orderDetail!!.outCane4)
        fragmentCustomerViewBinding?.edtviewOutcane5?.setText(data!!.orderDetail!!.outCane5)
        fragmentCustomerViewBinding?.edtviewAmount?.setText(data!!.orderDetail!!.cane_amount)
        /*  var totAmount =
              data!!.orderDetail!!.cane_amount?.toInt()
                  ?.times(data.orderDetail!!.quantity!!.toInt())*/
//        fragmentCustomerViewBinding?.edtviewTotAmount?.setText(totAmount.toString())

        if (data!!.orderDetail?.pending_amount.equals("") /*|| data!!.orderDetail?.pending_amount?.equals(
                "0"
            )!!*/
        ) {
            if ((data!!.orderDetail?.payable_amount.equals("")))
                fragmentCustomerViewBinding?.edtviewTotPayAmount?.setText("0")
            else
                fragmentCustomerViewBinding?.edtviewPrevBal?.setText(data!!.orderDetail?.payable_amount!!)

        } else
            fragmentCustomerViewBinding?.edtviewPrevBal?.setText(data!!.orderDetail?.pending_amount!!)
        if ((data!!.orderDetail?.payable_amount.equals("")))
            fragmentCustomerViewBinding?.edtviewTotPayAmount?.setText("0")
        else
            fragmentCustomerViewBinding?.edtviewPrevBal?.setText(data!!.orderDetail?.payable_amount!!)

//        fragmentCustomerViewBinding?.edtviewTotPayAmount?.setText(data!!.orderDetail!!.payable_amount!!)
//        fragmentCustomerViewBinding?.edtviewTotPayAmount?.setText(data!!.orderDetail!!.payable_amount)
        var spinner_pos = 0
        for (i in 0 until brandDTO!!.size) {
            //Storing names to string array
            brandItems[i] = brandDTO.get(i)?.name
            if (data!!.orderDetail!!.brand.equals(brandDTO.get(i)?.name)) {
                spinner_pos = i

            }
        }
        fragmentCustomerViewBinding?.projectSpinner?.setSelection(spinner_pos)

        fragmentCustomerViewBinding?.edtviewBalance?.setText(balance.toString())

    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentCustomerViewBinding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        cusId = arguments?.getString("cus_id")
        Log.i(TAG, "onCreateView: " + arguments?.getString("cus_id"))
        if (isNetworkConnected()) {
            customerViewModel.getBrand()

            customerViewModel.customerView(cusId!!)
        }
        resultCusView()
        resultBrand()
        updateCustomer()
    }

    private fun updateCustomer() {
        customerViewModel.updateOrderLiveData.observe(viewLifecycleOwner, Observer {

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

                    it.data.let { data ->
                        if (data?.status!!) {
                            Toast.makeText(requireActivity(), data.msg, Toast.LENGTH_SHORT).show()

                            Navigation.findNavController(fragmentCustomerViewBinding?.btnSubmit!!)
                                .navigate(R.id.action_customerViewFragment_to_customerListFragment);
                        }

                    }


                }
            }

        })

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CustomerViewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CustomerViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCheckedChanged(compoundBtn: CompoundButton?, isChecked: Boolean) {
        fragmentCustomerViewBinding?.edtviewNowPaid?.setText("")

        when (compoundBtn?.id) {

            R.id.checkbox_out_cane1 -> {

                if (isChecked) {
                    caneCount++
                    fragmentCustomerViewBinding?.edtviewTotAmount?.setText(
                        (caneCount * caneAmount.replace(
                            " Rs",
                            ""
                        ).trim().toInt()).toString()
                    )

                    fragmentCustomerViewBinding?.edtviewTotPayAmount?.setText(
                        (caneCount * caneAmount.replace(
                            " Rs",
                            ""
                        ).trim().toInt()).plus(
                            fragmentCustomerViewBinding?.edtviewPrevBal?.text.toString().toInt()
                        ).toString()
                    )
                    Log.i(
                        TAG,
                        "onCheckedChanged: " + caneCount * caneAmount.replace(" Rs", "").trim()
                            .toInt()
                    )
                } else {
                    caneCount--
                    fragmentCustomerViewBinding?.edtviewTotAmount?.setText(
                        (caneCount * caneAmount.replace(
                            " Rs",
                            ""
                        ).trim().toInt()).toString()
                    )
                    fragmentCustomerViewBinding?.edtviewTotPayAmount?.setText(
                        (caneCount * caneAmount.replace(
                            " Rs",
                            ""
                        ).trim().toInt()).plus(
                            fragmentCustomerViewBinding?.edtviewPrevBal?.text.toString().toInt()
                        ).toString()
                    )
                    Log.i(
                        TAG,
                        "onCheckedChanged: " + caneCount * caneAmount.replace(" Rs", "").trim()
                            .toInt()
                    )

                }
            }
            R.id.checkbox_out_cane2 -> {
                if (isChecked) {
                    caneCount++

                    fragmentCustomerViewBinding?.edtviewTotAmount?.setText(
                        (caneCount * caneAmount.replace(
                            " Rs",
                            ""
                        ).trim().toInt()).toString()
                    )
                    fragmentCustomerViewBinding?.edtviewTotPayAmount?.setText(
                        (caneCount * caneAmount.replace(
                            " Rs",
                            ""
                        ).trim().toInt()).plus(
                            fragmentCustomerViewBinding?.edtviewPrevBal?.text.toString().toInt()
                        ).toString()
                    )
                    Log.i(
                        TAG,
                        "onCheckedChanged: " + caneCount * caneAmount.replace(" Rs", "").trim()
                            .toInt()
                    )

                } else {
                    caneCount--
                    fragmentCustomerViewBinding?.edtviewTotAmount?.setText(
                        (caneCount * caneAmount.replace(
                            " Rs",
                            ""
                        ).trim().toInt()).toString()
                    )
                    fragmentCustomerViewBinding?.edtviewTotPayAmount?.setText(
                        (caneCount * caneAmount.replace(
                            " Rs",
                            ""
                        ).trim().toInt()).plus(
                            fragmentCustomerViewBinding?.edtviewPrevBal?.text.toString().toInt()
                        ).toString()
                    )
                    Log.i(
                        TAG,
                        "onCheckedChanged: " + caneCount * caneAmount.replace(" Rs", "").trim()
                            .toInt()
                    )

                }
            }
            R.id.checkbox_out_cane3 -> {
                if (isChecked) {
                    caneCount++

                    fragmentCustomerViewBinding?.edtviewTotAmount?.setText(
                        (caneCount * caneAmount.replace(
                            " Rs",
                            ""
                        ).trim().toInt()).toString()
                    )
                    fragmentCustomerViewBinding?.edtviewTotPayAmount?.setText(
                        (caneCount * caneAmount.replace(
                            " Rs",
                            ""
                        ).trim().toInt()).plus(
                            fragmentCustomerViewBinding?.edtviewPrevBal?.text.toString().toInt()
                        ).toString()
                    )
                    Log.i(
                        TAG,
                        "onCheckedChanged: " + caneCount * caneAmount.replace(" Rs", "").trim()
                            .toInt()
                    )

                } else {
                    caneCount--
                    fragmentCustomerViewBinding?.edtviewTotAmount?.setText(
                        (caneCount * caneAmount.replace(
                            " Rs",
                            ""
                        ).trim().toInt()).toString()
                    )
                    fragmentCustomerViewBinding?.edtviewTotPayAmount?.setText(
                        (caneCount * caneAmount.replace(
                            " Rs",
                            ""
                        ).trim().toInt()).plus(
                            fragmentCustomerViewBinding?.edtviewPrevBal?.text.toString().toInt()
                        ).toString()
                    )
                    Log.i(


                        TAG,
                        "onCheckedChanged: " + caneCount * caneAmount.replace(" Rs", "").trim()
                            .toInt()
                    )

                }
            }
            R.id.checkbox_out_cane4 -> {
                if (isChecked) {
                    caneCount++
                    fragmentCustomerViewBinding?.edtviewTotAmount?.setText(
                        (caneCount * caneAmount.replace(
                            " Rs",
                            ""
                        ).trim().toInt()).toString()
                    )
                    fragmentCustomerViewBinding?.edtviewTotPayAmount?.setText(
                        (caneCount * caneAmount.replace(
                            " Rs",
                            ""
                        ).trim().toInt()).plus(
                            fragmentCustomerViewBinding?.edtviewPrevBal?.text.toString().toInt()
                        ).toString()
                    )
                    Log.i(
                        TAG,
                        "onCheckedChanged: " + caneCount * caneAmount.replace(" Rs", "").trim()
                            .toInt()
                    )

                } else {
                    caneCount--

                    fragmentCustomerViewBinding?.edtviewTotAmount?.setText(
                        (caneCount * caneAmount.replace(
                            " Rs",
                            ""
                        ).trim().toInt()).toString()
                    )
                    fragmentCustomerViewBinding?.edtviewTotPayAmount?.setText(
                        (caneCount * caneAmount.replace(
                            " Rs",
                            ""
                        ).trim().toInt()).plus(
                            fragmentCustomerViewBinding?.edtviewPrevBal?.text.toString().toInt()
                        ).toString()
                    )
                    Log.i(
                        TAG,
                        "onCheckedChanged: " + caneCount * caneAmount.replace(" Rs", "").trim()
                            .toInt()
                    )

                }
            }
            R.id.checkbox_out_cane5 -> {
                if (isChecked) {
                    caneCount++
                    fragmentCustomerViewBinding?.edtviewTotAmount?.setText(
                        (caneCount * caneAmount.replace(
                            " Rs",
                            ""
                        ).trim().toInt()).toString()
                    )
                    fragmentCustomerViewBinding?.edtviewTotPayAmount?.setText(
                        (caneCount * caneAmount.replace(
                            " Rs",
                            ""
                        ).trim().toInt()).plus(
                            fragmentCustomerViewBinding?.edtviewPrevBal?.text.toString().toInt()
                        ).toString()
                    )
                    Log.i(
                        TAG,
                        "onCheckedChanged: " + caneCount * caneAmount.replace(" Rs", "").toInt()
                    )

                } else {
                    caneCount--
                    fragmentCustomerViewBinding?.edtviewTotAmount?.setText(
                        (caneCount * caneAmount.replace(
                            " Rs",
                            ""
                        ).trim().toInt()).toString()
                    )
                    fragmentCustomerViewBinding?.edtviewTotPayAmount?.setText(
                        (caneCount * caneAmount.replace(
                            " Rs",
                            ""
                        ).trim().toInt()).plus(
                            fragmentCustomerViewBinding?.edtviewPrevBal?.text.toString().toInt()
                        ).toString()
                    )
                    Log.i(

                        TAG,
                        "onCheckedChanged: " + caneCount * caneAmount.replace(" Rs", "").toInt()
                    )

                }
            }

        }

    }
}