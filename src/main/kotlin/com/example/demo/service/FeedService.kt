package com.example.demo.service

import com.example.demo.domain.FaceBookStatus
import com.example.demo.domain.FeedDTO
import com.example.demo.domain.InstagramStatus
import com.example.demo.domain.Tweet
import com.example.demo.integration.Feeds
import org.springframework.stereotype.Service

@Service
class FeedService(private val twitterFeed: Feeds<Tweet>,
                  private val facebook: Feeds<FaceBookStatus>,
                  private val instagram: Feeds<InstagramStatus>
) {
    fun get() : FeedDTO{
      return FeedDTO(
              twitter =  twitterFeed.getFeeds(),
              facebook= facebook.getFeeds(),
              instagram = instagram.getFeeds()
      )
    }
}