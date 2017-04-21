package application.entities.converters;

import javax.persistence.AttributeConverter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by diogo on 4/21/17.
 */
public class DateConverter implements AttributeConverter<String, Date> {
    @Override
    public Date convertToDatabaseColumn(String attribute) {
        DateFormat formatter = new SimpleDateFormat("yy-MM-dd");
        try {
            return formatter.parse(attribute);
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    public String convertToEntityAttribute(Date dbData) {
        return dbData.toString();
    }
}
