package org.list.archives

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.NavUtils
import android.view.MenuItem
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

import org.list.archives.dummy.DummyContent
import kotlinx.android.synthetic.main.activity_email_list.*
import kotlinx.android.synthetic.main.email_list_content.view.*
import kotlinx.android.synthetic.main.email_list.*
import okhttp3.*
import org.list.archives.dummy.content
import java.io.IOException

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [EmailDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class EmailListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    var arrayList_details:ArrayList<Thread> = ArrayList();
    private val client = OkHttpClient()

    val TAG = "EmailListActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_list)

        setSupportActionBar(toolbar)
        toolbar.title = intent.getStringExtra("subject")

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (email_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        val emailsURL = intent.getStringExtra("emails")

        run(emailsURL)

        Log.i(TAG, """Fetched total emails: ${content.getSize()}""")

    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                NavUtils.navigateUpFromSameTask(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun setupRecyclerView(recyclerView: RecyclerView, dum: DummyContent) {
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(this, dum.ITEMS, twoPane)
    }

    class SimpleItemRecyclerViewAdapter(
        private val parentActivity: EmailListActivity,
        private val values: List<Email>,
        private val twoPane: Boolean
    ) :
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as Email
                if (twoPane) {
                    val fragment = EmailDetailFragment().apply {
                        arguments = Bundle().apply {
                            putString(EmailDetailFragment.ARG_ITEM_ID, item.message_id_hash)
                        }
                    }
                    parentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.email_detail_container, fragment)
                        .commit()
                } else {
                    val intent = Intent(v.context, EmailDetailActivity::class.java).apply {
                        putExtra(EmailDetailFragment.ARG_ITEM_ID, item.message_id_hash)
                    }
                    v.context.startActivity(intent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.email_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            holder.senderView.text = item.date
            holder.dateView.text = item.sender_name

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val senderView: TextView = view.sender
            val dateView: TextView = view.date
        }
    }

    private fun run(url: String) {

        val TAG = "EmailListActivity"

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.i(TAG, "Failed to get emails for thread $e")
            }

            override fun onResponse(call: Call, response: Response) {
                var strResponse = response.body()!!.string()

                val moshi: Moshi = Moshi.Builder().build();
                val adapter: JsonAdapter<List<Email>> = moshi.adapter(Types.newParameterizedType(List::class.java, Email::class.java))
                val mls = adapter.fromJson(strResponse)

                for (i in 0..mls!!.lastIndex) {
                    Log.i(TAG, "Adding Email to list: " + mls[i].message_id + " from " + mls[i].sender_name)
                    content.ITEMS.add(mls[i])
                    content.ITEM_MAP.put(mls[i].message_id_hash, mls[i])
                }

                runOnUiThread{
                    setupRecyclerView(email_list, content)
                }
            }
        })
    }
}
