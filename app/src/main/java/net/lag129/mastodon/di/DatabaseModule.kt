package net.lag129.mastodon.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.lag129.mastodon.data.MastodonDatabase
import net.lag129.mastodon.data.StatusDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideMastodonDatabase(@ApplicationContext context: Context): MastodonDatabase {
        return MastodonDatabase.getDatabase(context)
    }

    @Provides
    fun provideStatusDao(database: MastodonDatabase): StatusDao {
        return database.statusDao()
    }
}