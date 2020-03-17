package dog.snow.androidrecruittest.presentation.view

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import dog.snow.androidrecruittest.C
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.base.VPFragment
import dog.snow.androidrecruittest.extensions.argNotNull
import dog.snow.androidrecruittest.presentation.view.list.model.VPListItem
import kotlinx.android.synthetic.main.details_fragment.tvAlbumTitle
import kotlinx.android.synthetic.main.details_fragment.tvPhotoTitle
import kotlinx.android.synthetic.main.details_fragment.tvUsername
import kotlinx.android.synthetic.main.details_fragment.tvEmail
import kotlinx.android.synthetic.main.details_fragment.tvPhone
import kotlinx.android.synthetic.main.details_fragment.ivPhoto

class VPDetailsFragment : VPFragment() {

    companion object {
        @JvmStatic
        fun newInstance(
            listItem: VPListItem
        ) : VPDetailsFragment {
            val fragment = VPDetailsFragment()
            val args = Bundle().apply {
                putParcelable(C.BundleArgs.LIST_ITEM, listItem)
            }
            fragment.arguments = args
            return fragment
        }
    }

    private val listItem: VPListItem by argNotNull(C.BundleArgs.LIST_ITEM)

    override val getLayoutResId: Int = R.layout.details_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showDetails()
    }

    private fun showDetails() {
        Glide.with(this)
            .load(listItem.url)
            .placeholder(R.drawable.ic_placeholder)
            .into(ivPhoto)
        listItem.apply {
            tvPhotoTitle.text = title
            tvAlbumTitle.text = albumTitle
            tvUsername.text = username
            tvEmail.text = email
            tvPhone.text = phone
        }
    }
}