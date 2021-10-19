package com.grofandriska.JPAdemo;

import com.grofandriska.JPAdemo.model.ParkingPlace;
import com.grofandriska.JPAdemo.model.dao.ParkingPlaceDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingPlaceDaoTest {

    private ParkingPlaceDao parkingPlaceDao;

    @BeforeEach
    public void init() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("pu");
        parkingPlaceDao = new ParkingPlaceDao(entityManagerFactory);
    }

    @Test
    public void saveParkingPlace(){
        ParkingPlace parkingPlace = new ParkingPlace(null,99);
        parkingPlaceDao.saveParkingLot(parkingPlace);
        ParkingPlace anotherParkingPlace = parkingPlaceDao.findParkingPLaceNumber(parkingPlace.getNumber());
        assertEquals(99,anotherParkingPlace.getNumber());
    }

}
