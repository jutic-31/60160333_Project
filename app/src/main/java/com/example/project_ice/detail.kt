package com.example.project_ice

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide


class detail : Fragment() {

    var image:String ?= null
    var title:String ?= null
    var cal:String ?= null
    var ingredientLines:String ?= null
    var step:String ?= null

    fun newInstance(image:String,title:String,cal:String,ingredientLines:String,step:String): detail {
        val fragment = detail()
        val bundle = Bundle()
        bundle.putString("image",image);
        bundle.putString("title",title);
        bundle.putString("cal",cal);
        bundle.putString("ingredientLines",ingredientLines);
        bundle.putString("step",step);
        fragment.setArguments(bundle)

        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if(bundle != null){
            this.image = bundle.getString("image").toString()
            this.title = bundle.getString("title").toString()
            this.cal = bundle.getString("cal").toString()
            this.ingredientLines = bundle.getString("ingredientLines").toString()
            this.step = bundle.getString("step").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        val image_view : ImageView = view.findViewById(R.id.image_view)
        val title_view : TextView = view.findViewById(R.id.title_view)
        val cal_view : TextView = view.findViewById(R.id.cal_view)
        val ingredientLines_view : TextView = view.findViewById(R.id.ingredientLines_view)
        val step_view : TextView = view.findViewById(R.id.step_view)
        val button : Button = view.findViewById(R.id.button_open_dialog)

        button.setOnClickListener {

                val builder: AlertDialog.Builder =  AlertDialog.Builder(this.context)
                builder.setMessage("สนใจ "+ this.title)

                builder.setNegativeButton("ใช่",
                    DialogInterface.OnClickListener{ dialog, id ->
                        Toast.makeText(this.context,"ขอบคุณค่ะ", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    })

                builder.setPositiveButton("ไม่ใช่",
                    DialogInterface.OnClickListener{ dialog, id ->
                        Toast.makeText(this.context,"ขอบคุณค่ะ", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    })
                builder.show();


        }
        title_view.text = this.title
        cal_view.text = this.cal
        ingredientLines_view.text = this.ingredientLines
        step_view.text = this.step
        Glide.with(activity!!.baseContext).load(image).into(image_view)
        return view
    }


}
