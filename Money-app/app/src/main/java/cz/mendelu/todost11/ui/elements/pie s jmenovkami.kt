package cz.mendelu.todost11.ui.elements

import android.graphics.Typeface
import android.text.TextUtils
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.model.AccessibilityConfig
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.bubblechart.model.BubbleChartData
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData

fun createPieChartData1(): PieChartData {
    val slices = listOf(
        PieChartData.Slice("VÝDAJE", 30f, Color.Red),
        PieChartData.Slice("PŘIJMY", 20f, Color.Blue),

    )
    return PieChartData(slices = slices, plotType = PlotType.Pie)
}

fun createPieChartConfig1(): PieChartConfig {
    return PieChartConfig(
        labelVisible = true,
        strokeWidth = 120f,
        activeSliceAlpha = .9f,
        isAnimationEnable = true
    )
}