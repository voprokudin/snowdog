package dog.snow.androidrecruittest.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dog.snow.androidrecruittest.domain.interactor.base.VPEmptySingleObserver
import javax.inject.Inject

class VPListFragmentViewModel
@Inject constructor(
//    private val getProductsCodesUseCase: VPGetProductsCodesUseCase
) : ViewModel() {

    val screenState: LiveData<ScreenState> by lazy { mutableScreenState }

    private val mutableScreenState = MutableLiveData<ScreenState>()

    fun getData() {
//        getProductsCodesUseCase.execute(observer = GetProductsCodesObserver())
        val items = arrayListOf<String>()
        items.add("A")
        items.add("B")
        items.add("C")
        mutableScreenState.value = ScreenState.ShowData(items)
    }

    internal inner class GetProductsCodesObserver : VPEmptySingleObserver<ArrayList<String>>() {
        override fun onSuccess(result: ArrayList<String>) {
            mutableScreenState.value = ScreenState.ShowData(result)
        }

        override fun onError(throwable: Throwable) {
            mutableScreenState.value = ScreenState.ShowGeneralError(throwable.message)
        }
    }

    sealed class ScreenState {
        class ShowData(val items: ArrayList<String>) : ScreenState()
        class ShowGeneralError(val errorMessage: String?) : ScreenState()
    }
}