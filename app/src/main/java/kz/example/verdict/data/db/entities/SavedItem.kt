package kz.example.verdict.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_items")
data class SavedItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val itemId: Int,
    val itemName: String,
    val category: String,
    val avgScore: Float,
    val savedDate: String
)
