package uz.telefonshop.shoptelfon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.mybiometric.firebasemikit.room.CarHistory
import uz.telefonshop.shoptelfon.databinding.HistoryLayoutBinding

class HistoryAdapter:
    ListAdapter<CarHistory,HistoryAdapter.VhCarHistory>(CallbackHisory()){

    class VhCarHistory(val view:HistoryLayoutBinding):RecyclerView.ViewHolder(view.root){

        fun onBind(carHistory:CarHistory){
            Glide.with(view.root)
                .load(carHistory.image)
                .into(view.imageHistory)
            view.tvHistoryName.text=carHistory.model
            view.tvHistoryCallnum.text=carHistory.callnum
            view.tvHistoryKint.text=carHistory.money


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VhCarHistory {
        val hisory=HistoryLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VhCarHistory(hisory)
    }

    override fun onBindViewHolder(holder: VhCarHistory, position: Int) {
        val item=holder.onBind(getItem(position))
    }
}

class CallbackHisory():DiffUtil.ItemCallback<CarHistory>(){
    override fun areItemsTheSame(oldItem: CarHistory, newItem: CarHistory): Boolean=
        oldItem==newItem

    override fun areContentsTheSame(oldItem: CarHistory, newItem: CarHistory): Boolean =
        oldItem.id==newItem.id

}