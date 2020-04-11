package dog.snow.androidrecruittest.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment() {

    protected abstract val getLayoutResId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(getLayoutResId, container, false)

    fun getBaseActivity(): BaseActivity = (activity as BaseActivity)
}