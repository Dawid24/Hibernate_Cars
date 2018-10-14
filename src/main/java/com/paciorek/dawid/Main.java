package com.paciorek.dawid;

import com.paciorek.dawid.model.Car;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Main {
    private static SessionFactory factory;
    public static void main(String[] args) {
        factory = new Configuration().configure().buildSessionFactory();

        Car car = new Car();
        car.setBrand("BMW");
        car.setModel("3");
        car.setCapacity(3.3);
        car.setPower(5);
        car.setDate(new Date());

        Main.persistCar(car);
        ArrayList<Car> cars = Main.getCars();
    }

    public static void persistCar(Car car) {
        Session session = Main.factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(car);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null) tx.rollback();
        } finally {
            session.close();
        }
    }

    public static ArrayList<Car> getCars() {
        Session session = Main.factory.openSession();
        Transaction transaction = null;
        ArrayList<Car> result = new ArrayList<Car>();

        transaction = session.beginTransaction();
        List cars = session.createQuery("FROM com.paciorek.dawid.model.Car").list();
        for (Iterator iterator = cars.iterator(); iterator.hasNext();) {
            Car c = (Car) iterator.next();
            result.add(c);
        }
        transaction.commit();
        return result;
    }
}
