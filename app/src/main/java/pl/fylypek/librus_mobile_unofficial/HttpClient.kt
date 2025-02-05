package pl.fylypek.librus_mobile_unofficial

class HttpClient(private val defaultHeaders: Map<String, String> = emptyMap()) {

    fun get(url: String): Promise<HttpResponse> {
        return fetch(
            url,
            FetchOptions(
                method = "GET",
                headers = defaultHeaders,
                body = null
            )
        )
    }

    fun post(url: String, body: String): Promise<HttpResponse> {
        return fetch(
            url,
            FetchOptions(
                method = "POST",
                headers = defaultHeaders,
                body = body
            )
        )
    }
}
