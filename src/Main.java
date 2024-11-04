import org.json.*;

public class Main {

    static String key = System.getenv("KEY");
    public static void main(String[] args) {
        try {
            Structure structure = new Structure();
            structure.setTitle("struct-test");
            structure.setStrict(true);
            structure.setAllowAdditionalProperties(false);

            String apiKey = key;
            String systemPrompt = "You are a friendly AI helper. Assist the user in the best way possible while sticking to the requested output structure";
            String query = "Follow the output structure. Give at least 5 pets";

            structure.addProperty("name", "string", "Name of the pet owner");
            Structure.Items petItems = structure.addArrayProperty("pets", "List of pets");
            petItems.addProperty("species", "string", "Type of pet");
            petItems.addProperty("name", "string", "Name of the pet");
            petItems.addProperty("age", "number", "Age of the pet");
            petItems.addRequired("species");
            petItems.addRequired("name");

            //Debug: Sending to API
            //System.out.println("A2: " + structure.toJsonSchema().toString());

            OpenAIClient client = new OpenAIClient(apiKey, systemPrompt, query);
            client.setTemperature(1);
            String response = client.makeRequest(structure);

            System.out.println("\nAPI Response:");
            System.out.println(new JSONObject(response).toString(2));

        } catch (Exception ex) {
            System.out.println("There was an error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}