package de.swe.aufgabenmanager._3_domain.entities;

public interface ISave<T> {
    void save(T entity);
}
