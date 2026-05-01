package kz.example.verdict.ui.screens.add

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kz.example.verdict.data.db.entities.Item
import kz.example.verdict.data.db.entities.Verdict
import kz.example.verdict.data.repository.ItemRepository
import kz.example.verdict.data.repository.VerdictRepository
import kz.example.verdict.utils.DateHelper
import java.io.File
import java.io.FileOutputStream

class AddVerdictViewModel(context: Context) : ViewModel() {

    private val itemRepo = ItemRepository(context)
    private val verdictRepo = VerdictRepository(context)
    private val appContext = context.applicationContext

    val name = MutableStateFlow("")
    val category = MutableStateFlow("Еда")
    val price = MutableStateFlow("")
    val score = MutableStateFlow(0)
    val note = MutableStateFlow("")
    val tags = MutableStateFlow<List<String>>(emptyList())
    val imageUri = MutableStateFlow<Uri?>(null)

    private val _saved = MutableStateFlow(false)
    val saved: StateFlow<Boolean> = _saved

    private fun saveImageToInternalStorage(uri: Uri): String {
        return try {
            val inputStream = appContext.contentResolver.openInputStream(uri)
            val file = File(appContext.filesDir, "image_${System.currentTimeMillis()}.jpg")
            val outputStream = FileOutputStream(file)
            inputStream?.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    fun save() {
        if (name.value.isBlank() || score.value == 0) return
        viewModelScope.launch(Dispatchers.IO) {
            val internalPath = imageUri.value?.let { saveImageToInternalStorage(it) } ?: ""
            val now = DateHelper.now()
            
            itemRepo.insert(
                Item(
                    name = name.value,
                    category = category.value,
                    price = price.value,
                    avgScore = score.value.toFloat(),
                    verdictCount = 1,
                    imageUri = internalPath,
                    createdAt = now
                )
            ) { itemId ->
                verdictRepo.insert(
                    Verdict(
                        itemId = itemId.toInt(),
                        itemName = name.value,
                        category = category.value,
                        score = score.value,
                        note = note.value,
                        tags = tags.value.joinToString(","),
                        date = now
                    )
                )
                _saved.value = true
            }
        }
    }
}
