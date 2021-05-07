package uz.mybiometric.firebasemikit.room

import android.content.Context
import androidx.room.Room

class BuilderDb {

        companion object{
            private var instanse:HistoryDatabase?=null
            fun instanse(ctx: Context): HistoryDatabase {
                if (instanse==null){
                    instanse=Room.databaseBuilder(ctx,HistoryDatabase::class.java,"store.db").build()
                }else{
                    instanse
                }
                return instanse!!
            }


    }
}