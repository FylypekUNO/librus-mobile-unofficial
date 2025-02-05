package pl.fylypek.librus_mobile_unofficial

//import com.google.gson.Gson

data class HttpResponse(
    val status: Int,
    val headers: Map<String, List<String>>,
    val body: String
)

fun HttpResponse.ok(): Boolean {
    return this.status in 200..299
}

//fun HttpResponse.json(): Map<*, *> {
//    return Gson().fromJson(this.body, Map::class.java) as Map<*, *>
//}

//inline fun <reified T> HttpResponse.json(): T {
//    return Gson().fromJson(this.body, T::class.java)
//}

fun HttpResponse.text(): String {
    return this.body
}