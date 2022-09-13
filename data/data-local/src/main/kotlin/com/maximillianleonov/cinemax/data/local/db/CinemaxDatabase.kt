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

package com.maximillianleonov.cinemax.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.maximillianleonov.cinemax.data.local.dao.details.MovieDetailsDao
import com.maximillianleonov.cinemax.data.local.dao.details.TvShowDetailsDao
import com.maximillianleonov.cinemax.data.local.dao.discover.DiscoverMovieDao
import com.maximillianleonov.cinemax.data.local.dao.discover.DiscoverMovieRemoteKeyDao
import com.maximillianleonov.cinemax.data.local.dao.discover.DiscoverTvShowDao
import com.maximillianleonov.cinemax.data.local.dao.discover.DiscoverTvShowRemoteKeyDao
import com.maximillianleonov.cinemax.data.local.dao.nowplaying.NowPlayingMovieDao
import com.maximillianleonov.cinemax.data.local.dao.nowplaying.NowPlayingMovieRemoteKeyDao
import com.maximillianleonov.cinemax.data.local.dao.nowplaying.NowPlayingTvShowDao
import com.maximillianleonov.cinemax.data.local.dao.nowplaying.NowPlayingTvShowRemoteKeyDao
import com.maximillianleonov.cinemax.data.local.dao.popular.PopularMovieDao
import com.maximillianleonov.cinemax.data.local.dao.popular.PopularMovieRemoteKeyDao
import com.maximillianleonov.cinemax.data.local.dao.popular.PopularTvShowDao
import com.maximillianleonov.cinemax.data.local.dao.popular.PopularTvShowRemoteKeyDao
import com.maximillianleonov.cinemax.data.local.dao.toprated.TopRatedMovieDao
import com.maximillianleonov.cinemax.data.local.dao.toprated.TopRatedMovieRemoteKeyDao
import com.maximillianleonov.cinemax.data.local.dao.toprated.TopRatedTvShowDao
import com.maximillianleonov.cinemax.data.local.dao.toprated.TopRatedTvShowRemoteKeyDao
import com.maximillianleonov.cinemax.data.local.dao.trending.TrendingMovieDao
import com.maximillianleonov.cinemax.data.local.dao.trending.TrendingMovieRemoteKeyDao
import com.maximillianleonov.cinemax.data.local.dao.trending.TrendingTvShowDao
import com.maximillianleonov.cinemax.data.local.dao.trending.TrendingTvShowRemoteKeyDao
import com.maximillianleonov.cinemax.data.local.dao.upcoming.UpcomingMovieDao
import com.maximillianleonov.cinemax.data.local.dao.upcoming.UpcomingMovieRemoteKeyDao
import com.maximillianleonov.cinemax.data.local.dao.wishlist.WishlistDao
import com.maximillianleonov.cinemax.data.local.entity.details.MovieDetailsEntity
import com.maximillianleonov.cinemax.data.local.entity.details.TvShowDetailsEntity
import com.maximillianleonov.cinemax.data.local.entity.discover.DiscoverMovieEntity
import com.maximillianleonov.cinemax.data.local.entity.discover.DiscoverMovieRemoteKeyEntity
import com.maximillianleonov.cinemax.data.local.entity.discover.DiscoverTvShowEntity
import com.maximillianleonov.cinemax.data.local.entity.discover.DiscoverTvShowRemoteKeyEntity
import com.maximillianleonov.cinemax.data.local.entity.nowplaying.NowPlayingMovieEntity
import com.maximillianleonov.cinemax.data.local.entity.nowplaying.NowPlayingMovieRemoteKeyEntity
import com.maximillianleonov.cinemax.data.local.entity.nowplaying.NowPlayingTvShowEntity
import com.maximillianleonov.cinemax.data.local.entity.nowplaying.NowPlayingTvShowRemoteKeyEntity
import com.maximillianleonov.cinemax.data.local.entity.popular.PopularMovieEntity
import com.maximillianleonov.cinemax.data.local.entity.popular.PopularMovieRemoteKeyEntity
import com.maximillianleonov.cinemax.data.local.entity.popular.PopularTvShowEntity
import com.maximillianleonov.cinemax.data.local.entity.popular.PopularTvShowRemoteKeyEntity
import com.maximillianleonov.cinemax.data.local.entity.toprated.TopRatedMovieEntity
import com.maximillianleonov.cinemax.data.local.entity.toprated.TopRatedMovieRemoteKeyEntity
import com.maximillianleonov.cinemax.data.local.entity.toprated.TopRatedTvShowEntity
import com.maximillianleonov.cinemax.data.local.entity.toprated.TopRatedTvShowRemoteKeyEntity
import com.maximillianleonov.cinemax.data.local.entity.trending.TrendingMovieEntity
import com.maximillianleonov.cinemax.data.local.entity.trending.TrendingMovieRemoteKeyEntity
import com.maximillianleonov.cinemax.data.local.entity.trending.TrendingTvShowEntity
import com.maximillianleonov.cinemax.data.local.entity.trending.TrendingTvShowRemoteKeyEntity
import com.maximillianleonov.cinemax.data.local.entity.upcoming.UpcomingMovieEntity
import com.maximillianleonov.cinemax.data.local.entity.upcoming.UpcomingMovieRemoteKeyEntity
import com.maximillianleonov.cinemax.data.local.entity.wishlist.WishlistEntity

@Database(
    entities = [
        UpcomingMovieEntity::class, UpcomingMovieRemoteKeyEntity::class,
        TopRatedMovieEntity::class, TopRatedMovieRemoteKeyEntity::class,
        TopRatedTvShowEntity::class, TopRatedTvShowRemoteKeyEntity::class,
        PopularMovieEntity::class, PopularMovieRemoteKeyEntity::class,
        PopularTvShowEntity::class, PopularTvShowRemoteKeyEntity::class,
        NowPlayingMovieEntity::class, NowPlayingMovieRemoteKeyEntity::class,
        NowPlayingTvShowEntity::class, NowPlayingTvShowRemoteKeyEntity::class,
        DiscoverMovieEntity::class, DiscoverMovieRemoteKeyEntity::class,
        DiscoverTvShowEntity::class, DiscoverTvShowRemoteKeyEntity::class,
        TrendingMovieEntity::class, TrendingMovieRemoteKeyEntity::class,
        TrendingTvShowEntity::class, TrendingTvShowRemoteKeyEntity::class,
        MovieDetailsEntity::class, TvShowDetailsEntity::class, WishlistEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class CinemaxDatabase : RoomDatabase() {
    abstract val upcomingMovieDao: UpcomingMovieDao
    abstract val upcomingMovieRemoteKeyDao: UpcomingMovieRemoteKeyDao

    abstract val topRatedMovieDao: TopRatedMovieDao
    abstract val topRatedMovieRemoteKeyDao: TopRatedMovieRemoteKeyDao
    abstract val topRatedTvShowDao: TopRatedTvShowDao
    abstract val topRatedTvShowRemoteKeyDao: TopRatedTvShowRemoteKeyDao

    abstract val popularMovieDao: PopularMovieDao
    abstract val popularMovieRemoteKeyDao: PopularMovieRemoteKeyDao
    abstract val popularTvShowDao: PopularTvShowDao
    abstract val popularTvShowRemoteKeyDao: PopularTvShowRemoteKeyDao

    abstract val nowPlayingMovieDao: NowPlayingMovieDao
    abstract val nowPlayingMovieRemoteKeyDao: NowPlayingMovieRemoteKeyDao
    abstract val nowPlayingTvShowDao: NowPlayingTvShowDao
    abstract val nowPlayingTvShowRemoteKeyDao: NowPlayingTvShowRemoteKeyDao

    abstract val discoverMovieDao: DiscoverMovieDao
    abstract val discoverMovieRemoteKeyDao: DiscoverMovieRemoteKeyDao
    abstract val discoverTvShowDao: DiscoverTvShowDao
    abstract val discoverTvShowRemoteKeyDao: DiscoverTvShowRemoteKeyDao

    abstract val trendingMovieDao: TrendingMovieDao
    abstract val trendingMovieRemoteKeyDao: TrendingMovieRemoteKeyDao
    abstract val trendingTvShowDao: TrendingTvShowDao
    abstract val trendingTvShowRemoteKeyDao: TrendingTvShowRemoteKeyDao

    abstract val movieDetailsDao: MovieDetailsDao
    abstract val tvShowDetailsDao: TvShowDetailsDao
    abstract val wishlistDao: WishlistDao

    companion object {
        const val CINEMAX_DATABASE = "cinemax.db"
    }
}
