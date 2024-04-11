package com.example.demo.services.LocationsService;

import com.example.demo.dtos.NewLocationDTO;
import com.example.demo.dtos.SpecialistDTO;
import com.example.demo.entity.Location;
import com.example.demo.entity.Specialist;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ILocationService {
    Location registerLocation(Location location);
    List<Location> getLocationsList();
    List<SpecialistDTO> getSpecialistsByLocation(long locationId);
    List<NewLocationDTO> getLocationBySpecialists(Integer idS);


}
