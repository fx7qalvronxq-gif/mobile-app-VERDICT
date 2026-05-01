package kz.example.verdict.ui.screens.add

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kz.example.verdict.ui.components.CategoryPill
import kz.example.verdict.ui.components.ImagePickerSection
import kz.example.verdict.ui.components.ScoreSelector
import kz.example.verdict.ui.components.TagEditor
import kz.example.verdict.ui.navigation.Screen
import kz.example.verdict.ui.theme.*
import kz.example.verdict.utils.Constants

@Composable
fun AddVerdictScreen(navController: NavController, context: Context) {
    val vm = remember { AddVerdictViewModel(context) }
    val name by vm.name.collectAsState()
    val category by vm.category.collectAsState()
    val price by vm.price.collectAsState()
    val score by vm.score.collectAsState()
    val note by vm.note.collectAsState()
    val tags by vm.tags.collectAsState()
    val imageUri by vm.imageUri.collectAsState()
    val saved by vm.saved.collectAsState()

    LaunchedEffect(saved) {
        if (saved) navController.navigate(Screen.Home.route)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text("Добавить вердикт", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Cream)

        ImagePickerSection(
            imageUri = imageUri,
            onImageSelected = { vm.imageUri.value = it }
        )

        Label("НАЗВАНИЕ")
        OutlinedTextField(
            value = name,
            onValueChange = { vm.name.value = it },
            placeholder = { Text("Что оцениваем?", color = DimText, fontSize = 16.sp) },
            modifier = Modifier.fillMaxWidth(),
            colors = fieldColors(),
            shape = RoundedCornerShape(12.dp),
            textStyle = LocalTextStyle.current.copy(fontSize = 16.sp)
        )

        Label("КАТЕГОРИЯ")
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Constants.CATEGORIES.take(4).forEach { cat ->
                CategoryPill(
                    text = cat,
                    selected = cat == category,
                    modifier = Modifier.clickable { vm.category.value = cat }
                )
            }
        }

        Label("ЦЕНА (необязательно)")
        OutlinedTextField(
            value = price,
            onValueChange = { vm.price.value = it },
            placeholder = { Text("2 400 ₸", color = DimText, fontSize = 16.sp) },
            modifier = Modifier.fillMaxWidth(),
            colors = fieldColors(),
            shape = RoundedCornerShape(12.dp),
            textStyle = LocalTextStyle.current.copy(fontSize = 16.sp)
        )

        Label("ОЦЕНКА · $score / 10")
        ScoreSelector(selectedScore = score) { vm.score.value = it }

        Label("ТЕГИ")
        TagEditor(
            tags = tags,
            suggestions = Constants.FOOD_TAGS,
            onTagsChanged = { vm.tags.value = it }
        )

        Label("ЗАМЕТКА")
        OutlinedTextField(
            value = note,
            onValueChange = { vm.note.value = it },
            placeholder = { Text("Что запомнилось?", color = DimText, fontSize = 16.sp) },
            modifier = Modifier.fillMaxWidth().height(120.dp),
            colors = fieldColors(),
            shape = RoundedCornerShape(12.dp),
            maxLines = 6,
            textStyle = LocalTextStyle.current.copy(fontSize = 16.sp)
        )

        Button(
            onClick = { vm.save() },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Gold),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Сохранить вердикт", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Background)
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun Label(text: String) {
    Text(text, fontSize = 13.sp, color = DimText, letterSpacing = 1.sp, fontWeight = FontWeight.Medium)
}

@Composable
private fun fieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = Gold,
    unfocusedBorderColor = Border,
    focusedTextColor = Cream,
    unfocusedTextColor = Cream,
    cursorColor = Gold,
    focusedContainerColor = SurfaceVariant,
    unfocusedContainerColor = SurfaceVariant
)
