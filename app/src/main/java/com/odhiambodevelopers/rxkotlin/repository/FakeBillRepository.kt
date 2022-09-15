package com.odhiambodevelopers.rxkotlin.repository

import android.os.Build
import com.odhiambodevelopers.rxkotlin.database.models.Bill
import com.odhiambodevelopers.rxkotlin.database.models.Product
import com.odhiambodevelopers.rxkotlin.database.models.ProductWithDebtors
import com.odhiambodevelopers.rxkotlin.database.models.User
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random.Default.nextInt

class FakeBillRepository {

    private val things = listOf("Socks", "Chips", "Juice", "Bread", "Water", "Pepper", "Apple", "Gum", "Clothes")
    private val titles = listOf("Party", "Night out", "Trip", "Culture", "Books", "Food", "Birthday", "Barbecue")
    private val names = listOf("Marek", "Zosia", "Artur", "Michał", "Adam", "Piotr", "Julka", "Ewa", "Ania")
    private val surnames = listOf("Zalewski", "Gorka", "Polec", "Zakoski", "Malecki", "Poplawski", "Zubecka", "Grosicki")
    private val prizes = listOf(12.97, 10.01, 6.56, 1.19, 3.14, 54.12, 6.99, 17.39, 21.09, 39.00)
    private val categories = listOf("Food", "Party", "Gift", "Invest", "Bills", "For home")
    private val emails = listOf("ewa.dot@abc.com", "jan.kow@123.pl", "ania.grow!@.edu.pl", "test@gmail.pl", "unia.pw@wp.pl", "jarmark.gdn@com")
//    private val users = listOf(
//            User(0, names.random(), surnames.random(), emails.random()),
//            User(1, names.random(), surnames.random(), emails.random()),
//            User(2, names.random(), surnames.random(), emails.random()),
//            User(3, names.random(), surnames.random(), emails.random()),
//            User(4, names.random(), surnames.random(), emails.random()),
//            User(5, names.random(), surnames.random(), emails.random()),
//            User(6, names.random(), surnames.random(), emails.random())
//    )
    private lateinit var users: List<User>
    private lateinit var bills: List<Bill>
    private lateinit var products: List<Product>

    init {
        initUsers()
        initBills()
        initProducts()
    }

    private fun initProducts() {
        products = getProducts()
    }

    private fun initBills() {
        bills = getBills()
    }

    private fun initUsers() {
        users = getUsers()
    }

    fun getBills(): List<Bill> {
        val bills = ArrayList<Bill>()
        for (i in 1..7) {
            bills.add(getBill(i.toLong()))
        }
        return bills
    }

    fun getProductWithDebtors(): ProductWithDebtors {
        return ProductWithDebtors(products.random(), listOf(users.random(), users.random(), users.random()))
    }

    fun getListProductsWithDebtora(): List<ProductWithDebtors> {
        val products = ArrayList<ProductWithDebtors>()
        for(i in 1..5) {
            products.add(getProductWithDebtors())
        }
        return products
    }

    fun getBill(billId: Long = 0): Bill {
        return Bill(billId,
            ownerId = users.random().userId,
            billName = titles.random(),
            category = categories.random(),
            prize = prizes.random(),
            date = getDateInLong())
    }

    fun getProduct(productId: Long = 0): Product {
        return Product(
            productId = productId,
            correspondingBillId = bills.random().billId,
            productName = things.random(),
            productPrize = prizes.random(),
            productAmount = (1..10).random()
        )
    }

    fun getProducts(): List<Product> {
        val products = ArrayList<Product>()
        for (i in 1..4) {
            products.add(getProduct(i.toLong()))
        }
        return products
    }

    fun getUser(userId: Long = 0): User {
        return User(
            userId = userId,
            userName = names.random(),
            userSurname = surnames.random(),
            userEmail = emails.random())
    }

    fun getUsers(): List<User> {
        val user = ArrayList<User>()
        for(i in 0..10) {
            user.add(getUser(i.toLong()))
        }
        return user
    }

    fun getDateInLong(): Long {
        return Calendar.getInstance().timeInMillis
    }
}