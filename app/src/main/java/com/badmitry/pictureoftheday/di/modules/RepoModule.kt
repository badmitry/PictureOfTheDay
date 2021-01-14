package com.badmitry.pictureoftheday.di.modules

import com.badmitry.pictureoftheday.mvvm.model.api.IDataSource
import com.badmitry.pictureoftheday.mvvm.model.network.INetworkChecker
import com.badmitry.pictureoftheday.mvvm.model.repo.INasaRepo
import com.badmitry.pictureoftheday.mvvm.model.repo.NasaRepo
import com.badmitry.pictureoftheday.ui.App
import com.badmitry.pictureoftheday.ui.network.AndroidNetworkChecker
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepoModule {
    @Singleton
    @Provides
    fun networkStatus(app: App): INetworkChecker = AndroidNetworkChecker(app)

    @Singleton
    @Provides
    fun getFutureRepo(
        api: IDataSource,
        networkChecker: INetworkChecker
    ): INasaRepo = NasaRepo(api, networkChecker)

}