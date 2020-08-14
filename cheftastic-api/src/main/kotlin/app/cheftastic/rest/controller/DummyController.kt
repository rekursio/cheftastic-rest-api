package app.cheftastic.rest.controller

object DummyController {

    fun helloWorld(): String =
        System.getenv()["APP_NAME"]?.let { "Hello $it" } ?: "Hello World"

}