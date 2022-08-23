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

package com.maximillianleonov.cinemax.data.local.source

import androidx.room.withTransaction
import com.maximillianleonov.cinemax.data.local.db.CinemaxDatabase
import com.maximillianleonov.cinemax.data.local.entity.nowplaying.NowPlayingMovieEntity
import com.maximillianleonov.cinemax.data.local.entity.nowplaying.NowPlayingMovieRemoteKeyEntity
import com.maximillianleonov.cinemax.data.local.entity.nowplaying.NowPlayingTvShowEntity
import com.maximillianleonov.cinemax.data.local.entity.nowplaying.NowPlayingTvShowRemoteKeyEntity
import javax.inject.Inject

class NowPlayingLocalDataSource @Inject constructor(private val db: CinemaxDatabase) {
    private val movieDao = db.nowPlayingMovieDao
    private val movieRemoteKeyDao = db.nowPlayingMovieRemoteKeyDao
    private val tvShowDao = db.nowPlayingTvShowDao
    private val tvShowRemoteKeyDao = db.nowPlayingTvShowRemoteKeyDao

    fun getMovies() = movieDao.getAll()
    fun getMoviesPaging() = movieDao.getAllPaging()
    suspend fun getMovieRemoteKeyById(id: Int) = movieRemoteKeyDao.getById(id)

    fun getTvShows() = tvShowDao.getAll()
    fun getTvShowsPaging() = tvShowDao.getAllPaging()
    suspend fun getTvShowRemoteKeyById(id: Int) = tvShowRemoteKeyDao.getById(id)

    suspend fun deleteAndInsertMovies(movies: List<NowPlayingMovieEntity>) = db.withTransaction {
        movieDao.deleteAll()
        movieDao.insertAll(movies)
    }

    suspend fun deleteAndInsertTvShows(tvShows: List<NowPlayingTvShowEntity>) = db.withTransaction {
        tvShowDao.deleteAll()
        tvShowDao.insertAll(tvShows)
    }

    suspend fun handleMoviesPaging(
        shouldDeleteMoviesAndRemoteKeys: Boolean,
        remoteKeys: List<NowPlayingMovieRemoteKeyEntity>,
        movies: List<NowPlayingMovieEntity>
    ) = db.withTransaction {
        if (shouldDeleteMoviesAndRemoteKeys) {
            movieDao.deleteAll()
            movieRemoteKeyDao.deleteAll()
        }
        movieRemoteKeyDao.insertAll(remoteKeys)
        movieDao.insertAll(movies)
    }

    suspend fun handleTvShowsPaging(
        shouldDeleteTvShowsAndRemoteKeys: Boolean,
        remoteKeys: List<NowPlayingTvShowRemoteKeyEntity>,
        tvShows: List<NowPlayingTvShowEntity>
    ) = db.withTransaction {
        if (shouldDeleteTvShowsAndRemoteKeys) {
            tvShowDao.deleteAll()
            tvShowRemoteKeyDao.deleteAll()
        }
        tvShowRemoteKeyDao.insertAll(remoteKeys)
        tvShowDao.insertAll(tvShows)
    }
}
