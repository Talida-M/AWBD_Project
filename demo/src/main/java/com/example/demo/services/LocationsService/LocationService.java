package com.example.demo.services.LocationsService;


import com.example.demo.dtos.NewLocationDTO;
import com.example.demo.dtos.SpecialistDTO;
import com.example.demo.entity.Location;
import com.example.demo.entity.Specialist;
import com.example.demo.exception.DuplicateException;
import com.example.demo.repositories.LocationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LocationService implements ILocationService{
    @Autowired
    LocationRepo locationRepo;


    public LocationService(LocationRepo locationRepo) {
        this.locationRepo =locationRepo;
    }

    @Transactional
    @Override
    public Location registerLocation(Location location) {
        Location existingLocation= locationRepo.getByName(location.getName()).orElse(null);

        if (existingLocation == null) {
            Location newLocation  = new Location ();
            newLocation.setName(location.getName());
            newLocation.setAdresa(location.getAdresa());
            newLocation.setSpecialists(location.getSpecialists());
            locationRepo.save(newLocation);


            return  newLocation;
        }
        else {
            throw new DuplicateException();
        }
    }

    @Override
    public List<Location> getLocationsList() {
        return  locationRepo.findAll();
    }

    @Override
    public List<SpecialistDTO> getSpecialistsByLocation(long locationId) {
        return locationRepo.getSpecialistsByLocation(locationId);
    }

    @Override
    public List<NewLocationDTO> getLocationBySpecialists(Integer idS) {
        return locationRepo.getLocationBySpecialists(idS);
    }



}
