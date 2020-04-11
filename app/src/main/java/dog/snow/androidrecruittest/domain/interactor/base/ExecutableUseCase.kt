package dog.snow.androidrecruittest.domain.interactor.base

import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver

interface ExecutableUseCase {

    interface Completable<in Params> {
        fun execute(
            observer: DisposableCompletableObserver = EmptyCompletableObserver(),
            params: Params? = null
        )
    }

    interface Single<Results, in Params> {
        fun execute(
            observer: DisposableSingleObserver<Results> = EmptySingleObserver(),
            params: Params? = null
        )
    }

    interface Observable<Results, in Params> {
        fun execute(
            observer: DisposableObserver<Results> = EmptyObserver<Results>(),
            params: Params? = null
        )
    }
}