package uz.mybiometric.firebasemikit.mainui.carradd

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.*

import uz.mybiometric.firebasemikit.utilis.PreferenseManager
import uz.telefonshop.shoptelfon.model.CarModel

class CarAddRepo (ctx:Context) {
    companion object {

        private var instanse: CarAddRepo? = null
        fun instanse(ctx: Context): CarAddRepo {
            if (instanse == null) {
                instanse = CarAddRepo(ctx)
            } else {
                instanse
            }
            return instanse!!
        }
    }

    private val firebaseDb by lazy { FirebaseDatabase.getInstance().getReference("number") }
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    private val preferense by lazy { PreferenseManager.instanse(ctx) }
    private var name: String? = null
    private var callnumber: String? = null
    private val status=MutableLiveData<NetworkStatus>()

    fun CarNumberSave(carModel: CarModel) {
        status.postValue(NetworkStatus.onLoading)
        name = carModel.surname
        callnumber = carModel.callnum
        scope.launch {
            firebaseDb.child("${carModel.callnum}${carModel.surname}${carModel.carnumber}")
                .setValue(carModel)
            status.postValue(NetworkStatus.onSuccess)
        }

    }

    fun onSaveFile() {
        preferense.savename = name.toString()
        preferense.callNumber = callnumber.toString()
    }
    fun ldStatus(): MutableLiveData<NetworkStatus> {
        return status
    }

    fun onCansel() {
        scope.cancel()
    }
}

sealed class NetworkStatus{
    object onLoading:NetworkStatus()
    object onSuccess:NetworkStatus()
}