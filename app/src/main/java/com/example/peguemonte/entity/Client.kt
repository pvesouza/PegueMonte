package com.example.peguemonte.entity

class Client(val name:String) {

    private var id:Int = 0;
    private var phone:Phone? = null
    private var address:Address? = null
    private var cpf:String = ""

    constructor(name:String, cpf: String, phone: Phone) : this(name) {
        this.setCpf(cpf)
        this.setPhone(phone)
    }

    fun setId(id:Int){
        this.id = id
    }

    fun getId():Int {
        return this.id
    }

    fun setAddress(address:Address){
        this.address = address
    }

    fun getAddress(): Address? {
        return this.address
    }

    fun setPhone(phone:Phone){
        this.phone = phone
    }

    fun getPhone(): Phone? {
        return this.phone
    }

    fun getCpf():String{
        return this.cpf
    }

    fun setCpf(cpf:String){
        this.cpf = cpf
    }
}