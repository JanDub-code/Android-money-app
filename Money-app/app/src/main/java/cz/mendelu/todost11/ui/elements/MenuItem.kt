package cz.mendelu.todost11.ui.elements

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.random.Random
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import java.io.InputStream
import android.content.res.AssetManager
import android.graphics.BitmapFactory
import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.foundation.Image
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import java.io.File
import java.io.FileInputStream
import cz.mendelu.todost11.R



@SuppressLint("ResourceType")
@Composable
fun RecordItem(
    placeFrom: String,
    placeTo: String,
    numberOfKm: Double,
    priceOfFuel: Double,
    fuelType: String
) {
    val random = Random.Default
    val colors = listOf(
        Color(0xFF2196F3),
        Color(0xFF4CAF50),
        Color(0xFF9C27B0),
        Color(0xFFF44336),
        Color(0xFFBBDEFB),
        Color(0xFFC8E6C9),
        Color(0xFFE1BEE7),
        Color(0xFFF8BBD0)
    )

    Row(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Circle with random color and initial letter of placeFrom
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(color = colors.random(), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = placeFrom.first().toString(),
                color = Color.White
            )
        }

        var price: Double = numberOfKm*priceOfFuel

        // Description of the record
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Text(text = "$placeFrom - $placeTo")
            Text(text = "$numberOfKm km, $price Kč")
        }


        val icon = when (fuelType) {
            "Benzín" -> painterResource(id =  R.drawable.gas)
            "Nafta" -> painterResource(R.drawable.diesel)
            "Elektřina" -> painterResource(R.drawable.electric)
            else -> Icons.Default.AccountBox // Default icon in case of unknown fuel type
        }
        androidx.compose.material3.Icon(painter = icon as Painter, contentDescription ="icon" )
    }
}


@Preview
@Composable
fun PreviewRecordItem() {
    RecordItem(
        placeFrom = "Prague",
        placeTo = "Brno",
        numberOfKm = 200.0,
        priceOfFuel = 30.0,
        fuelType = "Benzín"
    )
}