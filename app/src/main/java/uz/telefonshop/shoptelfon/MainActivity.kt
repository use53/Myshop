package uz.telefonshop.shoptelfon

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    val firabaseDb by lazy { FirebaseDatabase.getInstance().getReference(".info/connected") }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

          Handler().postDelayed({
              firabaseDb.addValueEventListener(object :ValueEventListener{
                  override fun onCancelled(error: DatabaseError) {
                      Log.d("TAG", "onCancelled: ${error.message}")
                  }

                  override fun onDataChange(snapshot: DataSnapshot) {
                      val connect=snapshot.getValue(Boolean::class.java)?:false
                      if (connect){
                          Intent(this@MainActivity,ShopActivity::class.java).apply {
                              startActivity(this)
                          }
                          finish()

                      }else{
                          Toast.makeText(
                                  this@MainActivity,
                                  "internet bilan aloqa yuq ",
                                  Toast.LENGTH_SHORT
                          ).show()
                      }
                  }
              })
          },3_000)

    }
}