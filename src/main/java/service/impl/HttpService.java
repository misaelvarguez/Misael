import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class HttpService {



    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(30))
            .build();



    public String sendRequestHttpS(String url, String method, String username, String password, String type, String body, String token) throws IOException {
        HttpResponse<String> response;
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url));
        String finalString;
        try {

            switch (method){
                case "GET":
                    requestBuilder.GET();

                    break;

                case "POST":
                    byte[] sampleData = body.getBytes();
                    requestBuilder
                            .POST(HttpRequest.BodyPublishers
                                    .ofInputStream(() -> new ByteArrayInputStream(sampleData)));

                    break;

                case "PUT":

                    break;

                case "DELETE":

                    break;
            }
            if(token!= null){
                requestBuilder.setHeader("Authorization", token);
            }
            if(type.equals("json")){
                requestBuilder.setHeader("Content-Type","application/json; charset=utf-8");
            }

        }catch (Exception e){
            System.out.println(e);
            System.out.println("Api CAIDA, no se puede conectar");
            return "[{\"status\":\"Error\", \"detail\":{\"id\":\"Null\"}}]";
        }

        int responseCode=0;
        try{
            response = httpClient.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString());

            responseCode = response.statusCode();
        }catch (Exception e){
            System.out.println(e);
            System.out.println("Api CAIDA, no se puede conectar");
            return "{\"status\":\"Error\", \"detail\":{\"id\":\"Null\"}}";
        }


        switch (responseCode){

            case 200:
                finalString=response.body();
                break;

            case 201:


                finalString="{\"status\":\"Created\", \"detail\":\"Not found\"}";
                break;

            case 404:
                System.out.println("POST request not worked");
                finalString="{\"status\":\"Error\", \"detail\":\"Not found\"}";

                break;

            case 500:
                System.out.println("POST request not worked");
                finalString="{\"status\":\"500\", \"detail\":\""+ response.body()+"\"}";
                break;

            default:
                System.out.println("POST request not worked");
                finalString="{\"status\":\"Error\",\"detail\":{\"id\":\"Null\"}}";
                break;
        }

        return finalString;

    }



