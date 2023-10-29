package com.tugaypamuk.app.onestask.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.tugaypamuk.app.onestask.R
import com.tugaypamuk.app.onestask.databinding.BottomSheetBinding
import com.tugaypamuk.app.onestask.databinding.FragmentHomeBinding
import com.tugaypamuk.app.onestask.domain.model.AccessLog
import com.tugaypamuk.app.onestask.services.Email
import com.tugaypamuk.app.onestask.utils.statusCode
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
   private val viewModel by viewModels<HomeViewModel>()
    private lateinit var binding : FragmentHomeBinding
    private var countDownTimer: CountDownTimer? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        setupToolBarMenu()
        observeViewModel()
        viewModel.getMailServer()
        binding.fragmentHomeBtn.setOnClickListener {
           viewModel.getAccessLog()
        }

    }



    private fun observeViewModel(){
        viewModel.accessLog.observe(viewLifecycleOwner){ log ->
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED){
                if ( log.data?.verifyStatusCode != null && log.data.verifyStatusCode > 0){
                    customAlertDialog(log.data)
                } else {
                    val statusCode = log.data?.verifyStatusCode
                    if (statusCode != null && statusCode > 0){
                        Toast.makeText(requireContext(),"Success",Toast.LENGTH_LONG).show()
                    }

                }
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner){ loading ->
            // accessLog istek surecindeki loading sureci
            binding.fragmentHomeBtnProgress.isVisible = loading
            binding.fragmentHomeBtnText.isVisible = !loading

        }
        viewModel.error.observe(viewLifecycleOwner){ error ->
            // istek sonucu donen hata mesaji
            Toast.makeText(requireContext(),error,Toast.LENGTH_LONG).show()
        }
        viewModel.approveError.observe(viewLifecycleOwner){ approveError ->
            // onaylama islemi sonrasi donen hata mesaji
            Toast.makeText(requireContext(),approveError,Toast.LENGTH_LONG).show()
        }
        viewModel.approveStatus.observe(viewLifecycleOwner){approveStatus ->
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED){
                Toast.makeText(requireContext(),approveStatus,Toast.LENGTH_LONG).show()
            }
            // onaylama islemi sonrasi donen response

        }
        viewModel.isMailServerExist.observe(viewLifecycleOwner){ mailServer ->
            // mail sunucu kontrol eger ekli degilse popup cikartip settingsFragment e yonlendiriliyor
            if (!mailServer){
                val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
                builder.setTitle(getString(R.string.mail_setup_title))
                builder.setMessage(getString(R.string.bypass_mail_setup))

                builder.setPositiveButton(getString(R.string.button_ok)) { dialog, _ ->
                    findNavController().navigate(R.id.action_homeFragment_to_fragmentSettings2)
                    dialog.dismiss()
                }
                val alertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
            }
        }

    }

    private fun customAlertDialog(accessLog: AccessLog){

        /*
            * acilan popup icerisinde accesslog dan donen parametreler yer almakta
            * eger 30 sn icinde onaylanmassa otomatik olarak mail servis calisip belirlenen yonetici adresine eposta gonderilmekte
            * onaylama islemi 30 sn icinde gerceklesirse API/AccessLog endpointine logdID ile post methodu tetiklenmekte

         */

        val builder = AlertDialog.Builder(requireContext())
        val dialogBinding : BottomSheetBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.bottom_sheet,
            null,
            false
        )
        //customAlertdialog icin databinding kullanilmistir donen degerler xml icerisinde set ediliyor
        dialogBinding.logs = accessLog
        val initialTime = 30 * 1000L //30sn bekleme suresi countdowntimer
        countDownTimer = object : CountDownTimer(initialTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                dialogBinding.approveBtn.text = getString(R.string.aprrove,secondsLeft)
            }
            override fun onFinish() {
                dialogBinding.approveBtn.text = getString(R.string.send_a_mail)
                dialogBinding.approveBtn.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.blue))
                dialogBinding.approveBtn.isClickable = false
                dialogBinding.cancelBtn.visibility = View.VISIBLE
                val email = Email("Yetki Hatasi", "E-posta içeriği", "alici@example.com")
                viewModel.sendEmail(email)

            }
        }
        countDownTimer?.start()
        dialogBinding.approveBtn.setOnClickListener {
            // dialog icerisinde yer alan onayla butonuna basildikdan sonra post methodu asagidaki
            // parametrelere gore tetiklenmektedir.
            val parameters : HashMap<String,String> = HashMap()
            parameters["LogID"] = accessLog.logID.toString()
            parameters["Description"] = "Onaylandi"  // aciklama alaniyla ilgili bilgi verilmedigi icin her seferinde statik onaylandi degeri gonderiliyor
            viewModel.approveAccessLog(parameters)
            countDownTimer?.cancel()
            dialogBinding.approveBtn.text = getString(R.string.aprroved)
            viewModel.accessLog.removeObservers(viewLifecycleOwner)
            dialogBinding.cancelBtn.visibility = View.VISIBLE

        }
        accessLog.verifyStatusCode?.let { dialogBinding.errorTypeTv.statusCode(it) }
        builder.setView(dialogBinding.root)
        val alertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()

        dialogBinding.cancelBtn.setOnClickListener {
              alertDialog.dismiss()
            countDownTimer?.cancel()
        }
    }
    override fun onPause() {
        super.onPause()
        countDownTimer?.cancel()
        viewModel.accessLog.removeObservers(viewLifecycleOwner)
    }
    private fun setupToolBarMenu(){
        (requireActivity() as MenuHost).addMenuProvider(object  : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId){
                    R.id.fragmentLogs ->findNavController().navigate(R.id.action_homeFragment_to_fragmentLogs2)
                    R.id.fragmentSettings  ->findNavController().navigate(R.id.action_homeFragment_to_fragmentSettings2)

                }
                return true
            }

        },viewLifecycleOwner,Lifecycle.State.RESUMED)
    }

}