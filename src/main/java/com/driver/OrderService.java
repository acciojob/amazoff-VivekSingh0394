package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    public void addOrder(Order order)
    {
        orderRepository.addOrderDb(order);
    }
    public void addPartner(String partnerid)
    {
           orderRepository.addPartnerDb(partnerid);
    }
    public void orderpartnerpair(String orderid,String partnerId)
    {
        orderRepository.orderPartnerpairDb(orderid,partnerId);
    }
    public Order getOrderById(String orderId)
    {
        return orderRepository.getOrderById(orderId);
    }
    public DeliveryPartner getPartnerById(String id)
    {
        return orderRepository.getPartnerById(id);
    }
    public int getOrdersforPartnerId(String id)
    {
        return orderRepository.getOrderCountForPartnerid(id);
    }

    public List<String> getOrderListForPartner(String id)
    {
        return orderRepository.orderListforPartnerId(id);
    }
    public List<String > getllOrders()
    {
        return orderRepository.getAllOrders();
    }
    public int unassignedOrderscount()
    {
        return orderRepository.unassignedOrders();
    }
    public int undeliveredOrders(String time,String id)
    {
        return orderRepository.ordersUndeliveredafterTime(time,id);
    }
    public String lastDeliveryTime(String id)
    {
        return orderRepository.lastDeliveryTime(id);
    }
    public void deleteDeliveryPartner(String id)
    {
        orderRepository.deletePartnerById(id);
    }
    public void deleteOrder(String id)
    {
        orderRepository.deleteOrder(id);
    }

}

