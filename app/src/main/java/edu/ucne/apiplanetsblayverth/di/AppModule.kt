package edu.ucne.apiplanetsblayverth.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.apiplanetsblayverth.data.remote.PlanetsApi
import edu.ucne.apiplanetsblayverth.data.repository.PlanetRepositoryImp
import edu.ucne.apiplanetsblayverth.domain.repository.PlanetRepository
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideMoshi(): Moshi{
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    @Singleton
    fun provideApi(moshi: Moshi): PlanetsApi{
        return Retrofit.Builder().baseUrl("https://dragonball-api.com/api/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build().create(PlanetsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(api: PlanetsApi): PlanetRepository{
        return PlanetRepositoryImp(api)
    }
}