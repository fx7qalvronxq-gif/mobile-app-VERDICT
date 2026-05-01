package kz.example.verdict.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.example.verdict.ui.theme.*

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagEditor(
    tags: List<String>,
    suggestions: List<String>,
    onTagsChanged: (List<String>) -> Unit
) {
    var input by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        if (tags.isNotEmpty()) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                tags.forEach { tag ->
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Gold.copy(alpha = 0.15f))
                            .border(0.5.dp, GoldDim.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                            .padding(start = 12.dp, end = 8.dp, top = 6.dp, bottom = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(tag, fontSize = 14.sp, color = GoldDim)
                        Icon(
                            Icons.Default.Close,
                            contentDescription = null,
                            tint = DimText,
                            modifier = Modifier
                                .size(16.dp)
                                .clickable {
                                    onTagsChanged(tags - tag)
                                }
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceVariant)
                .border(0.5.dp, Border, RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BasicTextField(
                value = input,
                onValueChange = { input = it },
                textStyle = TextStyle(fontSize = 15.sp, color = Cream),
                singleLine = true,
                modifier = Modifier.weight(1f),
                decorationBox = { inner ->
                    if (input.isEmpty()) Text("Свой тег...", fontSize = 15.sp, color = DimText)
                    inner()
                }
            )
            if (input.isNotBlank()) {
                Text(
                    "+ Добавить",
                    fontSize = 14.sp,
                    color = Gold,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable {
                            if (!tags.contains(input.trim())) {
                                onTagsChanged(tags + input.trim())
                            }
                            input = ""
                        }
                )
            }
        }

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            suggestions.filter { !tags.contains(it) }.take(5).forEach { suggestion ->
                Text(
                    text = "+ $suggestion",
                    fontSize = 13.sp,
                    color = DimText,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(SurfaceVariant)
                        .border(0.5.dp, Border, RoundedCornerShape(20.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                        .clickable { onTagsChanged(tags + suggestion) }
                )
            }
        }
    }
}
