package dog.snow.androidrecruittest.presentation.view

import android.os.Bundle
import android.view.View
import com.squareup.picasso.Picasso
import dog.snow.androidrecruittest.Constants
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.base.BaseFragment
import dog.snow.androidrecruittest.extensions.arg
import dog.snow.androidrecruittest.extensions.argNotNull
import dog.snow.androidrecruittest.presentation.view.list.model.ListItem
import kotlinx.android.synthetic.main.details_fragment.tvAlbumTitle
import kotlinx.android.synthetic.main.details_fragment.tvPhotoTitle
import kotlinx.android.synthetic.main.details_fragment.tvUsername
import kotlinx.android.synthetic.main.details_fragment.tvEmail
import kotlinx.android.synthetic.main.details_fragment.tvPhone
import kotlinx.android.synthetic.main.details_fragment.ivPhoto

class BaseDetailsFragment : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance(
            listItem: ListItem,
            transitionName: String
        ) : BaseDetailsFragment {
            val fragment = BaseDetailsFragment()
            val args = Bundle().apply {
                putParcelable(Constants.BundleArgs.LIST_ITEM, listItem)
                putString(Constants.BundleArgs.TRANSITION, transitionName)
            }
            fragment.arguments = args
            return fragment
        }
    }

    private val listItem: ListItem by argNotNull(Constants.BundleArgs.LIST_ITEM)

    private val transitionName: String? by arg(Constants.BundleArgs.TRANSITION)

    override val getLayoutResId: Int = R.layout.details_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showDetails()
    }

    private fun showDetails() {
        transitionName?.also { ivPhoto.transitionName = it }
        Picasso.get()
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