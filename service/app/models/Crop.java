package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Crop {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)                       /////////id comes from another table.
    public Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long area;

    public Long getArea() {
        return area;
    }

    public void setArea(Long area) {
        this.area = area;
    }

    public Long price;

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Date starttime;

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date endtime;

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Date bidendtime;

    public Date getBidendtime() {
        return bidendtime;
    }

    public void setBidendtime(Date bidendtime) {
        this.bidendtime = bidendtime;
    }

    public String transport;

    public String getTransport() { return transport;}

    public void setTransport(String transport) { this.transport = transport; }

    public String description;

    public String getDescription() { return description;}

    public void setDescription(String description) { this.description = description; }

    public Long quantitymin;

    public Long getQuantitymin() {
        return quantitymin;
    }

    public void setQuantitymin(Long quantitymin) {
        this.quantitymin = quantitymin;
    }

    public Long quantitymax;

    public Long getQuantitymax() {
        return quantitymax;
    }

    public void setQuantitymax(Long quantitymax) {
        this.quantitymax = quantitymax;
    }

    public Long fid;

    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}





















