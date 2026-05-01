package kz.example.verdict.ui.screens.item

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kz.example.verdict.data.db.entities.Item
import kz.example.verdict.data.db.entities.Verdict
import kz.example.verdict.data.repository.ItemRepository
import kz.example.verdict.data.repository.VerdictRepository
import kz.example.verdict.ui.components.CategoryPill
import kz.example.verdict.ui.components.ScoreBox
import kz.example.verdict.ui.theme.*
import kz.example.verdict.utils.DateHelper

@Composable
fun ItemDetailScreen(itemId: Int, navController: NavController, context: Context) {
    val itemRepo = remember { ItemRepository(context) }
    val verdictRepo = remember { VerdictRepository(context) }

    var item by remember { mutableStateOf<Item?>(null) }
    var verdicts by remember { mutableStateOf<List<Verdict>>(emptyList()) }

    LaunchedEffect(itemId) {
        itemRepo.getById(itemId) { item = it }
        verdictRepo.getByItem(itemId) { verdicts = it }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .background(SurfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            item?.let {
                if (it.imageUri.isNotEmpty()) {
                    AsyncImage(
                        model = it.imageUri,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text(it.category.take(1), fontSize = 64.sp, color = DimText)
                }
            }
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
                    .background(Background.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Cream)
            }
        }

        item?.let { it ->
            Column(modifier = Modifier.padding(24.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "${it.category.uppercase()} · ${it.subcategory.uppercase()}",
                            fontSize = 12.sp, color = DimText, letterSpacing = 1.sp, fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(it.name, fontSize = 26.sp, fontWeight = FontWeight.Black, color = Cream)
                        if (it.price.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(it.price, fontSize = 18.sp, color = Gold, fontWeight = FontWeight.Medium)
                        }
                    }
                    ScoreBox(score = it.avgScore, count = it.verdictCount)
                }

                Spacer(modifier = Modifier.height(16.dp))
                CategoryPill(text = it.category, selected = true)

                Spacer(modifier = Modifier.height(32.dp))
                Text("Вердикты (${verdicts.size})", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Cream)
                Spacer(modifier = Modifier.height(16.dp))

                verdicts.forEach { v ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(Surface)
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(DateHelper.relative(v.date), fontSize = 12.sp, color = DimText)
                            Text(v.score.toString(), fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Gold)
                        }
                        if (v.note.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(v.note, fontSize = 15.sp, color = Cream, lineHeight = 20.sp)
                        }
                        if (v.tags.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                v.tags.split(",").joinToString(" ") { "#$it" },
                                fontSize = 12.sp,
                                color = GoldDim
                            )
                        }
                    }
                }
            }
        } ?: Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Gold)
        }
    }
}
