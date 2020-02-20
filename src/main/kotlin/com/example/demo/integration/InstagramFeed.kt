package com.example.demo.integration

import com.example.demo.domain.InstagramStatus
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class InstagramFeed(@Value("\${integration.instagram.url}") endpoint: String, var client: OkHttpClient = OkHttpClient()) : Feeds<InstagramStatus> {

    val request = Request.Builder()
            .url(endpoint)
            .build()

    override fun getFeeds(): List<InstagramStatus> {
        client.newCall(request)
                .execute().use { response ->
                    if (!response.isSuccessful) {
                        println("Failed to get response")
                        return emptyList()
                    }
                    val res = response.body!!.string()
                    println("InstagramFeed: response %s".format(res))
                    return jacksonObjectMapper().readValue(res)
                }
    }

}