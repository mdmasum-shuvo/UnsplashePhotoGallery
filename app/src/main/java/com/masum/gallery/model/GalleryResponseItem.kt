package com.masum.gallery.model

data class GalleryResponseItem(
    var alt_description: String?,
    var blur_hash: String?,
    var color: String?,
    var created_at: String?,
    var description: String?,
    var height: Int?,
    var id: String?,
    var liked_by_user: Boolean?,
    var likes: Int?,
    var links: Links?,
    var promoted_at: String?,
    var sponsorship: Sponsorship?,
    var updated_at: String?,
    var urls: Urls?,
    var user: User?,
    var width: Int?
)