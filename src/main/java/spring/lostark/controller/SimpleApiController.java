package spring.lostark.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ListIterator;

@RestController
public class SimpleApiController {

    @GetMapping("/api/v1/test")
    public ObjectNode getTest() throws IOException {

        final String url = "https://lostark.game.onstove.com/Profile/Character/엉왜";
        Document document = getDocument(url);

        //char info
        String itemLv = document.select(".level-info2__item").text().replace("Lv.","").replace("달성 아이템 레벨","").substring(0, 5);
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
        charInfo.put("carve", carve);
        charInfo.put("attDef", attDef);
        charInfo.put("charImage", charImage);
        charInfo.put("card", card);

        arrayNode.add(charInfo);
        result.set("data", arrayNode);

        return result;
    }

    @GetMapping("/api/v1/loatool")
    public ObjectNode loatool() throws IOException {
        Document highUrl = getDocument("https://loatool.taeu.kr/calculator/craft/117");
        Document midUrl = getDocument("https://loatool.taeu.kr/calculator/craft/131");

        //상급 오레하 시세(가격, 시세차익(20개), 사용이득, 판매이득)
        String highRate = highUrl.select("#app > div > main > div > div > div > div > div.d-flex.flex-row.justify-center > div.main-container.d-flex.flex-row.justify-center > div > div:nth-child(3) > div.pb-0.pl-md-2.col-md-6.col-12 > div > div.v-card__text.text--primary > div > div:nth-child(4) > div > div > div").text();
        String highEarn = highUrl.select("#app > div > main > div > div > div > div > div.d-flex.flex-row.justify-center > div.main-container.d-flex.flex-row.justify-center > div > div:nth-child(3) > div.pb-0.pl-md-2.col-md-6.col-12 > div > div.v-card__text.text--primary > div > div:nth-child(16) > span").text();
        String highUse = highUrl.select("#app > div > main > div > div > div > div > div.d-flex.flex-row.justify-center > div.main-container.d-flex.flex-row.justify-center > div > div:nth-child(1) > div > div > div.v-card__text.text-center > div:nth-child(2) > div:nth-child(2) > span").text();
        String highSell = highUrl.select("#app > div > main > div > div > div > div > div.d-flex.flex-row.justify-center > div.main-container.d-flex.flex-row.justify-center > div > div:nth-child(1) > div > div > div.v-card__text.text-center > div:nth-child(2) > div:nth-child(3) > span").text();

        //중급 오레하 시세(가격, 시세차익(30개), 사용이득, 판매이득)
        String midRate = midUrl.select("#app > div > main > div > div > div > div > div.d-flex.flex-row.justify-center > div.main-container.d-flex.flex-row.justify-center > div > div:nth-child(3) > div.pb-0.pl-md-2.col-md-6.col-12 > div > div.v-card__text.text--primary > div > div:nth-child(4) > div > div > div").text();
        String midEarn = midUrl.select("#app > div > main > div > div > div > div > div.d-flex.flex-row.justify-center > div.main-container.d-flex.flex-row.justify-center > div > div:nth-child(3) > div.pb-0.pl-md-2.col-md-6.col-12 > div > div.v-card__text.text--primary > div > div:nth-child(16) > span").text();
        String midUse = midUrl.select("#app > div > main > div > div > div > div > div.d-flex.flex-row.justify-center > div.main-container.d-flex.flex-row.justify-center > div > div:nth-child(1) > div > div > div.v-card__text.text-center > div:nth-child(2) > div:nth-child(2) > span").text();
        String midSell = midUrl.select("#app > div > main > div > div > div > div > div.d-flex.flex-row.justify-center > div.main-container.d-flex.flex-row.justify-center > div > div:nth-child(1) > div > div > div.v-card__text.text-center > div:nth-child(2) > div:nth-child(3) > span").text();

        System.out.println("highRate = " + highRate);
        System.out.println("highEarn = " + highEarn);
        System.out.println("highUse = " + highUse);
        System.out.println("highSell = " + highSell);
        System.out.println("midRate = " + midRate);
        System.out.println("midEarn = " + midEarn);
        System.out.println("midUse = " + midUse);
        System.out.println("midSell = " + midSell);

        //전체 json object
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode result = mapper.createObjectNode();
        result.put("code", "200");
        result.put("message", "OK");

        //캐릭터 json array
        ObjectNode data = mapper.createObjectNode();
        ArrayNode arrayNode = mapper.createArrayNode();

        //json put set
        data.put("highRate", highRate);
        data.put("highEarn", highEarn);
        data.put("highUse", highUse);
        data.put("highSell", highSell);
        data.put("midRate", midRate);
        data.put("midEarn", midEarn);
        data.put("midUse", midUse);
        data.put("midSell", midSell);

        arrayNode.add(data);
        result.set("data", arrayNode);

        return result;
    }

    @GetMapping("/api/v1/chaosmap")
    public ObjectNode chaosMap() throws IOException {
        Document mapUrl = getDocument("https://loatool.taeu.kr/calculator/secret-map");

        String evenPoint =mapUrl.select("#app > div > main > div > div > div > div > div.d-flex.flex-row.justify-center > div.main-container.d-flex.flex-row.justify-center > div > div.v-window.v-item-group.theme--light.v-tabs-items > div > div.v-window-item.v-window-item--active > div:nth-child(1) > div > div:nth-child(3) > div:nth-child(2) > span").text();
        String bestPoint = mapUrl.select("#app > div > main > div > div > div > div > div.d-flex.flex-row.justify-center > div.main-container.d-flex.flex-row.justify-center > div > div.v-window.v-item-group.theme--light.v-tabs-items > div > div.v-window-item.v-window-item--active > div:nth-child(1) > div > div:nth-child(4) > div:nth-child(2) > span").text();
        String avgPoint = mapUrl.select("#app > div > main > div > div > div > div > div.d-flex.flex-row.justify-center > div.main-container.d-flex.flex-row.justify-center > div > div.v-window.v-item-group.theme--light.v-tabs-items > div > div.v-window-item.v-window-item--active > div:nth-child(1) > div > div:nth-child(5) > div:nth-child(2) > span").text();
        //전체 json object
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode result = mapper.createObjectNode();
        result.put("code", "200");
        result.put("message", "OK");

        //data json array
        ObjectNode data = mapper.createObjectNode();
        ArrayNode arrayNode = mapper.createArrayNode();

        //json put set
        data.put("evenPoint", evenPoint);
        data.put("bestPoint", bestPoint);
        data.put("avgPoint", avgPoint);

        arrayNode.add(data);
        result.set("data", arrayNode);

        return result;
    }

    @GetMapping("/api/v1/homework")
    public ObjectNode homework() throws IOException {

        Document contentUrl = getDocument("https://lostark.inven.co.kr/");
        int liSize = contentUrl.select("#timerLeftContent > a > div.hotbossPart > ul").select("li").size();
        List<String> li = contentUrl.select("#timerLeftContent > a > div.hotbossPart > ul").select("li").eachText();
        List<String> contentName = contentUrl.select("#timerLeftContent > a > div.hotbossPart > ul").select("li").select("p.npcname").eachText();
        List<String> contentTime = contentUrl.select("#timerLeftContent > a > div.hotbossPart > ul").select("li").select("p.gentime").eachText();
        List<String> endDateTime = contentUrl.select("#timerLeftContent > a > div.hotbossPart > ul").select("li").select("p").eachAttr("data-datetime");
        List<String> contentImg = contentUrl.select("#timerLeftContent > a > div.hotbossPart > ul").select("li").select("img").eachAttr("src");


        //시작시간, 시작시간변환
        String startDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime changeDate1 = LocalDateTime.parse(startDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        //전체 json object
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode result = mapper.createObjectNode();
        result.put("code", "200");
        result.put("message", "OK");

        //캐릭터 json array
        ArrayNode arrayNode = mapper.createArrayNode();

        //json put set
        for (int i = 0; i < liSize; i++) {
            ObjectNode dataInfo = mapper.createObjectNode();
            dataInfo.put("contentId", i);
            dataInfo.put("contentName", contentName.get(i));
            dataInfo.put("contentTime", contentTime.get(i));
            dataInfo.put("contentImg", contentImg.get(i));

            LocalDateTime changeDate2 = LocalDateTime.parse(endDateTime.get(i), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            Duration duration = Duration.between(changeDate1, changeDate2);
            long time = duration.getSeconds();
            long hour = time/(60*60);
            long minute = time/60-(hour*60);

            dataInfo.put("endTime", time == 0 ? "출현중" : hour + "시 " + minute + "분 " + "뒤 출현");

            arrayNode.add(dataInfo);
        }

        result.set("data", arrayNode);

        return result;
    }

    private Document getDocument(String url) throws IOException {
        return Jsoup.connect(url).ignoreContentType(true).get();
    }
}
