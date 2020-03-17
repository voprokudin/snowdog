package dog.snow.androidrecruittest.presentation.navigation

import androidx.fragment.app.FragmentManager
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.base.VPActivity
import dog.snow.androidrecruittest.common.intentfactory.VPHomeIntentFactory
import dog.snow.androidrecruittest.presentation.view.VPDetailsFragment
import dog.snow.androidrecruittest.presentation.view.VPListFragment
import dog.snow.androidrecruittest.presentation.view.list.model.VPListItem
import dog.snow.androidrecruittest.util.fragment.VPFragmentUtil
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VPNavigator
@Inject constructor(
    private val activity: VPActivity,
    private val fragmentUtil: VPFragmentUtil,
    private val homeIntentFactory: VPHomeIntentFactory
) {

    companion object {
        private const val FRAGMENT_CONTAINER = R.id.container
    }

    private val fragmentManager: FragmentManager by lazy { activity.supportFragmentManager }

    fun openHomeActivity() {
        val intent = homeIntentFactory.forHomeActivity()
        activity.startActivity(intent)
    }

    fun showDataListFragment() {
        if (isFragmentAlreadyShown<VPListFragment>()) return

        fragmentUtil.replaceFragmentAllowingStateLoss(
            fragmentManager = fragmentManager,
            fragment = VPListFragment.newInstance(),
            containerViewId = FRAGMENT_CONTAINER,
            addToBackStack = false
        )
    }

    fun showDetailsFragment(listItem: VPListItem) {
        if (isFragmentAlreadyShown<VPDetailsFragment>()) return

        fragmentUtil.replaceFragmentAllowingStateLoss(
            fragmentManager = fragmentManager,
            fragment = VPDetailsFragment.newInstance(listItem),
            containerViewId = FRAGMENT_CONTAINER,
            addToBackStack = true
        )
    }

    private inline fun <reified T> isFragmentAlreadyShown() = fragmentUtil.findFragment(fragmentManager, FRAGMENT_CONTAINER) is T
}