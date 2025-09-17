package xyz.bittslife.shubhamsharmatask.di

import android.content.Context
import androidx.room.Room
import com.bittslife.shubhamsharmademotest.data.db.HoldingsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import xyz.bittslife.shubhamsharmatask.data.network.ApiService

@InstallIn(SingletonComponent::class)
@Module
object DataModule {
    @Provides
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://35dee773a9ec441e9f38d5fc249406ce.api.mockbin.io")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiService::class.java)
    }

    @Provides
    fun provideDb(@ApplicationContext app: Context): HoldingsDatabase =
        Room.databaseBuilder(app, HoldingsDatabase::class.java, "holdings.db").build()

    @Provides
    fun provideDao(db: HoldingsDatabase) = db.holdingsDao()

}