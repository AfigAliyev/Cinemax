/*
 * Copyright 2022 Maximillian Leonov
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

package com.maximillianleonov.cinemax.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.maximillianleonov.cinemax.core.common.result.HttpException
import com.maximillianleonov.cinemax.core.common.result.isFailure
import com.maximillianleonov.cinemax.core.common.result.isSuccess
import com.maximillianleonov.cinemax.core.data.mapper.asTvShowModel
import com.maximillianleonov.cinemax.core.data.util.Constants
import com.maximillianleonov.cinemax.core.datastore.PreferencesDataStoreDataSource
import com.maximillianleonov.cinemax.core.domain.model.TvShowModel
import com.maximillianleonov.cinemax.core.network.model.tvshow.NetworkTvShow
import com.maximillianleonov.cinemax.core.network.source.TvShowNetworkDataSource
import com.maximillianleonov.cinemax.core.network.util.DEFAULT_PAGE
import kotlinx.coroutines.flow.first
import java.io.IOException

class SearchTvShowPagingSource(
    private val query: String,
    private val networkDataSource: TvShowNetworkDataSource,
    private val preferencesDataStoreDataSource: PreferencesDataStoreDataSource
) : PagingSource<Int, TvShowModel>() {

    override fun getRefreshKey(state: PagingState<Int, TvShowModel>) = state.anchorPosition

    @Suppress("ReturnCount")
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShowModel> {
        return try {
            val currentPage = params.key ?: DEFAULT_PAGE
            val response = networkDataSource.search(
                query = query,
                language = preferencesDataStoreDataSource.getContentLanguage().first(),
                page = currentPage
            )

            when {
                response.isSuccess() -> {
                    val data = response.value.results.map(NetworkTvShow::asTvShowModel)
                    val endOfPaginationReached = data.isEmpty()

                    val prevPage = if (currentPage == 1) null else currentPage - 1
                    val nextPage = if (endOfPaginationReached) null else currentPage + 1

                    LoadResult.Page(
                        data = data,
                        prevKey = prevPage,
                        nextKey = nextPage
                    )
                }
                response.isFailure() -> return LoadResult.Error(response.error)
                else -> error("${Constants.Messages.UNHANDLED_STATE} $response")
            }
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}
