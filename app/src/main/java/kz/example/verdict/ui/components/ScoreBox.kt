package kz.example.verdict.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.example.verdict.ui.theme.*

@Composable
fun ScoreBox(score: Float, count: Int, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(ScoreBg)
            .border(0.5.dp, Border, RoundedCornerShape(12.dp))
            .padding(horizontal = 14.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = if (score == 0f) "—" else "%.1f".format(score),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Gold,
                lineHeight = 22.sp
            )
            Text(
                text = "$count отз.",
                fontSize = 11.sp,
                color = DimText,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
