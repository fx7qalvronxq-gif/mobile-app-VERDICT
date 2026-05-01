package kz.example.verdict.ui.screens.profile

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.example.verdict.data.repository.ItemRepository
import kz.example.verdict.data.repository.VerdictRepository
import kz.example.verdict.ui.theme.*
import kz.example.verdict.utils.DateHelper

@Composable
fun ProfileScreen(context: Context) {
    val vm = remember { ProfileViewModel(context) }
    val verdicts by vm.verdicts.collectAsState()
    val avg by vm.avgScore.collectAsState()
    val itemRepo = remember { ItemRepository(context) }
    val verdictRepo = remember { VerdictRepository(context) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        // Шапка
        item {
            Column(modifier = Modifier.padding(20.dp, 12.dp, 20.dp, 20.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(bottom = 20.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(SurfaceVariant)
                            .border(1.dp, Color(0xFF3A3428), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("КН", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Gold)
                    }
                    Column {
                        Text("Корнев Никита", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Cream)
                        Text("@nikita · Алматы", fontSize = 14.sp, color = DimText)
                    }
                }

                // Статы — 3 блока
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(
                        Pair(verdicts.size.toString(), "Вердиктов"),
                        Pair(if (avg == 0f) "—" else "%.1f".format(avg), "Средняя"),
                        Pair(verdicts.map { it.category }.distinct().size.toString(), "Категорий")
                    ).forEach { (value, label) ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(ScoreBg)
                                .border(0.5.dp, Color(0xFF252018), RoundedCornerShape(12.dp))
                                .padding(vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(value, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Gold)
                                Text(label, fontSize = 11.sp, color = DimText, modifier = Modifier.padding(top = 4.dp))
                            }
                        }
                    }
                }
            }

            // Divider
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.5.dp)
                    .background(Color(0xFF252018))
            )

            Text(
                "Мои вердикты",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Cream,
                modifier = Modifier.padding(20.dp, 16.dp, 20.dp, 12.dp)
            )
        }

        // Список вердиктов
        items(verdicts) { verdict ->
            var showDeleteDialog by remember { mutableStateOf(false) }

            if (showDeleteDialog) {
                AlertDialog(
                    onDismissRequest = { showDeleteDialog = false },
                    title = { Text("Удалить вердикт?") },
                    text = { Text("Это действие нельзя будет отменить.") },
                    confirmButton = {
                        TextButton(onClick = {
                            // Сначала удаляем отзывы, связанные с этим товаром
                            verdictRepo.deleteByItemId(verdict.itemId) {
                                // Затем удаляем сам товар
                                itemRepo.delete(verdict.itemId) {
                                    vm.refresh() // Обновляем список в ViewModel
                                }
                            }
                            showDeleteDialog = false
                        }) {
                            Text("Удалить", color = Color.Red)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteDialog = false }) {
                            Text("Отмена", color = Cream)
                        }
                    },
                    containerColor = Surface,
                    titleContentColor = Cream,
                    textContentColor = DimText
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Surface)
                    .border(0.5.dp, Border, RoundedCornerShape(16.dp))
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Иконка категории
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(SurfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Text(verdict.category.take(1), fontSize = 18.sp, color = DimText)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(verdict.itemName, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Cream)
                    Text(
                        "${verdict.category} · ${DateHelper.relative(verdict.date)}",
                        fontSize = 12.sp,
                        color = DimText
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        verdict.score.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Gold
                    )
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red.copy(alpha = 0.6f), modifier = Modifier.size(20.dp))
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}
