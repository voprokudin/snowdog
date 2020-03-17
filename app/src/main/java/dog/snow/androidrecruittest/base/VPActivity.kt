package dog.snow.androidrecruittest.base

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.layout_toolbar.*

abstract class VPActivity : DaggerAppCompatActivity() {

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        initViews()
    }

    private fun initViews() {
        initActionBar()
    }

    private fun initActionBar() {
        setSupportActionBar(toolbar)
        toolbar?.setNavigationOnClickListener { onToolbarNavigateUp() }
        configureActionBar(supportActionBar)
    }

    private fun configureActionBar(actionBar: ActionBar?) {
        actionBar?.setDisplayShowTitleEnabled(true)
    }

    private fun onToolbarNavigateUp() {
        onBackPressed()
    }

    fun setToolbarNavigationIcon(@DrawableRes iconResId: Int?) {
        if (iconResId == null) toolbar?.navigationIcon = iconResId else toolbar?.setNavigationIcon(iconResId)
    }

    fun setToolbarTitle(@StringRes titleId: Int) {
        supportActionBar?.setTitle(titleId)
    }
}