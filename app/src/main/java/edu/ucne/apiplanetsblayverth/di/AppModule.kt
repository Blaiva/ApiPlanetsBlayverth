package edu.ucne.apiplanetsblayverth.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.apiplanetsblayverth.data.remote.remotedatasource.PlanetRemoteDataSource
import edu.ucne.apiplanetsblayverth.data.remote.DragonBallApi
import edu.ucne.apiplanetsblayverth.data.repository.PlanetRepositoryImp
import edu.ucne.apiplanetsblayverth.domain.repository.PlanetRepository
import edu.ucne.apiplanetsblayverth.domain.usecase.planet.GetPlanetDetailUseCase
import edu.ucne.apiplanetsblayverth.domain.usecase.planet.GetPlanetsUseCase
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
    fun providePlanetsApi(moshi: Moshi): DragonBallApi {
        return Retrofit.Builder()
            .baseUrl("https://dragonball-api.com/api/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(DragonBallApi::class.java)
    }

    @Provides
    @Singleton
    fun providePlanetRemoteDataSource(api: DragonBallApi): PlanetRemoteDataSource {
        return PlanetRemoteDataSource(api)
    }

    @Provides
    @Singleton
    fun providePlanetRepository(
        remoteDataSource: PlanetRemoteDataSource
    ): PlanetRepository {
        return PlanetRepositoryImp(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideGetPlanetsUseCase(repository: PlanetRepository): GetPlanetsUseCase {
        return GetPlanetsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetPlanetDetailUseCase(repository: PlanetRepository): GetPlanetDetailUseCase {
        return GetPlanetDetailUseCase(repository)
    }
}