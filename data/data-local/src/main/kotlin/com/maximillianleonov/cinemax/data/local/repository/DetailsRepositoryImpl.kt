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

package com.maximillianleonov.cinemax.data.local.repository

import com.maximillianleonov.cinemax.core.data.remote.common.networkBoundResource
import com.maximillianleonov.cinemax.core.domain.result.Result
import com.maximillianleonov.cinemax.data.local.mapper.listMap
import com.maximillianleonov.cinemax.data.local.mapper.toMovieDetailsEntity
import com.maximillianleonov.cinemax.data.local.mapper.toMovieDetailsModel
import com.maximillianleonov.cinemax.data.local.mapper.toTvShowDetailsEntity
import com.maximillianleonov.cinemax.data.local.mapper.toTvShowDetailsModel
import com.maximillianleonov.cinemax.data.local.source.DetailsLocalDataSource
import com.maximillianleonov.cinemax.data.local.source.WishlistLocalDataSource
import com.maximillianleonov.cinemax.data.remote.dto.movie.MovieDetailsDto
import com.maximillianleonov.cinemax.data.remote.dto.tvshow.TvShowDetailsDto
import com.maximillianleonov.cinemax.data.remote.source.DetailsRemoteDataSource
import com.maximillianleonov.cinemax.domain.model.MovieDetailsModel
import com.maximillianleonov.cinemax.domain.model.TvShowDetailsModel
import com.maximillianleonov.cinemax.domain.repository.DetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private val localDataSource: DetailsLocalDataSource,
    private val remoteDataSource: DetailsRemoteDataSource,
    private val wishlistLocalDataSource: WishlistLocalDataSource
) : DetailsRepository {
    override fun getMovieById(id: Int): Flow<Result<MovieDetailsModel?>> = networkBoundResource(
        query = {
            localDataSource.getMovieById(id).map {
                it?.toMovieDetailsModel(
                    isWishlisted = wishlistLocalDataSource.isMovieWishlisted(it.id)
                )
            }
        },
        fetch = { remoteDataSource.getMovieById(id) },
        saveFetchResult = { response ->
            localDataSource.deleteAndInsertMovie(response.toMovieDetailsEntity())
        }
    )

    override fun getTvShowById(id: Int): Flow<Result<TvShowDetailsModel?>> = networkBoundResource(
        query = {
            localDataSource.getTvShowById(id).map {
                it?.toTvShowDetailsModel(
                    isWishlisted = wishlistLocalDataSource.isTvShowWishlisted(it.id)
                )
            }
        },
        fetch = { remoteDataSource.getTvShowById(id) },
        saveFetchResult = { response ->
            localDataSource.deleteAndInsertTvShow(response.toTvShowDetailsEntity())
        }
    )

    override fun getMoviesByIds(ids: List<Int>): Flow<Result<List<MovieDetailsModel>>> =
        networkBoundResource(
            query = {
                localDataSource.getMoviesByIds(ids).listMap {
                    it.toMovieDetailsModel(
                        isWishlisted = wishlistLocalDataSource.isMovieWishlisted(it.id)
                    )
                }
            },
            fetch = { remoteDataSource.getMoviesByIds(ids) },
            saveFetchResult = { response ->
                localDataSource.deleteAndInsertMovies(
                    response.map(MovieDetailsDto::toMovieDetailsEntity)
                )
            }
        )

    override fun getTvShowsByIds(ids: List<Int>): Flow<Result<List<TvShowDetailsModel>>> =
        networkBoundResource(
            query = {
                localDataSource.getTvShowsByIds(ids).listMap {
                    it.toTvShowDetailsModel(
                        isWishlisted = wishlistLocalDataSource.isTvShowWishlisted(it.id)
                    )
                }
            },
            fetch = { remoteDataSource.getTvShowsByIds(ids) },
            saveFetchResult = { response ->
                localDataSource.deleteAndInsertTvShows(
                    response.map(TvShowDetailsDto::toTvShowDetailsEntity)
                )
            }
        )
}
