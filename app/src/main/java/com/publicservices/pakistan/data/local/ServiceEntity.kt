package com.publicservices.pakistan.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "services")
data class ServiceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    @ColumnInfo(name = "name_en")
    val nameEn: String,
    
    @ColumnInfo(name = "name_ur")
    val nameUr: String,
    
    @ColumnInfo(name = "description_en")
    val descriptionEn: String,
    
    @ColumnInfo(name = "description_ur")
    val descriptionUr: String,
    
    @ColumnInfo(name = "service_number")
    val serviceNumber: String,
    
    @ColumnInfo(name = "service_type")
    val serviceType: String, // "SMS", "CALL", "BOTH", "USSD"
    
    @ColumnInfo(name = "category")
    val category: String,
    
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false,
    
    @ColumnInfo(name = "icon_name")
    val iconName: String,

    @ColumnInfo(name = "instructions_en")
    val instructionsEn: String = "",

    @ColumnInfo(name = "instructions_ur")
    val instructionsUr: String = ""
)
