package uz.fergana.developer.api

import android.content.Context
import android.os.Build
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import uz.fergana.developer.utils.Constants
import java.security.KeyStore
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager


object Client {

    var retrofit: Retrofit? = null

    fun initClient(context: Context, host: String) {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        retrofit = Retrofit.Builder()
            .baseUrl(host)
            .client(getOkHttpClient(context))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun getInstance(context: Context): Retrofit {
        if (retrofit != null) {
            return retrofit!!
        } else {
            initClient(context, Constants.HOST)
            return retrofit!!
        }
    }

    fun getOkHttpClient(context: Context): OkHttpClient {
        var builder = OkHttpClient().newBuilder()
        builder.retryOnConnectionFailure(false)
        builder.connectTimeout(60, TimeUnit.SECONDS)
        builder.writeTimeout(60, TimeUnit.SECONDS)
        builder.readTimeout(60, TimeUnit.SECONDS)
        if (true) {
            builder.addInterceptor(
                ChuckerInterceptor.Builder(context)
                    .collector(ChuckerCollector(context))
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(true)
                    .build()
            )
        }
        builder.addInterceptor(AppInterceptor())
        builder = enableTls12OnPreLollipop(builder)

        return builder.build()
    }

    private fun enableTls12OnPreLollipop(client: OkHttpClient.Builder): OkHttpClient.Builder {
        if (Build.VERSION.SDK_INT in 16..21) {
            try {
                val trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
                trustManagerFactory.init(null as KeyStore?)
                val trustManagers = trustManagerFactory.trustManagers
                if (trustManagers.size != 1 || trustManagers[0] !is X509TrustManager) {
                    throw IllegalStateException(
                        "Unexpected default trust managers:" + Arrays.toString(
                            trustManagers
                        )
                    )
                }
                val trustManager = trustManagers[0] as X509TrustManager
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, arrayOf(trustManager), null)
                val sslSocketFactory = sslContext.socketFactory
                return client.sslSocketFactory(sslSocketFactory, trustManager)
            } catch (e: Exception) {
                return client
            }
        } else {
            return client

        }

    }

    class AppInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()
            return chain.proceed(getRequest(original))
        }

        fun getRequest(original: Request): Request {
            var builder = original.newBuilder()
            builder.addHeader("Content-Type", "application/json")
            builder.addHeader("Connection", "close")
            builder.addHeader("X-Mobile-Type", "android")
            builder.addHeader("X-Mobile-OS-Version", Build.VERSION.SDK_INT.toString())
//            if (!Prefs.getToken().isNullOrEmpty()) {
//                builder.addHeader("token", Prefs.getToken())
//            }
//            builder.method(original.method(), original.body())
            return builder.build()
        }
    }
}