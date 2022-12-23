package init_grid;
  
public class order implements Comparable<order> { 
    public String orderNo; 
    public double value; 
  
    public int compareTo(order o1) 
    { 
        return orderNo.compareTo(o1.orderNo); 
    } 
  
    public order(String orderNo, double value) 
    { 
        super(); 
        this.orderNo = orderNo; 
        this.value = value; 
    } 
  
    @Override
    public String toString() 
    { 
         return this.orderNo; 
    } 
  
    public String getOrderNo() 
    { 
        return orderNo; 
    } 
  
    public void setOrderNo(String orderNo) 
    { 
        this.orderNo = orderNo; 
    } 
  
    public double getValue() 
    { 
        return value; 
    } 
  
    public void setValue(double value) 
    { 
        this.value = value; 
    } 
} 