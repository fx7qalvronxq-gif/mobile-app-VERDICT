package kz.example.verdict.ui.screens.saved

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kz.example.verdict.data.db.entities.SavedItem
import kz.example.verdict.data.repository.SavedRepository
import kz.example.verdict.ui.theme.*

@Composable
fun SavedScreen(navController: NavController, context: Context) {
    val repo = remember { SavedRepository(context) }
    var items by remember { mutableStateOf<List<SavedItem>>(emptyList()) }

    LaunchedEffect(Unit) {
        repo.getAll { items = it }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(20.dp)
    ) {
        Text(
            text = "СОХРАНЁННОЕ",
            fontSize = 9.sp,
            color = DimText,
            letterSpacing = 1.sp
        )
        Text(
            text = "Избранное",
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium,
            color = Cream,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        if (items.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        modifier = Modifier.size(32.dp),
                        tint = DimText
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Ничего не сохранено", fontSize = 14.sp, color = DimText)
                    Text("Нажми ♡ на карточке", fontSize = 11.sp, color = DimText.copy(alpha = 0.6f))
                }
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(items) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(Surface)
                            .border(0.5.dp, Border, RoundedCornerShape(14.dp))
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(SurfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(item.category.take(1), fontSize = 14.sp, color = DimText)
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.itemName, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Cream)
                            Text(item.category, fontSize = 10.sp, color = DimText)
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(ScoreBg)
                                .border(0.5.dp, Border, RoundedCornerShape(8.dp))
                                .padding(horizontal = 10.dp, vertical = 5.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "%.1f".format(item.avgScore),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Gold
                            )
                        }
                    }
                }
            }
        }
    }
}
