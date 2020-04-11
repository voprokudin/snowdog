package dog.snow.androidrecruittest.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dog.snow.androidrecruittest.domain.interactor.GetLocalListItemsUseCase
import dog.snow.androidrecruittest.domain.interactor.base.EmptySingleObserver
import dog.snow.androidrecruittest.presentation.view.list.model.ListItem
import dog.snow.androidrecruittest.presentation.viewmodel.ListFragmentViewModel.ScreenState.ApplySearch
import dog.snow.androidrecruittest.presentation.viewmodel.ListFragmentViewModel.ScreenState.ShowGeneralError
import dog.snow.androidrecruittest.presentation.viewmodel.ListFragmentViewModel.ScreenState.ShowData
import javax.inject.Inject

class ListFragmentViewModel
@Inject constructor(
    private val getLocalListItemsUseCase: GetLocalListItemsUseCase
) : ViewModel() {

    val screenState: LiveData<ScreenState> by lazy { mutableScreenState }

    private val mutableScreenState = MutableLiveData<ScreenState>()

    private val mutableListItems: MutableLiveData<List<ListItem>> = MutableLiveData<List<ListItem>>().apply { value = emptyList() }

    fun performSearch(search: String) {
        val filteredItems = getMutableListItems().filter { it.title.contains(search) || it.albumTitle.contains(search) }
        mutableScreenState.value = ApplySearch(filteredItems)
    }

    fun getLocalListItems() {
        getLocalListItemsUseCase.execute(observer = GetLocalListItemsObserver())
    }

    private fun getMutableListItems() = mutableListItems.value!!.toMutableList()

    internal inner class GetLocalListItemsObserver : EmptySingleObserver<List<ListItem>>() {
        override fun onSuccess(result: List<ListItem>) {
            mutableListItems.value = result
            mutableScreenState.value = ShowData(result)
        }

        override fun onError(throwable: Throwable) {
            mutableScreenState.value = ShowGeneralError(throwable.message)
        }
    }

    sealed class ScreenState {
        class ApplySearch(val filteredItems: List<ListItem>) : ScreenState()
        class ShowData(val items: List<ListItem>) : ScreenState()
        class ShowGeneralError(val errorMessage: String?) : ScreenState()
    }
}