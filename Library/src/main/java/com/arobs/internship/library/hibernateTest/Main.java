package com.arobs.internship.library.hibernateTest;

import org.hibernate.Session;

public class Main {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String sql = "select version()";
        String result = (String) session.createNativeQuery(sql).getSingleResult();
        System.out.println(result);
        session.getTransaction().commit();
        session.close();
        HibernateUtil.shutdown();
    }
}
