package cz.mendelu.todost11.ui.elements

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer

@Composable
@ExperimentalMaterial3Api
fun CustomDatePickerDialog(
    modifier: Modifier = Modifier,
    date: Long?,
    onDismiss: () -> Unit,
    onDateSelected: (Long) -> Unit
) {
    val state = rememberDatePickerState(initialSelectedDateMillis = date)

    DatePickerDialog(
        modifier = modifier,
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(
                enabled = state.selectedDateMillis != null,
                onClick = { onDateSelected(state.selectedDateMillis!!) }) {
                Text(text = "Select")
            }

        },

        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text(text = "Cancel")
            }

        }
    ) {
        DatePicker(state = state)
    }

}
