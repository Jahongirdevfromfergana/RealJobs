package uz.fergana.developer.repository

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import uz.fergana.developer.MyApp
import uz.fergana.developer.api.Api
import uz.fergana.developer.api.Client


open class BaseRepository {

    val api = Client.getInstance(MyApp.app).create(Api::class.java)

    val compositeDisposable = CompositeDisposable()

    protected fun addDisposable(disposable: Disposable): Disposable {
        compositeDisposable.add(disposable)
        return disposable
    }
}