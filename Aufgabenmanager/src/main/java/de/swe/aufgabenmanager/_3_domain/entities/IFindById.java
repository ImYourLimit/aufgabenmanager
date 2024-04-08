package de.swe.aufgabenmanager._3_domain.entities;

public interface IFindById {
    <T> T findById(Long id);
}
