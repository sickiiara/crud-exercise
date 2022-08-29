package co.develhope.crudexercise.controllers;

import co.develhope.crudexercise.entities.Car;
import co.develhope.crudexercise.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarRepository carRepository;

    //create a new car
    @PostMapping("/addCar")
    public Car addCar (@RequestBody Car car){
        return carRepository.save(car);
    }

    //get a list of all cars
    @GetMapping("/getAllCars")
    public List<Car> getAllCars(){
        return carRepository.findAll();
    }

    //get a single car by id
    @GetMapping("/getCarById/{id}")
    public Optional<Car> getCarById(@PathVariable Long id){
        if(carRepository.existsById(id)){
            return carRepository.findById(id);
        }else{
            return null;
        }
    }

    //update the type of a specific Car, identified by id and passing a query param - if not present in the db, returns an empty Car
    @PutMapping("/putCar/{id}")
    public Car updateCar(@PathVariable Long id, @RequestBody Car car){
        if(carRepository.existsById(id)){
            car.setId(id);
            carRepository.save(car);
            return car;
        }else{
            return null;
        }
    }

    //delete a specific Car - if absent, the response will have a Conflict HTTP status
    @DeleteMapping("/deleteCar/{id}")
    public String deleteById(@PathVariable Long id, HttpServletResponse response){
        if(carRepository.existsById(id)){
            carRepository.deleteById(id);
            return "Car " + id + " has been deleted from the database.";
        }else{
            try {
                response.sendError(409, "conflict status");
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
    }

    //delete all cars
    @DeleteMapping("/deleteAll")
    public String deleteAllCars(){
        carRepository.deleteAll();
        return "All cars have been deleted.";
    }

}
