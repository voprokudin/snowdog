package dog.snow.androidrecruittest.presentation.view

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.base.BaseActivity
import dog.snow.androidrecruittest.extensions.obtainViewModel
import dog.snow.androidrecruittest.presentation.navigation.Navigator
import dog.snow.androidrecruittest.presentation.view.list.model.ListItem
import dog.snow.androidrecruittest.presentation.viewmodel.SplashViewModel
import dog.snow.androidrecruittest.presentation.viewmodel.SplashViewModel.ScreenState.SetUpView
import dog.snow.androidrecruittest.presentation.viewmodel.SplashViewModel.ScreenState.SaveListItem
import dog.snow.androidrecruittest.presentation.viewmodel.SplashViewModel.ScreenState.ShowListFragment
import dog.snow.androidrecruittest.presentation.viewmodel.SplashViewModel.ScreenState.ShowListFragmentWithoutAnimation
import dog.snow.androidrecruittest.presentation.viewmodel.SplashViewModel.ScreenState.ShowGeneralError
import dog.snow.androidrecruittest.util.animation.AnimationProvider
import kotlinx.android.synthetic.main.layout_progressbar.progressbar
import kotlinx.android.synthetic.main.splash_activity.ivLogoSdSymbol
import kotlinx.android.synthetic.main.splash_activity.ivLogoSdText
import kotlinx.android.synthetic.main.splash_activity.splashLayout
import javax.inject.Inject

class SplashActivity : BaseActivity() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var animationProvider: AnimationProvider

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { obtainViewModel<SplashViewModel>(viewModelFactory) }

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

    private fun saveListItems(listItems: List<ListItem>) {
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

    private inner class ScreenActionObserver : Observer<SplashViewModel.ScreenState> {
        override fun onChanged(screenAction: SplashViewModel.ScreenState?) {
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