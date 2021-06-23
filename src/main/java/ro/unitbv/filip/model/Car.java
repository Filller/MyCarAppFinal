package ro.unitbv.filip.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Car {
    private String registrationPlate;
    private String carBrand;
    private String carModel;
    private int ownerId;

    @Id
    @Column(name = "registrationPlate")
    public String getRegistrationPlate() {
        return registrationPlate;
    }

    public void setRegistrationPlate(String registrationPlate) {
        this.registrationPlate = registrationPlate;
    }

    @Basic
    @Column(name = "carBrand")
    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    @Basic
    @Column(name = "carModel")
    public String getCarModel() {
        return carModel;
    }

    @Basic
    @Column(name = "owner")
    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }


    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(registrationPlate, car.registrationPlate) && Objects.equals(carBrand, car.carBrand) && Objects.equals(carModel, car.carModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationPlate, carBrand, carModel);
    }
}
