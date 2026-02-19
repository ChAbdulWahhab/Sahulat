package com.publicservices.pakistan.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.*
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.unit.dp
import com.publicservices.pakistan.data.DidYouKnowFacts
import com.publicservices.pakistan.ui.theme.SurfaceContainerLow
import kotlinx.coroutines.delay

@Composable
fun DidYouKnowCarousel(
    language: String,
    modifier: Modifier = Modifier
) {
    // Recompute facts when language changes so text and font update immediately
    val facts = remember(language) { DidYouKnowFacts.getRandomFacts(5, language) }
    var currentIndex by remember { mutableStateOf(0) }
    
    // Reset to first fact when language changes
    LaunchedEffect(language) {
        currentIndex = 0
    }
    
    // Smooth infinite auto-scroll every 4 seconds
    LaunchedEffect(currentIndex, language) {
        delay(4000)
        currentIndex = (currentIndex + 1) % facts.size
    }
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.surfaceVariant else SurfaceContainerLow
        ),
        border = BorderStroke(0.dp, Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Icon
            Icon(
                imageVector = Icons.Default.Lightbulb,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )
            
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Label
                Text(
                    text = if (language == "ur") "کیا آپ جانتے ہیں؟" else "Did You Know?",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                
                // Fact text with smooth slide transition (infinite auto-scroll)
                AnimatedContent(
                    targetState = currentIndex,
                    transitionSpec = {
                        slideInHorizontally(initialOffsetX = { it }) + fadeIn(animationSpec = tween(300)) togetherWith
                            slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(animationSpec = tween(300))
                    },
                    label = "carousel_fact"
                ) { index ->
                    Text(
                        text = facts[index],
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            
            // Page indicators
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                facts.forEachIndexed { index, _ ->
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(
                                color = if (index == currentIndex) 
                                    MaterialTheme.colorScheme.primary 
                                else 
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(3.dp)
                            )
                    )
                }
            }
        }
    }
}
