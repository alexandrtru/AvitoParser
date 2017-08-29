package com.siteMonitor.Model;

public class Ad {
    private int siteId;
    private String name;
    private int price;
    private String link;
    private String description;
    private String date;
    private boolean isViewed;

    public Ad(int siteId, String name, int price, String link, String description, String date, boolean isViewed) {
        this.siteId = siteId;
        this.name = name;
        this.price = price;
        this.link = link;
        this.description = description;
        this.date = date;
        this.isViewed = isViewed;
    }

    public int getSiteId() {
        return siteId;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public boolean isViewed() {
        return isViewed;
    }

    public void setViewed(boolean viewed) {
        this.isViewed = viewed;
    }

    @Override
    public boolean equals(Object obj) {
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public String toString() {
        return "Ad{" +
                "siteId=" + siteId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", isViewed=" + isViewed +
                '}';
    }

/*    @Override
    public String toString() {
        //return this.getId()+ " % " + this.getName() + " % " + this.getPrice()   + " % " + this.getLink() + " % " + this.getDescription() +" % "+ this.date +" % " + this.isViewed();
        return "Ad details id: " + this.getId()+ " name: " + this.getName() + " price: " + this.getPrice()   + " LINK: " + this.getLink() + " description: " + this.getDescription();
    }*/

    @Override
    public int hashCode() {
        return siteId;
    }
}
