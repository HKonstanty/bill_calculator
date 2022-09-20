package com.odhiambodevelopers.rxkotlin.UI.billviewpager

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.odhiambodevelopers.rxkotlin.database.models.Bill
import com.odhiambodevelopers.rxkotlin.database.models.Product
import com.odhiambodevelopers.rxkotlin.database.models.ProductWithDebtors
import com.odhiambodevelopers.rxkotlin.database.models.User
import com.odhiambodevelopers.rxkotlin.repository.BillRepository
import com.odhiambodevelopers.rxkotlin.repository.UserRepository
import io.reactivex.Flowable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import kotlin.math.roundToInt

class BillViewModel(private val userRepo: UserRepository? = null,
                    private val billRepo: BillRepository? = null
                    ) : ViewModel(), LifecycleObserver {

    private val TAG = "BillViewModel"
    private var _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() = _users
    private var _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>>
        get() = _categories

    private var _title = MutableLiveData<String>()
    val title: LiveData<String> get() = _title
    private var _selectedCategory = MutableLiveData<String>()
    val selectedCategory: LiveData<String> get() = _selectedCategory
    private var _selectedOwner = MutableLiveData<String>()
    val selectedOwner: LiveData<String> get() = _selectedOwner
    private var _selectedDate = MutableLiveData<String>()
    val selectedDate: LiveData<String> get() = _selectedDate
    private var _prize = MutableLiveData<String>()
    val prize: LiveData<String> get() = _prize
    private var _selectedDebtors = MutableLiveData<String>()
    val selectedDebtors: LiveData<String> get() = _selectedDebtors

    private var _products = MutableLiveData<MutableList<ProductWithDebtors>>()
    val productList: LiveData<MutableList<ProductWithDebtors>> get() = _products

    private var _selectedOwnerUser: User? = null
    private var _selectedDateLong: Long? = null

    fun setSelectedOwner(user: User) {
        _selectedOwnerUser = user
    }

    fun setSelectedDate(date: Long) {
        _selectedDateLong = date
    }

    fun saveSelectedDebtors(selectedDebtors: BooleanArray) {
        Log.d(TAG, selectedDebtors.size.toString())
    }

    fun addProduct(title: String, amount: Int, prize: Double, selectedDebtors: List<User>) {
       val oldList = _products.value
        val newProduct = ProductWithDebtors(
            Product(0, 0, title, ((prize * 100).roundToInt().toDouble() / 100), amount),
            selectedDebtors)
        if (oldList == null)
            _products.value = mutableListOf(newProduct)
        else
            _products.value?.add(newProduct)
        Log.i(TAG, "add new product ${productList.value?.size}")
    }

    fun saveBillWithProducts() {
        TODO()
    }

    fun saveBill(billTitle: String, category: String, prize: String) {
        Log.d(TAG, billTitle)
        Log.d(TAG, category)
        Log.d(TAG, prize)
        Log.d(TAG, _selectedOwnerUser?.userName!!)
        Log.d(TAG, _selectedDateLong.toString())
        viewModelScope.launch(Dispatchers.IO) {
            val bill = Bill(0,
            _selectedOwnerUser?.userId!!,
            billTitle,
            category,
            ((prize.toDouble() * 100).roundToInt().toDouble() / 100),
            _selectedDateLong!!)
            billRepo!!.insertBill(bill)
        }
    }

    fun getUsers(): List<User> {
        return userRepo!!.getUsers()
    }

    fun getCategories() = listOf("Attraction", "Food", "Night out", "For home")

    fun getUsersFlowable(): Flowable<List<User>> {
        return userRepo!!.getUsersFlowable().switchMap { data -> Flowable.just(data) }
    }

    fun deleteProduct(productWithDebtors: ProductWithDebtors) {
        productList.value?.remove(productWithDebtors)
        Log.i(TAG, "Delete Product with debtors")
    }
}

class BillViewModelFactory(owner: SavedStateRegistryOwner,
                           private val dependency1: UserRepository,
                           private val dependency2: BillRepository,
                           defaultArgs: Bundle? = null) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return BillViewModel(dependency1, dependency2) as T
    }
}