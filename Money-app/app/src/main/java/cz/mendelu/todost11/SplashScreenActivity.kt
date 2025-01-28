package cz.mendelu.todost11

import dagger.hilt.android.AndroidEntryPoint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.*

@AndroidEntryPoint
class SplashScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            SplashScreenContent()
        }

        CoroutineScope(Dispatchers.Main).launch {
            delay(1000L) // Časovač na 2 sekundy
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            finish()
        }
    }
}

@Composable
fun SplashScreenContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF8BC34A)), // Zelené pozadí
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.bank), // Nahraďte 'your_image' názvem vašeho obrázku v assets
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )
    }
}