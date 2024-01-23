package com.example.mobile_tour.ui;

public class ClickedTravelData {

        private final String title;
        private final int imageUrl;
        private final String category;

        public ClickedTravelData(String title, int imageUrl, String category) {
            this.title = title;
            this.imageUrl = imageUrl;
            this.category = category;
        }

        public String getTitle() {
            return title;
        }

        public int getImageUrl() {
            return imageUrl;
        }

        public String getCategory() {
            return category;

    }
}
