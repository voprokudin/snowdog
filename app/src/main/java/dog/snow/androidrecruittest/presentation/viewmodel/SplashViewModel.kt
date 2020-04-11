package dog.snow.androidrecruittest.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dog.snow.androidrecruittest.domain.interactor.GetListItemsUseCase
import dog.snow.androidrecruittest.domain.interactor.SaveListItemsUseCase
import dog.snow.androidrecruittest.domain.interactor.base.EmptySingleObserver
import dog.snow.androidrecruittest.presentation.view.list.model.ListItem
import dog.snow.androidrecruittest.util.network.NetworkStateProvider
import javax.inject.Inject

class SplashViewModel
@Inject constructor(
    private val getListItemsUseCase: GetListItemsUseCase,
    private val saveListItemsUseCase: SaveListItemsUseCase,
    private val networkStateProvider: NetworkStateProvider
) : ViewModel() {

    val screenState: LiveData<ScreenState> by lazy { mutableScreenState }

    private val mutableScreenState = MutableLiveData<ScreenState>()

    fun maybeFetchListItems() {
        if (networkStateProvider.isNetworkConnected()) fetchListItems() else mutableScreenState.value = ScreenState.ShowListFragmentWithoutAnimation
    }

    fun saveListItems(listItems: List<ListItem>) {
        saveListItemsUseCase.execute(observer = SaveListItemsObserver(), params = listItems)
    }

    private fun fetchListItems() {
        mutableScreenState.value = ScreenState.SetUpView
        getListItemsUseCase.execute(observer = GetListItemsObserver())
    }

    internal inner class GetListItemsObserver : EmptySingleObserver<List<ListItem>>() {
        override fun onSuccess(result: List<ListItem>) {
            mutableScreenState.value = ScreenState.SaveListItem(result)
        }

        override fun onError(throwable: Throwable) {
            mutableScreenState.value = ScreenState.ShowGeneralError(throwable.message)
        }
    }

    internal inner class SaveListItemsObserver : EmptySingleObserver<List<Long>>() {
        override fun onSuccess(result: List<Long>) {
            mutableScreenState.value = ScreenState.ShowListFragment
        }

        override fun onError(throwable: Throwable) {
            mutableScreenState.value = ScreenState.ShowGeneralError(throwable.message)
        }
    }

    sealed class ScreenState {
        object ShowListFragment : ScreenState()
        object ShowListFragmentWithoutAnimation : ScreenState()
        object SetUpView : ScreenState()
        class SaveListItem(val listItems: List<ListItem>) : ScreenState()
        class ShowGeneralError(val errorMessage: String?) : ScreenState()
    }
}