package com.example.demo.controller

import com.example.demo.service.FeedService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FeedController(val feedService: FeedService) {

    @GetMapping("/")
    fun getFeeds() = feedService.get()
}

