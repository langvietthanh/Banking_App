package DAO;

import java.util.ArrayList;

public interface interfaceDAO<T> {
    public void create(T t);
    public void update(T t, Long ID);
    public void delete(T t);
    public <V> ArrayList<T> findByAttribute(String Attribute , V Value_Attribute);

}
