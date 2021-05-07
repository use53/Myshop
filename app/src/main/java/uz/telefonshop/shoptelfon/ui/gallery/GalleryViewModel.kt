package uz.telefonshop.shoptelfon.ui.gallery

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.mybiometric.firebasemikit.room.BuilderDb
import uz.mybiometric.firebasemikit.room.CarHistory

class GalleryViewModel(app:Application):AndroidViewModel(app){

    private val _historyLd =MutableLiveData<MutableList<CarHistory>>()
    private var corrent=false
    private val builderDb by lazy {
        BuilderDb.instanse(app.applicationContext) }
    fun onHistory(){
        viewModelScope.launch {
        val history=builderDb.historyDao().loadAll()
            _historyLd.postValue(history)
        }

    }

    val historyLd:LiveData<MutableList<CarHistory>>
    get() = _historyLd

}