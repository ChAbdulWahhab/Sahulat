package com.publicservices.pakistan.data.repository

import com.publicservices.pakistan.data.local.ServiceDao
import com.publicservices.pakistan.data.local.ServiceEntity
import com.publicservices.pakistan.data.model.Service
import com.publicservices.pakistan.data.model.ServiceCategory
import com.publicservices.pakistan.data.model.ServiceType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ServiceRepository(private val serviceDao: ServiceDao) {
    
    fun getAllServices(): Flow<List<Service>> {
        return serviceDao.getAllServices().map { entities ->
            entities.map { it.toService() }
        }
    }
    
    fun getServicesByCategory(category: ServiceCategory): Flow<List<Service>> {
        return if (category == ServiceCategory.ALL) {
            getAllServices()
        } else {
            serviceDao.getServicesByCategory(category.name).map { entities ->
                entities.map { it.toService() }
            }
        }
    }
    
    fun getFavoriteServices(): Flow<List<Service>> {
        return serviceDao.getFavoriteServices().map { entities ->
            entities.map { it.toService() }
        }
    }
    
    fun searchServices(query: String): Flow<List<Service>> {
        return serviceDao.searchServices(query).map { entities ->
            entities.map { it.toService() }
        }
    }
    
    suspend fun toggleFavorite(serviceId: Int, isFavorite: Boolean) {
        serviceDao.toggleFavorite(serviceId, isFavorite)
    }
    
    private fun ServiceEntity.toService(): Service {
        return Service(
            id = id,
            nameEn = nameEn,
            nameUr = nameUr,
            descriptionEn = descriptionEn,
            descriptionUr = descriptionUr,
            serviceNumber = serviceNumber,
            serviceType = ServiceType.valueOf(serviceType),
            category = ServiceCategory.valueOf(category),
            isFavorite = isFavorite,
            iconName = iconName,
            instructionsEn = instructionsEn,
            instructionsUr = instructionsUr
        )
    }
}
