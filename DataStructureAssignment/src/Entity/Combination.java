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
public class Combination {
    private String mSID;
    private Symptom symptom;
    private Medicine medicine;
    
    public Combination(){}
    
    public Combination(String mSID, Symptom symptom , Medicine medicine){
        this.mSID = mSID;
        this.symptom = symptom;
        this.medicine = medicine;
    }

    public String getmSID() {
        return mSID;
    }

    public void setmSID(String mSID) {
        this.mSID = mSID;
    }

    public Symptom getSymptom() {
        return symptom;
    }

    public void setSymptom(Symptom symptom) {
        this.symptom = symptom;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }
    
    
}
