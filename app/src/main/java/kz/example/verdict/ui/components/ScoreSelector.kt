package kz.example.verdict.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
fun ScoreSelector(
    selectedScore: Int,
    onScoreSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        (1..10).forEach { score ->
            val selected = score <= selectedScore
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (selected) Gold else SurfaceVariant)
                    .border(
                        0.5.dp,
                        if (selected) Gold else Border,
                        RoundedCornerShape(8.dp)
                    )
                    .clickable { onScoreSelected(score) },
                contentAlignment = Alignment.Center
            ) {
                if (selected) {
                    Text(
                        text = score.toString(),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Background
                    )
                }
            }
        }
    }
}
