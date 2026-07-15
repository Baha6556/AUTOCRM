package com.autocrm.domain.repository

import com.autocrm.domain.model.Client
import kotlinx.coroutines.flow.Flow

interface ClientRepository {
    fun getAllClients(): Flow<List<Client>>
    fun getClientById(uuid: String): Flow<Client?>
    suspend fun saveClient(client: Client)
    suspend fun deleteClient(uuid: String)
}
