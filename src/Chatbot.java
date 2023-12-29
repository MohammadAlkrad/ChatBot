import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Chatbot {
    public static void main(String[] args) throws Exception {
        System.out.println(chatGPT("Hello, who are you?"));
    }

    public static String chatGPT(String message) {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "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";
        String model = "GPT-4 Turbo";

        try {
            // create the HTTP POST request
            URL objUrl = new URL(url);
            HttpURLConnection con = (HttpURLConnection) objUrl.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer" + apiKey);
            con.setRequestProperty("Content-Type", "application/json");

            // build the request body
            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + message
                    + "\"}]}";
            con.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            // get the response
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return extractContent(response.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // to return the response
    public static String extractContent(String response) {
        int start = response.indexOf("content") + 11; // where the content starts off
        int end = response.indexOf("\"", start); // where it ends
        return response.substring(start, end);
    }
}
