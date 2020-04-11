package dog.snow.androidrecruittest.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dog.snow.androidrecruittest.domain.interactor.GetLocalListItemsUseCase
import dog.snow.androidrecruittest.domain.interactor.base.EmptySingleObserver
import dog.snow.androidrecruittest.presentation.view.list.model.ListItem
import javax.inject.Inject

class HomeActivityViewModel
@Inject constructor(
    private val getLocalListItemsUseCase: GetLocalListItemsUseCase
) : ViewModel() {

    val screenState: LiveData<ScreenState> by lazy { mutableScreenState }

    private val mutableScreenState = MutableLiveData<ScreenState>()

    val listItems: LiveData<List<ListItem>> by lazy { mutableListItems }

    private val mutableListItems: MutableLiveData<List<ListItem>> = MutableLiveData<List<ListItem>>().apply { value = emptyList() }

    fun getLocalListItems() {
        getLocalListItemsUseCase.execute(observer = GetLocalListItemsObserver())
    }

    fun performSearch(search: String) {
        val filteredItems = getMutableListItems().filter { it.title.contains(search) || it.albumTitle.contains(search) }
        mutableScreenState.value = ScreenState.ApplySearch(filteredItems)
    }

    private fun getMutableListItems() = mutableListItems.value!!.toMutableList()

    internal inner class GetLocalListItemsObserver : EmptySingleObserver<List<ListItem>>() {
        override fun onSuccess(result: List<ListItem>) {
            mutableListItems.value = result
        }

        override fun onError(throwable: Throwable) {
            mutableScreenState.value = ScreenState.ShowGeneralError(throwable.message)
        }
    }

    sealed class ScreenState {
        class ApplySearch(val filteredItems: List<ListItem>) : ScreenState()
        class ShowGeneralError(val errorMessage: String?) : ScreenState()
    }
}