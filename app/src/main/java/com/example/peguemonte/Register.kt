package com.example.peguemonte

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.peguemonte.application.PegueMonteApplication
import com.example.peguemonte.entity.Client
import com.example.peguemonte.entity.Phone
import com.example.peguemonte.helpers.HelperDB
import com.example.peguemonte.utils.Validation
import java.lang.Exception

class Register : AppCompatActivity() {

    private lateinit var helperDB:HelperDB
    private lateinit var nameField:EditText
    private lateinit var cpfField:EditText
    private lateinit var phoneField:EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_layout_activity)

        val buttonSave:Button = findViewById(R.id.button_save)
        val buttonCancel:Button = findViewById(R.id.button_cancel)
        val validate = Validation()

        nameField = findViewById(R.id.editTextTextPersonName)
        cpfField = findViewById(R.id.editTextCPF)
        phoneField = findViewById(R.id.editTextPhone)

        helperDB = PegueMonteApplication.instance.helperDB!!

        //Calls Finish
        buttonCancel.setOnClickListener{
            finish()
        }

        //Saves a client on dataBase
        buttonSave.setOnClickListener{
            val name = nameField.text.toString()
            val cpf = cpfField.text.toString()
            val phone = phoneField.text.toString()

            if (validate.checkNull(name) && validate.checkNull(cpf) && validate.checkNull(phone)){
                val client = Client(name, cpf, Phone(phone))
                saveClient(client)
            }
        }
    }

    //Saves a client on database
    private fun saveClient(client:Client){
        try {
            helperDB.saveClient(client)
            Toast.makeText(this, "Contact saved", Toast.LENGTH_LONG).show()
            finish()
        }catch (ex:Exception){
            Toast.makeText(this, "Contact not saved", Toast.LENGTH_LONG).show()
        }
    }
}