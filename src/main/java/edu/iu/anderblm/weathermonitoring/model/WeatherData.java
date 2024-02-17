package edu.iu.anderblm.weathermonitoring.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class WeatherData implements Subject {
    private List<Observer> observers;
    private float temperature;
    private float humidity;
    private float pressure;

    public WeatherData() {
        this.observers = new ArrayList<>();
        startMeasuring();
    }

    private void startMeasuring() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(sensorsChanged, 0, 3, TimeUnit.SECONDS);
    }

    Runnable sensorsChanged = () -> {
        setMeasurements((float) Math.random(),
                (float) Math.random(),
                (float) Math.random());
    };

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(temperature, humidity, pressure);
        }
    }

    @Override
    public List<Observer> getObservers() {
        return observers;
    }

    public void measurementChanged() {
        notifyObservers();
    }

    public void setMeasurements(float temperature,
                                float humidity,
                                float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementChanged();
    }

    // Add method to calculate heat index
    public float calculateHeatIndex(float temperature, float humidity) {
        // Calculation of heat index
        float heatIndex = (float)((16.923 + (0.185212 * temperature) + (5.37941 * humidity) - (0.100254 * temperature * humidity) +
                (0.00941695 * (temperature * temperature)) + (0.00728898 * (humidity * humidity)) +
                (0.000345372 * (temperature * temperature * humidity)) - (0.000814971 * (temperature * humidity * humidity)) +
                (0.0000102102 * (temperature * temperature * humidity * humidity)) - (0.000038646 * (temperature * temperature * temperature)) +
                (0.0000291583 * (humidity * humidity * humidity)) + (0.00000142721 * (temperature * temperature * temperature * humidity)) +
                (0.000000197483 * (temperature * humidity * humidity * humidity)) - (0.0000000218429 * (temperature * temperature * temperature * humidity * humidity)) +
                0.000000000843296 * (temperature * temperature * humidity * humidity * humidity)) -
                (0.0000000000481975 * (temperature * temperature * temperature * humidity * humidity * humidity)));
        return heatIndex;
    }
}
