package com.odhiambodevelopers.rxkotlin.UI.billviewpager

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.*
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.odhiambodevelopers.rxkotlin.R
import com.odhiambodevelopers.rxkotlin.database.AppDatabase
import com.odhiambodevelopers.rxkotlin.database.models.User
import com.odhiambodevelopers.rxkotlin.databinding.FragmentAddProductBinding
import com.odhiambodevelopers.rxkotlin.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddProductFragment : Fragment() {

    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!
    private lateinit var debtorsDialog: AlertDialog.Builder
    private val viewmodel: BillViewModel by activityViewModels()
    private lateinit var selectedDebtors: ArrayList<User>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddProductBinding.inflate(layoutInflater)


        setAmountAdapter()
        createDebtorsAlertDialog()
        binding.productDebtorsTv.setOnClickListener { showDebtorsDialog() }
        binding.addBt.setOnClickListener { saveProduct() }
        return binding.root
    }

    private fun saveProduct() {
        if (binding.productNameEt.text.toString().isEmpty()) {
            binding.productNameEt.error = "Required"
        }
        if (binding.productAmountTv.text.toString().isEmpty()) {
            binding.amount.error = "Required"
        }
        if (binding.productPrizeEt.text.toString().isEmpty()) {
            binding.prize.error = "Required"
        }
        if (binding.productDebtorsTv.text.toString().isEmpty()) {
            binding.debtors.error = "Required"
        }
        else {
            viewmodel.addProduct(title = binding.productNameEt.text.toString(),
                amount = binding.productAmountTv.text.toString().toInt(),
                prize = binding.productPrizeEt.text.toString().toDouble(),
                selectedDebtors = selectedDebtors
            )
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<ProductsListBillFragment>(R.id.fragment_container_view)
            }
        }
    }

    private fun showDebtorsDialog() {
        debtorsDialog.show()
    }

    private fun setAmountAdapter() {
        lifecycleScope.launch(Dispatchers.IO) {
            val amounts = (1..20).toList()
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, amounts)
            withContext(Dispatchers.Main) {
                (binding.amount.editText as? AutoCompleteTextView)?.setAdapter(adapter)
            }
        }
    }

    private fun createDebtorsAlertDialog() {
        lifecycleScope.launch(Dispatchers.IO) {
            val userRepo = UserRepository(AppDatabase.getInstance(requireContext()).userDao)
            val users = userRepo.getUsers()
            val usersNames: Array<String> = users.map { it.userName ?: it.userEmail ?: "" }.toTypedArray()
            val selected = BooleanArray(usersNames.size)

            debtorsDialog = AlertDialog.Builder(requireContext())
                .setTitle("Select debtors")
                .setMultiChoiceItems(usersNames, selected) { _, which, isChecked ->
                    if (isChecked) {
                        selected[which] = true
                    }
                    if (!isChecked) {
                        selected[which] = false
                    }
                }
                .setPositiveButton("OK") { _, i ->
                    Toast.makeText(requireContext(), "Positive button clicked", Toast.LENGTH_SHORT).show()
                    var debtors = ""
                    selectedDebtors = ArrayList<User>()
                    selected.onEachIndexed { index, b -> if (b) {
                            debtors += (usersNames[index] + ", ")
                            selectedDebtors.add(users[index])
                        }
                    }
                    binding.productDebtorsTv.setText(debtors.substring(0, debtors.length-2))
                }
                .setNegativeButton("Cancel") { dialogInterface, i ->
                    Log.d("RxJava", i.toString())
                }
                .setNeutralButton("Clear") { _, i ->
                    Toast.makeText(requireContext(), "Neutral button clicked", Toast.LENGTH_SHORT).show()
                    binding.productDebtorsTv.text.clear()
                    selected.onEachIndexed { index, _ -> selected[index] = false }
                    selectedDebtors.clear()
                }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val backCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                parentFragmentManager.popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backCallback)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}