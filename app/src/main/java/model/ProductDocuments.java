package model;

import java.util.List;

/**
 * Created by jinbody on 1/31/2018.
 */

public class ProductDocuments {

    public List manuals;
    public List components;
    public List assemblies;

    // from results array
    public String name;
    public int parent;

    public ProductDocuments() {}
}



/*
Ex.

{
    "manuals": [
        {
            "MIGX_id": "",
            "title": "Schwintek Bunk Lift Installation Manual",
            "pdf": "https:\/\/www.lci1.com\/assets\/content\/support\/manuals\/Power and Motion\/Schwintek_Bunk_Lift_OEM_Installation_Manual1.pdf",
            "image": "https:\/\/www.lci1.com\/assets\/content\/support\/"
        },
        {
            "MIGX_id": "",
            "title": "Schwintek Bunk Lift Owner's Manual",
            "pdf": "https:\/\/www.lci1.com\/assets\/content\/support\/manuals\/Power and Motion\/Schwintek_Bunk_Lift_Owner_s_Manual.pdf",
            "image": "https:\/\/www.lci1.com\/assets\/content\/support\/"
        }
    ],
    "components": [
        {
            "MIGX_id": "",
            "title": "Schwintek Bunk Lift Components",
            "pdf": "https:\/\/www.lci1.com\/assets\/content\/support\/complists\/Power and Motion\/Schwintek_Bunk_Lift_Component_.pdf",
            "image": "https:\/\/www.lci1.com\/assets\/content\/support\/complist_thumb\/Power and Motion\/Schwintek_Bunk_Lift_Component_.jpg"
        }
    ],
    "assemblies": [
        {
            "MIGX_id": "",
            "title": "Schwintek Bunk Lift Assembly",
            "pdf": "https:\/\/www.lci1.com\/assets\/content\/support\/assemblies\/Power and Motion\/Schwintek_Bunk_Lift_Assembly.pdf",
            "image": "https:\/\/www.lci1.com\/assets\/content\/support\/thumbs\/Power and Motion\/Schwintek_Bunk_Lift_Assembly.jpg"
        }
    ]
    "results": {
        "name": "Schwintek Bunk Lift",
        "parent": 423
    }
 */