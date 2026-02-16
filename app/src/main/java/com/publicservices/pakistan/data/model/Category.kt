package com.publicservices.pakistan.data.model

enum class ServiceCategory(val nameEn: String, val nameUr: String) {
    ALL("All Services", "تمام خدمات"),
    IDENTITY("Identity Services", "شناخت کی خدمات"),
    VEHICLE("Vehicle Services", "گاڑی کی خدمات"),
    WELFARE("Welfare Programs", "فلاحی پروگرام"),
    EMERGENCY("Emergency Helplines", "ایمرجنسی ہیلپ لائنز"),
    CYBER("Cyber & Security", "سائبر اور سیکیورٹی"),
    AMBULANCE("Ambulance Services", "ایمبولینس خدمات"),
    WOMEN_CHILD("Women & Child Support", "خواتین اور بچوں کی مدد"),
    UTILITY("Utility Services", "یوٹیلٹی خدمات");
    
    fun getName(language: String): String {
        return if (language == "ur") nameUr else nameEn
    }
}
