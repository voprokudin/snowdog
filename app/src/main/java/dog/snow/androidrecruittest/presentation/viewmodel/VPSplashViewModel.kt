package dog.snow.androidrecruittest.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dog.snow.androidrecruittest.data.model.VPRawUser
import dog.snow.androidrecruittest.domain.interactor.VPGetDetailsUseCase
import dog.snow.androidrecruittest.domain.interactor.VPGetListItemsUseCase
import dog.snow.androidrecruittest.domain.interactor.base.VPEmptySingleObserver
import dog.snow.androidrecruittest.presentation.view.list.model.VPDetail
import dog.snow.androidrecruittest.presentation.view.list.model.VPListItem
import javax.inject.Inject

class VPSplashViewModel
@Inject constructor(
    private val getListItemsUseCase: VPGetListItemsUseCase,
    private val getDetailsUseCase: VPGetDetailsUseCase
) : ViewModel() {

    val screenState: LiveData<ScreenState> by lazy { mutableScreenState }

    private val mutableScreenState = MutableLiveData<ScreenState>()

    fun fetchListItems() {
        getListItemsUseCase.execute(observer = GetListItemsObserver())
    }

    fun fetchDetails(listItems: List<VPListItem>) {
        getDetailsUseCase.execute(observer = GetDetailsObserver(), params = listItems)
    }

    internal inner class GetListItemsObserver : VPEmptySingleObserver<List<VPListItem>>() {
        override fun onSuccess(result: List<VPListItem>) {
            println(result)
            println(result.size)
            mutableScreenState.value = ScreenState.FetchDetails(result)
        }

        override fun onError(throwable: Throwable) {
            mutableScreenState.value = ScreenState.ShowGeneralError(throwable.message)
        }
    }

    internal inner class GetDetailsObserver : VPEmptySingleObserver<List<VPDetail>>() {
        override fun onSuccess(result: List<VPDetail>) {
            println(result)
            println(result.size)
            mutableScreenState.value = ScreenState.ShowData(result)
        }

        override fun onError(throwable: Throwable) {
            mutableScreenState.value = ScreenState.ShowGeneralError(throwable.message)
        }
    }

    sealed class ScreenState {
        class FetchDetails(val listItems: List<VPListItem>) : ScreenState()
        class ShowData(val details: List<VPDetail>) : ScreenState()
        class ShowGeneralError(val errorMessage: String?) : ScreenState()
    }
}