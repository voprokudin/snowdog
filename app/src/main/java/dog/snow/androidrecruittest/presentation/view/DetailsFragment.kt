package dog.snow.androidrecruittest.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import dog.snow.androidrecruittest.Constants
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.base.BaseFragment
import dog.snow.androidrecruittest.databinding.DetailsFragmentBinding
import dog.snow.androidrecruittest.extensions.arg
import dog.snow.androidrecruittest.extensions.argNotNull
import dog.snow.androidrecruittest.presentation.view.list.model.ListItem
import kotlinx.android.synthetic.main.details_fragment.tvAlbumTitle
import kotlinx.android.synthetic.main.details_fragment.tvPhotoTitle
import kotlinx.android.synthetic.main.details_fragment.tvUsername
import kotlinx.android.synthetic.main.details_fragment.tvEmail
import kotlinx.android.synthetic.main.details_fragment.tvPhone
import kotlinx.android.synthetic.main.details_fragment.ivPhoto

class DetailsFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(listItem: ListItem, transitionName: String) : DetailsFragment {
            val fragment = DetailsFragment()
            val args = Bundle().apply {
                putParcelable(Constants.BundleArgs.LIST_ITEM, listItem)
                putString(Constants.BundleArgs.TRANSITION, transitionName)
            }
            return fragment.apply { arguments = args }
        }
    }

    private lateinit var binding: DetailsFragmentBinding

    private val listItem: ListItem by argNotNull(Constants.BundleArgs.LIST_ITEM)

    private val transitionName: String? by arg(Constants.BundleArgs.TRANSITION)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  DetailsFragmentBinding.inflate(inflater, container, false)
        bindData()
        return binding.root
    }

    private fun bindData() {
        binding.photoDetail = listItem
        transitionName?.also { binding.imageTransition = it }
    }
}