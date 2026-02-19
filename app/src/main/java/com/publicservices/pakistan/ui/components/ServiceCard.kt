package com.publicservices.pakistan.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.publicservices.pakistan.data.model.Service
import com.publicservices.pakistan.data.model.ServiceType
import com.publicservices.pakistan.ui.theme.AccentRed

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
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp), // Modern 16dp rounding
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp), // Flat design with border is more modern
        border = androidx.compose.foundation.BorderStroke(
            1.dp, 
            MaterialTheme.colorScheme.outline.copy(alpha = 0.1f)
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header: Icon + Title/Description + Favorite
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Service Icon (Circle background for modern look)
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                            shape = androidx.compose.foundation.shape.CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = when(service.serviceType) {
                            ServiceType.SMS -> Icons.Default.Chat
                            ServiceType.CALL -> Icons.Default.Phone
                            ServiceType.USSD -> Icons.Default.Dialpad
                            else -> Icons.Default.MiscellaneousServices
                        },
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = service.getName(language),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = service.getDescription(language),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                }
                
                IconButton(
                    onClick = onFavoriteToggle,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = if (service.isFavorite) 
                            Icons.Filled.Favorite 
                        else 
                            Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (service.isFavorite) AccentRed else MaterialTheme.colorScheme.outline.copy(alpha = 0.6f),
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
            
            // Instruction Banner (Softer design)
            val instructions = service.getInstructions(language)
            if (instructions.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = instructions,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Bottom Section: Phone Number + Call/Sms Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Phone Number with Copy (Modern Badge look)
                Surface(
                    onClick = onCopy,
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(32.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = service.serviceNumber,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurface,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy",
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                // Action Buttons
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (service.serviceType == ServiceType.SMS || service.serviceType == ServiceType.BOTH) {
                        Surface(
                            onClick = onSendSms,
                            modifier = Modifier.size(44.dp),
                            shape = androidx.compose.foundation.shape.CircleShape,
                            color = MaterialTheme.colorScheme.primary,
                            shadowElevation = 2.dp
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Send,
                                    contentDescription = "SMS",
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                    if (service.serviceType == ServiceType.CALL || service.serviceType == ServiceType.USSD || service.serviceType == ServiceType.BOTH) {
                        Surface(
                            onClick = onCall,
                            modifier = Modifier.size(44.dp),
                            shape = androidx.compose.foundation.shape.CircleShape,
                            color = MaterialTheme.colorScheme.primary,
                            shadowElevation = 2.dp
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = if (service.serviceType == ServiceType.USSD) Icons.Default.Dialpad else Icons.Default.Call,
                                    contentDescription = "Call",
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
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
private fun ActionButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )
    }
}
