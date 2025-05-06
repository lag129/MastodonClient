package net.lag129.mastodon

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import net.lag129.mastodon.data.Status
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

interface ApiService {
    @GET("/api/v1/timelines/home")
    suspend fun fetchHomeData(
        @Query("max_id") maxId: String? = null,
        @Query("since_id") sinceId: String? = null,
        @Query("limit") limit: Int? = 20
    ): List<Status>

    @GET("/api/v1/timelines/public")
    suspend fun fetchLocalData(
        @Query("max_id") maxId: String? = null,
        @Query("local") local: Boolean? = true,
        @Query("since_id") sinceId: String? = null,
        @Query("limit") limit: Int? = 20
    ): List<Status>

    @GET("/api/v1/timelines/public")
    suspend fun fetchGlobalData(
        @Query("max_id") maxId: String? = null,
        @Query("local") local: Boolean? = false,
        @Query("since_id") sinceId: String? = null,
        @Query("limit") limit: Int? = 20
    ): List<Status>

    @GET("/api/v1/accounts/{id}/statuses")
    suspend fun fetchAccountData(
        @Path("id") id: String,
        @Query("max_id") maxId: String? = null,
        @Query("since_id") sinceId: String? = null,
        @Query("limit") limit: Int? = 20
    ): List<Status>

    @PUT("/api/v1/statuses/{id}/emoji_reactions/{emoji}")
    suspend fun postReaction(
        @Path("id") id: String,
        @Path("emoji") emoji: String
    ): Status

    @DELETE("/api/v1/statuses/{id}/emoji_reactions/{emoji}")
    suspend fun deleteReaction(
        @Path("id") id: String,
        @Path("emoji") emoji: String
    ): Status
}

@Singleton
class ApiClient @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    private val serverName = dataStoreRepository.getServerName()
    private val bearerToken = dataStoreRepository.getBearerToken()
    private val baseUrl = "https://${serverName}"

    private var cachedServerName: String? = null
    private var cachedBearerToken: String? = null

    //コンソールに出力する
    init {
        // Flow監視用の処理を追加
        CoroutineScope(Dispatchers.IO).launch {
            dataStoreRepository.getServerName().collect { serverName ->
                cachedServerName = serverName
                println("ServerName更新: $serverName")
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            dataStoreRepository.getBearerToken().collect { token ->
                cachedBearerToken = token
                println("BearerToken更新: $token")
            }
        }
    }

    private val authInterceptor = Interceptor { chain ->
        val newRequest: Request = chain
            .request()
            .newBuilder()
            .addHeader("Authorization", "Bearer $bearerToken")
            .build()
        chain.proceed(newRequest)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType())
        )
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
