package de.swe.aufgabenmanager._3_domain.interfaces;

import de.swe.aufgabenmanager._3_domain.entities.Group;

import java.util.List;

public interface IGroupRepository extends ISave<Group>, IDelete<Group>, IFindById, IFindAll {

    List<Long> findGroupIdsByUserId(Long userId);
}
