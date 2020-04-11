package dog.snow.androidrecruittest.domain.interactor.base

import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver

abstract class BaseSingleUseCase<Results, in Params> :
    BaseReactiveUseCase(),
    ExecutableUseCase.Single<Results, Params> {

    abstract fun buildUseCaseSingle(params: Params? = null): Single<Results>

    override fun execute(
        observer: DisposableSingleObserver<Results>,
        params: Params?
    ) {
        val single = buildUseCaseSingleWithSchedulers(params)
        addDisposable(single.subscribeWith(observer))
    }

    private fun buildUseCaseSingleWithSchedulers(params: Params?): Single<Results> = buildUseCaseSingle(params)
        .subscribeOn(threadExecutorScheduler)
        .observeOn(postExecutionThreadScheduler)
}