package framework.apiserver.core.push;

public interface PushService {
    void pushMessage(PushMessage pushMessage);

    void updatePushToken(String token);

    String getPushToken();
}
