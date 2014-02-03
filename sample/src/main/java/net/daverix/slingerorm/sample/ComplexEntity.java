package net.daverix.slingerorm.sample;

import net.daverix.slingerorm.annotation.DatabaseEntity;
import net.daverix.slingerorm.annotation.FieldName;
import net.daverix.slingerorm.annotation.GetField;
import net.daverix.slingerorm.annotation.SetField;

/**
 * Created by daverix on 2/1/14.
 */
@DatabaseEntity(name = "Complex", primaryKey = "_id")
public class ComplexEntity extends AbstractComplexEntity {
    @FieldName("name") private String _name;
    @FieldName("value") private double _value;

    @GetField("_name")
    public String getName() {
        return _name;
    }

    @SetField("_name")
    public void setName(String name) {
        _name = name;
    }

    @GetField("_value")
    public double getValue() {
        return _value;
    }

    @SetField("_value")
    public void setValue(double value) {
        _value = value;
    }
}
