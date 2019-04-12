package com.example.tea.teaphone.Model;

import com.example.tea.teaphone.R;

import java.util.ArrayList;

public class Slide {
    int image;
    String description;

    public Slide(){}

    public Slide(int image, String description) {
        this.image = image;
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static ArrayList init() {
        ArrayList<Slide> arrayList = new ArrayList<>();
        arrayList.add(new Slide(R.drawable.slideshow1, ""));
        arrayList.add(new Slide(R.drawable.slideshow2, ""));
        arrayList.add(new Slide(R.drawable.slideshow3, ""));

        return arrayList;
    }
}
