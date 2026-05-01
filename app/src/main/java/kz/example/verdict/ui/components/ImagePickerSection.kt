package kz.example.verdict.ui.components

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import kz.example.verdict.ui.theme.*
import kz.example.verdict.utils.ImageHelper

@Composable
fun ImagePickerSection(
    imageUri: Uri?,
    onImageSelected: (Uri) -> Unit
) {
    val context = LocalContext.current
    var cameraUri by remember { mutableStateOf<Uri?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && cameraUri != null) {
            onImageSelected(cameraUri!!)
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { onImageSelected(it) }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            val uri = ImageHelper.createImageUri(context)
            if (uri != null) {
                cameraUri = uri
                cameraLauncher.launch(uri)
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            containerColor = Surface,
            title = {
                Text("Добавить фото", color = Cream, fontSize = 16.sp)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceVariant)
                            .clickable {
                                showDialog = false
                                val hasCameraPermission = ContextCompat.checkSelfPermission(
                                    context, Manifest.permission.CAMERA
                                ) == PackageManager.PERMISSION_GRANTED
                                if (hasCameraPermission) {
                                    val uri = ImageHelper.createImageUri(context)
                                    if (uri != null) {
                                        cameraUri = uri
                                        cameraLauncher.launch(uri)
                                    }
                                } else {
                                    permissionLauncher.launch(Manifest.permission.CAMERA)
                                }
                            }
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("📷", fontSize = 20.sp)
                        Column {
                            Text("Камера", fontSize = 15.sp, color = Cream)
                            Text("Сфотографировать", fontSize = 12.sp, color = DimText)
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceVariant)
                            .clickable {
                                showDialog = false
                                galleryLauncher.launch("image/*")
                            }
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("🖼️", fontSize = 20.sp)
                        Column {
                            Text("Галерея", fontSize = 15.sp, color = Cream)
                            Text("Выбрать с телефона", fontSize = 12.sp, color = DimText)
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Отмена", color = DimText, fontSize = 14.sp)
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceVariant)
            .border(0.5.dp, Border, RoundedCornerShape(16.dp))
            .clickable { showDialog = true },
        contentAlignment = Alignment.Center
    ) {
        if (imageUri != null) {
            AsyncImage(
                model = imageUri,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(12.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Background.copy(alpha = 0.8f))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text("Изменить", fontSize = 12.sp, color = Gold)
            }
        } else {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.Add, contentDescription = null, tint = DimText, modifier = Modifier.size(32.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Text("Добавить фото", fontSize = 14.sp, color = DimText)
                Text("Камера или галерея", fontSize = 11.sp, color = DimText.copy(alpha = 0.6f))
            }
        }
    }
}
