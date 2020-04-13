package dog.snow.androidrecruittest.presentation.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.android.support.DaggerFragment
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.base.BaseActivity
import dog.snow.androidrecruittest.databinding.ListFragmentBinding
import dog.snow.androidrecruittest.extensions.obtainViewModel
import dog.snow.androidrecruittest.presentation.navigation.Navigator
import dog.snow.androidrecruittest.presentation.view.list.listener.ItemClickedListener
import dog.snow.androidrecruittest.presentation.viewmodel.HomeActivityViewModel.ScreenState.ShowGeneralError
import dog.snow.androidrecruittest.presentation.view.list.model.ListItem
import dog.snow.androidrecruittest.presentation.viewmodel.HomeActivityViewModel
import kotlinx.android.synthetic.main.layout_search.etSearch
import javax.inject.Inject

class ListFragment : DaggerFragment(), ItemClickedListener {

    companion object {
        @JvmStatic
        fun newInstance(): ListFragment = ListFragment()
    }

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: ListFragmentBinding

    private val viewModel by lazy { (activity as BaseActivity).obtainViewModel<HomeActivityViewModel>(viewModelFactory) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpView()
        setupViewModel()
    }

    override fun onItemRowClicked(listItem: ListItem, imageView: ImageView) {
        navigator.showDetailsFragment(listItem, imageView)
    }

    private fun getLocalListItems() {
        viewModel.getLocalListItems()
    }

    private fun setupViewModel() {
        binding.listener = this
        viewModel.screenState.observe(viewLifecycleOwner, ScreenActionObserver())
        viewModel.listItems.observe(viewLifecycleOwner, Observer { binding.photos = it })
    }

    private fun showError(errorMessage: String?) {
        MaterialAlertDialogBuilder(activity)
            .setTitle(R.string.cant_download_dialog_title)
            .setMessage(getString(R.string.cant_download_dialog_message, errorMessage))
            .setPositiveButton(R.string.cant_download_dialog_btn_positive) { _, _ -> getLocalListItems() }
            .setNegativeButton(R.string.cant_download_dialog_btn_negative) { _, _ -> }
            .create()
            .apply { setCanceledOnTouchOutside(false) }
            .show()
    }

    private fun setUpView() {
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                viewModel.performSearch(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }

    private inner class ScreenActionObserver : Observer<HomeActivityViewModel.ScreenState> {
        override fun onChanged(screenAction: HomeActivityViewModel.ScreenState?) {
            screenAction ?: return

            when (screenAction) {
                is ShowGeneralError -> showError(screenAction.errorMessage)
            }
        }
    }
}