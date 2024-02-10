package com.zahand0.moviesearch.data.paging_source

import android.util.Log
import androidx.paging.*
import androidx.room.withTransaction
import com.zahand0.moviesearch.data.local.FilmDatabase
import com.zahand0.moviesearch.data.remote.KinopoiskUnofficialAPI
import com.zahand0.moviesearch.domain.model.Film
import com.zahand0.moviesearch.domain.model.FilmRemoteKeys
import com.zahand0.moviesearch.util.Constants
import javax.inject.Inject

@ExperimentalPagingApi
class FilmRemoteMediator @Inject constructor(
    private val kinopoiskUnofficialAPI: KinopoiskUnofficialAPI,
    private val filmDatabase: FilmDatabase
) : RemoteMediator<Int, Film>() {

    private val filmDao = filmDatabase.filmDao()
    private val filmRemoteKeysDao = filmDatabase.filmRemoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        val currentTime = System.currentTimeMillis()
        val lastUpdated = filmRemoteKeysDao.getFirstRemoteKeys()?.lastUpdated ?: 0L
        Log.d("RemoteMediator", "init: filmRemoteKeys ${filmRemoteKeysDao.getFirstRemoteKeys()}")
        val cacheTimeout = 1440

        val diffInMinutes = (currentTime - lastUpdated) / 1000 / 60
        return if (diffInMinutes.toInt() <= cacheTimeout) {
            Log.d("RemoteMediator", "init: skip refresh")
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            Log.d("RemoteMediator", "init: refresh $diffInMinutes")
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Film>): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = kinopoiskUnofficialAPI.getAllFilms(page = page)
            if (response.films.isNotEmpty()) {
                filmDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        filmDao.deleteAllFilms()
                        filmRemoteKeysDao.deleteAllRemoteKeys()
                    }
                    val prevPage = if (page <= 1) null else page - 1
                    val nextPage =
                        if (page >= response.pagesCount || page >= Constants.API_MAX_PAGE_NUMBER)
                            null
                        else
                            page + 1
                    val keys = response.films.mapIndexed { index, film ->
                        FilmRemoteKeys(
                            id = film.filmId,
                            prevPage = prevPage,
                            nextPage = nextPage,
                            lastUpdated = System.currentTimeMillis(),
                            indexInPage = index
                        )
                    }
                    filmRemoteKeysDao.addAllRemoteKeys(filmRemoteKeys = keys)
                    filmDao.addFilms(films = response.films)
                }
            }
            MediatorResult.Success(endOfPaginationReached = response.pagesCount == page)
        } catch (e: Exception) {
            Log.e("RemoteMediator", "error", e)
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Film>
    ): FilmRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.filmId?.let { id ->
                filmRemoteKeysDao.getRemoteKeys(filmId = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Film>
    ): FilmRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { film ->
                filmRemoteKeysDao.getRemoteKeys(filmId = film.filmId)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Film>
    ): FilmRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { film ->
                filmRemoteKeysDao.getRemoteKeys(filmId = film.filmId)
            }
    }

//    private fun parseMillis(millis: Long): String {
//        val date = Date(millis)
//        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
//        return format.format(date)
//    }

}
















