package org.list.archives

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_email_detail.*
import kotlinx.android.synthetic.main.email_detail.view.*
import okhttp3.*
import org.list.archives.dummy.content
import java.io.IOException

/**
 * A fragment representing a single Email detail screen.
 * This fragment is either contained in a [EmailListActivity]
 * in two-pane mode (on tablets) or a [EmailDetailActivity]
 * on handsets.
 */
class EmailDetailFragment : Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
    private var item: Email? = null
    private val client = OkHttpClient()

    val TAG = "EmailDetailFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                item = content.ITEM_MAP[it.getString(ARG_ITEM_ID)]

                Log.i(TAG, "Got the item from content: ${item} and item_id is ${it.getString(
                    ARG_ITEM_ID)}")
                run(item?.url!!)
                activity?.toolbar_layout?.title = item?.sender_name + "\n" + item?.date
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.email_detail, container, false)

        // Show the dummy content as text in a TextView.
        item?.let {
            rootView.email_detail.text = it.content
        }

        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }

    fun updateContent(content: String?) {
        activity?.runOnUiThread{
            activity?.email_detail_container?.email_detail?.text = content
        }
    }

    private fun run(url: String) {

        val TAG = "EmailDetailFragment"

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
                val adapter: JsonAdapter<Email> = moshi.adapter(Email::class.java)
                item = adapter.fromJson(strResponse)

                updateContent(item?.content)
            }
        })
    }
}
