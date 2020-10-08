package entity;

import entity.Pompe;
import entity.Station;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-10-08T11:16:52")
@StaticMetamodel(Ilot.class)
public class Ilot_ { 

    public static volatile SingularAttribute<Ilot, Integer> ilotID;
    public static volatile SingularAttribute<Ilot, Station> station;
    public static volatile ListAttribute<Ilot, Pompe> pompeList;

}