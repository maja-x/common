package id.maja.xcore.serializer;

import id.maja.xcore.annotation.IsoDataElement;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class IsoDataSerializer {
    private final List<IsoDataElement> isoDataElement;
    private final Map<Integer, String> isoDataElementMap; // <fieldName, IsoDataElement>
    private final Class<?> clazz;

    public IsoDataSerializer(Builder builder) {
        this.isoDataElement = builder.isoDataElement;
        this.isoDataElementMap = builder.isoDataElementMap;
        this.clazz = builder.clazz;
    }

    /**
     * Untuk melakukan serialisasi data dari Object dengan annotation IsoDataElement
     * diubah menjadi String data ISO8583 (ini yg akan dikirim ke Bank)
     *
     * @param data The object containing fields annotated with IsoDataElement to be serialized.
     * @return A string representation of the serialized data.
     */
    public String serialize(Object data) {
        StringBuilder res = new StringBuilder();
        for (IsoDataElement isoDataElement : this.isoDataElement) {
            String fieldName = isoDataElementMap.get(isoDataElement.position());
            String value = getValue(data, fieldName);
            if (value != null) {
                if (isoDataElement.padded() && isoDataElement.paddingChar().equals(" ")) {
                    res.append(padLeft(value, isoDataElement.length()));
                } else if (isoDataElement.padded() && isoDataElement.paddingChar().equals("0")) {
                    res.append(padRight(value, isoDataElement.length()));
                } else {
                    res.append(value);
                }
            }
        }
        return res.toString();
    }

    /**
     * Ini untuk deserialize isoMsg Data Bit48
     * @param data String ISOMsg
     * @return Object IsoData
     */
    public Object deserialize(String data) {
        try {
            Object instance = this.clazz.getDeclaredConstructor().newInstance();
            int index = 0;
            for (IsoDataElement isoDataElement : this.isoDataElement) {
                String fieldName = isoDataElementMap.get(isoDataElement.position());
                String value = data.substring(index, isoDataElement.length() + index);

                PropertyAccessor myAccessor = PropertyAccessorFactory.forBeanPropertyAccess(instance);
                myAccessor.setPropertyValue(fieldName, value);
                index += isoDataElement.length();
            }
            return instance;
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            return null;
        }
    }

    /**
     * Menambahkan karakter " " (spasi) di belakang string
     *
     * @param value  the value to pad
     * @param length the desired length of the padded string
     * @return the padded string
     */
    private String padLeft(String value, int length) {
        if (value.length() >= length) {
            return value.substring(0, length);
        }
        return String.format("%-" + length + "s", value).replace(" ", String.valueOf(' '));
    }

    /**
     * Menambahkan karakter "0" (nol) di depan string
     *
     * @param value  the value to pad
     * @param length the desired length of the padded string
     * @return the padded string
     */
    private String padRight(String value, int length) {
        if (value.length() >= length) {
            return value.substring(0, length);
        }
        return String.format("%" + length + "s", value).replace(" ", String.valueOf('0'));
    }

    /**
     * Mengambil nilai dari object data dengan nama field yang sesuai
     *
     * @param data    object yang akan diambil nilainya
     * @param fieldName nama field yang akan diambil nilainya
     * @return nilai dari field yang sesuai, jika tidak ada maka akan dikembalikan kosong
     */
    public String getValue(Object data, String fieldName) {
        try {
            Object value = data.getClass()
                    .getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1))
                    .invoke(data);
            if (value == null) {
                return "";
            }
            return String.valueOf(value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return "";
        }
    }

    public static class Builder {
        public List<IsoDataElement> isoDataElement = new ArrayList<>();
        public Class<?> clazz;
        public Map<Integer, String> isoDataElementMap = new HashMap<>();

        public Builder(Class<?> clazz) {
            this.clazz = clazz;
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(IsoDataElement.class)) {
                    IsoDataElement isoField = field.getAnnotation(IsoDataElement.class);
                    this.isoDataElement.add(isoField);
                    isoDataElementMap.put(isoField.position(), field.getName());
                }
            }
            // Urutkan berdasarkan position
            this.isoDataElement.sort(Comparator.comparingInt(IsoDataElement::position));
        }
        public IsoDataSerializer build() {
            return new IsoDataSerializer(this);
        }
    }
}
