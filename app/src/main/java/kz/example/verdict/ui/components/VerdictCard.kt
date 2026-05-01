package kz.example.verdict.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kz.example.verdict.data.db.entities.Item
import kz.example.verdict.data.db.entities.SavedItem
import kz.example.verdict.data.repository.SavedRepository
import kz.example.verdict.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun VerdictCard(item: Item, onClick: () -> Unit) {
    val context = LocalContext.current
    val savedRepo = remember { SavedRepository(context) }
    var isSaved by remember { mutableStateOf(false) }

    LaunchedEffect(item.id) {
        savedRepo.isSaved(item.id) { isSaved = it }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Surface)
            .border(0.5.dp, Border, RoundedCornerShape(16.dp))
            .clickable { onClick() }
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .background(SurfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                if (item.imageUri.isNotEmpty()) {
                    AsyncImage(
                        model = item.imageUri,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text(
                        text = item.category.take(1),
                        fontSize = 40.sp,
                        color = DimText
                    )
                }
                
                // Кнопка сохранения
                IconButton(
                    onClick = {
                        if (isSaved) {
                            savedRepo.unsave(item.id)
                            isSaved = false
                        } else {
                            val savedItem = SavedItem(
                                itemId = item.id,
                                itemName = item.name,
                                category = item.category,
                                avgScore = item.avgScore,
                                savedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                            )
                            savedRepo.save(savedItem)
                            isSaved = true
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                ) {
                    Icon(
                        imageVector = if (isSaved) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = if (isSaved) Color.Red else Color.White
                    )
                }
            }
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "${item.category.uppercase()} · ${item.subcategory.uppercase()}",
                        fontSize = 11.sp,
                        color = DimText,
                        letterSpacing = 0.8.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Cream
                    )
                    if (item.price.isNotEmpty()) {
                        Text(
                            text = item.price,
                            fontSize = 14.sp,
                            color = DimText
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    CategoryPill(text = item.category, selected = true)
                }
                Spacer(modifier = Modifier.width(12.dp))
                ScoreBox(score = item.avgScore, count = item.verdictCount)
            }
        }
    }
}
