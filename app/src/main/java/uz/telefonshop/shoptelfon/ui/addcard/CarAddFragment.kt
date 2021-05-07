package uz.mybiometric.firebasemikit.mainui.carradd

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import uz.mybiometric.firebasemikit.utilis.PhoneNumberTextWatcher
import uz.telefonshop.shoptelfon.R
import uz.telefonshop.shoptelfon.databinding.CaraddFragmentBinding
import uz.telefonshop.shoptelfon.model.CarModel

@Suppress("DEPRECATION")
class CarAddFragment:Fragment(R.layout.caradd_fragment), View.OnClickListener {


    private var caraddfragment:CaraddFragmentBinding?=null
    private val caraddViewmodel:CarrAddViewModel by activityViewModels()
    private val navController by lazy { Navigation.findNavController(requireActivity(),R.id.nav_host_fragment) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding=CaraddFragmentBinding.bind(view)
        caraddfragment=binding
         val phone=PhoneNumberTextWatcher(binding.edAccountCallnum)
        binding.edAccountCallnum.addTextChangedListener(phone)
        caraddfragment!!.btSave.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        if (caraddfragment!!.edAccountCallnum.text!!.length>8){

            val surname=caraddfragment!!.edAccountName.text.toString()
            val lastname=caraddfragment!!.edAccountSurname.text.toString()
            val kint=caraddfragment!!.edAccountKint.text.toString()
            val callnum=caraddfragment!!.edAccountCallnum.text.toString()
            val carnum=caraddfragment!!.edAccountCarnumber.text.toString()

             val carmodel= CarModel(surname,lastname,kint,callnum,carnum)
               caraddViewmodel.onSaveFirebase(carmodel)
            caraddViewmodel.status.observe(viewLifecycleOwner, Observer {
                when(it){
                    is NetworkStatus.onLoading->loadingItem()
                    is NetworkStatus.onSuccess->showSuccess()
                }
            })

        }else{
            Toast.makeText(
                requireContext(),
                "telfon number 9 xonali bulishi kerak",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun showSuccess() {
        caraddfragment!!.codesaveAccount.visibility=View.INVISIBLE
        caraddfragment!!.lottieOkay.visibility=View.VISIBLE
        Handler().postDelayed({
            caraddfragment!!.lottieOkay.visibility=View.INVISIBLE
            navController.popBackStack()
        },3_000)

    }

    private fun loadingItem() {

        caraddfragment!!.codesaveAccount.visibility=View.VISIBLE
        caraddfragment!!.accountElastic.visibility=View.GONE
    }


}