package dodoz.cs.rmutt.canteenwallet.Retrofit

import android.util.Base64
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {

    private val AUTH = "Basic "+ Base64.encodeToString(
        "dodozkung:123456".toByteArray(),
        Base64.NO_WRAP
    )

//    private const val BASE_URL = "http://nackmine.ddns.net/dodoz/apiddz/myapi/public"
      private const val BASE_URL = "http://nhost.online:8080/dodoz/apiddz/myapi/public/"
//    private const val BASE_URL = "http://172.20.10.3/myapi/public/"

    var gson = GsonBuilder()
        .setLenient()
        .create()

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder()
                .addHeader("Authorization", AUTH)
                .method(original.method(), original.body())

            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()

    val instance: Api by lazy {
        val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()

        retrofit.create(Api::class.java)
        }
        }