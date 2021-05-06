package uz.mybiometric.firebasemikit.utilis

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class BooleanPreferense (
    val preferense:SharedPreferences,
    val key:String,
    val value:Boolean=false
):ReadWriteProperty<Any,Boolean>{
    override fun getValue(thisRef: Any, property: KProperty<*>): Boolean =
        preferense.getBoolean(key,false)


    override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
        preferense.edit().putBoolean(key,value).apply()
    }

}