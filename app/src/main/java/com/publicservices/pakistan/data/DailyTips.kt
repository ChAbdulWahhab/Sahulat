package com.publicservices.pakistan.data

data class DailyTip(
    val id: Int,
    val titleEn: String,
    val titleUr: String,
    val messageEn: String,
    val messageUr: String,
    val serviceId: Int? = null // Optional: link to specific service
)

object DailyTips {
    val tips = listOf(
        DailyTip(
            1,
            "Emergency Helpline",
            "ایمرجنسی ہیلپ لائن",
            "Save 1122 for emergency services. It connects you to police, ambulance, and fire services.",
            "ایمرجنسی سروسز کے لیے 1122 محفوظ کریں۔ یہ آپ کو پولیس، ایمبولینس اور فائر سروسز سے جوڑتا ہے۔",
            serviceId = null
        ),
        DailyTip(
            2,
            "Consumer Rights",
            "صارفین کے حقوق",
            "For consumer complaints, dial 1700. This helpline helps resolve issues with products and services.",
            "صارفین کی شکایات کے لیے 1700 ڈائل کریں۔ یہ ہیلپ لائن مصنوعات اور خدمات کے مسائل حل کرنے میں مدد کرتی ہے۔",
            serviceId = null
        ),
        DailyTip(
            3,
            "Women's Helpline",
            "خواتین کی ہیلپ لائن",
            "Women facing harassment can call 1099. This service provides support and guidance.",
            "ہراسانی کا سامنا کرنے والی خواتین 1099 پر کال کر سکتی ہیں۔ یہ سروس مدد اور رہنمائی فراہم کرتی ہے۔",
            serviceId = null
        ),
        DailyTip(
            4,
            "Child Protection",
            "بچوں کی حفاظت",
            "Report child abuse or missing children by calling 1121. Your call can save a child's life.",
            "بچوں کے ساتھ بدسلوکی یا لاپتہ بچوں کی اطلاع 1121 پر کال کر کے دیں۔ آپ کی کال کسی بچے کی جان بچا سکتی ہے۔",
            serviceId = null
        ),
        DailyTip(
            5,
            "Traffic Complaints",
            "ٹریفک کی شکایات",
            "Report traffic violations or road issues by calling 1915. Help make roads safer.",
            "ٹریفک کی خلاف ورزی یا سڑک کے مسائل کی اطلاع 1915 پر کال کر کے دیں۔ سڑکوں کو محفوظ بنانے میں مدد کریں۔",
            serviceId = null
        ),
        DailyTip(
            6,
            "Gas Leak Emergency",
            "گیس لیک ایمرجنسی",
            "If you smell gas, immediately call 1199. Do not use electrical switches or open flames.",
            "اگر آپ کو گیس کی بو آئے، فوری طور پر 1199 پر کال کریں۔ بجلی کے سوئچ یا کھلی آگ استعمال نہ کریں۔",
            serviceId = null
        ),
        DailyTip(
            7,
            "Electricity Complaints",
            "بجلی کی شکایات",
            "Report power outages or electrical issues by calling 118. Keep your reference number handy.",
            "بجلی کی بندش یا بجلی کے مسائل کی اطلاع 118 پر کال کر کے دیں۔ اپنا حوالہ نمبر ہاتھ میں رکھیں۔",
            serviceId = null
        ),
        DailyTip(
            8,
            "Water Complaints",
            "پانی کی شکایات",
            "For water supply issues, call 1166. This helpline handles complaints about water services.",
            "پانی کی فراہمی کے مسائل کے لیے 1166 پر کال کریں۔ یہ ہیلپ لائن پانی کی خدمات کے بارے میں شکایات سنبھالتی ہے۔",
            serviceId = null
        ),
        DailyTip(
            9,
            "Anti-Corruption",
            "بدعنوانی کے خلاف",
            "Report corruption anonymously by calling 1033. Help build a transparent Pakistan.",
            "بدعنوانی کی رپورٹ گمنام طور پر 1033 پر کال کر کے دیں۔ شفاف پاکستان بنانے میں مدد کریں۔",
            serviceId = null
        ),
        DailyTip(
            10,
            "Health Emergency",
            "صحت کی ایمرجنسی",
            "For medical emergencies, call 1021 (Aman Ambulance) or 1122. Every second counts.",
            "طبی ایمرجنسی کے لیے 1021 (امان ایمبولینس) یا 1122 پر کال کریں۔ ہر سیکنڈ اہم ہے۔",
            serviceId = null
        ),
        DailyTip(
            11,
            "Identity Card Issues",
            "شناختی کارڈ کے مسائل",
            "For NADRA services and CNIC issues, visit your nearest NADRA office or call 1777.",
            "نادرا کی خدمات اور شناختی کارڈ کے مسائل کے لیے اپنے قریب ترین نادرا دفتر جائیں یا 1777 پر کال کریں۔",
            serviceId = null
        ),
        DailyTip(
            12,
            "Passport Services",
            "پاسپورٹ کی خدمات",
            "For passport inquiries, call 051-111-786-100 or visit the passport office. Book appointments online.",
            "پاسپورٹ کی معلومات کے لیے 051-111-786-100 پر کال کریں یا پاسپورٹ آفس جائیں۔ آن لائن اپائنٹمنٹ بک کریں۔",
            serviceId = null
        ),
        DailyTip(
            13,
            "Banking Complaints",
            "بینکنگ کی شکایات",
            "For banking complaints, contact State Bank of Pakistan at 111-772-772 or visit their website.",
            "بینکنگ کی شکایات کے لیے اسٹیٹ بینک آف پاکستان سے 111-772-772 پر رابطہ کریں یا ان کی ویب سائٹ ملاحظہ کریں۔",
            serviceId = null
        ),
        DailyTip(
            14,
            "Tax Information",
            "ٹیکس کی معلومات",
            "For FBR tax inquiries, call 051-111-772-772 or visit the FBR website for online services.",
            "ایف بی آر ٹیکس کی معلومات کے لیے 051-111-772-772 پر کال کریں یا آن لائن خدمات کے لیے ایف بی آر کی ویب سائٹ ملاحظہ کریں۔",
            serviceId = null
        ),
        DailyTip(
            15,
            "Education Helpline",
            "تعلیم کی ہیلپ لائن",
            "For education-related queries, contact your provincial education department or call 0800-77677.",
            "تعلیم سے متعلق سوالات کے لیے اپنے صوبائی تعلیم محکمہ سے رابطہ کریں یا 0800-77677 پر کال کریں۔",
            serviceId = null
        ),
        DailyTip(
            16,
            "Legal Aid",
            "قانونی امداد",
            "For free legal aid, contact your local bar association or call 0800-70808 for legal assistance.",
            "مفت قانونی امداد کے لیے اپنی مقامی بار ایسوسی ایشن سے رابطہ کریں یا قانونی مدد کے لیے 0800-70808 پر کال کریں۔",
            serviceId = null
        ),
        DailyTip(
            17,
            "Disaster Management",
            "آفات کا انتظام",
            "In case of natural disasters, call 1122 for emergency response or contact PDMA at 0800-01100.",
            "قدرتی آفات کی صورت میں ایمرجنسی ردعمل کے لیے 1122 پر کال کریں یا پی ڈی ایم اے سے 0800-01100 پر رابطہ کریں۔",
            serviceId = null
        ),
        DailyTip(
            18,
            "Internet Complaints",
            "انٹرنیٹ کی شکایات",
            "For internet service complaints, contact PTA at 0800-55055 or file a complaint online.",
            "انٹرنیٹ سروس کی شکایات کے لیے پی ٹی اے سے 0800-55055 پر رابطہ کریں یا آن لائن شکایت درج کریں۔",
            serviceId = null
        ),
        DailyTip(
            19,
            "Food Safety",
            "خوراک کی حفاظت",
            "Report food safety issues to Punjab Food Authority at 0800-78601 or your local food authority.",
            "خوراک کی حفاظت کے مسائل کی رپورٹ پنجاب فوڈ اتھارٹی کو 0800-78601 پر یا اپنے مقامی فوڈ اتھارٹی کو دیں۔",
            serviceId = null
        ),
        DailyTip(
            20,
            "Employment Services",
            "ملازمت کی خدمات",
            "For job opportunities and employment services, visit your local employment exchange or call 0800-77677.",
            "ملازمت کے مواقع اور ملازمت کی خدمات کے لیے اپنے مقامی ملازمت ایکسچینج جائیں یا 0800-77677 پر کال کریں۔",
            serviceId = null
        ),
        DailyTip(
            21,
            "Housing Complaints",
            "رہائش کی شکایات",
            "For housing and property complaints, contact your local development authority or call 0800-77677.",
            "رہائش اور جائیداد کی شکایات کے لیے اپنے مقامی ترقیاتی اتھارٹی سے رابطہ کریں یا 0800-77677 پر کال کریں۔",
            serviceId = null
        ),
        DailyTip(
            22,
            "Public Transport",
            "عوامی نقل و حمل",
            "For public transport complaints, contact your local transport authority or call 0800-77677.",
            "عوامی نقل و حمل کی شکایات کے لیے اپنے مقامی ٹرانسپورٹ اتھارٹی سے رابطہ کریں یا 0800-77677 پر کال کریں۔",
            serviceId = null
        ),
        DailyTip(
            23,
            "Environmental Issues",
            "ماحولیاتی مسائل",
            "Report environmental violations to EPA at 0800-77677 or your local environmental protection agency.",
            "ماحولیاتی خلاف ورزیوں کی رپورٹ ای پی اے کو 0800-77677 پر یا اپنے مقامی ماحولیاتی تحفظ ایجنسی کو دیں۔",
            serviceId = null
        ),
        DailyTip(
            24,
            "Senior Citizen Support",
            "بزرگ شہریوں کی مدد",
            "Senior citizens can get support and information by calling 0800-77677 or visiting social welfare offices.",
            "بزرگ شہری 0800-77677 پر کال کر کے یا سماجی بہبود کے دفاتر جاکر مدد اور معلومات حاصل کر سکتے ہیں۔",
            serviceId = null
        ),
        DailyTip(
            25,
            "Disability Services",
            "معذوری کی خدمات",
            "For disability support services, contact your local social welfare department or call 0800-77677.",
            "معذوری کی مدد کی خدمات کے لیے اپنے مقامی سماجی بہبود محکمہ سے رابطہ کریں یا 0800-77677 پر کال کریں۔",
            serviceId = null
        ),
        DailyTip(
            26,
            "Mental Health",
            "ذہنی صحت",
            "For mental health support, contact your local hospital or call 0800-77677 for counseling services.",
            "ذہنی صحت کی مدد کے لیے اپنے مقامی ہسپتال سے رابطہ کریں یا مشاورت کی خدمات کے لیے 0800-77677 پر کال کریں۔",
            serviceId = null
        ),
        DailyTip(
            27,
            "Voter Registration",
            "ووٹر رجسٹریشن",
            "For voter registration and election information, visit your local election commission office or call 0800-77677.",
            "ووٹر رجسٹریشن اور انتخابی معلومات کے لیے اپنے مقامی الیکشن کمیشن آفس جائیں یا 0800-77677 پر کال کریں۔",
            serviceId = null
        ),
        DailyTip(
            28,
            "Petition Filing",
            "درخواست دائر کرنا",
            "Learn how to file petitions and complaints online through government portals. Visit pakistan.gov.pk for more info.",
            "حکومتی پورٹلز کے ذریعے آن لائن درخواستیں اور شکایات دائر کرنے کا طریقہ سیکھیں۔ مزید معلومات کے لیے pakistan.gov.pk ملاحظہ کریں۔",
            serviceId = null
        ),
        DailyTip(
            29,
            "Digital Services",
            "ڈیجیٹل خدمات",
            "Many government services are now available online. Visit pakistan.gov.pk to access digital services.",
            "بہت سی حکومتی خدمات اب آن لائن دستیاب ہیں۔ ڈیجیٹل خدمات تک رسائی کے لیے pakistan.gov.pk ملاحظہ کریں۔",
            serviceId = null
        ),
        DailyTip(
            30,
            "Stay Informed",
            "مطلع رہیں",
            "Keep Sahulat PK app updated to get the latest helpline numbers and service information. Share with friends!",
            "تازہ ترین ہیلپ لائن نمبرز اور سروس کی معلومات حاصل کرنے کے لیے سہولت پی کے ایپ کو اپ ڈیٹ رکھیں۔ دوستوں کے ساتھ شیئر کریں!",
            serviceId = null
        )
    )
    
    fun getTipForDay(dayOfYear: Int, language: String): DailyTip {
        val tip = tips[dayOfYear % tips.size]
        return tip
    }
}
