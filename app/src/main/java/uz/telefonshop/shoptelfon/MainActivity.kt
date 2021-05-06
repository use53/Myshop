package uz.telefonshop.shoptelfon

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Intent(this,ShopActivity::class.java)
                .apply { startActivity(this) }

    }
}