package ru.koryakin.carmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.koryakin.carmanager.entity.Car;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("select c from Car c where (brand = :brand or :brand is null) " +
            "and (color = :color or :color is null) " +
            "and (releaseYear = :releaseYear or :releaseYear is null)")
    List<Car> findAllCars(String brand, String color, Integer releaseYear);

}
