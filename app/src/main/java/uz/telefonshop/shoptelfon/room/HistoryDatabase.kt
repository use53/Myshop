package uz.mybiometric.firebasemikit.room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [CarHistory::class],version = 1,exportSchema = false)
abstract class HistoryDatabase :RoomDatabase(){
   abstract fun historyDao():HistoryDao
}