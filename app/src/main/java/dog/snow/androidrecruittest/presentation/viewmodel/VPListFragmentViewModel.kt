package dog.snow.androidrecruittest.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dog.snow.androidrecruittest.domain.interactor.VPGetLocalListItemsUseCase
import dog.snow.androidrecruittest.domain.interactor.base.VPEmptySingleObserver
import dog.snow.androidrecruittest.presentation.view.list.model.VPListItem
import dog.snow.androidrecruittest.presentation.viewmodel.VPListFragmentViewModel.ScreenState.ApplySearch
import dog.snow.androidrecruittest.presentation.viewmodel.VPListFragmentViewModel.ScreenState.ShowGeneralError
import dog.snow.androidrecruittest.presentation.viewmodel.VPListFragmentViewModel.ScreenState.ShowData
import javax.inject.Inject

class VPListFragmentViewModel
@Inject constructor(
    private val getLocalListItemsUseCase: VPGetLocalListItemsUseCase
) : ViewModel() {

    val screenState: LiveData<ScreenState> by lazy { mutableScreenState }

    private val mutableScreenState = MutableLiveData<ScreenState>()

    private val mutableListItems: MutableLiveData<List<VPListItem>> = MutableLiveData<List<VPListItem>>().apply { value = emptyList() }

    fun performSearch(search: String) {
        val filteredItems = getMutableListItems().filter { it.title.contains(search) || it.albumTitle.contains(search) }
        mutableScreenState.value = ApplySearch(filteredItems)
    }

    fun getLocalListItems() {
        getLocalListItemsUseCase.execute(observer = GetLocalListItemsObserver())
    }

    private fun getMutableListItems() = mutableListItems.value!!.toMutableList()

    internal inner class GetLocalListItemsObserver : VPEmptySingleObserver<List<VPListItem>>() {
        override fun onSuccess(result: List<VPListItem>) {
            mutableListItems.value = result
            mutableScreenState.value = ShowData(result)
        }

        override fun onError(throwable: Throwable) {
            mutableScreenState.value = ShowGeneralError(throwable.message)
        }
    }

    sealed class ScreenState {
        class ApplySearch(val filteredItems: List<VPListItem>) : ScreenState()
        class ShowData(val items: List<VPListItem>) : ScreenState()
        class ShowGeneralError(val errorMessage: String?) : ScreenState()
    }
}