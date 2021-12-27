package com.example.peguemonte.entity

class Phone (val number:String){

    private var id:Int = 0
    private var idClient = 0

    fun setId(id : Int){
        this.id = id
    }

    fun getId() : Int{
        return this.id
    }

    fun setIdClient(idClient : Int) {
        this.idClient = idClient
    }

    fun getIdClient() : Int{
        return this.idClient
    }

    override fun toString(): String {
        return number
    }

}