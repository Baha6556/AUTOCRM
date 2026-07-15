package com.autocrm.domain.repository

import com.autocrm.domain.model.Supplier
import kotlinx.coroutines.flow.Flow

interface SupplierRepository {
    fun getAllSuppliers(): Flow<List<Supplier>>
    fun getSupplierById(uuid: String): Flow<Supplier?>
    suspend fun saveSupplier(supplier: Supplier)
    suspend fun deleteSupplier(uuid: String)
}
