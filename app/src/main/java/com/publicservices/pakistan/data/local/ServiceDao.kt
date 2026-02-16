package com.publicservices.pakistan.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceDao {
    
    @Insert
    suspend fun insertService(service: ServiceEntity)
    
    @Query("SELECT * FROM services ORDER BY name_en ASC")
    fun getAllServices(): Flow<List<ServiceEntity>>
    
    @Query("SELECT * FROM services WHERE category = :category ORDER BY name_en ASC")
    fun getServicesByCategory(category: String): Flow<List<ServiceEntity>>
    
    @Query("SELECT * FROM services WHERE is_favorite = 1 ORDER BY name_en ASC")
    fun getFavoriteServices(): Flow<List<ServiceEntity>>
    
    @Query("""
        SELECT * FROM services 
        WHERE name_en LIKE '%' || :query || '%' 
        OR name_ur LIKE '%' || :query || '%' 
        OR description_en LIKE '%' || :query || '%'
        OR description_ur LIKE '%' || :query || '%'
        OR service_number LIKE '%' || :query || '%'
        ORDER BY name_en ASC
    """)
    fun searchServices(query: String): Flow<List<ServiceEntity>>
    
    @Update
    suspend fun updateService(service: ServiceEntity)
    
    @Query("UPDATE services SET is_favorite = :isFavorite WHERE id = :serviceId")
    suspend fun toggleFavorite(serviceId: Int, isFavorite: Boolean)
}
