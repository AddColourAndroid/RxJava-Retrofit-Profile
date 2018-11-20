package za.co.addcolour.rxjavaretrofitprofile.model;

import com.google.gson.annotations.SerializedName;

public class Profile {

    @SerializedName("id")
    private int id;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("last_message")
    private String lastMessage;

    @SerializedName("online_status")
    private boolean onlineStatus;

    @SerializedName("timestamp")
    private long timestamp;

    public Profile() {
    }

    public Profile(int id, String fullName, String lastMessage, boolean onlineStatus, long timestamp) {
        this.id = id;
        this.fullName = fullName;
        this.lastMessage = lastMessage;
        this.onlineStatus = onlineStatus;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public boolean isOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(boolean onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}