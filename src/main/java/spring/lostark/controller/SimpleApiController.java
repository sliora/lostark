package spring.lostark.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class SimpleApiController {
    @GetMapping("/api/v1/test")
    public String getTest() throws IOException {

        final String url = "https://lostark.game.onstove.com/Profile/Character/엉왜";

        Document document = Jsoup.connect(url).ignoreContentType(true).get();

        String charLv = document.select(".level-info__item").text().replace("Lv.", "").replace("전투 레벨", "");

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode result = mapper.createObjectNode();
        result.put("code", "200");
        result.put("message", "OK");

        ObjectNode charInfo = mapper.createObjectNode();

        charInfo.put("charLv", charLv);

        ArrayNode arrayNode = mapper.createArrayNode();
        arrayNode.add(charInfo);

        result.set("data", arrayNode);

        return result.toString();


    }
}
