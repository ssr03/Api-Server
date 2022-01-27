package framework.apiserver.core.push;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import framework.apiserver.core.push.exception.PushFcmException;
import framework.apiserver.core.push.exception.PushTokenNotFoundException;
import framework.apiserver.core.security.user.UserRepository;
import framework.apiserver.core.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Slf4j
@Service
public class PushServiceImpl implements PushService{
    @Autowired
    private Util util;

    @Autowired
    private UserRepository userRepository;

//    @Value("classpath:ijun-eas-firebase-adminsdk-o8jc8-f1dd258219.json")
//    Resource firebaseAdminSDKKey;
    ClassPathResource classPathResource = new ClassPathResource("ijun-eas-firebase-adminsdk-o8jc8-f1dd258219.json");

    @PostConstruct
    public void postConstruct() throws IOException {
//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setCredentials(GoogleCredentials.fromStream(classPathResource.getInputStream()))
//                .build();
//
//        FirebaseApp.initializeApp(options);
        log.info("firebaseAdminSDK initiated.");
    }

    @Override
    public void pushMessage(PushMessage pushMessage) {
        try {
            if (pushMessage.getFcmToken().isEmpty()) {
                throw new PushTokenNotFoundException();
            }

            Message message = Message.builder()
                    .setNotification(new Notification(pushMessage.getTitle(), pushMessage.getUserLoginId()))
                    .putData("pushNo", pushMessage.getPushNo())
                    .putData("body", pushMessage.getBody())
                    .setToken(pushMessage.getFcmToken())
                    .build();

            ApiFuture<String> apiFuture = FirebaseMessaging.getInstance().sendAsync(message);
            log.info("fcm push 전송 요청 pushNo:{}", pushMessage.getPushNo());
        }catch (PushFcmException e){
            log.info(e.getMessage() + " pushNo:{}", pushMessage.getPushNo());
        }
    }

    @Override
    public void updatePushToken(String token) {
        String loginId = util.getLoginId();
        userRepository.updatePushToken(token, loginId);
    }

    @Override
    public String getPushToken() {
        String token = util.getLoginUser().getAttribute1();
        return ObjectUtils.isEmpty(token) ? "" : token;
    }
}
