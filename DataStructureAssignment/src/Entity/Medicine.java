/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author Gab
 */
public class Medicine {
    private String mID;
    private String mName;
    private double mPrice;
    private String mDesc;
    
    
    public Medicine(String mID, String mName, double mPrice, String mDesc){
        this.mID = mID;
        this.mName = mName;
        this.mPrice = mPrice;
        this.mDesc = mDesc;
    }
    
    public Medicine(){};
    
    
    public String getmID(){
        return this.mID;
    }
    
    public String getmName(){
        return this.mName;
    }
    
    public double getmPrice() {
        return this.mPrice;
    }
    
    public String getmDesc(){
        return this.mDesc;
    }

    

    public void setmID(String mID) {
        this.mID = mID;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
    
    public void setmPrice(double mPrice) {
        this.mPrice = mPrice;
    }

    public void setmDesc(String mDesc) {
        this.mDesc = mDesc;
    }

   
    
    
}
