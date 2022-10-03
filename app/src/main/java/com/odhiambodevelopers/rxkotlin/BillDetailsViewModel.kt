package com.odhiambodevelopers.rxkotlin

import android.os.Bundle
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableChar
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.odhiambodevelopers.rxkotlin.UI.billviewpager.BillViewModel
import com.odhiambodevelopers.rxkotlin.database.models.Bill
import com.odhiambodevelopers.rxkotlin.database.models.BillWithOwnerAndDebtors
import com.odhiambodevelopers.rxkotlin.database.models.ProductWithDebtors
import com.odhiambodevelopers.rxkotlin.database.models.User
import com.odhiambodevelopers.rxkotlin.repository.BillRepository
import com.odhiambodevelopers.rxkotlin.repository.ProductRepository
import com.odhiambodevelopers.rxkotlin.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

class BillDetailsViewModel(private val billRepository: BillRepository,
                           private val productRepository: ProductRepository): ViewModel() {
    val isVisibleGeneralInfo = ObservableBoolean(false)
    val isVisibleBillPicture = ObservableBoolean(false)
    val isVisibleProductList = ObservableBoolean(false)
    val billName = ObservableField("")
    val billOwner = ObservableField("")
    val billDate = ObservableField("")
    val billDebtors = ObservableField("")
    val billPrize = ObservableField("")
    private var billId: Long = 0
    private lateinit var bill: BillWithOwnerAndDebtors
    private val _productList = MutableLiveData<List<ProductWithDebtors>>()
    val productList get() = _productList

    fun loadBillInfo(billId: Long) {
        this.billId = billId
        viewModelScope.launch(Dispatchers.IO) {
            bill = billRepository.getBillByBillIdWithOwnerAndDebtors(billId)
            billName.set(bill.bill.billName)
            billDate.set(getDateFromLong(bill.bill.date))
            billOwner.set(bill.user.userName)
            billDebtors.set(getDebtors(bill.debtors))
            billPrize.set(bill.bill.prize.toString() + " zł")
        }
    }

    fun loadBillProducts(billId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val productsWithDebtors: List<ProductWithDebtors> = productRepository.getProductsWithDebtorsWithBillId(billId)
            _productList.postValue(productsWithDebtors)
        }
    }

    private fun getDateFromLong(dateInLong: Long): String {
        val date = Date(dateInLong)
        val format = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)
        return format.format(date)
    }

    private fun getDebtors(users: List<User>): String {
        return users.map { it.userName }.joinToString(", ")
//        val debtors = ""
//        users.forEach { debtors + it.userName + ", " }
//        return debtors.substringBeforeLast(debtors, ",")
    }

    fun generalInfoVisibleChange() {
        if (isVisibleGeneralInfo.get())
            isVisibleGeneralInfo.set(false)
        else
            isVisibleGeneralInfo.set(true)
    }

    fun billPictureVisibleChange() {
        if (isVisibleBillPicture.get())
            isVisibleBillPicture.set(false)
        else
            isVisibleBillPicture.set(true)
    }

    fun productListVisibleChange() {
        if (isVisibleProductList.get())
            isVisibleProductList.set(false)
        else
            isVisibleProductList.set(true)
    }

    companion object {
        //    private val isVisibleGeneralInfo: Boolean = false
        //    private val isVisibleBillPicture: Boolean = false
        //    private val isVisibleProductList: Boolean = false
            const val TAG: String = "BillDetailsViewModel"
    }
}

class BillDetailsViewModelFactory(owner: SavedStateRegistryOwner,
                           private val billRepo: BillRepository,
                           private val productRepo: ProductRepository,
                           defaultArgs: Bundle? = null) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return BillDetailsViewModel(billRepo, productRepo) as T
    }
}