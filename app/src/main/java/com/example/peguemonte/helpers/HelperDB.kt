package com.example.peguemonte.helpers

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.peguemonte.entity.Client
import com.example.peguemonte.entity.Phone
import java.lang.Exception

class HelperDB(
    context:Context,
) : SQLiteOpenHelper(context, NAME, null, CURRENT_VERSION) {

    companion object{
        val NAME:String = "peguemonte.db"
        val CURRENT_VERSION = 2
    }

    // Create Table client
    private val TABLE_NAME_CLIENTS = "clients"
    private val COLUMN_ID = "id"
    private val COLUMN_NAME = "name"
    private val COLUMN_CPF = "cpf"
    private val COLUMN_PHONE = "phone"

    private val CREATE_TABLE = "CREATE TABLE $TABLE_NAME_CLIENTS (" +
            "$COLUMN_ID INTEGER NOT NULL," +
            "$COLUMN_NAME TEXT NOT NULL," +
            "$COLUMN_CPF TEXT NOT NULL," +
            "$COLUMN_PHONE TEXT NOT NULL," +
            ""+
            "PRIMARY KEY ($COLUMN_ID AUTOINCREMENT))"

    // Create Table Phones
    private val TABLE_NAME_PHONE = "phones"
    private val COLUMN_PHONE_NUMBER = "number"
    private val COLUMN_ID_CLIENT = "id_client"

    private val CREATE_PHONE_TABLE = "CREATE TABLE $TABLE_NAME_PHONE (" +
            "$COLUMN_ID INTEGER NOT NULL," +
            "$COLUMN_ID_CLIENT INTEGER NOT NULL," +
            "$COLUMN_PHONE_NUMBER TEXT NOT NULL," +
            "" +
            "PRIMARY KEY ($COLUMN_ID AUTOINCREMENT))"

    //Create Table Address
    private val TABLE_NAME_ADDRESS = "addresses"
    private val COLUMN_STREET = "street"
    private val COLUMN_NEIGHBORHOOD = "neighborhood"
    private val COLUMN_COMPLEMENT = "complement"
    private val COLUMN_ADDRESS_NUMBER = "number"

    private val CREATE_TABLE_ADDRESS = "CREATE TABLE $TABLE_NAME_ADDRESS (" +
            "$COLUMN_ID INTEGER NOT NULL," +
            "$COLUMN_ID_CLIENT INTEGER NOT NULL," +
            "$COLUMN_STREET TEXT NOT NULL," +
            "$COLUMN_NEIGHBORHOOD TEXT NOT NULL," +
            "$COLUMN_COMPLEMENT TEXT NULL," +
            "$COLUMN_ADDRESS_NUMBER INTEGER NULL," +
            "" +
            "PRIMARY KEY ($COLUMN_ID AUTOINCREMENT))"

    //Creates db Tables
    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.execSQL(CREATE_TABLE)
            db.execSQL(CREATE_PHONE_TABLE)
            db.execSQL(CREATE_TABLE_ADDRESS)
        }
    }

    //Updates the dataBank the versions are different
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        if (oldVersion != newVersion){
            if (db != null) {
                db.execSQL(dropTable(TABLE_NAME_CLIENTS))
                db.execSQL(dropTable(TABLE_NAME_PHONE))
                db.execSQL(dropTable(TABLE_NAME_ADDRESS))
                onCreate(db)
            }
        }
    }

    // Creates an SQL sentence to drop a table named tableName
    private fun dropTable(tableName:String):String {
        return "DROP TABLE IF EXISTS $tableName"
    }

    fun getClientList():List<Client>{
        val clientList:MutableList<Client> = mutableListOf()
        val db:SQLiteDatabase = readableDatabase
        val query = "SELECT *FROM $TABLE_NAME_CLIENTS ORDER BY $COLUMN_NAME"
        val cursor:Cursor = db.rawQuery(query, null)

        if (fillClientList(cursor, clientList)){
            db.close()
            return clientList
        }
        db.close()
        return clientList
    }

    //Search client by cpf
    fun searchClientByCPF(cpf:String):List<Client>{
        val db:SQLiteDatabase = readableDatabase
        val clientList:MutableList<Client> = mutableListOf()
        val query = "SELECT *FROM $TABLE_NAME_CLIENTS WHERE $COLUMN_CPF = '$cpf'"
        val cursor:Cursor = db.rawQuery(query, null)

        if (fillClientList(cursor, clientList)) {
            db.close()
            return clientList
        }
        db.close()
        return clientList
    }

    //Search client by name
    fun searchClientByName(name:String, isSearchById: Boolean = false, id: Int = 0):List<Client>{
        val db:SQLiteDatabase = readableDatabase
        val clientList:MutableList<Client> = mutableListOf()
        var query = "SELECT *FROM $TABLE_NAME_CLIENTS WHERE $COLUMN_NAME LIKE '%$name%'"

        if (isSearchById){
            query = "SELECT *FROM $TABLE_NAME_CLIENTS WHERE ${COLUMN_ID} = $id"
        }

        val cursor:Cursor = db.rawQuery(query, arrayOf())

        if (fillClientList(cursor, clientList)) {
            // Complete list
                db.close()
            return clientList
        }

        //List with size = 0
        db.close()
        return clientList
    }

    // Saves a Client inside db
    fun saveClient(client:Client){
        val db:SQLiteDatabase = writableDatabase
        val sql = "INSERT INTO $TABLE_NAME_CLIENTS ($COLUMN_NAME,$COLUMN_CPF,$COLUMN_PHONE) VALUES(?,?,?)"
        val arrayArgs = arrayOf(client.name, client.getCpf(), client.getPhone()?.number)
        try {
            db.execSQL(sql, arrayArgs)
            db.close()
        }catch (ex: Exception){
            throw ex
        }
    }

    fun updateClient(id:Int, client:Client){
        val db = writableDatabase
        val sql = "UPDATE ${TABLE_NAME_CLIENTS}\n" +
                "SET ${COLUMN_NAME} = '${client.name}',\n" +
                "${COLUMN_CPF} = '${client.getCpf()}',\n" +
                "${COLUMN_PHONE} = '${client.getPhone()?.number}'\n" +
                "WHERE ${COLUMN_ID} = ${id};"

        try {
            db.execSQL(sql)
            db.close()
        }catch (ex:Exception){
            throw Exception("Impossible to Update Client")
        }
    }

    //Delete a client by id
    fun deleteClient(id:Int){
        val db = writableDatabase ?: return
        val sql = "DELETE FROM $TABLE_NAME_CLIENTS WHERE $COLUMN_ID = $id"
        try {
            db.execSQL(sql)
            db.close()
        }catch (ex:Exception){
            throw Exception("Error Deleting Client")
        }
    }


    // Fills the list of clients based on a cursor and returns true or false
    private fun fillClientList(cursor: Cursor, clientList: MutableList<Client>) : Boolean {

        if (cursor == null){
            return false
        }
        // Reads all list Returned from dataBase
        while (cursor.moveToNext()) {

            //Gets a client from SQLite database
            val columnIndexName = cursor.getColumnIndex(COLUMN_NAME)
            val columnIndexCpf = cursor.getColumnIndex(COLUMN_CPF)
            val columnIndexPhone = cursor.getColumnIndex(COLUMN_PHONE)
            val columnIndexId = cursor.getColumnIndex(COLUMN_ID)
            val client = Client(cursor.getString(columnIndexName))
            client.setId(cursor.getInt(columnIndexId))
            client.setCpf(cursor.getString(columnIndexCpf))
            client.setPhone(Phone(cursor.getString(columnIndexPhone)))

            clientList.add(client)
        }
        return true
    }


}