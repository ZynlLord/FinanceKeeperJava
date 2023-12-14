package com.example.financekeeperjava.presentation.main.profile;

public class ProfileItem {
    private int iconResId;
    private String title;
    private String subtitle;

    public ProfileItem(int iconResId, String title, String subtitle) {
        this.iconResId = iconResId;
        this.title = title;
        this.subtitle = subtitle;
    }

    public int getIconResId() {
        return iconResId;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }
}