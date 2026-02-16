package com.publicservices.pakistan.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.publicservices.pakistan.data.model.ServiceCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryChip(
    category: ServiceCategory,
    isSelected: Boolean,
    onClick: () -> Unit,
    language: String,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = { 
            Text(
                text = category.getName(language),
                style = MaterialTheme.typography.labelMedium
            ) 
        },
        modifier = modifier.padding(horizontal = 4.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            labelColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
}
