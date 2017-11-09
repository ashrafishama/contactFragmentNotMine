package com.example.ashrafi.fragment;

/**
 * Created by HP on 10/23/2017.
 */

public class CallLog {
    private String number,type,date,duration,nameid;

    public CallLog(){

    }

    public CallLog(String number, String type, String date, String duration, String nameid){
        this.number = number;
        this.type = type;
        this.date = date;
        this.duration = duration;
        this.nameid = nameid;
    }

    //setters
    public void setNumber(String number){
        this.number = number;
    }

    public void setType(String type){
        this.type = type;
    }

    public void setData(String data){
        this.date = date;
    }

    public void setDuration(String duration){
        this.duration = duration;
    }

    public void setNameid(String nameid) {
        this.nameid = nameid;
    }

    //getters
    public String getNumber(){
        return this.number;
    }

    public String getType(){
        return this.type;
    }

    public String getDuration(){
        return this.duration;
    }

    public String getDate(){ return this.date; }

    public String getNameid(){
        return this.nameid;
    }


}
