package org.list.archives

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.android.synthetic.main.thread_scrolling.*
import okhttp3.*
import java.io.IOException

class ScrollingActivity : AppCompatActivity() {

    lateinit var listView_details: ListView
    var arrayList_details:ArrayList<Thread> = ArrayList();
    private val client = OkHttpClient()
    val TAG = "ScrollingActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.thread_scrolling)

        var threadsURL: String = intent.getStringExtra("threadsURL")
        val listName = intent.getStringExtra("listName")
        val displayName = intent.getStringExtra("displayName")

        toolbar.title = displayName + "<" + listName + ">"

        listView_details = findViewById<ListView>(R.id.listView) as ListView
        Log.i(TAG, "Trying to downloads threads at $threadsURL")
        run(threadsURL)
    }

    private fun run(url: String) {

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.i(TAG, "Failed to get threads for mailing list. $e")
            }

            override fun onResponse(call: Call, response: Response) {
                var strResponse = response.body()!!.string()

                val moshi: Moshi = Moshi.Builder().build();
                val adapter: JsonAdapter<List<Thread>> = moshi.adapter(Types.newParameterizedType(List::class.java, Thread::class.java))
                val mls = adapter.fromJson(strResponse)


                arrayList_details = ArrayList()

                for (i in 0..mls!!.lastIndex) {
                    arrayList_details.add(mls[i])
                }

                runOnUiThread {
                    //stuff that updates ui
                    val objAdapter = ThreadsListAdapter(applicationContext, arrayList_details)
                    listView_details.adapter = objAdapter
                    listView_details.onItemClickListener =
                        object : AdapterView.OnItemClickListener {
                            override fun onItemClick(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                val itemValue =
                                    listView_details.getItemAtPosition(position) as Thread

                                Log.i(TAG, "Clicked $position, opening a new actvity.")

                                val intent = Intent(this@ScrollingActivity, EmailListActivity::class.java)
                                intent.putExtra("emails", itemValue.emails)
                                intent.putExtra("subject", itemValue.subject)
                                Log.i(TAG, "Opening a new thread $itemValue.emails")
                                startActivity(intent)
                            }
                        }
                }
            }
        })
    }
}
