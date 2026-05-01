package kz.example.verdict.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "verdicts")
data class Verdict(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val itemId: Int,
    val itemName: String,
    val category: String,
    val score: Int,
    val note: String = "",
    val tags: String = "",
    val date: String
)
