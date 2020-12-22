package javaFX;

import javafx.beans.property.SimpleStringProperty;

public class LikedRestObj {
    private SimpleStringProperty userName;
    private SimpleStringProperty restName;
    private SimpleStringProperty Like;
    private SimpleStringProperty Review;

    public LikedRestObj(String userName, String restName, String Like, String Review) {
        this.userName = new SimpleStringProperty(userName);
        this.restName = new SimpleStringProperty(restName);
        this.Like = new SimpleStringProperty(Like);
        this.Review = new SimpleStringProperty(Review);

    }

    public String getUserName() {
        return userName.get();
    }

    public void setDescription(String des) {
        this.Review.set(des);
    }

    public String getName() {
        return this.restName.get();
    }

    public String getLike() {
        return this.Like.get();
    }

    public String getReview() {
        return this.Review.get();
    }

}

