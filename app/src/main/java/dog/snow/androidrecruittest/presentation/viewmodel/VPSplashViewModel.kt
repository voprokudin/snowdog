package dog.snow.androidrecruittest.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dog.snow.androidrecruittest.domain.interactor.VPGetListItemsUseCase
import dog.snow.androidrecruittest.domain.interactor.VPGetLocalListItemsUseCase
import dog.snow.androidrecruittest.domain.interactor.VPSaveListItemsUseCase
import dog.snow.androidrecruittest.domain.interactor.base.VPEmptySingleObserver
import dog.snow.androidrecruittest.presentation.view.list.model.VPListItem
import dog.snow.androidrecruittest.util.network.VPNetworkStateProvider
import javax.inject.Inject

class VPSplashViewModel
@Inject constructor(
    private val getListItemsUseCase: VPGetListItemsUseCase,
    private val saveListItemsUseCase: VPSaveListItemsUseCase,
    private val networkStateProvider: VPNetworkStateProvider
) : ViewModel() {

    val screenState: LiveData<ScreenState> by lazy { mutableScreenState }

    private val mutableScreenState = MutableLiveData<ScreenState>()

    private fun fetchListItems() {
        getListItemsUseCase.execute(observer = GetListItemsObserver())
    }

    fun saveListItems(listItems: List<VPListItem>) {
        saveListItemsUseCase.execute(observer = SaveListItemsObserver(), params = listItems)
    }

    fun maybeFetchListItems() {
        if (networkStateProvider.isNetworkConnected()) fetchListItems() else mutableScreenState.value = ScreenState.ShowListFragment
    }

    internal inner class GetListItemsObserver : VPEmptySingleObserver<List<VPListItem>>() {
        override fun onSuccess(result: List<VPListItem>) {
            mutableScreenState.value = ScreenState.SaveListItem(result)
        }

        override fun onError(throwable: Throwable) {
            mutableScreenState.value = ScreenState.ShowGeneralError(throwable.message)
        }
    }

    internal inner class SaveListItemsObserver : VPEmptySingleObserver<List<Long>>() {
        override fun onSuccess(result: List<Long>) {
            mutableScreenState.value = ScreenState.ShowListFragment
        }

        override fun onError(throwable: Throwable) {
            mutableScreenState.value = ScreenState.ShowGeneralError(throwable.message)
        }
    }

    sealed class ScreenState {
        object ShowListFragment : ScreenState()
        class SaveListItem(val listItems: List<VPListItem>) : ScreenState()
        class ShowGeneralError(val errorMessage: String?) : ScreenState()
    }
}