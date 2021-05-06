package uz.mybiometric.firebasemikit.utilis

import android.content.Context
import android.content.SharedPreferences


private const val SAVE_CORRENT="SAVECORRENT"
private const val SAVE_FASE="SAVEFASE"
private const val SAVE_NAME="SAVENAME"
private const val SAVE_CALLNUMBER="SAVECALLNUMBER"
class PreferenseManager(preferenseManager: SharedPreferences):IPreferense{

    override var saveCorrent: Boolean by
    BooleanPreferense(preferenseManager, SAVE_CORRENT)

    override var saveFase: String by
    StringPreferense(preferenseManager, SAVE_FASE)

    override var savename: String  by
    StringPreferense(preferenseManager, SAVE_NAME)

    override var callNumber: String by
            StringPreferense(preferenseManager, SAVE_CALLNUMBER)


    companion object{
        private var instanse:IPreferense?=null
        fun instanse(ctx: Context): IPreferense {
            if (instanse==null){
                val preference= androidx.preference.PreferenceManager.getDefaultSharedPreferences(ctx)
                instanse=PreferenseManager(preference)
            }
            return instanse!!
        }
    }
}