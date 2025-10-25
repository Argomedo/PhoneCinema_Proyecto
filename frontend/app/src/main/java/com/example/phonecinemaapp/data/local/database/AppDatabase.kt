package com.example.phonecinemaapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.phonecinemaapp.data.local.review.ReviewDao
import com.example.phonecinemaapp.data.local.review.ReviewEntity
import com.example.phonecinemaapp.data.local.user.UserDao
import com.example.phonecinemaapp.data.local.user.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [UserEntity::class, ReviewEntity::class],
    version = 14, // sube versión para forzar recreación
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun reviewDao(): ReviewDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private const val DB_NAME = "phone_cinema_app.db"

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(DatabaseCallback(context))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback(
        private val context: Context
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            // Este callback se ejecuta una sola vez cuando la DB se crea
            CoroutineScope(Dispatchers.IO).launch {
                val database = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                ).build()

                val userDao = database.userDao()

                // Insertar usuarios predeterminados
                userDao.insert(
                    UserEntity(
                        name = "Administrador",
                        email = "admin@phonecinema.com",
                        password = "Admin123!",
                        role = "Admin"
                    )
                )
                userDao.insert(
                    UserEntity(
                        name = "Moderador",
                        email = "mod@phonecinema.com",
                        password = "Mod123!",
                        role = "Moderador"
                    )
                )

                database.close()
            }
        }
    }
}
