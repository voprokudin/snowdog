package dog.snow.androidrecruittest.presentation.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.base.BaseFragment
import dog.snow.androidrecruittest.extensions.obtainViewModel
import dog.snow.androidrecruittest.presentation.navigation.Navigator
import dog.snow.androidrecruittest.presentation.view.list.listener.ItemClickedListener
import dog.snow.androidrecruittest.presentation.viewmodel.HomeActivityViewModel.ScreenState.ApplySearch
import dog.snow.androidrecruittest.presentation.viewmodel.HomeActivityViewModel.ScreenState.ShowGeneralError
import dog.snow.androidrecruittest.presentation.view.list.adapter.ListItemsAdapter
import dog.snow.androidrecruittest.presentation.view.list.model.ListItem
import dog.snow.androidrecruittest.presentation.viewmodel.HomeActivityViewModel
import kotlinx.android.synthetic.main.layout_search.etSearch
import kotlinx.android.synthetic.main.list_fragment.emptyView
import kotlinx.android.synthetic.main.list_fragment.rvItems
import javax.inject.Inject

class ListFragment :
    BaseFragment(),
    ItemClickedListener {

    companion object {
        @JvmStatic
        fun newInstance(): ListFragment = ListFragment()
    }

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var listItemsAdapter: ListItemsAdapter

    private val viewModel by lazy { getBaseActivity().obtainViewModel<HomeActivityViewModel>(viewModelFactory) }

    override val getLayoutResId: Int = R.layout.list_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        setupViewModel()
    }

    override fun onItemRowClicked(listItem: ListItem, imageView: ImageView) {
        navigator.showDetailsFragment(listItem, imageView)
    }

    private fun applySearch(filteredItems: List<ListItem>) {
        showData(filteredItems)
    }

    private fun getLocalListItems() {
        viewModel.getLocalListItems()
    }

    private fun setupViewModel() {
        viewModel.screenState.observe(viewLifecycleOwner, ScreenActionObserver())
        viewModel.listItems.observe(viewLifecycleOwner, Observer { showData(it) })
    }

    private fun showData(items: List<ListItem>) {
        emptyView.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE

        listItemsAdapter.run {
            itemsClickedListener = this@ListFragment
            submitList(items.toMutableList())
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
        setUpRecyclerView()
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                viewModel.performSearch(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        })
    }

    private fun setUpRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context)
        rvItems.run {
            visibility = View.VISIBLE
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = listItemsAdapter
        }
    }

    private inner class ScreenActionObserver : Observer<HomeActivityViewModel.ScreenState> {
        override fun onChanged(screenAction: HomeActivityViewModel.ScreenState?) {
            screenAction ?: return

            when (screenAction) {
                is ApplySearch -> applySearch(screenAction.filteredItems)
                is ShowGeneralError -> showError(screenAction.errorMessage)
            }
        }
    }
}