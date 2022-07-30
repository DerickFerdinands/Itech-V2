package dao.Custom;

import dao.CrudDAO;
import entity.Orders;

import java.util.ArrayList;
import java.util.HashMap;

public interface OrderDAO extends CrudDAO<Orders,String> {
    Orders get(String OrderID) throws Exception;

    HashMap<String, Double> getDistinctSales() throws Exception;

    HashMap<String, Double> getAnnualSales() throws Exception;

    HashMap<String, Double> getMonthlySales(int year) throws Exception;

    ArrayList<Integer> getDistinctYears() throws Exception;
}
