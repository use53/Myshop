package uz.telefonshop.shoptelfon.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import uz.telefonshop.shoptelfon.R
import uz.telefonshop.shoptelfon.adapter.HistoryAdapter
import uz.telefonshop.shoptelfon.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment(R.layout.fragment_gallery) {

    private var galeryFragment:FragmentGalleryBinding?=null
   private val history by lazy { HistoryAdapter() }
    private val viewmodel:GalleryViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding=FragmentGalleryBinding.bind(view)
          galeryFragment=binding
        binding.recGallery.adapter=history
        viewmodel.onHistory()
        viewmodel.historyLd.observe(viewLifecycleOwner, Observer {
            if (!it.isEmpty()){
                history.submitList(it)
                binding.tvGalleryHistory.visibility=View.GONE
                binding.recGallery.visibility=View.VISIBLE
            }
        })

    }
}