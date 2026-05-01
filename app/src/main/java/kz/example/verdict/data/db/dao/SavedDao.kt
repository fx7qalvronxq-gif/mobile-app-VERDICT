package kz.example.verdict.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kz.example.verdict.data.db.entities.SavedItem

@Dao
interface SavedDao {

    @Insert
    fun save(savedItem: SavedItem)

    @Query("DELETE FROM saved_items WHERE itemId = :itemId")
    fun unsave(itemId: Int)

    @Query("SELECT * FROM saved_items ORDER BY id DESC")
    fun getAll(): List<SavedItem>

    @Query("SELECT COUNT(*) FROM saved_items WHERE itemId = :itemId")
    fun isSaved(itemId: Int): Int
}
