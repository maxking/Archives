package org.list.archives

import com.squareup.moshi.JsonClass


/*

// A JSON representation of a MailingList.

{
    "url": "https://lists.mailman3.org/archives/api/list/mailman-users@mailman3.org/",
    "name": "mailman-users@mailman3.org",
    "display_name": "Mailman-users",
    "description": "General list for Mailman 3 users and others interested in Mailman 3",
    "subject_prefix": "[MM3-users] ",
    "archive_policy": "public",
    "created_at": "2016-03-27T03:16:24.629690Z",
    "threads": "https://lists.mailman3.org/archives/api/list/mailman-users@mailman3.org/threads/",
    "emails": "https://lists.mailman3.org/archives/api/list/mailman-users@mailman3.org/emails/"
}
*/

@JsonClass(generateAdapter = true)
data class MailingList (
    val url: String,
    val display_name: String,
    val name: String,
    val description: String,
    val subject_prefix: String,
    val archive_policy: String,
    val created_at: String,
    val threads: String,
    val emails: String
)