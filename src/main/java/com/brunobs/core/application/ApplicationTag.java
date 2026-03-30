package com.brunobs.core.application;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "APPLICATIONS_TAG") // tagAplicacao -> APPLICATION_TAG
public class ApplicationTag {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPLICATION_ID", nullable = false)
    private Application application;

    public ApplicationTag() {
        this.id = UUID.randomUUID().toString();
    }


    public ApplicationTag(String name, Application application) {
        this();
        this.name = name;
        this.application = application;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
