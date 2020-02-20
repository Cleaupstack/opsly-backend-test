package com.example.demo.integration

interface Feeds<T> {
    fun getFeeds(): List<T>
}