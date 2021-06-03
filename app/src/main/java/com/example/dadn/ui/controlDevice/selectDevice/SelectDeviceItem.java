package com.example.dadn.ui.controlDevice.selectDevice;

public class SelectDeviceItem {
    private String name;
    private String id;
    private String data;
    private String unit;

    public SelectDeviceItem(String id, String name, String data, String unit){
        this.name = name;
        this.data = data;
        this.id = id;
        this.unit = unit;
    }

    public String getId(){ return this.id; }
    public String getName() { return  this.name; }
    public String getData() { return this.data; }
    public String getUnit() { return  this.unit; }

    public void setName(String name){this.name = name;}
    public void setId(String id){this.id = id;}
    public void setData(String data){this.data = data;}
    public void setUnit(String unit){this.unit = unit;}


}
