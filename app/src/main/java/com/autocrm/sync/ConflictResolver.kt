package com.autocrm.sync

import com.autocrm.domain.model.*
import javax.inject.Inject

interface ConflictResolver {
    fun resolveCarConflict(local: Car?, remote: Car): Car
    fun resolveExpenseConflict(local: Expense?, remote: Expense): Expense
    fun resolveDeliveryEventConflict(local: DeliveryEvent?, remote: DeliveryEvent): DeliveryEvent
    fun resolveSupplierConflict(local: Supplier?, remote: Supplier): Supplier
    fun resolveClientConflict(local: Client?, remote: Client): Client
}

class EntityConflictResolver @Inject constructor() : ConflictResolver {

    /**
     * Car: Last-Write-Wins (LWW) based on version and updatedAt check.
     */
    override fun resolveCarConflict(local: Car?, remote: Car): Car {
        if (local == null) return remote
        
        // If remote version is strictly greater, it wins.
        if (remote.version > local.version) return remote
        
        // If versions are same, fallback to updatedAt timestamp.
        if (remote.version == local.version && remote.updatedAt > local.updatedAt) {
            return remote
        }
        
        return local
    }

    /**
     * Expense: Append-only model. Never overwrite, only add new.
     * We assume if local exists, it's the exact same record or local takes precedence.
     */
    override fun resolveExpenseConflict(local: Expense?, remote: Expense): Expense {
        if (local == null) return remote
        return local // Local is preserved in append-only model unless it's a completely new UUID
    }

    /**
     * DeliveryEvent: Event-sourcing merge.
     * Events are immutable conceptually.
     */
    override fun resolveDeliveryEventConflict(local: DeliveryEvent?, remote: DeliveryEvent): DeliveryEvent {
        if (local == null) return remote
        return local
    }

    /**
     * Supplier: Field-level merge strategy (placeholder for complex logic).
     * For now, using LWW.
     */
    override fun resolveSupplierConflict(local: Supplier?, remote: Supplier): Supplier {
        if (local == null) return remote
        return if (remote.updatedAt > local.updatedAt) remote else local
    }

    /**
     * Client: Field-level merge strategy.
     * For now, using LWW.
     */
    override fun resolveClientConflict(local: Client?, remote: Client): Client {
        if (local == null) return remote
        return if (remote.updatedAt > local.updatedAt) remote else local
    }
}
