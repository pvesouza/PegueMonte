package com.example.peguemonte

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.peguemonte.application.PegueMonteApplication
import com.example.peguemonte.entity.Client
import com.example.peguemonte.helpers.HelperDB
import com.example.peguemonte.utils.MyAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    lateinit var nameSearch:EditText
    lateinit var clientList:List<Client>
    lateinit var recyclerView:RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nameSearch = findViewById(R.id.editTextClientName)
        val buttonSearch:ImageButton = findViewById(R.id.imageButton_search)
        recyclerView = findViewById(R.id.clientlist_recyclerview)
        val addButton:FloatingActionButton = findViewById(R.id.floatingActionButton_add)
        val helperDB: HelperDB? = PegueMonteApplication.instance.helperDB
        val recyclerLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = recyclerLayoutManager

        //Fill the recycler view
        try {
            clientList = helperDB?.getClientList() ?: mutableListOf()
            val adapter = MyAdapter(clientList)
            recyclerView.adapter = adapter
        }catch (ex:Exception){
            ex.printStackTrace()
        }



        // Button search
        buttonSearch.setOnClickListener {
            onClickSearch()
        }

        // Button Add
        addButton.setOnClickListener{
            onClickAdd()
        }
    }

    //Button Listener
    private fun onClickSearch() {
        var str = nameSearch.text.toString()
        if (str.length == 0){
            str = "Fill search correctly"
            Toast.makeText(applicationContext, str, Toast.LENGTH_LONG).show()
            return
        }
        str = "Searching for $str"
        var list:List<Client> = mutableListOf()
        Toast.makeText(applicationContext, str, Toast.LENGTH_LONG).show()
        str = nameSearch.text.toString()
        val helperDB: HelperDB? = PegueMonteApplication.instance.helperDB
        try{
            if (helperDB != null) {
                list = helperDB.searchClientByName(str)
            }
            val adapter = MyAdapter(list)
            recyclerView.adapter = adapter
        }catch (ex: Exception){
            ex.printStackTrace()
        }
    }

    // Add Button
    private fun onClickAdd(){
        val helperDB = PegueMonteApplication.instance.helperDB
        val client = Client("Pedro")
        client.setCpf("01395783403")
        if (helperDB != null) {
            helperDB.saveClient(client)
        }
    }

}
