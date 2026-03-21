package com.brunobs.core.catalog.type.schema;

import com.brunobs.core.catalog.common.BaseType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "schema_types") // Plural e inglês
public class SchemaType extends BaseType {

    @Column(name = "json_schema", nullable = false, columnDefinition = "TEXT")
    private String jsonSchema; // Traduzido de 'schema' para evitar conflito com palavra reservada

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
