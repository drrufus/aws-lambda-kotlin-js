import kotlinx.serialization.Serializable

@Serializable
data class LambdaRequest(
    val id: Int,
    val name: String
)

@Serializable
data class LambdaResponse(
    val result: String
)

@Serializable
data class ErrorMessage(
    val errorType: String?,
    val errorMessage: String?
)
