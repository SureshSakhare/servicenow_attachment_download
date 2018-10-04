///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package servicenow.bitbucket;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import org.apache.commons.codec.binary.Base64;
//import org.apache.commons.io.IOUtils;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
///**
// *
// * @author suresh
// */
//public class MigrateBitbucket {
//    
//    public final String cloudUserName = "xxxx@yahoo.com";
//    public final String cloudUserPass = "xxxx";
//    
//    public final String serverUserName = "xxxx";
//    public final String serverUserPass = "xxxx";
//    
//    public final String SERVER_BASE_URL = "https://xxxx.xxxx.co.in/rest/api/1.0/";
//
//    public MigrateBitbucket() {
//        main();
//    }  
//
//    private void main() {
//        /*Get all projects from bitbucket
//        * url = https://xxxx.xxxx.co.in/rest/api/1.0/projects?limit=1000
//        */
//        List<String> projectKeys = getProjects();
//        
//        for (String projectKey : projectKeys) {
//            /**
//             * Get repositories from project
//             * url : https://xxxx.xxxx.co.in/rest/api/1.0/projects/HP/repos/
//             */
//            List<String>repoSlugs = getRepos(projectKey);
//            for (String repoSlug : repoSlugs) {
//                /**
//                 * Get pull request from repository
//                 * URL: https://xxxxx.xxxxx.co.in/rest/api/1.0/projects/HP/repos/madan/pull-requests?state=all
//                 */
//                 List<JSONObject> pRequests = new ArrayList<>();
//                pRequests = getPullRequests(pRequests,projectKey,repoSlug);
//                for (JSONObject pRequest : pRequests) {
//                    /**
//                     * Create pull request over the cloud bitbucket.
//                     * URL: https://api.bitbucket.org/2.0/repositories/xxxxx/Test/pullrequests
//                     */
//                    createPullRequestOnCloudInstance(pRequest,cloudUserName,cloudUserPass, repoSlug);
//                }
//            }
//        }
//    }
//
//    private List<String> getProjects() {
//        try {
//            URL url = new URL(SERVER_BASE_URL+"projects?limit=1000");
//            JSONObject projects = makeRestCall(url);
//            List<String> projectKeys = new ArrayList<>();
//            if(projects.getLong("size")>0){
//                JSONArray projectsArray = projects.getJSONArray("values");
//                if(projectsArray.length()>0)
//                 for (int i = 0; i < projectsArray.length(); i++) {
//                    JSONObject project = projectsArray.getJSONObject(i);
//                    projectKeys.add(project.getString("key"));
//                 }
//            }
//            return projectKeys;
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(MigrateBitbucket.class.getName()).log(Level.SEVERE, "Unable to find URL", ex);
//        } catch (IOException ex) { 
//            Logger.getLogger(MigrateBitbucket.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (JSONException ex) {
//            Logger.getLogger(MigrateBitbucket.class.getName()).log(Level.SEVERE, null, ex);
//        }
//      return null;  
//    }
//
//    private JSONObject makeRestCall(URL url) throws IOException, JSONException {
//        URLConnection uRLConnection = setUsernamePassword(url, serverUserName, serverUserPass);
//        InputStream projectInputStream = uRLConnection.getInputStream();
//        String jsonString = IOUtils.toString(projectInputStream);
//        return  new JSONObject(jsonString);
//    }
//
//    private List<String> getRepos(String projectKey) {
//        try {
//            URL url = new URL(SERVER_BASE_URL+"projects/"+projectKey+"/repos?limit=1000");
//            JSONObject repos = makeRestCall(url);
//            List<String> repoKeys = new ArrayList<>();
//            if(repos.getLong("size")>0){
//                JSONArray reposArray = repos.getJSONArray("values");
//                if(reposArray.length()>0)
//                 for (int i = 0; i < reposArray.length(); i++) {
//                    JSONObject repo = reposArray.getJSONObject(i);
//                    repoKeys.add(repo.getString("slug"));
//                 }
//            }
//            return repoKeys;
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(MigrateBitbucket.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(MigrateBitbucket.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (JSONException ex) {
//            Logger.getLogger(MigrateBitbucket.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
//
//    private List<JSONObject> getPullRequests( List<JSONObject> pullRequestBeans,String projectKey, String repoSlug) {
//        try {
//            URL url = new URL(SERVER_BASE_URL+"projects/"+projectKey+"/repos/"+repoSlug+"/pull-requests?state=all&limit=1000");
//            JSONObject pRequests = makeRestCall(url);
//            if(pRequests.getLong("size")>0){
//                JSONArray pRequetsArray = pRequests.getJSONArray("values");
//                if(pRequetsArray.length()>0)
//                 for (int i = 0; i < pRequetsArray.length(); i++) {
//                    JSONObject pullRequest = pRequetsArray.getJSONObject(i);
//                    pullRequestBeans.add(pullRequest);
//                 }
//            }
//            return pullRequestBeans;
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(MigrateBitbucket.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(MigrateBitbucket.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (JSONException ex) {
//            Logger.getLogger(MigrateBitbucket.class.getName()).log(Level.SEVERE, null, ex);
//        }
//     return null;   
//    }
//
//    private URLConnection setUsernamePassword(URL url,String username,String password) throws IOException {
//        URLConnection urlConnection = url.openConnection();
//        String authString = username + ":" + password;
//        String authStringEnc = new String(Base64.encodeBase64(authString.getBytes()));
//        urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
//        return urlConnection;
//    }
//
//    private void createPullRequestOnCloudInstance(JSONObject pRequest, String cloudUserName, String cloudUserPass, String repoSlug) {
//        try {
//            URL url = new URL("https://api.bitbucket.org/2.0/repositories/"+cloudUserName+"/"+repoSlug+"/pullrequests");
//            HttpURLConnection conn = (HttpURLConnection)setUsernamePassword(url, cloudUserName, cloudUserPass);
//            conn.setDoOutput(true);
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Content-Type", "application/json");
//            OutputStream os = conn.getOutputStream();
//            String data = getPullRequestMetaData(pRequest);
//            os.write(data.getBytes());
//            os.flush();
//
//            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
//                throw new RuntimeException("Failed : HTTP error code : "
//                        + conn.getResponseCode());
//            }
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(
//                    (conn.getInputStream())));
//
//            String output;
//            System.out.println("Output from Server .... \n");
//            while ((output = br.readLine()) != null) {
//                System.out.println(output);
//            }
//
//            conn.disconnect();
//            
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(MigrateBitbucket.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(MigrateBitbucket.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    private String getPullRequestMetaData(JSONObject pRequest) {
//        JSONObject data = new JSONObject();
//        /**
//         * 
//         */
//        return data.toString();
//    }
//
//    
//    
//}
