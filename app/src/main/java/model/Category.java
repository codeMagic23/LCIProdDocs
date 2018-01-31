package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinbody on 1/28/2018.
 */

public class Category {

    public String name;
    public int id;
    public int parent;
    public boolean children = true;

    public static List<Category> categoryArrayList = new ArrayList<>();
    private static Category selectedCategory;

    public Category() {
    }

    public static void selectCategory(Category cat) {
        selectedCategory = cat;
    }

    public static Category getSelectedCategory() {
        return selectedCategory;
    }

    public int getID(Category cat) {
        return cat.id;
    }


    /*
        {
    "results": [
        {
            "name": "Axles, Chassis and Suspension",
            "id": "417",
            "parent": "0",
            "children": true
        },
        {
            "name": "Awnings ",
            "id": "418",
            "parent": "0",
            "children": true
        }
    ],
    "menu": {
        "parent": "\/support",
        "name": "Home",
        "parentName": "Support"
    },
    "bottomMenu": {
        "route": {
            "capabilities": "\/capabilities",
            "support": "\/support",
            "videos": "\/videos",
            "up": "\/home",
            "disable": {
                "capabilities": "",
                "support": "",
                "videos":
        }
    },
    "exec": 0.0030560493469238281
}
     */
}
