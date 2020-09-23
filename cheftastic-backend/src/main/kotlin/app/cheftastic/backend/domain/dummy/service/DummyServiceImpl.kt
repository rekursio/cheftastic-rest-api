package app.cheftastic.backend.domain.dummy.service

import app.cheftastic.backend.data.repository.DummyRepository
import javax.inject.Inject

// TODO: To be removed. Just added to setup and test Dagger
class DummyServiceImpl @Inject constructor(
    private val repository: DummyRepository
) : DummyService {

    override fun foo(): String =
        repository.foo()

}