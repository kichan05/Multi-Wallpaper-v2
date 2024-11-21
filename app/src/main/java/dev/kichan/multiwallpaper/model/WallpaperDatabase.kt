package dev.kichan.multiwallpaper.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Wallpaper::class], version = 2)
abstract class WallpaperDatabase : RoomDatabase() {
    abstract fun wallpaperDao(): WallpaperDao

    companion object {
        fun getDB(context: Context): WallpaperDatabase = Room.databaseBuilder(
            context, WallpaperDatabase::class.java, "wallpaper"
        ).build()
    }
}