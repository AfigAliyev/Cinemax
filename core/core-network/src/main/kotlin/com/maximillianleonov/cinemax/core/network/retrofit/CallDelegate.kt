/*
 * Copyright 2022 Afig Aliyev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.maximillianleonov.cinemax.core.network.retrofit

import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal abstract class CallDelegate<In, Out>(protected val proxy: Call<In>) : Call<Out> {
    abstract fun enqueueImpl(callback: Callback<Out>)
    abstract fun cloneImpl(): Call<Out>
    final override fun clone(): Call<Out> = cloneImpl()
    final override fun enqueue(callback: Callback<Out>) = enqueueImpl(callback)
    override fun execute(): Response<Out> = throw NotImplementedError()
    override fun isExecuted(): Boolean = proxy.isExecuted
    override fun cancel() = proxy.cancel()
    override fun isCanceled(): Boolean = proxy.isCanceled
    override fun request(): Request = proxy.request()
}
