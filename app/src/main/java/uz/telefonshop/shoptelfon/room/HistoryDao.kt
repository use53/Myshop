package uz.mybiometric.firebasemikit.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface HistoryDao{

    @Insert
    suspend fun loadInsert(carHistory: CarHistory)


    @Query("select * from carhistory")
    suspend fun loadAll():MutableList<CarHistory>


}