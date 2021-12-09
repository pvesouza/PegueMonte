package com.example.peguemonte

import android.content.Intent
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
    var helperDB: HelperDB? = null
    var myAdapter: MyAdapter? = null

    companion object{
        val ID = "ID"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nameSearch = findViewById(R.id.editTextClientName)
        val buttonSearch:ImageButton = findViewById(R.id.imageButton_search)
        recyclerView = findViewById(R.id.clientlist_recyclerview)
        val addButton:FloatingActionButton = findViewById(R.id.floatingActionButton_add)
        helperDB = PegueMonteApplication.instance.helperDB
        val recyclerLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = recyclerLayoutManager


        //Fill the recycler view
        try {
            clientList = helperDB?.getClientList()!!
            myAdapter = MyAdapter(clientList, this)
            myAdapter!!.setOnItemClickListener{
                item -> startActivity(item.getId())
            }
            recyclerView.adapter = myAdapter
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
        val list:List<Client> = mutableListOf()
        myAdapter?.setList(list)

        if (str.length == 0){
            str = "Fill search correctly"
            Toast.makeText(applicationContext, str, Toast.LENGTH_LONG).show()
            try {
                if (helperDB != null) {
                    val list = helperDB!!.getClientList()
                    myAdapter?.setList(list)
                    recyclerView.adapter = myAdapter
                }
            }catch (ex: Exception){
                ex.printStackTrace()
            }
        }else{

            str = "Searching for $str"
            Toast.makeText(applicationContext, str, Toast.LENGTH_LONG).show()
            str = nameSearch.text.toString()
            try{
                if (helperDB != null) {
                    val list = helperDB!!.searchClientByName(str)
                    myAdapter?.setList(list)
                    recyclerView.adapter = myAdapter
                }
            }catch (ex: Exception){
                ex.printStackTrace()
            }
        }
    }

    override fun onResume(){
        super.onResume()
        try {
            clientList = helperDB?.getClientList() ?: mutableListOf()
            myAdapter?.setList(clientList)
            recyclerView.adapter = myAdapter
        }catch (ex:Exception){
            ex.printStackTrace()
        }
    }

    // Starts a new activity
    private fun startActivity(id:Int){
        val intent = Intent(this, Register::class.java)
        val bundle = Bundle()
        bundle.putInt(ID, id)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    // Add Button
    private fun onClickAdd(){
        val intent = Intent(this, Register::class.java)
        startActivity(intent)
    }
}
