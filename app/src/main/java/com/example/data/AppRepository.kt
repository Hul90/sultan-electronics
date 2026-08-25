package com.example.data

import com.example.model.AppSettingEntity
import com.example.model.CalculationHistoryEntity
import com.example.model.FavoriteEntity
import com.example.model.PcbProjectEntity
import com.example.model.StockItemEntity
import com.example.model.WorkbenchProjectEntity
import kotlinx.coroutines.flow.Flow

class AppRepository(private val db: AppDatabase) {
    val allHistory: Flow<List<CalculationHistoryEntity>> = db.historyDao().getAllHistory()
    val recentHistory: Flow<List<CalculationHistoryEntity>> = db.historyDao().getRecentHistory(10)
    val allFavorites: Flow<List<FavoriteEntity>> = db.favoritesDao().getAllFavorites()
    val allProjects: Flow<List<PcbProjectEntity>> = db.pcbProjectDao().getAllProjects()
    val allSettings: Flow<List<AppSettingEntity>> = db.settingsDao().getAllSettings()
    val allStock: Flow<List<StockItemEntity>> = db.stockDao().getAllStock()
    val allWorkbenchProjects: Flow<List<WorkbenchProjectEntity>> = db.workbenchDao().getAllProjects()

    fun isFavorite(id: String): Flow<Boolean> = db.favoritesDao().isFavoriteFlow(id)

    suspend fun toggleFavorite(
        id: String,
        itemType: String,
        title: String,
        subtitle: String,
        routeOrKey: String,
        iconName: String = ""
    ) {
        val exists = db.favoritesDao().isFavorite(id)
        if (exists) {
            db.favoritesDao().removeFavoriteById(id)
        } else {
            db.favoritesDao().addFavorite(
                FavoriteEntity(
                    id = id,
                    itemType = itemType,
                    title = title,
                    subtitle = subtitle,
                    iconName = iconName,
                    routeOrKey = routeOrKey
                )
            )
        }
    }

    suspend fun saveHistory(
        toolId: String,
        toolName: String,
        category: String,
        inputSummary: String,
        resultSummary: String,
        stepDetails: String = ""
    ): Long {
        return db.historyDao().insertHistory(
            CalculationHistoryEntity(
                toolId = toolId,
                toolName = toolName,
                category = category,
                inputSummary = inputSummary,
                resultSummary = resultSummary,
                stepDetails = stepDetails
            )
        )
    }

    suspend fun deleteHistory(id: Long) = db.historyDao().deleteHistoryById(id)
    suspend fun clearHistory() = db.historyDao().clearAllHistory()
    suspend fun removeFavorite(id: String) = db.favoritesDao().removeFavoriteById(id)
    suspend fun clearFavorites() = db.favoritesDao().clearAllFavorites()

    suspend fun savePcbProject(name: String, desc: String, compJson: String, wiresJson: String, id: Long = 0): Long {
        val proj = PcbProjectEntity(
            id = id,
            name = name,
            description = desc,
            componentsJson = compJson,
            wiresJson = wiresJson,
            updatedAt = System.currentTimeMillis()
        )
        return if (id > 0) {
            db.pcbProjectDao().updateProject(proj)
            id
        } else {
            db.pcbProjectDao().insertProject(proj)
        }
    }

    suspend fun getProject(id: Long) = db.pcbProjectDao().getProjectById(id)
    suspend fun deleteProject(id: Long) = db.pcbProjectDao().deleteProjectById(id)

    suspend fun saveStockItem(item: StockItemEntity): Long {
        return if (item.id == 0L) db.stockDao().insert(item) else {
            db.stockDao().update(item.copy(updatedAt = System.currentTimeMillis()))
            item.id
        }
    }

    suspend fun deleteStockItem(id: Long) = db.stockDao().delete(id)
    suspend fun adjustStock(id: Long, delta: Int) = db.stockDao().adjustQuantity(id, delta)

    suspend fun saveWorkbenchProject(project: WorkbenchProjectEntity): Long {
        return if (project.id == 0L) db.workbenchDao().insert(project) else {
            db.workbenchDao().update(project.copy(updatedAt = System.currentTimeMillis()))
            project.id
        }
    }

    suspend fun deleteWorkbenchProject(id: Long) = db.workbenchDao().delete(id)

    suspend fun setSetting(key: String, value: String) {
        db.settingsDao().saveSetting(AppSettingEntity(key, value))
    }

    suspend fun getSetting(key: String): String? = db.settingsDao().getSetting(key)
}
