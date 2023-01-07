package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {

    HashMap<String,Order> orderDb= new HashMap<>();
    HashMap<String,DeliveryPartner>deliveryPartnerDb = new HashMap<>();
    HashMap<String, List<String>> orderPartnerPairDb= new HashMap<>();

    public void addOrderDb(Order order)
    {
        String id = order.getId();
        orderDb.put(id,order);
    }
    public void addPartnerDb(String id)
    {
        deliveryPartnerDb.put(id,new DeliveryPartner(id));
    }
    public void orderPartnerpairDb(String orderId,String partnerId)
    {
        if(orderPartnerPairDb.containsKey(partnerId))
        {
            orderPartnerPairDb.get(partnerId).add(orderId);
        }
        else {
            List<String> orderList=new ArrayList<>();
            orderList.add(orderId);
            orderPartnerPairDb.put(partnerId,orderList);

        }
    }
    public Order getOrderById(String orderId)
    {

        if(orderDb.containsKey(orderId))
        {
           return orderDb.get(orderId);
        }
        return null;
    }
    public DeliveryPartner getPartnerById(String id)
    {
        if(deliveryPartnerDb.containsKey(id))
        {
            return deliveryPartnerDb.get(id);
        }
        return null;
    }
    public int getOrderCountForPartnerid(String id)
    {
       List<String> orders= orderPartnerPairDb.get(id);
       return orders.size();
    }
    public List<String> orderListforPartnerId(String id)
    {
        List<String> orderList= new ArrayList<>();

        if(orderPartnerPairDb.containsKey(id))
        {
            return orderPartnerPairDb.get(id);
        }
        return orderList;
    }
    public List<String > getAllOrders()
    {
        List<String> allOrders = new ArrayList<>();
        for(String x: orderDb.keySet())
        {
            allOrders.add(x);
        }
        return allOrders;
    }
    public int unassignedOrders()
    {
        int unassignedOrdersCount =0;
        boolean unassigned = true;
        for(String x:orderDb.keySet())
        {
            unassigned=true;
            for(String y:orderPartnerPairDb.keySet())
            {
                List<String > orders = orderPartnerPairDb.get(y);
                for(int i = 0 ; i < orders.size();i++)
                {
                    if(x.equals(orders.get(i)))
                    {
                        unassigned = false;
                        break;
                    }
                }
            }
            if(unassigned==true)
                unassignedOrdersCount++;
        }

        return unassignedOrdersCount;
    }
    public int ordersUndeliveredafterTime(String timeGiven,String partnerId)
    {
        int undeliveredOrders =0;
        String time1[] = timeGiven.split(":");
        int time= Integer.parseInt(time1[0])*60+ Integer.parseInt(time1[1]);
        if(orderPartnerPairDb.containsKey(partnerId))
        {
            List<String>orderList = orderPartnerPairDb.get(partnerId);
            for(int i =0 ; i < orderList.size();i++)
            {
                Order order = orderDb.get(orderList.get(i));
                int orderTime = order.getDeliveryTime();
                if(orderTime > time)
                {
                    undeliveredOrders++;
                }
            }
        }
    return undeliveredOrders;

    }
    public String lastDeliveryTime(String id)
    {
        int lastdelivery=Integer.MIN_VALUE;
        List<String>orderlist= orderPartnerPairDb.get(id);
        for(int i =0 ; i < orderlist.size();i++)
        {
            Order order = orderDb.get(orderlist.get(i));
            int orderTime = order.getDeliveryTime();
            if(orderTime > lastdelivery)
            {
                lastdelivery=orderTime;
            }
        }
        double ltime = lastdelivery/60;
        double hr = Math.floor(ltime);
        double min = ltime-hr;
        String ans ="";
        ans=ans+String.valueOf(hr);
        ans=ans+String.valueOf(min*60);

        return ans;
    }
    public  void deletePartnerById(String id)
    {
        deliveryPartnerDb.remove(id);
        orderPartnerPairDb.remove(id);
    }
    public void deleteOrder(String id)
    {
        orderDb.remove(id);
        for(String x:orderPartnerPairDb.keySet())
        {
            List<String> orderList= orderPartnerPairDb.get(x);
            for(int i =0 ; i < orderList.size();i++)
            {
                if(orderList.get(i).equals(id))
                {
                    orderList.remove(id);
                }
            }
        }
    }




}
