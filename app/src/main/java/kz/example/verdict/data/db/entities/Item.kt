package kz.example.verdict.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val category: String,
    val subcategory: String = "",
    val price: String = "",
    val avgScore: Float = 0f,
    val verdictCount: Int = 0,
    val imageUri: String = "",
    val createdAt: String = ""
)
