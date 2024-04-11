package com.example.demo.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;
    private String name;
    @ManyToMany
    @JoinTable(name = "specialist_location",
            joinColumns = @JoinColumn(name = "location_id", referencedColumnName = "id") ,
            inverseJoinColumns = @JoinColumn(name = "specialist_id", referencedColumnName = "specialistId"))
    private List<Specialist> specialists;
//    @ManyToMany(mappedBy = "location")
//    private List<Specialist> specialists;

    public Location(String address, String name) {
        this.address = address;
        this.name = name;

    }


    public Location() {
        this.specialists = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getAdresa() {
        return address;
    }

    public void setAdresa(String address) {
        this.address = address;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Specialist> getSpecialists() {
        return specialists;
    }

    public void setSpecialists(List<Specialist> specialists) {
        this.specialists = specialists;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", specialists=" + specialists +
                ", address=" + address +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;
        Location location = (Location) o;
        return Objects.equals(getId(), location.getId()) && Objects.equals(address, location.address) && Objects.equals(getName(), location.getName()) && Objects.equals(getSpecialists(), location.getSpecialists());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), address, getName(), getSpecialists());
    }
}
