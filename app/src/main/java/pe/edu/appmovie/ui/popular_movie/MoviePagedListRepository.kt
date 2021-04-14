package pe.edu.appmovie.ui.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import pe.edu.appmovie.data.api.POST_PER_PAGE
import pe.edu.appmovie.data.api.TheMovieDBInterface
import pe.edu.appmovie.data.repository.MovieDataSource
import pe.edu.appmovie.data.repository.MovieDataSourceFactory
import pe.edu.appmovie.data.repository.NetworkState
import pe.edu.appmovie.data.vo.Movie

class MoviePagedListRepository(private val apiService: TheMovieDBInterface) {

    lateinit var moviePagedList : LiveData<PagedList<Movie>>
    lateinit var moviesDataSourceFactory : MovieDataSourceFactory

    fun fetchLiveMoviePagedList(compositeDisposable: CompositeDisposable) : LiveData<PagedList<Movie>> {

        moviesDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(POST_PER_PAGE)
                .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return moviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSource, NetworkState>(
                moviesDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState)
    }
}