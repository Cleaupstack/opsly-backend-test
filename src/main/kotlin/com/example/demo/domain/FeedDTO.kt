package com.example.demo.domain

data class FeedDTO(
        val twitter: List<Tweet>,
        val facebook: List<FaceBookStatus>,
        val instagram: List<InstagramStatus>
)