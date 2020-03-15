package dog.snow.androidrecruittest.presentation.view

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.base.VPActivity
import dog.snow.androidrecruittest.extensions.obtainViewModel
import dog.snow.androidrecruittest.presentation.navigation.VPNavigator
import dog.snow.androidrecruittest.presentation.viewmodel.VPSplashViewModel
import dog.snow.androidrecruittest.presentation.viewmodel.VPSplashViewModel.ScreenState.ShowData
import dog.snow.androidrecruittest.presentation.viewmodel.VPSplashViewModel.ScreenState.ShowGeneralError
import kotlinx.android.synthetic.main.layout_progressbar.*
import java.util.ArrayList
import javax.inject.Inject

class VPSplashActivity : VPActivity() {

    @Inject
    lateinit var navigator: VPNavigator

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { obtainViewModel<VPSplashViewModel>(viewModelFactory) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        setupViewModel()
        fetchData()
    }

    private fun setupViewModel() {
        viewModel.screenState.observe(this, ScreenActionObserver())
    }

    private fun fetchData() {
        showProgressBar()
        viewModel.fetchData()
    }

    private fun showError(errorMessage: String?) {
        val r = Runnable {
            hideProgressBar()

            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.cant_download_dialog_title)
                .setMessage(getString(R.string.cant_download_dialog_message, errorMessage))
                .setPositiveButton(R.string.cant_download_dialog_btn_positive) { _, _ ->  fetchData()}
                .setNegativeButton(R.string.cant_download_dialog_btn_negative) { _, _ -> finish() }
                .create()
                .apply { setCanceledOnTouchOutside(false) }
                .show()
        }
        Handler().postDelayed(r, 2000)
    }

    private fun showProgressBar() {
        progressbar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressbar.visibility = View.GONE
    }

    private inner class ScreenActionObserver : Observer<VPSplashViewModel.ScreenState> {
        override fun onChanged(screenAction: VPSplashViewModel.ScreenState?) {
            screenAction ?: return

            when (screenAction) {
                is ShowData -> showData(screenAction.productsCodes)
                is ShowGeneralError -> showError(screenAction.errorMessage)
            }
        }
    }

    private fun showData(productsCodes: ArrayList<String>) {
        val r = Runnable {
            hideProgressBar()
            navigator.openHomeActivity()
        }
        Handler().postDelayed(r, 2000)
    }
}