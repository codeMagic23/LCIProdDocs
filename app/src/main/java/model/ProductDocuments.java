package model;

import java.util.ArrayList;

/**
 * Created by jinbody on 1/31/2018.
 */

public class ProductDocuments {

    public ArrayList<ProductDocuments> documentationList;
    public ArrayList<ChildItem> children;

    // from results array - used as page title
    public String name;
    public String title;
    public String type;

    // Types of documentation
    public static final String TYPE_MANUAL = "manuals";
    public static final String TYPE_COMPONENT = "components";
    public static final String TYPE_ASSEMBLY = "assemblies";


    public ProductDocuments() {
        documentationList = new ArrayList<>();
        children = new ArrayList<>();
    }

    public class ChildItem {

        // manuals, components, and assemblies arrays
        public String migxID;
        public String title;
        public String pdf;  // link to pdf
        public String image;    // link to image
        public ChildItem(){}

    }

}