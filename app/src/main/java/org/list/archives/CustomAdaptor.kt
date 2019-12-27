package org.list.archives

import android.content.Context
import android.provider.Telephony
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout

class CustomAdapter(context: Context, arrayListDetails:ArrayList<MailingList>) : BaseAdapter(){

    private val layoutInflater: LayoutInflater
    private val arrayListDetails:ArrayList<MailingList>

    init {
        this.layoutInflater = LayoutInflater.from(context)
        this.arrayListDetails=arrayListDetails
    }

    override fun getCount(): Int {
        return arrayListDetails.size
    }

    override fun getItem(position: Int): Any {
        return arrayListDetails.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val view: View?
        val listRowHolder: ListRowHolder
        if (convertView == null) {
            view = this.layoutInflater.inflate(R.layout.mailinglist_list, parent, false)
            listRowHolder = ListRowHolder(view)
            view.tag = listRowHolder
        } else {
            view = convertView
            listRowHolder = view.tag as ListRowHolder
        }

        listRowHolder.listname.text = arrayListDetails.get(position).display_name
        listRowHolder.address.text = arrayListDetails.get(position).name
        listRowHolder.description.text = arrayListDetails.get(position).description
        return view
    }
}

class ThreadsListAdapter(context: Context, arrayListDetails: ArrayList<Thread>): BaseAdapter() {
    private val layoutInflater: LayoutInflater
    private val arrayListDetails:ArrayList<Thread>

    init {
        this.layoutInflater = LayoutInflater.from(context)
        this.arrayListDetails=arrayListDetails
    }

    override fun getCount(): Int {
        return arrayListDetails.size
    }

    override fun getItem(position: Int): Any {
        return arrayListDetails.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val view: View?
        val listRowHolder: ThreadRowHolder
        if (convertView == null) {
            view = this.layoutInflater.inflate(R.layout.thread_list, parent, false)
            listRowHolder = ThreadRowHolder(view)
            view.tag = listRowHolder
        } else {
            view = convertView
            listRowHolder = view.tag as ThreadRowHolder
        }

        listRowHolder.subject.text = arrayListDetails.get(position).subject
        listRowHolder.replies.text = arrayListDetails.get(position).replies_count.toString()
        return view
    }

}


private class ListRowHolder(row: View?) {
    public val listname: TextView
    public val address: TextView
    public val description: TextView

    public val linearLayout: LinearLayout

    init {
        this.listname = row?.findViewById<TextView>(R.id.name) as TextView
        this.address = row?.findViewById<TextView>(R.id.address) as TextView
        this.description = row?.findViewById<TextView>(R.id.description) as TextView
        this.linearLayout = row?.findViewById<LinearLayout>(R.id.linearLayout) as LinearLayout
    }
}


private class ThreadRowHolder(row: View?) {
    public val subject: TextView
    public val replies: TextView

    public val linearLayout: LinearLayout

    init {
        this.subject = row?.findViewById<TextView>(R.id.subject) as TextView
        this.replies = row?.findViewById<TextView>(R.id.replies) as TextView
        this.linearLayout = row?.findViewById<LinearLayout>(R.id.linearLayout) as LinearLayout
    }
}  