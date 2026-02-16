package com.publicservices.pakistan.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [ServiceEntity::class], version = 1, exportSchema = false)
abstract class ServiceDatabase : RoomDatabase() {
    
    abstract fun serviceDao(): ServiceDao
    
    companion object {
        @Volatile
        private var INSTANCE: ServiceDatabase? = null
        
        fun getDatabase(context: Context): ServiceDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ServiceDatabase::class.java,
                    "service_database"
                )
                    .addCallback(DatabaseCallback(context))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
        
        private class DatabaseCallback(private val context: Context) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        populateDatabase(database.serviceDao())
                    }
                }
            }
        }
        
        suspend fun populateDatabase(serviceDao: ServiceDao) {
            val services = listOf(
                // IDENTITY SERVICES
                ServiceEntity(0, "CNIC Verification", "شناختی کارڈ کی تصدیق", "Verify CNIC details", "شناختی کارڈ کی تفصیلات چیک کریں", "8000", "SMS", "IDENTITY", false, "fingerprint", "Type your CNIC number and send", "اپنا شناختی کارڈ نمبر ٹائپ کریں اور بھیجیں"),
                ServiceEntity(0, "Registered SIMs", "رجسٹرڈ سمز", "Check SIMs on your CNIC", "اپنے نام پر سمز چیک کریں", "668", "SMS", "IDENTITY", false, "sim_card", "Type your CNIC number and send", "اپنا شناختی کارڈ نمبر ٹائپ کریں اور بھیجیں"),
                ServiceEntity(0, "Vote Info & Polling Station", "ووٹ کی معلومات", "Find your polling station", "اپنا پولنگ اسٹیشن تلاش کریں", "8300", "SMS", "IDENTITY", false, "how_to_vote", "Type your CNIC number and send", "اپنا شناختی کارڈ نمبر ٹائپ کریں اور بھیجیں"),
                ServiceEntity(0, "Family Tree", "خاندانی درخت", "Check family tree", "خاندانی درخت دیکھیں", "8001", "SMS", "IDENTITY", false, "family_restroom", "Type your CNIC number and send", "اپنا شناختی کارڈ نمبر ٹائپ کریں اور بھیجیں"),
                ServiceEntity(0, "CNIC Expiry", "شناختی کارڈ کی میعاد", "Check CNIC expiry date", "میعاد ختم ہونے کی تاریخ", "8008", "SMS", "IDENTITY", false, "event_busy", "Type your CNIC number and send", "اپنا شناختی کارڈ نمبر ٹائپ کریں اور بھیجیں"),
                ServiceEntity(0, "Nearest NADRA Center", "قریبی نادرا مرکز", "Find nearest NADRA office", "قریبی نادرا دفتر تلاش کریں", "8005", "SMS", "IDENTITY", false, "location_on", "Type your City name and send", "اپنے شہر کا نام ٹائپ کریں اور بھیجیں"),
                ServiceEntity(0, "Passport Status", "پاسپورٹ کی حیثیت", "Check passport status", "پاسپورٹ کی حیثیت دیکھیں", "9988", "SMS", "IDENTITY", false, "passport", "Type your 11-digit tracking ID and send", "اپنا 11 ہندسوں کا ٹریکنگ آئی ڈی ٹائپ کریں اور بھیجیں"),
                ServiceEntity(0, "Phone Ownership (MNP)", "فون کی ملکیت", "Check phone number ownership", "نمبر کی ملکیت چیک کریں", "667", "SMS", "IDENTITY", false, "phone_android", "Type 'MNP' and send", "'MNP' ٹائپ کریں اور بھیجیں"),
                ServiceEntity(0, "PTA Mobile Verification", "پی ٹی اے موبائل تصدیق", "Verify mobile registration", "موبائل رجسٹریشن کی تصدیق", "8484", "SMS", "IDENTITY", false, "verified", "Type your IMEI number and send", "اپنا آئی ایم ای آئی نمبر ٹائپ کریں اور بھیجیں"),
                ServiceEntity(0, "IMEI Check", "آئی ایم ای آئی چیک", "Check device IMEI", "ڈیوائس کا آئی ایم ای آئی چیک کریں", "*#06#", "USSD", "IDENTITY", false, "smartphone", "Dial this code on your phone", "اپنے فون پر یہ کوڈ ڈائل کریں"),
                
                // VEHICLE SERVICES
                ServiceEntity(0, "Punjab Car Registration", "پنجاب گاڑی رجسٹریشن", "Check vehicle registration (Punjab)", "گاڑی کی رجسٹریشن (پنجاب)", "8149", "SMS", "VEHICLE", false, "directions_car", "Type Registration number (e.g. ABC 123) and send", "گاڑی کا نمبر ٹائپ کریں اور بھیجیں (مثال: ABC 123)"),
                ServiceEntity(0, "Islamabad Car Registration", "اسلام آباد گاڑی رجسٹریشن", "Check vehicle registration (ICT)", "گاڑی کی رجسٹریشن (اسلام آباد)", "8521", "SMS", "VEHICLE", false, "directions_car", "Type Registration number (e.g. IDL 1234) and send", "گاڑی کا نمبر ٹائپ کریں اور بھیجیں (مثال: IDL 1234)"),
                ServiceEntity(0, "Punjab Driving License", "پنجاب ڈرائیونگ لائسنس", "Check driving license (Punjab)", "ڈرائیونگ لائسنس (پنجاب)", "8147", "SMS", "VEHICLE", false, "badge", "Type your CNIC number (without dashes) and send", "اپنا شناختی کارڈ نمبر (بغیر ڈیش کے) ٹائپ کریں اور بھیجیں"),
                ServiceEntity(0, "Sindh Driving License", "سندھ ڈرائیونگ لائسنس", "Check driving license (Sindh)", "ڈرائیونگ لائسنس (سندھ)", "6040", "SMS", "VEHICLE", false, "badge", "Type your CNIC number and send", "اپنا شناختی کارڈ نمبر ٹائپ کریں اور بھیجیں"),
                
                // WELFARE PROGRAMS
                ServiceEntity(0, "Benazir Income Support", "بے نظیر انکم سپورٹ", "Check BISP eligibility", "بی آئی ایس پی کی اہلیت چیک کریں", "8171", "SMS", "WELFARE", false, "account_balance_wallet", "Type your CNIC number and send", "اپنا شناختی کارڈ نمبر ٹائپ کریں اور بھیجیں"),
                ServiceEntity(0, "Sehat Card", "صحت کارڈ", "Health card information", "صحت کارڈ کی معلومات", "8500", "SMS", "WELFARE", false, "medical_services", "Type your CNIC number and send", "اپنا شناختی کارڈ نمبر ٹائپ کریں اور بھیجیں"),
                ServiceEntity(0, "Muft Rashan Program", "مفت راشن پروگرام", "Free ration program info", "مفت راشن پروگرام", "8123", "SMS", "WELFARE", false, "restaurant", "Type your CNIC number and send", "اپنا شناختی کارڈ نمبر ٹائپ کریں اور بھیجیں"),
                ServiceEntity(0, "PM Youth Program", "وزیراعظم نوجوان پروگرام", "Youth program information", "نوجوانوں کا پروگرام", "8900", "SMS", "WELFARE", false, "school", "Type your CNIC number and send", "اپنا شناختی کارڈ نمبر ٹائپ کریں اور بھیجیں"),
                
                // NATIONAL EMERGENCY
                ServiceEntity(0, "Pakistan Emergency (911)", "پاکستان ایمرجنسی", "Unified emergency helpline", "متحدہ ایمرجنسی ہیلپ لائن", "911", "CALL", "EMERGENCY", false, "emergency", "Call for immediate help", "فوری مدد کے لیے کال کریں"),
                ServiceEntity(0, "Police Emergency", "پولیس ایمرجنسی", "Police assistance", "پولیس کی مدد", "15", "CALL", "EMERGENCY", false, "local_police", "Call for police help", "پولیس کی مدد کے لیے کال کریں"),
                ServiceEntity(0, "Rescue 1122", "ایمبولینس", "Emergency medical services", "ایمرجنسی طبی خدمات", "1122", "CALL", "EMERGENCY", false, "local_hospital", "Call for rescue/ambulance", "ریسکیو کے لیے کال کریں"),
                ServiceEntity(0, "Fire Brigade", "فائر بریگیڈ", "Fire emergency", "آگ کی ایمرجنسی", "16", "CALL", "EMERGENCY", false, "local_fire_department", "Call for fire emergency", "آگ کی صورت میں کال کریں"),
                ServiceEntity(0, "Motorway Police", "موٹر وے پولیس", "Highway assistance", "شاہراہ کی مدد", "130", "CALL", "EMERGENCY", false, "local_shipping", "Call for motorway help", "موٹر وے پولیس کے لیے کال کریں"),
                ServiceEntity(0, "Cyber Crime", "سائبر کرائم", "Report online fraud", "آن لائن دھوکہ دہی کی رپورٹ", "1991", "CALL", "CYBER", false, "security", "Call to report cyber crime", "سائبر کرائم کی رپورٹ کے لیے کال کریں"),
                ServiceEntity(0, "Anti Corruption", "انسداد بدعنوانی", "Report corruption", "بدعنوانی کی رپورٹ", "1033", "CALL", "CYBER", false, "report", "Call to report corruption", "بدعنوانی کی اطلاع کے لیے کال کریں"),
                
                // AMBULANCE SERVICES
                ServiceEntity(0, "Edhi Ambulance", "ایدھی ایمبولینس", "Edhi Foundation ambulance", "ایدھی فاؤنڈیشن", "115", "CALL", "AMBULANCE", false, "airport_shuttle", "Call for Edhi ambulance", "ایدھی ایمبولینس کے لیے کال کریں"),
                ServiceEntity(0, "Chhipa Ambulance", "چھیپا ایمبولینس", "Chhipa Welfare ambulance", "چھیپا ویلفیئر", "1020", "CALL", "AMBULANCE", false, "airport_shuttle", "Call for Chhipa ambulance", "چھیپا ایمبولینس کے لیے کال کریں"),
                ServiceEntity(0, "Aman Ambulance", "امان ایمبولینس", "Aman Health Care", "امان ہیلتھ کیئر", "1021", "CALL", "AMBULANCE", false, "airport_shuttle", "Call for Aman ambulance", "امان ایمبولینس کے لیے کال کریں"),
                
                // WOMEN & CHILD SUPPORT
                ServiceEntity(0, "Madadgar Women & Children", "مددگار خواتین اور بچے", "Women and child helpline", "خواتین اور بچوں کی مدد", "1098", "CALL", "WOMEN_CHILD", false, "support", "Call for help", "مدد کے لیے کال کریں"),
                ServiceEntity(0, "Aurat Foundation", "عورت فاؤنڈیشن", "Women's rights support", "خواتین کے حقوق", "0800-22266", "CALL", "WOMEN_CHILD", false, "female", "Call for women rights support", "خواتین کے حقوق کی مدد کے لیے کال کریں"),
                
                // UTILITY SERVICES
                ServiceEntity(0, "Gas Helpline", "گیس ہیلپ لائن", "Gas supply issues", "گیس کی فراہمی", "1199", "CALL", "UTILITY", false, "propane", "Call for gas issues", "گیس کی شکایات کے لیے کال کریں"),
                ServiceEntity(0, "K-Electric Emergency", "کے الیکٹرک ایمرجنسی", "Electricity emergency", "بجلی کی ایمرجنسی", "118", "CALL", "UTILITY", false, "electrical_services", "Call for K-Electric help", "کے الیکٹرک کے لیے کال کریں"),
                ServiceEntity(0, "Bomb Disposal (Karachi)", "بم ڈسپوزل", "Bomb disposal squad", "بم ڈسپوزل اسکواڈ", "39212690", "CALL", "UTILITY", false, "warning", "Call for bomb disposal", "بم ڈسپوزل کے لیے کال کریں"),
                ServiceEntity(0, "CPLC Karachi", "سی پی ایل سی", "Citizen Police Liaison", "شہری پولیس رابطہ", "35682222", "CALL", "UTILITY", false, "contact_phone", "Call for CPLC help", "سی پی ایل سی کے لیے کال کریں")
            )
            
            // Insert all services
            services.forEach { service ->
                serviceDao.insertService(service)
            }
        }
    }
}
