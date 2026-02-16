package com.publicservices.pakistan.data.model

data class Service(
    val id: Int,
    val nameEn: String,
    val nameUr: String,
    val descriptionEn: String,
    val descriptionUr: String,
    val serviceNumber: String,
    val serviceType: ServiceType,
    val category: ServiceCategory,
    val isFavorite: Boolean,
    val iconName: String,
    val instructionsEn: String = "",
    val instructionsUr: String = ""
) {
    fun getName(language: String): String {
        return if (language == "ur") nameUr else nameEn
    }
    
    fun getDescription(language: String): String {
        return if (language == "ur") descriptionUr else descriptionEn
    }

    fun getInstructions(language: String): String {
        return if (language == "ur") instructionsUr else instructionsEn
    }
}

enum class ServiceType {
    SMS, CALL, BOTH, USSD
}
