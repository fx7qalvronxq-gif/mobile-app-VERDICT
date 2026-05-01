package kz.example.verdict.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kz.example.verdict.data.db.entities.Item

@Dao
interface ItemDao {

    @Insert
    fun insert(item: Item): Long

    @Update
    fun update(item: Item)

    @Query("SELECT * FROM items ORDER BY verdictCount DESC")
    fun getAll(): List<Item>

    @Query("SELECT * FROM items WHERE category = :category ORDER BY avgScore DESC")
    fun getByCategory(category: String): List<Item>

    @Query("SELECT * FROM items WHERE name LIKE '%' || :query || '%'")
    fun search(query: String): List<Item>

    @Query("SELECT * FROM items WHERE id = :id")
    fun getById(id: Int): Item?

    @Query("SELECT * FROM items ORDER BY verdictCount DESC LIMIT 4")
    fun getTrending(): List<Item>

    @Query("DELETE FROM items WHERE id = :id")
    fun deleteById(id: Int)
}
