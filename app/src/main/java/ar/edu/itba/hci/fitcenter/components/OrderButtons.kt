package ar.edu.itba.hci.fitcenter.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SortingButtons(
    sortingCriterion: SortingCriterion,
    onSortingCriterionChanged: (SortingCriterion) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SortingButton(
            text = "Date",
            isSelected = sortingCriterion == SortingCriterion.DATE,
            onClick = {
                if (sortingCriterion == SortingCriterion.DATE) {
                    // Clicked on the already selected button, unselect and use default sorting
                    onSortingCriterionChanged(SortingCriterion.NAME)
                } else {
                    // Clicked on a different button, change sorting
                    onSortingCriterionChanged(SortingCriterion.DATE)
                }
            }
        )
        SortingButton(
            text = "Score",
            isSelected = sortingCriterion == SortingCriterion.SCORE,
            onClick = {
                if (sortingCriterion == SortingCriterion.SCORE) {
                    onSortingCriterionChanged(SortingCriterion.NAME)
                } else {
                    onSortingCriterionChanged(SortingCriterion.SCORE)
                }
            }
        )
        SortingButton(
            text = "Difficulty",
            isSelected = sortingCriterion == SortingCriterion.DIFFICULTY,
            onClick = {
                if (sortingCriterion == SortingCriterion.DIFFICULTY) {
                    onSortingCriterionChanged(SortingCriterion.NAME)
                } else {
                    onSortingCriterionChanged(SortingCriterion.DIFFICULTY)
                }
            }
        )
        SortingButton(
            text = "Category",
            isSelected = sortingCriterion == SortingCriterion.CATEGORY,
            onClick = {
                if (sortingCriterion == SortingCriterion.CATEGORY) {
                    onSortingCriterionChanged(SortingCriterion.NAME)
                } else {
                    onSortingCriterionChanged(SortingCriterion.CATEGORY)
                }
            }
        )
    }
}

@Composable
fun VerticalSortingButtons(
    sortingCriterion: SortingCriterion,
    onSortingCriterionChanged: (SortingCriterion) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        SortingButton(
            text = "Date",
            isSelected = sortingCriterion == SortingCriterion.DATE,
            onClick = {
                if (sortingCriterion == SortingCriterion.DATE) {
                    // Clicked on the already selected button, unselect and use default sorting
                    onSortingCriterionChanged(SortingCriterion.NAME)
                } else {
                    // Clicked on a different button, change sorting
                    onSortingCriterionChanged(SortingCriterion.DATE)
                }
            },
            modifier = Modifier
                .widthIn(min = 150.dp)
        )
        SortingButton(
            text = "Score",
            isSelected = sortingCriterion == SortingCriterion.SCORE,
            onClick = {
                if (sortingCriterion == SortingCriterion.SCORE) {
                    onSortingCriterionChanged(SortingCriterion.NAME)
                } else {
                    onSortingCriterionChanged(SortingCriterion.SCORE)
                }
            },
            modifier = Modifier
                .widthIn(min = 150.dp)
        )
        SortingButton(
            text = "Difficulty",
            isSelected = sortingCriterion == SortingCriterion.DIFFICULTY,
            onClick = {
                if (sortingCriterion == SortingCriterion.DIFFICULTY) {
                    onSortingCriterionChanged(SortingCriterion.NAME)
                } else {
                    onSortingCriterionChanged(SortingCriterion.DIFFICULTY)
                }
            },
            modifier = Modifier
                .widthIn(min = 150.dp)
        )
        SortingButton(
            text = "Category",
            isSelected = sortingCriterion == SortingCriterion.CATEGORY,
            onClick = {
                if (sortingCriterion == SortingCriterion.CATEGORY) {
                    onSortingCriterionChanged(SortingCriterion.NAME)
                } else {
                    onSortingCriterionChanged(SortingCriterion.CATEGORY)
                }
            },
            modifier = Modifier
                .widthIn(min = 150.dp)
        )
    }
}

@Composable
fun SortingButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
        ),
        modifier = modifier
    ) {
        Text(text = text, color = Color.White)
    }
}
