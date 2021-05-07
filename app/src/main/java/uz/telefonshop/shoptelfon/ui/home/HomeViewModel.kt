package uz.telefonshop.shoptelfon.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch
import uz.mybiometric.firebasemikit.mainui.carradd.NetworkStatus
import uz.mybiometric.firebasemikit.room.BuilderDb
import uz.mybiometric.firebasemikit.room.CarHistory
import uz.telefonshop.shoptelfon.model.Telfon

class HomeViewModel(app:Application) :AndroidViewModel(app){

    private val firebaseDb by lazy { FirebaseDatabase.getInstance() }
    private val  _ldTelfon= MutableLiveData<FirebaseRecyclerOptions<Telfon>>()
    private val builderDb by lazy(LazyThreadSafetyMode.NONE) {
        BuilderDb.instanse(app.applicationContext)
    }
    private var corrent=false

    private val _networkStatus=MutableLiveData<NetworkStatus>()
            /*
            * internetda search orqali  firebasedan uqish
            * viewModelScope esa bowqa patokka utkaziladi
             */
    fun readTalfon(text:String){
              _networkStatus.postValue(NetworkStatus.onLoading)
              viewModelScope.launch {
                  val options=FirebaseRecyclerOptions.Builder<Telfon>()
                      .setQuery(firebaseDb.getReference().child("shoptelefon")
                          .orderByChild("model")
                          .startAt(text).endAt(text+"\uf8ff"),Telfon::class.java)
                      .build()
                  _ldTelfon.postValue(options)
                  _networkStatus.postValue(NetworkStatus.onSuccess)
              }


    }

    val searchItem:LiveData<FirebaseRecyclerOptions<Telfon>>
    get() = _ldTelfon
    val networkStatus:LiveData<NetworkStatus>
        get() = _networkStatus



    fun onSaveHistory(telfon: Telfon){
        val carHistory=CarHistory(telfon.model,
            telfon.money,
            telfon.callnum,
            telfon.color,
            telfon.image)
          viewModelScope.launch {
              builderDb.historyDao()
                  .loadInsert(carHistory = carHistory)
          }
    }

}