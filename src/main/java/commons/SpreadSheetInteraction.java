package commons;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SpreadSheetInteraction {
    public final String APPLICATION_NAME = "Request Verification Now group";
    public final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    public final String TOKENS_DIRECTORY_PATH = "tokens";
    public NetHttpTransport HTTP_TRANSPORT = null;
    public Sheets service;
    public String valueInputOption = "USER_ENTERED";
    public int lastRow, rangeLastRows;
    public ValueRange response;

    public final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);

    public SpreadSheetInteraction() {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredential(HTTP_TRANSPORT)).setApplicationName(APPLICATION_NAME).build();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function to create an authorized credential object
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object
     * @throw s IOException If the credentials.json file can not be found
     */
    public Credential getCredential(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        //Load client secrets
        InputStream in = SpreadSheetInteraction.class.getResourceAsStream(Constants.CREDENTIAL_FILE_PATH);
        if (in == null) {
            try {
                throw new FileNotFoundException("Credentials file not found: " + Constants.CREDENTIAL_FILE_PATH);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        GoogleClientSecrets clientSecrets = null;
        try {
            clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Build flow and trigger user authorization request
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public void appendDataToSpreadSheet(String id, String status, String comment) {
        List<List<Object>> values = Arrays.asList(
                Arrays.asList(id, status, comment)
        );
        ValueRange body = new ValueRange().setValues(values);
        if(!isIDExistedWithinLast20Rows(id)) {
            try {
                AppendValuesResponse result = service.spreadsheets().values().append(Constants.SPREADSHEET_ID, Constants.RANGE, body).setValueInputOption(valueInputOption).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("The ID just inputted before");
        }
    }

    public boolean isIDExistedWithinLast20Rows(String ids) {
        rangeLastRows = getLastRow() - 20;
        try {
            response = service.spreadsheets().values().get(Constants.SPREADSHEET_ID, String.valueOf(rangeLastRows)).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<List<Object>> lastValues = response.getValues();
        List<String> lastValuesToCheck = new ArrayList<>();
        if (lastValues == null || lastValues.isEmpty()) {
            System.out.println("No data found!");
        } else {
            System.out.println("Data:");
            for (List row : lastValues) {
                lastValuesToCheck.add((String) row.get(0));
            }
        }
        boolean isExisted;
        if(lastValuesToCheck.contains(ids)){
            isExisted = true;
        } else{
            isExisted = false;
        }
        return isExisted;
    }

    public int getLastRow(){
        try {
            response = service.spreadsheets().values().get(Constants.SPREADSHEET_ID, Constants.RANGE).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            lastRow = service.spreadsheets().values().get(Constants.SPREADSHEET_ID, Constants.RANGE).execute().getValues().size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lastRow;
    }

    public void getDataFromSheet(){
        //Build a new authorized API client service
        try {
            response = service.spreadsheets().values()
                    .get(Constants.SPREADSHEET_ID, Constants.RANGE)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            System.out.println("No data found!");
        } else {
            System.out.println("Data:");
            for (List row : values) {
                System.out.println(row.get(0) + " | " + row.get(2));
            }
        }
    }
}
