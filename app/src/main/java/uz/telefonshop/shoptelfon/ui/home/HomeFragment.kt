package uz.telefonshop.shoptelfon.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapController
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import uz.mybiometric.firebasemikit.mainui.carradd.NetworkStatus
import uz.mybiometric.firebasemikit.utilis.WatcherSearch
import uz.telefonshop.shoptelfon.R
import uz.telefonshop.shoptelfon.adapter.HomeTelfonAdapter
import uz.telefonshop.shoptelfon.databinding.BotomsheetDialogBinding
import uz.telefonshop.shoptelfon.databinding.FragmentHomeBinding
import uz.telefonshop.shoptelfon.model.Telfon
import uz.telefonshop.shoptelfon.onClick.IonClickListener

@Suppress("DEPRECATION")
class HomeFragment : Fragment(R.layout.fragment_home), IonClickListener {

    private var homeBinding: FragmentHomeBinding?=null
    private var closed = false
    private var mapController: MapController?=null
    private var adapter:HomeTelfonAdapter?=null
    private  val rotateOpen : Animation by lazy { AnimationUtils.loadAnimation(requireActivity(),R.anim.rotate_open_anim) }
    private  val rotateClose : Animation by lazy { AnimationUtils.loadAnimation(requireActivity(),R.anim.rotate_close_anim) }
    private  val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(requireActivity(),R.anim.from_bottom_anim) }
    private  val toBottom : Animation by lazy { AnimationUtils.loadAnimation(requireActivity(),R.anim.to_bottom_anim) }
    private val homeViewModel:HomeViewModel by activityViewModels()
    private val navCotroller by lazy { Navigation.findNavController(requireActivity(),R.id.nav_host_fragment) }

    override fun onStart() {
        super.onStart()
        homeViewModel.readTalfon("")
        homeViewModel.networkStatus.observe(viewLifecycleOwner, Observer {
            when(it){
                is NetworkStatus.onLoading->showLoading()
                is NetworkStatus.onSuccess->showSuccess()
            }
        })
        homeViewModel.searchItem.observe(viewLifecycleOwner, Observer {
            adapter=HomeTelfonAdapter(it,this)
            adapter!!.startListening()
            homeBinding!!.recHome.adapter=adapter
            Log.d("tag", "itemSearchRecView: ${it}")

        })

    }

    private fun showSuccess() {
        homeBinding!!.codesaveAccount.visibility=View.INVISIBLE
    }

    private fun showLoading() {
        homeBinding!!.codesaveAccount.visibility=View.VISIBLE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding=FragmentHomeBinding.bind(view)
           homeBinding=binding
        onClickFab()
        markerMaps()
        searchWatcher()
        itemSearchRecView()


    }

    private fun itemSearchRecView() {
        homeViewModel.searchItem.observe(viewLifecycleOwner, Observer {
            adapter= HomeTelfonAdapter(it,this)
            adapter!!.startListening()
            homeBinding!!.recHome.adapter=adapter
            Log.d("tag", "itemSearchRecView: ${it}")
        })

    }

    private fun searchWatcher() {
        homeBinding!!.edSearch.addTextChangedListener(object : WatcherSearch(){
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                homeViewModel.readTalfon(s.toString())
            adapter!!.stopListening()
            }
        })
    }


    private fun onClickFab() {
        homeBinding!!.addF.setOnClickListener {
            OnAddButtonClick()
        }
        homeBinding!!.editF.setOnClickListener {
           navCotroller.navigate(R.id.addcard_navigation)
        }
        homeBinding!!.settingF.setOnClickListener {
            Toast.makeText(requireContext(),"yangilash huquqiga ega emassiz", Toast.LENGTH_SHORT).show();
        }
    }

    private fun OnAddButtonClick() {
        setVisibility(closed)
        setAnimation(closed)
        closed = !closed;
    }
    private fun setAnimation(closed:Boolean) {
        if(!closed){
            homeBinding!!.editF.startAnimation(fromBottom)
            homeBinding!!.settingF.startAnimation(fromBottom)
            homeBinding!!.addF.startAnimation(rotateOpen)
        }else{
            homeBinding!!.editF.startAnimation(toBottom)
            homeBinding!!.settingF.startAnimation(toBottom)
            homeBinding!!.addF.startAnimation(rotateClose)
        }
    }

    private fun setVisibility(closed:Boolean) {
        if(!closed)
        {
            homeBinding!!.editF.visibility = View.VISIBLE
            homeBinding!!.settingF.visibility = View.VISIBLE
        }else{
            homeBinding!!.editF.visibility = View.INVISIBLE
            homeBinding!!.settingF.visibility = View.INVISIBLE
        }
    }

    private fun markerMaps() {
        Configuration.getInstance().load(requireContext(),
            PreferenceManager.getDefaultSharedPreferences(requireContext()))

        homeBinding!!.mapView.setTileSource(TileSourceFactory.MAPNIK)
        homeBinding!!.mapView.setBuiltInZoomControls(true)
        homeBinding!!.mapView.setMultiTouchControls(true)
        homeBinding!!.mapView.setUseDataConnection(true)
        homeBinding!!.mapView.invalidate()



        mapController = homeBinding!!.mapView.controller as MapController?

        mapController!!.setZoom(9)
        val list=ArrayList<GeoPoint>()
        val gPt = GeoPoint(41.33879, 69.27197)
        val point = GeoPoint(41.28914, 69.19068)
        val pointBank= GeoPoint(41.24827, 69.16415)
        list.add(gPt)
        list.add(point)
        list.add(pointBank)
        mapController!!.setCenter(gPt)
        homeBinding!!.mapView.mapOrientation=4F
        val startmarker= Marker( homeBinding!!.mapView)
        val endmarker= Marker (homeBinding!!.mapView)
        val markers= Marker( homeBinding!!.mapView)


        startmarker.position=gPt
        endmarker.position=point
        markers.position=pointBank

        startmarker.title="Malika Telefon Bazar"
        endmarker.title="telefon bozor "
        markers.title="Abu sahiy bozor "
        startmarker.isDraggable=true
        endmarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)


        homeBinding!!.mapView.overlays.add(startmarker)
        homeBinding!!.mapView.overlays.add(endmarker)
        homeBinding!!.mapView.overlays.add(markers)

        val livedate= MutableLiveData<Polyline>()
        val markma=MarkerTask(list,livedate,requireContext())
        markma.start()
        markma.livedate.observe(viewLifecycleOwner, Observer {
            homeBinding!!.mapView.overlays.add(it)
        })
    }

    override fun onCilickItem(telfon: Telfon) {
        val view=BotomsheetDialogBinding.inflate(LayoutInflater.from(requireContext()),null,false)
        val dialog= BottomSheetDialog(requireContext())
        dialog.setContentView(view.root)
        view.tvModeli.text=telfon.model
        view.tvColor.text=telfon.color
        view.lcCall.setOnClickListener {
            Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+telfon.callnum)).apply {
                startActivity(this)
            }
        }
        view.lcSave.setOnClickListener {
            homeViewModel.onSaveHistory(telfon)
            dialog.dismiss()
        }

        dialog.show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter!!.stopListening()
    }

}