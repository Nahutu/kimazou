package entity;

import entity.Ilot;
import entity.Proprietaire;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-10-08T11:16:52")
@StaticMetamodel(Station.class)
public class Station_ { 

    public static volatile SingularAttribute<Station, Proprietaire> proprietaire;
    public static volatile SingularAttribute<Station, String> stationName;
    public static volatile SingularAttribute<Station, String> stationPhone;
    public static volatile ListAttribute<Station, Ilot> ilotList;
    public static volatile SingularAttribute<Station, String> stationAddress;
    public static volatile SingularAttribute<Station, Integer> stationID;

}