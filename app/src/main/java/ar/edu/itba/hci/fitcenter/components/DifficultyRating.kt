package ar.edu.itba.hci.fitcenter.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ar.edu.itba.hci.fitcenter.api.Models

@Composable
fun DifficultyRating(difficulty: Models.Difficulty) {
    Row {
        repeat(3) { index ->
            val iconColor = if (index < difficulty.ordinal) {
                Color.Red
            } else {
                Color.Gray
            }
            Icon(
                imageVector = Icons.Default.LocalFireDepartment,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.padding(end = 4.dp)
            )
        }
    }
}