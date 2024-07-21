package dev.kilua.dom.core

import java.util.concurrent.CompletableFuture

// TODO: Error handling is't there yet in then and catch
public actual class Promise<T : JsAny?>
internal constructor(internal val completableFuture: CompletableFuture<PromiseResult<T>>) :
    JsAny {
    public actual constructor(executor: (resolve: (T) -> Unit, reject: (JsAny?) -> Unit) -> Unit) : this(
        CompletableFuture<PromiseResult<T>>()
    ) {
        executor(
            { completableFuture.complete(PromiseResult.Success(it)) },
            { completableFuture.complete(PromiseResult.Failure(it)) }
        )
    }

    public actual fun <S : JsAny?> then(onFulfilled: ((T) -> S)?): Promise<S> {
        if (onFulfilled != null) {
            val newFuture = completableFuture.thenApply {
                when (it) {
                    is PromiseResult.Success -> {
                        PromiseResult.Success(onFulfilled(it.value))
                    }

                    is PromiseResult.Failure -> {
                        PromiseResult.Failure(it.error)
                    }
                }
            }
            return Promise(newFuture)
        }

        TODO("Nullable option not yet implemented")
    }

    public actual fun <S : JsAny?> then(
        onFulfilled: (T) -> S,
        onRejected: ((JsAny?) -> S)?,
    ): Promise<S> {
        if (onRejected != null) {
            val newFuture = completableFuture.thenApply<PromiseResult<S>> {
                when (it) {
                    is PromiseResult.Success -> {
                        PromiseResult.Success(onFulfilled(it.value))
                    }

                    is PromiseResult.Failure -> {
                        PromiseResult.Success(onRejected(it.error))
                    }
                }
            }
            return Promise(newFuture)
        }

        return then(onFulfilled)
    }

    public actual fun catch(onRejected: ((JsAny?) -> T)?): Promise<T> {
        if (onRejected != null) {
            val newFuture = completableFuture.thenApply {
                when (it) {
                    is PromiseResult.Success -> {
                        PromiseResult.Success(it.value)
                    }

                    is PromiseResult.Failure -> {
                        PromiseResult.Failure(onRejected(it.error))
                    }
                }
            }
            return Promise(newFuture)
        }

        return this
    }

    public actual fun finally(onFinally: () -> Unit): Promise<T> {
        completableFuture.handle { _, _ -> onFinally() }
        return this
    }

    public actual companion object {
        public actual fun <S : JsAny?> all(promises: JsArray<out Promise<S>>): Promise<JsArray<out S>> {
            @Suppress("performance:SpreadOperator")
            val all = CompletableFuture.allOf(*promises.list.map { it.completableFuture }.toTypedArray()).thenApply {
                val list: List<PromiseResult<out S>> = promises.list.map { it.completableFuture.resultNow() }
                // TODO: Correct?
                if (list.any { it is PromiseResult.Failure }) {
                    val errors: JsArray<JsAny?> =
                        list.map { if (it is PromiseResult.Failure) it.error else null }.toJsArray()
                    PromiseResult.Failure(errors)
                } else {
                    val results: JsArray<out S> = list.map { (it as PromiseResult.Success).value }.toJsArray()
                    PromiseResult.Success(results)
                }
            }
            return Promise(all)
        }

        public actual fun <S : JsAny?> race(promises: JsArray<out Promise<S>>): Promise<S> {
            @Suppress("performance:SpreadOperator")
            val first = CompletableFuture.anyOf(*promises.list.map { it.completableFuture }.toTypedArray()).thenApply {
                promises.list.first { it.completableFuture.isDone }.completableFuture.resultNow()
            }
            return Promise(first)
        }

        public actual fun reject(e: JsAny?): Promise<Nothing> {
            val future: CompletableFuture<PromiseResult<Nothing>> =
                CompletableFuture.completedFuture(PromiseResult.Failure(e))
            return Promise(future)
        }

        public actual fun <S : JsAny?> resolve(r: S): Promise<S> {
            val future: CompletableFuture<PromiseResult<S>> =
                CompletableFuture.completedFuture(PromiseResult.Success(r))
            return Promise(future)
        }

        public actual fun <S : JsAny?> resolve(p: Promise<S>): Promise<S> {
            return p
        }
    }
}

internal sealed interface PromiseResult<T : JsAny?> {
    class Success<T : JsAny?>(val value: T) : PromiseResult<T>
    class Failure<T : JsAny?>(val error: JsAny?) : PromiseResult<T>
}
