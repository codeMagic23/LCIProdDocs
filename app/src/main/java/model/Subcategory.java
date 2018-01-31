package model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jinbody on 1/30/2018.
 */

public class Subcategory {

    public String name;
    public int id;
    public int parent;
    public boolean children = true;

    public static ArrayList<Subcategory> subcategoryList = new ArrayList();

    public Subcategory() {

    }
}


/*
 Ex.
{
        "results": [
        {
        "name": "Schwintek Bed Tilt ",
        "id": "467",
        "parent": "423",
        "children": false
        },
        {
        "name": "Schwintek Murphy Bed Lift",
        "id": "468",
        "parent": "423",
        "children": false
        }
        ],
        "menu": {
        "parent": "\/support",
        "name": "Power and Motion ",
        "parentName": "Support"
        },
        "bottomMenu": {
        "route": {
        "capabilities": "\/product\/423\/20\/20",
        "support": "\/support\/423\/20\/20",
        "videos": "\/video\/423\/20\/20",
        "up": "\/home",
        "disable": {
        "capabilities": "",
        "support": "",
        "videos": ""
        }
        }
        },
        "exec": 0.0043752193450927734
        }
*/