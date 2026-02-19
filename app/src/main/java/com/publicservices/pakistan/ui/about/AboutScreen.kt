package com.publicservices.pakistan.ui.about

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.animation.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.app.NotificationManagerCompat
import android.content.pm.PackageManager
import com.publicservices.pakistan.utils.IntentHelper
import com.publicservices.pakistan.utils.NotificationScheduler
import com.publicservices.pakistan.utils.PreferencesManager

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
        
        // Sahulat Logo (same as Home top nav)
        Surface(
            modifier = Modifier.size(110.dp),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
            color = Color.Transparent,
            shadowElevation = 0.dp,
            border = BorderStroke(0.dp, Color.Transparent)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize().padding(8.dp)) {
                Image(
                    painter = painterResource(id = context.resources.getIdentifier("only_sahulat_logo_transparent", "drawable", context.packageName)),
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
            shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f)
            ),
            border = BorderStroke(0.dp, Color.Transparent),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
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
        
        // Manage Notifications (list-style entry)
        ManageNotificationsEntry(
            currentLanguage = currentLanguage,
            context = context
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Status Checker Guide Section
        StatusCheckerGuide(currentLanguage = currentLanguage)
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // App Info Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            border = BorderStroke(0.dp, Color.Transparent),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
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

@Composable
private fun ManageNotificationsEntry(
    currentLanguage: String,
    context: android.content.Context
) {
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            PreferencesManager.setNotificationsEnabled(context, true)
            NotificationScheduler.scheduleDailyNotifications(context, currentLanguage)
            Toast.makeText(context, if (currentLanguage == "ur") "نوٹیفکیشنز آن ہیں" else "Notifications are Active", Toast.LENGTH_SHORT).show()
        }
    }
    
    fun hasNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
        } else {
            NotificationManagerCompat.from(context).areNotificationsEnabled()
        }
    }
    
    Surface(
        onClick = {
            if (hasNotificationPermission()) {
                Toast.makeText(context, if (currentLanguage == "ur") "نوٹیفکیشنز پہلے سے آن ہیں" else "Notifications are Active", Toast.LENGTH_SHORT).show()
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    PreferencesManager.setNotificationsEnabled(context, true)
                    NotificationScheduler.scheduleDailyNotifications(context, currentLanguage)
                    Toast.makeText(context, if (currentLanguage == "ur") "نوٹیفکیشنز آن ہیں" else "Notifications are Active", Toast.LENGTH_SHORT).show()
                }
            }
        },
        modifier = Modifier.fillMaxWidth(),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        border = BorderStroke(0.dp, Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (currentLanguage == "ur") "نوٹیفکیشنز منظم کریں" else "Manage Notifications",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = if (currentLanguage == "ur") "روزانہ نکات آن یا بند کریں" else "Turn daily tips on or off",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun StatusCheckerGuide(currentLanguage: String) {
    val context = LocalContext.current
    var isExpanded by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        border = BorderStroke(0.dp, Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Column {
                        Text(
                            text = if (currentLanguage == "ur") 
                                "سروس کی حیثیت چیک کرنے کا طریقہ" 
                            else 
                                "How to Check Service Status",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (currentLanguage == "ur") 
                                "Step-by-step guide" 
                            else 
                                "Step-by-step guide",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 420.dp)
                        .verticalScroll(scrollState)
                        .padding(top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    val steps = if (currentLanguage == "ur") listOf(
                        "مرحلہ 1: مطلوبہ ہیلپ لائن نمبر تلاش کریں",
                        "مرحلہ 2: 'کال' بٹن پر کلک کریں (یہ آپ کو ڈائلر میں لے جائے گا)",
                        "مرحلہ 3: نمبر ڈائل کریں اور ہدایات پر عمل کریں",
                        "مرحلہ 4: SMS سروسز کے لیے، 'پیغام' بٹن استعمال کریں",
                        "مرحلہ 5: USSD کوڈز کے لیے، نمبر کاپی کریں اور ڈائل کریں",
                        "نوٹ: کچھ خدمات کے لیے آپ کو اپنا CNIC نمبر بھیجنا پڑ سکتا ہے"
                    ) else listOf(
                        "Step 1: Find the required helpline number",
                        "Step 2: Click the 'Call' button (it will open your dialer)",
                        "Step 3: Dial the number and follow the instructions",
                        "Step 4: For SMS services, use the 'Message' button",
                        "Step 5: For USSD codes, copy the number and dial it",
                        "Note: Some services may require you to send your CNIC number"
                    )
                    
                    steps.forEachIndexed { index, step ->
                        GuideStep(
                            stepNumber = index + 1,
                            text = step,
                            language = currentLanguage
                        )
                    }
                    
                    // Quick dial codes — Copy to clipboard for engagement
                    Text(
                        text = if (currentLanguage == "ur") "فوری ڈائل کوڈز" else "Quick dial codes",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    val quickCodes = if (currentLanguage == "ur") listOf(
                        "1122" to "پولیس ایمرجنسی",
                        "15" to "عام ایمرجنسی",
                        "998" to "ہیلپ لائن"
                    ) else listOf(
                        "1122" to "Police emergency",
                        "15" to "General emergency",
                        "998" to "Helpline"
                    )
                    quickCodes.forEach { (code, label) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = code,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            FilledTonalButton(
                                onClick = {
                                    IntentHelper.copyToClipboard(context, code, "Dial code")
                                    Toast.makeText(context, if (currentLanguage == "ur") "کاپی: $code" else "Copied: $code", Toast.LENGTH_SHORT).show()
                                },
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(if (currentLanguage == "ur") "کاپی" else "Copy", style = MaterialTheme.typography.labelMedium)
                            }
                        }
                    }
                    
                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                    )
                    
                    // Additional Tips
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = if (currentLanguage == "ur") 
                                    "مفید نکات:" 
                                else 
                                    "Helpful Tips:",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            val tips = if (currentLanguage == "ur") listOf(
                                "• ہمیشہ سرکاری نمبرز استعمال کریں",
                                "• اپنا حوالہ نمبر محفوظ رکھیں",
                                "• کچھ خدمات کے لیے انتظار کا وقت ہو سکتا ہے",
                                "• اگر کوئی مسئلہ ہو تو براہ راست محکمہ سے رابطہ کریں"
                            ) else listOf(
                                "• Always use official numbers",
                                "• Keep your reference number safe",
                                "• Some services may have waiting times",
                                "• Contact the department directly if there's an issue"
                            )
                            tips.forEach { tip ->
                                Text(
                                    text = tip,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GuideStep(
    stepNumber: Int,
    text: String,
    language: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Surface(
            modifier = Modifier.size(32.dp),
            shape = androidx.compose.foundation.shape.CircleShape,
            color = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = stepNumber.toString(),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
    }
}
