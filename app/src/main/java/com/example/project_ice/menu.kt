package com.example.project_ice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentTransaction


class menu : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        val button : Button = view.findViewById(R.id.button_food)
        val button2 : Button =view.findViewById(R.id.button_chili)
        val button3 : Button =view.findViewById(R.id.button_drink)

        button.setOnClickListener {
            val food = food()
            val fm =fragmentManager
            val transaction : FragmentTransaction = fm!!.beginTransaction()
            transaction.replace(R.id.contentContainer, food,"food")
            transaction.addToBackStack("food")
            transaction.commit()
        }

        button2.setOnClickListener {
            val sauce = sauce()
            val fm =fragmentManager
            val transaction : FragmentTransaction = fm!!.beginTransaction()
            transaction.replace(R.id.contentContainer,sauce,"sauce")
            transaction.addToBackStack("sauce")
            transaction.commit()
        }

        button3.setOnClickListener {
            val drink = drink()
            val fm =fragmentManager
            val transaction : FragmentTransaction = fm!!.beginTransaction()
            transaction.replace(R.id.contentContainer,drink,"drink")
            transaction.addToBackStack("drink")
            transaction.commit()
        }
        return view
    }


}
