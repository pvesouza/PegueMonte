package com.example.peguemonte.entity

class Address() {

    private var street:String = ""
    private var number:String = ""
    private var neighborhood:String = ""
    private var complement:String = ""

    private var id:Int = 0;
    private var idClient:Int = 0;

    fun setId(id:Int){
        this.id = id
    }

    fun getId():Int {
        return this.id
    }

    fun setIdClient(idClient:Int){
        this.idClient = idClient
    }

    fun getIdClient():Int {
        return this.idClient
    }

    fun setStreet(street:String){
        this.street = street
    }

    fun setNumber(number:String){
        this.number = number
    }

    fun setNeighborhood(neigh:String){
        this.neighborhood = neigh
    }

    fun setComplement(complement:String){
        this.complement = complement
    }

    fun getSteet():String{
        return this.street;
    }

    fun getNumber():String{
        return this.number;
    }

    fun getNeighborhood():String{
        return this.neighborhood;
    }

    fun getComplement():String{
        return this.complement;
    }

    override fun toString(): String {
        var str:String = ""
        str = "Rua ${this.street}"

        return str;
    }

}