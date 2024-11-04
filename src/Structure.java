import org.json.*;
import java.util.*;

public class Structure {
    private String title;
    private ArrayList<Property> properties = new ArrayList<Property>();
    private ArrayList<String> requiredProperties = new ArrayList<String>();
    private boolean isStrict;
    private boolean allowAdditionalProperties;

    public static class Property {
        private String name;
        private String type;
        private String description;
        private Items items;

        public Property(String name, String type, String description) {
            this.name = name; //1-2 words
            this.type = type; //Either 'string', 'number', or 'array'
            this.description = description;
        }
        public Property(String name, String description, Items items) {
            this.name = name;
            this.type = "array";
            this.description = description;
            this.items = items;
        }

        public JSONObject toJson() {
            JSONObject json = new JSONObject();
            json.put("type", type);
            if (description != null && !description.isEmpty()) {
                json.put("description", description);
            }

            // Include items schema for arrays
            if ("array".equals(type) && items != null) {
                json.put("items", items.toJson());
            }

            return json;
        }
    }
    public static class Items {
        private String type;
        private Map<String, Property> properties;
        private ArrayList<String> required;

        public Items(String type) {
            this.type = type;
            this.properties = new HashMap<>();
            this.required = new ArrayList<>();
        }

        public void addProperty(String name, String type, String description) {
            properties.put(name, new Property(name, type, description));
        }

        public void addRequired(String propertyName) {
            required.add(propertyName);
        }

        public JSONObject toJson() {
            JSONObject json = new JSONObject();
            json.put("type", type);

            if (!properties.isEmpty()) {
                JSONObject propsJson = new JSONObject();
                for (Map.Entry<String, Property> entry : properties.entrySet()) {
                    propsJson.put(entry.getKey(), entry.getValue().toJson());
                }
                json.put("properties", propsJson);
            }

            if (!required.isEmpty()) {
                json.put("required", new JSONArray(required));
            }

            return json;
        }
    }

    public JSONObject toJsonSchema() {
        JSONObject schema = new JSONObject();
        schema.put("type", "object");

        JSONObject propertiesJson = new JSONObject();
        for (Property prop : properties) {
            propertiesJson.put(prop.name, prop.toJson());
        }

        schema.put("properties", propertiesJson);

        if (!requiredProperties.isEmpty()) {
            schema.put("required", new JSONArray(requiredProperties));
        }

        schema.put("additionalProperties", allowAdditionalProperties);

        return schema;
    }

    // Method to get properly escaped JSON string
    public String toJsonString() {
        return this.toJsonSchema().toString()
                .replace("\n", "")
                .replace("\r", "");
    }

    //Getters and setters
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public ArrayList<Property> getProperties() {
        return properties;
    }
    public Items addArrayProperty(String name, String description) {
        Items items = new Items("object");
        properties.add(new Property(name, description, items));
        return items;
    }
    public void addProperty(String name, String type, String description) {
        this.properties.add(new Property(name, type, description));
        if(this.isStrict)
        {
            this.addRequiredProperties(name);
        }
    }
    public ArrayList<String> getRequiredProperties() {
        return requiredProperties;
    }
    public void addRequiredProperties(String requiredProperty) {
        this.requiredProperties.add(requiredProperty);
    }
    public boolean isStrict() {
        return isStrict;
    }
    public void setStrict(boolean isStrict) {
        this.isStrict = isStrict;
    }
    public boolean isAllowAdditionalProperties() {
        return allowAdditionalProperties;
    }
    public void setAllowAdditionalProperties(boolean allowAdditionalProperties) {
        this.allowAdditionalProperties = allowAdditionalProperties;
    }
}