package com.openweathermap.app.weatherapp

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver

fun <T> assertObservableSequence(
        obs: Observable<T>,
        vararg result: T) {

    val observer = TestObserver<T>()

    obs.subscribe(observer)

    observer.assertNoErrors()
            .assertComplete()
            .assertValueSequence(result.toMutableList())
}

fun <T> assertSingle(
        single: Single<T>,
        result: T) {
    val observer = TestObserver<T>()

    single.subscribe(observer)

    observer.assertNoErrors()
            .assertComplete()
            .assertResult(result)
}

fun <T> assertSingleError(
        single: Single<T>,
        error: Class<out Throwable>) {
    val observer = TestObserver<T>()
    single.subscribe(observer)
    observer.assertError(error)
}

