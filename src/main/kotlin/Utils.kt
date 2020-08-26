import org.w3c.fetch.Headers
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import org.w3c.fetch.RequestInit
import org.w3c.fetch.Response
import kotlin.js.Promise

@JsModule("node-fetch")
@JsNonModule
external fun fetch(url: String): Promise<Response>

@JsModule("node-fetch")
@JsNonModule
external fun fetch(url: String, params: RequestInit): Promise<Response>

private external val process: Process

private external interface Process {
    val env: dynamic
}

fun getEnv(name: String): String? = process.env[name].unsafeCast<String?>()

suspend inline fun <reified T: Any> sendGet(url: String): FetchResponse<T> {
    return suspendCoroutine { continuation ->
        fetch(url).then {
            val headers = it.headers
            it.text().then { bodyString ->
                continuation.resume(FetchResponse(
                    headers,
                    Json.decodeFromString(bodyString)
                ))
            }
        }
    }
}

suspend fun sendPost(url: String, body: Any): String {
    val request = RequestInit(
        method = "POST",
        headers = mapOf("Content-Type" to "application/json"),
        body = Json.encodeToString(body)
    )
    return suspendCoroutine { continuation ->
        fetch(
            url,
            request
        ).then { it.text() }
            .then { continuation.resume(it) }
    }
}

data class FetchResponse <T>(
    val headers: Headers,
    val body: T
)

fun interface Handler {
    fun handle(request: LambdaRequest, headers: Headers): LambdaResponse
}
