package com.brunobs.core.configuration;

import com.brunobs.core.publisher.Publisher;
import jakarta.persistence.*;

@Entity
@Table(name = "publisher_configs")
public class PublisherConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @Column(name = "order_index", nullable = false)
    private Integer order;

    @Column(name = "parameters", columnDefinition = "TEXT")
    private String parameters;

    public PublisherConfig() {
    }

    public PublisherConfig(Publisher publisher, Integer order, String parameters) {
        this.publisher = publisher;
        this.order = order;
        this.parameters = parameters;
    }

    public Long getId() {
        return id;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }
}
