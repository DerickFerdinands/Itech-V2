package dao.Custom.impl;

import Util.FactoryConfigurations;
import dao.Custom.PurchaseDAO;
import entity.Orders;
import entity.Purchase;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class PurchaseDAOImpl implements PurchaseDAO {
    @Override
    public ArrayList<Purchase> getAll() throws Exception {
        Session session = FactoryConfigurations.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        List<Purchase> list = session.createQuery("FROM Purchase").list();
        transaction.commit();
        session.close();
        return new ArrayList<Purchase>(list);
    }

    @Override
    public boolean delete(String s) throws Exception {
        Session session = FactoryConfigurations.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(session.load(Purchase.class,s));
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean save(Purchase entity) throws Exception {
        Session session = FactoryConfigurations.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(entity);
            transaction.commit();
            session.close();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            transaction.rollback();
            session.close();
            return false;
        }

    }

    @Override
    public boolean update(Purchase entity) throws Exception {
        Session session = FactoryConfigurations.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(entity);
            transaction.commit();
            session.close();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            transaction.rollback();
            session.close();
            return false;
        }

    }

    @Override
    public ArrayList<Purchase> getMatchingResults(String search) throws Exception {
        return null;
    }

    @Override
    public String getLastId() throws Exception {
        return null;
    }

    @Override
    public String getNextId() throws Exception {
        Session session = FactoryConfigurations.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        List<String> id = session.createQuery("SELECT ID FROM Purchase ORDER BY ID DESC").setMaxResults(1).list();
        transaction.commit();
        session.close();

        return id.size()>0?String.format("#P%05d",Integer.valueOf(id.get(0).replace("#P",""))+1):"#P00001";
    }
}
