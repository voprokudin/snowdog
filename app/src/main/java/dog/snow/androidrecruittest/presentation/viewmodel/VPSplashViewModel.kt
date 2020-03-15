package dog.snow.androidrecruittest.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dog.snow.androidrecruittest.domain.interactor.VPGetPhotosUseCase
import dog.snow.androidrecruittest.domain.interactor.base.VPEmptySingleObserver
import dog.snow.androidrecruittest.presentation.view.list.model.VPListItem
import javax.inject.Inject

class VPSplashViewModel
@Inject constructor(
    private val getPhotosUseCase: VPGetPhotosUseCase
) : ViewModel() {

    val screenState: LiveData<ScreenState> by lazy { mutableScreenState }

    private val mutableScreenState = MutableLiveData<ScreenState>()

    fun fetchData() {
        getPhotosUseCase.execute(observer = GetPhotosObserver())
//        val list = arrayListOf<String>()
//        list.add("df")
//        mutableScreenState.value = ScreenState.ShowData(list)
    }

    internal inner class GetPhotosObserver : VPEmptySingleObserver<List<VPListItem>>() {
        override fun onSuccess(result: List<VPListItem>) {
            mutableScreenState.value = ScreenState.ShowData(result)
        }

        override fun onError(throwable: Throwable) {
            mutableScreenState.value = ScreenState.ShowGeneralError(throwable.message)
        }
    }

    sealed class ScreenState {
        class ShowData(val photos: List<VPListItem>) : ScreenState()
        class ShowGeneralError(val errorMessage: String?) : ScreenState()
    }
}