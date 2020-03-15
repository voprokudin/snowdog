package dog.snow.androidrecruittest.ui

import android.os.Bundle
import android.view.View
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.base.VPFragment
import kotlinx.android.synthetic.main.details_fragment.*

class VPDetailsFragment : VPFragment() {

    companion object {
        @JvmStatic
        fun newInstance(): VPListFragment = VPListFragment()
    }

    override val getLayoutResId: Int = R.layout.details_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showDetails()
    }

    private fun showDetails() {
        tvEmail.text = "tvEmail"
        tvUsername.text = "tvUsername"
        tvPhone.text = "tvPhone"
        tvAlbumTitle.text = "tvAlbumTitle"
    }
}