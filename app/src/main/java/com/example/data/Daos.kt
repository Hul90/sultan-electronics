package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.model.AppSettingEntity
import com.example.model.CalculationHistoryEntity
import com.example.model.FavoriteEntity
import com.example.model.PcbProjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Query("SELECT * FROM calculation_history ORDER BY timestamp DESC")
    fun getAllHistory(): Flow<List<CalculationHistoryEntity>>

    @Query("SELECT * FROM calculation_history ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentHistory(limit: Int = 10): Flow<List<CalculationHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(item: CalculationHistoryEntity): Long

    @Query("DELETE FROM calculation_history WHERE id = :id")
    suspend fun deleteHistoryById(id: Long)

    @Query("DELETE FROM calculation_history")
    suspend fun clearAllHistory()
}

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorites ORDER BY timestamp DESC")
    fun getAllFavorites(): Flow<List<FavoriteEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE id = :id)")
    fun isFavoriteFlow(id: String): Flow<Boolean>

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE id = :id)")
    suspend fun isFavorite(id: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE id = :id")
    suspend fun removeFavoriteById(id: String)

    @Query("DELETE FROM favorites")
    suspend fun clearAllFavorites()
}

@Dao
interface PcbProjectDao {
    @Query("SELECT * FROM pcb_projects ORDER BY updatedAt DESC")
    fun getAllProjects(): Flow<List<PcbProjectEntity>>

    @Query("SELECT * FROM pcb_projects WHERE id = :id")
    suspend fun getProjectById(id: Long): PcbProjectEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(project: PcbProjectEntity): Long

    @Update
    suspend fun updateProject(project: PcbProjectEntity)

    @Query("DELETE FROM pcb_projects WHERE id = :id")
    suspend fun deleteProjectById(id: Long)
}

@Dao
interface SettingsDao {
    @Query("SELECT * FROM app_settings")
    fun getAllSettings(): Flow<List<AppSettingEntity>>

    @Query("SELECT value FROM app_settings WHERE `key` = :key")
    suspend fun getSetting(key: String): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSetting(setting: AppSettingEntity)
}

@Dao
interface StockDao {
    @Query("SELECT * FROM stock_items ORDER BY category ASC, partNumber ASC")
    fun getAllStock(): Flow<List<com.example.model.StockItemEntity>>

    @Query("SELECT * FROM stock_items WHERE id = :id")
    suspend fun getById(id: Long): com.example.model.StockItemEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: com.example.model.StockItemEntity): Long

    @Update
    suspend fun update(item: com.example.model.StockItemEntity)

    @Query("DELETE FROM stock_items WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("UPDATE stock_items SET quantity = quantity + :delta, updatedAt = :updatedAt WHERE id = :id")
    suspend fun adjustQuantity(id: Long, delta: Int, updatedAt: Long = System.currentTimeMillis())
}

@Dao
interface WorkbenchDao {
    @Query("SELECT * FROM workbench_projects ORDER BY updatedAt DESC")
    fun getAllProjects(): Flow<List<com.example.model.WorkbenchProjectEntity>>

    @Query("SELECT * FROM workbench_projects WHERE id = :id")
    suspend fun getById(id: Long): com.example.model.WorkbenchProjectEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(project: com.example.model.WorkbenchProjectEntity): Long

    @Update
    suspend fun update(project: com.example.model.WorkbenchProjectEntity)

    @Query("DELETE FROM workbench_projects WHERE id = :id")
    suspend fun delete(id: Long)
}
