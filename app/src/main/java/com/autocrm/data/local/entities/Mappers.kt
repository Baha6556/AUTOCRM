package com.autocrm.data.local.entities

import com.autocrm.domain.model.*

fun CarEntity.toDomain() = Car(
    uuid = uuid, serverId = serverId, make = make, model = model, year = year,
    vin = vin, mileage = mileage, countryOfOrigin = countryOfOrigin, color = color,
    engineVolume = engineVolume, transmission = transmission,
    purchasePrice = purchasePrice, estimatedSalePrice = estimatedSalePrice,
    salePrice = salePrice, currency = currency, status = status,
    purchaseDate = purchaseDate, saleDate = saleDate, arrivalDate = arrivalDate,
    notes = notes, supplierUuid = supplierUuid, clientUuid = clientUuid, deliveryStatus = deliveryStatus,
    createdAt = createdAt, updatedAt = updatedAt, syncStatus = syncStatus, isDeleted = isDeleted, version = version
)

fun Car.toEntity() = CarEntity(
    uuid = uuid, serverId = serverId, make = make, model = model, year = year,
    vin = vin, mileage = mileage, countryOfOrigin = countryOfOrigin, color = color,
    engineVolume = engineVolume, transmission = transmission,
    purchasePrice = purchasePrice, estimatedSalePrice = estimatedSalePrice,
    salePrice = salePrice, currency = currency, status = status,
    purchaseDate = purchaseDate, saleDate = saleDate, arrivalDate = arrivalDate,
    notes = notes, supplierUuid = supplierUuid, clientUuid = clientUuid, deliveryStatus = deliveryStatus,
    createdAt = createdAt, updatedAt = updatedAt, syncStatus = syncStatus, isDeleted = isDeleted, version = version
)

fun CarPhotoEntity.toDomain() = CarPhoto(
    uuid = uuid, serverId = serverId, carUuid = carUuid, filePath = filePath,
    remoteUrl = remoteUrl, sortOrder = sortOrder, isPrimary = isPrimary,
    createdAt = createdAt, updatedAt = updatedAt, syncStatus = syncStatus, isDeleted = isDeleted, version = version
)

fun CarPhoto.toEntity() = CarPhotoEntity(
    uuid = uuid, serverId = serverId, carUuid = carUuid, filePath = filePath,
    remoteUrl = remoteUrl, sortOrder = sortOrder, isPrimary = isPrimary,
    createdAt = createdAt, updatedAt = updatedAt, syncStatus = syncStatus, isDeleted = isDeleted, version = version
)

fun ExpenseEntity.toDomain() = Expense(
    uuid = uuid, serverId = serverId, carUuid = carUuid, category = category,
    amount = amount, currency = currency, description = description, date = date,
    createdAt = createdAt, updatedAt = updatedAt, syncStatus = syncStatus, isDeleted = isDeleted, version = version
)

fun Expense.toEntity() = ExpenseEntity(
    uuid = uuid, serverId = serverId, carUuid = carUuid, category = category,
    amount = amount, currency = currency, description = description, date = date,
    createdAt = createdAt, updatedAt = updatedAt, syncStatus = syncStatus, isDeleted = isDeleted, version = version
)

fun SupplierEntity.toDomain() = Supplier(
    uuid = uuid, companyName = companyName, contactPerson = contactPerson,
    phone = phone, telegram = telegram, whatsapp = whatsapp, country = country,
    city = city, address = address, notes = notes, rating = rating,
    serverId = serverId, createdAt = createdAt, updatedAt = updatedAt,
    isDeleted = isDeleted, syncStatus = syncStatus, version = version
)

fun Supplier.toEntity() = SupplierEntity(
    uuid = uuid, companyName = companyName, contactPerson = contactPerson,
    phone = phone, telegram = telegram, whatsapp = whatsapp, country = country,
    city = city, address = address, notes = notes, rating = rating,
    serverId = serverId, createdAt = createdAt, updatedAt = updatedAt,
    isDeleted = isDeleted, syncStatus = syncStatus, version = version
)

fun ClientEntity.toDomain() = Client(
    uuid = uuid, fullName = fullName, phone = phone, telegram = telegram,
    whatsapp = whatsapp, city = city, preferredBrands = preferredBrands, notes = notes,
    serverId = serverId, createdAt = createdAt, updatedAt = updatedAt,
    isDeleted = isDeleted, syncStatus = syncStatus, version = version
)

fun Client.toEntity() = ClientEntity(
    uuid = uuid, fullName = fullName, phone = phone, telegram = telegram,
    whatsapp = whatsapp, city = city, preferredBrands = preferredBrands, notes = notes,
    serverId = serverId, createdAt = createdAt, updatedAt = updatedAt,
    isDeleted = isDeleted, syncStatus = syncStatus, version = version
)

fun DeliveryEventEntity.toDomain() = DeliveryEvent(
    uuid = uuid, carUuid = carUuid, status = status, date = date,
    location = location, port = port, carrierName = carrierName, containerNumber = containerNumber,
    notes = notes, estimatedDeliveryDate = estimatedDeliveryDate, actualDeliveryDate = actualDeliveryDate,
    serverId = serverId, createdAt = createdAt, updatedAt = updatedAt,
    isDeleted = isDeleted, syncStatus = syncStatus, version = version
)

fun DeliveryEvent.toEntity() = DeliveryEventEntity(
    uuid = uuid, carUuid = carUuid, status = status, date = date,
    location = location, port = port, carrierName = carrierName, containerNumber = containerNumber,
    notes = notes, estimatedDeliveryDate = estimatedDeliveryDate, actualDeliveryDate = actualDeliveryDate,
    serverId = serverId, createdAt = createdAt, updatedAt = updatedAt,
    isDeleted = isDeleted, syncStatus = syncStatus, version = version
)

fun DeliveryPhotoEntity.toDomain() = DeliveryPhoto(
    uuid = uuid, eventUuid = eventUuid, filePath = filePath, remoteUrl = remoteUrl,
    serverId = serverId, createdAt = createdAt, updatedAt = updatedAt,
    isDeleted = isDeleted, syncStatus = syncStatus, version = version
)

fun DeliveryPhoto.toEntity() = DeliveryPhotoEntity(
    uuid = uuid, eventUuid = eventUuid, filePath = filePath, remoteUrl = remoteUrl,
    serverId = serverId, createdAt = createdAt, updatedAt = updatedAt,
    isDeleted = isDeleted, syncStatus = syncStatus, version = version
)

fun CarWithDetailsEntity.toDomain() = CarWithDetails(
    car      = car.toDomain(),
    expenses = expenses.map { it.toDomain() },
    photos   = photos.map { it.toDomain() },
    deliveryEvents = deliveryEvents.map { it.event.toDomain() },
    supplier = supplier?.toDomain(),
    client   = client?.toDomain()
)
