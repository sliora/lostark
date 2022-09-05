package spring.lostark.config;

import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolver;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class utf8DecodeResourceResolver extends PathResourceResolver implements ResourceResolver {

    //해당 UTF_8이 되는지 재확인 필요
    //맥 파일질라로 한글 파일 전송시 자음모음이 분리되는 현상이 있음..
    //하지만 리눅스에서 파일조회 해보면 정상으로 나옴 ㅜㅜ..
    //윈도우에서 확인하면 파일명 분리됨
    //해당 Resolver 없이 되는지는 확인해볼 필요 있을듯..
    @Override
    protected Resource getResource(String resourcePath, Resource location) throws IOException {
        resourcePath = URLDecoder.decode(resourcePath, StandardCharsets.UTF_8);
        return super.getResource(resourcePath, location);
    }
}
