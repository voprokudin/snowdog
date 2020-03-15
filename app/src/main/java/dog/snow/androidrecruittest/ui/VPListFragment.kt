package dog.snow.androidrecruittest.ui

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
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
import dog.snow.androidrecruittest.presentation.viewmodel.VPSplashViewModel
import dog.snow.androidrecruittest.ui.adapter.VPListAdapter
import kotlinx.android.synthetic.main.list_fragment.*
import java.util.ArrayList
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
        getData()
    }

    private fun getData() {
        viewModel.getData()
    }

    private fun setupViewModel() {
        viewModel.screenState.observe(this, ScreenActionObserver())
    }

    private fun showData(items: ArrayList<String>) {
        emptyView.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE

        val listAdapter = VPListAdapter(
            items = items,
            itemsClickedListener = this
        )
        rvItems.run {
            visibility = View.VISIBLE
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = listAdapter
//            addItemDecoration(VPDividerItemDecoration(resources.getDrawable(R.drawable.vp_line_devider)))
        }
    }

    private fun showError(errorMessage: String?) {
        val r = Runnable {
            MaterialAlertDialogBuilder(activity)
                .setTitle(R.string.cant_download_dialog_title)
                .setMessage(getString(R.string.cant_download_dialog_message, errorMessage))
                .setPositiveButton(R.string.cant_download_dialog_btn_positive) { _, _ ->  getData()}
                .setNegativeButton(R.string.cant_download_dialog_btn_negative) { _, _ ->  }
                .create()
                .apply { setCanceledOnTouchOutside(false) }
                .show()
        }
        Handler().postDelayed(r, 2000)
    }

    override fun onItemRowClicked() {
        Toast.makeText(activity, "This is my Toast message!", Toast.LENGTH_SHORT).show()
        navigator.showDetailsFragment()
    }

    private inner class ScreenActionObserver : Observer<VPListFragmentViewModel.ScreenState> {
        override fun onChanged(screenAction: VPListFragmentViewModel.ScreenState?) {
            screenAction ?: return

            when (screenAction) {
                is VPListFragmentViewModel.ScreenState.ShowData -> showData(screenAction.items)
                is VPListFragmentViewModel.ScreenState.ShowGeneralError -> showError(screenAction.errorMessage)
            }
        }
    }
}