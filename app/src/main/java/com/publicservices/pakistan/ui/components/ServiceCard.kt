package com.publicservices.pakistan.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.rounded.Dialpad
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.publicservices.pakistan.data.model.Service
import com.publicservices.pakistan.data.model.ServiceCategory
import com.publicservices.pakistan.data.model.ServiceType
import com.publicservices.pakistan.ui.theme.AccentRed

private fun ServiceCategory.tagEmoji(): String = when (this) {
    ServiceCategory.ALL -> "📋"
    ServiceCategory.IDENTITY -> "🆔"
    ServiceCategory.VEHICLE -> "🚗"
    ServiceCategory.WELFARE -> "🤝"
    ServiceCategory.EMERGENCY -> "⚠️"
    ServiceCategory.CYBER -> "🛡️"
    ServiceCategory.AMBULANCE -> "🚑"
    ServiceCategory.WOMEN_CHILD -> "👶"
    ServiceCategory.UTILITY -> "🔥"
    ServiceCategory.TRAVEL -> "🚗"
    ServiceCategory.TRANSPORT -> "🚆"
}

@Composable
fun ServiceCard(
    service: Service,
    language: String,
    onSendSms: () -> Unit,
    onCall: () -> Unit,
    onCopy: () -> Unit,
    onFavoriteToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }
    val haptic = LocalHapticFeedback.current
    val tagLabel = "${service.category.tagEmoji()} ${service.category.getName(language)}"
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { isExpanded = !isExpanded },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(0.dp, Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f)
                ) {
                    Text(
                        text = tagLabel,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                IconButton(
                    onClick = { haptic.performHapticFeedback(HapticFeedbackType.LongPress); onFavoriteToggle() },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = if (service.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (service.isFavorite) AccentRed else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = service.getName(language),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = service.getDescription(language),
                style = MaterialTheme.typography.bodySmall,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(animationSpec = tween(220)) + fadeIn(animationSpec = tween(220)),
                exit = shrinkVertically(animationSpec = tween(180)) + fadeOut(animationSpec = tween(180))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    val description = service.getInstructions(language).ifEmpty { service.getDescription(language) }
                    if (description.isNotEmpty()) {
                        Text(
                            text = description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Left: Copy icon + helpline number only (no "Copy" text)
                        OutlinedButton(
                            onClick = { haptic.performHapticFeedback(HapticFeedbackType.LongPress); onCopy() },
                            modifier = Modifier.height(44.dp)
                        ) {
                            Icon(Icons.Outlined.ContentCopy, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(service.serviceNumber, style = MaterialTheme.typography.labelLarge)
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            if (service.serviceType == ServiceType.CALL || service.serviceType == ServiceType.USSD || service.serviceType == ServiceType.BOTH) {
                                Button(
                                    onClick = { haptic.performHapticFeedback(HapticFeedbackType.LongPress); onCall() },
                                    modifier = Modifier.height(44.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.onPrimary)
                                ) {
                                    Icon(if (service.serviceType == ServiceType.USSD) Icons.Rounded.Dialpad else Icons.Default.Call, contentDescription = null, modifier = Modifier.size(20.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(if (language == "ur") "ابھی کال کریں" else "Call Now", style = MaterialTheme.typography.labelLarge)
                                }
                            }
                            if (service.serviceType == ServiceType.SMS || service.serviceType == ServiceType.BOTH) {
                                Button(
                                    onClick = { haptic.performHapticFeedback(HapticFeedbackType.LongPress); onSendSms() },
                                    modifier = Modifier.height(44.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.onPrimary)
                                ) {
                                    Icon(Icons.Default.Sms, contentDescription = null, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(if (language == "ur") "پیغام" else "Message", style = MaterialTheme.typography.labelLarge)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
