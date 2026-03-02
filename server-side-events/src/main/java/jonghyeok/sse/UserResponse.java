package jonghyeok.sse;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserResponse {
    private String threadName;
    private String data;
}
