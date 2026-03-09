/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 *
 * @author pc
 */
public class GoogleOAuthService {
    private final String CLIENT_ID;
    private final String CLIENT_SECRET;
    private final String REDIRECT_URI;

    public GoogleOAuthService(String clientId, String clientSecret, String redirectUri) {
        this.CLIENT_ID = clientId;
        this.CLIENT_SECRET = clientSecret;
        this.REDIRECT_URI = redirectUri;
    }

    public JsonObject getUserInfoFromCode(String code) throws IOException {
        // Bước 1: Lấy access token từ code
        GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                "https://oauth2.googleapis.com/token",
                CLIENT_ID,
                CLIENT_SECRET,
                code,
                REDIRECT_URI
        ).execute();

        String accessToken = tokenResponse.getAccessToken();

        // Bước 2: Gọi API để lấy thông tin user từ access token
        URL url = new URL("https://www.googleapis.com/oauth2/v2/userinfo?access_token=" + accessToken);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder responseStr = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            responseStr.append(inputLine);
        }
        in.close();

        // Bước 3: Parse JSON response
        return JsonParser.parseString(responseStr.toString()).getAsJsonObject();
    }
}
