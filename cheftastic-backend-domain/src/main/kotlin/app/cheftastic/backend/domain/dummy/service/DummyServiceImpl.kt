package app.cheftastic.backend.domain.dummy.service

import app.cheftastic.backend.data.repository.DummyRepository
import app.cheftastic.backend.domain.di.DomainScope
import javax.inject.Inject

// TODO: To be removed. Just added to setup and test Dagger
@DomainScope
class DummyServiceImpl @Inject constructor(
    private val repository: DummyRepository
) : DummyService {

    override fun foo(): String =
        repository.foo()

}