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
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData

fun createPieChartData(): PieChartData {
    val slices = listOf(
        PieChartData.Slice("Slice 1", 30f, Color.Red),
        PieChartData.Slice("Slice 2", 20f, Color.Blue),
        PieChartData.Slice("Slice 3", 25f, Color.Green),
        PieChartData.Slice("Slice 4", 15f, Color.Yellow),
        PieChartData.Slice("Slice 5", 10f, Color.Gray)
    )
    return PieChartData(slices = slices, plotType = PlotType.Pie)
}

fun createPieChartConfig(): PieChartConfig {
    return PieChartConfig(
        startAngle = 45f,
        showSliceLabels = true,
        sliceLabelTextSize = 14.sp,
        sliceLabelTextColor = Color.Black,
        sliceLabelTypeface = Typeface.DEFAULT,
        isAnimationEnable = true,
        animationDuration = 1000,
        strokeWidth = 2f,
        labelFontSize = 18.sp,
        labelTypeface = Typeface.DEFAULT_BOLD,
        labelVisible = true,
        labelType = PieChartConfig.LabelType.VALUE,
        labelColor = Color.Black,
        labelColorType = PieChartConfig.LabelColorType.SPECIFIED_COLOR,
        backgroundColor = Color.White,
        activeSliceAlpha = 1f,
        inActiveSliceAlpha = 0.5f,
        isEllipsizeEnabled = true,
        sliceMinTextWidthToEllipsize = 50.dp,
        sliceLabelEllipsizeAt = TextUtils.TruncateAt.END,
        chartPadding = 20,
        accessibilityConfig = AccessibilityConfig(
            chartDescription = "Donut Pie Chart"
        ),
        isSumVisible = false,
        sumUnit = "",
        isClickOnSliceEnabled = true
    )
}

@Composable
fun DonutPieChartExample() {
    val pieChartData = createPieChartData()
    val pieChartConfig = createPieChartConfig()

    DonutPieChart(
        modifier = Modifier
            .width(300.dp)
            .height(300.dp),
        pieChartData = pieChartData,
        pieChartConfig = pieChartConfig
    ) { slice ->
        // Handle click event on pie chart slice
    }
}

@Composable
fun PieChartExample() {
    val pieChartData = createPieChartData()
    val pieChartConfig = createPieChartConfig()

    PieChart(
        modifier = Modifier
            .width(300.dp)
            .height(300.dp),
        pieChartData = pieChartData,
        pieChartConfig = pieChartConfig
    ) { slice ->
        // Handle click event on pie chart slice
    }
}