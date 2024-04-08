package de.swe.aufgabenmanager._3_domain.entities;

import java.util.List;

public interface IFindAll {
    <T> List<T> findAll();
}