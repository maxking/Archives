package org.list.archives


import com.squareup.moshi.JsonClass



/*
     {
        "url": "https://lists.mailman3.org/archives/api/list/mailman-users@mailman3.org/thread/3VDVK3DIGAC42FNWI2BFF4H73LDMNZ4V/",
        "mailinglist": "https://lists.mailman3.org/archives/api/list/mailman-users@mailman3.org/",
        "thread_id": "3VDVK3DIGAC42FNWI2BFF4H73LDMNZ4V",
        "subject": "[MM3-users] Migration from 2.1 to 3",
        "date_active": "2019-12-26T20:17:16Z",
        "starting_email": "https://lists.mailman3.org/archives/api/list/mailman-users@mailman3.org/email/3VDVK3DIGAC42FNWI2BFF4H73LDMNZ4V/",
        "emails": "https://lists.mailman3.org/archives/api/list/mailman-users@mailman3.org/thread/3VDVK3DIGAC42FNWI2BFF4H73LDMNZ4V/emails/",
        "votes_total": 0,
        "replies_count": 6,
        "next_thread": null,
        "prev_thread": "https://lists.mailman3.org/archives/api/list/mailman-users@mailman3.org/thread/6M4A2Q46JAFQQJH3EALMPGLRN3ORLPOZ/"
    }
*/
@JsonClass(generateAdapter = true)
data class Thread (
    val url: String,
    val mailinglist: String,
    val thread_id: String,
    val subject: String,
    val date_active: String,
    val starting_email: String,
    val emails: String,
    val votes_total: Int,
    val replies_count: Int,
    val next_thread: String? = "",
    val prev_thread: String? = ""
)