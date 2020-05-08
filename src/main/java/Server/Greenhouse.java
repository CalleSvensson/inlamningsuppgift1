package Server;

import java.time.LocalDate;

public class Greenhouse{
    private int id;
    private float temperature;
    private float humidity;
    private float luminosity;
    private float electricityConsumption;
    private LocalDate measureTime;
    private LocalDate updated;

    public Greenhouse() {
    }

    public Greenhouse(int id, float temperature, float humidity, float luminosity, float electricityConsumption, LocalDate measureTime) {
        this.id = id;
        this.temperature = temperature;
        this.humidity = humidity;
        this.luminosity = luminosity;
        this.electricityConsumption = electricityConsumption;
        this.measureTime = measureTime;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getLuminosity() {
        return luminosity;
    }

    public void setLuminosity(float luminosity) {
        this.luminosity = luminosity;
    }

    public float getElectricityConsumption() {
        return electricityConsumption;
    }

    public void setElectricityConsumption(float electricityConsumption) {
        this.electricityConsumption = electricityConsumption;
    }

    public LocalDate getMeasureTime() {
        return measureTime;
    }

    public void setMeasureTime(LocalDate measureTime) {
        this.measureTime = measureTime;
    }

    public LocalDate getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDate updated) {
        this.updated = updated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
