package Server;

import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
@RestController
public class GreenhouseController {

    GreenhouseDBDAO db = new GreenhouseDBDAO();



    @RequestMapping("/measures/{type}")
    public String getMeasuresByType(@PathVariable String type){
        if(type.equals("all")) {
            String res = "Measures: \n";
            Greenhouse measure = db.getMeasureNow();
            return res += "temperature: " + measure.getTemperature() + "C humidity: " + measure.getHumidity() + "% luminsotiry: " + measure.getLuminosity() + "% time " + measure.getMeasureTime() + "\n";
        }
        else if(type.equals("temperature")){
            String res = "Temperature now: \n";
            Greenhouse measure = db.getMeasureNow();
            return res += "temperature: " + measure.getTemperature()+"C time " + measure.getMeasureTime();

        }
        else if(type.equals("humidity")){
            String res = "Humidity now: \n";
            Greenhouse measure = db.getMeasureNow();
            return res += "humidity: " + measure.getHumidity()+"% time " + measure.getMeasureTime();

        }
        else if(type.equals("luminosity")){
            String res = "Luminosity now: \n";
            Greenhouse measure = db.getMeasureNow();
            return res += "Luminosity: " + measure.getLuminosity()+"% time " + measure.getMeasureTime();
        }
        return "hej";
    }

    @RequestMapping("/rapports/{type}")
    public String getRapportsByType(@PathVariable String type){
        if(type.equals("temperature")) {
            String res = "Temperatur rapport for last week: \n";
            for (Greenhouse measure : db.getRapport()){
                res += "temperature: " + measure.getTemperature()+"C time " + measure.getMeasureTime()+ "\n";
            }
            float avg = db.getAVG("temperature");
            res += "Average temperature for last week: "+avg;
            return res;
        }
        else if(type.equals("humidity")){
            String res = "Humidity rapport for last week: \n";
            for (Greenhouse measure : db.getRapport()){
                res += "Humidity: " + measure.getHumidity()+"% time " + measure.getMeasureTime()+ "\n";
            }
            float avg = db.getAVG("humidity");
            res += "Average humidity for last week: "+avg;
            return res;
        }
        else if(type.equals("luminosity")){
            String res = "Luminosity rapport for last week: \n";
            for (Greenhouse measure : db.getRapport()){
                res += "Luminosity: " + measure.getLuminosity()+"% time " + measure.getMeasureTime()+ "\n";
            }
            float avg = db.getAVG("luminosity");
            res += "Average luminosity for last week: "+avg;
            return res;
        }
        return " ";
    }



    @PostMapping("/addMeasure")
    public String addMeasure(@RequestBody Greenhouse measure){
        String res = " ";
        db.addMeasure(measure.getTemperature(), measure.getHumidity(), measure.getLuminosity(), measure.getElectricityConsumption());
        return res;
    }

    @PostMapping("/update")
    public String updateAll(@RequestBody Greenhouse measure){
        String res = " ";
        db.updateAll(measure.getTemperature(), measure.getHumidity(), measure.getLuminosity(), measure.getElectricityConsumption());
        return res;
    }

    @RequestMapping("/cost/{type}/{cost}")
    public String getCost(@PathVariable String type, @PathVariable String cost) {
        String res = "";
        if (type.equals("toDay")) {
            Greenhouse measure = db.getMeasureNow();
            return res += "Electricity Consumption last day: " + measure.getElectricityConsumption()+" time " + measure.getMeasureTime() + "\n";
        } else if (type.equals("week")) {
            float costForTheWeek = db.getCost(Integer.parseInt(cost));
            Greenhouse measure = db.getMeasureNow();
            return res += "Total cost for the week: " + costForTheWeek;
        }
        return " ";
    }

}

