package dog.snow.androidrecruittest.domain.interactor.base

import io.reactivex.observers.DisposableSingleObserver

open class EmptySingleObserver<T> : DisposableSingleObserver<T>() {

    override fun onSuccess(result: T) {
    }

    override fun onError(throwable: Throwable) {
    }
}