package com.odhiambodevelopers.rxkotlin.repository

import com.odhiambodevelopers.rxkotlin.database.ProductDao
import com.odhiambodevelopers.rxkotlin.database.models.Product
import com.odhiambodevelopers.rxkotlin.database.models.ProductDebtorCrossRef
import com.odhiambodevelopers.rxkotlin.database.models.ProductWithDebtors
import com.odhiambodevelopers.rxkotlin.database.models.User

class ProductRepository(private val productDao: ProductDao) {

    suspend fun getProductsWithBillId(billId: Long): List<Product> {
        return productDao.getProductsWithBillId(billId)
    }

    suspend fun getProductsWithDebtorsWithBillId(billId: Long): List<ProductWithDebtors> {
        return productDao.getProductsWithDebtorsWithBillId(billId)
    }

    suspend fun insertProduct(products: List<Product>): List<Long> {
        return productDao.insertProduct(*products.toTypedArray())
    }

    suspend fun insertProductDebtor(productId: Long, debtorId: Long) {
        productDao.insertProductDebtorCrossRef(ProductDebtorCrossRef(productId, debtorId))
    }

    suspend fun insertProductWithDebtors(product: Product, debtors: List<User>) {
        val productId = insertProduct(listOf(product))[0]
        debtors.forEach {
            insertProductDebtor(productId, it.userId)
        }
    }

    suspend fun insertProductsWithDebtors(products: List<ProductWithDebtors>) {
        products.forEach {
            insertProductWithDebtors(it.product, it.debtors)
        }
    }

    suspend fun deleteProduct(product: Product, withDebtors: Boolean = true) {
        if (withDebtors)
            productDao.deleteProductWithDebtors(product)
        else
            productDao.deleteProduct(product)
    }
}