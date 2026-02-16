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
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp), // Standardized to 12dp
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp) // Increased padding to 20dp
        ) {
            // Header: Icon + Title/Description + Favorite
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top // Align to top for better hierarchy
            ) {
                // Service Icon
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
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
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = service.getName(language),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium, // Medium for titles
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = service.getDescription(language),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal, // Regular for secondary
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 18.sp
                    )
                }
                
                IconButton(
                    onClick = onFavoriteToggle,
                    modifier = Modifier
                        .minimumInteractiveComponentSize() // Ensures 48dp+ (satisfies 44dp requirement)
                ) {
                    Icon(
                        imageVector = if (service.isFavorite) 
                            Icons.Filled.Favorite 
                        else 
                            Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (service.isFavorite) AccentRed else MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            
            // Prominent Instruction Banner for non-technical users
            val instructions = service.getInstructions(language)
            if (instructions.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp, 
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = instructions,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Bottom Section: Phone Number + Call/Sms Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Phone Number with Copy Icon (Larger touch target)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                        )
                        .clickable(onClick = onCopy)
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = service.serviceNumber,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Black, // Bold dark neutral
                        color = MaterialTheme.colorScheme.onBackground,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copy",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                    )
                }

                // Action Buttons (Condensed)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (service.serviceType == ServiceType.SMS || service.serviceType == ServiceType.BOTH) {
                        IconButton(
                            onClick = onSendSms,
                            modifier = Modifier
                                .size(44.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = androidx.compose.foundation.shape.CircleShape
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "SMS",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    if (service.serviceType == ServiceType.CALL || service.serviceType == ServiceType.USSD || service.serviceType == ServiceType.BOTH) {
                        IconButton(
                            onClick = onCall,
                            modifier = Modifier
                                .size(44.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = androidx.compose.foundation.shape.CircleShape
                                )
                        ) {
                            Icon(
                                imageVector = if (service.serviceType == ServiceType.USSD) Icons.Default.Dialpad else Icons.Default.Call,
                                contentDescription = "Call",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
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
