package com.bignerdranch.android.blackboard;


public class MyResponse<E>
{
    private Integer code;
    private String message;
    private E data;

    public E getData() {
        return data;
    }
}
