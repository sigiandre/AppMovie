package pe.edu.appmovie.ui.single_movie_details

import androidx.lifecycle.LiveData
import io.reactivex.disposables.CompositeDisposable
import pe.edu.appmovie.data.api.TheMovieDBInterface
import pe.edu.appmovie.data.repository.MovieDetailsNetworkDataSource
import pe.edu.appmovie.data.repository.NetworkState
import pe.edu.appmovie.data.vo.MovieDetails

class MovieDetailsRepository (private val apiService : TheMovieDBInterface) {

    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchSingleMovieDetails (compositeDisposable: CompositeDisposable, movieId: Int) : LiveData<MovieDetails> {

        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieResponse

    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }
}