package cz.mendelu.todost11.ui.elements

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.mendelu.todost11.R
import cz.mendelu.todost11.ui.screens.SettingsViewModel

@Composable
fun InfoElement(
    icon: ImageVector,
    value: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = hiltViewModel<SettingsViewModel>()

    Box(
        modifier = modifier
            .clickable { onClick() }
            .border(width = 1.dp, color = Color.Black)
            .padding(8.dp) // Optional: To ensure there's some padding inside the border
    ) {
        Column {
            Row {
                Text(text = stringResource(id = R.string.date))
            }
            Row {
                Icon(imageVector = icon, contentDescription = null)
                Column {
                    Text(text = value ?: stringResource(id = R.string.date))
                }
            }
        }
    }
}