package dog.snow.androidrecruittest.domain.interactor.base

import io.reactivex.observers.DisposableCompletableObserver

open class EmptyCompletableObserver : DisposableCompletableObserver() {

    override fun onComplete() {
    }

    override fun onError(throwable: Throwable) {
    }
}