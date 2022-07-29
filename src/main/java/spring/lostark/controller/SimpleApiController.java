package spring.lostark.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class SimpleApiController {
    @GetMapping("/api/v1/test")
    public ObjectNode getTest() throws IOException {

        final String url = "https://lostark.game.onstove.com/Profile/Character/엉왜";

        Document document = Jsoup.connect(url).ignoreContentType(true).get();

        //char info
        String itemLv = document.select(".level-info2__item").get(0).ownText();
        String charLv = document.select(".level-info__item").text().replace("Lv.", "").replace("전투 레벨", "");
        String totalLv = document.select(".level-info__expedition").text().replace("Lv.", "").replace("원정대 레벨", "");
        String server = document.select(".profile-character-info__server").text().replace("@", "");
        String guild = document.select(".game-info__guild").text().replace("길드", "");
        String skillPoint = document.select(".profile-skill__point").text().replace("사용 스킬 포인트", "").replace("보유 스킬 포인트", "");
        String carve = document.select(".profile-ability-engrave span").text().replace("환류", "환").replace("예리한 둔기", "예").replace("타격의 대가", "타").replace("아드레날린", "아").replace("원한", "원").replace("저주받은 인형", "저").replace("버스트", "버").replace("기습의 대가", "기").replace("속전속결", "속").replace("점화", "점").replace("돌격대장", "돌").replace("멈출 수 없는 충동", "충").replace("핸드거너", "핸").replace("정밀 단도", "정").replace("속전속결", "속").replace("안정된 상태", "안").replace("진화의 유산", "유").replace("바리게이트", "바").replace("최대 마나 증가", "최").replace("절실한 구원", "절").replace("중갑 착용", "중").replace("각성", "각").replace("위기 모면", "위").replace("달인의 저력", "달").replace("강화 무기", "강").replace("정기 흡수", "정").replace("일격필살", "일").replace("피스메이커", "피").replace("잔재된 기운", "잔").replace("슈퍼 차지", "슈").replace("상급 소환사", "상").replace("만개", "만").replace("전문의", "전");
        String attDef = document.select(".profile-ability-basic span").next("span").text().replace(" ", " / ").replace("\\B(?=(\\d{3})+(?!\\d))", ",");
        String charImage = document.select(".profile-equipment__character img").get(0).absUrl("src");
        String card = document.select(".card-effect__title").last().text();

        //전체 json object
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode result = mapper.createObjectNode();
        result.put("code", "200");
        result.put("message", "OK");

        //캐릭터 json array
        ObjectNode charInfo = mapper.createObjectNode();
        ArrayNode arrayNode = mapper.createArrayNode();

        //json put set
        charInfo.put("itemLv", itemLv);
        charInfo.put("charLv", charLv);
        charInfo.put("totalLv", totalLv);
        charInfo.put("server", server);
        charInfo.put("guild", guild);
        charInfo.put("skillPoint", skillPoint);
        charInfo.put("carve", card);
        charInfo.put("attDef", attDef);
        charInfo.put("charImage", charImage);
        charInfo.put("card", card);

        arrayNode.add(charInfo);
        result.set("data", arrayNode);

        return result;

        //githook 테스트2
    }
}
