package dog.snow.androidrecruittest.presentation.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.base.VPFragment
import dog.snow.androidrecruittest.extensions.obtainViewModel
import dog.snow.androidrecruittest.presentation.navigation.VPNavigator
import dog.snow.androidrecruittest.presentation.view.list.listener.VPItemClickedListener
import dog.snow.androidrecruittest.presentation.viewmodel.VPListFragmentViewModel
import dog.snow.androidrecruittest.presentation.view.list.adapter.VPListAdapter
import dog.snow.androidrecruittest.presentation.view.list.model.VPListItem
import dog.snow.androidrecruittest.presentation.viewmodel.VPListFragmentViewModel.ScreenState.ShowGeneralError
import dog.snow.androidrecruittest.presentation.viewmodel.VPListFragmentViewModel.ScreenState.ShowData
import kotlinx.android.synthetic.main.list_fragment.emptyView
import kotlinx.android.synthetic.main.list_fragment.rvItems
import javax.inject.Inject

class VPListFragment :
    VPFragment(),
    VPItemClickedListener {

    companion object {
        @JvmStatic
        fun newInstance(): VPListFragment = VPListFragment()
    }

    @Inject
    lateinit var navigator: VPNavigator

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { obtainViewModel<VPListFragmentViewModel>(viewModelFactory) }

    override val getLayoutResId: Int = R.layout.list_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        getLocalListItems()
    }

    override fun onItemRowClicked(listItem: VPListItem) {
        navigator.showDetailsFragment(listItem)
    }

    private fun getLocalListItems() {
        viewModel.getLocalListItems()
    }

    private fun setupViewModel() {
        viewModel.screenState.observe(viewLifecycleOwner, ScreenActionObserver())
    }

    private fun showData(items: List<VPListItem>) {
        emptyView.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE

        val listAdapter =
            VPListAdapter(
                items = items,
                itemsClickedListener = this,
                requireContext = this
            )
        rvItems.run {
            visibility = View.VISIBLE
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = listAdapter
        }
    }

    private fun showError(errorMessage: String?) {
        MaterialAlertDialogBuilder(activity)
            .setTitle(R.string.cant_download_dialog_title)
            .setMessage(getString(R.string.cant_download_dialog_message, errorMessage))
            .setPositiveButton(R.string.cant_download_dialog_btn_positive) { _, _ ->  getLocalListItems()}
            .setNegativeButton(R.string.cant_download_dialog_btn_negative) { _, _ ->  }
            .create()
            .apply { setCanceledOnTouchOutside(false) }
            .show()
    }

    private inner class ScreenActionObserver : Observer<VPListFragmentViewModel.ScreenState> {
        override fun onChanged(screenAction: VPListFragmentViewModel.ScreenState?) {
            screenAction ?: return

            when (screenAction) {
                is ShowData -> showData(screenAction.items)
                is ShowGeneralError -> showError(screenAction.errorMessage)
            }
        }
    }
}