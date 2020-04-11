package dog.snow.androidrecruittest.presentation.navigation

import android.widget.ImageView
import androidx.fragment.app.FragmentManager
import androidx.transition.TransitionInflater
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.base.BaseActivity
import dog.snow.androidrecruittest.common.intentfactory.HomeIntentFactory
import dog.snow.androidrecruittest.presentation.view.DetailsFragment
import dog.snow.androidrecruittest.presentation.view.ListFragment
import dog.snow.androidrecruittest.presentation.view.list.model.ListItem
import dog.snow.androidrecruittest.util.fragment.FragmentUtil
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator
@Inject constructor(
    private val activity: BaseActivity,
    private val fragmentUtil: FragmentUtil,
    private val homeIntentFactory: HomeIntentFactory
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
        if (isFragmentAlreadyShown<ListFragment>()) return

        fragmentUtil.replaceFragmentAllowingStateLoss(
            fragmentManager = fragmentManager,
            fragment = ListFragment.newInstance(),
            containerViewId = FRAGMENT_CONTAINER,
            addToBackStack = false,
            animate = false
        )
    }

    fun showDetailsFragment(listItem: ListItem, imageView: ImageView) {
        if (isFragmentAlreadyShown<DetailsFragment>()) return

        val fragment = DetailsFragment.newInstance(
            listItem = listItem,
            transitionName = imageView.transitionName
        ).also {
            it.sharedElementEnterTransition = TransitionInflater.from(activity).inflateTransition(R.transition.default_transition)
            it.enterTransition = TransitionInflater.from(activity).inflateTransition(android.R.transition.no_transition)
        }

        fragmentUtil.replaceFragmentAllowingStateLoss(
            imageView = imageView,
            fragmentManager = fragmentManager,
            fragment = fragment,
            containerViewId = FRAGMENT_CONTAINER,
            addToBackStack = true,
            animate = true
        )
    }

    private inline fun <reified T> isFragmentAlreadyShown() = fragmentUtil.findFragment(fragmentManager, FRAGMENT_CONTAINER) is T
}