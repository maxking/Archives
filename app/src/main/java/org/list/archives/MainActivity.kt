package org.list.archives

import okhttp3.*
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.widget.ListView

import java.io.IOException


import kotlinx.android.synthetic.main.activity_main.*
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types



class MainActivity : AppCompatActivity() {
    lateinit var listView_details: ListView
    var arrayList_details:ArrayList<MailingList> = ArrayList();
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        listView_details = findViewById<ListView>(R.id.listView) as ListView
        run("https://lists.mailman3.org/archives/api/lists/")

    }

    private fun run(url: String) {

        val TAG = "MyActivity"

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.i(TAG, "Failed to get mailing list. $e")
            }

            override fun onResponse(call: Call, response: Response) {
                var strResponse = response.body()!!.string()

                val moshi: Moshi = Moshi.Builder().build();
                val adapter: JsonAdapter<List<MailingList>> = moshi.adapter(Types.newParameterizedType(List::class.java, MailingList::class.java))
                val mls = adapter.fromJson(strResponse)


                arrayList_details = ArrayList()

                for (i in 0..mls!!.lastIndex) {
                    arrayList_details.add(mls[i])
                }

                runOnUiThread {
                    //stuff that updates ui
                    val objAdapter : CustomAdapter
                    objAdapter = CustomAdapter(applicationContext, arrayList_details)
                    listView_details.adapter=objAdapter
                }
            }
        })
    }
}
