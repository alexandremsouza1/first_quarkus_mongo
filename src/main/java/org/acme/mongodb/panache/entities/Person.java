package org.acme.mongodb.panache.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import java.lang.reflect.Field;


public class Person extends PanacheMongoEntity {
    @JsonProperty
    public String name;
    public String status;
    public int account;

    public Person merge(Person personToSave){
        Field[] fields = personToSave.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                Object value = field.get(personToSave);
                if (value != null) {
                    field.set(this, value);
                }
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException(e);
            }
        }
        return this;
    }
}
