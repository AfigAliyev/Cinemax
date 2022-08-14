package com.maximillianleonov.cinemax.data.local.source

import com.maximillianleonov.cinemax.data.local.db.CinemaxDatabase
import javax.inject.Inject

class MovieLocalDataSource @Inject constructor(db: CinemaxDatabase) {
    private val upcomingMovieDao = db.upcomingMovieDao
}
