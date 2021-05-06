package uz.telefonshop.shoptelfon.ui.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Polyline

class MarkerTask(val list:ArrayList<GeoPoint>,
                 val livedate:MutableLiveData<Polyline>,
                 val ctx:Context) :Thread(){
    override fun run() {
        super.run()

    val rootm=OSRMRoadManager(ctx)

        val road=rootm.getRoad(list)

        val readoverlov= RoadManager.buildRoadOverlay(road)
        livedate.postValue(readoverlov)

    }
}