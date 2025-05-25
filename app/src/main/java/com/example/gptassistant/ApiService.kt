import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Header
import retrofit2.http.Body
interface OpenAIApi {
    @POST("v1/chat/completions")
    suspend fun getResponse(
        @Header("Authorization") auth: String,
        @Body request: ChatRequest
    ): Response<ChatResponse>
}

data class ChatRequest(
    val model: String = "gpt-3.5-turbo",
    val messages: List<MessageRequest>
)

data class MessageRequest(
    val role: String, // "user" или "assistant"
    val content: String
)

data class ChatResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: MessageRequest
)
