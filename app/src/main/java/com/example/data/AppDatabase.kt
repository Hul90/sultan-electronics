package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.model.AppSettingEntity
import com.example.model.CalculationHistoryEntity
import com.example.model.FavoriteEntity
import com.example.model.PcbProjectEntity
import com.example.model.StockItemEntity
import com.example.model.WorkbenchProjectEntity

@Database(
    entities = [
        CalculationHistoryEntity::class,
        FavoriteEntity::class,
        PcbProjectEntity::class,
        AppSettingEntity::class,
        StockItemEntity::class,
        WorkbenchProjectEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
    abstract fun favoritesDao(): FavoritesDao
    abstract fun pcbProjectDao(): PcbProjectDao
    abstract fun settingsDao(): SettingsDao
    abstract fun stockDao(): StockDao
    abstract fun workbenchDao(): WorkbenchDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "sultan_electronics.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
