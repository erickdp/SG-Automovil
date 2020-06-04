package data;

import java.util.Date;
import java.util.Set;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;

public interface CRUD<T> {

    int create(T miObjeto);

    Set<T> read(ButtonGroup grupoRD, JCheckBox[] grupoCB, String placa, String propietario, Date fechaEntrada);

    int update(T miObjeto);

    int delete(T miObjeto);

}
