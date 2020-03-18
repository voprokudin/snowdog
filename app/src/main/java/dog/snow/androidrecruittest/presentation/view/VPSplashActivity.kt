package dog.snow.androidrecruittest.presentation.view

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.base.VPActivity
import dog.snow.androidrecruittest.extensions.obtainViewModel
import dog.snow.androidrecruittest.presentation.navigation.VPNavigator
import dog.snow.androidrecruittest.presentation.view.list.model.VPListItem
import dog.snow.androidrecruittest.presentation.viewmodel.VPSplashViewModel
import dog.snow.androidrecruittest.presentation.viewmodel.VPSplashViewModel.ScreenState.SetUpView
import dog.snow.androidrecruittest.presentation.viewmodel.VPSplashViewModel.ScreenState.SaveListItem
import dog.snow.androidrecruittest.presentation.viewmodel.VPSplashViewModel.ScreenState.ShowListFragment
import dog.snow.androidrecruittest.presentation.viewmodel.VPSplashViewModel.ScreenState.ShowListFragmentWithoutAnimation
import dog.snow.androidrecruittest.presentation.viewmodel.VPSplashViewModel.ScreenState.ShowGeneralError
import dog.snow.androidrecruittest.util.animation.VPAnimationProvider
import kotlinx.android.synthetic.main.layout_progressbar.progressbar
import kotlinx.android.synthetic.main.splash_activity.ivLogoSdSymbol
import kotlinx.android.synthetic.main.splash_activity.ivLogoSdText
import kotlinx.android.synthetic.main.splash_activity.splashLayout
import javax.inject.Inject

class VPSplashActivity : VPActivity() {

    @Inject
    lateinit var navigator: VPNavigator

    @Inject
    lateinit var animationProvider: VPAnimationProvider

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { obtainViewModel<VPSplashViewModel>(viewModelFactory) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        setupViewModel()
        maybeFetchListItems()
    }

    private fun setUpView() {
        ivLogoSdSymbol.startAnimation(animationProvider.comeInToRight())
        ivLogoSdText.startAnimation(animationProvider.comeInToLeft())
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
        val anim = animationProvider.slideUp()
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                splashLayout.visibility = View.GONE
                navigator.openHomeActivity()
                finish()
            }

            override fun onAnimationStart(p0: Animation?) {
            }
        })
        splashLayout.startAnimation(anim)
        hideProgressBar()
    }

    private fun showListFragmentWithoutAnimation() {
        hideProgressBar()
        navigator.openHomeActivity()
        finish()
    }

    private inner class ScreenActionObserver : Observer<VPSplashViewModel.ScreenState> {
        override fun onChanged(screenAction: VPSplashViewModel.ScreenState?) {
            screenAction ?: return

            when (screenAction) {
                is SetUpView -> setUpView()
                is ShowListFragmentWithoutAnimation -> showListFragmentWithoutAnimation()
                is SaveListItem -> saveListItems(screenAction.listItems)
                is ShowListFragment -> showListFragment()
                is ShowGeneralError -> showError(screenAction.errorMessage)
            }
        }
    }
}