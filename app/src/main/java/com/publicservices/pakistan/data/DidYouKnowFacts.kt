package com.publicservices.pakistan.data

data class DidYouKnowFact(
    val id: Int,
    val factEn: String,
    val factUr: String,
    val category: String
)

object DidYouKnowFacts {
    val facts = listOf(
        DidYouKnowFact(
            1,
            "Emergency helpline 1122 connects you to police, ambulance, and fire services all in one call.",
            "ایمرجنسی ہیلپ لائن 1122 آپ کو ایک کال میں پولیس، ایمبولینس اور فائر سروسز سے جوڑتی ہے۔",
            "Emergency"
        ),
        DidYouKnowFact(
            2,
            "You can report consumer complaints anonymously through helpline 1700.",
            "آپ ہیلپ لائن 1700 کے ذریعے گمنام طور پر صارفین کی شکایات رپورٹ کر سکتے ہیں۔",
            "Consumer Rights"
        ),
        DidYouKnowFact(
            3,
            "Women's helpline 1099 operates 24/7 to provide support and guidance.",
            "خواتین کی ہیلپ لائن 1099 مدد اور رہنمائی فراہم کرنے کے لیے 24/7 کام کرتی ہے۔",
            "Women's Rights"
        ),
        DidYouKnowFact(
            4,
            "Child protection helpline 1121 can help locate missing children and report abuse.",
            "بچوں کی حفاظت ہیلپ لائن 1121 لاپتہ بچوں کو تلاش کرنے اور بدسلوکی کی رپورٹ کرنے میں مدد کر سکتی ہے۔",
            "Child Protection"
        ),
        DidYouKnowFact(
            5,
            "Traffic violations can be reported through helpline 1915 to make roads safer.",
            "سڑکوں کو محفوظ بنانے کے لیے ٹریفک کی خلاف ورزیوں کی رپورٹ ہیلپ لائن 1915 کے ذریعے دی جا سکتی ہے۔",
            "Traffic"
        ),
        DidYouKnowFact(
            6,
            "Gas leak emergencies require immediate action - call 1199 and avoid using electrical switches.",
            "گیس لیک کی ایمرجنسی میں فوری کارروائی کی ضرورت ہوتی ہے - 1199 پر کال کریں اور بجلی کے سوئچ استعمال کرنے سے گریز کریں۔",
            "Emergency"
        ),
        DidYouKnowFact(
            7,
            "Power outage complaints can be filed through helpline 118 - keep your reference number safe.",
            "بجلی کی بندش کی شکایات ہیلپ لائن 118 کے ذریعے درج کی جا سکتی ہیں - اپنا حوالہ نمبر محفوظ رکھیں۔",
            "Utilities"
        ),
        DidYouKnowFact(
            8,
            "Water supply issues can be reported to helpline 1166 for quick resolution.",
            "پانی کی فراہمی کے مسائل کی تیز حل کے لیے ہیلپ لائن 1166 پر رپورٹ کی جا سکتی ہے۔",
            "Utilities"
        ),
        DidYouKnowFact(
            9,
            "Anti-corruption helpline 1033 allows anonymous reporting to build a transparent Pakistan.",
            "بدعنوانی کے خلاف ہیلپ لائن 1033 شفاف پاکستان بنانے کے لیے گمنام رپورٹنگ کی اجازت دیتی ہے۔",
            "Governance"
        ),
        DidYouKnowFact(
            10,
            "Medical emergencies require immediate response - call 1021 or 1122. Every second counts.",
            "طبی ایمرجنسی میں فوری ردعمل کی ضرورت ہوتی ہے - 1021 یا 1122 پر کال کریں۔ ہر سیکنڈ اہم ہے۔",
            "Health"
        ),
        DidYouKnowFact(
            11,
            "NADRA services for CNIC issues are available at helpline 1777 or visit your nearest NADRA office.",
            "شناختی کارڈ کے مسائل کے لیے نادرا کی خدمات ہیلپ لائن 1777 پر دستیاب ہیں یا اپنے قریب ترین نادرا دفتر جائیں۔",
            "Identity"
        ),
        DidYouKnowFact(
            12,
            "Many government services are now available online through pakistan.gov.pk portal.",
            "بہت سی حکومتی خدمات اب pakistan.gov.pk پورٹل کے ذریعے آن لائن دستیاب ہیں۔",
            "Digital Services"
        ),
        DidYouKnowFact(
            13,
            "Consumer rights in Pakistan include the right to safe products and fair pricing.",
            "پاکستان میں صارفین کے حقوق میں محفوظ مصنوعات اور منصفانہ قیمتوں کا حق شامل ہے۔",
            "Consumer Rights"
        ),
        DidYouKnowFact(
            14,
            "You can file complaints about banking services through State Bank helpline 111-772-772.",
            "آپ اسٹیٹ بینک ہیلپ لائن 111-772-772 کے ذریعے بینکنگ خدمات کے بارے میں شکایات درج کر سکتے ہیں۔",
            "Banking"
        ),
        DidYouKnowFact(
            15,
            "FBR tax inquiries can be made through helpline 051-111-772-772 or online portal.",
            "ایف بی آر ٹیکس کی معلومات ہیلپ لائن 051-111-772-772 یا آن لائن پورٹل کے ذریعے حاصل کی جا سکتی ہیں۔",
            "Tax"
        )
    )
    
    fun getRandomFacts(count: Int, language: String): List<String> {
        return facts.shuffled().take(count).map { 
            if (language == "ur") it.factUr else it.factEn 
        }
    }
}
