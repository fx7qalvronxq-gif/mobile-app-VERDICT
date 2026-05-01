package kz.example.verdict.ui.screens.explore

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kz.example.verdict.ui.theme.*
import kz.example.verdict.utils.Constants

@Composable
fun ExploreScreen(navController: NavController, context: Context) {
    var selected by remember { mutableStateOf("") }

    // Используем базовые иконки, чтобы избежать ошибок компиляции до синхронизации
    val categoryIcons: Map<String, ImageVector> = mapOf(
        "Еда" to Icons.Default.RestaurantMenu,
        "Техника" to Icons.Default.Settings,
        "Место" to Icons.Default.LocationOn,
        "Услуга" to Icons.Default.Build,
        "Товар" to Icons.Default.ShoppingBasket,
        "Фильм" to Icons.Default.PlayArrow,
        "Книга" to Icons.Default.Book
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(20.dp)
    ) {
        // Header
        Text(
            text = "КАТЕГОРИИ",
            fontSize = 9.sp,
            color = DimText,
            letterSpacing = 1.sp
        )
        Text(
            text = "Обзор",
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium,
            color = Cream,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(Constants.CATEGORIES) { cat ->
                val isSelected = cat == selected
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSelected) androidx.compose.ui.graphics.Color(0xFF241F14) else SurfaceVariant)
                        .border(
                            0.5.dp,
                            if (isSelected) androidx.compose.ui.graphics.Color(0xFF3A3428) else Border,
                            RoundedCornerShape(12.dp)
                        )
                        .clickable { selected = cat }
                        .padding(12.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        categoryIcons[cat]?.let { icon ->
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = if (isSelected) Gold else DimText,
                                modifier = Modifier.size(24.dp)
                            )
                        } ?: Icon(
                            imageVector = Icons.Default.Label,
                            contentDescription = null,
                            tint = if (isSelected) Gold else DimText,
                            modifier = Modifier.size(24.dp)
                        )

                        Text(
                            text = cat,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (isSelected) Gold else Cream
                        )
                    }
                }
            }
        }
    }
}
