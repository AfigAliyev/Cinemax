package com.maximillianleonov.cinemax.data.local.source

import androidx.room.withTransaction
import com.maximillianleonov.cinemax.data.local.db.CinemaxDatabase
import com.maximillianleonov.cinemax.data.local.entity.upcoming.UpcomingMovieEntity
import javax.inject.Inject

class MovieLocalDataSource @Inject constructor(private val db: CinemaxDatabase) {
    private val upcomingMovieDao = db.upcomingMovieDao

    fun getUpcomingMovies() = upcomingMovieDao.getAll()
    fun getUpcomingMoviesPaging() = upcomingMovieDao.getAllPaging()
    suspend fun deleteAndInsertUpcomingMovies(entities: List<UpcomingMovieEntity>) =
        db.withTransaction {
            upcomingMovieDao.deleteAll()
            upcomingMovieDao.insertAll(entities)
        }
}
