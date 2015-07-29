package net.daverix.slingerorm.compiler;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collection;

public class SelectMultipleMethod implements StorageMethod {
    private final String methodName;
    private final String entityName;
    private final String returnTypeName;
    private final String parameterText;
    private final String where;
    private final Collection<String> parameterGetters;
    private final String orderBy;
    private final MapperDescription mapperDescription;

    public SelectMultipleMethod(String methodName,
                                String entityName,
                                String returnTypeName,
                                String parameterText,
                                String where,
                                Collection<String> parameterGetters,
                                String orderBy,
                                MapperDescription mapperDescription) {
        this.methodName = methodName;
        this.entityName = entityName;
        this.returnTypeName = returnTypeName;
        this.parameterText = parameterText;
        this.where = where;
        this.parameterGetters = parameterGetters;
        this.orderBy = orderBy;
        this.mapperDescription = mapperDescription;
    }

    @Override
    public void write(Writer writer) throws IOException {
        String args = createArguments();
        String orderByText = createOrderBy();

        writer.write("    @Override\n");
        writer.write("    public " + returnTypeName + " " + methodName + "(" + parameterText + ") {\n");
        writer.write("        Cursor cursor = null;\n");
        writer.write("        try {\n");
        writer.write("            cursor = db.query(false, " + mapperDescription.getVariableName() + ".getTableName(), " + mapperDescription.getVariableName() + ".getFieldNames(), \"" + where + "\", " + args + ", null, null, " + orderByText + ", null);\n");
        writer.write("            " + returnTypeName + " items = new ArrayList<" + entityName + ">();\n");
        writer.write("            if(!cursor.moveToFirst()) return items;\n");
        writer.write("            \n");
        writer.write("            do {\n");
        writer.write("              " + entityName + " item = new " + entityName + "();\n");
        writer.write("              " + mapperDescription.getVariableName() + ".mapItem(cursor, item);\n");
        writer.write("            } while(cursor.moveToNext());\n");
        writer.write("            return items;\n");
        writer.write("        } finally {\n");
        writer.write("            if(cursor != null) cursor.close();\n");
        writer.write("        }\n");
        writer.write("    }\n");
        writer.write("\n");
    }

    private String createOrderBy() {
        if(orderBy == null) return "null";

        return "\"" + orderBy + "\"";
    }

    private String createArguments() {
        return "new String[]{}";
    }

    @Override
    public Collection<String> getImports() {
        return Arrays.asList(
                "android.database.Cursor",
                "android.database.sqlite.SQLiteDatabase",
                "java.util.List",
                "java.util.ArrayList"
        );
    }

    @Override
    public MapperDescription getMapper() {
        return mapperDescription;
    }
}
