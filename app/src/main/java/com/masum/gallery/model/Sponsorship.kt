package com.masum.gallery.model

data class Sponsorship(
    var impression_urls: List<String>?,
    var sponsor: Sponsor?,
    var tagline: String?,
    var tagline_url: String?
)