package com.mcgill.application.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LiveTick {
    private final StringProperty symbol = new SimpleStringProperty();
    private final StringProperty time = new SimpleStringProperty();
    private final DoubleProperty price = new SimpleDoubleProperty();

    public String getSymbol() { return symbol.get(); }
    public void setSymbol(String value) { symbol.set(value); }
    public StringProperty symbolProperty() { return symbol; }

    public String getTime() { return time.get(); }
    public void setTime(String value) { time.set(value); }
    public StringProperty timeProperty() { return time; }

    public double getPrice() { return price.get(); }
    public void setPrice(double value) { price.set(value); }
    public DoubleProperty priceProperty() { return price; }
}


