package com.odhiambodevelopers.rxkotlin.UI.billviewpager

import android.R
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.MaterialDatePicker
import com.odhiambodevelopers.rxkotlin.MainActivity
import com.odhiambodevelopers.rxkotlin.database.AppDatabase
import com.odhiambodevelopers.rxkotlin.database.models.User
import com.odhiambodevelopers.rxkotlin.databinding.FragmentInfoBillBinding
import com.odhiambodevelopers.rxkotlin.repository.BillRepository
import com.odhiambodevelopers.rxkotlin.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InfoBillFragment : Fragment() {

    private var _binding: FragmentInfoBillBinding? = null
    private val binding get() = _binding!!
    private lateinit var datePicker: MaterialDatePicker<Long>
    private lateinit var dialog: AlertDialog.Builder
    private var categories: List<String>? = null
    private var users: List<User>? = null
    private val viewmodel: BillViewModel by viewModels {
        BillViewModelFactory(
            this,
            UserRepository(AppDatabase.getInstance(requireContext()).userDao),
            BillRepository(AppDatabase.getInstance(requireContext()).billDao)
        )
    }
    private lateinit var userRepo: UserRepository
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoBillBinding.inflate(layoutInflater)
        userRepo = UserRepository(AppDatabase.getInstance(requireContext()).userDao)

        viewmodel.categories.observe(viewLifecycleOwner) {
            categories = it ?: listOf("init failed")
            Log.d("InfoBill", "Size category in observer: ${categories?.size}")
        }
        viewmodel.users.observe( viewLifecycleOwner) {
            users = it
            Log.d("InfoBill", "Size users in observer: ${users?.size}")
        }

        //prepareDate()
        lifecycleScope.launch(Dispatchers.IO) {
            createDatePicker()
        }
        lifecycleScope.launch(Dispatchers.IO) {
            createAlertDialog()
        }
        setupOwner()
        setupCategory()
        setOnClickListeners()
            return binding.root
        }


    private fun prepareDate() {
        val result: Disposable = viewmodel.getUsersFlowable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list ->
                    Log.d("RxJava", "users list size: ${list.size}")
                },
                { error -> Log.e("RxJava", error.message.toString())}
            )
        compositeDisposable.add(result)

//        Flowable.fromArray(viewmodel.getUsers())
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnNext {
//                Log.d("RxJava", "User: ${it.size}")
//            }
//            .subscribe()
//        val observable = Observable.fromArray(userRepo.getUsers())
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//
//        observable.subscribe(object : Observer<List<User>> {
//            override fun onSubscribe(d: Disposable) {
//
//            }
//
//            override fun onNext(u: List<User>) {
//                Log.d("RxJava", u.toString())
//            }
//
//            override fun onError(e: Throwable) {
//                Log.d("RxJava", e.toString())
//            }
//
//            override fun onComplete() {
//                Log.d("RxJava", "Complete, list size ${intList.size}")
//            }
//
//        })

    }

    private fun setOnClickListeners() {
        binding.billDateTv.setOnClickListener { showDatePicker() }
        binding.saveBt.setOnClickListener { save() }
        binding.billDebtorsTv.setOnClickListener { showAlertDialog() }
    }

    private fun createAlertDialog() {
        //val items = arrayOf("Ewa", "Bartek", "Zosia")
        val users: Array<String> = userRepo.getUsers().map { it.userName ?: it.userEmail ?: "" }.toTypedArray()
        val selected = BooleanArray(users.size)

        dialog = AlertDialog.Builder(requireContext())
            .setTitle("Select debtors")
            .setMultiChoiceItems(users, selected) { _, which, isChecked ->
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
                selected.onEachIndexed { index, b -> if (b) { debtors += (users[index] + ", ") } }
                // String index out of range: -2
                if (debtors.length > 2) {
                    binding.billDebtorsTv.setText(debtors.substring(0, debtors.length-2))
                } else {
                    binding.billDebtorsTv.text.clear()
                }

                viewmodel.saveSelectedDebtors(selected)
            }
            .setNegativeButton("Cancel") { dialogInterface, i ->
                Log.d("RxJava", i.toString())
            }
            .setNeutralButton("Clear") { _, i ->
                Toast.makeText(requireContext(), "Neutral button clicked", Toast.LENGTH_SHORT).show()
                binding.billDebtorsTv.text.clear()
                selected.onEachIndexed { index, _ -> selected[index] = false }
            }
        //dialog.show()
    }

    private fun showAlertDialog() {
        dialog.show()
    }

    private fun save() {
        if (binding.billTitleEt.text.toString().isEmpty()) {
            binding.title.error = "Required"
        }
        if (binding.billCategoryTv.text.toString().isEmpty()) {
            binding.category.error = "Required"
        }
        if (binding.billOwnerTv.text.toString().isEmpty()) {
            binding.owner.error = "Required"
        }
        if (binding.billDateTv.text.toString().isEmpty()) {
            binding.date.error = "Required"
        }
        if (binding.billPrizeEt.text.toString().isEmpty()) {
            binding.prize.error = "Required"
        }
        if (binding.billDebtorsTv.text.toString().isEmpty()) {
            binding.debtors.error = "Required"
        }
        else {
            viewmodel.saveBill(
                binding.billTitleEt.text.toString(),
                binding.billCategoryTv.text.toString(),
                binding.billPrizeEt.text.toString()
            )
            Intent(requireContext(), MainActivity::class.java).also { startActivity(it) }
        }
    }

    private fun setupOwner() {
        lifecycleScope.launch(Dispatchers.IO) {
//            val users = viewmodel.getUsers().map { it.userName }
            val users = viewmodel.getUsers()
            Log.d("InfoBill", "Size in setup: ${users.size}")
            val adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, users)
            withContext(Dispatchers.Main) {
                (binding.owner.editText as? AutoCompleteTextView)?.setAdapter(adapter)
                (binding.owner.editText as? AutoCompleteTextView)?.setOnItemClickListener { adapterView, view, i, l ->
                    val selectedOwner = adapterView.getItemAtPosition(i) as User
                    Log.d("RxJava", i.toString())
                    Log.d("RxJava", selectedOwner.userName.toString())
                    viewmodel.setSelectedOwner(selectedOwner)
                }
            }
        }
    }

    private fun setupCategory() {
        lifecycleScope.launch(Dispatchers.IO) {
            val categories = viewmodel.getCategories()
            val adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, categories)
            withContext(Dispatchers.Main) {
                (binding.category.editText as? AutoCompleteTextView)?.setAdapter(adapter)
            }
        }
    }

    private fun createDatePicker() {
        Log.d("DATE_PICKER", "datepicker clicked")
        datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
            .also {
                it.addOnPositiveButtonClickListener {it ->
                    Log.d("DATE_PICKER", datePicker.headerText)
                    viewmodel.setSelectedDate(it)
                    binding.date.editText?.setText(datePicker.headerText)
                }
            }
    }

    private fun showDatePicker() {
        datePicker.show(childFragmentManager, "MATERIAL_DATE_PICKER")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}