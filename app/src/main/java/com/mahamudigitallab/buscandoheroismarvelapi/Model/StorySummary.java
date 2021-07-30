package com.mahamudigitallab.buscandoheroismarvelapi.Model;

import java.io.Serializable;

public class StorySummary implements Serializable {
    private String resourceURI;
    private String name;
    private String type;

    public StorySummary() {
    }

    public StorySummary(String resourceURI, String name, String type) {
        this.resourceURI = resourceURI;
        this.name = name;
        this.type = type;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
