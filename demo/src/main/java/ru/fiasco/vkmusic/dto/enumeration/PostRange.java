package ru.fiasco.vkmusic.dto.enumeration;

public enum PostRange {
    DAY(1),
    WEEK(7),
    MONTH(30);

    private int dayCount;

    PostRange(int dayCount) {
        this.dayCount = dayCount;
    }

    public int getDayCount() {
        return dayCount;
    }

    public void setDayCount(int dayCount) {
        this.dayCount = dayCount;
    }
}
