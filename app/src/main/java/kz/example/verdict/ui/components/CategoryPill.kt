package kz.example.verdict.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.example.verdict.ui.theme.*

@Composable
fun CategoryPill(
    text: String,
    selected: Boolean = false,
    modifier: Modifier = Modifier
) {
    val bg = if (selected) Color(0xFF241E10) else SurfaceVariant
    val textColor = if (selected) GoldDim else DimText
    val borderColor = if (selected) Color(0xFF3A2E10) else Border

    Text(
        text = text,
        fontSize = 13.sp,
        color = textColor,
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(bg)
            .border(0.5.dp, borderColor, RoundedCornerShape(20.dp))
            .padding(horizontal = 14.dp, vertical = 6.dp)
    )
}
