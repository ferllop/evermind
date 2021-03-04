package com.ferllop.evermind.db;

import com.ferllop.evermind.models.Model;

public class ModelDao {
    String id;
    Model model;

    public ModelDao(String id, Model model) {
        this.id = id;
        this.model = model;
    }

    public String getId() {
        return id;
    }

    public Model getModel() {
        return model;
    }

}
