package com.example.demo.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class LocationSpecialistsDTO {

        private Long id;
        private String name;
        private String address;

        public LocationSpecialistsDTO(String name, String address) {
                this.name = name;
                this.address = address;
        }
}
