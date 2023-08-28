package com.yuvarani.interview_task.firebasedatabase;

public class UserNote {

    private String Name;
    private String description;
    private String key;
    public UserNote(String Name, String description,String key) {
        this.Name = Name;
        this.description = description;
        this.key = key;
    }
    public String getName() {
        return Name;
    }
    public void setName(String Name) {
        this.Name = Name;
    }  public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getdescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

//    @SerializedName("from")
//    private String from;
//    @SerializedName("username")
//    private String username;

}
