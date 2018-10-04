/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package servicenow;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLConnection;
//import org.apache.commons.codec.binary.Base64;

/*public class PostRestCall {

    // http://localhost:8080/RESTfulExample/json/product/post
    public static void main(String[] args) {

        try {

            URL url = new URL("https://api.bitbucket.org/2.0/repositories/xxxxx/test/pullrequests");
            //URLConnection URL = setUsernamePassword("", "xxxxx@yahoo.com", "xxxxx");
            HttpURLConnection conn = setUsernamePassword(url, "sasakhare@yahoo.com", "suresh2702");
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = "{  \n" +
"   \"title\":\"Testing pull request created from java api call \",\n" +
"   \"close_source_branch\":true,\n" +
"   \"destination\":{  \n" +
"      \"repository\":{  \n" +
"         \"type\":\"repository\",\n" +
"         \"name\":\"test\"\n" +
"      },\n" +
"      \"branch\":{  \n" +
"         \"name\":\"master\"\n" +
"      }\n" +
"   },\n" +
"   \"state\":\"OPEN\",\n" +
"   \"source\":{  \n" +
"      \"repository\":{  \n" +
"         \"type\":\"repository\",\n" +
"         \"name\":\"test\"\n" +
"      },\n" +
"      \"branch\":{  \n" +
"         \"name\":\"PLUG-6\"\n" +
"      }\n" +
"   },\n" +
"   \"reviewers\":[  \n" +
"      {  \n" +
"         \"username\":\"anujsapate\"\n" +
"      }\n" +
"   ]\n" +
"}";

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    private static HttpURLConnection setUsernamePassword(URL url, String username, String password) throws IOException {
        URLConnection urlConnection = url.openConnection();
        String authString = username + ":" + password;
        String authStringEnc = new String(Base64.encodeBase64(authString.getBytes()));
        urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
        return (HttpURLConnection) urlConnection;
    }

}
*/