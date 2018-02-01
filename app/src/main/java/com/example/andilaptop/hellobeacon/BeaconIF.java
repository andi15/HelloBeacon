package com.example.andilaptop.hellobeacon;

/**
 * Created by golnaz.elmamooz on 04.01.2018.
 */

public interface BeaconIF {

    public String getUUID();

    public void setUUID(String uuid);

    public Integer getMajor();

    public void setMajor(int major);

    public Integer getMinor();

    public void setMinor(int minor);
}
