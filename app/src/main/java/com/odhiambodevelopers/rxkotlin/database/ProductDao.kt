package com.odhiambodevelopers.rxkotlin.database

import androidx.room.*
import com.odhiambodevelopers.rxkotlin.database.models.Product
import com.odhiambodevelopers.rxkotlin.database.models.ProductDebtorCrossRef
import com.odhiambodevelopers.rxkotlin.database.models.ProductWithDebtors

@Dao
interface ProductDao {

    @Transaction
    @Query("SELECT * FROM Product")
    fun getProductsWithDebtors(): List<ProductWithDebtors>

    @Query("SELECT * FROM Product")
    fun getProducts(): List<Product>

    @Transaction
    @Query("SELECT * FROM Product WHERE correspondingBillId = :billId")
    fun getProductsWithDebtorsWithBillId(billId: Long): List<ProductWithDebtors>

    @Query("SELECT * FROM Product WHERE correspondingBillId = :billId")
    fun getProductsWithBillId(billId: Long): List<Product>

    @Insert
    fun insertProduct(vararg product:Product): List<Long>

    @Insert
    fun insertProductDebtorCrossRef(vararg productDebtorCrossRef: ProductDebtorCrossRef)

    @Update
    fun updateProduct(product: Product)

    @Delete
    fun deleteProduct(vararg product: Product)

    @Transaction
    fun deleteProductWithDebtors(product: Product) {
        deleteProduct(product)
        deleteProductDebtorCrossRef(product.productId)
    }

    @Query("DELETE FROM ProductDebtorCrossRef WHERE productId = :productId")
    fun deleteProductDebtorCrossRef(productId: Long)
}