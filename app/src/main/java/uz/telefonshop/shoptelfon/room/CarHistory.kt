package uz.mybiometric.firebasemikit.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class CarHistory (
    val model:String="",
    val money:String="",
    val callnum:String="",
    val color:String="",
    val image:String="",
    @PrimaryKey(autoGenerate = true)
    val id:Long=0L
)