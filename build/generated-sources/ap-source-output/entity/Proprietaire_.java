package entity;

import entity.Station;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-10-08T11:16:52")
@StaticMetamodel(Proprietaire.class)
public class Proprietaire_ { 

    public static volatile SingularAttribute<Proprietaire, String> firstname;
    public static volatile SingularAttribute<Proprietaire, String> address;
    public static volatile ListAttribute<Proprietaire, Station> stationList;
    public static volatile SingularAttribute<Proprietaire, Integer> propID;
    public static volatile SingularAttribute<Proprietaire, String> telephone;
    public static volatile SingularAttribute<Proprietaire, String> nif;
    public static volatile SingularAttribute<Proprietaire, String> email;
    public static volatile SingularAttribute<Proprietaire, String> lastname;

}