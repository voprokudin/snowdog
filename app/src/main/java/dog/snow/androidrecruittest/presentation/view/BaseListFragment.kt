package dog.snow.androidrecruittest.presentation.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.base.BaseFragment
import dog.snow.androidrecruittest.extensions.obtainViewModel
import dog.snow.androidrecruittest.presentation.navigation.Navigator
import dog.snow.androidrecruittest.presentation.view.list.listener.ItemClickedListener
import dog.snow.androidrecruittest.presentation.viewmodel.ListFragmentViewModel
import dog.snow.androidrecruittest.presentation.view.list.adapter.ListAdapter
import dog.snow.androidrecruittest.presentation.view.list.model.ListItem
import dog.snow.androidrecruittest.presentation.viewmodel.ListFragmentViewModel.ScreenState.ShowGeneralError
import dog.snow.androidrecruittest.presentation.viewmodel.ListFragmentViewModel.ScreenState.ShowData
import dog.snow.androidrecruittest.presentation.viewmodel.ListFragmentViewModel.ScreenState.ApplySearch
import kotlinx.android.synthetic.main.layout_search.etSearch
import kotlinx.android.synthetic.main.list_fragment.emptyView
import kotlinx.android.synthetic.main.list_fragment.rvItems
import javax.inject.Inject

class BaseListFragment :
    BaseFragment(),
    ItemClickedListener {

    companion object {
        @JvmStatic
        fun newInstance(): BaseListFragment = BaseListFragment()
    }

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { obtainViewModel<ListFragmentViewModel>(viewModelFactory) }

    private var listAdapter: ListAdapter? = null

    override val getLayoutResId: Int = R.layout.list_fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(R.transition.default_transition)
        exitTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.no_transition)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        setupViewModel()
        getLocalListItems()
    }

    override fun onItemRowClicked(listItem: ListItem, imageView: ImageView) {
        navigator.showDetailsFragment(listItem, imageView)
    }

    private fun applySearch(filteredItems: List<ListItem>) {
        listAdapter?.updateList(filteredItems)
    }

    private fun getLocalListItems() {
        viewModel.getLocalListItems()
    }

    private fun setupViewModel() {
        viewModel.screenState.observe(viewLifecycleOwner, ScreenActionObserver())
    }

    private fun showData(items: List<ListItem>) {
        emptyView.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE

        listAdapter = ListAdapter(
                items = items,
                itemsClickedListener = this
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

    private inner class ScreenActionObserver : Observer<ListFragmentViewModel.ScreenState> {
        override fun onChanged(screenAction: ListFragmentViewModel.ScreenState?) {
            screenAction ?: return

            when (screenAction) {
                is ApplySearch -> applySearch(screenAction.filteredItems)
                is ShowData -> showData(screenAction.items)
                is ShowGeneralError -> showError(screenAction.errorMessage)
            }
        }
    }
}