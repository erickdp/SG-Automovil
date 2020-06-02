package data;

import java.util.List;

public interface CRUD<T> {

    int create(T miObjeto);

    List<T> read();

    int update(T miObjeto);

    int delete(T miObjeto);
    
    T readObjetById(T miObjeto);
}
