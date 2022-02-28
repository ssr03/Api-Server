package framework.apiserver.core.push;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushMessage {
    private String fcmToken;
    private String pushNo;
    private String userLoginId;
    private String title;
    private String body;
}
