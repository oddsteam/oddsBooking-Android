package com.odds.oddsbooking.di

import android.content.Context
import com.odds.oddsbooking.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
//เอาไปใช้ที่อื่นได้
object NetworkModule {
    @Provides
    fun provideOkHttpClient (@ApplicationContext context: Context): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient
            .Builder()
            .addInterceptor(logging)
            .addSslSocketFactory(context)
            .build()
    }

    //TODO: move another file
    private fun OkHttpClient.Builder.addSslSocketFactory(context: Context) = apply {
        val trustManagers = createTrustManagers(context)
        trustManagers?.let {
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, trustManagers, null)
            this.sslSocketFactory(
                sslContext.socketFactory,
                trustManagers[0] as X509TrustManager,
            )
        }
    }

    //TODO: move to another file
    private fun createTrustManagers(context: Context): Array<TrustManager>? {
        return try {
            val factory = CertificateFactory.getInstance("X.509")
            val inputStream = context.resources.openRawResource(R.raw.odds_team)
            val certificate = factory.generateCertificate(inputStream)
            inputStream.close()

            val keyStoreType: String = KeyStore.getDefaultType()
            val keyStore: KeyStore = KeyStore.getInstance(keyStoreType)
            keyStore.load(null, null)
            keyStore.setCertificateEntry("ca", certificate)

            val algorithm = TrustManagerFactory.getDefaultAlgorithm()
            val trustManagerFactory = TrustManagerFactory.getInstance(algorithm)
            trustManagerFactory.init(keyStore)
            trustManagerFactory.trustManagers
        } catch (e: Exception) {
            null
        }
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val apiUrl = "https://api-odds-booking.odds.team"
        return Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}