/**
 * ServiceNow to Jira Attachment Migration.
 * This is Part 1 : Download the attachment from serviceNow.
 * 
 * With this script you can download the attachment from service now in JIRA attachment directory format.
 * 
 * PreRequest: You need to map Incident Key field in Jira and take the export from Jira with this two column(issueKey,incident_id).
 *             Take the export from serviceNow with incident_id and its sys_id.And finally make three column incident_id,sys_id and issueKey
 */
package servicenow;
import org.apache.commons.codec.binary.Base64;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * @author suresh
 */
public class DownloadAttachmentFromServiceNow {

    
    /*Service Now Instance Details*/
    private static final String baseUrl = "https://xxxxx.service-now.com";
    private static final String username = "xxxxx";
    private static final String password = "xxxxx@1234";
 
    /* Incident Sys_id and Jira issue_key mapped csv file path*/
    static final String csvFile = "/home/xxxx/Development/Jira_Plugin/ServiceNow/Production_Technology_Support/ihd.csv";
    
    /* Attachment download path*/
    private static final String downloadPath = "/home/xxxx/Development/Jira_Plugin/ServiceNow/Production_Technology_Support/";
    
    private static URLConnection setUsernamePassword(URL url) throws IOException {
        URLConnection urlConnection = url.openConnection();
        String authString = username + ":" + password;
        String authStringEnc = new String(Base64.encodeBase64(authString.getBytes()));
        urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
        return urlConnection;
    }
    public static void main(String[] args) throws JSONException {
       // MigrateBitbucket migrateBitbucket = new MigrateBitbucket();
       
       
       String line = "";
       String cvsSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
           boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if(firstLine){
                    firstLine = false;
                    continue;
                }
                String[] incident = line.split(cvsSplitBy);
                 
                System.out.println( " Incident Key:" + incident[0] + "Sys_id:" + incident[1] + "Jira Key:"+incident[2]);
                
                //restInvoker.getRESTResponse("/e7950d7fdb7b5b00819c320a689619ed/file");
                JSONObject jSONObject = getAttachments(incident[1]);
                
                //if incidents has attachments
                JSONArray attachmentJSONArray = jSONObject.getJSONArray("result");
                if(attachmentJSONArray.length()>0){
                    //create directory for incident.
                    String directoryPath = createFilePath(incident[2]);
                    
                    //getAttachmentFiles
                    for (int i = 0; i < attachmentJSONArray.length(); i++) {
                      
                        JSONObject attachment =  attachmentJSONArray.getJSONObject(i);
                        String attachent_sys_id = attachment.getString("sys_id");
                        String fileName = attachment.getString("file_name");
                        
                        //get attachment file bytecode
                         byte[] bytes = getAttachmentFileByteCode(attachent_sys_id);
                         
                         //Check for same file name if found add index in fileName
                        File f = new File(directoryPath + "/" + fileName);
                        if (f.exists()) {
                            fileName = fileName.concat("("+i+")");
                        }
                         Path fPath = Paths.get(directoryPath+"/"+fileName);
                         Files.write(fPath, bytes);
                    }
                }
                
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static JSONObject getAttachments(String incident_sys_id) {
        InputStream is = null;
        try {
            URL url = new URL(baseUrl +"/api/now/v1/attachment?table_name=incident&table_sys_id="+incident_sys_id);
            URLConnection urlConnection = setUsernamePassword(url);
            is = urlConnection.getInputStream();
            String jsonString = IOUtils.toString(is);
            JSONObject JSONResponse = new JSONObject(jsonString);
            
            return JSONResponse;
        } catch (MalformedURLException ex) {
            Logger.getLogger(DownloadAttachmentFromServiceNow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | JSONException e) {
            Logger.getLogger(DownloadAttachmentFromServiceNow.class.getName()).log(Level.SEVERE, null, e);
        }
        finally{
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    Logger.getLogger(DownloadAttachmentFromServiceNow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }
    
    private static String createFilePath(String incidentName) {
        File f = new File(downloadPath+incidentName+"/");
                if(!f.isDirectory()){
                    f.mkdir();
                }
            return f.getAbsolutePath();
    }

    private static byte[] getAttachmentFileByteCode(String attachent_sys_id) {
        
        try {
            URL url = new URL(baseUrl + "/api/now/attachment/" + attachent_sys_id + "/file");
            URLConnection urlConnection = setUsernamePassword(url);
            InputStream is = urlConnection.getInputStream();
            return IOUtils.toByteArray(is);
        } catch (MalformedURLException ex) {
            Logger.getLogger(DownloadAttachmentFromServiceNow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DownloadAttachmentFromServiceNow.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
}
