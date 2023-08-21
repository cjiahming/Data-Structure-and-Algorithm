package Entity;

public class Symptom {
    
    String sID;
    String sName;
    String sDesc;
    
    public Symptom() {
        
    }
    
    public Symptom(String sID, String sName, String sDesc) {
        this.sID = sID;
        this.sName = sName;
        this.sDesc = sDesc;
    }
    
    public String getSID() {
        return sID;
    }
    
    public String getSName() {
        return sName;
    }
    
    public String getSDesc() {
        return sDesc;
    }
    
    public void setSID(String sID) {
        this.sID = sID;
    }
    
    public void setSName(String sName) {
        this.sName = sName;
    }
    
    public void setSDesc(String sDesc) {
        this.sDesc = sDesc;
    }
}
