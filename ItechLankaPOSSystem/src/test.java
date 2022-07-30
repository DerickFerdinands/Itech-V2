import Util.FactoryConfigurations;
import dao.Custom.OrderDAO;
import dao.Custom.impl.OrderDAOImpl;
import dao.Custom.impl.OrderDetailDAOImpl;
import entity.Item;
import entity.Orders;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;

public class test {
    private static OrderDAO oDAO = new OrderDAOImpl();
    public static void main(String[] args) {
/*
        Session session = FactoryConfigurations.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(new Orders(oDAO.getNextId(),))
        transaction.commit();
        session.close();*/
    }
}
