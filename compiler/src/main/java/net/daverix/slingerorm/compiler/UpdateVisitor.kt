package net.daverix.slingerorm.compiler

import com.squareup.javapoet.MethodSpec
import net.daverix.slingerorm.entity.DatabaseEntity
import net.daverix.slingerorm.storage.Update
import javax.lang.model.element.TypeElement


class UpdateVisitor(private val typeElement: TypeElement,
                    private val dbEntities: DatabaseEntityModelMap) : DatabaseStorageBuilderVisitor {
    override fun visit(builder: DatabaseStorageBuilder) {
        val updateMethods = typeElement.directMethods
                .filter { it.isAnnotatedWith<Update>() }
                .toList()

        builder.methods += updateMethods.map {
            if(it.parameters.size > 1)
                throw InvalidElementException("Only one parameter supported for Update methods", it)

            if(it.parameters.size == 0)
                throw InvalidElementException("Update methods must have one parameter which is the entity to update", it)

            val firstParameter = it.parameters.first()
            val databaseEntityElement = firstParameter.asTypeElement()
            if(!databaseEntityElement.isAnnotatedWith<DatabaseEntity>())
                throw InvalidElementException("The type of parameter ${firstParameter.simpleName} must be annotated with @DatabaseEntity", firstParameter)

            val model = dbEntities[databaseEntityElement]

            MethodSpec.overriding(it)
                    .apply {
                        addCode("db.edit(\"${model.tableName}\")\n")
                        for(getter in model.getGetters(firstParameter.simpleName.toString())) {
                            addCode("  .put($getter)\n")
                        }
                        addCode("  .update(\"${model.itemSql}\", new String[] {\n" +
                                model.getItemSqlArgs(firstParameter.simpleName.toString()).joinToString(","){"    $it\n"} +
                                "  });\n")
                    }
                    .build()
        }.toList()
    }
}