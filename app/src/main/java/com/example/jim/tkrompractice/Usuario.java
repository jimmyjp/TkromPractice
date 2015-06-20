package com.example.jim.tkrompractice;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jim on 26/05/15.
 */
public class Usuario implements Parcelable {

    String code;
    String name;
    String age;
    String city;

    public Usuario(String code, String name, String age, String city){

        this.code = code;
        this.name = name;
        this.age = age;
        this.city = city;
    }

    public Usuario(Parcel in) {
        readFromParcel(in);
    }

    public String getCode(){

        return this.code;
    }

    public String getName(){

        return this.name;
    }

    public String getAge(){

        return this.age;
    }

    public String getCity(){

        return this.city;
    }

    public void setCode(String code){

        this.code = code;
    }

    public void setName(String name){

        this.name = name;
    }

    public void setAge(String age){

        this.age = age;
    }

    public void setCity(String city){

        this.city = city;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(code);
        dest.writeString(name);
        dest.writeString(age);
        dest.writeString(city);

    }

    private void readFromParcel(Parcel in) {

        code = in.readString();
        name = in.readString();
        age = in.readString();
        city = in.readString();

    }

    public static final Creator<Usuario> CREATOR
            = new Creator<Usuario>() {
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

}
