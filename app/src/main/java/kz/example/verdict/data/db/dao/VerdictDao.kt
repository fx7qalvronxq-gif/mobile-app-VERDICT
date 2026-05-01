package kz.example.verdict.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kz.example.verdict.data.db.entities.Verdict

@Dao
interface VerdictDao {

    @Insert
    fun insert(verdict: Verdict)

    @Query("SELECT * FROM verdicts WHERE itemId = :itemId ORDER BY id DESC")
    fun getByItem(itemId: Int): List<Verdict>

    @Query("SELECT * FROM verdicts ORDER BY id DESC")
    fun getAll(): List<Verdict>

    @Query("SELECT AVG(score) FROM verdicts WHERE itemId = :itemId")
    fun getAvgScore(itemId: Int): Float

    @Query("SELECT COUNT(*) FROM verdicts WHERE itemId = :itemId")
    fun getCount(itemId: Int): Int

    @Query("DELETE FROM verdicts WHERE itemId = :itemId")
    fun deleteByItemId(itemId: Int)
}
