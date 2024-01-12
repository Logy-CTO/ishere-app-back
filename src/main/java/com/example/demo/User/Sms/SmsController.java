package com.example.demo.User.Sms;
import com.google.api.client.util.Value;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import javax.annotation.PostConstruct;
import java.util.Random;

@RequiredArgsConstructor
@RestController
public class SmsController {

    private DefaultMessageService messageService;

    @org.springframework.beans.factory.annotation.Value("${cool-sms.api-key}")
    private String apiKey;

    @org.springframework.beans.factory.annotation.Value("${cool-sms.api-secret}")
    private String apiSecret;

    @org.springframework.beans.factory.annotation.Value("${cool-sms.api-url}")
    private String apiUrl;

    @PostConstruct
    private void init() {
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, apiUrl);
    }
    //api연동
    @PostMapping("/sendSMS")
    @ResponseBody
    public SingleMessageSentResponse sendSMS(@RequestParam String to, HttpSession session) {
        Random rand = new Random();
        String numStr = "";
        for(int i=0; i<6; i++){
            String ran = Integer.toString(rand.nextInt(10));
            numStr += ran;
        }//인증번호 난수값 생성
        session.setAttribute("authNum", numStr);
        session.setAttribute("creationTime", System.currentTimeMillis());
        Message message = new Message();
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
        message.setFrom("01064371608");//테스트할 때만 국현우 번호 사용하여 발신
        message.setTo(to);//수신 -> HTML부분에서 JS로 수신자 번호 받음
        message.setText("[Ishere] 이즈히어 인증번호는 [" + numStr + "] 입니다.");

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);
        return response;
    }
    @PostMapping("/verifySMS")
    @ResponseBody
    public boolean verifySMS(@RequestParam String inputNum, HttpSession session) {
        // 세션에 저장된 인증번호와 생성 시간.
        String authNum = (String) session.getAttribute("authNum");
        long creationTime = (Long) session.getAttribute("creationTime");

        // 현재 시간(밀리초 단위)을 가져온 후 대입.
        long currentTime = System.currentTimeMillis();

        // 3분이 지났는지 확인
        if (currentTime - creationTime > 3 * 60 * 1000) {
            // 3분이 지났다면 세션에서 인증번호와 생성 시간을 삭제하고 false를 반환
            session.removeAttribute("authNum");
            session.removeAttribute("creationTime");
            return false;
        }

        // 사용자가 입력한 인증번호와 세션에 저장된 인증번호가 일치하는지 확인
        boolean isMatch = inputNum.equals(authNum);

        // 인증번호가 일치하면 세션에서 인증번호와 생성 시간을 삭제
        if (isMatch) {
            session.removeAttribute("authNum");
            session.removeAttribute("creationTime");
        }

        return isMatch;
    }
}