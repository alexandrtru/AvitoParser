package com.siteMonitor.Model;

import javax.persistence.*;

@Entity
@Table(name = "ads")
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int siteId;
    private String name;
    private int price;
    private String link;
    private String description;
    private String date;
    private boolean isViewed;

    public Ad() {
    }

    public Ad(int siteId, String name, int price, String link, String description, String date, boolean isViewed) {
        this.siteId = siteId;
        this.name = name;
        this.price = price;
        this.link = link;
        this.description = description;
        this.date = date;
        this.isViewed = isViewed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
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
