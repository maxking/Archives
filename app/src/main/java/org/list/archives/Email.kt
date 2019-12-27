package org.list.archives

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Email (
    val url: String,
    val mailinglist: String,
    val message_id: String,
    val message_id_hash: String,
    val thread: String,
    val sender: Sender,
    val sender_name: String,
    val subject: String,
    val date: String,
    val parent: String? = "",
    val children: List<String>,
    val votes: Vote? = Vote(0, 0, ""),
    val content: String? = "",
    val attachments: List<Attachment>?
)


@JsonClass(generateAdapter = true)
data class Sender (
    val address: String,
    val mailman_id: String,
    val emails: String
)


@JsonClass(generateAdapter = true)
data class Vote (
    val likes: Int,
    val dislikes: Int,
    val status: String
)


@JsonClass(generateAdapter = true)
data class Attachment(
    val email: String,
    val counter: Int,
    val name: String,
    val content_type: String,
    val encoding: String? = "",
    val size: Int,
    val download: String
)