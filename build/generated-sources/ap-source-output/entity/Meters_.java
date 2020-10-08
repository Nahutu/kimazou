package entity;

import entity.Pistolet;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-10-08T11:16:52")
@StaticMetamodel(Meters.class)
public class Meters_ { 

    public static volatile SingularAttribute<Meters, Double> quantitePrelevee;
    public static volatile SingularAttribute<Meters, Pistolet> pistolet;
    public static volatile SingularAttribute<Meters, String> produit;
    public static volatile SingularAttribute<Meters, Date> datePrelevement;
    public static volatile SingularAttribute<Meters, Integer> metersID;

}