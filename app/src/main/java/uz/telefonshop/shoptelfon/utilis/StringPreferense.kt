package uz.mybiometric.firebasemikit.utilis

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class StringPreferense (
    val preferense: SharedPreferences,
    val key:String,
    val value: String=""
):ReadWriteProperty<Any,String>{
    override fun getValue(thisRef: Any, property: KProperty<*>): String =
        preferense.getString(key,"").toString()


    override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
        preferense.edit().putString(key,value).apply()
    }
}