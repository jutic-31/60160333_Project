package com.example.project_ice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.json.JSONArray

class Adapter (context: Context, fragment:FragmentManager,val dataSource: JSONArray) : RecyclerView.Adapter<Adapter.Holder>(){
    private val thiscontext : Context = context
    private val fm :FragmentManager = fragment
    private var title:String ?= null
    private var cal:String ?= null
    private var ingredientLines:String ?= null
    private var image:String ?= null
    private var step:String ?= null

    class Holder(view : View) : RecyclerView.ViewHolder(view) {
        private val View = view;

        lateinit var layout : LinearLayout
        lateinit var titleTextView: TextView
        lateinit var calTextView: TextView
        lateinit var image: ImageView

        fun Holder(){
            layout = View.findViewById<View>(R.id.recyview_layout) as LinearLayout
            titleTextView = View.findViewById<View>(R.id.tv_name) as TextView
            calTextView = View.findViewById<View>(R.id.tv_cal) as TextView
            image = View.findViewById<View>(R.id.imgV) as ImageView
        }
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.recy_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return dataSource.length()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.Holder()
        holder.titleTextView.setText( dataSource.getJSONObject(position).getString("title").toString() )
        holder.calTextView.setText( dataSource.getJSONObject(position).getString("cal").toString() )

        Glide.with(thiscontext)
            .load(dataSource.getJSONObject(position).getString("image").toString())
            .into(holder.image)

        holder.layout.setOnClickListener{
            Toast.makeText(thiscontext,holder.titleTextView.text.toString(), Toast.LENGTH_SHORT).show()
            this.title = dataSource.getJSONObject(position).getString("title").toString()
            this.cal = dataSource.getJSONObject(position).getString("cal").toString()
            this.ingredientLines = dataSource.getJSONObject(position).getString("ingredientLines").toString()
            this.step = dataSource.getJSONObject(position).getString("step").toString()
            this.image = dataSource.getJSONObject(position).getString("image").toString()

            val detail = detail().newInstance(image!!, title!!, cal!!, ingredientLines!!, step!!)
            val transaction : FragmentTransaction = fm!!.beginTransaction()
            transaction.replace(com.example.project_ice.R.id.contentContainer,detail,"detail")
            transaction.addToBackStack("detail")
            transaction.commit()
        }

    }
}