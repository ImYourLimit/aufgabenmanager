package de.swe.aufgabenmanager._3_domain.interfaces;

public interface IFindById {
    <T> T findById(Long id);
}
