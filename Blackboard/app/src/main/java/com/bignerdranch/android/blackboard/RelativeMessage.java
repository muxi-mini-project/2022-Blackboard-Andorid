package com.bignerdranch.android.blackboard;

public class RelativeMessage {

    private String tag;
    private String contents;

    public RelativeMessage(){

    }

    public RelativeMessage(String tag,String contents){
        this.tag = tag;
        this.contents = contents;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
