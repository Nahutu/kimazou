package entity;

import entity.Ilot;
import entity.Pistolet;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-10-08T11:16:52")
@StaticMetamodel(Pompe.class)
public class Pompe_ { 

    public static volatile SingularAttribute<Pompe, String> noSerie;
    public static volatile SingularAttribute<Pompe, Integer> pompeID;
    public static volatile SingularAttribute<Pompe, String> modele;
    public static volatile SingularAttribute<Pompe, Ilot> ilot;
    public static volatile SingularAttribute<Pompe, Integer> nbrePistolet;
    public static volatile ListAttribute<Pompe, Pistolet> pistoletList;

}