package com.example.demo.service

import com.example.demo.domain.FaceBookStatus
import com.example.demo.domain.InstagramStatus
import com.example.demo.domain.Tweet
import com.example.demo.integration.Feeds
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class FeedServiceTest {

    var instagramFeed = mockk<Feeds<InstagramStatus>>()

    var facebookFeed = mockk<Feeds<FaceBookStatus>>()

    var twitterFeed = mockk<Feeds<Tweet>>()

    var feedService = FeedService(twitterFeed = twitterFeed, facebook = facebookFeed, instagram = instagramFeed)

    @Test
    fun `should return all data when all feeds are available`() {
        every { twitterFeed.getFeeds() } returns listOf(Tweet("jill", "i am happy"))
        every { facebookFeed.getFeeds() } returns listOf(FaceBookStatus("me", "my car"))
        every { instagramFeed.getFeeds() } returns listOf(InstagramStatus("jake", "dog"))

        val response = feedService.get()

        assertThat(response.facebook.size).isEqualTo(1)
        assertThat(response.facebook[0].name).isEqualTo("me")
        assertThat(response.facebook[0].status).isEqualTo("my car")
        assertThat(response.instagram.size).isEqualTo(1)
        assertThat(response.instagram[0].username).isEqualTo("jake")
        assertThat(response.instagram[0].picture).isEqualTo("dog")
//       assertThat(response.twitter.size).isEqualTo(1)
//       assertThat(response.twitter[0].username).isEqualTo("jill")
//       assertThat(response.twitter[0].tweet).isEqualTo("i am happy")

    }
}