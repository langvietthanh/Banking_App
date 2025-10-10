package DAO;

import java.util.ArrayList;
import java.util.Objects;

public interface interfaceDAO<T> {
    public void create(T t);
    public <V> void updateAttribute(String attribute,V value, String key);
    public void delete(T t);
    public Object findByAttribute(String attribute, String key);
}
