package com.brunobs.core.publisher;

import com.brunobs.core.catalog.type.publisherscope.PublisherScopeType;
import jakarta.persistence.*;

@Entity
@Table(name = "publishers")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String label;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(name = "json_schema", nullable = false, columnDefinition = "TEXT")
    private String jsonSchema;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "publisher_scope_type_id")
    private PublisherScopeType publisherScope;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private boolean deprecated;

    public Publisher() {}

    // Getters e Setters padronizados...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getJsonSchema() { return jsonSchema; }
    public void setJsonSchema(String jsonSchema) { this.jsonSchema = jsonSchema; }

    public PublisherScopeType getPublisherScope() { return publisherScope; }
    public void setPublisherScope(PublisherScopeType publisherScope) { this.publisherScope = publisherScope; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public boolean isDeprecated() { return deprecated; }
    public void setDeprecated(boolean deprecated) { this.deprecated = deprecated; }
}
