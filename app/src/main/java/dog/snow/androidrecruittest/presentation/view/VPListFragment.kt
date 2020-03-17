package dog.snow.androidrecruittest.presentation.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import dog.snow.androidrecruittest.presentation.viewmodel.VPListFragmentViewModel.ScreenState.ApplySearch
import kotlinx.android.synthetic.main.layout_search.etSearch
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

    private var listAdapter: VPListAdapter? = null

    override val getLayoutResId: Int = R.layout.list_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        setupViewModel()
        getLocalListItems()
    }

    override fun onItemRowClicked(listItem: VPListItem) {
        navigator.showDetailsFragment(listItem)
    }

    private fun applySearch(filteredItems: List<VPListItem>) {
        listAdapter?.updateList(filteredItems)
    }

    private fun getLocalListItems() {
        viewModel.getLocalListItems()
    }

    private fun setupViewModel() {
        viewModel.screenState.observe(viewLifecycleOwner, ScreenActionObserver())
    }

    private fun showData(items: List<VPListItem>) {
        emptyView.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE

        listAdapter = VPListAdapter(
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

    private fun setUpView() {
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                viewModel.performSearch(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        })
    }

    private inner class ScreenActionObserver : Observer<VPListFragmentViewModel.ScreenState> {
        override fun onChanged(screenAction: VPListFragmentViewModel.ScreenState?) {
            screenAction ?: return

            when (screenAction) {
                is ApplySearch -> applySearch(screenAction.filteredItems)
                is ShowData -> showData(screenAction.items)
                is ShowGeneralError -> showError(screenAction.errorMessage)
            }
        }
    }
}