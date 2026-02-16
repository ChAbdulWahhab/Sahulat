package com.publicservices.pakistan.ui.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun AboutScreen(
    currentLanguage: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        
        // Sahulat Logo Section
        Surface(
            modifier = Modifier.size(110.dp),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp), // Standardized 12dp
            color = Color.White,
            shadowElevation = 2.dp,
            border = androidx.compose.foundation.BorderStroke(
                1.dp, 
                MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
            )
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(16.dp)) {
                androidx.compose.foundation.Image(
                    painter = androidx.compose.ui.res.painterResource(id = context.resources.getIdentifier("sahulat_logo", "drawable", context.packageName)),
                    contentDescription = "Sahulat Logo",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = if (currentLanguage == "ur") 
                "سہولت" 
            else 
                "Sahulat",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold, // Title bolder
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )
        
        Text(
            text = if (currentLanguage == "ur") 
                "آپ کی حفاظت، ہماری ترجیح" 
            else 
                "Aapki Hifazat, Hamari Priority",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium, // Medium weight as requested
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Modern Disclaimer Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp), // Standardized 12dp
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f)
            ),
            border = androidx.compose.foundation.BorderStroke(
                1.dp, 
                MaterialTheme.colorScheme.error.copy(alpha = 0.1f)
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Warning, 
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (currentLanguage == "ur") "ضروری اعلان" else "Important Notice",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    val points = if (currentLanguage == "ur") listOf(
                        "یہ ایک غیر سرکاری ایپلیکیشن ہے",
                        "سروس نمبرز وقت کے ساتھ بدل سکتے ہیں",
                        "یہ حکومت کی طرف سے نہیں بنائی گئی",
                        "کسی بھی سروس کے لیے براہ راست محکموں سے رجوع کریں"
                    ) else listOf(
                        "This is an unofficial application.",
                        "Service numbers may change over time.",
                        "Not affiliated with any government entity.",
                        "Always verify info with official departments."
                    )

                    points.forEach { point ->
                        if (currentLanguage == "ur") {
                            DisclaimerPointUrdu(point)
                        } else {
                            DisclaimerPoint(point)
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // App Info Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                )
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                    androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                )
                .padding(16.dp)
        ) {
            InfoRow(
                label = if (currentLanguage == "ur") "ورژن" else "Version",
                value = "1.0.0"
            )
            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
            )
            InfoRow(
                label = if (currentLanguage == "ur") "ڈیٹا اپ ڈیٹ" else "Data Updated",
                value = "Feb 2026"
            )
        }
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Text(
            text = if (currentLanguage == "ur") 
                "پاکستان کے لیے بنایا گیا 🇵🇰" 
            else 
                "Made for Pakistan 🇵🇰",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
            fontWeight = FontWeight.Normal, // Regular for secondary
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun DisclaimerPoint(text: String) {
    Row(verticalAlignment = Alignment.Top) {
        Text("• ", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onErrorContainer)
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onErrorContainer.copy(alpha = 0.9f)
        )
    }
}

@Composable
private fun DisclaimerPointUrdu(text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onErrorContainer.copy(alpha = 0.9f),
            textAlign = TextAlign.End
        )
        Text(" •", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onErrorContainer)
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
