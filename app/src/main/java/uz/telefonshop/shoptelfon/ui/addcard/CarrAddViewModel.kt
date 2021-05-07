package uz.mybiometric.firebasemikit.mainui.carradd

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uz.telefonshop.shoptelfon.model.CarModel

class CarrAddViewModel (app:Application):AndroidViewModel(app){
    private val carAddRepo=CarAddRepo.instanse(app.applicationContext)
     private var _status=MutableLiveData<NetworkStatus>()
    fun onSaveFirebase(carModel: CarModel){
       carAddRepo.CarNumberSave(carModel)
       carAddRepo.onSaveFile()
        _status=carAddRepo.ldStatus()
   }

    val status:LiveData<NetworkStatus>
    get() = _status
    override fun onCleared() {
        super.onCleared()
        carAddRepo.onCansel()
    }

}