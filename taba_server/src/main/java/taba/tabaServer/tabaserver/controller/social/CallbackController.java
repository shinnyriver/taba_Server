package taba.tabaServer.tabaserver.controller.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CallbackController {

    @Autowired
    private RestTemplate restTemplate;

    //redirect-Uri
    @GetMapping("/callback/kakao")
    public ResponseEntity<String> handleKakaoCallback(@RequestParam String code) {
        // API 엔드포인트 URL
        String apiUrl = "http://localhost:8080/api/oauth/kakao";

        // 인가 코드를 JSON 형태로 포장
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> body = new HashMap<>();
        body.put("authorizationCode", code);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        // RestTemplate를 사용하여 OAuthController의 /kakao 엔드포인트 호출
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            // 성공 응답을 클라이언트에 전달
            return ResponseEntity.ok("로그인에 성공하셨습니다. 앱으로 돌아가주세요. Token: " + response.getBody());
        } else {
            // 실패 응답 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그인 실패");
        }
    }
    @GetMapping("/callback/naver")
    public ResponseEntity<String> naverCallback(@RequestParam String code, @RequestParam(required = false) String state) {
        // API 엔드포인트 URL
        String apiUrl = "http://localhost:8080/api/oauth/naver";

        // 인증 코드를 JSON 형태로 포장
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> body = new HashMap<>();
        body.put("authorizationCode", code);
        body.put("state", state);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        // RestTemplate를 사용하여 OAuthController의 /naver 엔드포인트 호출
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            // 성공 응답을 클라이언트에 전달
            return ResponseEntity.ok("네이버 로그인에 성공하셨습니다. 앱으로 돌아가주세요. Token: " + response.getBody());
        } else {
            // 실패 응답 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("네이버 로그인 실패: " + response.getBody());
        }
    }
}