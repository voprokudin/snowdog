package dog.snow.androidrecruittest.util.fragment

import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FragmentUtil
@Inject constructor() {

    fun replaceFragmentAllowingStateLoss(
        imageView: ImageView? = null,
        fragmentManager: FragmentManager,
        fragment: Fragment,
        @IdRes containerViewId: Int,
        addToBackStack: Boolean,
        animate: Boolean
    ) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(containerViewId, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        if (animate) {
            ViewCompat.getTransitionName(imageView!!)?.also { transaction.addSharedElement(imageView, it) }
        }
        transaction.commit()
    }

    fun findFragment(fragmentManager: FragmentManager, @IdRes fragmentContainerId: Int): Fragment? = fragmentManager.findFragmentById(fragmentContainerId)
}