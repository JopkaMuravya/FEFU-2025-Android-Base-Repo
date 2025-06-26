package co.feip.fefu2025

import android.app.Application
import co.feip.fefu2025.data.repository.AnimeRepositoryImpl
import co.feip.fefu2025.domain.repository.AnimeRepository

class MyApplication : Application() {
    lateinit var repository: AnimeRepository
        private set

    override fun onCreate() {
        super.onCreate()
        repository = AnimeRepositoryImpl(this)
    }
}