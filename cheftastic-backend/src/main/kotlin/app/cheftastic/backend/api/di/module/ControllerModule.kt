package app.cheftastic.backend.api.di.module

import app.cheftastic.backend.api.controller.DummyController
import app.cheftastic.backend.api.controller.DummyControllerImpl
import app.cheftastic.backend.api.di.ApiScope
import dagger.Binds
import dagger.Module

@Module
interface ControllerModule {

    @Binds
    @ApiScope
    fun bindDummyController(controller: DummyControllerImpl): DummyController

}