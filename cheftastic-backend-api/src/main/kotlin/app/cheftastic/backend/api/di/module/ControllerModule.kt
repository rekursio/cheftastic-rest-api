package app.cheftastic.backend.api.di.module

import app.cheftastic.backend.api.controller.DummyController
import app.cheftastic.backend.api.controller.DummyControllerImpl
import dagger.Binds
import dagger.Module

@Module
interface ControllerModule {

    @Binds
    fun bindDummyController(controller: DummyControllerImpl): DummyController

}