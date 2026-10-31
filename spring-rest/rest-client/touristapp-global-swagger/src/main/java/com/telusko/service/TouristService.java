package com.telusko.service;

import com.telusko.entity.Tourist;
import com.telusko.exception.TouristNotFoundException;
import com.telusko.repo.ITouristRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TouristService implements ITouristService{

    @Autowired
    private ITouristRepo repo;

    @Override
    public String addTourist(Tourist tourist) {
        Tourist touristDb = repo.save(tourist);
        return "Tourist info saved with id: " + touristDb.getId();
    }

    @Override
    public String updateTourist(Integer id, Double budget) {

        Optional<Tourist> optional = repo.findById(id);

        if(optional.isPresent())
        {
            Tourist touristDb = optional.get();
            touristDb.setBudget(budget);
            repo.save(touristDb);
            return "Tourist budget updated successfully for id:" + id;
        }

        throw new TouristNotFoundException("Tourist not found with id : " + id);

//        Tourist touristDb = repo.findById(id).orElseThrow(() -> new RuntimeException("Tourist not found"));
//        touristDb.setBudget(budget);
//        repo.save(touristDb);
//        return "";
    }

    @Override
    public String updateTourist(Tourist tourist)
    {
        Tourist touristDb = repo.findById(tourist.getId()).orElseThrow(() -> new TouristNotFoundException("Tourist not found with id : " + tourist.getId()));
        touristDb.setName(tourist.getName());
        touristDb.setCity(tourist.getCity());
        touristDb.setPackageType(tourist.getPackageType());
        touristDb.setBudget(tourist.getBudget());

        repo.save(touristDb);

        return "Tourist info updated succesfully for id: " + tourist.getId();
    }

    @Override
    public String deleteTourist(Integer id)
    {
        Optional<Tourist> optional = repo.findById(id);

        if(optional.isPresent())
        {
            repo.deleteById(id);
            return "Tourist budget deleted successfully for id:" + id;
        }

        throw new TouristNotFoundException("Tourist not found with id : " + id);
    }

    @Override
    public Tourist getTourist(Integer id) {
        Optional<Tourist> optional = repo.findById(id);

        if(optional.isPresent())
        {

            return optional.get();
        }

        throw new TouristNotFoundException("Tourist not found with id : " + id);
    }

    @Override
    public List<Tourist> getAllTourists() {
        return repo.findAll();
    }
}
