package dog.snow.androidrecruittest.domain.interactor.base

import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver

interface VPExecutableUseCase {

    interface Completable<in Params> {
        fun execute(
            observer: DisposableCompletableObserver = VPEmptyCompletableObserver(),
            params: Params? = null
        )
    }

    interface Single<Results, in Params> {
        fun execute(
            observer: DisposableSingleObserver<Results> = VPEmptySingleObserver(),
            params: Params? = null
        )
    }

    interface Observable<Results, in Params> {
        fun execute(
            observer: DisposableObserver<Results> = VPEmptyObserver<Results>(),
            params: Params? = null
        )
    }
}