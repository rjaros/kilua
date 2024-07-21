package dev.kilua.dom.core

public expect class Promise<T : JsAny?>(executor: (resolve: (T) -> Unit, reject: (JsAny?) -> Unit) -> Unit) :
    JsAny {
    public fun <S : JsAny?> then(onFulfilled: ((T) -> S)?): Promise<S>

    public fun <S : JsAny?> then(onFulfilled: (T) -> S, onRejected: ((JsAny?) -> S)?): Promise<S>

    public fun catch(onRejected: ((JsAny?) -> T)?): Promise<T>
    public fun finally(onFinally: () -> Unit): Promise<T>

    public companion object {
        public fun <S : JsAny?> all(promises: JsArray<out Promise<S>>): Promise<JsArray<out S>>
        public fun <S : JsAny?> race(promises: JsArray<out Promise<S>>): Promise<S>
        public fun reject(e: JsAny?): Promise<Nothing>
        public fun <S : JsAny?> resolve(r: S): Promise<S>
        public fun <S : JsAny?> resolve(p: Promise<S>): Promise<S>
    }

}
