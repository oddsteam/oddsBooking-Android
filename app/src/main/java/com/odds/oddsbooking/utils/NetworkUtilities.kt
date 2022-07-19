package com.odds.oddsbooking.utils

import android.content.Context
import com.odds.oddsbooking.R
import okhttp3.OkHttpClient
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

object NetworkUtilities {
    fun OkHttpClient.Builder.addSslSocketFactory(context: Context) = apply {
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
}