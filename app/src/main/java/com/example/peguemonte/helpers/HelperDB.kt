package com.example.peguemonte.helpers

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.peguemonte.entity.Client

class HelperDB(
    context:Context,
) : SQLiteOpenHelper(context, NAME, null, CURRENT_VERSION) {

    companion object{
        val NAME:String = "peguemonte.db"
        val CURRENT_VERSION = 1
    }

    // Create Table client
    private val TABLE_NAME_CLIENTS = "clients"
    private val COLUMN_ID = "id"
    private val COLUMN_NAME = "name"
    private val COLUMN_CPF = "cpf"

    private val CREATE_TABLE = "CREATE TABLE ${TABLE_NAME_CLIENTS} (" +
            "${COLUMN_ID} INTEGRER NOT NULL," +
            "${COLUMN_NAME} TEXT NOT NULL," +
            "${COLUMN_CPF} TEXT NOT NULL" +
            "" +
            "PRIMARY KEY (${COLUMN_ID} AUTOINCREMENT))"

    // Create Table Phones
    private val TABLE_NAME_PHONE = "phones"
    private val COLUMN_PHONE_NUMBER = "number"
    private val COLUMN_ID_CLIENT = "id_client"

    private val CREATE_PHONE_TABLE = "CREATE TABLE ${TABLE_NAME_PHONE} (" +
            "${COLUMN_ID} INTEGER NOT NULL" +
            "${COLUMN_ID_CLIENT} INTEGER NOT NULL" +
            "${COLUMN_PHONE_NUMBER} TEXT NOT NULL" +
            "" +
            "PRIMARY KEY (${COLUMN_ID} AUTOINCREMENT))"

    //Create Table Address
    private val TABLE_NAME_ADDRESS = "addresses"
    private val COLUMN_STREET = "street"
    private val COLUMN_NEIGHBORHOOD = "neighborhood"
    private val COLUMN_COMPLEMENT = "complement"
    private val COLUMN_ADDRESS_NUMBER = "number"

    private val CREATE_TABLE_ADDRESS = "CREATE TABLE ${TABLE_NAME_ADDRESS} (" +
            "${COLUMN_ID} INTEGRER NOT NULL," +
            "${COLUMN_ID_CLIENT} INTEGER NOT NULL" +
            "${COLUMN_STREET} TEXT NOT NULL," +
            "${COLUMN_NEIGHBORHOOD} TEXT NOT NULL" +
            "${COLUMN_COMPLEMENT} TEXT" +
            "${COLUMN_ADDRESS_NUMBER} INTEGER" +
            "" +
            "PRIMARY KEY (${COLUMN_ID} AUTOINCREMENT))"

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
        return "DROP TABLE IF EXISTS ${tableName}"
    }

    //Search client by cpf
    fun searchClientByCPF(cpf:String):List<Client>{
        val db:SQLiteDatabase = readableDatabase
        val clientList:MutableList<Client> = mutableListOf<Client>()
        val query:String = "SELECT * FROM ${TABLE_NAME_CLIENTS}" +
                "WHERE ${COLUMN_CPF} = ${cpf}"
        val cursor:Cursor = db.rawQuery(query, null)

        if (fillClientList(cursor, clientList)) {
            return clientList
        }

        return clientList
    }

    //Search client by name
    fun searchClientByName(name:String):List<Client>{
        val db:SQLiteDatabase = readableDatabase
        val clientList:MutableList<Client> = mutableListOf<Client>()
        val query:String = "SELECT * FROM ${TABLE_NAME_CLIENTS}" +
                "WHERE ${COLUMN_NAME} = ${name}"
        val cursor:Cursor = db.rawQuery(query, null)

        if (fillClientList(cursor, clientList)) {
            return clientList
        }

        return clientList
    }


    // Fills the list of clients based on a cursor and returns true or false
    private fun fillClientList(cursor: Cursor, clientList: MutableList<Client>) : Boolean {

        // Reads all list Returned from dataBase
        while (cursor.moveToNext()) {

            //Gets a client from SQLite database
            val columnIndexName = cursor.getColumnIndex(COLUMN_NAME)
            val columnIndexCpf = cursor.getColumnIndex(COLUMN_NAME)
            val columnIndexId = cursor.getColumnIndex(COLUMN_NAME)
            val client = Client(cursor.getString(columnIndexName))
            client.setId(cursor.getInt(columnIndexId))
            client.setCpf(cursor.getString(columnIndexCpf))

            clientList.add(client)
        }
        return false
    }

}