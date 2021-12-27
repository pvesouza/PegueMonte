package com.example.peguemonte

import android.os.Bundle
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

    private var index:Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_layout_activity)

        val buttonSave:Button = findViewById(R.id.button_save)
        val buttonCancel:Button = findViewById(R.id.button_cancel)
        val buttonDelete:Button = findViewById(R.id.button_delete)
        val validate = Validation()

        nameField = findViewById(R.id.editTextTextPersonName)
        cpfField = findViewById(R.id.editTextCPF)
        phoneField = findViewById(R.id.editTextPhone)

        helperDB = PegueMonteApplication.instance.helperDB!!

        setupClient()

        // Sets the button visibility according to Update or Create client
        if (this.index > -1){
            buttonDelete.visibility = Button.VISIBLE
            buttonCancel.visibility = Button.GONE
        }else{
            buttonDelete.visibility = Button.GONE
            buttonCancel.visibility = Button.VISIBLE
        }

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

        //Delete a Client
        buttonDelete.setOnClickListener{
            if (this.index > -1){
                try {
                    this.helperDB.deleteClient(this.index)
                    finish()
                }catch (ex:Exception){
                    Toast.makeText(this, "Can not  delete client", Toast.LENGTH_LONG).show()
                }

            }
        }
    }

    //Saves a client on database
    private fun saveClient(client:Client){
        try {
            if (this.index == -1){
                helperDB.saveClient(client)
                Toast.makeText(this, "Client saved", Toast.LENGTH_LONG).show()
            }else{
                // Update contact
                helperDB.updateClient(this.index, client)
                Toast.makeText(this, "Client saved", Toast.LENGTH_LONG).show()
            }
            finish()
        }catch (ex:Exception){
            Toast.makeText(this, "Contact not saved", Toast.LENGTH_LONG).show()
        }
    }

    //Setup Client
    private fun setupClient(){
        val bundle:Bundle? = intent.extras
        this.index = bundle?.getInt(MainActivity.ID) ?:return

        val clientList = helperDB.searchClientByName("", true, this.index)
        val client = clientList[0]

        nameField.setText(client.name)
        cpfField.setText(client.getCpf())
        phoneField.setText(client.getPhone().toString())

    }
}
