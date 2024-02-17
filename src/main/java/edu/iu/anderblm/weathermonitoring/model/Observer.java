package edu.iu.anderblm.weathermonitoring.model;

public interface Observer {
    void update(float temperature, float humidity, float pressure);
    String name();
    String id();
}
