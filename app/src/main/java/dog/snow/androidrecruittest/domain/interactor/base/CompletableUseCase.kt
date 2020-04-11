package dog.snow.androidrecruittest.domain.interactor.base

import io.reactivex.Completable
import io.reactivex.observers.DisposableCompletableObserver

abstract class CompletableUseCase<in Params> :
    BaseReactiveUseCase(),
    ExecutableUseCase.Completable<Params> {

    abstract fun buildUseCaseCompletable(params: Params? = null): Completable

    override fun execute(
        observer: DisposableCompletableObserver,
        params: Params?
    ) {
        val completable = buildUseCaseCompletableWithSchedulers(params)
        addDisposable(completable.subscribeWith(observer))
    }

    private fun buildUseCaseCompletableWithSchedulers(params: Params?): Completable {
        return buildUseCaseCompletable(params)
            .subscribeOn(threadExecutorScheduler)
            .observeOn(postExecutionThreadScheduler)
    }
}