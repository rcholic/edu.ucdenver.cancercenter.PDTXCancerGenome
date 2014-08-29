package dao;

import com.sun.org.apache.xml.internal.utils.SerializableLocatorImpl;

import java.io.Serializable;

/**
 * Created by tonywang on 6/25/14.
 * Interface for doing CRUD with hibernate
 */
public interface GenericDaoInterface <T, PK extends Serializable>
{
    /* persist the newInstance object into database */
    PK create(T newInstance);

    /* Retrieve an object from database */
    T read(PK id);

    /* save changes made to a persisted object */
    void update(T transientObject);

    /* remove a persisted object */
    void delete(T persistedObject);
}
