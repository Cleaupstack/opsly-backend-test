package com.example.demo.integration

import com.example.demo.domain.FaceBookStatus
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service


@Service
class FacebookFeed(@Value("\${integration.facebook.url}") endpoint: String,  var client: OkHttpClient = OkHttpClient() ) : Feeds<FaceBookStatus> {
    val request = Request.Builder()
            .url(endpoint)
            .build()

    override fun getFeeds(): List<FaceBookStatus> {
        client.newCall(request)
                .execute().use {
                    response ->
                    if (!response.isSuccessful) {
                        println("Failed to get response")
                        return emptyList()
                    }
                    val res = response.body!!.string()
                    println("FacebookFeed: response %s".format(res))
                    return  jacksonObjectMapper().readValue(res)
                }
    }

}