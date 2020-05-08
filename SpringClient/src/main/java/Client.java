import jdk.jshell.execution.LoaderDelegate;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Client {

    private static String getMeasures(String type){
        final String uri = "http://localhost:8080/measures/{type}";
        Map<String, String> params = new HashMap<String, String>();
        params.put("type",type);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class, params);
        System.out.println(result);
        return result;
    }

    private static String getRapports(String type){
        final String uri = "http://localhost:8080/rapports/{type}";
        Map<String, String> params = new HashMap<String, String >();
        params.put("type",type);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class, params);
        System.out.println(result);
        return result;
    }


    private static String getCost(String type, String cost){
        final String uri = "http://localhost:8080/cost/{type}/{cost}";
        Map<String, String> params = new HashMap<String, String>();
        params.put("type",type);
        params.put("cost",cost);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class, params);
        System.out.println(result);
        return result;
    }

    private static String update(float temperature, float humidity, float luminosity, float electricityConsumption){
        final String uri = "http://localhost:8080/update";
        Greenhouse measure = new Greenhouse(0, temperature, humidity, luminosity, electricityConsumption);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject(uri, measure, String.class);
        System.out.println(result);
        return result;
    }

    private static String addMeasure(float temperature, float humidity, float luminosity, float electricityConsumption){
        System.out.println(temperature+humidity+luminosity+electricityConsumption);
        final String uri = "http://localhost:8080/addMeasure";
        Greenhouse measure = new Greenhouse(0, temperature, humidity, luminosity, electricityConsumption);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject(uri, measure, String.class);
        System.out.println(result);
        return result;
    }

    private static void Menu(){
        boolean exit = false;
        do {
            System.out.println("GREENHOUSE \n1. Show measures.\n2. Update measures\n3. Rapports\n4. Get cost\n5. Add measure\n6. Exit");
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    MeasuresMenu();
                    break;
                case 2:
                    float temperature= (float) 0.0, humidity = (float) 0.0, luminosity = (float) 0.0, electricityConsumption = (float) 0.0;
                    System.out.println("New temperature: ");
                    if(sc.hasNextFloat()) {
                        temperature = sc.nextFloat();
                    }
                    System.out.println("Humidity: ");
                    if(sc.hasNextFloat()) {
                        humidity = sc.nextFloat();
                    }
                    System.out.println("Luminosity: ");
                    if(sc.hasNextFloat()) {
                        luminosity = sc.nextFloat();
                    }
                    System.out.println("Electricity consumption: ");
                    if(sc.hasNextFloat()) {
                        electricityConsumption = sc.nextFloat();
                    }

                    update(temperature,humidity,luminosity,electricityConsumption);
                    //UpdateMenu();
                    break;
                case 3:
                    RapportsMenu();
                    break;
                case 4:
                    CostMenu();
                    break;
                case 5:
                    float temperatureUpdate= (float) 0.0, humidityUpdate = (float) 0.0, luminosityUpdate = (float) 0.0, electricityConsumptionUpdate = (float) 0.0;
                    System.out.println("New temperature: ");
                    if(sc.hasNextFloat()) {
                        temperatureUpdate = sc.nextFloat();
                    }
                    System.out.println("Humidity: ");
                    if(sc.hasNextFloat()) {
                        humidityUpdate = sc.nextFloat();
                    }
                    System.out.println("Luminosity: ");
                    if(sc.hasNextFloat()) {
                        luminosityUpdate = sc.nextFloat();
                    }
                    System.out.println("Electricity consumption: ");
                    if(sc.hasNextFloat()) {
                        electricityConsumptionUpdate = sc.nextFloat();
                    }

                    addMeasure(temperatureUpdate,humidityUpdate,luminosityUpdate,electricityConsumptionUpdate);
                    break;
                case 6:
                exit = true;
                break;
            }
        } while (!exit);
    }

    private static void MeasuresMenu(){
        boolean exit = false;
        do {
            System.out.println("MEASURES \n1. Show measures for all.\n2. Show measures for temperature\n3. Show measures for humidity\n4. Show measures for luminosity\n5. Back");
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    getMeasures("all");
                    break;
                case 2:
                    getMeasures("temperature");
                    break;
                case 3:
                    getMeasures("humidity");
                    break;
                case 4:
                    getMeasures("luminosity");
                    break;
                case 5:
                    exit = true;
                    break;
            }
        } while (!exit);
    }


    private static void RapportsMenu(){
        boolean exit = false;
        do {
        System.out.println("RAPPORTS \n1. Rapport for temperature.\n2. Rapport for humidity\n3. Rapport for luminosity\n4. Back");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                getRapports("temperature");
                break;
            case 2:
                getRapports("humidity");
                break;
            case 3:
                getRapports("luminosity");
                break;
            case 4:
                exit = true;
                break;
        }
    } while (!exit);
}

    private static void CostMenu(){
        boolean exit = false;
        do {
            System.out.println("COST \n1. Electricity consumption last day.\n2. Electricity cost last week\n3. Back");
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            String cost = "0";
            switch (choice) {
                case 1:
                    getCost("toDay", cost);
                    break;
                case 2:
                    System.out.println("Cost: ");
                    if(sc.hasNext()) {
                        cost = sc.next();
                    }
                    getCost("week",cost);
                    break;
                case 3:
                    exit = true;
                    break;
            }
        } while (!exit);

    }

    public static void main(String[] args){
        Menu();
    }
}
