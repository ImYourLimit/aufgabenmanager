package de.swe.aufgabenmanager._3_domain.interfaces;

import de.swe.aufgabenmanager._3_domain.entities.User;

public interface IUserRepository extends ISave<User>, IFindById, IFindAll { }