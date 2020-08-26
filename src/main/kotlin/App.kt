import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun main() {

    val runtimeApiEndpoint = "http://${getEnv("AWS_LAMBDA_RUNTIME_API")}"
    val lambdaHandler = handler

    GlobalScope.launch {
        while (true) {
            val next = sendGet<LambdaRequest>("$runtimeApiEndpoint/2018-06-01/runtime/invocation/next")

            val requestId = next.headers.get("lambda-runtime-aws-request-id")
            println("requestId: $requestId")

            try {
                sendPost(
                    "$runtimeApiEndpoint/2018-06-01/runtime/invocation/$requestId/response",
                    lambdaHandler.handle(next.body, next.headers)
                )
            } catch (e: Exception) {
                val msg = ErrorMessage(e::class.simpleName, e.message)
                sendPost(
                    "$runtimeApiEndpoint/2018-06-01/runtime/invocation/$requestId/error",
                    msg
                )
            }
        }
    }

}
