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

    public static final String SUB_CAT_ID = "subCategoryID";

    private static Category selectedCategory;

    // empty constructor
    public Category() {}

    public static void selectCategory(Category cat) {
        selectedCategory = cat;
    }

    public static Category getSelectedCategory() {
        return selectedCategory;
    }

    public int getID(Category cat) {
        return cat.id;
    }

    public boolean isSubCategory(Category cat) {
        return cat.parent != 0;
    }
}
