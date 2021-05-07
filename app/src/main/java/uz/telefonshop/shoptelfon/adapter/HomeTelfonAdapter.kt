package uz.telefonshop.shoptelfon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import uz.telefonshop.shoptelfon.databinding.CarsMoneyBinding
import uz.telefonshop.shoptelfon.model.Telfon
import uz.telefonshop.shoptelfon.onClick.IonClickListener

class HomeTelfonAdapter (options: FirebaseRecyclerOptions<Telfon>,val ionClickListener: IonClickListener):
    FirebaseRecyclerAdapter<Telfon, HomeTelfonAdapter.Vh>(options){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        val view=CarsMoneyBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Vh(view,ionClickListener)
    }

    override fun onBindViewHolder(holder: Vh, position: Int, model: Telfon) {
        holder.onBind(model)
     }

    class Vh(val view:CarsMoneyBinding,ionClickListener: IonClickListener): RecyclerView.ViewHolder(view.root){
        private var carModel:Telfon?=null

        init {
            itemView.setOnClickListener {
                carModel?.let { it1 -> ionClickListener.onCilickItem(it1) }
            }
        }
        fun onBind(telfon: Telfon){
          carModel=telfon
            Glide.with(view.root)
                .load(telfon.image)
                .into(view.imageCars)
             view.tvMoney.text="${telfon.money} 00.so'm"

        }
    }
}