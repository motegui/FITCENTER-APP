package ar.edu.itba.hci.fitcenter.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.itba.hci.fitcenter.R
import ar.edu.itba.hci.fitcenter.ui.theme.FitcenterTheme

@Composable
fun SortingButtons(
    sortingCriterion: SortingCriterion,
    onSortingCriterionChanged: (SortingCriterion) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SortingButton(
                text = stringResource(R.string.date),
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
                modifier = Modifier.weight(1f)
            )
            SortingButton(
                text = stringResource(R.string.difficulty),
                isSelected = sortingCriterion == SortingCriterion.DIFFICULTY,
                onClick = {
                    if (sortingCriterion == SortingCriterion.DIFFICULTY) {
                        onSortingCriterionChanged(SortingCriterion.NAME)
                    } else {
                        onSortingCriterionChanged(SortingCriterion.DIFFICULTY)
                    }
                },
                modifier = Modifier.weight(1f)
            )
            SortingButton(
                text = stringResource(R.string.category),
                isSelected = sortingCriterion == SortingCriterion.CATEGORY,
                onClick = {
                    if (sortingCriterion == SortingCriterion.CATEGORY) {
                        onSortingCriterionChanged(SortingCriterion.NAME)
                    } else {
                        onSortingCriterionChanged(SortingCriterion.CATEGORY)
                    }
                },
                modifier = Modifier.weight(1f)
            )
        }
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
            text = stringResource(R.string.date),
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
            modifier = Modifier.requiredWidth(150.dp)

        )
        Spacer(modifier = Modifier.width(8.dp))
        SortingButton(
            text = stringResource(R.string.difficulty),
            isSelected = sortingCriterion == SortingCriterion.DIFFICULTY,
            onClick = {
                if (sortingCriterion == SortingCriterion.DIFFICULTY) {
                    onSortingCriterionChanged(SortingCriterion.NAME)
                } else {
                    onSortingCriterionChanged(SortingCriterion.DIFFICULTY)
                }
            },
            modifier = Modifier.requiredWidth(150.dp)

        )
        Spacer(modifier = Modifier.width(8.dp))
        SortingButton(
            text = stringResource(R.string.category),
            isSelected = sortingCriterion == SortingCriterion.CATEGORY,
            onClick = {
                if (sortingCriterion == SortingCriterion.CATEGORY) {
                    onSortingCriterionChanged(SortingCriterion.NAME)
                } else {
                    onSortingCriterionChanged(SortingCriterion.CATEGORY)
                }
            },
            modifier = Modifier.requiredWidth(150.dp)

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
        Text(
            text = text,
            color = if (isSelected) Color.Black else Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSortingButtons() {
    FitcenterTheme {
        SortingButtons(SortingCriterion.DATE) {}
    }
}
