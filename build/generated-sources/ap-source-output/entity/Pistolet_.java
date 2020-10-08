package entity;

import entity.Meters;
import entity.Pompe;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-10-08T11:16:52")
@StaticMetamodel(Pistolet.class)
public class Pistolet_ { 

    public static volatile SingularAttribute<Pistolet, Double> volumeVendu;
    public static volatile SingularAttribute<Pistolet, String> typeGaz;
    public static volatile SingularAttribute<Pistolet, Date> dateReleve;
    public static volatile SingularAttribute<Pistolet, Integer> pistoletID;
    public static volatile ListAttribute<Pistolet, Meters> metersList;
    public static volatile SingularAttribute<Pistolet, Pompe> pompe;

}