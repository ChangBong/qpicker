package com.peopulley.core.base.converter;

import javax.persistence.Column;
import javax.persistence.Convert;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class TestCls {

    static String path = "C:\\workspace\\backend-new-sklmno\\daema-core\\src\\main\\java\\com\\daema\\core";
    static Set<String> set = new HashSet<>();
    static Set<String> set2 = new HashSet<>();

    public static void main(String[] args) {
        //test
        File dir = new File(path);

        try {
            if (dir.isDirectory()) {
                getFileSystem(dir);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        buildUpdateQuery();

    }

    private static void buildUpdateQuery() {
        try {
            for (String s : set) {

                Class cls = Class.forName(s);

                StringBuffer lastQuery = new StringBuffer();
                StringBuffer botQuery = new StringBuffer(" where 1 = 1;");

                for (Annotation annotation : cls.getAnnotations()) {

                    if (annotation.annotationType().getName().contains("annotations.Table")) {

                        StringBuffer topQuery = new StringBuffer("update "
                                + annotation.annotationType().getMethod("appliesTo").invoke(annotation)
                                + " set ");

                        StringBuffer midQuery = new StringBuffer();

                        for (Field field : cls.getDeclaredFields()) {

                            if (field.isAnnotationPresent(Convert.class)) {

                                //System.out.println(field.getDeclaredAnnotation(Convert.class).converter().getName());

                                //set2.add(cls.getSimpleName() + "." + field.getName());
                                set2.add(field.getName() + " " + field.getDeclaredAnnotation(Column.class).name());

                                midQuery.append(field.getDeclaredAnnotation(Column.class).name()
                                        + " = to_base64(aes_encrypt(" + field.getDeclaredAnnotation(Column.class).name()
                                        + ", 'F1582AF85B636C96')), ");
                            }
                        }

                        if (midQuery.length() > 0) {
                            midQuery = new StringBuffer(midQuery.substring(0, midQuery.length() - 2));

                            lastQuery.append("\n\r");
                            lastQuery.append(topQuery.append(midQuery).append(botQuery));

                            topQuery.ensureCapacity(0);
                            midQuery.ensureCapacity(0);
                        }

                    }
                }

                System.out.println(lastQuery.toString());
            }

            set2.forEach(
                    System.out::println
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getFileSystem(File dir) {
        String list[] = dir.list();

        for (int i = 0; i < list.length; i++) {

            File f = new File(dir + File.separator + list[i]);

            if (f.isDirectory()) {
                getFileSystem(f);
            } else {
                if (f.getPath().contains("domain"))
                    set.add(f.getPath().replace("C:\\workspace\\backend-new-sklmno\\daema-core\\src\\main\\java\\", "").replace("\\", ".")
                            .replace(".java", ""));
            }
        }
    }
}
