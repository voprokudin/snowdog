package dog.snow.androidrecruittest.presentation.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.base.VPActivity
import dog.snow.androidrecruittest.extensions.obtainViewModel
import dog.snow.androidrecruittest.presentation.navigation.VPNavigator
import dog.snow.androidrecruittest.presentation.view.list.model.VPListItem
import dog.snow.androidrecruittest.presentation.viewmodel.VPSplashViewModel
import dog.snow.androidrecruittest.presentation.viewmodel.VPSplashViewModel.ScreenState.*
import kotlinx.android.synthetic.main.layout_progressbar.*
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
        maybeFetchListItems()
    }

    private fun maybeFetchListItems() {
        showProgressBar()
        viewModel.maybeFetchListItems()
    }

    private fun setupViewModel() {
        viewModel.screenState.observe(this, ScreenActionObserver())
    }

    private fun showError(errorMessage: String?) {
        hideProgressBar()
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.cant_download_dialog_title)
            .setMessage(getString(R.string.cant_download_dialog_message, errorMessage))
            .setPositiveButton(R.string.cant_download_dialog_btn_positive) { _, _ ->  maybeFetchListItems()}
            .setNegativeButton(R.string.cant_download_dialog_btn_negative) { _, _ -> finish() }
            .create()
            .apply { setCanceledOnTouchOutside(false) }
            .show()
    }

    private fun showProgressBar() {
        progressbar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressbar.visibility = View.GONE
    }

    private fun saveListItems(listItems: List<VPListItem>) {
        viewModel.saveListItems(listItems)
    }

    private fun showListFragment() {
        hideProgressBar()
        navigator.openHomeActivity()
    }

    private inner class ScreenActionObserver : Observer<VPSplashViewModel.ScreenState> {
        override fun onChanged(screenAction: VPSplashViewModel.ScreenState?) {
            screenAction ?: return

            when (screenAction) {
                is SaveListItem -> saveListItems(screenAction.listItems)
                is ShowListFragment -> showListFragment()
                is ShowGeneralError -> showError(screenAction.errorMessage)
            }
        }
    }
}