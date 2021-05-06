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
import uz.telefonshop.shoptelfon.model.Telfon

class HomeViewModel(app:Application) :AndroidViewModel(app){

    private val firebaseDb by lazy { FirebaseDatabase.getInstance() }
    private val  _ldTelfon= MutableLiveData<FirebaseRecyclerOptions<Telfon>>()

            /*
            * Online databasedan uqiymiz 2 xil qiymat qartaradi aloqa bor yoki
            * yuqligi haqida
             */
    fun readTalfon(text:String){
          viewModelScope.launch {
              val options=FirebaseRecyclerOptions.Builder<Telfon>()
                  .setQuery(firebaseDb.getReference().child("shoptelefon")
                      .orderByChild("model")
                      .startAt(text).endAt(text+"\uf8ff"),Telfon::class.java)
                  .build()
              _ldTelfon.postValue(options)
          }
    }
    val searchItem:LiveData<FirebaseRecyclerOptions<Telfon>>
    get() = _ldTelfon

}