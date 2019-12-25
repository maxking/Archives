package org.list.archives

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView

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
            view = this.layoutInflater.inflate(R.layout.threads_list, parent, false)
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