package database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class Conexion {
			
	private Session sesion;

	public Session iniciaOperacion()
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        sesion = sessionFactory.openSession();
        sesion.getTransaction().begin();
        return sesion;
    }

    public void terminaOperacion()
    {
        sesion.getTransaction().commit();
        sesion.close();
    }
}
