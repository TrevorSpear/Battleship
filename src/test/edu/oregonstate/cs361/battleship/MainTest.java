package edu.oregonstate.cs361.battleship;

import com.google.gson.Gson;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import spark.Spark;
import spark.utils.IOUtils;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.io.OutputStream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static spark.Spark.awaitInitialization;

/**
 * Created by TrevorSpear on 1/26/17.
 */

class  MainTest {

    @BeforeAll
    public static void beforeClass() {

        Main.main(null);
        awaitInitialization();

    }

    @AfterAll
    public static void afterClass() {
        Spark.stop();
    }

    @Test
    public void testGetModel() {
        TestResponse res = request("GET", "/model/Normal", null);

        BattleshipModelNormal test = new BattleshipModelNormal();
        Gson gson = new Gson();
        String check = gson.toJson(test);

        assertEquals(200, res.status);
        assertEquals(check,res.body);
    }

    @Test
    public void testPlaceBattleshipH() {
        BattleshipModelNormal model = new BattleshipModelNormal();
        Gson gson = new Gson();
        String jason = gson.toJson(model);
        TestResponse res = request("POST", "/placeShip/Normal/battleship/1/1/horizontal/Hard", jason);
        assertEquals(res.status, 200);

    }

    @Test
    public void testPlaceBattleshipV() {
        BattleshipModelNormal model = new BattleshipModelNormal();
        Gson gson = new Gson();
        String jason = gson.toJson(model);
        TestResponse res = request("POST", "/placeShip/Normal/battleShip/1/1/vertical/Hard", jason);
        assertEquals(res.status, 200);

        TestResponse res2 = request("POST", "/placeShip/Normal/AircraftCarrier/2/1/vertical/Hard", jason);
        assertEquals(res2.status, 200);

        TestResponse res3 = request("POST", "/placeShip/Normal/Cruiser/1/1/vertical/Hard", jason);
        assertEquals(res3.status, 200);

        TestResponse res4 = request("POST", "/placeShip/Normal/Submarine/1/1/vertical/Hard", jason);
        assertEquals(res4.status, 200);

        TestResponse res5 = request("POST", "/placeShip/Normal/Destroyer/1/1/vertical/Hard", jason);
        assertEquals(res5.status, 200);
    }

    @Test
    public void testPlaceBattleshipVWrong() {
        BattleshipModelNormal model = new BattleshipModelNormal();
        Gson gson = new Gson();
        String jason = gson.toJson(model);
        TestResponse res = request("POST", "/placeShip/Normal/battleShip/11/1/vertical/Hard", jason);
        assertEquals(res.status, 200);

    }


    @Test
    public void testFireAt() {
        BattleshipModelNormal model = new BattleshipModelNormal();

        model.getPlayerAircraftCarrier().setStart(1,1);
        model.getPlayerAircraftCarrier().setEnd(1,5);
        model.getPlayerBattleship().setStart(2,1);
        model.getPlayerBattleship().setEnd(2,4);
        model.getPlayerCruiser().setStart(3,1);
        model.getPlayerCruiser().setEnd(3,3);
        model.getPlayerDestroyer().setStart(4,1);
        model.getPlayerDestroyer().setEnd(4,2);
        model.getPlayerSubmarine().setStart(5,1);
        model.getPlayerSubmarine().setEnd(5,3);

        model.getComputerAircraftCarrier().setStart(1,1);
        model.getComputerAircraftCarrier().setEnd(1,4);
        model.getComputerBattleship().setStart(2,1);
        model.getComputerBattleship().setEnd(2,4);
        model.getComputerCruiser().setStart(3,1);
        model.getComputerCruiser().setEnd(3,3);
        model.getComputerDestroyer().setStart(4,1);
        model.getComputerDestroyer().setEnd(4,2);
        model.getComputerSubmarine().setStart(5,1);
        model.getComputerSubmarine().setEnd(5,3);

        Gson gson = new Gson();
        String jason = gson.toJson(model);

        TestResponse res1 = request("POST", "/fire/Normal/2/2/Hard", jason);
        assertEquals(res1.status, 200);

        TestResponse res2 = request("POST", "/fire/Normal/4/4/Hard", jason);
        assertEquals(res2.status, 200);

        TestResponse res3 = request("POST", "/fire/Normal/6/6/Hard", jason);
        assertEquals(res3.status, 200);
    }

    @Test
    public void testFireAtFail() {
        BattleshipModelUpdated model = new BattleshipModelUpdated();

        model.getPlayerAircraftCarrier().setStart(1,1);
        model.getPlayerAircraftCarrier().setEnd(1,5);
        model.getPlayerBattleship().setStart(2,1);
        model.getPlayerBattleship().setEnd(2,4);
        model.getPlayerClipper().setStart(3,1);
        model.getPlayerClipper().setEnd(3,3);
        model.getPlayerDinghy().setStart(4,1);
        model.getPlayerDinghy().setEnd(4,1);
        model.getPlayerSubmarine().setStart(5,1);
        model.getPlayerSubmarine().setEnd(5,3);

        model.getComputerAircraftCarrier().setStart(1,1);
        model.getComputerAircraftCarrier().setEnd(1,4);
        model.getComputerBattleship().setStart(2,1);
        model.getComputerBattleship().setEnd(2,4);
        model.getComputerClipper().setStart(3,1);
        model.getComputerClipper().setEnd(3,3);
        model.getComputerDinghy().setStart(4,1);
        model.getComputerDinghy().setEnd(4,1);
        model.getComputerSubmarine().setStart(5,1);
        model.getComputerSubmarine().setEnd(5,3);

        model.getShipByID("Pizza");

        Gson gson = new Gson();
        String jason = gson.toJson(model);

        for(int j = 1;j < 11; j++){
            for(int i = 1;i < 11; i++) {

                TestResponse res = request("POST", "/scan/Updated/" + i + "/" + j + "/Hard", jason);
                assertEquals(res.status, 200);
            }
        }


    }

    private TestResponse request(String method, String path, String body) {
        try {
            URL url = new URL("http://localhost:4567" + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            if(body != null) {
                connection.setDoInput(true);
                byte[] outputInBytes = body.getBytes("UTF-8");
                OutputStream os = connection.getOutputStream();
                os.write(outputInBytes);
            }
            connection.connect();
            body = IOUtils.toString(connection.getInputStream());
            return new TestResponse(connection.getResponseCode(), body);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Sending request failed: " + e.getMessage());
            return null;
        }
    }

    //--------------------------------------------------------------------------
    @Test
    public void testGetUpdatedModel() {
        TestResponse res = request("GET", "/model/Updated", null);

        BattleshipModelUpdated test = new BattleshipModelUpdated();
        Gson gson = new Gson();
        String check = gson.toJson(test);

        assertEquals(200, res.status);
        assertEquals(check,res.body);
    }

    @Test
    public void testUpdatedPlaceBattleshipH() {
        BattleshipModelUpdated model = new BattleshipModelUpdated();
        Gson gson = new Gson();
        String jason = gson.toJson(model);
        TestResponse res = request("POST", "/placeShip/Updated/battleship/1/1/horizontal/Hard", jason);
        assertEquals(res.status, 200);

    }

    @Test
    public void testUpdatedPlaceBattleshipV() {
        BattleshipModelUpdated model = new BattleshipModelUpdated();
        Gson gson = new Gson();
        String jason = gson.toJson(model);
        TestResponse res = request("POST", "/placeShip/Updated/Battleship/1/1/vertical/Hard", jason);
        assertEquals(res.status, 200);

        TestResponse res2 = request("POST", "/placeShip/Updated/AircraftCarrier/2/1/vertical/Hard", jason);
        assertEquals(res2.status, 200);

        TestResponse res3 = request("POST", "/placeShip/Updated/Clipper/1/1/vertical/Hard", jason);
        assertEquals(res3.status, 200);

        TestResponse res4 = request("POST", "/placeShip/Updated/Dinghy/1/1/vertical/Hard", jason);
        assertEquals(res4.status, 200);

        TestResponse res5 = request("POST", "/placeShip/Updated/Submarine/1/1/vertical/Hard", jason);
        assertEquals(res5.status, 200);

        Point testPoint = new Point(1,1);
        model.addPointtoArray(testPoint, model.getPlayerHits());
        jason = gson.toJson(model);
        TestResponse res6 = request("POST", "/placeShip/Updated/Submarine/1/1/vertical/Hard", jason);
        assertEquals(res6.status, 200);


        model = new BattleshipModelUpdated();
        gson = new Gson();
        jason = gson.toJson(model);
        TestResponse Res = request("POST", "/placeShip/Updated/Battleship/1/1/vertical/Easy", jason);
        assertEquals(Res.status, 200);

        TestResponse Res2 = request("POST", "/placeShip/Updated/AircraftCarrier/2/1/vertical/Easy", jason);
        assertEquals(Res2.status, 200);

        TestResponse Res3 = request("POST", "/placeShip/Updated/Clipper/1/1/vertical/Easy", jason);
        assertEquals(Res3.status, 200);

        TestResponse Res4 = request("POST", "/placeShip/Updated/Dinghy/1/1/vertical/Easy", jason);
        assertEquals(Res4.status, 200);

        TestResponse Res5 = request("POST", "/placeShip/Updated/Submarine/1/1/vertical/Hard", jason);
        assertEquals(Res5.status, 200);

        testPoint = new Point(1,1);
        model.addPointtoArray(testPoint, model.getPlayerHits());
        jason = gson.toJson(model);
        TestResponse Res6 = request("POST", "/placeShip/Updated/Submarine/1/1/vertical/Easy", jason);
        assertEquals(Res6.status, 200);
    }

    @Test
    public void testUpdatedPlaceBattleshipVWrong() {
        BattleshipModelUpdated model = new BattleshipModelUpdated();
        Gson gson = new Gson();
        String jason = gson.toJson(model);
        TestResponse res = request("POST", "/placeShip/Updated/battleShip/11/1/vertical/Hard", jason);
        assertEquals(res.status, 200);

    }



    @Test
    public void testUpdatedFireAt() {
        BattleshipModelUpdated model = new BattleshipModelUpdated();

        model.getPlayerAircraftCarrier().setStart(1,1);
        model.getPlayerAircraftCarrier().setEnd(1,5);
        model.getPlayerBattleship().setStart(2,1);
        model.getPlayerBattleship().setEnd(2,4);
        model.getPlayerClipper().setStart(3,1);
        model.getPlayerClipper().setEnd(3,3);
        model.getPlayerDinghy().setStart(4,1);
        model.getPlayerDinghy().setEnd(4,1);
        model.getPlayerSubmarine().setStart(5,1);
        model.getPlayerSubmarine().setEnd(5,3);

        model.getComputerAircraftCarrier().setStart(0,0);
        model.getComputerAircraftCarrier().setEnd(0,0);
        model.getComputerBattleship().setStart(2,1);
        model.getComputerBattleship().setEnd(2,4);
        model.getComputerClipper().setStart(3,1);
        model.getComputerClipper().setEnd(3,3);
        model.getComputerDinghy().setStart(4,1);
        model.getComputerDinghy().setEnd(4,1);
        model.getComputerSubmarine().setStart(5,1);
        model.getComputerSubmarine().setEnd(5,3);

        Ship_Stealth computer = new Ship_Stealth("Pizza");
        Ship_Civilian comp = new Ship_Civilian("Pizza");
        Ship co = new Ship_Stealth("Pizza");
        Ship covb = new Ship("Dinghy");
        Ship coqw = new Ship("Clipper");

        model.getShipByID("AircraftCarrier");
        model.getShipByID("Battleship");
        model.getShipByID("Clipper");
        model.getShipByID("Dinghy");
        model.getShipByID("Submarine");

        model.getShipByID("computerAircraftCarrier");
        model.getShipByID("computerBattleship");
        model.getShipByID("computerClipper");
        model.getShipByID("computerDinghy");
        model.getShipByID("computerSubmarine");

        for(int j = 0;j < 10; j++){
            for(int i = 0;i < 10; i++) {
                Gson gson = new Gson();
                String jason = gson.toJson(model);
                TestResponse res = request("POST", "/fire/Updated/"+i+"/"+j+"/Hard", jason);
                Point testPoint = new Point(j,i);
                model.addPointtoArray(testPoint, model.getComputerHits());
                assertEquals(res.status, 200);
            }
            for(int i = 0;i < 10; i++) {
                Gson gson = new Gson();
                String jason = gson.toJson(model);
                TestResponse res = request("POST", "/scan/Updated/"+i+"/"+j+"/Hard", jason);
                Point testPoint = new Point(j,i);
                model.addPointtoArray(testPoint, model.getComputerHits());
                assertEquals(res.status, 200);
            }
            for(int i = 0;i < 10; i++) {
                Gson gson = new Gson();
                String jason = gson.toJson(model);
                TestResponse res = request("POST", "/fire/Updated/"+i+"/"+j+"/Easy", jason);
                Point testPoint = new Point(j,i);
                model.addPointtoArray(testPoint, model.getComputerHits());
                assertEquals(res.status, 200);
            }
            for(int i = 0;i < 10; i++) {
                Gson gson = new Gson();
                String jason = gson.toJson(model);
                TestResponse res = request("POST", "/scan/Updated/"+i+"/"+j+"/Easy", jason);
                Point testPoint = new Point(j,i);
                model.addPointtoArray(testPoint, model.getComputerHits());
                assertEquals(res.status, 200);
            }
        }

        model.getComputerAircraftCarrier().setStart(1,1);
        model.getComputerAircraftCarrier().setEnd(1,4);


        for(int j = 0;j < 10; j++){
            for(int i = 0;i < 10; i++) {
                Gson gson = new Gson();
                String jason = gson.toJson(model);
                TestResponse res = request("POST", "/fire/Updated/"+i+"/"+j+"/Hard", jason);
                Point testPoint = new Point(j,i);
                model.addPointtoArray(testPoint, model.getComputerHits());
                assertEquals(res.status, 200);
            }

            for(int i = 0;i < 10; i++) {
                Gson gson = new Gson();
                String jason = gson.toJson(model);
                TestResponse res = request("POST", "/fire/Updated/"+i+"/"+j+"/Hard", jason);
                Point testPoint = new Point(j,i);
                model.addPointtoArray(testPoint, model.getComputerMisses());
                assertEquals(res.status, 200);
            }

            for(int i = 0;i < 10; i++) {
                Gson gson = new Gson();
                String jason = gson.toJson(model);
                TestResponse res = request("POST", "/fire/Updated/"+i+"/"+j+"/Hard", jason);
                Point testPoint = new Point(j,i);
                model.addPointtoArray(testPoint, model.getComputerHits());
                assertEquals(res.status, 200);
            }

            for(int i = 0;i < 10; i++) {
                Gson gson = new Gson();
                String jason = gson.toJson(model);
                TestResponse res = request("POST", "/fire/Updated/"+i+"/"+j+"/Hard", jason);
                Point testPoint = new Point(j,i);
                model.addPointtoArray(testPoint, model.getPlayerMisses());
                assertEquals(res.status, 200);
            }

            for(int i = 0;i < 10; i++) {
                Gson gson = new Gson();
                String jason = gson.toJson(model);
                TestResponse res = request("POST", "/fire/Updated/"+i+"/"+j+"/Hard", jason);
                Point testPoint = new Point(j,i);
                model.addPointtoArray(testPoint, model.getPlayerHits());
                assertEquals(res.status, 200);
            }

            for(int i = 0;i < 10; i++) {
                Gson gson = new Gson();
                String jason = gson.toJson(model);
                TestResponse res = request("POST", "/fire/Updated/"+i+"/"+j+"/Hard", jason);
                Point testPoint = new Point(j,i);
                model.addPointtoArray(testPoint, model.getPlayerMisses());
                assertEquals(res.status, 200);
            }

            for(int i = 0;i < 10; i++) {
                Gson gson = new Gson();
                String jason = gson.toJson(model);
                TestResponse res = request("POST", "/fire/Updated/"+i+"/"+j+"/Hard", jason);
                Point testPoint = new Point(j,i);
                model.addPointtoArray(testPoint, model.getPlayerHits());
                assertEquals(res.status, 200);
            }

            for(int i = 0;i < 10; i++) {
                Gson gson = new Gson();
                String jason = gson.toJson(model);
                TestResponse res = request("POST", "/fire/Updated/"+i+"/"+j+"/Hard", jason);
                Point testPoint = new Point(j,i);
                model.addPointtoArray(testPoint, model.getPlayerMisses());
                assertEquals(res.status, 200);
            }
            for(int i = 0;i < 10; i++) {
                Gson gson = new Gson();
                String jason = gson.toJson(model);
                TestResponse res = request("POST", "/fire/Updated/"+i+"/"+j+"/Easy", jason);
                Point testPoint = new Point(j,i);
                model.addPointtoArray(testPoint, model.getComputerHits());
                assertEquals(res.status, 200);
            }

            for(int i = 0;i < 10; i++) {
                Gson gson = new Gson();
                String jason = gson.toJson(model);
                TestResponse res = request("POST", "/fire/Updated/"+i+"/"+j+"/Easy", jason);
                Point testPoint = new Point(j,i);
                model.addPointtoArray(testPoint, model.getComputerMisses());
                assertEquals(res.status, 200);
            }

            for(int i = 0;i < 10; i++) {
                Gson gson = new Gson();
                String jason = gson.toJson(model);
                TestResponse res = request("POST", "/fire/Updated/"+i+"/"+j+"/Easy", jason);
                Point testPoint = new Point(j,i);
                model.addPointtoArray(testPoint, model.getComputerHits());
                assertEquals(res.status, 200);
            }

            for(int i = 0;i < 10; i++) {
                Gson gson = new Gson();
                String jason = gson.toJson(model);
                TestResponse res = request("POST", "/fire/Updated/"+i+"/"+j+"/Easy", jason);
                Point testPoint = new Point(j,i);
                model.addPointtoArray(testPoint, model.getPlayerMisses());
                assertEquals(res.status, 200);
            }

            for(int i = 0;i < 10; i++) {
                Gson gson = new Gson();
                String jason = gson.toJson(model);
                TestResponse res = request("POST", "/fire/Updated/"+i+"/"+j+"/Easy", jason);
                Point testPoint = new Point(j,i);
                model.addPointtoArray(testPoint, model.getPlayerHits());
                assertEquals(res.status, 200);
            }

            for(int i = 0;i < 10; i++) {
                Gson gson = new Gson();
                String jason = gson.toJson(model);
                TestResponse res = request("POST", "/fire/Updated/"+i+"/"+j+"/Easy", jason);
                Point testPoint = new Point(j,i);
                model.addPointtoArray(testPoint, model.getPlayerMisses());
                assertEquals(res.status, 200);
            }

            for(int i = 0;i < 10; i++) {
                Gson gson = new Gson();
                String jason = gson.toJson(model);
                TestResponse res = request("POST", "/fire/Updated/"+i+"/"+j+"/Easy", jason);
                Point testPoint = new Point(j,i);
                model.addPointtoArray(testPoint, model.getPlayerHits());
                assertEquals(res.status, 200);
            }

            for(int i = 0;i < 10; i++) {
                Gson gson = new Gson();
                String jason = gson.toJson(model);
                TestResponse res = request("POST", "/fire/Updated/"+i+"/"+j+"/Easy", jason);
                Point testPoint = new Point(j,i);
                model.addPointtoArray(testPoint, model.getPlayerMisses());
                assertEquals(res.status, 200);
            }
        }

        Gson gson = new Gson();
        String jason = gson.toJson(model);

        TestResponse res1 = request("POST", "/fire/Updated/2/2/Hard", jason);
        assertEquals(res1.status, 200);

        TestResponse res2 = request("POST", "/fire/Updated/4/4/Hard", jason);
        assertEquals(res2.status, 200);

        TestResponse res3 = request("POST", "/fire/Updated/6/6/Hard", jason);
        assertEquals(res3.status, 200);
    }


    private static class TestResponse {

        public final String body;
        public final int status;

        public TestResponse(int status, String body) {
            this.status = status;
            this.body = body;
        }

        public Map<String,String> json() {
            return new Gson().fromJson(body, HashMap.class);
        }
    }


}