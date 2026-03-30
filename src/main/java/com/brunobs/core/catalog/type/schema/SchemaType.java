package com.brunobs.core.catalog.type.schema;

import com.brunobs.core.catalog.common.BaseType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "type_schemas")
public class SchemaType extends BaseType {

    @Column(name = "json_schema", nullable = false, columnDefinition = "TEXT")
    private String jsonSchema;

    public SchemaType() {
        super();
    }

    public String getJsonSchema() {
        return jsonSchema;
    }

    public void setJsonSchema(String jsonSchema) {
        this.jsonSchema = jsonSchema;
    }
}
