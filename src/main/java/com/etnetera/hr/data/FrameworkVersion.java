package com.etnetera.hr.data;

import javax.persistence.*;

@Entity
public class FrameworkVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @ManyToOne(cascade=CascadeType.ALL)
    private JavaScriptFramework framework;

    public FrameworkVersion() {
    }

    public FrameworkVersion(String name) {
        this.name = name;
    }

    public JavaScriptFramework getFramework() {
        return framework;
    }

    public void setFramework(JavaScriptFramework framework) {
        this.framework = framework;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
