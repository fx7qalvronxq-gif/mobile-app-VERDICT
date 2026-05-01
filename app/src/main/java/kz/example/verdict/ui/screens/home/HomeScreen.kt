package kz.example.verdict.ui.screens.home

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kz.example.verdict.ui.components.CategoryPill
import kz.example.verdict.ui.components.VerdictCard
import kz.example.verdict.ui.navigation.Screen
import kz.example.verdict.ui.theme.*
import kz.example.verdict.utils.DateHelper
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeScreen(navController: NavController, context: Context) {
    val vm = remember { HomeViewModel(context) }
    val items by vm.items.collectAsState()
    val trending by vm.trending.collectAsState()
    val selectedCategory by vm.selectedCategory.collectAsState()
    val searchQuery by vm.searchQuery.collectAsState()

    val categories = listOf("Всё", "Еда", "Товары", "Места", "Услуги")
    
    val currentMonth = remember {
        val sdf = SimpleDateFormat("MMMM", Locale("ru"))
        val month = sdf.format(Date())
        // Делаем первую букву заглавной, остальные строчные (например, Апрель)
        month.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale("ru")) else it.toString() }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "АЛМАТЫ · $currentMonth",
                    fontSize = 11.sp,
                    color = DimText,
                    letterSpacing = 1.2.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Verdict",
                    fontSize = 32.sp,
                    fontFamily = FontFamily.Serif,
                    color = Cream,
                    letterSpacing = (-1).sp,
                    lineHeight = 32.sp,
                    fontWeight = FontWeight.Black
                )
            }
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(SurfaceVariant, RoundedCornerShape(50)),
                contentAlignment = Alignment.Center
            ) {
                Text("КН", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Gold)
            }
        }

        // Search bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceVariant)
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = DimText
            )
            BasicTextField(
                value = searchQuery,
                onValueChange = { vm.search(it) },
                textStyle = TextStyle(fontSize = 15.sp, color = Cream),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { inner ->
                    if (searchQuery.isEmpty()) {
                        Text(
                            "Блюда, товары, места, услуги...",
                            fontSize = 15.sp,
                            color = DimText
                        )
                    }
                    inner()
                }
            )
        }

        // Category filters
        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            items(categories) { cat ->
                CategoryPill(
                    text = cat,
                    selected = cat == selectedCategory,
                    modifier = Modifier.clickable { vm.filterBy(cat) }
                )
            }
        }

        // Результаты поиска или основная лента
        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (searchQuery.isNotEmpty()) {
                Text(
                    "Результаты: ${items.size}",
                    fontSize = 14.sp,
                    color = DimText,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            items.forEach { item ->
                VerdictCard(item = item) {
                    navController.navigate(Screen.ItemDetail.createRoute(item.id))
                }
            }

            if (items.isEmpty() && (searchQuery.isNotEmpty() || selectedCategory != "Всё")) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val msg = if (searchQuery.isNotEmpty()) "Ничего не найдено по запросу «$searchQuery»"
                             else "В категории «$selectedCategory» пока нет вердиктов"
                    Text(msg, fontSize = 16.sp, color = DimText)
                }
            }

            // Trending
            if (searchQuery.isEmpty() && selectedCategory == "Всё" && trending.isNotEmpty()) {
                Text(
                    "Популярное сегодня",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Cream,
                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                )
                trending.chunked(2).forEach { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        row.forEach { item ->
                            Box(modifier = Modifier.weight(1f)) {
                                SmallCard(item = item) {
                                    navController.navigate(Screen.ItemDetail.createRoute(item.id))
                                }
                            }
                        }
                        if (row.size == 1) Box(modifier = Modifier.weight(1f))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun SmallCard(
    item: kz.example.verdict.data.db.entities.Item,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Surface)
            .clickable { onClick() }
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
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
                    Text(item.category.take(1), fontSize = 24.sp, color = DimText)
                }
            }
            Column(modifier = Modifier.padding(10.dp)) {
                Text(item.category.uppercase(), fontSize = 9.sp, color = DimText, fontWeight = FontWeight.Bold)
                Text(item.name, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Cream, maxLines = 1)
                
                // Отображение времени добавления
                if (item.createdAt.isNotEmpty()) {
                    Text(
                        text = DateHelper.relative(item.createdAt),
                        fontSize = 10.sp,
                        color = GoldDim,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("%.1f".format(item.avgScore), fontSize = 13.sp, color = Gold, fontWeight = FontWeight.Bold)
                    if (item.price.isNotEmpty())
                        Text(item.price, fontSize = 11.sp, color = DimText)
                }
            }
        }
    }
}
