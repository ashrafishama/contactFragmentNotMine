package com.example.ashrafi.fragment;

/**
 * Created by HP on 10/17/2017.
 */

public class Contact implements Comparable<Contact>{
    private String name;
    private String id;
    private String number;

    public Contact() {
    }

    public Contact(String name,String id,String number){
        this.name = name;
        this.id = id;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String year) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }


    public int compareTo(Contact contact){
        if(this.name.compareTo(contact.name) < 0 ) return -1;
        else return 1;
    }

}
