/////*
//// * To change this license header, choose License Headers in Project Properties.
//// * To change this template file, choose Tools | Templates
//// * and open the template in the editor.
//// */
//package servicenow;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLConnection;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import org.apache.commons.codec.binary.Base64;
//import org.apache.commons.io.DirectoryWalker;
//import org.apache.commons.io.IOUtils;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
///**
// *
// * @author Suresh
// */
//public class JiraServerToCloudAttachment {
//
//    private static final String baseUrl = "https://xxxxx.co.in";
//    private static final String username = "xxxxx";
//    private static final String password = "xxxx@321";
//
//    private static URLConnection setUsernamePassword(URL url) throws IOException {
//        URLConnection urlConnection = url.openConnection();
//        String authString = username + ":" + password;
//        String authStringEnc = new String(Base64.encodeBase64(authString.getBytes()));
//        urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
//        return urlConnection;
//    }
//
//    public static void main(String[] args) throws JSONException {
//        // MigrateBitbucket migrateBitbucket = new MigrateBitbucket();
//        /*Reading csv file*/
////        //String csvFile = "/home/addteq/Development/Jira_Plugin/JiraServerToCloudAttachment/server.csv";
////        String line = "";
////        String cvsSplitBy = ",";
//        //try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
////            boolean firstLine = true;
////           // while ((line = br.readLine()) != null) {
////                if (firstLine) {
////                    firstLine = false;
////                    continue;
////                }
////                String[] incident = line.split(cvsSplitBy);
////
////                System.out.println("Jira Key:" + incident[0]);
////
////                //restInvoker.getRESTResponse("/e7950d7fdb7b5b00819c320a689619ed/file");
////                JSONObject jSONObject = JiraServerToCloudAttachment.getAttachments(incident[0]);
////
////                JSONObject fieldsObject = jSONObject.getJSONObject("fields");
////
////                //if incidents has attachments
////                JSONArray attachmentJSONArray = fieldsObject.getJSONArray("attachment");
////                if (attachmentJSONArray.length() > 0) {
////                    //create directory for incident.
////                    String directoryPath = JiraServerToCloudAttachment.createFilePath(incident[0]);
////
////                    //getAttachmentFiles
////                    for (int i = 0; i < attachmentJSONArray.length(); i++) {
////
////                        JSONObject attachment = attachmentJSONArray.getJSONObject(i);
////                        String attachent_download_url = attachment.getString("content");
////                        String fileName = attachment.getString("filename");
////
////                        //get attachment file bytecode
////                        byte[] bytes = JiraServerToCloudAttachment.getAttachmentFileByteCode(attachent_download_url);
////
////                        //Check for same file name if found add index in fileName
////                        File f = new File(directoryPath + "/" + fileName);
////                        if (f.exists()) {
////                            fileName = fileName.concat("(" + i + ")");
////                        }
////                        Path fPath = Paths.get(directoryPath + "/" + fileName);
////                        Path filePath = Files.write(fPath, bytes);
////                        /*Move to git directory*/
////        Runtime ChangeDirrt = Runtime.getRuntime();
////        Process ChangeDir;
//        try {
//            //ChangeDir = ChangeDirrt.exec("cd ~/Development/Bitbucket/UserMapping/wseworld/");
////            ProcessBuilder processBuilder = new ProcessBuilder("cd", "~/Development/Bitbucket/UserMapping/wseworld/");
////            processBuilder.directory(new File("home/Development/Bitbucket/UserMapping/wseworld"));
////            int ChangeDirexitVal = ChangeDir.waitFor();
////            System.out.println("Exited with error code " + ChangeDirexitVal);
//
////            Runtime gitCommitsForUser = Runtime.getRuntime();
//            //Process commitsProcess = gitCommitsForUser.exec("git log --author=ashokkumar_irtt@outlook.com");
//               ProcessBuilder processBuilder = new ProcessBuilder("git","log", "--author=ashokkumar_irtt@outlook.com");
//               File fileDir = new File("/home/Development/Bitbucket/UserMapping/wseworld/");
//               processBuilder.directory(fileDir.getAbsoluteFile());
////                        ProcessBuilder processBuilder = new ProcessBuilder("curl","-D-", "-u", "sbaddteq@gmail.com:addteq123", "-X","POST","-H", "\"X-Atlassian-Token: no-check\"","-F", "\"file=@"+csvFile+"\"", "https://wallstreetenglishtest.atlassian.net/rest/api/2/issue/CCNS-629/attachments");
////
//                        processBuilder.redirectErrorStream(true);
//                        List<String> commands = processBuilder.command();
//                        String commandPrinted="";
//                        for (String command : commands) {
//                            commandPrinted+=command;
//                        }
//                        System.out.println("Curl Command"+commandPrinted);
//                        Process pr = processBuilder.start();
//            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
//
//            String cmdline = null;
//
//            while ((cmdline = input.readLine()) != null) {
//                System.out.println(cmdline);
//            }
//
//            int exitVal = pr.waitFor();
//            System.out.println("Exited with error code " + exitVal);
////                    }
////                }
//
////            }
//        } catch (IOException ex) {
//            Logger.getLogger(JiraServerToCloudAttachment.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(JiraServerToCloudAttachment.class.getName()).log(Level.SEVERE, null, ex);
//        }
////
////        } catch (IOException e) {
////            e.printStackTrace();
////        } catch (InterruptedException ex) {
////            Logger.getLogger(JiraServerToCloudAttachment.class.getName()).log(Level.SEVERE, null, ex);
////        }
//
//    }
//
//    private static JSONObject getAttachments(String issueKey) {
//        try {
//            URL url = new URL(baseUrl + "/rest/api/2/issue/" + issueKey);
//            URLConnection urlConnection = setUsernamePassword(url);
//            InputStream is = urlConnection.getInputStream();
//            String jsonString = IOUtils.toString(is);
//            JSONObject JSONResponse = new JSONObject(jsonString);
//            return JSONResponse;
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(JiraServerToCloudAttachment.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(JiraServerToCloudAttachment.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (JSONException ex) {
//            Logger.getLogger(JiraServerToCloudAttachment.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
//
//    private static String createFilePath(String issueKey) {
//        File f = new File("/home/addteq/Development/Jira_Plugin/JiraServerToCloudAttachment/ITIL/" + issueKey + "/");
//        if (!f.isDirectory()) {
//            f.mkdir();
//        }
//        return f.getAbsolutePath();
//    }
//
//    private static byte[] getAttachmentFileByteCode(String attachent_download_url) {
//
//        try {
//            URL url = new URL(attachent_download_url);
//            URLConnection urlConnection = setUsernamePassword(url);
//            InputStream is = urlConnection.getInputStream();
//            return IOUtils.toByteArray(is);
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(JiraServerToCloudAttachment.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(JiraServerToCloudAttachment.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
////    public boolean addAttachmentToIssue(String issueKey, String fullfilename) throws IOException{
////
////		CloseableHttpClient httpclient = HttpClients.createDefault();
////		
////		HttpPost httppost = new HttpPost(jira_attachment_baseURL+"/api/latest/issue/"+issueKey+"/attachments");
////		httppost.setHeader("X-Atlassian-Token", "nocheck");
////		httppost.setHeader("Authorization", "Basic "+jira_attachment_authentication);
////		
////		File fileToUpload = new File(fullfilename);
////		FileBody fileBody = new FileBody(fileToUpload);
////		
////		HttpEntity entity = MultipartEntityBuilder.create()
////				.addPart("file", fileBody)
////				.build();
////		
////		httppost.setEntity(entity);
////        String mess = "executing request " + httppost.getRequestLine();
////        logger.info(mess);
////        
////        CloseableHttpResponse response;
////		
////        try {
////			response = httpclient.execute(httppost);
////		} finally {
////			httpclient.close();
////		}
////        
////		if(response.getStatusLine().getStatusCode() == 200)
////			return true;
////		else
////			return false;
////
////	}
//}
