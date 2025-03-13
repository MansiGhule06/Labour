package com.example.labourcinnect1;

import com.denzcoskun.imageslider.constants.ScaleTypes;

public class SlideModel {
    private String imageUrl;
    private String title;

    public SlideModel(String imageUrl, String title, ScaleTypes centerCrop) {
        this.imageUrl = imageUrl;
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }
}


